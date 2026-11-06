package semester1.practice08.homework;

// Звичайний банківський рахунок
class BankAccount {
    private String iban;
    private String ownerName;
    protected double balance; // protected - щоб нащадки могли змінювати баланс

    public BankAccount(String iban, String ownerName, double initialBalance) {
        this.iban = iban;
        this.ownerName = ownerName;
        if (initialBalance < 0) {
            this.balance = 0.0;
        } else {
            this.balance = initialBalance;
        }
    }

    public String getIban() {
        return iban;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        System.out.println("Відмова: недостатньо коштів або некоректна сума!");
        return false;
    }

    @Override
    public String toString() {
        return String.format("Рахунок[%s | Власник: %s | Баланс: %.2f грн]", iban, ownerName, balance);
    }
}

// Ощадний рахунок: є відсотки і незнижуваний залишок
class SavingsAccount extends BankAccount {
    private double interestRate;
    private double minBalance;

    public SavingsAccount(String iban, String ownerName, double initialBalance, double interestRate, double minBalance) {
        super(iban, ownerName, initialBalance);
        this.interestRate = Math.max(0, interestRate);
        this.minBalance = Math.max(0, minBalance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount >= minBalance) {
            return super.withdraw(amount);
        }
        System.out.printf("ВІДМОВА (порушення незнижуваного залишку %.2f грн)%n", minBalance);
        return false;
    }

    public void applyInterest() {
        double interest = balance * (interestRate / 100.0);
        deposit(interest);
    }

    @Override
    public String toString() {
        return String.format("SavingsAccount{%s, Ставка: %.1f%%, Мін.залишок: %.2f грн}",
                super.toString(), interestRate, minBalance);
    }
}

// Кредитний рахунок: можна піти в мінус до ліміту, з комісією
class CreditAccount extends BankAccount {
    private double creditLimit;
    private double feePercent;

    public CreditAccount(String iban, String ownerName, double initialBalance, double creditLimit, double feePercent) {
        super(iban, ownerName, initialBalance);
        this.creditLimit = Math.max(0, creditLimit);
        this.feePercent = Math.max(0, feePercent);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Відмова: некоректна сума!");
            return false;
        }
        if (balance >= amount) {
            return super.withdraw(amount); // своїх грошей вистачає - без комісії
        }
        double borrowed = amount - Math.max(0.0, balance);
        double fee = borrowed * (feePercent / 100.0);
        double newBalance = balance - amount - fee;

        if (newBalance < -creditLimit) {
            System.out.println("ВІДМОВА: перевищено кредитний ліміт!");
            return false;
        }
        balance = newBalance;
        return true;
    }

    public double getCurrentDebt() {
        return balance < 0 ? Math.abs(balance) : 0.0;
    }

    @Override
    public String toString() {
        return String.format("CreditAccount{%s, Ліміт: %.2f грн, Борг: %.2f грн}",
                super.toString(), creditLimit, getCurrentDebt());
    }
}

public class Task2BankingHierarchy {
    public static void main(String[] args) {
        System.out.println("================ БАНКІВСЬКА ІЄРАРХІЯ РАХУНКІВ ================");

        System.out.println("0. Звичайний рахунок:");
        BankAccount simple = new BankAccount("UA000", "Іван Петренко", 1500);
        System.out.println(simple);
        simple.withdraw(2000);
        simple.withdraw(500);
        System.out.println(simple);
        System.out.println();

        System.out.println("1. Ощадний рахунок (початковий стан):");
        SavingsAccount savings = new SavingsAccount("UA101", "Олена Коваль", 5000, 12.0, 1000);
        System.out.println(savings);
        System.out.print("-> Спроба зняти 4500 грн: ");
        savings.withdraw(4500);
        System.out.println("-> Нарахування річних відсотків (+12%)...");
        savings.applyInterest();
        System.out.println(savings);
        System.out.println();

        System.out.println("2. Кредитний рахунок (початковий стан):");
        CreditAccount credit = new CreditAccount("UA202", "Максим Бондар", 2000, 10000, 2.0);
        System.out.println(credit);
        System.out.print("-> Покупка на 5000 грн (3000 грн у кредит з комісією 2% = 60 грн)... ");
        if (credit.withdraw(5000)) {
            System.out.println("УСПІШНО!");
        }
        System.out.println(credit);
        System.out.print("-> Покупка на 8000 грн... ");
        credit.withdraw(8000);
        System.out.println(credit);
        System.out.println("==============================================================");
    }
}
