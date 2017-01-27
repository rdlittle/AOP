/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

/**
 *
 * @author rlittle
 */
public class QueueError {

    private String lineNumber;
    private String commKey;
    private String payingId;
    private String reportedComm;
    private String storeId;
    private String storeName;
    private String srp;
    private String orderDate;
    private String orderId;
    private String tierKey;
    private String sku;
    private String errorCode;
    private String errorText;
    private boolean disabled;

    public QueueError() {
    }
    
    /**
     * @return the lineNumber
     */
    public Integer getLineNumber() {
        return Integer.parseInt(lineNumber);
    }

    /**
     * @param lineNumber the lineNumber to set
     */
    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return the commKey
     */
    public String getCommKey() {
        return commKey;
    }

    /**
     * @param commKey the commKey to set
     */
    public void setCommKey(String commKey) {
        this.commKey = commKey;
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
     * @return the reportedComm
     */
    public String getReportedComm() {
        return reportedComm;
    }

    /**
     * @param reportedComm the reportedComm to set
     */
    public void setReportedComm(String reportedComm) {
        this.reportedComm = reportedComm;
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
     * @return the srp
     */
    public String getSrp() {
        return srp;
    }

    /**
     * @param srp the srp to set
     */
    public void setSrp(String srp) {
        this.srp = srp;
    }

    /**
     * @return the orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorText
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * @param errorText the errorText to set
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return the tierKey
     */
    public String getTierKey() {
        return tierKey;
    }

    /**
     * @param tierKey the tierKey to set
     */
    public void setTierKey(String tierKey) {
        this.tierKey = tierKey;
    }

    /**
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return the disabled
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * @param disabled the disabled to set
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
