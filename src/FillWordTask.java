package src;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class FillWordTask implements Callable<FillWord> {

    private final int size;
    private final String[] words;


    public FillWordTask(int size, String[] words) {
        this.size = size;
        this.words = Arrays.copyOf(words, words.length);
    }

    @Override
    public FillWord call() throws Exception {
        FillWord result = null;
        int counter = 0;
        int difficulty = 100;
        do {
            result = FillWordGenerator.generate(size, words, ThreadLocalRandom.current(), difficulty);
            counter++;
            if (counter == 1000) {
                difficulty -= 3;
                counter = 0;
            }
            shuffle(words, ThreadLocalRandom.current());
        } while (result == null);
        return result;
    }

    private void shuffle(String[] string, ThreadLocalRandom gen) {
        for (int i = 0; i < string.length; i++) {
            int toSwap = gen.nextInt(string.length);
            var tmp = string[toSwap];
            string[toSwap] = string[i];
            string[i] = tmp;
        }
    }

}
