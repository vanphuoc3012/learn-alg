package org.example.bestmatching.serial;

import org.example.bestmatching.common.BestMatchingData;
import org.example.bestmatching.loader.WordLoader;

import java.util.Date;
import java.util.List;

public class BestMatchingSerialMain {
    public static void main(String[] args) {
        Date startTime, endTime;

//        List<String> dictionary = WordLoader.load("data/UK Advanced Cryptics Dictionary.txt");
        List<String> dictionary = WordLoader.load("data/words.txt");
        System.out.println("Loading dictionary success. Dictionary size: " + dictionary.size());

        startTime = new Date();
        BestMatchingData result = BestMatchingSerialCalculation.getBestMatchingWords(args[0],
                                                                                     dictionary);
        endTime = new Date();
        List<String> words = result.getWords();
        System.out.println("Word: " + args[0]);
        System.out.println("Minimum distance: " + result.getDistance());
        System.out.println("List of best matching words: " + words.size());
        words.forEach(System.out::println);
        System.out.println("Execution time: " + (endTime.getTime() - startTime.getTime()) + " ms");
    }
}
