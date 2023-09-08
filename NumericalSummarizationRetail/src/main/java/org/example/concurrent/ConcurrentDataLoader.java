package org.example.concurrent;

import org.example.common.Record;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ConcurrentDataLoader {
    public static List<Record> load(Path path) throws IOException {
        System.out.println("Concurrent load data");

        List<String> lines = Files.readAllLines(path);

        return lines.parallelStream()
                    .skip(1)
                    .map(line -> line.split(";"))
                    .map(str -> new Record(str))
                    .collect(Collectors.toList());
    }
}
