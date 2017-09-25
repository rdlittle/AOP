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
public class CommissionTier {
    private String tierKey;
    private String payType;
    private String ibv;
    private String cashback;
    private String commission;
    private String maProfit;
    private String tierTitle;
    private String ibvRate;
    private String cbRate;

    public CommissionTier() {
        tierKey = "";
        payType = "";
        ibv = "";
        cashback = "";
        commission = "";
        maProfit = "";
        tierTitle = "";
        ibvRate = "";
        cbRate = "";
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
     * @return the payType
     */
    public String getPayType() {
        return payType;
    }

    /**
     * @param payType the payType to set
     */
    public void setPayType(String payType) {
        this.payType = payType;
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
     * @return the maProfit
     */
    public String getMaProfit() {
        return maProfit;
    }

    /**
     * @param maProfit the maProfit to set
     */
    public void setMaProfit(String maProfit) {
        this.maProfit = maProfit;
    }

    /**
     * @return the tierTitle
     */
    public String getTierTitle() {
        return tierTitle;
    }

    /**
     * @param tierTitle the tierTitle to set
     */
    public void setTierTitle(String tierTitle) {
        this.tierTitle = tierTitle;
    }

    /**
     * @return the ibvRate
     */
    public String getIbvRate() {
        return ibvRate;
    }

    /**
     * @param ibvRate the ibvRate to set
     */
    public void setIbvRate(String ibvRate) {
        this.ibvRate = ibvRate;
    }

    /**
     * @return the cbRate
     */
    public String getCbRate() {
        return cbRate;
    }

    /**
     * @param cbRate the cbRate to set
     */
    public void setCbRate(String cbRate) {
        this.cbRate = cbRate;
    }
    
}
