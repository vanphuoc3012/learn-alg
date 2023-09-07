package org.example.keywordextraction.concurrent;

import org.example.keywordextraction.common.Document;
import org.example.keywordextraction.common.DocumentParser;
import org.example.keywordextraction.common.Keyword;
import org.example.keywordextraction.common.Word;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Phaser;

public class KeywordExtractionTasks implements Runnable {
    private ConcurrentHashMap<String, Word> globalVoc;
    private ConcurrentHashMap<String, Integer> globalKeywords;

    private ConcurrentLinkedDeque<File> concurrentFileListPhase1;
    private ConcurrentLinkedDeque<File> concurrentFileListPhase2;

    private Phaser phaser;

    private String name;
    private boolean main;

    private int parsedDocuments;
    private int numDocuments;

    public KeywordExtractionTasks(ConcurrentHashMap<String, Word> globalVoc,
                                  ConcurrentHashMap<String, Integer> globalKeywords,
                                  ConcurrentLinkedDeque<File> concurrentFileListPhase1,
                                  ConcurrentLinkedDeque<File> concurrentFileListPhase2,
                                  Phaser phaser, String name, boolean main, int numDocuments) {
        this.globalVoc = globalVoc;
        this.globalKeywords = globalKeywords;
        this.concurrentFileListPhase1 = concurrentFileListPhase1;
        this.concurrentFileListPhase2 = concurrentFileListPhase2;
        this.phaser = phaser;
        this.name = name;
        this.main = main;
        this.numDocuments = numDocuments;
//        System.out.println(name + ": " + main);
    }

    @Override
    public void run() {
        File file;
        // Phase 1:
        phaser.arriveAndAwaitAdvance();
//        System.out.println(name + ": Phase 1");
        while ((file = concurrentFileListPhase1.poll()) != null) {
            Document document = DocumentParser.parse(file.getAbsolutePath());
            for (Word word : document.getVoc().values()) {
                globalVoc.merge(word.getWord(), word, Word::merge);
            }
            parsedDocuments++;
        }
//            System.out.println("Phase 1: " + name + ": " + parsedDocuments + " parsed documents.");
        phaser.arriveAndAwaitAdvance();
        // Phase 2:
//        System.out.println(name + ": Phase 2");
        while ((file = concurrentFileListPhase2.poll()) != null) {
            Document document = DocumentParser.parse(file.getAbsolutePath());
            List<Word> keywords = new ArrayList<>(document.getVoc().values());

            for (Word word : keywords) {
                Word globalWord = globalVoc.get(word.getWord());
                word.setDf(globalWord.getDf(), numDocuments);
            }
            Collections.sort(keywords);

            if (keywords.size() > 10) keywords = keywords.subList(0, 10);
            for (Word word : keywords) {
                addKeyword(globalKeywords, word.getWord());
            }
        }
//        System.out.println("Phase 2: " + name + ": " + parsedDocuments + " parsed.");

        if (main) {
            phaser.arriveAndAwaitAdvance();

            //Phase 3:
//            System.out.println(name + ": Phase 3");
            Iterator<Map.Entry<String, Integer>> iterator = globalKeywords.entrySet().iterator();
            Keyword[] orderedGlobalKeywords = new Keyword[globalKeywords.size()];

            int index = 0;
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                Keyword keyword = new Keyword();
                keyword.setWord(entry.getKey());
                keyword.setDf(entry.getValue());
                orderedGlobalKeywords[index] = keyword;
                index++;
            }
//            System.out.println("Keyword Size: " + orderedGlobalKeywords.length);
            Arrays.parallelSort(orderedGlobalKeywords);

            int count = 0;
            for (int i = 0; i < orderedGlobalKeywords.length; i++) {
                Keyword keyword = orderedGlobalKeywords[i];
//                System.out.println(keyword.getWord() + ": " + keyword.getDf());
                count++;
                if (count == 100) {
                    break;
                }
            }
        }
        System.out.println(phaser);
        phaser.arriveAndDeregister();
//        System.out.println("Thread " + name + " has finished.");
    }

    private synchronized void addKeyword(ConcurrentHashMap<String, Integer> globalKeywords,
                                         String word) {
        globalKeywords.merge(word, 1, Integer::sum);
    }
}
