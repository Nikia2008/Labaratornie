

    // 1 способ
    public static int For(int a, int b) {
        int result = 0;
        for (int i = 0; i < b; i++) {
            result = result + a;
        }
        return result;
    }

    // 2 способ
    public static int While(int a, int b) {
        int result = 0;
        while (b > 0) {
            result = result + a;
            b--;
        }
        return result;
    }

    // 3 способ
    public static int Recursion(int a, int b) {
        if (b == 0) return 0;
        return a + Recursion(a, b - 1);
    }

    // 4 способ
    public static int Array(int a, int b) {
        int[] ar = new int[b];
        int result = 0;
        for (int i = 0; i < b; i++) {
            ar[i] = a;
            result = result + ar[i];
        }
        return result;
    }

    // 5 способ
    public static int ForEach(int a, int b) {
        int result = 0;
        int[] temp = new int[b];
        for (int num : temp) {
            result = result + a;
        }
        return result;
    }

    // 6 способ
    public static int DoWhile(int a, int b) {
        int result = 0;
        int count = 0;
        do {
            result = result + a;
            count++;
        } while (count < b);
        return result;
    }

public static void main(String[] args) {
    int a = 5;
    int b = 5;

    System.out.println("1. For: " + For(a, b));
    System.out.println("2. While: " + While(a, b));
    System.out.println("3. Рекурсия: " + Recursion(a, b));
    System.out.println("4. Массив: " + Array(a, b));
    System.out.println("5. ForEach: " + ForEach(a, b));
    System.out.println("6. DoWhile: " + DoWhile(a, b));
}
