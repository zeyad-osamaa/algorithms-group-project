package com.mycompany.algorithmproject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PointColoring {

    private static final int BLACK = 1;
    private static final int WHITE = -1;

    private final int n;
    private int boxCount;
    private int[] edgeX;
    private int[] edgeY;
    private int[][] adj;
    private int[] color;
    private int[] d;
    private int[] usedStamp;
    private int walkId = 0;

    private PointColoring(int n, int[][] points) {
        this.n = n;
        buildGraph(points);
        initColoring();
    }

    public static char[] colorPoints(int n, int[][] points) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        if (points == null || points.length != n) {
            throw new IllegalArgumentException("points must be non-null with length n");
        }
        for (int[] p : points) {
            if (p == null || p.length != 2) {
                throw new IllegalArgumentException("each point must be {x, y}");
            }
        }
        if (n == 0) {
            return new char[0];
        }
        PointColoring solver = new PointColoring(n, points);
        solver.balance();
        return solver.toChars();
    }

    public static boolean isValid(int[][] points, char[] colors) {
        if (points == null || colors == null || points.length != colors.length) {
            return false;
        }
        Map<Integer, Integer> xSum = new HashMap<>();
        Map<Integer, Integer> ySum = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            if (colors[i] != 'B' && colors[i] != 'W') {
                return false;
            }
            int c = colors[i] == 'B' ? BLACK : WHITE;
            xSum.merge(points[i][0], c, Integer::sum);
            ySum.merge(points[i][1], c, Integer::sum);
        }
        for (int s : xSum.values()) {
            if (Math.abs(s) > 1) return false;
        }
        for (int s : ySum.values()) {
            if (Math.abs(s) > 1) return false;
        }
        return true;
    }

    private void buildGraph(int[][] points) {
        Map<Integer, Integer> xId = new HashMap<>();
        Map<Integer, Integer> yId = new HashMap<>();
        edgeX = new int[n];
        edgeY = new int[n];
        int[] degree = new int[2 * n];
        boxCount = 0;

        for (int i = 0; i < n; i++) {
            Integer bx = xId.get(points[i][0]);
            if (bx == null) {
                bx = boxCount++;
                xId.put(points[i][0], bx);
            }
            Integer by = yId.get(points[i][1]);
            if (by == null) {
                by = boxCount++;
                yId.put(points[i][1], by);
            }
            edgeX[i] = bx;
            edgeY[i] = by;
            degree[bx]++;
            degree[by]++;
        }

        adj = new int[boxCount][];
        for (int b = 0; b < boxCount; b++) {
            adj[b] = new int[degree[b]];
        }
        int[] fill = new int[boxCount];
        for (int i = 0; i < n; i++) {
            adj[edgeX[i]][fill[edgeX[i]]++] = i;
            adj[edgeY[i]][fill[edgeY[i]]++] = i;
        }
    }

    private void initColoring() {
        color = new int[n];
        usedStamp = new int[n];
        d = new int[boxCount];
        Arrays.fill(color, BLACK);
        for (int b = 0; b < boxCount; b++) {
            for (int i : adj[b]) {
                d[b] += color[i];
            }
        }
    }

    private void flip(int i) {
        int old = color[i];
        color[i] = -old;
        d[edgeX[i]] -= 2 * old;
        d[edgeY[i]] -= 2 * old;
    }

    private int other(int i, int box) {
        return edgeX[i] == box ? edgeY[i] : edgeX[i];
    }

    private int findEdge(int box, int c) {
        for (int i : adj[box]) {
            if (usedStamp[i] != walkId && color[i] == c) {
                return i;
            }
        }
        throw new IllegalStateException("No usable edge found; invariant broken");
    }

    private void fixOneWalk(int v) {
        walkId++;
        int c = Integer.signum(d[v]);
        int box = v;
        while (true) {
            int i = findEdge(box, c);
            usedStamp[i] = walkId;
            flip(i);
            box = other(i, box);
            if (d[box] * c >= -1) {
                return;
            }
            c = -c;
        }
    }

    private void balance() {
        for (int v = 0; v < boxCount; v++) {
            while (Math.abs(d[v]) >= 2) {
                fixOneWalk(v);
            }
        }
    }

    private char[] toChars() {
        char[] result = new char[n];
        for (int i = 0; i < n; i++) {
            result[i] = color[i] == BLACK ? 'B' : 'W';
        }
        return result;
    }
}
