/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.controller;

import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.beans.WebDEBean;
import com.webfront.model.IBVMaster;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import org.primefaces.context.RequestContext;

/**
 *
 * @author rlittle
 */
@ManagedBean
@RequestScoped
public class IBVMasterController implements ValueChangeListener {

    private IBVMaster ibvMaster;
    
    public IBVMasterController() {
        this.ibvMaster=new IBVMaster();
    }

    public IBVMaster getIbvMaster() {
        ibvMaster=new IBVMaster();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        String id=requestContext.getAttributes().get("vendorID").toString();
        try {
            RedObject rbo = new RedObject("WDE", "Vendor:Master");
            rbo.setProperty("id", id);
            rbo.callMethod("getMaster");
            ibvMaster.setName(rbo.getProperty("name"));
            ibvMaster.setCategory(rbo.getProperty("category"));
            ibvMaster.setType(rbo.getProperty("type"));
            ibvMaster.setPrefix(rbo.getProperty("prefix"));
            ibvMaster.setCountry(rbo.getProperty("country"));
            ibvMaster.setCurrency(rbo.getProperty("currency"));
            ibvMaster.setMappingId(rbo.getProperty("mappingId"));
            ibvMaster.setDataFeedAccessType(rbo.getProperty("dataFeedAccessMethod"));
            ibvMaster.setDataFeedFormat(rbo.getProperty("dataFeedFormat"));
            ibvMaster.setDataFeedURL(rbo.getProperty("url"));
            ibvMaster.setUserName(rbo.getProperty("userName"));
            ibvMaster.setPassword(rbo.getProperty("password"));
            ibvMaster.setCreateDate(rbo.getProperty("createDate"));
            ibvMaster.setActive(rbo.getProperty("isActive").equals("1") ? true : false);
            ibvMaster.setNextDetailId(rbo.getProperty("nextDetailId"));
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ibvMaster;
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        getIbvMaster();
    }
}
