/**
 * Класс - реализация узла кольцевого списка Buffer
 * @see Buffer
 * */
public class BufferNode {
    /** Значение, хранимое в узле */
    byte value;
    /** Указатель на следующий узел */
    BufferNode next;

    public BufferNode(byte value) {
        this.value = value;
    }
}
