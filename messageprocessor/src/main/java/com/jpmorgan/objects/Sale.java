/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpmorgan.objects;

/**
 *
 * @author David Reoch
 */
public final class Sale {

    private String type;
    private double cost;
    private Integer amount;
    private String adjustment;

    public Sale(String input) {
        if (input != null || !"".equals(input)) {
            String[] line = input.split("\\|");

            if (line.length == 2) {
                this.setType(line[0]);
                this.setCost(Double.parseDouble(line[1]));
            }
            else if (line.length == 3) {
                this.setType(line[0]);
                this.setCost(Double.parseDouble(line[1]));
                this.setAmount(Integer.parseInt(line[2]));
            } else if (line.length == 4){
                this.setType(line[1]);
                this.setCost(Double.parseDouble(line[2]));
                this.setAdjustment(line[3]);
            }
            else {
                System.out.println("Message in incorrect format!");
            }
        }
        else {
            System.out.println("Message is null!");
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    public String getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(String adjustment) {
        this.adjustment = adjustment;
    }
}
