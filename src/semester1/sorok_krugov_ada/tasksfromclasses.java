package semester1.sorok_krugov_ada;

public class tasksfromclasses  {
    static void main() {

        int age = 18;
        double height = 1.85;
        char firstNameLetter = 'V';
        boolean isStudent = true;
        System.out.println("Вік: " +age);
        System.out.println("Ріст: " +height);
        System.out.println("Перша літера імені: " +firstNameLetter);
        System.out.println("Чи є студентом: " +isStudent);

        byte temp = 14;
        short year = 2026;
        int cityPopulation = 2980000;
        long worldPopulation = 8310000000L;
        System.out.println("Темперарута: " +temp);
        System.out.println("Рік: " +year);
        System.out.println("Популяція Київа: " +cityPopulation);
        System.out.println("популяция Світу: " +worldPopulation);

        char symbol1 = 'K';
        char symbol2 = 75;
        char symbol3 = 0x4B;
        System.out.println(symbol1);
        System.out.println(symbol2);
        System.out.println(symbol3);

        boolean isJavaFun = true;
        boolean isFishFly = false;
        boolean isSkyBlue = true;
        System.out.println(isJavaFun);
        System.out.println(isFishFly);
        System.out.println(isSkyBlue);

        final double GRAVITY = 9.81;
        final int DAYS_IN_WEEK = 7;
        final String UNIVERSITY_NAME = "ДУІКТ";
        System.out.println(GRAVITY);
        System.out.println(DAYS_IN_WEEK);
        System.out.println(UNIVERSITY_NAME);

        int intVal = 250;
        double doubleVal = intVal;
        System.out.println("int: " + intVal);
        System.out.println("double: " + doubleVal);

        double priceWithPennies = 49.99;
        int roundedPrice = (int) priceWithPennies;
        System.out.println(roundedPrice);

        int firstNumber = 5;
        double userWeight = 72.5;
        String userCity = "Kyiv";
        boolean isValidUser = true;
        System.out.println(firstNumber + ", " + userWeight + ", " + userCity + ", " + isValidUser);

        String firstName = "Тарас";
        String lastName = "Шевченко";
        int yearsOld = 47;
        System.out.println("Мене звати " + firstName + " " + lastName + ", мені " + age + " років.");

        int a = 10;
        int b = 20;
        System.out.println("Сума без дужок: " + a + b);
        System.out.println("Сума з дужками: " + (a + b));

        String login = "ivan.petrenko";
        String domain = "gmail.com";
        String role = "student";
        String email = login + "@" + domain;
        System.out.println("Користувач " + login + " (роль: " + role + ") має пошту: " + email);

        String productName = "Ноутбук";
        int quantity = 2;
        double pricePerUnit = 24500.50;
        System.out.println("ЧЕК:\nТовар: " + productName + "\nКількість: " + quantity
                + " шт.\nЦіна: " + pricePerUnit + " грн");

        String currency = "UAH";
        int hryvnias = 1500;
        int kopecks = 75;
        System.out.println("Поточний баланс: " + hryvnias + "." + kopecks + " " + currency);

        String protocol = "https://";
        String host = "api.example.com";
        String endpoint = "/users";
        int userId = 42;
        System.out.println("Запит відправлено на: " + protocol + host + endpoint + "/" + userId);

        String language = "Java";
        int level = 1;
        boolean isReady = true;
        System.out.println("Мова: " + language + " | Рівень: " + level + " | Готовність до практики: " + isReady);

        int number = 19;
        int remainder = number % 2;
        System.out.println("Число 19 при діленні на 2 дає остачу: " + remainder);

        int totalSeconds = 385;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        System.out.println(totalSeconds + " секунд = " + minutes + " хв " + seconds + " сек");

        int n = 47;
        int tens = n / 10;
        int units = n % 10;
        int digitSum = tens + units;
        System.out.println("Число: " + n + ". Десятки: " + tens + ", Одиниці: " + units
                + ". Сума цифр: " + digitSum);

        int score = 100;
        score += 50;
        System.out.println("Після додавання: " + score);
        score -= 20;
        System.out.println("Після віднімання: " + score);
        score *= 2;
        System.out.println("Після множення: " + score);
        score /= 4;
        System.out.println("Після ділення: " + score);

        int counter = 5;
        System.out.println(counter++);
        System.out.println(counter);

        int counters = 5;
        System.out.println(++counters);

        int grade1 = 4;
        int grade2 = 5;
        int grade3 = 4;
        double average = (grade1 + grade2 + grade3) / 3.0;
        System.out.println("Середній бал: " + average);

        double celsius = 25.0;
        double fahrenheit = celsius * 1.8 + 32;
        double kelvin = celsius + 273.15;
        System.out.println(celsius + "°C = " + fahrenheit + "°F");
        System.out.println(celsius + "°C = " + kelvin + " K");

        int userAge = 17;
        boolean isAdult = userAge >= 18;
        System.out.println("Вік: " + userAge + ". Чи є повнолітнім: " + isAdult);

        int x = 15;
        int y = 20;
        System.out.println("p == r: " + (x == y));
        System.out.println("x != y: " + (x != y));
        System.out.println("x > y: " + (x > y));

        int speed = 75;
        boolean isSafeSpeed = (speed >= 20) && (speed <= 80);
        System.out.println("Швидкість " + speed + " у безпечному діапазоні: " + isSafeSpeed);

        int personAge = 70;
        boolean hasFreePass = (personAge < 7) || (personAge >= 65);
        System.out.println("Вік: " + personAge + ". Право на безкоштовний проїзд: " + hasFreePass);

        boolean isDoorLocked = true;
        boolean canEnter = !isDoorLocked;
        System.out.println("Двері зачинені: " + isDoorLocked);
        System.out.println("Чи можна увійти: " + canEnter);

        int number1 = 30;
        boolean isDivisibleByBoth1 = (number1 % 3 == 0) && (number1 % 5 == 0);
        System.out.println("Число " + number1 + " ділиться на 3 і на 5: " + isDivisibleByBoth1);

        int number2 = 25;
        boolean isDivisibleByBoth2 = (number2 % 3 == 0) && (number2 % 5 == 0);
        System.out.println("Число " + number2 + " ділиться на 3 і на 5: " + isDivisibleByBoth2);

        boolean hasPassword = true;
        boolean hasSmsCode = true;
        boolean isAccountBlocked = false;
        boolean canLogin = hasPassword && hasSmsCode && !isAccountBlocked;
        System.out.println("Доступ дозволено: " + canLogin);

        int yearr = 2024;
        boolean isLeapYear = (yearr % 4 == 0 && yearr % 100 != 0) || (yearr % 400 == 0);
        System.out.println("Рік " + yearr + " високосний: " + isLeapYear);

        double legA = 3.0;
        double legB = 4.0;
        double hypotenuse = Math.sqrt(Math.pow(legA, 2) + Math.pow(legB, 2));
        System.out.println("Катети: " + legA + " та " + legB + ". Гіпотенуза: " + hypotenuse);

        double radius = 5.0;
        double circumference = 2 * Math.PI * radius;
        double area = Math.PI * Math.pow(radius, 2);
        System.out.println("Радіус: " + radius);
        System.out.println("Довжина кола: " + circumference);
        System.out.println("Площа круга: " + area);

        double dayTemp = 18.5;
        double nightTemp = -3.2;
        System.out.println("Перепад температури: " + Math.abs(dayTemp - nightTemp));
        System.out.println("Максимальна температура: " + Math.max(dayTemp, nightTemp));
        System.out.println("Мінімальна температура: " + Math.min(dayTemp, nightTemp));

        double num = 5.67;
        System.out.println(Math.round(num));
        System.out.println(Math.ceil(num));
        System.out.println(Math.floor(num));

        double negNum = -5.67;
        System.out.println(Math.round(negNum));
        System.out.println(Math.ceil(negNum));
        System.out.println(Math.floor(negNum));double coefA = 1.0;
        double coefB = -7.0;
        double coefC = 10.0;
        double discriminant = Math.pow(coefB, 2) - 4 * coefA * coefC;
        double x1 = (-coefB + Math.sqrt(discriminant)) / (2 * coefA);
        double x2 = (-coefB - Math.sqrt(discriminant)) / (2 * coefA);
        System.out.println("Дискримінант D = " + discriminant);
        System.out.println("Корінь x1 = " + x1);
        System.out.println("Корінь x2 = " + x2);

        int dice = (int) (Math.random() * 6) + 1;
        System.out.println("Випало на кубику: " + dice);

        int min = -10;
        int max = 35;
        int randomTemp = (int) (Math.random() * (max - min + 1)) + min;
        System.out.println("Погода на завтра: " + randomTemp + "°C");

        double principal = 10000;
        int years = 3;
        double rate = 12;
        double finalAmount = principal * Math.pow(1 + rate / 100, years);
        double profit = finalAmount - principal;

        System.out.println("+------------------------------------+");
        System.out.println("|     ДЕПОЗИТНИЙ КАЛЬКУЛЬАТОР        |");
        System.out.println("+------------------------------------+");
        System.out.println("Початкова сума: " + principal + " грн");
        System.out.println("Термін: " + years + " роки під " + rate + "% річних");
        System.out.printf("Підсумкова сума: %.2f грн%n",finalAmount);
        System.out.printf("Чистий прибуток: %.2f грн%n", profit);
        System.out.println("+------------------------------------+");

    }
}
