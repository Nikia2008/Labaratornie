import java.io.*;
import java.nio.file.*;

class CopyFiles {

    static void copyFile(String from, String to) throws IOException {
        Files.copy(Path.of(from), Path.of(to), StandardCopyOption.REPLACE_EXISTING);
    }

    static void createTestFile(String name, int lines) throws IOException {
        File f = new File(name);
        if (f.exists()) return;
        try (BufferedWriter w = new BufferedWriter(new FileWriter(f))) {
            for (int i = 1; i <= lines; i++) {
                w.write("Строка номер " + i + " текстового файла " + name);
                w.newLine();
            }
        }
    }

    static long timeCopy(String in, String out) throws IOException {
        long start = System.nanoTime();
        copyFile(in, out);
        return System.nanoTime() - start;
    }

    public static void main(String[] args) throws Exception {
        String in1 = "file1.txt", in2 = "file2.txt";

        createTestFile(in1, 2_000_000);
        createTestFile(in2, 2_000_000);

        long seqTime = timeCopy(in1, "copy_seq_1.txt") + timeCopy(in2, "copy_seq_2.txt");

        long startPar = System.nanoTime();
        Thread t1 = new Thread(() -> copyQuietly(in1, "copy_par_1.txt"));
        Thread t2 = new Thread(() -> copyQuietly(in2, "copy_par_2.txt"));
        t1.start(); t2.start();
        t1.join(); t2.join();
        long parTime = System.nanoTime() - startPar;

        System.out.printf("Последовательно: %.2f мс%n", seqTime / 1_000_000.0);
        System.out.printf("Параллельно:     %.2f мс%n", parTime / 1_000_000.0);
        System.out.println(parTime < seqTime
                ? "Параллельное копирование быстрее"
                : "Последовательное копирование быстрее");
    }

    static void copyQuietly(String in, String out) {
        try {
            copyFile(in, out);
        } catch (IOException e) {
            System.out.println("Ошибка копирования " + in + ": " + e.getMessage());
        }
    }
}
