package org.example.bestmatching.distance;

public class LevenshteinDistance {
    private LevenshteinDistance() {
    }

    public static int calculate(String str1, String str2) {
        int[][] distances = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 1; i <= str1.length(); i++) {
            distances[i][0] = i;
        }

        for (int i = 1; i <= str2.length(); i++) {
            distances[0][i] = i;
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    distances[i][j] = distances[i - 1][j - 1];
                } else {
                    distances[i][j] = minimum(distances[i - 1][j], distances[i][j - 1],
                                              distances[i - 1][j - 1]) + 1;
                }
            }
        }

        return distances[str1.length()][str2.length()];
    }

    private static int minimum(int i, int i1, int i2) {
        return Math.min(i, Math.min(i1, i2));
    }
}
