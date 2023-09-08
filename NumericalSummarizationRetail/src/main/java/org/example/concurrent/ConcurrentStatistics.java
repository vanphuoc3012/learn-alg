package org.example.concurrent;

import org.example.common.Invoice;
import org.example.common.Record;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcurrentStatistics {
    private static final String UK = "the United Kingdom";

    public static void customerFromUnitedKingdom(List<Record> records) {
        System.out.println("*********************************************");
        System.out.println("Customers from United Kingdom");
        Map<String, List<Record>> map = records.parallelStream()
                                               .filter(r -> r.getCountry().equals(UK))
                                               .collect(Collectors.groupingByConcurrent(
                                                       Record::getCustomer));

        map.forEach((k, v) -> System.out.println(k + ": " + v.size()));
        System.out.println("*********************************************");
    }

    public static void quantityFromUnitedKingdom(List<Record> records) {
        System.out.println("*********************************************");
        System.out.println("Quantity from United Kingdom");
        DoubleSummaryStatistics statistics = records.parallelStream()
                                                    .filter(r -> r.getCountry().equals(UK))
                                                    .collect(Collectors.summarizingDouble(
                                                            Record::getQuantity));
        System.out.println("Min: " + statistics.getMin());
        System.out.println("Max: " + statistics.getMax());
        System.out.println("Average: " + statistics.getAverage());
        System.out.println("*********************************************");
    }

    public static void countriesForProduct(List<Record> records) {
        System.out.println("*********************************************");
        System.out.println("Country for product 85123A");
        records.parallelStream()
               .filter(r -> r.getStockCode().equals("85123A"))
               .map(r -> r.getCountry())
               .distinct()
               .sorted()
               .forEachOrdered(System.out::println);
        System.out.println("*********************************************");
    }

    public static void quantityForProduct(List<Record> records) {
        System.out.println("*********************************************");
        System.out.println("Quantity for product 85123A");
        IntSummaryStatistics statistics = records.parallelStream()
                                                 .filter(r -> r.getStockCode().equals("85123A"))
                                                 .mapToInt(r -> r.getQuantity())
                                                 .summaryStatistics();
        System.out.println("Max quantity: " + statistics.getMax());
        System.out.println("Min" + " quantity: " + statistics.getMin());
        System.out.println("*********************************************");
    }

    public static void multipleFilterData(List<Record> records) {
        System.out.println("*********************************************");
        System.out.println("Multiple filter with Predicate");

        Predicate<Record> p1 = r -> r.getQuantity() > 50;
        Predicate<Record> p2 = r -> r.getUnitPrice() > 10;

        Predicate<Record> pred = Stream.of(p1, p2).reduce(Predicate::or).get();
        long value = records.parallelStream().filter(pred).count();
        System.out.println("Number of products: " + value);
        System.out.println("*********************************************");
    }

    public static void getBiggestInvoiceAmounts(List<Record> records) {
        System.out.println("*********************************************");
        System.out.println("Biggest Invoice Amounts");

        ConcurrentMap<String, List<Record>> map = records.stream()
                                                         .unordered()
                                                         .parallel()
                                                         .collect(Collectors.groupingByConcurrent(
                                                                 r -> r.getId()));
        ConcurrentLinkedDeque<Invoice> invoices = new ConcurrentLinkedDeque<>();
        map.values().parallelStream().forEach(list -> {
            Invoice invoice = new Invoice();
            invoice.setId(list.get(0).getId());
            double amount = list.stream()
                                .mapToDouble(r -> r.getUnitPrice() * r.getQuantity())
                                .sum();
            invoice.setAmount(amount);
            invoice.setCustomerId(list.get(0).getCustomer());

            invoices.add(invoice);
        });

        System.out.println("Invoices: " + invoices.size() + ": " + map.getClass());
        invoices.stream()
                .sorted(Comparator.comparingDouble(Invoice::getAmount).reversed())
                .limit(10)
                .forEachOrdered(i -> System.out.println(
                        "Customer: " + i.getCustomerId() + "; Amount: " + i.getAmount()));
        System.out.println("*********************************************");
    }

    public static void productsBetween1and10(List<Record> records) {
        System.out.println("*********************************************");
        System.out.println("Products between 1 and 10");
        int count = records.stream()
                           .unordered()
                           .parallel()
                           .filter(r -> r.getUnitPrice() >= 1 && r.getUnitPrice() <= 10)
                           .distinct()
                           .mapToInt(a -> 1)
                           .reduce(0, Integer::sum);
        System.out.println("Products between 1 and 10: " + count);
        System.out.println("*********************************************");
    }
}
