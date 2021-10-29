import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedStream {
    /**
     * Количество байт, записанных в PipedOutputStream.
     * Необходимо для чтения из потока PipedInputStream, т.к. поток не хранит эту информацию
     */
    private int threadLength;
    /**
     * Кольцевой список, в котором хранятся данные
     */
    private final Buffer buffer;
    private PipedInputStream pin;
    private PipedOutputStream pout;

    /**
     * @param bufferSize Размер буфера
     */
    public PipedStream(int bufferSize) {
        buffer = new Buffer(bufferSize);
    }

    /**
     * Записывает данные в поток вывода, по умолчанию блокируя в этот момент поток ввода
     *
     * @param bytes Массив байтов, который необходимо поэлементно записать в кольцевой список в виде узлов
     */
    private void threadWrite(byte[] bytes) throws IOException {
        threadLength = bytes.length;
        for (int i = 0; i < threadLength; i++) {
            pout.write(bytes);
        }
        pout.flush(); // гарантируем, что дальше в наш поток вывода ничего не запишется
        pout.close(); // закрываем поток вывода
    }

    /**
     * Читает из потока ввода, имеющего ссылку на поток вывода (в который записаны данные), блокируя поток вывода
     * Полученные данные записываются в буфер путем создания узлов
     */
    private void threadRead() throws IOException {
        for (int i = 0; i < threadLength; i++) {
            buffer.addNode((byte) pin.read());
        }
        threadLength = 0;
        pin.close(); // закрываем поток ввода
    }

    /**
     * Извлекает данные из буфера, если он заполнен, либо записывает данные в буфер,
     * если сгенерированный массив байтов туда "влезет"
     */
    public void execute(byte[] bytes) throws IOException {
        // количество свободных мест в буфере, которые можно заполнить новыми узлами
        int freeSpace = buffer.getMaxSize() - buffer.getCurrentSize();
        System.out.println("\nДоступно: " + freeSpace + " Хотим записать: " + bytes.length);

        System.out.print("[ ");
        for (byte b : bytes) {
            System.out.print(b + " ");
        }
        System.out.print("]");

        if (freeSpace >= bytes.length) {
            // если еще влазит, дозаписываем полученные данные в буфер
            System.out.println("\nСвободное место есть\n");
            pin = new PipedInputStream();
            pout = new PipedOutputStream(pin);
            threadWrite(bytes);
            threadRead();
        } else {
            // иначе извлекаем оттуда данные, выводя значения узлов на экран, а сами узлы удаляем
            System.out.println("\nСтолько не влезет, извлекаем данные");
            buffer.traverseList();

            System.out.println("Средний объем данных в буфере: " + buffer.getAverageSize());
        }
    }
}
