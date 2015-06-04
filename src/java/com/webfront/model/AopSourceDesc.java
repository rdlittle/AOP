/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author rlittle
 */
public class AopSourceDesc {
    
    private String userName;
    private String countryCode;
    private String commissionType;
    private ArrayList<Customer> custList;
    private String pcId;
    private String pcType;
    private String pcHome;
    private String sponsor;
    private String orderTotal;
    private Date orderDate;
    private String ibv;
    private String cashback;
    private String vendorId;
    private String vendorDiv;

    public AopSourceDesc() {
        pcId = "";
        pcType = "";
        pcHome = "";
        orderTotal = "";
        LocalDate ld = LocalDate.now();
        ld = ld.minusDays(30);
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
        orderDate = cal.getTime();
        ibv = "";
        cashback = "";
        sponsor = "";
        custList = new ArrayList<>();
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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the commissionType
     */
    public String getCommissionType() {
        return commissionType;
    }

    /**
     * @param commissionType the commissionType to set
     */
    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    /**
     * @return the vendorId
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return the vendorDiv
     */
    public String getVendorDiv() {
        return vendorDiv;
    }

    /**
     * @param vendorDiv the vendorDiv to set
     */
    public void setVendorDiv(String vendorDiv) {
        this.vendorDiv = vendorDiv;
    }

    /**
     * @return the custList
     */
    public ArrayList<Customer> getCustList() {
        return custList;
    }

    /**
     * @param custList the custList to set
     */
    public void setCustList(ArrayList<Customer> custList) {
        this.custList = custList;
    }
    
}
