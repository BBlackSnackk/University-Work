package semester1.practice09.homework;

// Точка на площині
class Point2D {
    private double x;
    private double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // конструктор копіювання (для захисного копіювання)
    public Point2D(Point2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public double distanceTo(Point2D other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", x, y);
    }
}

// Абстрактна фігура, яка МАЄ точку центра (композиція)
abstract class AbstractShape2D {
    private final String name;
    private final Point2D anchorPoint;

    public AbstractShape2D(String name, Point2D anchorPoint) {
        this.name = (name == null || name.isBlank()) ? "Без назви" : name;
        this.anchorPoint = (anchorPoint != null) ? new Point2D(anchorPoint) : new Point2D(0, 0);
    }

    public String getName() {
        return name;
    }

    public Point2D getAnchorPoint() {
        return new Point2D(anchorPoint); // віддаємо копію
    }

    public void moveBy(double dx, double dy) {
        anchorPoint.translate(dx, dy); // делегування
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    // параметри конкретної фігури (r, a x b ...)
    protected abstract String getParams();

    @Override
    public String toString() {
        return String.format("%s[name='%s', center=%s, %s | S=%.2f, P=%.2f]",
                getClass().getSimpleName(), name, anchorPoint, getParams(), getArea(), getPerimeter());
    }
}

class Circle2D extends AbstractShape2D {
    private double radius;

    public Circle2D(String name, Point2D center, double radius) {
        super(name, center);
        this.radius = (radius > 0) ? radius : 1.0;
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
    protected String getParams() {
        return String.format("r=%.2f", radius);
    }
}

class Rectangle2D extends AbstractShape2D {
    private double width;
    private double height;

    public Rectangle2D(String name, Point2D center, double width, double height) {
        super(name, center);
        this.width = (width > 0) ? width : 1.0;
        this.height = (height > 0) ? height : 1.0;
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
    protected String getParams() {
        return String.format("%.2fx%.2f", width, height);
    }
}

class RightTriangle2D extends AbstractShape2D {
    private double legA;
    private double legB;

    public RightTriangle2D(String name, Point2D center, double legA, double legB) {
        super(name, center);
        this.legA = (legA > 0) ? legA : 1.0;
        this.legB = (legB > 0) ? legB : 1.0;
    }

    public double getHypotenuse() {
        return Math.sqrt(legA * legA + legB * legB);
    }

    @Override
    public double getArea() {
        return 0.5 * legA * legB;
    }

    @Override
    public double getPerimeter() {
        return legA + legB + getHypotenuse();
    }

    @Override
    protected String getParams() {
        return String.format("a=%.2f, b=%.2f, c=%.2f", legA, legB, getHypotenuse());
    }
}

// Полотно, яке містить масив фігур
class GeometryCanvas {
    private final String title;
    private final AbstractShape2D[] shapes;
    private int count;

    public GeometryCanvas(String title, int capacity) {
        this.title = title;
        this.shapes = new AbstractShape2D[(capacity > 0) ? capacity : 5];
        this.count = 0;
    }

    public boolean addShape(AbstractShape2D shape) {
        if (shape == null) {
            System.out.println("Помилка: фігура не може бути null!");
            return false;
        }
        if (count >= shapes.length) {
            System.out.println("Помилка: полотно переповнене!");
            return false;
        }
        shapes[count] = shape;
        count++;
        return true;
    }

    public double getTotalArea() {
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += shapes[i].getArea();
        }
        return sum;
    }

    public double getTotalPerimeter() {
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += shapes[i].getPerimeter();
        }
        return sum;
    }

    public AbstractShape2D findLargestShape() {
        if (count == 0) {
            return null;
        }
        AbstractShape2D largest = shapes[0];
        for (int i = 1; i < count; i++) {
            if (shapes[i].getArea() > largest.getArea()) {
                largest = shapes[i];
            }
        }
        return largest;
    }

    public void moveAllShapes(double dx, double dy) {
        for (int i = 0; i < count; i++) {
            shapes[i].moveBy(dx, dy);
        }
    }

    @Override
    public String toString() {
        String result = "Полотно: '" + title + "' (Фігур: " + count + "/" + shapes.length + ")\n";
        for (int i = 0; i < count; i++) {
            result += "  " + (i + 1) + ". " + shapes[i] + "\n";
        }
        return result;
    }
}

public class Task1CompositeShapesCanvas {
    public static void main(String[] args) {
        System.out.println("================ ГЕОМЕТРИЧНЕ ПОЛОТНО (КОМПОЗИЦІЯ + ABSTRACT) ================");

        Point2D center = new Point2D(0, 0);
        Circle2D circle = new Circle2D("Коло-А", center, 5);
        Rectangle2D rect = new Rectangle2D("Плита-Б", new Point2D(10, 5), 4, 6);
        RightTriangle2D triangle = new RightTriangle2D("Клин-В", new Point2D(-3, 2), 3, 4);

        // перевірка захисного копіювання
        center.translate(100, 100);
        Point2D copy = circle.getAnchorPoint();
        copy.translate(50, 50);

        GeometryCanvas canvas = new GeometryCanvas("Інженерне креслення №1", 5);
        canvas.addShape(circle);
        canvas.addShape(rect);
        canvas.addShape(triangle);
        canvas.addShape(null);

        System.out.print(canvas);
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("Сумарна площа креслення:    %.2f кв.од.%n", canvas.getTotalArea());
        System.out.printf("Сумарний периметр:          %.2f од.%n", canvas.getTotalPerimeter());
        AbstractShape2D largest = canvas.findLargestShape();
        System.out.printf("Найбільша фігура:           %s (S = %.2f)%n", largest.getName(), largest.getArea());

        System.out.println("Перевірка захисту: зовнішня точка змінилась на " + center
                + ", а центр кола досі " + circle.getAnchorPoint());

        System.out.println("-> Переміщуємо всі фігури полотна на вектор (+5.0; -2.0)...");
        canvas.moveAllShapes(5.0, -2.0);
        System.out.println("Новий центр '" + circle.getName() + "': " + circle.getAnchorPoint());
        System.out.println("=============================================================================");
    }
}
