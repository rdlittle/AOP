/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import com.webfront.util.DateUtils;
import com.webfront.util.MVUtils;
import java.util.Date;

/**
 *
 * @author rlittle
 */
public class TieredCommission {

    private String id;
    private String vendorId;
    private String storeId;
    private Float minSale;
    private Float commAmt;
    private Date startDate;
    private Date endDate;

    public TieredCommission() {
        id = "";
        vendorId = "";
        storeId = "";
        minSale = new Float(0);
        commAmt = new Float(0);
        startDate = null;
        endDate = null;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the storeId
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the minSale
     */
    public Float getMinSale() {
        return minSale;
    }

    /**
     * @param minSale the minSale to set
     */
    public void setMinSale(Float minSale) {
        this.minSale = minSale;
    }

    public void setMinSale(String amt) {
        this.minSale = Float.valueOf(amt);
    }

    /**
     * @return the commAmt
     */
    public Float getCommAmt() {
        return commAmt;
    }

    /**
     * @param commAmt the commAmt to set
     */
    public void setCommAmt(Float commAmt) {
        this.commAmt = commAmt;
    }

    /**
     *
     * @param amt
     */
    public void setCommAmt(String amt) {
        this.commAmt = Float.valueOf(amt);
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    public String getStartDateAsString() {
        return DateUtils.asString(startDate);
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(String sdate) {
        this.startDate = MVUtils.oConvDate(sdate);
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    public String getEndDateAsString() {
        return DateUtils.asString(endDate);
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(String edate) {
        this.endDate = MVUtils.oConvDate(edate);
    }

}
