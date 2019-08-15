package com.jpmorgan.messageprocessor;

/**
 * Interface for processors
 * @author David Reoch
 */
public interface Processor {

    /**
     * This method reads each line from the input file for processing
     */
    public void read();

    /**
     * This method processes each sale and performs the necessary operations on
     * each one
     *
     * @param input single line of input representing a sale
     */
    public void process(String input);
}
