/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.task02dominotiling;

/**
 *
 * @author dell
 */
public class Task02DominoTiling {

    public static void main(String[] args) {
        System.out.println("=== Test 1: 4x4, missing (0,0) and (3,2) ===");
        DominoTiler.Result r1 = DominoTiler.tileDominoes(4, new DominoTiler.Cell(0, 0), new DominoTiler.Cell(3, 2));
        System.out.println("possible = " + r1.possible());
        r1.dominoes().forEach(System.out::println);
        System.out.println();

        System.out.println("=== Test 2: 3x3, missing (0,0) and (0,1) ===");
        DominoTiler.Result r2 = DominoTiler.tileDominoes(3, new DominoTiler.Cell(0, 0), new DominoTiler.Cell(0, 1));
        System.out.println("possible = " + r2.possible());
        r2.dominoes().forEach(System.out::println);
        System.out.println();

        System.out.println("=== Test 3: 8x8, missing (0,0) and (7,6) ===");
        DominoTiler.Result r3 = DominoTiler.tileDominoes(8, new DominoTiler.Cell(0, 0), new DominoTiler.Cell(7, 6));
        System.out.println("possible = " + r3.possible());
        r3.dominoes().forEach(System.out::println);
        System.out.println();

        System.out.println("=== Test 4: invalid, same color (0,0) and (1,1) ===");
        DominoTiler.Result r4 = DominoTiler.tileDominoes(4, new DominoTiler.Cell(0, 0), new DominoTiler.Cell(1, 1));
        System.out.println("possible = " + r4.possible());
        r4.dominoes().forEach(System.out::println);
    }
}
    