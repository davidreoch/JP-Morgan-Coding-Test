package com.jpmorgan.messageprocessor;

import com.jpmorgan.objects.Sale;
import java.util.Map;

/**
 *
 * @author David Reoch
 */
public interface Report {

    /**
     * Generates a report from the supplied input file containing sales
     */
    public void generateReport();

    /**
     * Prints the results of the processing
     */
    public void print();

    /**
     * Gets the sales results
     *
     * @return Returns a map containing the sales
     */
    public Map<String, Double> getResult();

    /**
     * Performs an addition, subtraction, multiplication adjustment for each
     * sale of a given type
     *
     * @param sale sale object containing the adjustment information
     */
    public void performAdjustment(Sale sale);

}
