/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AffiliateDetail implements Serializable {
    
    private String id;
    private String affiliateMasterId;
    private String storeName;
    private String subVendorId;
    private String createDate;
    private String storeId;
    private String tieredCommissionKeys;
    private String siteCountry;
    private String commission;
    private String commissionType;
    private String defaultIBV;
    private boolean isActive;
    private String ibvTermsId;
    private String cbTermsId;
    private boolean cbExclude;
    private String currencyType;
    private boolean tiered;
    private String minPay;
    private String maxPay;
    private String ibvTerms;
    private String cbTerms;
    private boolean displayIBV;
    private boolean displayCB;
    private boolean ibvOnGiftCard;
    private String threshhold;

    public AffiliateDetail() {
        this.init();
    }
    
    public final void init() {
        id="";
        affiliateMasterId="";
        storeName="";
        subVendorId="";
        createDate="";
        storeId="";
        tieredCommissionKeys="";
        siteCountry="";
        commission="";
        commissionType="";
        defaultIBV="";
        isActive=true;
        ibvTermsId="";
        cbTermsId="";
        cbExclude=false;
        minPay="";
        maxPay="";
        ibvTerms="";
        cbTerms="";
        displayIBV=true;
        displayCB=true;
        ibvOnGiftCard=false;
        threshhold="";        
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
     * @return the subVendorId
     */
    public String getSubVendorId() {
        return subVendorId;
    }

    /**
     * @param subVendorId the subVendorId to set
     */
    public void setSubVendorId(String subVendorId) {
        this.subVendorId = subVendorId;
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
     * @return the siteCountry
     */
    public String getSiteCountry() {
        return siteCountry;
    }

    /**
     * @param siteCountry the siteCountry to set
     */
    public void setSiteCountry(String siteCountry) {
        this.siteCountry = siteCountry;
    }

    /**
     * @return the defaultCommissionId
     */
    public String getCommission() {
        return commission;
    }

    /**
     * @param commission the commission to set
     */
    public void setCommission(String commission) {
        if(commission==null) {
            commission="";
        }
        this.commission = commission;
    }

    /**
     * @return the ibvTermsId
     */
    public String getIbvTermsId() {
        return ibvTermsId;
    }

    /**
     * @param ibvTermsId the ibvTermsId to set
     */
    public void setIbvTermsId(String ibvTermsId) {
        this.ibvTermsId = ibvTermsId;
    }

    /**
     * @return the cbTermsId
     */
    public String getCbTermsId() {
        return cbTermsId;
    }

    /**
     * @param cbTermsId the cbTermsId to set
     */
    public void setCbTermsId(String cbTermsId) {
        this.cbTermsId = cbTermsId;
    }

    /**
     * @return the cbExclude
     */
    public boolean isCbExclude() {
        return cbExclude;
    }

    /**
     * @param cbExclude the cbExclude to set
     */
    public void setCbExclude(boolean cbExclude) {
        this.cbExclude = cbExclude;
    }

    /**
     * @return the currencyType
     */
    public String getCurrencyType() {
        return currencyType;
    }

    /**
     * @param currencyType the currencyType to set
     */
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
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
     * @return the tiered
     */
    public boolean isTiered() {
        return tiered;
    }

    /**
     * @param tiered the tiered to set
     */
    public void setIsTiered(boolean tiered) {
        this.tiered = tiered;
    }
    public void setTiered(boolean tiered) {
        this.tiered=tiered;
    }

    /**
     * @return the tieredCommissionKeys
     */
    public String getTieredCommissionKeys() {
        return tieredCommissionKeys;
    }

    /**
     * @param tieredCommissionKeys the tieredCommissionKeys to set
     */
    public void setTieredCommissionKeys(String tieredCommissionKeys) {
        if(tieredCommissionKeys==null) {
            tieredCommissionKeys = "";
        }
        this.tieredCommissionKeys = tieredCommissionKeys;
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
        if(commissionType==null) {
            commissionType="";
        }
        this.commissionType = commissionType;
    }

    /**
     * @return the defaultIBV
     */
    public String getDefaultIBV() {
        return defaultIBV;
    }

    /**
     * @param defaultIBV the defaultIBV to set
     */
    public void setDefaultIBV(String defaultIBV) {
        this.defaultIBV = defaultIBV;
    }

    /**
     * @return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the minPay
     */
    public String getMinPay() {
        return minPay;
    }

    /**
     * @param minPay the minPay to set
     */
    public void setMinPay(String minPay) {
        if(minPay==null) {
            minPay="";
        }
        this.minPay = minPay;
    }

    /**
     * @return the maxPay
     */
    public String getMaxPay() {
        return maxPay;
    }

    /**
     * @param maxPay the maxPay to set
     */
    public void setMaxPay(String maxPay) {
        if(maxPay==null) {
            maxPay = "";
        }
        this.maxPay = maxPay;
    }

    /**
     * @return the ibvTerms
     */
    public String getIbvTerms() {
        return ibvTerms;
    }

    /**
     * @param ibvTerms the ibvTerms to set
     */
    public void setIbvTerms(String ibvTerms) {
        if(ibvTerms==null) {
            ibvTerms="";
        }
        this.ibvTerms = ibvTerms;
    }

    /**
     * @return the cbTerms
     */
    public String getCbTerms() {
        return cbTerms;
    }

    /**
     * @param cbTerms the cbTerms to set
     */
    public void setCbTerms(String cbTerms) {
        if(cbTerms==null) {
            cbTerms="";
        }
        this.cbTerms = cbTerms;
    }

    /**
     * @return the displayIBV
     */
    public boolean isDisplayIBV() {
        return displayIBV;
    }

    /**
     * @param displayIBV the displayIBV to set
     */
    public void setDisplayIBV(boolean displayIBV) {
        this.displayIBV = displayIBV;
    }

    /**
     * @return the displayCB
     */
    public boolean isDisplayCB() {
        return displayCB;
    }

    /**
     * @param displayCB the displayCB to set
     */
    public void setDisplayCB(boolean displayCB) {
        this.displayCB = displayCB;
    }

    /**
     * @return the threshhold
     */
    public String getThreshhold() {
        return threshhold;
    }

    /**
     * @param threshhold the threshhold to set
     */
    public void setThreshhold(String threshhold) {
        if(threshhold==null) {
            threshhold="0";
        }
        this.threshhold = threshhold;
    }

    /**
     * @return the ibvOnGiftCard
     */
    public boolean isIbvOnGiftCard() {
        return ibvOnGiftCard;
    }

    /**
     * @param ibvOnGiftCard the ibvOnGiftCard to set
     */
    public void setIbvOnGiftCard(boolean ibvOnGiftCard) {
        this.ibvOnGiftCard = ibvOnGiftCard;
    }
    
}
