package com.jpmorgan.messageprocessor;

import com.jpmorgan.objects.Sale;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author David Reoch
 */
public class SalesReport implements Report {

    private int salesCount;
    private List<Sale> sales;
    final private Map<String, Double> resultSet = new HashMap<>();

    private double sumSales(final Sale sale) {

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

    /**
     * generates a report from the supplied input file containing sales
     */
    @Override
    public void generateReport() {

        sales.forEach((sale) -> {
            resultSet.put(sale.getType(), sumSales(sale));
        });

        System.out.println("____TOTAL NUMBER OF SALES PER ITEM______");
        resultSet.entrySet().forEach((entry) -> {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        });
    }

    @Override
    public void print() {
        System.out.println("____ADJUSTMENTSMADE______");
        sales.forEach(sale -> {
            if (sale.getAdjustment() != null) {
                System.out.println("Adjustment was made to " + sale.getAdjustment() + " from all products of type: " + sale.getType() + " at the value of: " + sale.getCost());
            }
        });
    }

    /**
     * Performs an addition, subtraction, multiplication adjustment for each sale of a given type
     * @param sale
     */
    @Override
    public void performAdjustment(final Sale sale) {
        if (sale.getAdjustment() != null && sale.getAdjustment().equals("add")) {
            sales.stream().forEach(s -> {
                if (s.getAdjustment() == null) {
                    if (s.getType().equals(sale.getType())) {
                        s.setCost(sale.getCost() + s.getCost());
                    }
                }
            });

        }
        else if (sale.getAdjustment() != null && sale.getAdjustment().equals("subtract")) {
            sales.stream().forEach(s -> {
                if (s.getAdjustment() == null) {
                    if (s.getType().equals(sale.getType())) {
                        s.setCost(s.getCost() - sale.getCost());
                    }
                }
            });
        }
        else if (sale.getAdjustment() != null && sale.getAdjustment().equals("multiply")) {
            sales.stream().forEach(s -> {
                if (s.getAdjustment() == null) {
                    if (s.getType().equals(sale.getType())) {
                        s.setCost(s.getCost() * sale.getCost());
                    }
                }
            });
        }
    }

    /**
     * Gets the sales results 
     * @return Returns a map containing the sales
     */
    @Override
    public Map<String, Double> getResult() {
        return resultSet;
    }

    public SalesReport(List<Sale> sales) {
        this.sales = sales;
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
}
