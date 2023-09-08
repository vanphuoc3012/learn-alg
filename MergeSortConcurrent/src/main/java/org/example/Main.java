package org.example;

import org.example.common.AmazonMetaData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader("data/amazon-meta.txt"));
                BufferedWriter out = new BufferedWriter(new FileWriter("data/output.csv"))
        ) {

            List<AmazonMetaData> dataList = new ArrayList<>();
            AmazonMetaData data = new AmazonMetaData();
            String line;
            int skipRow = 6;
            while ((line = reader.readLine()) != null) {
                if (skipRow >= 0) {
                    skipRow--;
                    continue;
                }
                if (line.isEmpty()) {
                    dataList.add(data);
                    String csvLine = createCsvLine(data);
                    out.write(csvLine);
                    out.newLine();
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
    }

    private static String createCsvLine(AmazonMetaData data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getId())
          .append(",")
          .append(data.getASIN())
          .append(",")
          .append(data.getTitle())
          .append(",")
          .append(data.getGroup())
          .append(",")
          .append(data.getSalesrank())
          .append(",")
          .append(data.getSimilar())
          .append(",")
          .append(data.getCategories())
          .append(",")
          .append(data.getReviews());
        return sb.toString();
    }
}