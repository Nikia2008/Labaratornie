// Вариант - 1
void main() {
    Scanner scanner = new Scanner(System.in);
    
    int[] numbers = new int[1000];
    for (int i = 0; i < 1000; i++) {
        numbers[i] = scanner.nextInt();
    }

    int maxR = -1;
    
    for (int i = 0; i < 1000; i++) {
        for (int j = i + 1; j < 1000; j++) {
            int product = numbers[i] * numbers[j];
            
            if (product % 14 == 0 && product > maxR) {
                maxR = product;
            }
        }
    }
    
    System.out.println(maxR);
  
}
