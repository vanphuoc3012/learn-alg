package com.phuoc.learn.alg.concurrent.chap03.loader;

import com.phuoc.learn.alg.concurrent.chap03.knn.data.BankMarketing;
import com.phuoc.learn.alg.concurrent.chap03.knn.loader.BankMarketingLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BankMarketingLoaderTest {

    @Test
    void testLoadData() {
        BankMarketingLoader bankMarketingLoader = new BankMarketingLoader();
        String url = "bankdata/bank.data";
        List<BankMarketing> loadedData = bankMarketingLoader.load(url);

        System.out.println("Size: " + loadedData.size());

        Assertions.assertThat(loadedData.size()).isPositive();
    }
}