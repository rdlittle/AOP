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
import com.webfront.model.SelectItem;
import java.io.Serializable;
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
    private final HashMap<String, String> columnNames;

    public AffiliateMasterController() {
        this.rb = new RedObject("WDE", "Affiliates:Master");
        columnNames = new HashMap<>();
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
            affiliateMaster.setNetworkId(rbo.getProperty("networkId"));
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
            affiliateMaster.setColumnMap(this.populateColumns(ID));
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affiliateMaster;
    }

    public void setAffiliateMaster(AffiliateMaster rec) {
        affiliateMaster = rec;
        RedObject rbo = new RedObject("WDE", "Affiliates:Master");
        rbo.setProperty("id", rec.getID());
        rbo.setProperty("affiliateName", rec.getName());
        rbo.setProperty("networkId", rec.getNetworkId());
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
            String errStat = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMsg = rbo.getProperty("svrMessage");
            if (errStat.equals("-1")) {
                String msg = "Error: " + errCode + " " + errMsg;
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("msgs", new FacesMessage(msg));
            } else {
                UniDynArray ibvMappingRec = new UniDynArray();
                int val = 1;
                for (SelectItem se : rec.getColumns()) {
                    String fieldName = se.getKey();
                    ibvMappingRec.replace(1, 1, val, fieldName);
                    val += 1;
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
                    String msg = rec.getID() + " saved";
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("msgs", new FacesMessage(msg));
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HashMap<String, String> populateColumns(String id) {
        HashMap<String, String> list = new HashMap<>();
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
            HashMap<String,String> map = getColumnNames();
            for (int val = 1; val <= vals; val++) {
                String fieldKey = uda.extract(1, 1, val).toString();
                String fieldValue = map.get(fieldKey);
                list.put(fieldKey, fieldValue);
            }

        } catch (RbException rbe) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
        return list;
    }

    public HashMap<String, String> getColumnNames() {
        if (columnNames.isEmpty()) {
            try {
                RedObject rbo = new RedObject("WDE", "UTILS:Files");
                rbo.setProperty("fileName", "PARAMS");
                rbo.setProperty("id", "DATA.FEED.COLUMN.NAMES");
                rbo.callMethod("getFileRec");
                String errStat = rbo.getProperty("errStat");
                String errCode = rbo.getProperty("errCode");
                String errMsg = rbo.getProperty("errMsg");
                UniDynArray uda = rbo.getPropertyToDynArray("fileRec");
                int vals = uda.dcount(1, 1);
                for (int val = 1; val <= vals; val++) {
                    columnNames.put(uda.extract(1, 2, val).toString(), uda.extract(1, 1, val).toString());
                }
            } catch (RbException rbe) {
                Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
            }
        }
        return columnNames;
    }

}
