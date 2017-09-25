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
import com.webfront.model.Store;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

/**
 *
 * @author rlittle
 */
@Named("storeBean")
@ApplicationScoped
public class StoreBean {

    private Store selectedStore;
    private String aggregatorId;
    private ArrayList<SelectItem> storeList;
//    private StoreConverter converter;
    private ArrayList<SelectItem> currencyCodeList;

    private RedObject rbo;

    public StoreBean() {
        selectedStore = new Store();
        aggregatorId = "";
        storeList = new ArrayList<>();
        currencyCodeList = new ArrayList<>();
        rbo = new RedObject("WDE", "AFFILIATE:Store");
//        converter = new StoreConverter();
    }

    public void init() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        Map<String, String> map = fCtx.getExternalContext().getRequestParameterMap();
        if (map.containsKey("aggregatorId")) {
            aggregatorId = map.get("aggregatorId");
            selectedStore = new Store();
        }
    }

    public void changeStore(AjaxBehaviorEvent evt) {
        Logger.getLogger(StoreBean.class.getName()).log(Level.INFO, null, "changeStore");
        try {
            String storeId = selectedStore.getStoreId();
            rbo.setProperty("aggregatorID", aggregatorId);
            rbo.setProperty("storeID", storeId);
            rbo.callMethod("getAffiliateStore");
            String errStatus = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMessage = rbo.getProperty("svrMessage");
            String errName = rbo.getProperty("svrCtrlName");
            if ("-1".equals(errStatus)) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else {
                rbo.getPropertyToDynArray("storeID");
                UniDynArray storeNames = rbo.getPropertyToDynArray("storeName");
                UniDynArray currencyTypes = rbo.getPropertyToDynArray("currencyType");
                Store s = new Store();
                s.setAggregatorId(aggregatorId);
                s.setStoreId(storeId);
                s.setStoreName(rbo.getPropertyToDynArray("storeName").toString());
                s.setInactive(rbo.getPropertyToDynArray("storeID").toString().equals("1"));
                s.setReportingCurrency(rbo.getPropertyToDynArray("currencyType").toString());
                s.setSabpType(rbo.getPropertyToDynArray("sabpType").toString());
                s.setZeroCommission(rbo.getPropertyToDynArray("zeroCommission").toString());
                setSelectedStore(s);
            }
        } catch (RbException ex) {
            Logger.getLogger(StoreBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the selectedStore
     */
    public Store getSelectedStore() {
        return selectedStore;
    }

    /**
     * @param selectedStore the selectedStore to set
     */
    public void setSelectedStore(Store selectedStore) {
        this.selectedStore = selectedStore;
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
        storeList.clear();
        try {
            rbo.setProperty("aggregatorID", aggregatorId);
            rbo.callMethod("getAffiliateStoreList");
            String errStatus = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMessage = rbo.getProperty("svrMessage");
            String errName = rbo.getProperty("svrCtrlName");
            if ("-1".equals(errStatus)) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else {
                UniDynArray storeIds = rbo.getPropertyToDynArray("storeID");
                UniDynArray storeNames = rbo.getPropertyToDynArray("storeName");
                UniDynArray currencyTypes = rbo.getPropertyToDynArray("currencyType");
                int itemCount = storeIds.dcount(1);
                for (int val = 1; val <= itemCount; val++) {
                    Store s = new Store();
                    s.setAggregatorId(aggregatorId);
                    s.setStoreId(storeIds.extract(1, val).toString());
                    s.setStoreName(storeNames.extract(1, val).toString());
                    s.setReportingCurrency(currencyTypes.extract(1, val).toString());
                    SelectItem se = new SelectItem(s.getStoreId(), s.getStoreName());
                    storeList.add(se);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(StoreBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storeList;
    }

    /**
     * @param storeList the storeList to set
     */
    public void setStoreList(ArrayList<Store> storeList) {
//        this.storeList = storeList;
    }

    /**
     * @return the currencyTypeList
     */
    public ArrayList<SelectItem> getCurrencyCodeList() {
        if (currencyCodeList.isEmpty()) {
            try {
                RedObject rb = new RedObject("WDE", "UTIL:Currency");
                rb.callMethod("getUtilCurrencyRateList");
                String errStatus = rb.getProperty("svrStatus");
                String errCode = rb.getProperty("svrCtrlCode");
                String errMessage = rb.getProperty("svrMessage");
                String errName = rb.getProperty("svrCtrlName");
                if ("-1".equals(errStatus)) {
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                    ctx.addMessage(null, msg);
                } else {
                    UniDynArray currencyCodes = rb.getPropertyToDynArray("currencyCode");
                    UniDynArray currencyNames = rb.getPropertyToDynArray("currencyName");
                    int itemCount = Integer.parseInt(rb.getProperty("itemCount"));
                    for (int val = 1; val <= itemCount; val++) {
                        SelectItem s = new SelectItem();
                        s.setKey(currencyCodes.extract(1, val).toString());
                        s.setValue(currencyNames.extract(1, val).toString());
                        currencyCodeList.add(s);
                    }
                }
            } catch (RbException ex) {
                Logger.getLogger(StoreBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return currencyCodeList;
    }

    /**
     *
     * @return StoreConverter
     */
//    public StoreConverter getConverter() {
//        return this.converter;
//    }
//    @FacesConverter(value = "storeConverter")
//    public class StoreConverter implements Converter {
//
//        @Override
//        public String getAsString(FacesContext context, UIComponent component, Object obj) {
//            if (!obj.equals("-1") && obj != null) {
//                String storeId = obj.toString();
//                for (Store s : storeList) {
//                    if (storeId.equalsIgnoreCase(s.getStoreId())) {
//                        return s.getStoreName();
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        public Object getAsObject(FacesContext context, UIComponent component, String storeName) {
//            if (!storeName.equals("-1")) {
//                for (Store s : storeList) {
//                    if (storeName.equalsIgnoreCase(s.getStoreName())) {
//                        return s;
//                    }
//                }
//            }
//            return null;
//        }
//    }
}
