/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task07;

/**
 * Finds the poisoned barrel from what happened to each tester.
 * It follows the same divide steps as WineSolver, in reverse.
 */
public final class Decoder {

    /** Value in the outcome array for a tester who is still alive. */
    public static final int ALIVE = -1;

    private final int testers;
    private final int tastingDays;
    private final int base;

    Decoder(int testers, int tastingDays) {
        this.testers = testers;
        this.tastingDays = tastingDays;
        this.base = tastingDays + 1;
    }

    /**
     * @param deathDays one entry per tester: ALIVE, or the day on which the tester died
     *                  (30 + the tasting day)
     * @return the poisoned barrel
     */
    public int decode(int[] deathDays) {
        if (deathDays.length != testers) {
            throw new IllegalArgumentException(
                    "expected " + testers + " outcomes, got " + deathDays.length);
        }
        return decode(0, 0, Validation.BARRELS, deathDays);
    }

    /** Looks at one tester, picks the matching group, and continues with the next tester. */
    private int decode(int tester, int lo, int hi, int[] deathDays) {
        if (hi - lo == 1) {
            for (int t = tester; t < testers; t++) {
                if (deathDays[t] != ALIVE) {
                    throw new IllegalArgumentException("outcome does not match any barrel");
                }
            }
            return lo;
        }
        if (tester == testers) {
            throw new IllegalArgumentException("outcome does not identify a single barrel");
        }
        int digit = digitOf(deathDays[tester]);
        int size = WineSolver.groupSize(hi - lo, base);
        int start = lo + digit * size;
        if (start >= hi) {
            throw new IllegalArgumentException("outcome does not match any barrel");
        }
        int end = Math.min(hi, start + size);
        return decode(tester + 1, start, end, deathDays);
    }

    /** Converts a death day into the digit used when the plan was built. */
    private int digitOf(int deathDay) {
        if (deathDay == ALIVE) {
            return 0;
        }
        int tastingDay = deathDay - Validation.POISON_DELAY_DAYS;
        if (tastingDay < 0 || tastingDay >= tastingDays) {
            throw new IllegalArgumentException("impossible death day: " + deathDay);
        }
        return tastingDay + 1;
    }
}