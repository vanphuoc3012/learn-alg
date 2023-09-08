package org.example.concurrent;

import org.example.common.Record;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConcurrentMain {
    static Map<String, List<Double>> totalTimes = new LinkedHashMap<>();
    static List<Record> records;

    private static void measure(String name, Runnable r) {
        long start = System.nanoTime();
        r.run();
        long end = System.nanoTime();
        totalTimes.computeIfAbsent(name, k -> new ArrayList<>()).add((end - start) / 1_000_000.0);
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("data/Online_Retail.csv");
        records = ConcurrentDataLoader.load(path);

        for (int i = 0; i < 10; i++) {
            measure("Customers from United Kingdom",
                    () -> ConcurrentStatistics.customerFromUnitedKingdom(records));
            measure("Quantity from United Kingdom",
                    () -> ConcurrentStatistics.quantityFromUnitedKingdom(records));
            measure("Country for product 85123A",
                    () -> ConcurrentStatistics.countriesForProduct(records));
            measure("Quantity for product 85123A",
                    () -> ConcurrentStatistics.quantityForProduct(records));
            measure("Multiple filter with Predicate",
                    () -> ConcurrentStatistics.multipleFilterData(records));
            measure("Biggest Invoice Amounts",
                    () -> ConcurrentStatistics.getBiggestInvoiceAmounts(records));
            measure("Products between 1 and 10",
                    () -> ConcurrentStatistics.productsBetween1and10(records));
        }

        totalTimes.forEach((name, times) -> {
            System.out.printf("%25s: %s [avg: %6.2f] ms%n", name, times.stream()
                                                                       .map(t -> String.format(
                                                                               "%6.2f", t))
                                                                       .collect(Collectors.joining(
                                                                               " ")), times.stream()
                                                                                           .mapToDouble(
                                                                                                   Double::doubleValue)
                                                                                           .average()
                                                                                           .getAsDouble());
        });
    }
}
