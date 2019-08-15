package com.jpmorgan.messageprocessor;

import com.jpmorgan.objects.Sale;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * A class containing tests for the processing of sales
 * @author David Reoch
 */
public class ReportTest {

    private Properties prop;

    public ReportTest() {
    }

    @Before
    public void setProperties() {
        prop = new Properties();
        prop.setProperty("inbound.messages.filename", "");
        prop.setProperty("inbound.messages.directory", "");
    }

    @Test
    public void testAddingBasicSalesTogether() {
        System.out.println("generateReport");
        List<Sale> sales = new ArrayList<>();
        sales = Arrays.asList(new Sale("apple|10"), new Sale("apple|10"));

        Report instance = new SalesReport(sales);
        instance.generateReport();

        double val = instance.getResult().get("apple");
        assertEquals(20, val, 0D);

        sales = Arrays.asList(new Sale("orange|1"), new Sale("orange|2"), new Sale("orange|3"), new Sale("orange|4"));
        instance = new SalesReport(sales);
        instance.generateReport();

        val = instance.getResult().get("orange");
        assertEquals(10, val, 0D);
    }

    @Test
    public void testAddingSalesWithMultiplierTogether() {
        System.out.println("generateReport");
        List<Sale> sales = new ArrayList<>();
        sales = Arrays.asList(new Sale("apple|10|10"), new Sale("apple|10|10"));

        Report instance = new SalesReport(sales);
        instance.generateReport();

        double val = instance.getResult().get("apple");
        assertEquals(200, val, 0D);

        sales = Arrays.asList(new Sale("orange|1|2"), new Sale("orange|2|2"), new Sale("orange|3|3"), new Sale("orange|4|4"));
        instance = new SalesReport(sales);
        instance.generateReport();

        val = instance.getResult().get("orange");
        assertEquals(31, val, 0D);
    }

    @Test
    public void testAdditionAdjustment() {
        System.out.println("generateReport");
        List<Sale> sales = new ArrayList<>();
        sales = Arrays.asList(new Sale("apple|10|10"), new Sale("apple|10"), new Sale("adjustment|apple|10|add"));

        Report instance = new SalesReport(sales);
        sales.forEach(sale -> {
            instance.performAdjustment(sale);
        });

        instance.generateReport();

        double val = instance.getResult().get("apple");
        assertEquals(220, val, 0D);

    }

    @Test
    public void testSubtractionAdjustment() {
        System.out.println("generateReport");
        List<Sale> sales = new ArrayList<>();
        sales = Arrays.asList(new Sale("apple|10|10"), new Sale("apple|10"), new Sale("adjustment|apple|5|subtract"));

        Report instance = new SalesReport(sales);
        sales.forEach(sale -> {
            instance.performAdjustment(sale);
        });

        instance.generateReport();

        double val = instance.getResult().get("apple");
        assertEquals(55, val, 0D);
    }

    @Test
    public void testMultiplyAdjustment() {
        System.out.println("generateReport");
        List<Sale> sales = new ArrayList<>();
        sales = Arrays.asList(new Sale("apple|10|10"), new Sale("apple|10"), new Sale("adjustment|apple|5|multiply"));

        Report instance = new SalesReport(sales);
        sales.forEach(sale -> {
            instance.performAdjustment(sale);
        });

        instance.generateReport();

        double val = instance.getResult().get("apple");
        assertEquals(550, val, 0D);
    }

}
