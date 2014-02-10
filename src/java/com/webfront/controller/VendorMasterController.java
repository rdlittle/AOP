/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.controller;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.beans.WebDEBean;
import com.webfront.model.VendorMaster;
import com.webfront.model.IbvMapping;
import com.webfront.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
public class VendorMasterController implements Serializable {

    private final RedObject rb;
    protected VendorMaster vendorMaster;

    public VendorMasterController() {
        this.rb = new RedObject("WDE", "Vendor:Master");
    }

    public VendorMaster getVendorMaster(String ID) {
        vendorMaster = new VendorMaster();
        try {
            RedObject rbo = new RedObject("WDE", "Vendor:Master");
            rbo.setProperty("id", ID);
            rbo.callMethod("getMaster");
            String errStat = rbo.getProperty("errStat");
            String errCode = rbo.getProperty("errCode");
            String errMsg = rbo.getProperty("errMsg");
            vendorMaster.setName(rbo.getProperty("vendorName"));
            vendorMaster.setCategory(rbo.getProperty("category"));
            vendorMaster.setType(rbo.getProperty("type"));
            vendorMaster.setPrefix(rbo.getProperty("prefix"));
            vendorMaster.setCountry(rbo.getProperty("country"));
            vendorMaster.setCurrency(rbo.getProperty("currencyType"));
            vendorMaster.setMappingId(rbo.getProperty("mappingId"));
            vendorMaster.setDataFeedAccessType(rbo.getProperty("dataFeedAccessMethod"));
            vendorMaster.setDataFeedFormat(rbo.getProperty("dataFeedFormat"));
            vendorMaster.setDataFeedURL(rbo.getProperty("url"));
            vendorMaster.setUserName(rbo.getProperty("userName"));
            vendorMaster.setPassword(rbo.getProperty("password"));
            vendorMaster.setCreateDate(rbo.getProperty("createDate"));
            vendorMaster.setActive(rbo.getProperty("isActive").equals("1"));
            vendorMaster.setNextDetailId(rbo.getProperty("nextDetailId"));
            vendorMaster.setFieldMap(this.populateFieldMap(ID));
            vendorMaster.setFieldMapList(new ArrayList<SelectItem>());
            ArrayList<SelectItem> list=new ArrayList<>();
            for(Integer i : vendorMaster.getFieldMap().keySet()) {
                String colName = vendorMaster.getFieldMap().get(i).getColumnName();
                list.add(new SelectItem(i.toString(),colName));
            }
            vendorMaster.setFieldMapList(list);
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vendorMaster;
    }

    public HashMap<Integer, IbvMapping> populateFieldMap(String id) {
        HashMap<Integer, IbvMapping> list = new HashMap<>();
        try {
            RedObject rbo = new RedObject("WDE", "UTILS:Files");
            rbo.setProperty("fileName", "IBV.MAPPING");
            rbo.setProperty("id", id);
            rbo.callMethod("getFileRec");
            String errStat = rbo.getProperty("errStat");
            String errCode = rbo.getProperty("errCode");
            String errMsg = rbo.getProperty("errMsg");
            UniDynArray uda = rbo.getPropertyToDynArray("fileRec");
            int vals = uda.dcount(1, 1);
            for (int val = 1; val <= vals; val++) {
                IbvMapping im = new IbvMapping();
                String fieldName = uda.extract(1, 1, val).toString();
                String excludeFlag = uda.extract(1, 4, val).toString();
                im.setColumnName(fieldName);
                im.setExclude(excludeFlag);
                list.put(Integer.valueOf(val), im);
            }

        } catch (RbException rbe) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
        return list;
    }
    
    public void createColumn(AjaxBehaviorEvent event) {
        if (vendorMaster.isNewColumn()==false && vendorMaster.getFieldMap() != null) {
            int nextColumn = vendorMaster.getFieldMap().size();
            nextColumn++;
            IbvMapping ibvMapping = new IbvMapping();
            ibvMapping.setId(Integer.toString(nextColumn));
            vendorMaster.getFieldMap().put(nextColumn, ibvMapping);
            vendorMaster.getFieldMapList().add(new SelectItem(Integer.toString(nextColumn), ibvMapping.getColumnName()));
            vendorMaster.setNewColumn(true);
        }
    }
    
    public void saveColumn(AjaxBehaviorEvent event) {
        vendorMaster.setNewColumn(false);
    }

}
