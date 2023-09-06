package org.example.bestmatching.serial;

import org.example.bestmatching.distance.LevenshteinDistance;

import java.util.List;

public class ExistSerialCalculation {
    private ExistSerialCalculation() {
    }

    public static boolean existWord(String word, List<String> dictionary) {
        for (String str : dictionary) {
            if (LevenshteinDistance.calculate(word, str) == 0) {
                return true;
            }
        }
        return false;
    }
}
