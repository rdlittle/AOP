/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.text.DecimalFormat;

/**
 *
 * @author rlittle
 */
public class Award {

    private String orderNumber;
    private String orderSrp;
    private String awardAmt;
    private String awardPct;
    private String originalOrder;
    private String referenceNum;
    
    final DecimalFormat decFormat = new DecimalFormat("###0.00");
    
    public Award() {
        orderNumber = "";
        orderSrp = "";
        awardAmt = "";
        awardPct = "";
        originalOrder = "";
        referenceNum = "";
    }
    
    public boolean isEmpty() {
        if(orderNumber.isEmpty()) {
            if(awardAmt.isEmpty() || awardAmt.equals("0.0")) {
                if(orderSrp.isEmpty() || orderSrp.equals("0.0")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the awardAmt
     */
    public Float getAwardAmt() {
        if ("".equals(awardAmt) || awardAmt.isEmpty()) {
            return new Float(0);
        }
        return Float.parseFloat(awardAmt);
    }

    /**
     * @param awardAmt the awardAmt to set
     */
    public void setAwardAmt(String awardAmt) {
        this.awardAmt = awardAmt;
    }
    
    public void setAwardAmt(Float amt) {
        this.awardAmt = amt.toString();
    }

    /**
     *
     * @return The percentage of the original order amount this award earned
     */
    public Float getAwardPct() {
        if ("".equals(awardPct) || awardPct.isEmpty()) {
            return new Float(0);
        }
        return Float.parseFloat(awardPct);
    }
    
    public String getAwardAmtAsString() {
        return awardAmt;
    }

    /**
     * @param awardPct the awardPct to set
     */
    public void setAwardPct(String awardPct) {
        this.awardPct = awardPct;
    }

    /**
     * @return the originalOrder
     */
    public String getOriginalOrder() {
        return originalOrder;
    }

    /**
     * @param originalOrder the originalOrder to set
     */
    public void setOriginalOrder(String originalOrder) {
        this.originalOrder = originalOrder;
    }

    /**
     * @return the referenceNum
     */
    public String getReferenceNum() {
        return referenceNum;
    }

    /**
     * @param referenceNum the referenceNum to set
     */
    public void setReferenceNum(String referenceNum) {
        this.referenceNum = referenceNum;
    }

    /**
     * @return the orderSrp
     */
    public Float getOrderSrp() {
        if ("".equals(orderSrp) || orderSrp.isEmpty()) {
            return new Float(0);
        }
        return Float.parseFloat(orderSrp);
    }
    
    public String getOrderSrpAsString() {
        return orderSrp;
    }

    /**
     * @param orderSrp the orderSrp to set
     */
    public void setOrderSrp(String orderSrp) {
        this.orderSrp = orderSrp;
    }

    public void setOrderSrp(Float orderSrp) {
        this.orderSrp = orderSrp.toString();
    }
}
