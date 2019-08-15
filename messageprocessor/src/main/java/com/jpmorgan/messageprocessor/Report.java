/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.messageprocessor;

import com.jpmorgan.objects.Sale;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author David Reoch
 */
public class Report {

    private int salesCount;
    private List<Sale> sales;
    private Map<String, Integer> salesMap;
    private Map<String, Double> costMap;
    private Map<String, Integer> occurenceMap;

    public Report(List<Sale> sales) {
        this.sales = sales;
        salesMap = new HashMap<>();
        costMap = new HashMap<>();
        occurenceMap = new HashMap<>();
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    private double sumSales(Sale sale) {

        double sum = sales.stream()
                .filter(s -> s.getAmount() != null)
                .filter(s -> s.getType().equals(sale.getType()))
                .mapToDouble(s -> s.getAmount() * s.getCost()).sum();

        sum += sales.stream()
                .filter(s -> s.getAmount() == null)
                .filter(s -> s.getType().equals(sale.getType()))
                .filter(s -> s.getAdjustment() == null)
                .mapToDouble(s -> s.getCost()).sum();

        return sum;
    }

    void generateReport() {
        Map<String, Double> resultSet = new HashMap<>();
        for (Sale sale : sales) {
            resultSet.put(sale.getType(), sumSales(sale));
        }

        System.out.println("____TOTAL NUMBER OF SALES PER ITEM______");
        for (Map.Entry<String, Double> entry : resultSet.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

//        Map<String, Book> result = books.stream() .collect(Collectors.toMap(Book::getISBN, b -> b));
    void printAdjustments() {
        System.out.println("____ADJUSTMENTSMADE______");
        sales.forEach(sale -> {
            if (sale.getAdjustment() != null) {
                System.out.println("Adjustment was made to " + sale.getAdjustment() + " from all products of type: " + sale.getType() + " at the value of: " + sale.getCost());
            }
        });
    }
}
