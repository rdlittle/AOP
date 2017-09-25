/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.Commission;
import com.webfront.model.CommissionTier;
import com.webfront.model.SelectItem;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author rlittle
 */
@Named
@RequestScoped
public class CommissionBean {

    private RedObject rbo;
    private Commission selectedCommission = null;
    private String aggregatorId;
    private String storeId;
    private ArrayList<SelectItem> commissionTypeList;

    public CommissionBean() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String str = ctx.getCurrentPhaseId().getName();
//        Logger.getLogger(CommissionBean.class.getName()).log(Level.INFO, "CommissionBean() " + str + " isPostback: " + ctx.isPostback());
        rbo = new RedObject("WDE", "AFFILIATE:Commission");
        if (selectedCommission == null) {
            selectedCommission = new Commission();
            commissionTypeList = new ArrayList<>();
            init();
        }
    }

    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String str = ctx.getCurrentPhaseId().getName();
//        Logger.getLogger(CommissionBean.class.getName()).log(Level.INFO, "CommissionBean.init() " + str + " isPostback: " + ctx.isPostback());
        Map<String, String> map = ctx.getExternalContext().getRequestParameterMap();
        if (map.containsKey("aggregatorId")) {
            aggregatorId = map.get("aggregatorId");
        } else {
            return;
        }
        if (map.containsKey("storeId")) {
            storeId = map.get("storeId");
        } else {
            return;
        }
        rbo.setProperty("aggregatorID", aggregatorId);
        rbo.setProperty("storeID", storeId);
        try {
            rbo.callMethod("getAffiliateCommission");
            int errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else {
                selectedCommission.setCreateDate(rbo.getProperty("createDate"));
                selectedCommission.setSabpType(rbo.getProperty("sabpType"));
                selectedCommission.setCommissionType(rbo.getProperty("commPayType"));
                selectedCommission.setCommission(rbo.getProperty("commission"));
                selectedCommission.setTiered("1".equals(rbo.getProperty("tiered")));
                selectedCommission.setIbv(rbo.getProperty("ibv"));
                selectedCommission.setCashback(rbo.getProperty("cashback"));
                selectedCommission.setMaProfit(rbo.getProperty("maProfit"));
                selectedCommission.setExcludeType(rbo.getProperty("exclusionType"));
                UniDynArray tierKeys = rbo.getPropertyToDynArray("commKey");
                UniDynArray commTypes = rbo.getPropertyToDynArray("commPayType");
                UniDynArray ibvList = rbo.getPropertyToDynArray("ibv");
                UniDynArray cbList = rbo.getPropertyToDynArray("cashback");
                UniDynArray commList = rbo.getPropertyToDynArray("commission");
                UniDynArray profitList = rbo.getPropertyToDynArray("maProfit");
                UniDynArray tierTitles = rbo.getPropertyToDynArray("titles");
                int tierCount = Integer.parseInt(rbo.getProperty("tieredCount"));
                for (int t = 1; t <= tierCount; t++) {
                    CommissionTier tier = new CommissionTier();
                    tier.setTierKey(tierKeys.extract(1, t).toString());
                    tier.setPayType(commTypes.extract(1, t).toString());
                    tier.setIbv(ibvList.extract(1, t).toString());
                    tier.setCashback(cbList.extract(1, t).toString());
                    tier.setCommission(commList.extract(1, t).toString());
                    tier.setMaProfit(profitList.extract(1, t).toString());
                    tier.setTierTitle(tierTitles.extract(1, t).toString());
                    selectedCommission.getTiers().add(tier);
                }
                setCommissionTypeList();
            }
        } catch (RbException ex) {
            Logger.getLogger(CommissionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the selectedCommission
     */
    public Commission getSelectedCommission() {
        return selectedCommission;
    }

    /**
     * @param selectedCommission the selectedCommission to set
     */
    public void setSelectedCommission(Commission selectedCommission) {
        this.selectedCommission = selectedCommission;
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
     * @return the commissionTypeList
     */
    public ArrayList<SelectItem> getCommissionTypeList() {
        return commissionTypeList;
    }

    /**
     * @param commissionTypeList the commissionTypeList to set
     */
    public void setCommissionTypeList() {
        RedObject ro = new RedObject("WDE", "UTILS:Files");
        ro.setProperty("fileName", "PARAMS");
        ro.setProperty("id", "COMMISSION.TYPES");
        ro.setProperty("keyField", "1");
        ro.setProperty("valueField", "2");
        try {
            ro.callMethod("getSelectObject");
            String errStat = ro.getProperty("svrStatus");
            String errCode = ro.getProperty("svrCtrlCode");
            String errMsg = ro.getProperty("svrMessage");
            if (errStat.equals("-1")) {
                FacesMessage msg = new FacesMessage(errCode + ": " + errMsg);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                UniDynArray keys = ro.getPropertyToDynArray("keyList");
                UniDynArray values = ro.getPropertyToDynArray("valueList");
                int vals = keys.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    SelectItem item = new SelectItem();
                    String key = keys.extract(1, val).toString();
                    String value = values.extract(1, val).toString();
                    item.setKey(key);
                    item.setValue(value);
                    commissionTypeList.add(item);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(CommissionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
