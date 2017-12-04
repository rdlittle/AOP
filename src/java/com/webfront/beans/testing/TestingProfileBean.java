/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans.testing;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author rlittle
 */
@ManagedBean(name = "testingProfileBean")
@SessionScoped
public class TestingProfileBean {

    private String userId;
    private String ordersPerCust;
    private String itemsPerOrder;
    private String refundType;
    private String pcList;
    private String aggregatorId;
    private String storeList;
    private String tierList;
    private String message;
    private boolean newProfile;
    private boolean changed;
    private String profile;
    private String buttonText;
    private boolean hasFormData;

    public TestingProfileBean() {
        userId = "";
        ordersPerCust = "";
        itemsPerOrder = "";
        refundType = "";
        pcList = "";
        aggregatorId = "";
        storeList = "";
        tierList = "";
        message = "";
        newProfile = true;
        changed = false;
        hasFormData = false;
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        Logger.getLogger(TestingProfileBean.class.getName()).log(Level.INFO,"TestingProfileBean(): "+ctx.getCurrentPhaseId().getName());
    }

    @PostConstruct
    public void init() {
//        FacesContext ctx = FacesContext.getCurrentInstance();
//        Logger.getLogger(TestingProfileBean.class.getName()).log(Level.INFO,"TestingProfileBean.init(): "+ctx.getCurrentPhaseId().getName());
    }

    public String getProfile() {
        RedObject rbo = new RedObject("WDE", "AFFILIATE:Testing");
        rbo.setProperty("userId", userId);
        try {
            rbo.callMethod("getAffiliateTestingProfile");
            String errStatus = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMessage = rbo.getProperty("svrMessage");
            String errName = rbo.getProperty("svrCtrlName");
            if ("-1".equals(errStatus)) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else if (errName.equalsIgnoreCase("errNoItems")) {
                newProfile = true;
            } else {
                newProfile = false;
                this.userId = userId;
                this.ordersPerCust = rbo.getProperty("ordersPerCust");
                this.itemsPerOrder = rbo.getProperty("itemsPerOrder");
                this.refundType = rbo.getProperty("refundType");
                UniDynArray work;
                work = rbo.getPropertyToDynArray("pcList");
                int vals = work.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    this.pcList = work.extract(1, val).toString();
                    if (val < vals) {
                        this.pcList += ",";
                    }
                }
                this.aggregatorId = rbo.getProperty("aggregatorId");
                work = rbo.getPropertyToDynArray("storeList");
                vals = work.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    this.storeList = work.extract(1, val).toString();
                    if (val < vals) {
                        this.storeList += ",";
                    }
                }
                work = rbo.getPropertyToDynArray("tierList");
                vals = work.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    this.tierList = work.extract(1, val).toString();
                    if (val < vals) {
                        this.tierList += ",";
                    }
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(TestingProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void setProfile() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        RedObject rbo = new RedObject("WDE", "AFFILIATE:Testing");
        rbo.setProperty("userId", this.userId);
        rbo.setProperty("ordersPerCust", this.ordersPerCust);
        rbo.setProperty("itemsPerOrder", this.itemsPerOrder);
        rbo.setProperty("refundType", this.refundType);
        rbo.setProperty("pcList", this.pcList);
        rbo.setProperty("aggregatorId", this.aggregatorId);
        rbo.setProperty("storeList", this.storeList);
        rbo.setProperty("tierList", this.tierList);
        try {
            rbo.callMethod("setAffiliateTestingProfile");
            String errStatus = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMessage = rbo.getProperty("svrMessage");
            String errName = rbo.getProperty("svrCtrlName");
            if ("-1".equals(errStatus)) {
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else {
                setChanged(false);
                FacesMessage msg = new FacesMessage(rbo.getProperty("message"));
                ctx.addMessage(null, msg);
            }
        } catch (RbException ex) {
            Logger.getLogger(TestingProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String setTestData() {
        RedObject rbo = new RedObject("WDE", "AFFILIATE:Testing");
        rbo.setProperty("userId", this.userId);
        rbo.setProperty("ordersPerCust", this.ordersPerCust);
        rbo.setProperty("itemsPerOrder", this.itemsPerOrder);
        rbo.setProperty("refundType", this.refundType);
        rbo.setProperty("pcList", this.pcList);
        rbo.setProperty("aggregatorId", this.aggregatorId);
        rbo.setProperty("storeList", this.storeList);
        rbo.setProperty("tierList", this.tierList);
        try {
            rbo.callMethod("setAffiliateTestData");
        } catch (RbException ex) {
            Logger.getLogger(TestingProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/affiliate/queue/aopQueue.xhtml?faces-redirect=true";
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
     * @return the ordersPerCust
     */
    public String getOrdersPerCust() {
        return ordersPerCust;
    }

    /**
     * @param ordersPerCust the ordersPerCust to set
     */
    public void setOrdersPerCust(String ordersPerCust) {
        this.ordersPerCust = ordersPerCust;
    }

    /**
     * @return the itemsPerOrder
     */
    public String getItemsPerOrder() {
        return itemsPerOrder;
    }

    /**
     * @param itemsPerOrder the itemsPerOrder to set
     */
    public void setItemsPerOrder(String itemsPerOrder) {
        this.itemsPerOrder = itemsPerOrder;
    }

    /**
     * @return the refundType
     */
    public String getRefundType() {
        return refundType;
    }

    /**
     * @param refundType the refundType to set
     */
    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    /**
     * @return the pcList
     */
    public String getPcList() {
        return pcList;
    }

    /**
     * @param pcList the pcList to set
     */
    public void setPcList(String pcList) {
        this.pcList = pcList;
    }

    /**
     * @return the aggregatorId
     */
    public String getAggregatorId() {
        return aggregatorId;
    }

    /**
     * @param aggregatorId the aggregatorId to set
     */
    public void setAggregatorId(String aggregatorId) {
        this.aggregatorId = aggregatorId;
    }

    /**
     * @return the storeList
     */
    public String getStoreList() {
        return storeList;
    }

    /**
     * @param storeList the storeList to set
     */
    public void setStoreList(String storeList) {
        this.storeList = storeList;
    }

    /**
     * @return the tierList
     */
    public String getTierList() {
        return tierList;
    }

    /**
     * @param tierList the tierList to set
     */
    public void setTierList(String tierList) {
        this.tierList = tierList;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the newProfile
     */
    public boolean isNewProfile() {
        return newProfile;
    }

    /**
     * @param newProfile the newProfile to set
     */
    public void setNewProfile(boolean newProfile) {
        this.newProfile = newProfile;
    }

    /**
     * @return the changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * @param changed the changed to set
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void changeListener() {
        setChanged(true);
    }

    public String getButtonText() {
        if (newProfile || changed) {
            return "Save";
        }
        return "Submit";
    }

    public boolean getHasFormData() {
        if ("".equals(this.userId)) {
            return false;
        }
        if ("".equals(this.ordersPerCust)) {
            return false;
        }
        if ("".equals(this.itemsPerOrder)) {
            return false;
        }
        if ("".equals(this.refundType)) {
            return false;
        }
        if ("".equals(this.pcList)) {
            return false;
        }
        if ("".equals(this.aggregatorId)) {
            return false;
        }
        return !"".equals(this.storeList);
    }

}
