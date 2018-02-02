/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans.testing;

import asjava.uniclientlibs.UniDynArray;
import asjava.uniclientlibs.UniStringException;
import asjava.uniobjects.UniObjectsTokens;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.beans.StoreBean;
import com.webfront.model.SelectItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class TestingProfileBean {

    private String userId;
    private String ordersPerCust;
    private String itemsPerOrder;
    private String refundType;
    private String pcList;
    private String aggregatorId;
//    private String storeList;
    private String tierList;
    private String message;
    private boolean newProfile;
    private boolean changed;
    private String profile;
    private String buttonText;
    private boolean hasFormData;
    @ManagedProperty(value = "#{storeBean}")
    private StoreBean storeBean;
    private ArrayList<SelectItem> storeList;
    private ArrayList<SelectItem> storeSelector;
    private SelectItemConverter storeConverter;

    public TestingProfileBean() {
        userId = "";
        ordersPerCust = "";
        itemsPerOrder = "";
        refundType = "";
        pcList = "";
        aggregatorId = "";
        tierList = "";
        message = "";
        newProfile = true;
        changed = false;
        hasFormData = false;
        storeList = new ArrayList<>();
        storeSelector = new ArrayList<>();
        storeConverter = new SelectItemConverter();
    }

    @PostConstruct
    public void init() {
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
                storeList.clear();
                storeSelector.clear();
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
                this.storeBean.setAggregatorId(this.aggregatorId);
                storeSelector = this.storeBean.getStoreList();
                work = rbo.getPropertyToDynArray("storeList");
                vals = work.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    String sid = work.extract(1, val).toString();
                    for (SelectItem s : storeSelector) {
                        if (s.getKey().equalsIgnoreCase(sid)) {
                            storeList.add(s);
                            break;
                        }
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
        rbo.setProperty("storeList", listToString(storeList));
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

    public void setTestData() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        RedObject rbo = new RedObject("WDE", "AFFILIATE:Testing");

        rbo.setProperty("userId", this.userId);
        rbo.setProperty("ordersPerCust", this.ordersPerCust);
        rbo.setProperty("itemsPerOrder", this.itemsPerOrder);
        rbo.setProperty("refundType", this.refundType);
        rbo.setProperty("pcList", this.pcList);
        rbo.setProperty("aggregatorId", this.aggregatorId);
        rbo.setProperty("storeList", listToString(this.storeList));
        
        rbo.setProperty("tierList", this.tierList);
        try {
            rbo.callMethod("setAffiliateTestData");
            String errStatus = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMessage = rbo.getProperty("svrMessage");
            String errName = rbo.getProperty("svrCtrlName");
            if ("-1".equals(errStatus)) {
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else {
                setChanged(false);
                String outcome = "/affiliate/queue/aopQueue.xhtml?faces-redirect=true";
                ctx.getApplication().getNavigationHandler().handleNavigation(ctx, null, outcome);
            }
        } catch (RbException ex) {
            Logger.getLogger(TestingProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public ArrayList<SelectItem> getStoreList() {
        return storeList;
    }

    /**
     * @param storeList the storeList to set
     */
    public void setStoreList(ArrayList<SelectItem> storeList) {
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

    /**
     * @return the storeBean
     */
    public StoreBean getStoreBean() {
        return storeBean;
    }

    /**
     * @param storeBean the storeBean to set
     */
    public void setStoreBean(StoreBean storeBean) {
        this.storeBean = storeBean;
    }

    /**
     * @return the storeSelector
     */
    public ArrayList<SelectItem> getStoreSelector() {
        return storeSelector;
    }

    public void aggregatorListener() {
        this.storeBean.setAggregatorId(this.aggregatorId);
        storeSelector = this.storeBean.getStoreList();
    }

    /**
     * @param storeSelector the storeSelector to set
     */
    public void setStoreSelector(ArrayList<SelectItem> storeSelector) {
        this.storeSelector = storeSelector;
    }

    private String listToString(ArrayList<SelectItem> list) {
        String s = "";
        Iterator<SelectItem> liter = list.iterator();
        while(liter.hasNext()) {
            SelectItem si = liter.next();
            s = s.concat(si.getKey());
            if(liter.hasNext()) {
                s = s.concat(",");
            }
        }
        return s;
    }

    /**
     * @return the storeConverter
     */
    public SelectItemConverter getStoreConverter() {
        return storeConverter;
    }

    @FacesConverter(forClass = SelectItem.class)
    public class SelectItemConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            for (SelectItem si : storeSelector) {
                if (si.getKey().equals(value)) {
                    return si;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            for (SelectItem si : storeSelector) {
                SelectItem store = (SelectItem) value;
                if (store.equals(si)) {
                    String label = si.getLabel();
                    String key = si.getKey();
                    return key;
                }
            }
            return null;
        }

    }
}
