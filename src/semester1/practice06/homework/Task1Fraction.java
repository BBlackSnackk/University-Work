package semester1.practice06.homework;

class Fraction {
    private int numerator;   // чисельник
    private int denominator; // знаменник

    public void setValues(int num, int den) {
        if (den == 0) {
            System.out.println("Помилка: знаменник не може дорівнювати 0!");
            den = 1;
        }
        if (den < 0) {
            num = -num;
            den = -den;
        }
        numerator = num;
        denominator = den;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    // НСД за алгоритмом Евкліда
    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public void simplify() {
        int d = gcd(numerator, denominator);
        if (d != 0) {
            numerator = numerator / d;
            denominator = denominator / d;
        }
    }

    public double toDecimal() {
        return (double) numerator / denominator;
    }

    public void add(int otherNum, int otherDen) {
        if (otherDen == 0) {
            System.out.println("Помилка: не можна додати дріб зі знаменником 0!");
            return;
        }
        int newNum = numerator * otherDen + otherNum * denominator;
        int newDen = denominator * otherDen;
        setValues(newNum, newDen);
        simplify();
    }

    public void multiply(int otherNum, int otherDen) {
        if (otherDen == 0) {
            System.out.println("Помилка: не можна помножити на дріб зі знаменником 0!");
            return;
        }
        setValues(numerator * otherNum, denominator * otherDen);
        simplify();
    }

    public void printFraction() {
        if (denominator == 1) {
            System.out.print(numerator);
        } else {
            System.out.print(numerator + "/" + denominator);
        }
    }
}

public class Task1Fraction {
    public static void main(String[] args) {
        System.out.println("=== КАЛЬКУЛЯТОР ЗВИЧАЙНИХ ДРОБІВ ===");

        Fraction f1 = new Fraction();
        f1.setValues(8, 12);
        System.out.print("Дріб 1: ");
        f1.printFraction();
        System.out.println();

        f1.simplify();
        System.out.print("Після скорочення: ");
        f1.printFraction();
        System.out.printf(" (десятковий вигляд: %.4f)%n", f1.toDecimal());

        System.out.println();
        System.out.println("Додаємо дріб 3/4 до 2/3...");
        int a = f1.getNumerator();
        int b = f1.getDenominator();
        System.out.println("Формула: " + a + "/" + b + " + 3/4 = (" + a + "*4 + 3*" + b + ") / (" + b + "*4) = "
                + (a * 4 + 3 * b) + "/" + (b * 4));
        f1.add(3, 4);
        System.out.print("Результат додавання: ");
        f1.printFraction();
        System.out.printf(" (десятковий вигляд: %.4f)%n", f1.toDecimal());

        System.out.println();
        System.out.println("Множимо результат на 6/17...");
        f1.multiply(6, 17);
        System.out.print("Результат множення: ");
        f1.printFraction();
        System.out.printf(" (десятковий вигляд: %.4f)%n", f1.toDecimal());

        System.out.println();
        System.out.println("Перевірка помилок:");
        Fraction f2 = new Fraction();
        f2.setValues(5, 0);
        System.out.print("Дріб 5/0 став: ");
        f2.printFraction();
        System.out.println();

        Fraction f3 = new Fraction();
        f3.setValues(3, -9);
        f3.simplify();
        System.out.print("Дріб 3/-9 після скорочення: ");
        f3.printFraction();
        System.out.println();
        System.out.println("=====================================");
    }
}
