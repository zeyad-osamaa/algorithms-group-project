package com.mycompany.task3;

/**
 *
 * @author LapTop
 */
public class TurnOffSwitches {

    /** Holds the pair (minMoves, toggles). */
    public static class Result {
        public final int minMoves;
        public final int[] toggles;

        public Result(int minMoves, int[] toggles) {
            this.minMoves = minMoves;
            this.toggles = toggles;
        }
    }

    /**
     * Returns the minimum number of moves and the sequence of switches
     * toggled to turn off all n switches.
     */
    public static Result turnOffSwitches(int n) {
        if (n < 1) {
            return new Result(0, new int[0]);
        }
        if (n == 1) {
            return new Result(1, new int[]{1});
        }

        // Decrease by one: solve for the first n-1 switches
        Result prev = turnOffSwitches(n - 1);
        int[] oldToggles = prev.toggles;

        boolean odd = (n % 2 == 1);
        int[] toggles = new int[2 * oldToggles.length + (odd ? 1 : 0)];
        int idx = 0;

        // When n is odd, the first old toggle needs switch n to be OFF
        if (odd) {
            toggles[idx++] = n;
        }

        // After every old toggle, flip switch n so the next move is legal
        for (int x : oldToggles) {
            toggles[idx++] = x;
            toggles[idx++] = n;
        }

        return new Result(toggles.length, toggles);
    }
}

