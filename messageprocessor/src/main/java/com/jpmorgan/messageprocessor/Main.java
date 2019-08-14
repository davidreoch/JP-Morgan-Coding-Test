/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.messageprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Reoch
 */
public class Main {

    private static final String propertyFileName = "config.properties";

    public static void main(String[] args) {
        System.out.println();

        try (InputStream input = new FileInputStream(System.getProperty("user.dir") + File.separator + propertyFileName)) {
            Properties prop = new Properties();
            prop.load(input);
            
            Report report = new Report();
            MessageProcessor mp = new MessageProcessor(prop, report);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
