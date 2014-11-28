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
import com.webfront.model.AffiliateMaster;
import com.webfront.model.AffiliateMapping;
import com.webfront.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AffiliateMasterController implements Serializable {

    private final RedObject rb;
    protected AffiliateMaster affiliateMaster;

    public AffiliateMasterController() {
        this.rb = new RedObject("WDE", "Affiliates:Master");
    }

    public AffiliateMaster getAffiliateMaster(String ID) {
        affiliateMaster = new AffiliateMaster();
        try {
            RedObject rbo = new RedObject("WDE", "Affiliates:Master");
            rbo.setProperty("id", ID);
            rbo.callMethod("getMaster");
            String errStat = rbo.getProperty("errStat");
            String errCode = rbo.getProperty("errCode");
            String errMsg = rbo.getProperty("errMsg");
            affiliateMaster.setName(rbo.getProperty("affiliateName"));
            affiliateMaster.setCategory(rbo.getProperty("category"));
            affiliateMaster.setType(rbo.getProperty("type"));
            affiliateMaster.setPrefix(rbo.getProperty("prefix"));
            affiliateMaster.setCountry(rbo.getProperty("country"));
            affiliateMaster.setCurrency(rbo.getProperty("currencyType"));
            affiliateMaster.setMappingId(rbo.getProperty("mappingId"));
            affiliateMaster.setDataFeedAccessType(rbo.getProperty("dataFeedAccessMethod"));
            affiliateMaster.setDataFeedFormat(rbo.getProperty("dataFeedFormat"));
            affiliateMaster.setDataFeedURL(rbo.getProperty("url"));
            affiliateMaster.setUserName(rbo.getProperty("userName"));
            affiliateMaster.setPassword(rbo.getProperty("password"));
            affiliateMaster.setCreateDate(rbo.getProperty("createDate"));
            affiliateMaster.setActive(rbo.getProperty("isActive").equals("1"));
            affiliateMaster.setNextDetailId(rbo.getProperty("nextDetailId"));
            affiliateMaster.setFieldMap(this.populateFieldMap(ID));
            affiliateMaster.setFieldMapList(new ArrayList<SelectItem>());
            ArrayList<SelectItem> list = new ArrayList<>();
            for (Integer i : affiliateMaster.getFieldMap().keySet()) {
                String colName = affiliateMaster.getFieldMap().get(i).getColumnName();
                list.add(new SelectItem(i.toString(), colName));
            }
            affiliateMaster.setFieldMapList(list);
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affiliateMaster;
    }

    public void setAffiliateMaster(AffiliateMaster rec) {
        affiliateMaster = rec;
        RedObject rbo = new RedObject("WDE", "Vendor:Master");
        rbo.setProperty("id", rec.getID());
        rbo.setProperty("vendorName", rec.getName());
        rbo.setProperty("category", rec.getCategory());
        rbo.setProperty("type", rec.getType());
        rbo.setProperty("prefix", rec.getPrefix());
        rbo.setProperty("country", rec.getCountry());
        rbo.setProperty("currencyType", rec.getCurrency());
        rbo.setProperty("mappingId", rec.getMappingId());
        rbo.setProperty("dataFeedAccessMethod", rec.getDataFeedAccessType());
        rbo.setProperty("dataFeedFormat", rec.getDataFeedFormat());
        rbo.setProperty("url", rec.getDataFeedURL());
        rbo.setProperty("userName", rec.getUserName());
        rbo.setProperty("password", rec.getPassword());
        rbo.setProperty("createDate", rec.getCreateDate());
        rbo.setProperty("isActive", rec.isActive() ? "1" : "0");
        rbo.setProperty("nextDetailId", rec.getNextDetailId());
        try {
            rbo.callMethod("setMaster");
            String errStat = rbo.getProperty("errStat");
            String errCode = rbo.getProperty("errCode");
            String errMsg = rbo.getProperty("errMsg");
            if (errStat.equals("-1")) {
                String msg = "Error: " + errCode + " " + errMsg;
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("msgs", new FacesMessage(msg));
            } else {
                UniDynArray ibvMappingRec = new UniDynArray();
                for (Integer i : rec.getFieldMap().keySet()) {
                    AffiliateMapping ibvMapping = rec.getFieldMap().get(i);
                    ibvMappingRec.replace(1, 1, i, ibvMapping.getColumnName());
                    ibvMappingRec.replace(1, 4, i, ibvMapping.getExclude().equals("1") ? "1" : "0");
                }
                rbo = new RedObject("WDE", "UTILS:Files");
                rbo.setProperty("fileName", "IBV.MAPPING");
                rbo.setProperty("fileRec", ibvMappingRec);
                rbo.setProperty("id", rec.getID());
                rbo.callMethod("setFileRec");
                errStat = rbo.getProperty("errStat");
                errCode = rbo.getProperty("errCode");
                errMsg = rbo.getProperty("errMsg");
                if (errStat.equals("-1")) {
                    String msg = "Error: " + errCode + " " + errMsg;
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("msgs", new FacesMessage(msg));
                } else {
                    String msg = rec.getID()+" saved";
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("msgs", new FacesMessage(msg));
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HashMap<Integer, AffiliateMapping> populateFieldMap(String id) {
        HashMap<Integer, AffiliateMapping> list = new HashMap<>();
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
                AffiliateMapping im = new AffiliateMapping();
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
}
