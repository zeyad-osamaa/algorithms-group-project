/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.algorithmproject;

/**
 *
 * @author DELL
 */
public class CoinRemoval {
    // Returns the removal sequence, or null if there is no solution
    public static int[] removeCoins(int n, char[] coins) {
        if (coins == null || n < 1 || coins.length != n) {
            throw new IllegalArgumentException(
                    "Number of coins must be at least 1 and match the length of the row");
        }

        for (int i = 0; i < n; i++) {
            if (coins[i] != 'H' && coins[i] != 'T') {
                throw new IllegalArgumentException(
                        "Invalid coin '" + coins[i] + "' at position " + i + " (use H or T only)");
            }
        }

        coins = coins.clone();   //working on a copy, original array stays unchanged
        
        int countH = 0;

        // Count initial H
        for (int i = 0; i < n; i++) {
            if (coins[i] == 'H') {
                countH++;
            }
        }

        if (countH % 2 == 0) {
            return null;
        }

        int[] sequence = new int[n];
        int count = 0;

        // Greedy removal
        for (int j = 0; j < n; j++) {

            if (coins[j] == 'H') {

                // Remove the coin
                coins[j] = 'X';

                // Flip left neighbor
                if (j > 0 && coins[j - 1] != 'X') {
                    coins[j - 1] =
                            (coins[j - 1] == 'H') ? 'T' : 'H';
                }

                // Flip right neighbor
                if (j < n - 1 && coins[j + 1] != 'X') {
                    coins[j + 1] =
                            (coins[j + 1] == 'H') ? 'T' : 'H';
                }

                // Save original index
                sequence[count] = j;
                count++;

                // Restart searching from index 0
                j = -1;
            }
        }

        if (count == n) {
            return sequence;
        }

        return null;
    }
}
