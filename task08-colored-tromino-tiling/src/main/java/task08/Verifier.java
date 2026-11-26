package task08;

import java.util.Arrays;
import java.util.List;

public class Verifier {

    public static String verify(int n, int missingRow, int missingCol, List<Tromino> tiles) {
        int size = 1 << n;
        if (tiles.size() != (size * size - 1) / 3) {
            return "wrong number of trominoes: " + tiles.size();
        }
        int[][] owner = new int[size][size];
        for (int[] row : owner) {
            Arrays.fill(row, -1);
        }
        for (int i = 0; i < tiles.size(); i++) {
            Tromino t = tiles.get(i);
            if (t.color < 0 || t.color > 2) {
                return "invalid color on tromino " + i;
            }
            int minR = Integer.MAX_VALUE, maxR = -1, minC = Integer.MAX_VALUE, maxC = -1;
            for (int j = 0; j < 3; j++) {
                int r = t.rows[j];
                int c = t.cols[j];
                if (r < 0 || r >= size || c < 0 || c >= size) {
                    return "tromino " + i + " is outside the board";
                }
                if (r == missingRow && c == missingCol) {
                    return "tromino " + i + " covers the missing square";
                }
                if (owner[r][c] != -1) {
                    return "square (" + r + "," + c + ") is covered twice";
                }
                owner[r][c] = i;
                minR = Math.min(minR, r);
                maxR = Math.max(maxR, r);
                minC = Math.min(minC, c);
                maxC = Math.max(maxC, c);
            }
            if (maxR - minR != 1 || maxC - minC != 1) {
                return "tromino " + i + " is not L-shaped";
            }
        }
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                boolean missing = (r == missingRow && c == missingCol);
                if (!missing && owner[r][c] == -1) {
                    return "square (" + r + "," + c + ") is not covered";
                }
                if (owner[r][c] == -1) {
                    continue;
                }
                if (r + 1 < size && owner[r + 1][c] != -1 && owner[r + 1][c] != owner[r][c]
                        && tiles.get(owner[r + 1][c]).color == tiles.get(owner[r][c]).color) {
                    return "same color at (" + r + "," + c + ") and (" + (r + 1) + "," + c + ")";
                }
                if (c + 1 < size && owner[r][c + 1] != -1 && owner[r][c + 1] != owner[r][c]
                        && tiles.get(owner[r][c + 1]).color == tiles.get(owner[r][c]).color) {
                    return "same color at (" + r + "," + c + ") and (" + r + "," + (c + 1) + ")";
                }
            }
        }
        return null;
    }
}