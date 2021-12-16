package src;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class FillWordGenerator {

    private static final char NULL = '\u0000';

    private FillWordGenerator() {}

    public static FillWord generate(int size, String[] words, ThreadLocalRandom gen) {
        var result = new char[size][size];
        for (String word : words) {
            var freePoints = getFreePoints(result, size);
            while (true) {
                var index = gen.nextInt(freePoints.size());
                Pair[] path = PathGenerator.generate(word, result, freePoints.get(index),  gen);
                freePoints.remove(index);
                if (path != null) {
                    placeWord(path, result, word);
                    break;
                }
                if (freePoints.isEmpty()) return null;
            }
        }
        return new FillWord(result);
    }

    private static ArrayList<Pair> getFreePoints(char[][] fillWord, int range) {
        var result = new ArrayList<Pair>();
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                if (fillWord[i][j] == NULL) result.add(new Pair(i, j));
            }
        }
        return result;
    }

    private static void placeWord(Pair[] path, char[][] result, String word) {
        for (int i = 0; i < word.length(); i++) {
            result[path[i].x][path[i].y] = word.charAt(i);
        }
    }
}
