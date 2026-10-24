package WithLossResult;

import java.util.concurrent.atomic.AtomicInteger;

public class InterferenceExample {
    private static final int HUNDRED_MILLION = 100_000_000;
    private int mode = 0;
    private final AtomicInteger counter = new AtomicInteger();
    private final AtomicInteger atomicI = new AtomicInteger();
    private int synchronizedI = 0;

    public InterferenceExample() {
        this.mode = mode;
    }

    boolean stop() {
        return counter.incrementAndGet() > HUNDRED_MILLION;
    }

    void incrementResult() {
        if (mode == 1) {
            atomicI.incrementAndGet();
        } else {
            incrementSynchronized();
        }
    }

    private synchronized void incrementSynchronized() {
        synchronizedI++;
    }

    int getResult() {
        if (mode == 1) {
            return atomicI.get();
        } else {
            return getSynchronizedResult();
        }
    }

    private synchronized int getSynchronizedResult() {
        return synchronizedI;
    }

    public void example() throws InterruptedException {
        InterferenceThread thread1 = new InterferenceThread(this);
        InterferenceThread thread2 = new InterferenceThread(this);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Expected: " + HUNDRED_MILLION);
        System.out.println("Result: " + getResult());
    }
}