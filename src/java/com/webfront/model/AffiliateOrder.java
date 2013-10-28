/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.text.NumberFormat;

/**
 *
 * @author rlittle
 */
public final class AffiliateOrder {

    private String id;
    private String orderRef;
    private String filingId;
    private String payingId;
    private String payingIdName;
    private String orderTotal;
    private String ibvTotal;
    private Float commissionTotal;
    private String placementId;
    private String orderDate;
    private String vendorOrderNum;
    private String errorCount;
    private boolean hasErrors;
    private String entryDate;
    private String vendorOrderDate;
    private String payingIdAddr1;
    private String payingIdAddr2;
    private String payingIdCityStZip;
    private String storeId;
    private String storeName;
    private String vendorDiv;
    private String vendorId;
    private String errorCode;
    private String errorMessage;
    private String batchId;

    public AffiliateOrder() {
        setOrderTotal("0.00");
        setIbvTotal("0.00");
        setCommissionTotal(Float.valueOf("0.00"));
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
     * @return the reference
     */
    public String getOrderRef() {
        return orderRef;
    }

    /**
     * @param reference the reference to set
     */
    public void setOrderRef(String reference) {
        this.orderRef = reference;
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
     * @return the ibvTotal
     */
    public String getIbvTotal() {
        return ibvTotal;
    }

    /**
     * @param ibvTotal the ibvTotal to set
     */
    public void setIbvTotal(String ibvTotal) {
        this.ibvTotal = ibvTotal;
    }

    /**
     * @return the placement
     */
    public String getPlacementId() {
        return placementId;
    }

    /**
     * @param placement the placement to set
     */
    public void setPlacementId(String placement) {
        this.placementId = placement;
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
     * @return the hasErrors
     */
    public boolean isHasErrors() {
        return hasErrors;
    }

    /**
     * @param hasErrors the hasErrors to set
     */
    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    /**
     * @return the commissionTotal
     */
    public Float getCommissionTotal() {
       return commissionTotal;
    }
    
    public String getCommissionAsString() {
        NumberFormat nf=NumberFormat.getCurrencyInstance();
        
        String comm=nf.format(commissionTotal/100);
        return comm;        
    }

    /**
     * @param commissionTotal the commissionTotal to set
     */
    public void setCommissionTotal(Float commissionTotal) {
        this.commissionTotal = commissionTotal;
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

    /**
     * @return the entryDate
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate the entryDate to set
     */
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
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
     * @return the payingIdAddr1
     */
    public String getPayingIdAddr1() {
        return payingIdAddr1;
    }

    /**
     * @param payingIdAddr1 the payingIdAddr1 to set
     */
    public void setPayingIdAddr1(String payingIdAddr1) {
        this.payingIdAddr1 = payingIdAddr1;
    }

    /**
     * @return the payingIdAddr2
     */
    public String getPayingIdAddr2() {
        return payingIdAddr2;
    }

    /**
     * @param payingIdAddr2 the payingIdAddr2 to set
     */
    public void setPayingIdAddr2(String payingIdAddr2) {
        this.payingIdAddr2 = payingIdAddr2;
    }

    /**
     * @return the payingIdCityStZip
     */
    public String getPayingIdCityStZip() {
        return payingIdCityStZip;
    }

    /**
     * @param payingIdCityStZip the payingIdCityStZip to set
     */
    public void setPayingIdCityStZip(String payingIdCityStZip) {
        this.payingIdCityStZip = payingIdCityStZip;
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
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
}
