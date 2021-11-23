package src;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;

public class FillWordTask implements Callable<FillWord> {

    private final int size;
    private final String[] words;
    private final Random gen = new Random();


    public FillWordTask(int size, String[] words) {
        this.size = size;
        this.words = Arrays.copyOf(words, words.length);
    }

    @Override
    public FillWord call() throws Exception {
        FillWord result = null;
        do {
            result = FillWordGenerator.generate(size, words);
            shuffle(words);
        } while (result == null);
        return result;
    }

    private void shuffle(String[] string) {
        for (int i = 0; i < string.length; i++) {
            int toSwap = gen.nextInt(string.length);
            var tmp = string[toSwap];
            string[toSwap] = string[i];
            string[i] = tmp;
        }
    }

}
