package task08;

public class Validation {

    public static final int MIN_N = 2;
    public static final int MAX_N = 10;

    public static void check(int n, int missingRow, int missingCol) {
        if (n < MIN_N || n > MAX_N) {
            throw new IllegalArgumentException("n must be between " + MIN_N + " and " + MAX_N + ", got " + n);
        }
        int size = 1 << n;
        if (missingRow < 0 || missingRow >= size) {
            throw new IllegalArgumentException("missingRow must be between 0 and " + (size - 1) + ", got " + missingRow);
        }
        if (missingCol < 0 || missingCol >= size) {
            throw new IllegalArgumentException("missingCol must be between 0 and " + (size - 1) + ", got " + missingCol);
        }
    }
}