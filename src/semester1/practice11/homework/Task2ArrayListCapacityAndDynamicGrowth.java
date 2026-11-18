package semester1.practice11.homework;

import java.util.ArrayList;

// Свій емулятор ArrayList, щоб побачити, як він росте всередині
class CustomArrayCapacityEmulator<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size = 0;
    private int resizeCount = 0;
    private int totalElementsCopied = 0;

    public CustomArrayCapacityEmulator() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    // якщо масив заповнений - збільшуємо в 1.5 раза
    private void ensureCapacityInternal() {
        if (size == elementData.length) {
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            Object[] newBuffer = new Object[newCapacity];
            System.arraycopy(elementData, 0, newBuffer, 0, size);
            elementData = newBuffer;
            resizeCount++;
            totalElementsCopied += size;
        }
    }

    public void add(E element) {
        ensureCapacityInternal();
        elementData[size++] = element;
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacityInternal();
        // зсуваємо все праворуч від index на 1
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null; // щоб GC міг прибрати об'єкт
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return elementData.length;
    }

    public int getResizeCount() {
        return resizeCount;
    }

    public int getTotalElementsCopied() {
        return totalElementsCopied;
    }

    // стискаємо масив рівно до size
    public void trimToSize() {
        if (size < elementData.length) {
            Object[] trimmed = new Object[size];
            System.arraycopy(elementData, 0, trimmed, 0, size);
            elementData = trimmed;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}

public class Task2ArrayListCapacityAndDynamicGrowth {
    public static void main(String[] args) {
        System.out.println("================ ЕМУЛЯТОР ВНУТРІШНЬОЇ БУДОВИ ARRAYLIST (ПІД КАПОТОМ) ================");

        // var + явний тип у new - тип зберігається
        var emulator = new CustomArrayCapacityEmulator<String>();
        // а от так НЕ можна: var raw = new ArrayList<>(); -> це вже ArrayList<Object>, тип втрачено
        ArrayList<String> normalList = new ArrayList<>(); // ромб <> бере тип зліва
        normalList.add("ok");

        System.out.println("[ІНІЦІАЛІЗАЦІЯ]: Створено CustomArrayCapacityEmulator<String> | size = "
                + emulator.size() + " | capacity = " + emulator.getCapacity());

        System.out.println();
        System.out.println("--- Додавання 35 елементів у список ---");
        for (int i = 1; i <= 35; i++) {
            int oldCapacity = emulator.getCapacity();
            emulator.add("Block-" + i);
            int newCapacity = emulator.getCapacity();
            if (newCapacity != oldCapacity) {
                System.out.println("  ⚡ [GROW #" + emulator.getResizeCount() + "] Переповнення при додаванні "
                        + i + "-го елемента! Ємність: " + oldCapacity + " -> " + newCapacity
                        + " (System.arraycopy скопіював " + oldCapacity + " ел.)");
            }
        }
        int free = emulator.getCapacity() - emulator.size();
        System.out.println("Стан після додавання 35 елементів: size = " + emulator.size()
                + " | capacity = " + emulator.getCapacity() + " (Вільний резерв: " + free + " комірок null)");

        System.out.println();
        System.out.println("--- Тестування вставки add(0, 'HEADER') та видалення remove(5) ---");
        int shiftedRight = emulator.size();
        emulator.add(0, "HEADER");
        System.out.println("  -> Вставлено 'HEADER' на індекс 0 (зсунуто праворуч " + shiftedRight
                + " елементів через System.arraycopy)");

        int shiftedLeft = emulator.size() - 5 - 1;
        String removed = emulator.remove(5);
        System.out.println("  -> Видалено елемент '" + removed + "' з індексу 5 (зсунуто ліворуч "
                + shiftedLeft + " елементів, хвіст занулено для GC)");
        System.out.println("  Поточний стан: size = " + emulator.size() + " | capacity = " + emulator.getCapacity()
                + " | Перший: '" + emulator.get(0) + "' | Останній: '" + emulator.get(emulator.size() - 1) + "'");

        System.out.println();
        System.out.println("--- Оптимізація пам'яті trimToSize() ---");
        int capacityBefore = emulator.getCapacity();
        emulator.trimToSize();
        System.out.println("  -> Викликано trimToSize(): буфер Object[" + capacityBefore
                + "] стиснуто рівно до Object[" + emulator.getCapacity() + "]!");
        System.out.println("  Фінальна телеметрія: size = " + emulator.size() + " | capacity = "
                + emulator.getCapacity() + " | Кількість розширень: " + emulator.getResizeCount());
        System.out.println("  Всього скопійовано при розширеннях: " + emulator.getTotalElementsCopied() + " ел.");
        System.out.println("=====================================================================================");
    }
}
