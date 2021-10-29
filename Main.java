import java.util.Random;

public class Main {
    static ApplicationProcess process;

    public static void main(String[] args) throws Exception {
        process = new ApplicationProcess();

        while (true) {
            int delay = (new Random().nextInt(10) + 1) * 1000;
            System.out.println("\nЗадержка: " + delay + " мс");

            Thread.sleep(delay);
            process.run();
        }
    }
}
