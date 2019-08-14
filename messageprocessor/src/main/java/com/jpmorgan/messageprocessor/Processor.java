/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.messageprocessor;

/**
 *
 * @author David Reoch
 */
public interface Processor {
    
    public void read();
    public void process(String input);
}
