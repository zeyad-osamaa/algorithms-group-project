/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task07;

import java.util.Arrays;

/**
 * Simulates the poison for a plan and checks that the decoder finds the right barrel.
 */
public final class Verifier {

    private Verifier() {
    }

    /** What the king sees if the given barrel is the poisoned one. */
    public static int[] observe(WinePlan plan, int poisonedBarrel) {
        Validation.checkBarrel(poisonedBarrel);
        int[] deathDays = new int[plan.testers()];
        Arrays.fill(deathDays, Decoder.ALIVE);
        for (Tasting tasting : plan.schedule()) {
            if (tasting.barrels().contains(poisonedBarrel)) {
                deathDays[tasting.tester()] = Validation.POISON_DELAY_DAYS + tasting.day();
            }
        }
        return deathDays;
    }

    /** Tries every barrel as the poisoned one and counts how many are decoded correctly. */
    public static int countCorrect(WinePlan plan) {
        if (!plan.possible()) {
            return 0;
        }
        int correct = 0;
        for (int barrel = 0; barrel < Validation.BARRELS; barrel++) {
            if (plan.decoder().decode(observe(plan, barrel)) == barrel) {
                correct++;
            }
        }
        return correct;
    }
}