/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.SelectItem;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class VendorDetailBean {

    private String vendorMasterId;
    private String vendorDetailId;
    private String storeName;

    private ArrayList<SelectItem> storeList;
    private ArrayList<SelectItem> minPayList;
    private ArrayList<SelectItem> maxPayList;

    /**
     * @return the storeList
     */
    public ArrayList<SelectItem> getStoreList() {
        return storeList;
    }

    public void changeMasterId(AjaxBehaviorEvent event) {
        setStoreList(new ArrayList<SelectItem>());
        setMinPayList(new ArrayList<SelectItem>());
        setMaxPayList(new ArrayList<SelectItem>());
    }

    /**
     * @param storeList the storeList to set
     */
    public void setStoreList(ArrayList<SelectItem> storeList) {
        RedObject rb = new RedObject("WDE", "Vendor:Detail");
        if(this.storeList != null) {
            if(!this.storeList.isEmpty()) {
                this.storeList.clear();
            }
        }
        rb.setProperty("vendorMasterId", vendorMasterId);
        try {
            rb.callMethod("getIbvDetailList");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            for (int val = 1; val <= vals; val++) {
                String key = keys.extract(1, val).toString();
                String value = values.extract(1, val).toString();
                storeList.add(new SelectItem(key, value));
            }
        } catch (RbException ex) {
            Logger.getLogger(VendorDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.storeList = storeList;
    }
    
    

    /**
     * @return the vendorMasterId
     */
    public String getVendorMasterId() {
        return vendorMasterId;
    }

    /**
     * @param vendorMasterId the vendorMasterId to set
     */
    public void setVendorMasterId(String vendorMasterId) {
        this.vendorMasterId = vendorMasterId;
    }

    /**
     * @return the vendorDetailId
     */
    public String getVendorDetailId() {
        return vendorDetailId;
    }

    /**
     * @param vendorDetailId the vendorDetailId to set
     */
    public void setVendorDetailId(String vendorDetailId) {
        this.vendorDetailId = vendorDetailId;
    }

    /**
     * @return the minPayList
     */
    public ArrayList<SelectItem> getMinPayList() {
        return minPayList;
    }

    /**
     * @param minPayList the minPayList to set
     */
    public void setMinPayList(ArrayList<SelectItem> minPayList) {
        RedObject rb = new RedObject("WDE", "UTILS:Files");
        try {
            rb.setProperty("fileName", "PARAMS");
            rb.setProperty("id", "TC.CODES");
            rb.setProperty("keyField", "1");
            rb.setProperty("valueField", "2");
            rb.callMethod("getSelectObject");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            for(int val=1; val<=vals; val++) {
                String key=keys.extract(1, val).toString();
                String value=values.extract(1, val).toString();
                minPayList.add(new SelectItem(key,value));
            }
        } catch (RbException ex) {
            Logger.getLogger(VendorDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.minPayList = minPayList;
    }

    /**
     * @return the maxPayList
     */
    public ArrayList<SelectItem> getMaxPayList() {
        return maxPayList;
    }

    /**
     * @param maxPayList the maxPayList to set
     */
    public void setMaxPayList(ArrayList<SelectItem> maxPayList) {
        RedObject rb = new RedObject("WDE", "UTILS:Files");
        try {
            rb.setProperty("fileName", "PARAMS");
            rb.setProperty("id", "TC.CODES");
            rb.setProperty("keyField", "3");
            rb.setProperty("valueField", "4");
            rb.callMethod("getSelectObject");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            for(int val=1; val<=vals; val++) {
                String key=keys.extract(1, val).toString();
                String value=values.extract(1, val).toString();
                maxPayList.add(new SelectItem(key,value));
            }            
        } catch (RbException ex) {
            Logger.getLogger(VendorDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.maxPayList = maxPayList;
    }

    public void changeStore(AjaxBehaviorEvent event) {
        RedObject rbo = new RedObject("WDE", "Vendor:Detail");
        rbo.setProperty("id", getVendorDetailId());
        try {
            rbo.callMethod("getIbvDetailRec");
            String str=rbo.getProperty("storeName");
            this.storeName=str;
        } catch (RbException ex) {
            Logger.getLogger(VendorDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
