package src;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int digits = 0;
        for (String word: args) {
            digits += word.length();
        }
        checkDigits(digits);
        int size = (int) Math.round(Math.sqrt(digits));

        ExecutorService executor = Executors.newCachedThreadPool();

        List<FillWordTask> taskList = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            taskList.add(new FillWordTask(size, args));
        }

        try
        {
            Instant start = Instant.now();

            var result = executor.invokeAny(taskList);

            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);

            System.out.println("Сможла за: "  + timeElapsed.toMillis() + "мс.");

            result.print();

            System.out.println(Arrays.toString(args));

            executor.shutdown();
            System.exit(0);
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    private static void checkDigits(int digits) {
        for (int i = 2; i < 15; i++) {
            if (digits == i * i) {
                System.out.println("Количество букв: " + digits  + ", можно попробовать замутить филворд...");
                break;
            }
            if (digits > i * i && digits < (i + 1) * (i + 1)) {
                System.out.println("Количество букв: " + digits + ", попробуй убавить до " + (i * i) + " или прибавить до " + ((i + 1) * (i + 1)));
                System.exit(0);
            }
        }
    }
}
