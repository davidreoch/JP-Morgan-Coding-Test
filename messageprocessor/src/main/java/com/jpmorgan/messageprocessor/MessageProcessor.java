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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 *
 * @author David Reoch
 */
public class MessageProcessor implements Processor {

    private Report report;
    private Properties prop;
    private String filename;
    private String fileDirectory;
    private List<Sale> sales;
    private int salesCount = 0;
    private int totalSalesCount = 0;

    public MessageProcessor(Properties prop) {
        sales = new ArrayList();
        report = new Report(sales);
        this.prop = prop;
        this.filename = prop.getProperty("inbound.messages.filename");
        this.fileDirectory = prop.getProperty("inbound.messages.directory");
        read();
    }

    public void process(String input) {
        Sale sale = new Sale(input);
        salesCount++;
        sales.add(sale);
        
        
        if(sale.getAdjustment() != null && sale.getAdjustment().equals("add")){
            sales.stream().forEach(s -> {
                if(s.getType().equals(sale.getType())){
                    s.setCost(sale.getCost() + s.getCost());
                }
            });
        }
        
        if(salesCount == 10){
            totalSalesCount += salesCount;
            salesCount = 0;
            report.generateReport();
        }else if(totalSalesCount == 50){
            System.out.println("System is pausing");
            report.printAdjustments();
            System.exit(0);
        }
    }

    public void read() {
        String fileName = this.fileDirectory + File.separator + this.filename;
        System.out.println(filename);

        try {
            Stream<String> lines = Files.lines(Paths.get(fileName)).skip(1);
            lines.forEach(input -> process(input));
            lines.close();
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }

}
