package org.example.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AmazonMetaDataLoader {
    public static AmazonMetaData[] load(Path path) {
        List<AmazonMetaData> list = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader("data/amazon-meta.txt"));
        ) {

            AmazonMetaData data = new AmazonMetaData();
            String line;
            int skipRow = 6;
            while ((line = reader.readLine()) != null) {
                if (skipRow >= 0) {
                    skipRow--;
                    continue;
                }
                if (line.isEmpty()) {
                    list.add(data);
                    data = new AmazonMetaData();
                    continue;
                }

                String[] parts = line.trim().split("\\s+", 2);
                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];

                    switch (key) {
                        case "Id:":
                            data.setId(Integer.parseInt(value));
                            break;
                        case "ASIN:":
                            data.setASIN(value);
                            break;
                        case "title:":
                            data.setTitle(value);
                            break;
                        case "group:":
                            data.setGroup(value);
                            break;
                        case "salesrank:":
                            data.setSalesrank(Long.parseLong(value));
                            break;
                        case "similar:":
                            data.setSimilar(Integer.parseInt(value.split("\\s+", 2)[0]));
                            break;
                        case "categories:":
                            data.setCategories(Integer.parseInt(value));
                            break;
                        case "reviews:":
                            data.setReviews(Integer.parseInt(value.split("\\s+")[1]));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        AmazonMetaData[] ret = new AmazonMetaData[list.size()];
        return list.toArray(ret);
    }
}
