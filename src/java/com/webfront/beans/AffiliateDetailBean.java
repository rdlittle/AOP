/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.controller.AffiliateDetailController;
import com.webfront.model.AffiliateDetail;
import com.webfront.model.SelectItem;
import com.webfront.model.UVException;
import com.webfront.util.JSFHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AffiliateDetailBean implements Serializable {

    private final AffiliateDetailController controller;
    private String masterId;
    private String detailId;
    private String storeName;
    private AffiliateDetail detail;

    private ArrayList<SelectItem> storeList;
    private ArrayList<SelectItem> minPayList;
    private ArrayList<SelectItem> maxPayList;
    private final ArrayList<SelectItem> commTypeList;

    private UISelectItems selItems;
    private boolean ordersOnly;

    public AffiliateDetailBean() {
        this.controller = new AffiliateDetailController();
        commTypeList = new ArrayList<>();
        commTypeList.add(new SelectItem("P", "Percentage"));
        commTypeList.add(new SelectItem("F", "Fixed"));
    }

    /**
     * @return the storeList
     */
    public ArrayList<SelectItem> getStoreList() {
        if (storeList == null || storeList.isEmpty()) {
            if (masterId != null) {
                setStoreList(null);
            }
        }
        return storeList;
    }

    public void changeMasterId(AjaxBehaviorEvent event) {
        setStoreList(new ArrayList<>());
        setMinPayList(new ArrayList<>());
        setMaxPayList(new ArrayList<>());
        detailId = "";
        detail = new AffiliateDetail();
    }

    /**
     * @param storeList the storeList to set
     */
    public void setStoreList(ArrayList<SelectItem> storeList) {
        RedObject rb = new RedObject("WDE", "Affiliates:Detail");
        if (this.storeList != null) {
            if (!this.storeList.isEmpty()) {
                this.storeList.clear();
            }
        }
        rb.setProperty("masterId", masterId);
        if (isOrdersOnly()) {
            rb.setProperty("ordersOnly", "1");
        }
        try {
            rb.callMethod("getAffiliateDetailList");
            String errStat = rb.getProperty("svrStatus");
            String errCode = rb.getProperty("svrCtrlCode");
            String errMsg = rb.getProperty("svrMessage");
            if (errStat.equals("-1")) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                String msg = "Error code " + errCode + ": " + errMsg;
                ctx.addMessage("msg", new FacesMessage(msg));
                return;
            }
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            for (int val = 1; val <= vals; val++) {
                String key = keys.extract(1, val).toString();
                String value = values.extract(1, val).toString();
                storeList.add(new SelectItem(key, value));
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.storeList = storeList;
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
     * @return the detailId
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * @param detailId the detailId to set
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
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
            for (int val = 1; val <= vals; val++) {
                String key = keys.extract(1, val).toString();
                String value = values.extract(1, val).toString();
                minPayList.add(new SelectItem(key, value));
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateDetailBean.class.getName()).log(Level.SEVERE, null, ex);
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
            for (int val = 1; val <= vals; val++) {
                String key = keys.extract(1, val).toString();
                String value = values.extract(1, val).toString();
                maxPayList.add(new SelectItem(key, value));
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.maxPayList = maxPayList;
    }

    public void changeStore(AjaxBehaviorEvent event) {
        try {
            detail = controller.getAffiliateDetail(detailId);
        } catch(UVException e) {
            JSFHelper.sendFacesMessage(e.getMessage());
        } catch (RbException ex) {
            JSFHelper.sendFacesMessage(ex.getMessage());
        }
    }
    
    public void onSaveDetail() {
        try {
            detail.setId(detailId);
            controller.setAffiliateDetail(detail);
        } catch (RbException rbe) {
            JSFHelper.sendFacesMessage(rbe.getMessage());
        } catch (UVException uve) {
            JSFHelper.sendFacesMessage(uve.getMessage());
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

    /**
     * @return the selItems
     */
    public UISelectItems getSelItems() {
        return selItems;
    }

    /**
     * @param selItems the selItems to set
     */
    public void setSelItems(UISelectItems selItems) {
        this.selItems = selItems;
    }

    /**
     * @return the ordersOnly
     */
    public boolean isOrdersOnly() {
        return ordersOnly;
    }

    /**
     * @param ordersOnly the ordersOnly to set
     */
    public void setOrdersOnly(boolean ordersOnly) {
        this.ordersOnly = ordersOnly;
    }

    /**
     * @return the commTypeList
     */
    public ArrayList<SelectItem> getCommTypeList() {
        return commTypeList;
    }

    /**
     * @return the detail
     */
    public AffiliateDetail getDetail() {
        return detail;
    }
}
