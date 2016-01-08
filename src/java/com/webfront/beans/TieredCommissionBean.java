/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.TieredCommission;
import com.webfront.util.JSFHelper;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author rlittle
 */
@Named(value = "tieredCommissionBean")
@Dependent
public class TieredCommissionBean {

    private final ArrayList<TieredCommission> tieredCommList;
    private TieredCommission selectedItem;
    
    /**
     * Creates a new instance of TieredCommissionBean
     */
    public TieredCommissionBean() {
        tieredCommList = new ArrayList<>();
        selectedItem = new TieredCommission();
    }

    /**
     * @return the tieredCommList
     */
    public ArrayList<TieredCommission> getTieredCommList() {
        return tieredCommList;
    }

    public void setTieredCommList() {
        tieredCommList.clear();
        RedObject rbo = new RedObject("WDE", "Affiliates:Commission");
        rbo.setProperty("vendorId", selectedItem.getVendorId());
        rbo.setProperty("storeId", selectedItem.getStoreId());
        try {
            rbo.callMethod("getAopTieredCommission");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String svrCtrlCode = rbo.getProperty("svrCtrlCode");
            String svrMessage = rbo.getProperty("svrMessage");
            if (svrStatus == -1) {
                JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
            } else {
                UniDynArray oList = new UniDynArray();
                oList.insert(1, rbo.getPropertyToDynArray("itemId"));
                oList.insert(2, rbo.getPropertyToDynArray("vendorId"));
                oList.insert(3, rbo.getPropertyToDynArray("storeId"));
                oList.insert(4, rbo.getPropertyToDynArray("minSale"));
                oList.insert(5, rbo.getPropertyToDynArray("commAmt"));
                oList.insert(6, rbo.getPropertyToDynArray("startDate"));
                oList.insert(7, rbo.getPropertyToDynArray("endDate"));
                int vals = oList.count(1);
                for(int val=1 ; val<= vals; val++) {
                    TieredCommission tc = new TieredCommission();
                    tc.setId(oList.extract(1, val).toString());
                    tc.setVendorId(oList.extract(2, val).toString());
                    tc.setStoreId(oList.extract(3, val).toString());
                    tc.setMinSale(oList.extract(4, val).toString());
                    tc.setCommAmt(oList.extract(5, val).toString());
                    tc.setStartDate(oList.extract(6, val).toString());
                    tc.setEndDate(oList.extract(7, val).toString());
                    tieredCommList.add(tc);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(TieredCommissionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the selectedItem
     */
    public TieredCommission getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(TieredCommission selectedItem) {
        this.selectedItem = selectedItem;
    }
    
}
