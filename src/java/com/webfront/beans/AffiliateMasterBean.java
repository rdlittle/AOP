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
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author rlittle
 */
@Named("affiliateMasterBean")
@ApplicationScoped
public class AffiliateMasterBean implements Serializable {

    private RedObject rbo;
    private LinkedList<SelectItem> affiliateMasterList;
    private ArrayList<SelectItem> networkList;
    private int todayInternal;
    private String todayExternal;
    private LinkedList<SelectItem> mappedFields;
    private ArrayList<SelectItem> accessMethods;
    private ArrayList<SelectItem> fileFormats;
    private String networkId;
    private String userId;
    private String countryCode;
    private String commType;

    /**
     * Creates a new instance of WebDEBean
     */
    public AffiliateMasterBean() {
        todayInternal = 1;
        rbo = new RedObject("WDE", "Affiliates:Master");
        affiliateMasterList = new LinkedList<>();
        networkList = new ArrayList<>();
        mappedFields = new LinkedList<>();
        accessMethods = new ArrayList<>();
        fileFormats = new ArrayList<>();
        networkId = "";
        commType = "";
        countryCode = "";
    }

    public void init() {
        System.out.println("AffiliateMasterBean.init()");
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
    public LinkedList<SelectItem> getAffiliateMasterList() {
        LinkedList<SelectItem> list = new LinkedList<>();
        try {
            if (networkId != null) {
                getRbo().setProperty("networkId", networkId);
            }
            if (countryCode != null) {
                getRbo().setProperty("country", countryCode);
            }
            if (commType != null) {
                getRbo().setProperty("type", commType);
            }
            getRbo().callMethod("getMasterList");
            int errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else {
                UniDynArray vendorNames = getRbo().getPropertyToDynArray("affiliateName");
                UniDynArray vendorIds = getRbo().getPropertyToDynArray("masterId");
                int vals = vendorNames.dcount(1);
                SelectItem defaultItem = new SelectItem("-1", "Select Affiliate");
                list.add(defaultItem);
                for (int i = 1; i <= vals; i++) {
                    String str = vendorNames.extract(1, i).toString();
                    String vid = vendorIds.extract(1, i).toString();
                    list.add(new SelectItem(vid, str));
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateMasterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        affiliateMasterList = list;
        return affiliateMasterList;
    }

    /**
     * @param vendors the vendorNames to set
     */
    public void setAffiliateMasterList(LinkedList<SelectItem> vendors) {
        try {
            getRbo().callMethod("getMasterList");
            UniDynArray vendorNames = getRbo().getPropertyToDynArray("affiliateName");
            UniDynArray vendorIds = getRbo().getPropertyToDynArray("masterId");
            int vals = vendorNames.dcount(1);
            SelectItem defaultItem = new SelectItem("-1", "Select Affiliate");
            vendors.add(defaultItem);
            for (int i = 1; i <= vals; i++) {
                String str = vendorNames.extract(1, i).toString();
                String vid = vendorIds.extract(1, i).toString();
                vendors.add(new SelectItem(vid, str));
            }
            this.affiliateMasterList = vendors;
        } catch (RbException ex) {
            Logger.getLogger(AffiliateMasterBean.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AffiliateMasterBean.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AffiliateMasterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return todayExternal;
    }

    /**
     * @param todayExternal the todayExternal to set
     */
    public void setTodayExternal(String todayExternal) {
        this.todayExternal = todayExternal;
    }

    public LinkedList<SelectItem> getMappedFields() {
        return mappedFields;
    }

    public ArrayList<SelectItem> getAccessMethods() {
        return accessMethods;
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
            Logger.getLogger(AffiliateMasterBean.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AffiliateMasterBean.class.getName()).log(Level.SEVERE, null, rbe);
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
            Logger.getLogger(AffiliateMasterBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
        this.fileFormats = fileFormats;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        if (userId == null || userId.isEmpty()) {
            try {
                setRbo(new RedObject("WDE", "AOP:Utils"));
                getRbo().callMethod("getLogName");
                userId = getRbo().getProperty("logName");
                setUserId(userId);
            } catch (RbException ex) {
                Logger.getLogger(AffiliateMasterBean.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * @return the networkList
     */
    public ArrayList<SelectItem> getNetworkList() {
        if (networkList == null) {
            this.networkList = new ArrayList<SelectItem>();
        }
        return networkList;
    }

    /**
     * @param networkList the networkList to set
     */
    public void setNetworkList(ArrayList<SelectItem> networkList) {
        try {
            RedObject networkRbo = new RedObject("WDE", "Affiliates:Network");
            networkRbo.callMethod("getNetworkList");
            UniDynArray networkIds = networkRbo.getPropertyToDynArray("networkId");
            UniDynArray networkNames = networkRbo.getPropertyToDynArray("networkName");
            int vals = networkNames.dcount(1);
            for (int i = 1; i <= vals; i++) {
                String netId = networkIds.extract(1, i).toString();
                String netName = networkNames.extract(1, i).toString();
                networkList.add(new SelectItem(netId, netName));
            }
            this.networkList = networkList;
        } catch (RbException ex) {
            Logger.getLogger(AffiliateMasterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the commType
     */
    public String getCommType() {
        return commType;
    }

    /**
     * @param commType the commType to set
     */
    public void setCommType(String commType) {
        this.commType = commType;
    }
}
