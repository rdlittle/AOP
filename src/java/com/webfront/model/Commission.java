/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author rlittle
 */
public class Commission {
    private String createDate;
    private String userId;
    private String sabpType;
    private String excludeType;
    private String commissionType;
    private String ibv;
    private String commission;
    private String cashback;
    private String maProfit;
    private boolean tiered;
    
    private ArrayList<CommissionTier> tiers;
    
    public Commission() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String str = ctx.getCurrentPhaseId().getName();
//        Logger.getLogger(Commission.class.getName()).log(Level.INFO, "Commission() "+str);
        createDate = "";
        userId = "";
        tiers = new ArrayList<>();
        sabpType = "";
        excludeType = "";
        ibv = "";
        commission = "";
        cashback = "";
        maProfit = "";
        commissionType = "";
        tiered = false;
    }
    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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
     * @return the tiers
     */
    public ArrayList<CommissionTier> getTiers() {
        return tiers;
    }

    /**
     * @param tiers the tiers to set
     */
    public void setTiers(ArrayList<CommissionTier> tiers) {
        this.tiers = tiers;
    }

    /**
     * @return the sabpType
     */
    public String getSabpType() {
        return sabpType;
    }

    /**
     * @param sabpType the sabpType to set
     */
    public void setSabpType(String sabpType) {
        this.sabpType = sabpType;
    }

    /**
     * @return the excludeType
     */
    public String getExcludeType() {
        return excludeType;
    }

    /**
     * @param excludeType the excludeType to set
     */
    public void setExcludeType(String excludeType) {
        this.excludeType = excludeType;
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
     * @return the tiered
     */
    public boolean isTiered() {
        return tiered;
    }

    /**
     * @param tiered the tiered to set
     */
    public void setTiered(boolean tiered) {
        this.tiered = tiered;
    }
    
}
