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

    void generateReport() {
        sales.forEach(sale -> {
            double saleTotal = 0;

            if (sale.getAmount() != null && sale.getAmount() > 0) {
                double amount = Double.valueOf(sale.getAmount());
                saleTotal = sale.getCost() * amount;
                if (!(salesMap.containsKey(sale.getType()))) {
                    salesMap.put(sale.getType(), 0);
                }
                if (!(costMap.containsKey(sale.getType()))) {
                    costMap.put(sale.getType(), 0.0);
                }
                costMap.put(sale.getType(), costMap.get(sale.getType()) + saleTotal);
                salesMap.put(sale.getType(), salesMap.get(sale.getType()) + sale.getAmount());
            }
            else if (sale.getAmount() == null) {
                if (!(salesMap.containsKey(sale.getType()))) {
                    salesMap.put(sale.getType(), 0);
                }
                salesMap.put(sale.getType(), salesMap.get(sale.getType()) + 1);

                if (!(costMap.containsKey(sale.getType()))) {
                    costMap.put(sale.getType(), 0.0);
                }
                costMap.put(sale.getType(), costMap.get(sale.getType()) + sale.getCost());
            }
        });

        System.out.println("____TOTAL NUMBER OF SALES PER ITEM______");
        for (Map.Entry<String, Integer> entry : salesMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println("____TOTAL VALUE PER ITEM______");
        for (Map.Entry<String, Double> entry : costMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

//        Map<String, Book> result = books.stream() .collect(Collectors.toMap(Book::getISBN, b -> b));
}
