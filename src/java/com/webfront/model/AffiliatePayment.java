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
    private final String checkId;
    private final String networkId;
    private final String checkDate;
    private final String postDate;
    private final String checkAmount;
    private final String releasedAmount;
    private final String userName;
    private final String balance;
    
    public AffiliatePayment() {
        checkId = "";
        networkId = "";
        checkDate = "";
        postDate = "";
        checkAmount = "";
        releasedAmount = "";
        userName = "";
        balance = "";
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
    
}
