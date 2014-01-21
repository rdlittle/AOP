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
import com.webfront.model.IBVMaster;
import com.webfront.model.IbvMapping;
import com.webfront.model.SelectItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rlittle
 */
public class IBVMasterController {

    public IBVMasterController() {
        
    }

    public IBVMaster getIbvMaster(String ID) {
        IBVMaster ibvMaster=new IBVMaster();
        try {
            RedObject rbo = new RedObject("WDE", "Vendor:Master");
            rbo.setProperty("id", ID);
            rbo.callMethod("getMaster");
            String errStat=rbo.getProperty("errStat");
            String errCode=rbo.getProperty("errCode");
            String errMsg=rbo.getProperty("errMsg");
            ibvMaster.setName(rbo.getProperty("vendorName"));
            ibvMaster.setCategory(rbo.getProperty("category"));
            ibvMaster.setType(rbo.getProperty("type"));
            ibvMaster.setPrefix(rbo.getProperty("prefix"));
            ibvMaster.setCountry(rbo.getProperty("country"));
            ibvMaster.setCurrency(rbo.getProperty("currencyType"));
            ibvMaster.setMappingId(rbo.getProperty("mappingId"));
            ibvMaster.setDataFeedAccessType(rbo.getProperty("dataFeedAccessMethod"));
            ibvMaster.setDataFeedFormat(rbo.getProperty("dataFeedFormat"));
            ibvMaster.setDataFeedURL(rbo.getProperty("url"));
            ibvMaster.setUserName(rbo.getProperty("userName"));
            ibvMaster.setPassword(rbo.getProperty("password"));
            ibvMaster.setCreateDate(rbo.getProperty("createDate"));
            ibvMaster.setActive(rbo.getProperty("isActive").equals("1"));
            ibvMaster.setNextDetailId(rbo.getProperty("nextDetailId"));
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ibvMaster;
    }
    
    public HashMap<Integer,IbvMapping> getFieldMap(String id) {
        HashMap<Integer,IbvMapping> list=new HashMap<Integer,IbvMapping>();
        try {
            RedObject rbo = new RedObject("WDE","UTILS:Files");
            rbo.setProperty("fileName", "IBV.MAPPING");
            rbo.setProperty("id", id);
            rbo.callMethod("getFileRec");
            String errStat=rbo.getProperty("errStat");
            String errCode=rbo.getProperty("errCode");
            String errMsg=rbo.getProperty("errMsg");
            UniDynArray uda=rbo.getPropertyToDynArray("fileRec");
            int vals = uda.dcount(1,1);
            for(int val=1; val<=vals; val++) {
                IbvMapping im=new IbvMapping();
                String fieldName = uda.extract(1,1,val).toString();
                String excludeFlag = uda.extract(1, 4, val).toString();
                im.setColumnName(fieldName);
                im.setExclude(excludeFlag);
                list.put(Integer.valueOf(val),im);
            }
            
        } catch(RbException rbe) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
        return list;
    }
    


}
