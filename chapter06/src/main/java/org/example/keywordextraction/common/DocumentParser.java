package org.example.keywordextraction.common;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;

public class DocumentParser {
    public static Document parse(String path) {
        Document doc = new Document();
        Path file = Paths.get(path);
        doc.setFileName(file.getFileName().toString());

        try  {
            for (String line: Files.readAllLines(file)) {
                parseLine(line, doc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    private static void parseLine(String line, Document doc) {
        line = Normalizer.normalize(line, Normalizer.Form.NFKD);
        line = line.replaceAll("[^\\p{ASCII}]", "");
        line = line.toLowerCase();

        for (String w : line.split("\\W+")) {
            doc.addWord(w);
        }
    }
}
