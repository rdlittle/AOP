/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.SelectItem;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class WebDEBean implements Serializable {

    private RedObject rbo;
    private LinkedList<SelectItem> vendorList;
    private int todayInternal;
    private String todayExternal;

    /**
     * Creates a new instance of WebDEBean
     */
    public WebDEBean() {
        todayInternal=1;
        setTodayInternal(1);
        setRbo(new RedObject("WDE", "AOP:Forms"));
        setVendorList(new LinkedList<SelectItem>());
        
    }

    /**
     * @return the rbo
     */
    public RedObject getRbo() {
        return this.rbo;
    }

    /**
     * @param rbo the rbo to set
     */
    public void setRbo(RedObject rbo) {
        this.rbo = rbo;
    }

    /**
     * @return the vendorNames
     */
    public LinkedList<SelectItem> getVendorList() {
        return vendorList;
    }

    /**
     * @param vendors the vendorNames to set
     */
    public void setVendorList(LinkedList<SelectItem> vendors) {
        try {
            getRbo().callMethod("getVendorList");
            UniDynArray vendorNames=getRbo().getPropertyToDynArray("vendorNameList");
            UniDynArray vendorIds=getRbo().getPropertyToDynArray("vendorId");
            int vals=vendorNames.dcount(1);
            for(int i=1; i<=vals; i++) {
                String str=vendorNames.extract(1,i).toString();
                String vid=vendorIds.extract(1, i).toString();
                vendors.add(new SelectItem(vid,str));
            }
            this.vendorList = vendors;
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the todayInternal
     */
    public int getTodayInternal() {
        if(todayInternal==1) {
            setTodayInternal(1);
        }
        return todayInternal;
    }

    public void setTodayInternal(int idate) {
        try {
            setRbo(new RedObject("WDE", "AOP:Utils"));
            getRbo().setProperty("oDate", "");
            getRbo().callMethod("getIDate");
            String iDate = getRbo().getProperty("iDate");
            todayInternal = Integer.parseInt(iDate);
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the todayExternal
     */
    public String getTodayExternal() {
        try {
            setRbo(new RedObject("WDE", "AOP:Utils"));
            getRbo().setProperty("iDate", Integer.toString(todayInternal));
            getRbo().callMethod("getODate");
            String oDate = getRbo().getProperty("oDate");
            setTodayExternal(oDate);
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return todayExternal;
    }

    /**
     * @param todayExternal the todayExternal to set
     */
    public void setTodayExternal(String todayExternal) {
        this.todayExternal = todayExternal;
    }
    
}
