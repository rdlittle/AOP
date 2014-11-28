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
public class CheckReconcilliation {
    
    private String networkId;
    private String affiliateMasterId;
    private String storeId;
    private String postDate;
    private String payingId;
    private String amount;

    public CheckReconcilliation() {
        
    }
    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * @return the affiliateMasterId
     */
    public String getAffiliateMasterId() {
        return affiliateMasterId;
    }

    /**
     * @param affiliateMasterId the affiliateMasterId to set
     */
    public void setAffiliateMasterId(String affiliateMasterId) {
        this.affiliateMasterId = affiliateMasterId;
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
     * @return the postDate
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(String postDate) {
        this.postDate = postDate;
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
    
}
