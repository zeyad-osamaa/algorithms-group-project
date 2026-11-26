package task08;

import java.util.ArrayList;
import java.util.List;

public class TrominoSolver {

    private static final int[][] PERMS = {
        {0, 1, 2}, {0, 2, 1}, {1, 0, 2}, {1, 2, 0}, {2, 0, 1}, {2, 1, 0}
    };

    public static List<Tromino> tileTromino(int n, int missingRow, int missingCol) {
        Validation.check(n, missingRow, missingCol);
        int size = 1 << n;
        return solve(size, 0, 0, missingRow, missingCol);
    }

    private static List<Tromino> solve(int size, int top, int left, int mr, int mc) {
        List<Tromino> result = new ArrayList<>();
        if (size == 2) {
            int[] rs = new int[3];
            int[] cs = new int[3];
            int k = 0;
            for (int r = top; r < top + 2; r++) {
                for (int c = left; c < left + 2; c++) {
                    if (r != mr || c != mc) {
                        rs[k] = r;
                        cs[k] = c;
                        k++;
                    }
                }
            }
            result.add(new Tromino(rs, cs, 0));
            return result;
        }
        int half = size / 2;
        int midR = top + half;
        int midC = left + half;
        int[] qTop = {top, top, midR, midR};
        int[] qLeft = {left, midC, left, midC};
        int[] qMr = new int[4];
        int[] qMc = new int[4];
        int missingQuad = (mr >= midR ? 2 : 0) + (mc >= midC ? 1 : 0);
        int[] rs = new int[3];
        int[] cs = new int[3];
        int k = 0;
        for (int q = 0; q < 4; q++) {
            if (q == missingQuad) {
                qMr[q] = mr;
                qMc[q] = mc;
            } else {
                qMr[q] = (q < 2) ? midR - 1 : midR;
                qMc[q] = (q % 2 == 0) ? midC - 1 : midC;
                rs[k] = qMr[q];
                cs[k] = qMc[q];
                k++;
            }
        }
        Tromino center = new Tromino(rs, cs, 0);
        List<List<Tromino>> parts = new ArrayList<>();
        for (int q = 0; q < 4; q++) {
            parts.add(solve(half, qTop[q], qLeft[q], qMr[q], qMc[q]));
        }
        combine(size, top, left, center, parts, result);
        return result;
    }

    private static void combine(int size, int top, int left, Tromino center,
                                List<List<Tromino>> parts, List<Tromino> result) {
        List<Tromino> all = new ArrayList<>();
        for (int q = 0; q < 4; q++) {
            all.addAll(parts.get(q));
        }
        all.add(center);
        int[][] id = new int[size][size];
        for (int[] row : id) {
            java.util.Arrays.fill(row, -1);
        }
        for (int i = 0; i < all.size(); i++) {
            Tromino t = all.get(i);
            for (int j = 0; j < 3; j++) {
                id[t.rows[j] - top][t.cols[j] - left] = i;
            }
        }
        int[] base = new int[all.size()];
        for (int i = 0; i < all.size(); i++) {
            base[i] = all.get(i).color;
        }
        int[] start = new int[5];
        for (int q = 0; q < 4; q++) {
            start[q + 1] = start[q] + parts.get(q).size();
        }
        int[] col = new int[all.size()];
        for (int p1 = 0; p1 < 6; p1++) {
            for (int p2 = 0; p2 < 6; p2++) {
                for (int p3 = 0; p3 < 6; p3++) {
                    for (int cc = 0; cc < 3; cc++) {
                        int[][] perm = {PERMS[0], PERMS[p1], PERMS[p2], PERMS[p3]};
                        for (int q = 0; q < 4; q++) {
                            for (int i = start[q]; i < start[q + 1]; i++) {
                                col[i] = perm[q][base[i]];
                            }
                        }
                        col[all.size() - 1] = cc;
                        if (valid(id, col, size)) {
                            for (int i = 0; i < all.size(); i++) {
                                all.get(i).color = col[i];
                            }
                            result.addAll(all);
                            return;
                        }
                    }
                }
            }
        }
        throw new IllegalStateException("no valid coloring at size " + size);
    }

    private static boolean valid(int[][] id, int[] col, int size) {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (id[r][c] < 0) continue;
                if (r + 1 < size && id[r + 1][c] >= 0 && id[r + 1][c] != id[r][c]
                        && col[id[r + 1][c]] == col[id[r][c]]) return false;
                if (c + 1 < size && id[r][c + 1] >= 0 && id[r][c + 1] != id[r][c]
                        && col[id[r][c + 1]] == col[id[r][c]]) return false;
            }
        }
        return true;
    }
}