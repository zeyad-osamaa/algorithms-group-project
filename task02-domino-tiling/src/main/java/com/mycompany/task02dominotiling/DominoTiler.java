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

}
