void main() throws FileNotFoundException {
    List<Integer> nums = new ArrayList<>();
    Scanner scanner = new Scanner(new File("C:\\Users\\nnn\\Desktop\\1111.txt"));
    while(scanner.hasNextInt()){
        nums.add(scanner.nextInt());
    }

    int min37 = Integer.MAX_VALUE;
    int max73 = Integer.MIN_VALUE;

    for(int x : nums) {
        if(x % 37 == 0 && x < min37) {
            min37 = x;
        }
        if(x % 73 == 0 && x > max73) {
            max73 = x;
        }
    }

    int count = 0;
    int minSum = Integer.MAX_VALUE;
    int left = Math.min(min37, max73);
    int right = Math.max(min37, max73);
    

    for(int i = 0; i < nums.size() - 1; i++) {
        int a = nums.get(i);
        int b = nums.get(i+1);

        boolean aa = a > left && a < right;
        boolean bb = b > left && b < right;

        if(aa != bb) {
            count++;
            int sum = a + b;
            if (sum < minSum) {
                minSum = sum;
            }
        }
    }

    System.out.printf("Кол-во пар: %d\n", count);
    System.out.printf("Минимальная сумма: %d", minSum);
}
