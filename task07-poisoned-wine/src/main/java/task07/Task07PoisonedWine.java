package task07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Console entry point for Task 7 (Poisoned Wine).
 *
 * Usage:
 *   no arguments           runs the four built-in test cases
 *   testers [tastingDays]  runs one case
 *   file.txt               runs one case read from a file containing: testers [tastingDays]
 */
public class Task07PoisonedWine {

    private static final String[][] BUILT_IN_CASES = {
        {"10", "1"},
        {"8", "1"},
        {"8", "2"},
        {"7", "2"}
    };

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                for (int i = 0; i < BUILT_IN_CASES.length; i++) {
                    System.out.println("=== Test case " + (i + 1) + " ===");
                    runCase(BUILT_IN_CASES[i]);
                    System.out.println();
                }
            } else if (args.length == 1 && args[0].endsWith(".txt")) {
                String text = Files.readString(Path.of(args[0])).trim();
                runCase(text.split("\\s+"));
            } else {
                runCase(args);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void runCase(String[] tokens) {
        if (tokens.length < 1 || tokens.length > 2) {
            throw new IllegalArgumentException("expected: testers [tastingDays]");
        }
        int testers = Integer.parseInt(tokens[0]);
        WinePlan plan = tokens.length == 2
                ? WineSolver.designWineTests(testers, Integer.parseInt(tokens[1]))
                : WineSolver.designWineTests(testers);
        report(plan);
    }

    private static void report(WinePlan plan) {
        int days = plan.tastingDays();
        System.out.println("Testers: " + plan.testers() + ", tasting days: " + days);
        System.out.println("Barrels the testers can tell apart (capped at "
                + Validation.BARRELS + "): "
                + Validation.capacity(days + 1, plan.testers()));

        if (!plan.possible()) {
            System.out.println("Result: NOT POSSIBLE");
            return;
        }
        System.out.println("Result: POSSIBLE");

        for (Tasting tasting : plan.schedule()) {
            System.out.println("  Tester " + tasting.tester() + ", day " + tasting.day()
                    + ": " + tasting.barrels().size() + " barrels ("
                    + preview(tasting.barrels()) + ")");
        }

        int correct = Verifier.countCorrect(plan);
        System.out.println("Verified: " + correct + "/" + Validation.BARRELS
                + " poisoned-barrel cases decoded correctly");

        int example = 777;
        int[] outcome = Verifier.observe(plan, example);
        System.out.println("Example: poisoned barrel " + example + " gives outcome "
                + Arrays.toString(outcome) + " (-1 = alive, otherwise the day of death)");
        System.out.println("Decoded barrel: " + plan.decoder().decode(outcome));
    }

    private static String preview(List<Integer> barrels) {
        int shown = Math.min(5, barrels.size());
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < shown; i++) {
            if (i > 0) {
                text.append(", ");
            }
            text.append(barrels.get(i));
        }
        if (barrels.size() > shown) {
            text.append(", ...");
        }
        return text.toString();
    }
}
