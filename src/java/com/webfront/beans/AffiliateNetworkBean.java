/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.AffiliateNetwork;
import com.webfront.model.AffiliatePayment;
import com.webfront.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class AffiliateNetworkBean implements Serializable {

    private RedObject rbo;
    private ArrayList<SelectItem> networkList;
    private int todayInternal;
    private String todayExternal;
    private LinkedList<SelectItem> currencyTypes;
    private LinkedList<SelectItem> countryCodes;
    private LinkedList<SelectItem> mappedFields;
    private ArrayList<SelectItem> accessMethods;
    private ArrayList<SelectItem> fileFormats;
    private AffiliatePayment payment;
    private String networkId;
    private String userId;
    private String masterId;
    private ArrayList<SelectItem> members;
    private SelectItem selectedItem;
    private ArrayList<SelectItem> unassignedMembers;
    private AffiliateNetwork selectedNetwork;
    private boolean selected;

    /**
     * Creates a new instance of WebDEBean
     */
    public AffiliateNetworkBean() {
        todayInternal = 1;
        setTodayInternal(1);
        setRbo(new RedObject("WDE", "Affiliates:Network"));
        setCurrencyTypes(new LinkedList<SelectItem>());
        setCountryCodes(new LinkedList<SelectItem>());
        setMappedFields(new LinkedList<SelectItem>());
        setAccessMethods(new ArrayList<SelectItem>());
        setFileFormats(new ArrayList<SelectItem>());
        unassignedMembers = new ArrayList<>();
        payment = new AffiliatePayment();
        networkId = "";
        selected = false;
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
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, rbe);
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
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, rbe);
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
                Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setNetworkList();
        return networkList;
    }

    public void onChangeNetwork(String newId) {
        if (newId != null && !"".equals(newId)) {
            setNetworkId(newId);
        }
        setMembers();
    }

    /**
     * @param networkList the networkList to set
     */
    public void setNetworkList() {
        networkList = new ArrayList<>();
        setRbo(new RedObject("WDE", "Affiliates:Network"));
        try {
            getRbo().callMethod("getNetworkList");
            UniDynArray networkIds = getRbo().getPropertyToDynArray("networkId");
            UniDynArray networkNames = getRbo().getPropertyToDynArray("networkName");
            int vals = networkNames.dcount(1);
            for (int i = 1; i <= vals; i++) {
                String netId = networkIds.extract(1, i).toString();
                String netName = networkNames.extract(1, i).toString();
                networkList.add(new SelectItem(netId, netName));
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the payment
     */
    public AffiliatePayment getPayment(String checkId) {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(AffiliatePayment payment) {
        this.payment = payment;
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
     * @return the members
     */
    public ArrayList<SelectItem> getMembers() {
        return members;
    }

    public void onChangeVendor() {
        System.out.println(masterId);
    }
    
    public void onDeleteMember() {
        if (selectedItem != null) {
            try {
                setRbo(new RedObject("WDE", "Affiliates:Network"));
                getRbo().setProperty("networkId", networkId);
                getRbo().setProperty("memberId", selectedItem.getKey());
                getRbo().setProperty("isRemoving", "1");
                getRbo().callMethod("setNetwork");
                String errStat = getRbo().getProperty("errStat");
                String errCode = getRbo().getProperty("errCode");
                String errMsg = getRbo().getProperty("errMsg");
                setMembers();
                masterId = "-1";
                selected=false;
                selectedItem = null;
            } catch (RbException rbe) {
                Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, rbe);
            }
        }        
    }
    
    public void onRowSelect(SelectEvent  se) {
        selected=true;
    }
    
    public void onRowUnselect(SelectEvent se) {
        selected=false;
    }

    public void onAddMember() {
        if (!"".equals(masterId)) {
            try {
                setRbo(new RedObject("WDE", "Affiliates:Network"));
                getRbo().setProperty("networkId", networkId);
                getRbo().setProperty("memberId", masterId);
                getRbo().setProperty("isRemoving", "");
                getRbo().callMethod("setNetwork");
                String errStat = getRbo().getProperty("errStat");
                String errCode = getRbo().getProperty("errCode");
                String errMsg = getRbo().getProperty("errMsg");
                setMembers();
                masterId = "-1";
            } catch (RbException rbe) {
                Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, rbe);
            }
        }
    }

    public void setMembers() {
        members = new ArrayList<>();
        try {
            setRbo(new RedObject("WDE", "Affiliates:Network"));
            getRbo().setProperty("networkId", networkId);
            getRbo().callMethod("getNetwork");
            String errStat = getRbo().getProperty("errStat");
            String errCode = getRbo().getProperty("errCode");
            String errMsg = getRbo().getProperty("errMsg");
            UniDynArray keys = getRbo().getPropertyToDynArray("memberId");
            UniDynArray values = getRbo().getPropertyToDynArray("memberName");
            int vals = keys.dcount(1);
            for (int val = 1; val <= vals; val++) {
                SelectItem item = new SelectItem();
                String key = keys.extract(1, val).toString();
                String value = values.extract(1, val).toString();
                item.setKey(key);
                item.setValue(value);
                members.add(item);
            }
        } catch (RbException rbe) {
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
    }

    /**
     * @return the selectedNetwork
     */
    public AffiliateNetwork getSelectedNetwork() {
        return selectedNetwork;
    }

    /**
     * @param selectedNetwork the selectedNetwork to set
     */
    public void setSelectedNetwork(AffiliateNetwork selectedNetwork) {
        this.selectedNetwork = selectedNetwork;
    }

    /**
     * @return the nonMembers
     */
    public ArrayList<SelectItem> getUnassignedMembers() {
        if (!"".equals(networkId)) {
            setUnassignedMembers(new ArrayList<SelectItem>());
        }
        return this.unassignedMembers;
    }

    /**
     * @param nonMembers the nonMembers to set
     */
    public void setUnassignedMembers(ArrayList<SelectItem> nonMembers) {
        this.unassignedMembers = new ArrayList<>();
        try {
            setRbo(new RedObject("WDE", "Affiliates:Master"));
            getRbo().setProperty("isUnassigned", "1");
            getRbo().callMethod("getMasterList");
            String errStat = getRbo().getProperty("errStat");
            String errCode = getRbo().getProperty("errCode");
            String errMsg = getRbo().getProperty("errMsg");
            UniDynArray keys = getRbo().getPropertyToDynArray("masterId");
            UniDynArray values = getRbo().getPropertyToDynArray("affiliateName");
            int vals = keys.dcount(1);
            for (int val = 1; val <= vals; val++) {
                SelectItem item = new SelectItem();
                String key = keys.extract(1, val).toString();
                String value = values.extract(1, val).toString();
                item.setKey(key);
                item.setValue(value);
                this.unassignedMembers.add(item);
            }

        } catch (RbException rbe) {
            Logger.getLogger(AffiliateNetworkBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
    }

    /**
     * @return the masterId
     */
    public String getMasterId() {
        return masterId;
    }

    /**
     * @param masterId the masterId to set
     */
    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    /**
     * @return the selectedItem
     */
    public SelectItem getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(SelectItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * @return the isSelected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param isSelected the isSelected to set
     */
    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }

}
