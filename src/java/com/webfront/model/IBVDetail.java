/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import com.webfront.controller.IBVDetailController;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class IBVDetail {

    private IBVDetailController controller;
    private String id;
    private String ibvMasterId;
    private String storeName;
    private String subVendorId;
    private String createDate;
    private String storeId;
    private String tieredCommissionKeys;
    private String siteCountry;
    private String defaultCommission;
    private String defaultCommissionType;
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

    public IBVDetail() {
        this.controller = new IBVDetailController();
    }
    
    public void changeIbvMaster(AjaxBehaviorEvent event) {
        
    }
    
    public void changeDetail(AjaxBehaviorEvent event) {
        System.out.println(getId());
    }
    
    public void changeStore(AjaxBehaviorEvent event) {
        IBVDetail newStore = getController().getIbvDetail(getId());
        if(newStore != null) {
            setIbvMasterId(newStore.getIbvMasterId());
            setStoreName(newStore.getStoreName());
            setSubVendorId(newStore.getSubVendorId());
            setCreateDate(newStore.getCreateDate());
            setStoreId(newStore.getStoreId());
            setIsActive(newStore.isActive);
            setSiteCountry(newStore.getSiteCountry());
            setCurrencyType(newStore.getCurrencyType());            
            setDefaultIBV(newStore.defaultIBV);
            setDefaultCommission(newStore.getDefaultCommission());
            setDefaultCommissionType(newStore.getDefaultCommissionType());
            setIbvTermsId(newStore.getIbvTermsId());
            setCbTermsId(newStore.getCbTermsId());
            setCbExclude(newStore.isCbExclude());
            setTieredCommissionKeys(newStore.getTieredCommissionKeys());
            setIsTiered(newStore.isTiered());
            setMinPay(newStore.minPay);
            setMaxPay(newStore.maxPay);
            setIbvTerms(newStore.getIbvTerms());
            setCbTerms(newStore.getCbTerms());
        }
    }    
    /**
     * @return the controller
     */
    public IBVDetailController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(IBVDetailController controller) {
        this.controller = controller;
    }

    /**
     * @return the ibvMasterId
     */
    public String getIbvMasterId() {
        return ibvMasterId;
    }

    /**
     * @param ibvMasterId the ibvMasterId to set
     */
    public void setIbvMasterId(String ibvMasterId) {
        this.ibvMasterId = ibvMasterId;
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
    public String getDefaultCommission() {
        return defaultCommission;
    }

    /**
     * @param defaultCommission the defaultCommission to set
     */
    public void setDefaultCommission(String defaultCommission) {
        this.defaultCommission = defaultCommission;
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
        this.tieredCommissionKeys = tieredCommissionKeys;
    }

    /**
     * @return the defaultCommissionType
     */
    public String getDefaultCommissionType() {
        return defaultCommissionType;
    }

    /**
     * @param defaultCommissionType the defaultCommissionType to set
     */
    public void setDefaultCommissionType(String defaultCommissionType) {
        this.defaultCommissionType = defaultCommissionType;
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
        this.cbTerms = cbTerms;
    }
    
}
