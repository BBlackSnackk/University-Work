package semester1.practice10.homework;

// Здатність масштабуватися (CAN-DO)
interface Scalable2D {
    double MIN_SCALE_FACTOR = 0.1; // неявно public static final

    void scale(double factor);
}

// Здатність повертатися (CAN-DO)
interface Rotatable2D {
    void rotate(double deltaDegrees);

    double getRotationAngle();
}

// Спільна база всіх фігур (IS-A)
abstract class AbstractShape2D {
    private final String name;
    private double centerX;
    private double centerY;

    public AbstractShape2D(String name, double centerX, double centerY) {
        this.name = (name == null || name.isBlank()) ? "Без назви" : name;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public String getName() {
        return name;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    // параметри конкретної фігури для toString
    protected abstract String getParams();

    // final - нащадки не можуть змінити формулу
    public final double getIsoperimetricQuotient() {
        double p = getPerimeter();
        if (p == 0) {
            return 0;
        }
        return 4 * Math.PI * getArea() / (p * p);
    }

    @Override
    public String toString() {
        return String.format("%s['%s', center=(%.1f; %.1f), %s | S=%.2f, P=%.2f, Q=%.4f]",
                getClass().getSimpleName(), name, centerX, centerY, getParams(),
                getArea(), getPerimeter(), getIsoperimetricQuotient());
    }
}

class CircleShape extends AbstractShape2D implements Scalable2D {
    private double radius;

    public CircleShape(String name, double centerX, double centerY, double radius) {
        super(name, centerX, centerY);
        this.radius = (radius > 0) ? radius : 1.0;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void scale(double factor) {
        if (factor < MIN_SCALE_FACTOR) {
            factor = MIN_SCALE_FACTOR;
        }
        radius = radius * factor;
    }

    @Override
    protected String getParams() {
        return String.format("r=%.2f", radius);
    }
}

class RectangleShape extends AbstractShape2D implements Scalable2D, Rotatable2D {
    private double width;
    private double height;
    private double rotationAngle;

    public RectangleShape(String name, double centerX, double centerY, double width, double height) {
        super(name, centerX, centerY);
        this.width = (width > 0) ? width : 1.0;
        this.height = (height > 0) ? height : 1.0;
        this.rotationAngle = 0;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void scale(double factor) {
        if (factor < MIN_SCALE_FACTOR) {
            factor = MIN_SCALE_FACTOR;
        }
        width = width * factor;
        height = height * factor;
    }

    @Override
    public void rotate(double deltaDegrees) {
        rotationAngle = (rotationAngle + deltaDegrees) % 360;
        if (rotationAngle < 0) {
            rotationAngle += 360;
        }
    }

    @Override
    public double getRotationAngle() {
        return rotationAngle;
    }

    @Override
    protected String getParams() {
        return String.format("%.2fx%.2f, angle=%.1f°", width, height, rotationAngle);
    }
}

// final клас: нащадків бути не може, трансформацій не підтримує
final class ImmutableReferenceSquare extends AbstractShape2D {
    private final double side;

    public ImmutableReferenceSquare(String name, double centerX, double centerY, double side) {
        super(name, centerX, centerY);
        this.side = (side > 0) ? side : 1.0;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }

    @Override
    protected String getParams() {
        return String.format("side=%.2f", side);
    }
}

public class Task1PolymorphicShapesAndTransforms {

    public static double calculateTotalArea(AbstractShape2D[] shapes) {
        double total = 0;
        for (AbstractShape2D shape : shapes) {
            total += shape.getArea(); // поліморфний виклик
        }
        return total;
    }

    public static void transformScene(AbstractShape2D[] shapes, double scaleFactor, double rotateDeg) {
        for (AbstractShape2D shape : shapes) {
            boolean transformed = false;

            if (shape instanceof Scalable2D s) {
                s.scale(scaleFactor);
                transformed = true;
                if (shape instanceof CircleShape c) {
                    System.out.printf("   [Scalable2D] '%s' масштабовано в %.1f разів (новий r=%.2f)%n",
                            c.getName(), scaleFactor, c.getRadius());
                } else if (shape instanceof RectangleShape r) {
                    System.out.printf("   [Scalable2D] '%s' масштабовано в %.1f разів (%.2fx%.2f)%n",
                            r.getName(), scaleFactor, r.getWidth(), r.getHeight());
                }
            }

            if (shape instanceof Rotatable2D r) {
                r.rotate(rotateDeg);
                transformed = true;
                System.out.printf("   [Rotatable2D] '%s' повернуто на +%.1f° (поточний кут: %.1f°)%n",
                        shape.getName(), rotateDeg, r.getRotationAngle());
            }

            if (!transformed) {
                System.out.println("   [Info] '" + shape.getName() + "' (final class) пропущено — не підтримує трансформації.");
            }
        }
    }

    private static void printScene(AbstractShape2D[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            System.out.println("  " + (i + 1) + ". " + shapes[i]);
        }
    }

    public static void main(String[] args) {
        System.out.println("================ ПОЛІМОРФНА ГЕОМЕТРІЯ ТА ІНТЕРФЕЙСИ ТРАНСФОРМАЦІЙ ================");

        AbstractShape2D[] shapes = {
                new CircleShape("Коло-Лінза", 0, 0, 5),
                new RectangleShape("Рамка-А", 2, 3, 4, 6),
                new ImmutableReferenceSquare("Еталон-Квадрат", 10, 10, 5)
        };

        System.out.println("[СТАН СЦЕНИ ДО ТРАНСФОРМАЦІЇ]:");
        printScene(shapes);
        System.out.printf("Сумарна площа сцени: %.2f кв.од.%n", calculateTotalArea(shapes));

        System.out.println();
        System.out.println("-> Виконуємо пакетну трансформацію (scale x2.0, rotate +45.0°) через instanceof...");
        transformScene(shapes, 2.0, 45.0);

        System.out.println();
        System.out.println("[СТАН СЦЕНИ ПІСЛЯ ТРАНСФОРМАЦІЇ]:");
        printScene(shapes);
        System.out.printf("Нова сумарна площа сцени: %.2f кв.од.%n", calculateTotalArea(shapes));
        System.out.println("==================================================================================");
    }
}
