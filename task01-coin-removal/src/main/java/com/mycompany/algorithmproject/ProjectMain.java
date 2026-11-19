/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.algorithmproject;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public class ProjectMain {

    public static void main(String[] args) {
    //TASK 1 Tests
    //Assumption: User enters H and T only values for coins
    // Greedy Technique used
            Scanner input = new Scanner(System.in);

        try {
            System.out.print("Enter number of coins: ");
            int n = input.nextInt();

            if (n < 1) {
                System.out.println("Error: number of coins must be at least 1");
                return;
            }

            char[] coins = new char[n];

            System.out.print("Enter coins (H/T): ");
            String s = input.next().toUpperCase();

            if (s.length() != n) {
                System.out.println("Error: expected " + n + " coins but got " + s.length());
                return;
            }

            for (int i = 0; i < n; i++) {
                coins[i] = s.charAt(i);
            }

            int[] sequence = CoinRemoval.removeCoins(n, coins);

            if (sequence == null) {
                System.out.println("No solution!");
            } else {
                System.out.println("Removal sequence:");

                for (int i = 0; i < sequence.length; i++) {
                    System.out.print(sequence[i] + " ");
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: number of coins must be an integer");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            input.close();
        }
    
    }
}
