static final Object lock = new Object();
static boolean eggTurn = true;   
static int step = 0;             
static final int MAX_STEPS = 10; 

void main() throws InterruptedException {

    Thread egg = new Thread(() -> speak("Яйцо", true));
    Thread chicken = new Thread(() -> speak("Курица", false));

    System.out.println("Спор начался...\n");

    egg.start();
    chicken.start();

    egg.join();     
    chicken.join();


    if (step % 2 == 0) {
        System.out.println("\nПобедила КУРИЦА! (сказала последнее слово)");
    } else {
        System.out.println("\nПобедило ЯЙЦО! (сказало последнее слово)");
    }
}

static void speak(String name, boolean isEgg) {
    while (true) {
        synchronized (lock) {
            while (step < MAX_STEPS && eggTurn != isEgg) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            if (step >= MAX_STEPS) {
                lock.notifyAll();
                return;
            }

            step++;
            System.out.println(name + " " + step);

            eggTurn = !eggTurn; 
            lock.notifyAll();     
        }
    }
}
