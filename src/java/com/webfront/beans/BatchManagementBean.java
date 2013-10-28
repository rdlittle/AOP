/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class BatchManagementBean implements Serializable {

    private String mgmtRole;
    private Float checkAmt;
    private String vendorId;
    private String userId;
    private int internalDate;
    private String externalDate;
    private Float balance;

    private static final Map<String, String> roleItems;
    static {
        roleItems = new LinkedHashMap<>();
        roleItems.put("PS", "PS");
        roleItems.put("QA", "QA");
    }
    
    public BatchManagementBean() {
        mgmtRole="PS";
        checkAmt=Float.valueOf("123456.78");
        balance=Float.valueOf("0.00");
    }

    public String nextPage() {
        String nextPage = "/batchManager?faces-redirect=true";
        return nextPage;
    }
    
    /**
     * @return the checkAmt
     */
    public Float getCheckAmt() {
        return checkAmt;
    }

    /**
     * @param checkAmt the checkAmt to set
     */
    public void setCheckAmt(Float checkAmt) {
        this.checkAmt = checkAmt;
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

    public Map<String, String> getMgmtRoles() {
        return roleItems;
    }
    
    public String getMgmtRole() {
        return mgmtRole;
    }
    
    public void setMgmtRole(String role) {
        this.mgmtRole=role;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the internalDate
     */
    public int getInternalDate() {
        return internalDate;
    }

    /**
     * @param internalDate the internalDate to set
     */
    public void setInternalDate(int internalDate) {
        this.internalDate = internalDate;
    }

    /**
     * @return the externalDate
     */
    public String getExternalDate() {
        return externalDate;
    }

    /**
     * @param externalDate the externalDate to set
     */
    public void setExternalDate(String externalDate) {
        this.externalDate = externalDate;
    }

    /**
     * @return the balance
     */
    public Float getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
