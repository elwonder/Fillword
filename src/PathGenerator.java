package src;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PathGenerator {

    private static final Random gen = new Random();
    private static final char NULL = '\u0000';

    private final int range;
    private final Pair[] path;
    private final char[][] fillWord;

    public PathGenerator(String word, char[][] fillWord) {
        this.path = new Pair[word.length()];
        this.fillWord = fillWord;
        this.range = fillWord.length;
    }

    public Pair[] generate(Pair initialPoint) {
        path[0] = initialPoint;
        var point = initialPoint;
        for (int i = 1; i < path.length; i++) {
            point = step(point);
            if (point == null) return null;
            path[i] = point;
        }
        return path;
    }

    public Pair step(Pair p) {
        Pair result;

        var directions = Direction.values();

        shuffle(directions);

        for (Direction dir : directions) {
            result = makeStep(p, dir);
            if (result != null) return result;
        }

        return null;
    }

    private static void shuffle(Direction[] string) {
        for (int i = 0; i < string.length; i++) {
            int toSwap = gen.nextInt(string.length);
            var tmp = string[toSwap];
            string[toSwap] = string[i];
            string[i] = tmp;
        }
    }

    private Pair makeStep(Pair p, Direction direction) {
        Pair result;
        switch (direction) {
            case UP:
                if (p.x != fillWord.length - 1 && fillWord[p.x+1][p.y] == NULL) {
                    result = new Pair(p.x + 1, p.y);
                    if (notInPath(result)) return result;
                }
                break;
            case DOWN:
                if (p.y != fillWord.length - 1 && fillWord[p.x][p.y+1] == NULL) {
                    result = new Pair(p.x, p.y + 1);
                    if (notInPath(result)) return result;
                }
                break;
            case LEFT:
                if (p.x != 0  && fillWord[p.x-1][p.y] == NULL) {
                    result = new Pair(p.x - 1, p.y);
                    if (notInPath(result)) return result;
                }
                break;
            case RIGHT:
                if (p.y != 0 && fillWord[p.x][p.y-1] == NULL) {
                    result =  new Pair(p.x, p.y - 1);
                    if (notInPath(result)) return result;
                }
                break;
            default:
                return null;
        }
        return null;
    }

    private boolean notInPath(Pair p) {
        for (Pair p1 : path) {
            if (p1 != null && p1.x == p.x && p1.y == p.y) return false;
        }
        return true;
    }
}
