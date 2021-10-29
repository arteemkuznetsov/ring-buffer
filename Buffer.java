/**
 * Класс - реализация кольцевого списка
 */
public class Buffer {
    /**
     * Максимальное количество узлов
     */
    private final int maxSize;
    /**
     * Текущее количество узлов
     */
    private int currentSize;
    /**
     * Количество выполненных итераций (сколько раз наш буфер оказывался заполненным, чтобы в него больше не влезало)
     * Необходимо для подсчета среднего объема буфера
     */
    private double iterationsNum;
    /**
     * Сумма объемов данных, когда-либо присутствовавших в буфере
     * Необходимо для подсчета среднего объема буфера
     */
    private double sizesSum;
    /**
     * Средний объем буфера
     */
    private double avgSize;

    /**
     * Указатель на первый элемент списка
     */
    private BufferNode head;
    /**
     * Указатель на последний элемент списка
     */
    private BufferNode tail;

    /**
     * Инициализирует пустой кольцевой список
     *
     * @param maxSize Максимальное количество узлов
     */
    public Buffer(int maxSize) throws IllegalArgumentException {
        if (maxSize > 0) {
            this.maxSize = maxSize;
            currentSize = 0;
            iterationsNum = 0;
            sizesSum = 0;
            avgSize = 0;
            head = null;
            tail = null;
        } else {
            throw new IllegalArgumentException("Слишком маленький размер буфера");
        }
    }

    /**
     * Добавляет узел в список
     */
    public void addNode(byte value) {
        if (currentSize < maxSize) {
            BufferNode node = new BufferNode(value);
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;
            tail.next = head;
            currentSize++;
            System.out.println(value + " успешно вставлено");
        } else {
            System.out.println("Ошибка вставки - буфер заполнен");
        }
    }

    /**
     * Обходит кольцевой список, выводя значения узлов, после чего удаляет эти узлы
     */
    public void traverseList() {
        BufferNode currentNode = head;
        if (head != null) {
            System.out.println("Содержимое буфера:");
            do {
                System.out.print(currentNode.value + " ");
                // ведем статистику
                sizesSum += getCurrentSize();
                iterationsNum++;
                countAverageSize();

                BufferNode temp = currentNode.next;
                currentNode = null;
                currentNode = temp;
            } while (currentNode != head);
            head = null;
            currentSize = 0;

            System.out.println("\nБуфер очищен");
        } else {
            System.out.println("Буфер пуст");
        }
    }

    private void countAverageSize() {
        avgSize = sizesSum / iterationsNum;
    }

    /**
     * @return Максимальное количество узлов
     * @see Buffer#maxSize
     */
    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * @return Количество узлов в списке
     * @see Buffer#currentSize
     */
    public int getCurrentSize() {
        return currentSize;
    }

    public double getAverageSize() {
        return avgSize;
    }
}
