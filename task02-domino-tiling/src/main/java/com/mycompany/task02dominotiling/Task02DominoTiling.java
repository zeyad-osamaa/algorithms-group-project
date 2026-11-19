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
        DominoTiler.Result r = DominoTiler.tileDominoes(4, new DominoTiler.Cell(0, 0), new DominoTiler.Cell(3, 2));
        System.out.println("possible = " + r.possible());
        r.dominoes().forEach(System.out::println);
    }
}
