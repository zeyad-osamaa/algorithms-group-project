/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.task3;
import java.util.Arrays;

/**
 *
 * @author LapTop
 */
public class Task3 {
    static boolean isValid(int n, int[] toggles) {
        boolean[] on = new boolean[n + 2];
        for (int i = 1; i <= n; i++) {
            on[i] = true;
        }

        for (int k : toggles) {
            if (k < 1 || k > n) {
                return false;
            }
            if (k != n) {
                // the right neighbor must be ON
                if (!on[k + 1]) {
                    return false;
                }
                // everything farther to the right must be OFF
                for (int j = k + 2; j <= n; j++) {
                    if (on[j]) {
                        return false;
                    }
                }
            }
            on[k] = !on[k];
        }

        for (int i = 1; i <= n; i++) {
            if (on[i]) {
                return false;
            }
        }
        return true;
    }

    /** Closed-form minimum number of moves: (2^(n+1) - 2)/3 for even n, (2^(n+1) - 1)/3 for odd n. */
    static long expectedMoves(int n) {
        long pow = 1L << (n + 1);
        return (n % 2 == 0) ? (pow - 2) / 3 : (pow - 1) / 3;
    }
    
    
    
    
    
    public static void main(String[] args) {
        for (int n = 1; n <= 6; n++) {
            TurnOffSwitches.Result r = TurnOffSwitches.turnOffSwitches(n);
            System.out.println("n = " + n);
            System.out.println("  minMoves = " + r.minMoves);
            System.out.println("  toggles  = " + Arrays.toString(r.toggles));
            System.out.println("  valid    = " + isValid(n, r.toggles));
            System.out.println();
        }

        // Larger automatic check (sequence is validated, count compared with the formula)
        boolean allOk = true;
        for (int n = 1; n <= 20; n++) {
            TurnOffSwitches.Result r = TurnOffSwitches.turnOffSwitches(n);
            boolean ok = isValid(n, r.toggles) && r.minMoves == expectedMoves(n);
            if (!ok) {
                System.out.println("FAILED for n = " + n);
                allOk = false;
            }
        }
        System.out.println(allOk ? "All tests passed for n = 1..20" : "Some tests failed");
    }
}
