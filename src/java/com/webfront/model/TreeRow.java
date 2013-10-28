/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import java.io.Serializable;

/**
 *
 * @author rlittle
 */
public class TreeRow implements Serializable {
    private String id;
    private String batchId;
    private String payingId;
    private String payingIdName;    
    private String filingId;
    private String ibv;
    private String commission;
    private String vendorOrderNum;
    private String vendorOrderDate;
    private String description;
    private String processDate;
    private String orderCount;
    private String errorCount;
    private String appliedAmount;
    private boolean psApproval;
    private String psApprovalName;
    private String psApprovalDate;
    private String number;
    private String divisionPlacement;
    private boolean isBatch;
    private boolean isOrder;
    private String maOrderRef;


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
     * @return the batchId
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    /**
     * @return the payingId
     */
    public String getPayingId() {
        return payingId;
    }

    /**
     * @param payingId the payingId to set
     */
    public void setPayingId(String payingId) {
        this.payingId = payingId;
    }

    /**
     * @return the filingId
     */
    public String getFilingId() {
        return filingId;
    }

    /**
     * @param filingId the filingId to set
     */
    public void setFilingId(String filingId) {
        this.filingId = filingId;
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
     * @return the commission
     */
    public String getCommission() {
        return commission;
    }

    /**
     * @param commission the commission to set
     */
    public void setCommission(String commission) {
        this.commission = commission;
    }

    /**
     * @return the vendorOrderNum
     */
    public String getVendorOrderNum() {
        return vendorOrderNum;
    }

    /**
     * @param vendorOrderNum the vendorOrderNum to set
     */
    public void setVendorOrderNum(String vendorOrderNum) {
        this.vendorOrderNum = vendorOrderNum;
    }

    /**
     * @return the vendorOrderDate
     */
    public String getVendorOrderDate() {
        return vendorOrderDate;
    }

    /**
     * @param vendorOrderDate the vendorOrderDate to set
     */
    public void setVendorOrderDate(String vendorOrderDate) {
        this.vendorOrderDate = vendorOrderDate;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the processDate
     */
    public String getProcessDate() {
        return processDate;
    }

    /**
     * @param processDate the processDate to set
     */
    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    /**
     * @return the orderCount
     */
    public String getOrderCount() {
        return orderCount;
    }

    /**
     * @param orderCount the orderCount to set
     */
    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    /**
     * @return the errorCount
     */
    public String getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount the errorCount to set
     */
    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * @return the appliedAmount
     */
    public String getAppliedAmount() {
        return appliedAmount;
    }

    /**
     * @param appliedAmount the appliedAmount to set
     */
    public void setAppliedAmount(String appliedAmount) {
        this.appliedAmount = appliedAmount;
    }
    
    public void setPsApproval(boolean b) {
        this.psApproval=b;
    }
    
    public boolean getPsApproval() {
        return this.psApproval;
    }

    /**
     * @return the psApprovalName
     */
    public String getPsApprovalName() {
        return psApprovalName;
    }

    /**
     * @param psApprovalName the psApprovalName to set
     */
    public void setPsApprovalName(String psApprovalName) {
        this.psApprovalName = psApprovalName;
    }

    /**
     * @return the psApprovalDate
     */
    public String getPsApprovalDate() {
        return psApprovalDate;
    }

    /**
     * @param psApprovalDate the psApprovalDate to set
     */
    public void setPsApprovalDate(String psApprovalDate) {
        this.psApprovalDate = psApprovalDate;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the divisionPlacement
     */
    public String getDivisionPlacement() {
        return divisionPlacement;
    }

    /**
     * @param divisionPlacement the divisionPlacement to set
     */
    public void setDivisionPlacement(String divisionPlacement) {
        this.divisionPlacement = divisionPlacement;
    }

    /**
     * @return the isBatch
     */
    public boolean isIsBatch() {
        return isBatch;
    }

    /**
     * @param isBatch the isBatch to set
     */
    public void setIsBatch(boolean isBatch) {
        this.isBatch = isBatch;
    }

    /**
     * @return the isOrder
     */
    public boolean isIsOrder() {
        return isOrder;
    }

    /**
     * @param isOrder the isOrder to set
     */
    public void setIsOrder(boolean isOrder) {
        this.isOrder = isOrder;
    }

    /**
     * @return the maOrderRef
     */
    public String getMaOrderRef() {
        return maOrderRef;
    }

    /**
     * @param maOrderRef the maOrderRef to set
     */
    public void setMaOrderRef(String maOrderRef) {
        this.maOrderRef = maOrderRef;
    }

    /**
     * @return the payingIdName
     */
    public String getPayingIdName() {
        return payingIdName;
    }

    /**
     * @param payingIdName the payingIdName to set
     */
    public void setPayingIdName(String payingIdName) {
        this.payingIdName = payingIdName;
    }
    
}
