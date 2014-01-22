/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.controller;

import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.beans.WebDEBean;
import com.webfront.model.IBVDetail;
import com.webfront.model.SelectItem;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rlittle
 */
public class IBVDetailController {

    private RedObject rb;
    private ArrayList<SelectItem> storeList;

    public IBVDetailController() {
        this.rb = new RedObject("WDE", "Vendor:Detail");
    }

    /**
     * @return the rb
     */
    public RedObject getRb() {
        return rb;
    }

    /**
     * @param rb the rb to set
     */
    public void setRb(RedObject rb) {
        this.rb = rb;
    }

    /**
     * @return the storeList
     */
    public ArrayList<SelectItem> getStoreList() {
        return storeList;
    }

    /**
     * @param storeList the storeList to set
     */
    public void setStoreList(ArrayList<SelectItem> storeList) {
        getRb().setProperty("ibvMasterId", "LS010000");
        this.storeList = storeList;
    }

    public IBVDetail getIbvDetail(String id) {
        IBVDetail newStore = new IBVDetail();
        try {
            getRb().setProperty("id", id);
            getRb().callMethod("getIbvDetailRec");
            String errStat = getRb().getProperty("errStat");
            String errCode = getRb().getProperty("errCode");
            String errMsg = getRb().getProperty("errMsg");
            newStore.setIbvMasterId(getRb().getProperty("ibvMasterId"));
            newStore.setStoreName(getRb().getProperty("storeName"));
            newStore.setSubVendorId(getRb().getProperty("subVendorId"));
            newStore.setCreateDate(getRb().getProperty("createDate"));
            newStore.setStoreId(getRb().getProperty("storeId"));
            newStore.setIsActive(!"".equals(getRb().getProperty("isActive").toString()));
            newStore.setCurrencyType(getRb().getProperty("currencyType"));
            newStore.setTieredCommissionKeys(getRb().getProperty("tieredCommissionKeys"));
            newStore.setSiteCountry(getRb().getProperty("siteCountry"));
            newStore.setDefaultIBV(getRb().getProperty("defaultIBV"));
            newStore.setDefaultCommission(getRb().getProperty("defaultCommission"));
            newStore.setDefaultCommissionType(getRb().getProperty("defaultCommissionType"));
            newStore.setIbvTermsId(getRb().getProperty("ibvTermsId"));
            newStore.setCbTermsId(getRb().getProperty("cbTermsId"));
            newStore.setCbExclude("1".equals(getRb().getProperty("cbExclude")));
            newStore.setIsTiered(!"".equals(newStore.getTieredCommissionKeys()));
            newStore.setMinPay(getRb().getProperty("minPay"));
            newStore.setMaxPay(getRb().getProperty("maxPay"));
            newStore.setIbvTerms(getRb().getProperty("ibvTerms"));
            newStore.setCbTerms(getRb().getProperty("cbTerms"));
        } catch (RbException rbe) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
        return newStore;
    }
    
}
