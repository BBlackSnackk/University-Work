package semester1.practice08.homework;

// Базовий клас - коло
class Circle {
    private double radius;

    public Circle(double radius) {
        setRadius(radius);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius <= 0) {
            System.out.println("Попередження: радіус має бути > 0, встановлено 1.0");
            this.radius = 1.0;
        } else {
            this.radius = radius;
        }
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getCircumference() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("Circle[r=%.2f, S=%.2f, C=%.2f]", radius, getArea(), getCircumference());
    }
}

// Циліндр Є колом + висота
class Cylinder extends Circle {
    private double height;

    public Cylinder(double radius, double height) {
        super(radius);
        if (height <= 0) {
            System.out.println("Попередження: висота має бути > 0, встановлено 1.0");
            this.height = 1.0;
        } else {
            this.height = height;
        }
    }

    public double getHeight() {
        return height;
    }

    public double getVolume() {
        return super.getArea() * height;
    }

    public double getTotalSurfaceArea() {
        return 2 * super.getArea() + super.getCircumference() * height;
    }

    @Override
    public String toString() {
        return String.format("Cylinder[base=%s, h=%.2f, V=%.2f, TotalArea=%.2f]",
                super.toString(), height, getVolume(), getTotalSurfaceArea());
    }
}

// Конус Є колом + висота
class Cone extends Circle {
    private double height;

    public Cone(double radius, double height) {
        super(radius);
        if (height <= 0) {
            System.out.println("Попередження: висота має бути > 0, встановлено 1.0");
            this.height = 1.0;
        } else {
            this.height = height;
        }
    }

    public double getHeight() {
        return height;
    }

    public double getSlantHeight() {
        return Math.sqrt(getRadius() * getRadius() + height * height);
    }

    public double getVolume() {
        return super.getArea() * height / 3.0;
    }

    public double getTotalSurfaceArea() {
        return super.getArea() + Math.PI * getRadius() * getSlantHeight();
    }

    @Override
    public String toString() {
        return String.format("Cone[base=%s, h=%.2f, l=%.2f, V=%.2f, TotalArea=%.2f]",
                super.toString(), height, getSlantHeight(), getVolume(), getTotalSurfaceArea());
    }
}

public class Task1GeometryInheritance {
    public static void main(String[] args) {
        System.out.println("================ ГЕОМЕТРІЯ ТІЛ ОБЕРТАННЯ ================");

        Circle circle = new Circle(5.0);
        Cylinder cylinder = new Cylinder(5.0, 10.0);
        Cone cone = new Cone(3.0, 4.0);

        System.out.println("Базове коло:");
        System.out.println(circle);
        System.out.println();

        System.out.println("Циліндр (успадковано від Circle):");
        System.out.println(cylinder);
        System.out.println();

        System.out.println("Конус (успадковано від Circle):");
        System.out.println(cone);
        System.out.println();

        System.out.println("Перевірка валідації:");
        Circle bad = new Circle(-2);
        System.out.println(bad);
        System.out.println("=========================================================");
    }
}
