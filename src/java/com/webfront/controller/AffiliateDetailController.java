/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.controller;

import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.beans.WebDEBean;
import com.webfront.model.AffiliateDetail;
import com.webfront.model.ErrorObject;
import com.webfront.model.SelectItem;
import com.webfront.model.UVException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author rlittle
 */
@ManagedBean(name = "affiliateDetailController")
@SessionScoped
public class AffiliateDetailController implements Serializable {

    private RedObject rb;
    private ArrayList<SelectItem> storeList;

    public AffiliateDetailController() {
        this.rb = new RedObject("WDE", "Affiliates:Detail");
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
        this.storeList = storeList;
    }

    public AffiliateDetail getAffiliateDetail(String id) throws RbException,UVException {
        AffiliateDetail newStore = new AffiliateDetail();
        try {
            getRb().setProperty("id", id);
            getRb().callMethod("getDetail");
            String errStat = getRb().getProperty("errStat");
            String errCode = getRb().getProperty("errCode");
            String errMsg = getRb().getProperty("errMsg");
            if (errStat.equals("-1")) {
                ErrorObject eObj = new ErrorObject(errStat, errCode, errMsg);
                throw new UVException(eObj);
            } else {
                newStore.setAffiliateMasterId(getRb().getProperty("masterId"));
                newStore.setStoreName(getRb().getProperty("storeName"));
                newStore.setSubVendorId(getRb().getProperty("subVendorId"));
                newStore.setCreateDate(getRb().getProperty("createDate"));
                newStore.setStoreId(getRb().getProperty("storeId"));
                newStore.setIsActive(!"".equals(getRb().getProperty("isActive").toString()));
                newStore.setCurrencyType(getRb().getProperty("currencyType"));
                newStore.setTieredCommissionKeys(getRb().getProperty("tieredCommissionKeys"));
                newStore.setSiteCountry(getRb().getProperty("siteCountry"));
                newStore.setDefaultIBV(getRb().getProperty("defaultIBV"));
                newStore.setCommission(getRb().getProperty("defaultCommission"));
                newStore.setCommissionType(getRb().getProperty("defaultCommissionType"));
                newStore.setIbvTermsId(getRb().getProperty("ibvTermsId"));
                newStore.setCbTermsId(getRb().getProperty("cbTermsId"));
                newStore.setCbExclude("1".equals(getRb().getProperty("cbExclude")));
                newStore.setIsTiered(!"".equals(newStore.getTieredCommissionKeys()));
                newStore.setMinPay(getRb().getProperty("minPay"));
                newStore.setMaxPay(getRb().getProperty("maxPay"));
                newStore.setIbvTerms(getRb().getProperty("ibvTerms"));
                newStore.setCbTerms(getRb().getProperty("cbTerms"));
                newStore.setDisplayIBV("1".equals(getRb().getProperty("displayIBV")));
                newStore.setDisplayCB("1".equals(getRb().getProperty("displayCB")));
                newStore.setThreshhold(getRb().getProperty("threshhold"));
                newStore.setIbvOnGiftCard("1".equals(getRb().getProperty("ibvOnGiftCard")));
                System.out.println("AffiliateDetailController.getAffiliateDetail(): " + newStore.toString());
            }
        } catch (RbException rbe) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
            throw rbe;
        }
        return newStore;
    }

    public void setAffiliateDetail(AffiliateDetail rec) throws RbException, UVException {
        try {
            getRb().setProperty("id", rec.getId());
            getRb().setProperty("affiliateMasterId", rec.getAffiliateMasterId());
            getRb().setProperty("storeName", rec.getStoreName());
            getRb().setProperty("subVendorId", rec.getSubVendorId());
            getRb().setProperty("createDate", rec.getCreateDate());
            getRb().setProperty("storeId", rec.getStoreId());
            getRb().setProperty("isActive", rec.isIsActive() ? "1" : "0");
            getRb().setProperty("siteCountry", rec.getSiteCountry());
            getRb().setProperty("currencyType", rec.getCurrencyType());
            getRb().setProperty("defaultIBV", rec.getDefaultIBV());
            getRb().setProperty("defaultCommission", rec.getCommission());
            getRb().setProperty("defaultCommissionType", rec.getCommissionType());
            getRb().setProperty("ibvTermsId", rec.getIbvTermsId());
            getRb().setProperty("cbTermsId", rec.getCbTermsId());
            getRb().setProperty("cbExclude", rec.isCbExclude() ? "1" : "0");
            getRb().setProperty("tieredCommissionKeys", rec.getTieredCommissionKeys());
            getRb().setProperty("minPay", rec.getMinPay());
            getRb().setProperty("maxPay", rec.getMaxPay());
            getRb().setProperty("displayIBV", rec.isDisplayIBV() ? "1" : "0");
            getRb().setProperty("displayCB", rec.isDisplayCB() ? "1" : "0");
            getRb().setProperty("threshhold", rec.getThreshhold());
            getRb().setProperty("ibvTerms", rec.getIbvTerms());
            getRb().setProperty("cbTerms", rec.getCbTerms());
            getRb().setProperty("ibvOnGiftCard", rec.isIbvOnGiftCard() ? "1" : "0");
            getRb().callMethod("setAffiliateDetail");
            String errStat = getRb().getProperty("errStat");
            String errCode = getRb().getProperty("errCode");
            String errMsg = getRb().getProperty("errMsg");
            if (errStat.equals("-1")) {
                ErrorObject eObj = new ErrorObject(errStat, errCode, errMsg);
                throw new UVException(eObj);
            }
        } catch (RbException rbe) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
            throw rbe;
        }
    }

}
