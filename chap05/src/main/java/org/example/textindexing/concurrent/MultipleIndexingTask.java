package org.example.textindexing.concurrent;

import org.example.textindexing.common.Document;
import org.example.textindexing.common.DocumentParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class MultipleIndexingTask implements Callable<List<Document>> {
    private List<File> fileList;

    public MultipleIndexingTask(List<File> fileList) {
        this.fileList = fileList;
    }

    @Override
    public List<Document> call() throws Exception {
        List<Document> documentList = new ArrayList<>();
        for (File file : fileList) {
            DocumentParser parser = new DocumentParser();
            Map<String, Integer> voc = parser.parse(file.getAbsolutePath());

            Document document = new Document();
            document.setVoc(voc);
            document.setFileName(file.getName());
            documentList.add(document);
        }
        return documentList;
    }
}
