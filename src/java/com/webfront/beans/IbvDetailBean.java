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
public class IbvDetailBean {

    private String ibvMasterId;
    private String ibvDetailId;

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
        rb.setProperty("ibvMasterId", ibvMasterId);
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
            Logger.getLogger(IbvDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.storeList = storeList;
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
     * @return the ibvDetailId
     */
    public String getIbvDetailId() {
        return ibvDetailId;
    }

    /**
     * @param ibvDetailId the ibvDetailId to set
     */
    public void setIbvDetailId(String ibvDetailId) {
        this.ibvDetailId = ibvDetailId;
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
            Logger.getLogger(IbvDetailBean.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(IbvDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.maxPayList = maxPayList;
    }
}
