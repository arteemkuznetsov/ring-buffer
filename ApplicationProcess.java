import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Random;

/**
 * Класс - имитация прикладного процесса
 */
public class ApplicationProcess {

    PipedStream pipedStream;
    /**
     * Минимальное количество байт при случайной генерации
     */
    int minBytes = 1;
    /**
     * Максимальное количество байт при случайной генерации
     */
    int maxBytes = 5;

    public ApplicationProcess() throws Exception {
        pipedStream = new PipedStream(maxBytes);
    }

    public void run() {
        // максимальная длина порции генерируемых байтов равна размеру буфера
        // больше байтов генерировать нет смысла - буферу будет всегда не хватать места для записи
        byte[] bytes = getRandomBytes();
        try {
            pipedStream.execute(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Заполняет случайным образом массив байтов заданной случайной длины в диапазоне от minBytes</i> до <i>maxBytes</i>
     */
    private byte @NotNull [] getRandomBytes() {
        byte[] bytes = new byte[getRandomInt()];
        new Random().nextBytes(bytes);

        return bytes;
    }

    /**
     * Генерирует случайное целое число, в данном случае - длину массива
     */
    private int getRandomInt() {
        return new Random().nextInt(maxBytes - minBytes + 1) + minBytes;
    }
}
