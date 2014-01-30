/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class WebDEBean implements Serializable {

    private RedObject rbo;
    private LinkedList<SelectItem> vendorList;
    private int todayInternal;
    private String todayExternal;
    private LinkedList<SelectItem> currencyTypes;
    private LinkedList<SelectItem> countryCodes;
    private LinkedList<SelectItem> mappedFields;
    private ArrayList<SelectItem> accessMethods;
    private ArrayList<SelectItem> fileFormats;
    private String userId;

    /**
     * Creates a new instance of WebDEBean
     */
    public WebDEBean() {
        todayInternal = 1;
        setTodayInternal(1);
        setRbo(new RedObject("WDE", "AOP:Forms"));
        setVendorList(new LinkedList<SelectItem>());
        setCurrencyTypes(new LinkedList<SelectItem>());
        setCountryCodes(new LinkedList<SelectItem>());
        setMappedFields(new LinkedList<SelectItem>());
        setAccessMethods(new ArrayList<SelectItem>());
        setFileFormats(new ArrayList<SelectItem>());
    }

    /**
     * @return the rbo
     */
    public RedObject getRbo() {
        return this.rbo;
    }

    /**
     * @param rbo the rbo to set
     */
    public void setRbo(RedObject rbo) {
        this.rbo = rbo;
    }

    /**
     * @return the vendorNames
     */
    public LinkedList<SelectItem> getVendorList() {
        return vendorList;
    }

    /**
     * @param vendors the vendorNames to set
     */
    public void setVendorList(LinkedList<SelectItem> vendors) {
        try {
            getRbo().callMethod("getVendorList");
            UniDynArray vendorNames = getRbo().getPropertyToDynArray("vendorNameList");
            UniDynArray vendorIds = getRbo().getPropertyToDynArray("vendorId");
            int vals = vendorNames.dcount(1);
            SelectItem defaultItem = new SelectItem("-1", "Select Vendor");
            vendors.add(defaultItem);
            for (int i = 1; i <= vals; i++) {
                String str = vendorNames.extract(1, i).toString();
                String vid = vendorIds.extract(1, i).toString();
                vendors.add(new SelectItem(vid, str));
            }
            this.vendorList = vendors;
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the todayInternal
     */
    public int getTodayInternal() {
        if (todayInternal == 1) {
            setTodayInternal(1);
        }
        return todayInternal;
    }

    public void setTodayInternal(int idate) {
        try {
            setRbo(new RedObject("WDE", "AOP:Utils"));
            getRbo().setProperty("oDate", "");
            getRbo().callMethod("getIDate");
            String iDate = getRbo().getProperty("iDate");
            todayInternal = Integer.parseInt(iDate);
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the todayExternal
     */
    public String getTodayExternal() {
        try {
            setRbo(new RedObject("WDE", "AOP:Utils"));
            getRbo().setProperty("iDate", Integer.toString(todayInternal));
            getRbo().callMethod("getODate");
            String oDate = getRbo().getProperty("oDate");
            setTodayExternal(oDate);
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return todayExternal;
    }

    /**
     * @param todayExternal the todayExternal to set
     */
    public void setTodayExternal(String todayExternal) {
        this.todayExternal = todayExternal;
    }

    /**
     * @return the currencyTypes
     */
    public LinkedList<SelectItem> getCurrencyTypes() {
        return currencyTypes;
    }

    public LinkedList<SelectItem> getCountryCodes() {
        return countryCodes;
    }

    public LinkedList<SelectItem> getMappedFields() {
        return mappedFields;
    }

    public ArrayList<SelectItem> getAccessMethods() {
        return accessMethods;
    }

    /**
     * @param currencyTypes the currencyTypes to set
     */
    public void setCurrencyTypes(LinkedList<SelectItem> currencyTypes) {
        try {
            RedObject rb = new RedObject("WDE", "UTILS:Files");
            rb.setProperty("fileName", "PARAMS");
            rb.setProperty("id", "LOCALE.INFO");
            rb.setProperty("keyField", "7");
            rb.setProperty("valueField", "8");
            rb.callMethod("getSelectObject");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            SelectItem defaultItem = new SelectItem("-1", "- Select -");
            currencyTypes.add(defaultItem);
            for (int i = 1; i <= vals; i++) {
                String key = keys.extract(1, i).toString();
                String value = values.extract(1, i).toString();
                currencyTypes.add(new SelectItem(key, value));
            }
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.currencyTypes = currencyTypes;
    }

    public void setCountryCodes(LinkedList<SelectItem> cc) {
        try {
            RedObject rb = new RedObject("WDE", "UTILS:Files");
            rb.setProperty("fileName", "PARAMS");
            rb.setProperty("id", "LOCALE.INFO");
            rb.setProperty("keyField", "1");
            rb.setProperty("valueField", "4");
            rb.callMethod("getSelectObject");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            SelectItem defaultItem = new SelectItem("-1", "- Select -");
            cc.add(defaultItem);
            for (int i = 1; i <= vals; i++) {
                String key = keys.extract(1, i).toString();
                String value = values.extract(1, i).toString();
                cc.add(new SelectItem(key, value));
            }
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.countryCodes = cc;
    }

    public void setMappedFields(LinkedList<SelectItem> mf) {
        try {
            RedObject rb = new RedObject("WDE", "UTILS:Files");
            rb.setProperty("fileName", "PARAMS");
            rb.setProperty("id", "DATA.FEED.COLUMN.NAMES");
            rb.setProperty("keyField", "2");
            rb.setProperty("valueField", "1");
            rb.callMethod("getSelectObject");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            SelectItem defaultItem = new SelectItem("-1", "- Select -");
            mf.add(defaultItem);
            for (int i = 1; i <= vals; i++) {
                String key = keys.extract(1, i).toString();
                String value = values.extract(1, i).toString();
                mf.add(new SelectItem(key, value));
            }
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.mappedFields = mf;
    }

    public void setAccessMethods(ArrayList<SelectItem> list) {
        try {
            RedObject rb = new RedObject("WDE", "UTILS:Files");
            rb.setProperty("fileName", "PARAMS");
            rb.setProperty("id", "DATA.FEED.ACCESS.TYPES");
            rb.setProperty("keyField", "1");
            rb.setProperty("valueField", "2");
            rb.callMethod("getSelectObject");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            for (int val = 1; val <= vals; val++) {
                SelectItem item = new SelectItem();
                String key = keys.extract(1, val).toString();
                String value = values.extract(1, val).toString();
                item.setKey(key);
                item.setValue(value);
                list.add(item);
            }

        } catch (RbException rbe) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
        this.accessMethods = list;
    }

    /**
     * @return the fileFormats
     */
    public ArrayList<SelectItem> getFileFormats() {
        return fileFormats;
    }

    /**
     * @param fileFormats the fileFormats to set
     */
    public void setFileFormats(ArrayList<SelectItem> fileFormats) {
        try {
            RedObject rb = new RedObject("WDE", "UTILS:Files");
            rb.setProperty("fileName", "PARAMS");
            rb.setProperty("id", "DATA.FEED.FORMATS");
            rb.setProperty("keyField", "1");
            rb.setProperty("valueField", "1");
            rb.callMethod("getSelectObject");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            for (int val = 1; val <= vals; val++) {
                SelectItem item = new SelectItem();
                String key = keys.extract(1, val).toString();
                String value = values.extract(1, val).toString();
                item.setKey(key);
                item.setValue(value);
                fileFormats.add(item);
            }

        } catch (RbException rbe) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
        this.fileFormats = fileFormats;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        if(userId == null || userId.isEmpty()) {
            try {
                setRbo(new RedObject("WDE", "AOP:Utils"));
                getRbo().callMethod("getLogName");
                userId = getRbo().getProperty("logName");
                setUserId(userId);
            } catch (RbException ex) {
                Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
