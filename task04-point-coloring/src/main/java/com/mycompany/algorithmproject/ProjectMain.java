package com.mycompany.algorithmproject;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProjectMain {

    public static void main(String[] args) {
        // TASK 4 Point Coloring
        // Assumptions: points are distinct, coordinates are integers,
        // colors are printed as B (black) and W (white)
        // Iterative Improvement technique used
        Scanner input = new Scanner(System.in);

        try {
            System.out.print("Enter number of points: ");
            int n = input.nextInt();

            if (n < 1) {
                System.out.println("Error: number of points must be at least 1");
                return;
            }

            int[][] points = new int[n][2];

            System.out.println("Enter " + n + " points (x y), one per line:");
            for (int i = 0; i < n; i++) {
                points[i][0] = input.nextInt();
                points[i][1] = input.nextInt();
            }

            char[] colors = PointColoring.colorPoints(n, points);

            System.out.println("Colors:");
            for (int i = 0; i < n; i++) {
                System.out.println("(" + points[i][0] + ", " + points[i][1] + ") -> " + colors[i]);
            }
            System.out.println("Valid coloring: " + PointColoring.isValid(points, colors));

        } catch (InputMismatchException e) {
            System.out.println("Error: input must be integers");
        } catch (NoSuchElementException e) {
            System.out.println("Error: not enough input values");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            input.close();
        }
    }
}
