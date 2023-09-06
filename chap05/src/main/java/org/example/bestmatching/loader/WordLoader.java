package org.example.bestmatching.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordLoader {
    private WordLoader() {
    }

    public static List<String> load(String path) {
        Path filePath = Paths.get(path);
        List<String> data = new ArrayList<>();
        try (
                InputStream in = Files.newInputStream(filePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
