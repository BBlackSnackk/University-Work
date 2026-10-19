package semester1.practice02.homework;

public class Task2NumberAnalysis {
    public static void main(String[] args) {
        int number = 742;
        int hundreds = number / 100;
        int tens = number / 10 % 10;
        int units = number % 10;
        //System.out.println(hundreds+tens+units);
        //System.out.println(hundreds*tens*units);
        int reverseNumber = units * 100 + tens * 10 + hundreds;
        //System.out.println(reverseNumber);
        int sum = hundreds+tens+units;
        boolean isSumEven = sum % 2 == 0;
        //System.out.println("Сума парна: " + isSumEven);

        System.out.println("Початкове число:  " + number);
        System.out.println("Розряди: " + "Сотні = " + hundreds + ", Десятки = " + tens +", Одиниці = " + units);
        System.out.println("Сума цифр: " + sum);
        System.out.println("Добуток цифр: " + hundreds*tens*units);
        System.out.println("Перевернуте число: " + reverseNumber);
        System.out.println("Чи є сума цифр парною? " + isSumEven);
    }

    }
