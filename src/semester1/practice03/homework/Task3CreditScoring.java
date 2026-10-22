package semester1.practice03.homework;

import java.util.Scanner;

public class Task3CreditScoring {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== СИСТЕМА БАНКІВСЬКОГО СКОРИНГУ ===");

        System.out.print("Скільки вам рочків?: ");
        int age = scanner.nextInt();

        System.out.print("Скільки ви заробляєте? (грн): ");
        double monthlyIncome = scanner.nextDouble();
        if (monthlyIncome < 10000) {
            System.out.println("Фу, ніщук");
            return;
        }

        System.out.print("Чи є негативна кредитна історія? (true/false): ");
        boolean hasBadCreditHistory = scanner.nextBoolean();

        System.out.print("Якого розміру кредит хочете взяти? (грн): ");
        double loanAmount = scanner.nextDouble();

        System.out.print("За який час плануєте повернути? (місяців): ");
        int loanMonths = scanner.nextInt();

        boolean isAgeValid = age >= 21 && age <= 65;
        boolean isCreditHistoryGood = !hasBadCreditHistory;

        boolean isPaymentAffordable = (loanMonths > 0)
                && ((loanAmount / loanMonths) <= (monthlyIncome * 0.5));

        double annualRate = monthlyIncome >= 35000 ? 14.5 : 21.0;
        double maxPayment = monthlyIncome * 0.5;

        System.out.println();
        System.out.println("------------- РІШЕННЯ СКОРИНГУ -------------");

        if (isAgeValid && isCreditHistoryGood && isPaymentAffordable) {
            double monthlyPayment = loanAmount / loanMonths;

            System.out.println("Статус заявки: СХВАЛЕНО");
            System.out.printf("Орієнтовний платіж/міс: %.2f грн%n", monthlyPayment);

            if (monthlyIncome > 0) {
                double paymentPercent = monthlyPayment / monthlyIncome * 100;
                System.out.printf("Частка від доходу: %.1f%%%n", paymentPercent);
            }

            System.out.printf("Персональна ставка: %.1f%% річних%n", annualRate);
        } else if (!isAgeValid) {
            System.out.println("Статус заявки: ВІДХИЛЕНО");
            System.out.println("Відмова: вік не відповідає критеріям (21-65 років)");
        } else if (!isCreditHistoryGood) {
            System.out.println("Статус заявки: ВІДХИЛЕНО");
            System.out.println("Відмова: виявлено негативну кредитну історію");
        } else if (loanMonths <= 0 || !isPaymentAffordable) {
            System.out.println("Статус заявки: ВІДХИЛЕНО");
            System.out.println("Відмова: термін кредиту некоректний або щомісячний платіж перевищує 50% доходу");
        }

        System.out.printf("Максимально допустимий платіж: %.2f грн/міс%n", maxPayment);
        System.out.println("=============================================");
    }
}
