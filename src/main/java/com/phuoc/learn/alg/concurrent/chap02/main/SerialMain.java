package com.phuoc.learn.alg.concurrent.chap02.main;

import com.phuoc.learn.alg.concurrent.chap02.data.BankMarketing;
import com.phuoc.learn.alg.concurrent.chap02.loader.BankMarketingLoader;
import com.phuoc.learn.alg.concurrent.chap02.serial.KnnClassifier;

import java.util.Date;
import java.util.List;

public class SerialMain {
    public static void main(String[] args) {
        BankMarketingLoader loader = new BankMarketingLoader();
        List<BankMarketing> train = loader.load("bankdata/bank.data");
        System.out.println("Train: " + train.size());
        List<BankMarketing> test = loader.load("bankdata/bank.test");
        System.out.println("Test: " + test.size());

        double currentTime = 0d;
        int success = 0, mistakes = 0;

        int k = 10;
        if (args.length == 1) {
            k = Integer.parseInt(args[0]);
        }

        KnnClassifier classifier = new KnnClassifier(train, k);
        try {
            Date start, end;
            start = new Date();

            for (BankMarketing example : test) {
                String tag = classifier.classify(example);

                if (tag.equals(example.getTag())) {
                    success++;
                } else {
                    mistakes++;
                }
            }
            end = new Date();
            currentTime = end.getTime() - start.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("******************************************");
        System.out.println("Serial Classifier - K: " + k);
        System.out.println("Success: " + success);
        System.out.println("Mistakes: " + mistakes);
        System.out.println("Execution Time: " + (currentTime / 1000) + " seconds.");
        System.out.println("******************************************");
    }
}
