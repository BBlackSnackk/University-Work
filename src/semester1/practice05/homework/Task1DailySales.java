package semester1.practice05.homework;

import java.util.Scanner;

public class Task1DailySales {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] days = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця", "Субота", "Неділя"};
        double[] sales = new double[7];

        System.out.println("=== ФІНАНСОВИЙ ЗВІТ КАВ'ЯРНІ ЗА ТИЖДЕНЬ ===");
        for (int i = 0; i < sales.length; i++) {
            System.out.print("Введіть виторг за " + days[i] + " (грн): ");
            sales[i] = scanner.nextDouble();
        }

        double total = 0;
        int maxIndex = 0;
        int minIndex = 0;

        for (int i = 0; i < sales.length; i++) {
            total += sales[i];
            if (sales[i] > sales[maxIndex]) {
                maxIndex = i;
            }
            if (sales[i] < sales[minIndex]) {
                minIndex = i;
            }
        }

        double average = total / sales.length;

        System.out.println();
        System.out.println("---------------- ПІДСУМКИ ----------------");
        System.out.printf("Загальний тижневий виторг: %.2f грн%n", total);
        System.out.printf("Середній виторг на день:    %.2f грн%n", average);
        System.out.printf("Найкращий день:            %s (%.2f грн)%n", days[maxIndex], sales[maxIndex]);
        System.out.printf("Найгірший день:            %s (%.2f грн)%n", days[minIndex], sales[minIndex]);

        System.out.println();
        System.out.println("Дні з виторгом вище середнього:");
        for (int i = 0; i < sales.length; i++) {
            if (sales[i] > average) {
                System.out.printf("- %s: %.2f грн%n", days[i], sales[i]);
            }
        }
        System.out.println("==========================================");

        scanner.close();
    }
}
