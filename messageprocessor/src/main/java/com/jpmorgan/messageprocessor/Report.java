package com.jpmorgan.messageprocessor;

import com.jpmorgan.objects.Sale;
import java.util.Map;

/**
 *
 * @author David Reoch
 */
public interface Report {
    
    public void generateReport();
    
    public void print();
    
    public Map<String, Double> getResult();
    
    public void performAdjustment(Sale sale);
    
}
