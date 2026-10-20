package semester1.practice03.homework;

import javax.swing.*;
import java.util.Scanner;

public class Task1FuelCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("<====КАЛЬКУЛЯТОР ПОЇЗДОК ВІД ТЦК====>");
        System.out.println("Який у вас бюджет?:");
        double budget = scanner.nextDouble();
        System.out.println("Введіть відстань поїздки (км): ");
        double distance = scanner.nextDouble();
        System.out.println("Введіть середню витрату на 100 км (л): ");
        double fuelConsumption = scanner.nextDouble();


        if (distance <= 0 || fuelConsumption <= 0) {
            System.out.println("Помилка: ТИ НЕ МОЖЕШЬ ЇЗДИТИ В МІНУС");
            scanner.close();
            return;
        } else {
            System.out.println("Поїхали");
            double neededFuel = distance * fuelConsumption / 100.0;
            System.out.println("Оберіть тип пального (1 - А-95; 2 - Він Дизель; 3 - ГАЗ): ");
            int fuelType = scanner.nextInt();
            double pricePerLiter;
            switch (fuelType) {
                case 1: //Бенз А-95
                    pricePerLiter = 85.96;
                    break;
                case 2: //Він Дизель
                    pricePerLiter = 89.25;
                    break;
                case 3: //ГАЗ
                    pricePerLiter = 45.21;
                    break;
                default:
                    System.out.println("Шо це таке?");
                    scanner.close();
                    return;
            }
            String fuelName;
            switch (fuelType) {
                case 1:
                    fuelName = ("Бензин А-95");
                    break;
                case 2:
                    fuelName = ("Дизель");
                    break;
                case 3:
                    fuelName = ("Газ");
                    break;
            }
            double baseCost = neededFuel * pricePerLiter;
            double finalCost = (baseCost >= 2000.0) ? baseCost * 0.95 : baseCost;
            double discount = baseCost - finalCost;
            if (finalCost > budget) {
                System.out.println("===============================================");
                System.out.println("Уви, сьогодні в бусик");
                System.out.println("===============================================");
                scanner.close();
                return;
            } else {

            System.out.println("===============================================");
            System.out.println("Ціна за пальне: " + pricePerLiter);
            System.out.println("Обране пальне:" + fuelType);
            System.out.println("Необхідно пального:" + neededFuel);
            System.out.println("Базова вартість:" + baseCost);
            System.out.println("Знижка:" + discount);
            System.out.println("-----------------------------------------------");
            System.out.println("Підсумкова сума до сплати:"+ finalCost);
            System.out.println("===============================================");
            }

            scanner.close();
        }
    }
}