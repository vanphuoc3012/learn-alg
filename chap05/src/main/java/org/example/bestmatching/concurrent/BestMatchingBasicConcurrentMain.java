package org.example.bestmatching.concurrent;

import org.example.bestmatching.common.BestMatchingData;
import org.example.bestmatching.loader.WordLoader;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BestMatchingBasicConcurrentMain {
    public static void main(String[] args) {
        try {
            Date startTime, endTime;
            List<String> dictionary = WordLoader.load("data/words.txt");

            System.out.println("Dictionary size: " + dictionary.size());
            startTime = new Date();

            String word = "drectry";

            if (args.length == 1) {
                word = args[0];
            }

            BestMatchingData ret = BestMatchingBasicConcurrentCalculation.getBestMatchingWords(word,
                                                                                               dictionary);
            endTime = new Date();

            List<String> words = ret.getWords();
            System.out.println("Word: " + word);
            System.out.println("Minimun distance: " + ret.getDistance());
            System.out.println("List of best matching words: " + words.size());

            words.forEach(System.out::println);
            System.out.println(
                    "Execution time: " + (endTime.getTime() - startTime.getTime()) + " ms");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
