package task08;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Task08ColoredTrominoTiling {

    private static final char[] COLOR_NAMES = {'R', 'G', 'B'};

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            runCase(2, 0, 0);
            runCase(3, 5, 2);
            runCase(4, 7, 7);
            runCase(5, 17, 9);
            runCase(8, 100, 200);
        } else {
            for (String path : args) {
                String[] parts = new String(Files.readAllBytes(Paths.get(path))).trim().split("\\s+");
                if (parts.length != 3) {
                    System.out.println(path + ": expected 3 numbers: n missingRow missingCol");
                    continue;
                }
                System.out.println("File: " + path);
                runCase(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            }
        }
    }

    private static void runCase(int n, int missingRow, int missingCol) {
        int size = 1 << n;
        System.out.println("==============================================");
        System.out.println("n = " + n + " (board " + size + "x" + size + "), missing = (" + missingRow + "," + missingCol + ")");
        List<Tromino> tiles;
        try {
            tiles = TrominoSolver.tileTromino(n, missingRow, missingCol);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return;
        }
        System.out.println("Trominoes placed: " + tiles.size() + " (expected " + (size * size - 1) / 3 + ")");
        if (n <= 3) {
            for (int i = 0; i < tiles.size(); i++) {
                Tromino t = tiles.get(i);
                System.out.println("  #" + i + " color " + COLOR_NAMES[t.color] + ": (" + t.rows[0] + "," + t.cols[0] + ") ("
                        + t.rows[1] + "," + t.cols[1] + ") (" + t.rows[2] + "," + t.cols[2] + ")");
            }
        }
        if (n <= 5) {
            printBoard(size, missingRow, missingCol, tiles);
        }
        String error = Verifier.verify(n, missingRow, missingCol, tiles);
        System.out.println(error == null ? "Verification: PASSED" : "Verification: FAILED - " + error);
    }

    private static void printBoard(int size, int missingRow, int missingCol, List<Tromino> tiles) {
        char[][] board = new char[size][size];
        board[missingRow][missingCol] = 'X';
        for (Tromino t : tiles) {
            for (int j = 0; j < 3; j++) {
                board[t.rows[j]][t.cols[j]] = COLOR_NAMES[t.color];
            }
        }
        for (int r = 0; r < size; r++) {
            StringBuilder line = new StringBuilder();
            for (int c = 0; c < size; c++) {
                line.append(board[r][c]).append(' ');
            }
            System.out.println(line.toString().trim());
        }
    }
}