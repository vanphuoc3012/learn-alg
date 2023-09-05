package org.example.writer;

import org.example.buffer.NewsBuffer;
import org.example.common.CommonInformationItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NewsWriter implements Runnable {
    private NewsBuffer buffer;

    public NewsWriter(NewsBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                CommonInformationItem item = buffer.get();
                Path path = Paths.get("output/" + item.getFileName());

                try (
                        BufferedWriter fileWriter = Files.newBufferedWriter(path,
                                                                            StandardOpenOption.CREATE)
                ) {
                    fileWriter.write(item.toXML());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            
        }
    }
}
