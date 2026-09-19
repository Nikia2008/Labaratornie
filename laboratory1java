void main() throws IOException {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Ввведите имя первого процесса!");
    Process processOne = new ProcessBuilder(scanner.nextLine() + ".exe").start();
    System.out.println("Ввведите имя второго процесса!");
    Process processTwo = new ProcessBuilder(scanner.nextLine() + ".exe").start();
    System.out.println("Ввведите имя третьего процесса!");
    Process processThree = new ProcessBuilder(scanner.nextLine() + ".exe").start();

    System.out.printf(
            """
                Блокнот PID: %s
                Калькулятор PID: %s
                Paint PID: %s
            """, processOne.pid(), processTwo.pid(), processThree.pid()
    );

    System.out.print("Закрыть первый процесс? Д/Н: ");
    if (scanner.nextLine().equalsIgnoreCase("д")) {
        processOne.destroyForcibly();
        System.out.println("первый процесс закрыт");
    }

    System.out.print("Закрыть второй процесс? Д/Н: ");
    if (scanner.nextLine().equalsIgnoreCase("д")) {
        processTwo.destroyForcibly();
        System.out.println("второй процесс закрыт");
    }

    System.out.print("Закрыть третий процесс? Д/Н: ");
    if (scanner.nextLine().equalsIgnoreCase("д")) {
        processThree.destroyForcibly();
        System.out.println("третий процесс закрыт");
    }

}
