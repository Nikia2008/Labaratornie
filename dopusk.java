import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


void main() throws InterruptedException {
    final int N = 1000, THREADS = 4;
    int[] numbers = new Random().ints(N, 1, 101).toArray();

    long startSeq = System.nanoTime();
    long seqSum = 0;
    for (int x : numbers) seqSum += x;
    long seqTime = System.nanoTime() - startSeq;

    AtomicLong parSum = new AtomicLong();
    int chunk = N / THREADS;

    long startPar = System.nanoTime();
    Thread[] threads = new Thread[THREADS];
    for (int t = 0; t < THREADS; t++) {
        final int from = t * chunk;
        final int to = (t == THREADS - 1) ? N : from + chunk;
        threads[t] = new Thread(() -> {
            long s = 0;
            for (int i = from; i < to; i++) s += numbers[i];
            parSum.addAndGet(s);
        });
        threads[t].start();
    }
    for (Thread th : threads) th.join();
    long parTime = System.nanoTime() - startPar;

    System.out.printf("Последовательно: сумма = %d, время = %.3f мс%n", seqSum, seqTime / 1_000_000.0);
    System.out.printf("Параллельно:     сумма = %d, время = %.3f мс%n", parSum.get(), parTime / 1_000_000.0);
    System.out.println(seqSum == parSum.get() ? "Суммы совпадают" : "ОШИБКА: суммы не совпадают!");
    System.out.println(parTime < seqTime ? "Параллельный вариант быстрее" : "Последовательный вариант быстрее");
}
