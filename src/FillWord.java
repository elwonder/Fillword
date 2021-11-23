package src;

public class FillWord {
    char[][] data;

    public FillWord(char[][] data) {
        this.data = data;
    }

    public void print() {
        for (char[] a : data) {
            for (char c : a) {
                System.out.print(c);
                System.out.print(' ');
            }
            System.out.println();
        }
    }
}
