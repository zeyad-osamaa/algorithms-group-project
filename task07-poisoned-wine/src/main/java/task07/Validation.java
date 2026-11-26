/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task07;

/**
 * Problem constants, input checks and capacity math for Task 7 (Poisoned Wine).
 */
public final class Validation {

    /** Number of barrels in the problem. */
    public static final int BARRELS = 1000;

    /** The poison kills exactly this many days after tasting. */
    public static final int POISON_DELAY_DAYS = 30;

    /** Days from now until the feast (five weeks). */
    public static final int FEAST_DAYS = 35;

    /** Last day on which a tasting can still show its result before the feast. */
    public static final int LAST_TASTING_DAY = FEAST_DAYS - POISON_DELAY_DAYS;

    /** Number of distinct tasting days available (days 0 to 5). */
    public static final int MAX_TASTING_DAYS = LAST_TASTING_DAY + 1;

    /** Largest number of testers accepted by the program. */
    public static final int MAX_TESTERS = 64;

    private Validation() {
    }

    /** Checks that the number of testers is in the accepted range. */
    public static void checkTesters(int testers) {
        if (testers < 0 || testers > MAX_TESTERS) {
            throw new IllegalArgumentException(
                    "testers must be between 0 and " + MAX_TESTERS + ", got " + testers);
        }
    }

    /** Checks that the number of tasting days fits before the feast. */
    public static void checkTastingDays(int tastingDays) {
        if (tastingDays < 1 || tastingDays > MAX_TASTING_DAYS) {
            throw new IllegalArgumentException(
                    "tastingDays must be between 1 and " + MAX_TASTING_DAYS + ", got " + tastingDays);
        }
    }

    /** Checks that a barrel number is valid (0 to BARRELS - 1). */
    public static void checkBarrel(int barrel) {
        if (barrel < 0 || barrel >= BARRELS) {
            throw new IllegalArgumentException(
                    "barrel must be between 0 and " + (BARRELS - 1) + ", got " + barrel);
        }
    }

    /**
     * Returns base^testers, but never more than BARRELS.
     * Each tester has "base" possible outcomes, so base^testers is how many
     * barrels the testers can tell apart. Stopping at BARRELS avoids overflow.
     */
    public static int capacity(int base, int testers) {
        int result = 1;
        for (int i = 0; i < testers; i++) {
            result *= base;
            if (result >= BARRELS) {
                return BARRELS;
            }
        }
        return result;
    }

    /** True if the given testers can tell all barrels apart. */
    public static boolean isFeasible(int tastingDays, int testers) {
        checkTastingDays(tastingDays);
        checkTesters(testers);
        return capacity(tastingDays + 1, testers) >= BARRELS;
    }

    /** Smallest number of testers that can tell all barrels apart. */
    public static int minimumTesters(int tastingDays) {
        checkTastingDays(tastingDays);
        int base = tastingDays + 1;
        int testers = 0;
        while (capacity(base, testers) < BARRELS) {
            testers++;
        }
        return testers;
    }
}
