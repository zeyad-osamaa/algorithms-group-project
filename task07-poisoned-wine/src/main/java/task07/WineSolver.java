/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task07;

import java.util.ArrayList;
import java.util.List;

/**
 * Task 7 (Poisoned Wine), solved with Divide and Conquer.
 */
public final class WineSolver {

    private WineSolver() {
    }

    /**
     * Uses the smallest number of tasting days that makes the plan possible.
     * If no number of days is enough, the result is not possible and reports
     * the largest number of days that was tried.
     */
    public static WinePlan designWineTests(int testers) {
        Validation.checkTesters(testers);
        for (int days = 1; days <= Validation.MAX_TASTING_DAYS; days++) {
            if (Validation.isFeasible(days, testers)) {
                return designWineTests(testers, days);
            }
        }
        return new WinePlan(false, testers, Validation.MAX_TASTING_DAYS, List.of(), null);
    }

    /** Designs the plan for a fixed number of tasting days (days 0 .. tastingDays - 1). */
    public static WinePlan designWineTests(int testers, int tastingDays) {
        Validation.checkTesters(testers);
        Validation.checkTastingDays(tastingDays);
        if (!Validation.isFeasible(tastingDays, testers)) {
            return new WinePlan(false, testers, tastingDays, List.of(), null);
        }

        // tastings.get(tester).get(day) holds the barrels that tester tastes on that day.
        List<List<List<Integer>>> tastings = new ArrayList<>();
        for (int t = 0; t < testers; t++) {
            List<List<Integer>> perDay = new ArrayList<>();
            for (int d = 0; d < tastingDays; d++) {
                perDay.add(new ArrayList<>());
            }
            tastings.add(perDay);
        }

        divide(0, Validation.BARRELS, 0, testers, tastingDays + 1, tastings);

        List<Tasting> schedule = new ArrayList<>();
        for (int t = 0; t < testers; t++) {
            for (int d = 0; d < tastingDays; d++) {
                List<Integer> barrels = tastings.get(t).get(d);
                if (!barrels.isEmpty()) {
                    schedule.add(new Tasting(t, d, barrels));
                }
            }
        }
        return new WinePlan(true, testers, tastingDays, schedule, new Decoder(testers, tastingDays));
    }

    /** Size of each group when count barrels are split into base groups. */
    static int groupSize(int count, int base) {
        return (count + base - 1) / base;
    }

    /**
     * Divide: split the barrels [lo, hi) into base groups.
     * Group g (g >= 1) is tasted by this tester on day g - 1; group 0 is not tasted.
     * Conquer: each group is handled the same way by the next tester.
     */
    private static void divide(int lo, int hi, int tester, int testers, int base,
                               List<List<List<Integer>>> tastings) {
        if (hi - lo <= 1 || tester == testers) {
            return;
        }
        int size = groupSize(hi - lo, base);
        for (int g = 0; g < base; g++) {
            int start = lo + g * size;
            if (start >= hi) {
                break;
            }
            int end = Math.min(hi, start + size);
            if (g > 0) {
                List<Integer> taste = tastings.get(tester).get(g - 1);
                for (int barrel = start; barrel < end; barrel++) {
                    taste.add(barrel);
                }
            }
            divide(start, end, tester + 1, testers, base, tastings);
        }
    }
}
