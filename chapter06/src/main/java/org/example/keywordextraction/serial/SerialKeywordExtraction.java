package org.example.keywordextraction.serial;

import org.example.keywordextraction.common.Document;
import org.example.keywordextraction.common.DocumentParser;
import org.example.keywordextraction.common.Keyword;
import org.example.keywordextraction.common.Word;

import javax.print.Doc;
import javax.swing.event.DocumentEvent;
import java.io.File;
import java.util.*;

public class SerialKeywordExtraction {
    public static void main(String[] args) {
        Date start, end;

        File source = new File("data");
        File[] files = source.listFiles();

        HashMap<String, Word> globalVoc = new HashMap<>();
        HashMap<String, Integer> globalKeywords = new HashMap<>();
        int totalCalls = 0;
        int numDocuments = 0;

        start = new Date();

        // Phase 1: Parse all the documents
        if (files ==null) {
            System.out.println("Unable to read the 'data' folder");
            return;
        }

        for (File file : files) {
            if (file.getName().endsWith(".txt")) {
                Document doc = DocumentParser.parse(file.getAbsolutePath());
                for (Word word : doc.getVoc().values()) {
                    globalVoc.merge(word.getWord(), word, Word::merge);
                }
                numDocuments++;
            }
        }
        System.out.println("Corpus: " + numDocuments + " documents.") ;

        // Phase 2: Update te df of the voc of the Documents
        for (File file : files) {
            if (file.getName().endsWith(".txt")) {
                Document doc = DocumentParser.parse(file.getAbsolutePath());
                List<Word> keywords = new ArrayList<>(doc.getVoc().values());
                for (Word word : keywords) {
                    Word globalWord = globalVoc.get(word.getWord());
                    word.setDf(globalWord.getDf(), numDocuments);
                }

                Collections.sort(keywords);

                if (keywords.size() > 10) {
                    keywords = keywords.subList(0, 10);
                }

                for (Word word : keywords) {
                    addKeyword(globalKeywords, word.getWord());
                    totalCalls++;
                }
            }
        }

        // Phase 3: Get a list of a better keywords
        List<Keyword> orderedGlobalKeywords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : globalKeywords.entrySet()) {
            Keyword keyword = new Keyword();
            keyword.setWord(entry.getKey());
            keyword.setDf(entry.getValue());
            orderedGlobalKeywords.add(keyword);
        }

        Collections.sort(orderedGlobalKeywords);

        if (orderedGlobalKeywords.size() > 100) {
            orderedGlobalKeywords = orderedGlobalKeywords.subList(0, 100);
        }

        for (Keyword keyword : orderedGlobalKeywords) {
            System.out.println(keyword.getWord()+": " + keyword.getDf());
        }

        end = new Date();

        System.out.println("Execution time: " + (end.getTime() - start.getTime()));
        System.out.println("Vocabulary size: " + globalVoc.size());
        System.out.println("Keyword size: " + globalKeywords.size());
        System.out.println("Number of documents: " + numDocuments);
        System.out.println("Total calls: " + totalCalls);
    }

    private static void addKeyword(HashMap<String, Integer> globalKeywords, String word) {
        globalKeywords.merge(word, 1, Integer::sum);
    }
}
