package com.phuoc.learn.alg.concurrent.chap02.loader;

import com.phuoc.learn.alg.concurrent.chap02.data.BankMarketing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BankMarketingLoader {

    public List<BankMarketing> load(String dataPath) {
        Path file = Paths.get(dataPath);
        List<BankMarketing> dataset = new ArrayList<>();
        try (InputStream in = Files.newInputStream(file)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                BankMarketing bankObject = new BankMarketing();
                bankObject.setData(data);
                dataset.add(bankObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataset;
    }
}
