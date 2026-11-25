import org.example.NIO.NIOCompiler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;


void main(String[] args) {
    try {
        Path one = Path.of(args.length == 2 ? args[0] : "file1.txt");
        Path two = Path.of(args.length == 2 ? args[1] : "file2.txt");

        if (args.length == 0) {
            makeFile(one, 500_000);
            makeFile(two, 500_000);
        }

        Path seqOne = Path.of("copy_seq_1.txt");
        Path seqTwo = Path.of("copy_seq_2.txt");
        Path asyncOne = Path.of("copy_async_1.txt");
        Path asyncTwo = Path.of("copy_async_2.txt");

        System.out.println("--- Синхронно ---");
        long t1 = System.nanoTime();
        NIOCompiler.copySync(one, seqOne);
        NIOCompiler.copySync(two, seqTwo);
        long seqTime = System.nanoTime() - t1;

        System.out.println("--- Асинхронно ---");
        long t2 = System.nanoTime();
        CompletableFuture<Void> a = NIOCompiler.copyAsync(one, asyncOne);
        CompletableFuture<Void> b = NIOCompiler.copyAsync(two, asyncTwo);
        CompletableFuture.allOf(a, b).join();
        long asyncTime = System.nanoTime() - t2;

        System.out.printf(Locale.ROOT, "%nСинхронно:  %.3f мс%n", seqTime / 1e6);
        System.out.printf(Locale.ROOT, "Асинхронно: %.3f мс%n", asyncTime / 1e6);

        List<Path[]> pairs = List.of(
                new Path[]{one, seqOne},
                new Path[]{two, seqTwo},
                new Path[]{one, asyncOne},
                new Path[]{two, asyncTwo});

        for (Path[] p : pairs) {
            if (Files.mismatch(p[0], p[1]) != -1) {
                throw new IOException("Копия отличается: " + p[1]);
            }
        }
        System.out.println("Все копии совпадают с исходниками.");

    } catch (Exception e) {
        System.err.println("Ошибка: " + e.getMessage());
    }
}

private static void makeFile(Path path, int lines) throws IOException {
    if (Files.exists(path)) return;
    System.out.println("Создаётся: " + path);
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= lines; i++) {
        sb.append("Строка ").append(i).append(" в ").append(path.getFileName()).append('\n');
    }
    Files.writeString(path, sb, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
}
