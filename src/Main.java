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
        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].toUpperCase();
            digits += args[i].length();
        }
        checkDigits(digits);
        int size = (int) Math.round(Math.sqrt(digits));

        ExecutorService executor = Executors.newCachedThreadPool();

        List<FillWordTask> taskList = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            taskList.add(new FillWordTask(size, args));
        }

        try
        {
            Instant start = Instant.now();

            var result = executor.invokeAny(taskList);

            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);

            System.out.println("Finished in: "  + timeElapsed.toMillis() + "ms.");

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

        for (int i = 2; i <= 15; i++) {
            int nSquare = i * i;
            int nPlusOneSquare = (i+1) * (i+1);
            if (digits == nSquare) {
                System.out.println("Amount of digits: " + digits  + ", let's try...");
                return;
            }
            if (digits > nSquare && digits < nPlusOneSquare) {
                throw new IllegalArgumentException("Amount of digits: " + digits +
                        ", decrease to " + nSquare + " or increase to " + nPlusOneSquare);
            }
        }
    }
}
