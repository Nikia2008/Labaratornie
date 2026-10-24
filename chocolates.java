import java.util.Scanner;

static int redeem(int bonusCount, int bonusPerChoco, int[] totalRef) {
    int gained = 0;
    int bonus = bonusCount;
    while (bonus >= bonusPerChoco) {
        int newChoco = bonus / bonusPerChoco;
        gained += newChoco;
        totalRef[0] += newChoco;
        bonus = bonus % bonusPerChoco + newChoco;
    }
    return gained;
}

void main(){
        Scanner scan = new Scanner(System.in);

        System.out.print("Введите бюджет: ");
        int budget = scan.nextInt();
        System.out.print("Введите цену одной шоколадки: ");
        int price = scan.nextInt();
        System.out.print("Введите k (за сколько купленных дают 1): ");
        int k = scan.nextInt();
        System.out.print("Введите f (за сколько фантиков дают 1): ");
        int f = scan.nextInt();

        if (budget < 0 || price <= 0 || k <= 0 || f <= 1) {
            System.out.println("Ошибка: проверьте введённые значения.");
            return;
        }

        int bought = budget / price;
        int[] total = { bought };

        redeem(bought, k, total);

        redeem(total[0], f, total);

        System.out.println("Без добавки: " + bought);
        System.out.println("Всего: " + total[0]);
    }
