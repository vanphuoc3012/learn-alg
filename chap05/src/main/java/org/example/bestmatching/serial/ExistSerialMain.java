package org.example.bestmatching.serial;

import org.example.bestmatching.loader.WordLoader;

import java.util.Date;
import java.util.List;

public class ExistSerialMain {
    public static void main(String[] args) {
        Date startTime, endTime;
        List<String> dictionary = WordLoader.load("data/words.txt");

        System.out.println("Dictionary size: " + dictionary.size());

        String word = "drectry";

        if (args.length == 1) {
            word = args[0];
        }
        startTime = new Date();
        boolean result = ExistSerialCalculation.existWord(word, dictionary);
        endTime = new Date();

        System.out.println("Word: " + word);
        System.out.println("Exist: " + result);
        System.out.println("Execution time: " + (endTime.getTime() - startTime.getTime()) + " ms");
    }
}
