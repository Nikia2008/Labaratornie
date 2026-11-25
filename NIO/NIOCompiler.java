package org.example.NIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import static java.nio.file.StandardOpenOption.*;

public final class NIOCompiler {

    private static final int BUF = 64 * 1024;

    private NIOCompiler() {}

    public static void copySync(Path src, Path dst) throws IOException {
        try (FileChannel in = FileChannel.open(src, READ);
             FileChannel out = FileChannel.open(dst, CREATE, WRITE, TRUNCATE_EXISTING)) {

            ByteBuffer buf = ByteBuffer.allocate(BUF);
            while (in.read(buf) != -1) {
                buf.flip();
                while (buf.hasRemaining()) out.write(buf);
                buf.clear();
            }
        }
    }

    public static CompletableFuture<Void> copyAsync(Path src, Path dst) {
        CompletableFuture<Void> done = new CompletableFuture<>();
        try {
            AsynchronousFileChannel in = AsynchronousFileChannel.open(src, READ);
            AsynchronousFileChannel out = AsynchronousFileChannel.open(
                    dst, CREATE, WRITE, TRUNCATE_EXISTING);
            new AsyncCopy(in, out, done).start();
        } catch (IOException e) {
            done.completeExceptionally(e);
        }
        return done;
    }

    private static final class AsyncCopy {
        private final AsynchronousFileChannel in, out;
        private final CompletableFuture<Void> done;
        private final ByteBuffer buf = ByteBuffer.allocate(BUF);
        private long readPos, writePos;

        AsyncCopy(AsynchronousFileChannel in, AsynchronousFileChannel out,
                  CompletableFuture<Void> done) {
            this.in = in;
            this.out = out;
            this.done = done;
        }

        void start() { read(); }

        private void read() {
            in.read(buf, readPos, null, new java.nio.channels.CompletionHandler<>() {
                public void completed(Integer n, Object att) {
                    if (n == -1) { close(); return; }
                    readPos += n;
                    buf.flip();
                    write();
                }
                public void failed(Throwable t, Object att) { fail(t); }
            });
        }

        private void write() {
            out.write(buf, writePos, null, new java.nio.channels.CompletionHandler<>() {
                public void completed(Integer n, Object att) {
                    writePos += n;
                    if (buf.hasRemaining()) { write(); }
                    else { buf.clear(); read(); }
                }
                public void failed(Throwable t, Object att) { fail(t); }
            });
        }

        private void close() {
            try {
                in.close();
                out.close();
                done.complete(null);
            } catch (IOException e) {
                fail(e);
            }
        }

        private void fail(Throwable t) {
            try { in.close(); } catch (IOException ignored) {}
            try { out.close(); } catch (IOException ignored) {}
            done.completeExceptionally(t);
        }
    }
}