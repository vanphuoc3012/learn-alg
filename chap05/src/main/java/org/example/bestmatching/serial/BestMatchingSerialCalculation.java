package org.example.bestmatching.serial;

import org.example.bestmatching.common.BestMatchingData;
import org.example.bestmatching.distance.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

public class BestMatchingSerialCalculation {
    private BestMatchingSerialCalculation() {
    }

    public static BestMatchingData getBestMatchingWords(String word, List<String> dictionary) {
        ArrayList<String> result = new ArrayList<>();
        int minDistance = Integer.MAX_VALUE;
        int distance;

        for (String str : dictionary) {
            distance = LevenshteinDistance.calculate(word, str);
            if (distance < minDistance) {
                result.clear();
                minDistance = distance;
                result.add(str);
            } else if (distance == minDistance) {
                result.add(str);
            }
        }
        BestMatchingData data = new BestMatchingData();
        data.setDistance(minDistance);
        data.setWords(result);
        return data;
    }
}
