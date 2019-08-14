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

    public MessageProcessor(Properties prop, Report report) {
        sales = new ArrayList();
        report = new Report();
        this.prop = prop;
        this.filename = prop.getProperty("inbound.messages.filename");
        this.fileDirectory = prop.getProperty("inbound.messages.directory");
        read();
    }

    public void process(String input) {
        Sale sale = new Sale(input);
        salesCount++;
        sales.add(sale);
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
