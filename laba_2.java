void main() {
    AnimalThread rabbit = new AnimalThread("Кролик", 5);
    AnimalThread turtle = new AnimalThread("Черепаха", 10);

    rabbit.start();
    turtle.start();
}

static class AnimalThread extends Thread {
    private final String name;

    AnimalThread(String name, int priority) {
        this.name = name;
        setName(name);
        setPriority(priority);
    }

    @Override
    public void run(){
        int meters = 0;
        while (meters < 100) {
            meters++;
            System.out.printf("\n%s пробежал %d метров\n", name, meters);

            if(name.equals("Черепаха")) {
                setPriority(10);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("\n%s FINISH!!\n", name);
    }
}
