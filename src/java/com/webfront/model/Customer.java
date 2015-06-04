/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author rlittle
 */
public class Customer {
    private String pcId;
    private String pcType;
    private String pcHome;
    private String sponsor;
    private String amount;
    private String cashback;
    private String ibv;
    private Date orderDate;
    private String orderTotal;

    public Customer() {
        pcId = "";
        pcType = "";
        pcHome = "";
        sponsor = "";
        amount = "";
        cashback = "";
        ibv = "";
        orderDate = Calendar.getInstance(Locale.getDefault()).getTime();
        orderTotal = "";
    }
    /**
     * @return the pcId
     */
    public String getPcId() {
        return pcId;
    }

    /**
     * @param pcId the pcId to set
     */
    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the cashback
     */
    public String getCashback() {
        return cashback;
    }

    /**
     * @param cashback the cashback to set
     */
    public void setCashback(String cashback) {
        this.cashback = cashback;
    }

    /**
     * @return the ibv
     */
    public String getIbv() {
        return ibv;
    }

    /**
     * @param ibv the ibv to set
     */
    public void setIbv(String ibv) {
        this.ibv = ibv;
    }

    /**
     * @return the pcType
     */
    public String getPcType() {
        return pcType;
    }

    /**
     * @param pcType the pcType to set
     */
    public void setPcType(String pcType) {
        this.pcType = pcType;
    }

    /**
     * @return the pcHome
     */
    public String getPcHome() {
        return pcHome;
    }

    /**
     * @param pcHome the pcHome to set
     */
    public void setPcHome(String pcHome) {
        this.pcHome = pcHome;
    }

    /**
     * @return the sponsor
     */
    public String getSponsor() {
        return sponsor;
    }

    /**
     * @param sponsor the sponsor to set
     */
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    /**
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }
    
    public String dateAsString() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        return format.format(getOrderDate());
    }    

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the orderTotal
     */
    public String getOrderTotal() {
        return orderTotal;
    }

    /**
     * @param orderTotal the orderTotal to set
     */
    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }
}
