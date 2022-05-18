package src;

import java.util.concurrent.ThreadLocalRandom;

public class PathGenerator {

    private static final char NULL = '\u0000';

    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;

    private PathGenerator() {}

    public static Pair[] generate(String word, char[][] fillWord, Pair start, ThreadLocalRandom gen) {
        Pair point = start;
        var wordLength = word.length();
        var path = new Pair[wordLength];
        path[0] = point;
        int[] directions =  {UP, DOWN, LEFT, RIGHT};
        for (int i = 1; i < wordLength; i++) {
            point = step(point, path, fillWord, directions);
            if (point == null) return null;
            shuffle(directions, gen);
            path[i] = point;
        }
        return path;
    }

    public static Pair step(Pair p, Pair[] path, char[][] fillWord, int[] directions) {
        Pair point;
        for (int i = 0; i < 4; i++) {
            point = makeStep(p, directions[i], fillWord);
            if (point != null && notInPath(point, path)) return point;
        }
        return null;
    }

    private static void shuffle(int[] arr, ThreadLocalRandom gen) {
        for (int i = 0; i < arr.length; i++) {
            int toSwap = gen.nextInt(arr.length);
            var tmp = arr[toSwap];
            arr[toSwap] = arr[i];
            arr[i] = tmp;
        }
    }

    private static Pair makeStep(Pair p, int direction, char[][] fillWord) {
        switch (direction) {
            case UP:
                if (p.x != fillWord.length - 1 && fillWord[p.x+1][p.y] == NULL) {
                    return new Pair(p.x + 1, p.y);
                }
                break;
            case DOWN:
                if (p.y != fillWord.length - 1 && fillWord[p.x][p.y+1] == NULL) {
                    return new Pair(p.x, p.y + 1);
                }
                break;
            case LEFT:
                if (p.x != 0  && fillWord[p.x-1][p.y] == NULL) {
                    return new Pair(p.x - 1, p.y);
                }
                break;
            case RIGHT:
                if (p.y != 0 && fillWord[p.x][p.y-1] == NULL) {
                    return new Pair(p.x, p.y - 1);
                }
                break;
            default:
                return null;
        }
        return null;
    }

    private static boolean notInPath(Pair p, Pair[] path) {
        for (Pair p1 : path) {
            if (p1 != null && p1.x == p.x && p1.y == p.y) return false;
        }
        return true;
    }
}
