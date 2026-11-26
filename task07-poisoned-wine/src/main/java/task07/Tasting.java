/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package task07;

import java.util.List;

/**
 * One tasting event: a tester tastes a set of barrels on a given day.
 *
 * @param tester the tester index (0-based)
 * @param day    the day of the tasting (0 = today)
 * @param barrels the barrels the tester tastes on that day
 */
public record Tasting(int tester, int day, List<Integer> barrels) {

    public Tasting {
        barrels = List.copyOf(barrels);
    }
}
