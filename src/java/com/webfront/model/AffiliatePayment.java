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
public class AffiliatePayment {
    private String id;
    private String checkId;
    private String networkId;
    private String checkDate;
    private String postDate;
    private String checkAmount;
    private String releasedAmount;
    private String userName;
    private String balance;
    
    public AffiliatePayment() {
        checkId = "";
        networkId = "";
        checkDate = "";
        postDate = "";
        checkAmount = "";
        releasedAmount = "";
        userName = "";
        balance = "";
        id = "";
    }

    /**
     * @return the checkId
     */
    public String getCheckId() {
        return checkId;
    }

    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @return the checkDate
     */
    public String getCheckDate() {
        return checkDate;
    }

    /**
     * @return the postDate
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     * @return the checkAmount
     */
    public String getCheckAmount() {
        return checkAmount;
    }

    /**
     * @return the releasedAmount
     */
    public String getReleasedAmount() {
        return releasedAmount;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the balance
     */
    public String getBalance() {
        return balance;
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
     * @param checkId the checkId to set
     */
    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * @param checkDate the checkDate to set
     */
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    /**
     * @param checkAmount the checkAmount to set
     */
    public void setCheckAmount(String checkAmount) {
        this.checkAmount = checkAmount;
    }

    /**
     * @param releasedAmount the releasedAmount to set
     */
    public void setReleasedAmount(String releasedAmount) {
        this.releasedAmount = releasedAmount;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }
    
}
