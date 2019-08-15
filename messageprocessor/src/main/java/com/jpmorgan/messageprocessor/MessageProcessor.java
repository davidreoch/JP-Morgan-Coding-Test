/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.messageprocessor;

import com.jpmorgan.objects.Sale;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 *
 * @author David Reoch
 */
public final class MessageProcessor implements Processor {

    final private Report report;
    final private Properties prop;
    private final String filename;
    private final String fileDirectory;
    private final List<Sale> sales;
    private int salesCount = 0;
    private int totalSalesCount = 0;

    public MessageProcessor(final Properties prop) {
        sales = new ArrayList();
        report = new SalesReport(sales);
        this.prop = prop;
        this.filename = prop.getProperty("inbound.messages.filename");
        this.fileDirectory = prop.getProperty("inbound.messages.directory");
        read();
    }

    /**
     * This method reads each line from the input file for processing
     */
    @Override
    public void read() {
        final String fileName = this.fileDirectory + File.separator + this.filename;
        System.out.println(filename);

        try (Stream<String> lines = Files.lines(Paths.get(fileName)).skip(1)) {
            lines.forEach(input -> process(input));
        }
        catch (IOException ex) {
            ex.getMessage();
        }
    }

    /**
     * This method processes each sale and performs the necessary operations on each one
     * @param input single line of input representing a sale
     */
    @Override
    public void process(final String input) {
        final Sale sale = new Sale(input);
        salesCount++;
        sales.add(sale);

        report.performAdjustment(sale);

        if (salesCount == 10) {
            totalSalesCount += salesCount;
            salesCount = 0;
            report.generateReport();
        }
        else if (totalSalesCount >= 50) {
            System.out.println("System is pausing");
            report.print();
            System.exit(0);
        }
    }
}
