/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.task02dominotiling;

/**
 *
 * @author dell
 */

import java.util.List;

public class DominoTiler {

    public record Cell(int row, int col) {}
    public record Domino(Cell first, Cell second) {}
    public record Result(boolean possible, List<Domino> dominoes) {}
    
    private static boolean isValidInput(int n, Cell a, Cell b) {
        if (n < 2 || n > 8 || a == null || b == null) {
            return false;
            }
        if (!isInside(n, a) || !isInside(n, b)) {
            return false;
            }
        return colorOf(a) != colorOf(b);
        }

    private static boolean isInside(int n, Cell c) {
        return c.row() >= 0 && c.row() < n && c.col() >= 0 && c.col() < n;
        }
    
    private static int colorOf(Cell c) {
        return (c.row() + c.col()) % 2;
        }
    
    private static Cell findFirstEmpty(boolean[][] filled) {
        for (int r = 0; r < filled.length; r++) {
            for (int c = 0; c < filled.length; c++) {
                if (!filled[r][c]) {
                    return new Cell(r, c);
                    }
                }
            }
        return null;
        }

    private static boolean isFree(boolean[][] filled, Cell c) {
        return c.row() < filled.length && c.col() < filled.length && !filled[c.row()][c.col()];
        }
    
    private static boolean solve(boolean[][] filled, List<Domino> placed) {
        Cell start = findFirstEmpty(filled);
        if (start == null) {
            return true;
            }
        
        Cell right = new Cell(start.row(), start.col() + 1);
        Cell below = new Cell(start.row() + 1, start.col());
        return tryPlace(filled, placed, start, right)|| tryPlace(filled, placed, start, below);
    }

    private static boolean tryPlace(boolean[][] filled, List<Domino> placed,Cell a, Cell b) {
        if (!isFree(filled, b)) {
            return false;
        }
        
        filled[a.row()][a.col()] = true;
        filled[b.row()][b.col()] = true;
        placed.add(new Domino(a, b));

        if (solve(filled, placed)) {
            return true;
        }

        placed.remove(placed.size() - 1);
        filled[a.row()][a.col()] = false;
        filled[b.row()][b.col()] = false;
        return false;
    }
}