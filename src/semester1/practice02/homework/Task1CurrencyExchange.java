package semester1.practice02.homework;

public class Task1CurrencyExchange {
    public static void main(String[] args) {

        double usdAmount = 500.0;
        final double EXCHANGE_RATE = 45.04;
        final double COMMISSION_PERCENT = 1.5; // 1.5%
        double totalUAH = usdAmount*EXCHANGE_RATE;
        double commission = totalUAH * COMMISSION_PERCENT / 100;
        double amountPayable = totalUAH - commission;
        int roundedAmount = (int)Math.round(amountPayable);

        System.out.println("Сума до обміну: " + usdAmount + " USD");
        System.out.println("Курс валют:" + EXCHANGE_RATE + "UAH/USD");
        System.out.println("Нараховано до комісії:" + totalUAH);
        System.out.println("Комісія банку:" + commission + " UAH");
        System.out.println("Чиста сума до видачі:" + amountPayable + " UAH");
        System.out.println("До видачі готівкою:" + roundedAmount + " UAH");


    }
}

