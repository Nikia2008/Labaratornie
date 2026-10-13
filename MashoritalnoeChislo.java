void main() {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
        arr[i] = scanner.nextInt();
    }
    
    int candidate = arr[0];
    int count = 1;

    for (int i = 1; i < n; i++) {
        if (arr[i] == candidate) {
            count++;
        } else {
            count--;
            if (count == 0) {
                candidate = arr[i];
                count = 1;
            }
        }
    }

    int occurrences = 0;
    for (int i = 0; i < n; i++) {
        if (arr[i] == candidate) {
            occurrences++;
        }
    }
    
    if (occurrences > n / 2) {
        System.out.println(candidate);
    } else {
        System.out.println("Мажоритарного элемента нет");
    }

}
