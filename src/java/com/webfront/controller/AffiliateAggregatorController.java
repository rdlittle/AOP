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
import com.webfront.model.Aggregator;
import java.io.Serializable;
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
public class AffiliateAggregatorController implements Serializable {

    private final RedObject rb;
    protected Aggregator aggregator;
    private String aggregatorID;

    public AffiliateAggregatorController() {
        this.rb = new RedObject("WDE", "AFFILIATE:Aggregator");
        aggregatorID = "";
    }

    public Aggregator getAggregator(String ID) {
        aggregator = new Aggregator();
        try {
            RedObject rbo = new RedObject("WDE", "AFFILIATE:Aggregator");
            rbo.setProperty("aggregatorID", ID);
            rbo.callMethod("getAffiliateAggregator");
            String errStat = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMsg = rbo.getProperty("svrMessage");
            if (errStat.equals("-1")) {
                String msg = "Error: " + errCode + " " + errMsg;
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("msgs", new FacesMessage(msg));
            } else {
                aggregator.setName(rbo.getProperty("aggregatorName"));
                aggregator.setNetworkId(rbo.getProperty("networkId"));
                aggregator.setInvPrefix(rbo.getProperty("invPrefix"));
                aggregator.setPayCountry(rbo.getProperty("payCountry"));
                aggregator.setPayCurrency(rbo.getProperty("payCurrency"));
                aggregator.setReportFormat(rbo.getProperty("reportFormat"));
                aggregator.setIsInactive(rbo.getProperty("isInactive").equals("1"));
            }
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aggregator;
    }

    public void setAggregator(Aggregator rec) {
        aggregator = rec;
        RedObject rbo = new RedObject("WDE", "AFFILIATE:Aggregator");
        rbo.setProperty("id", rec.getID());
        rbo.setProperty("aggregatorName", rec.getName());
        rbo.setProperty("networkId", rec.getNetworkId());
        rbo.setProperty("invPrefix", rec.getInvPrefix());
        rbo.setProperty("payCountry", rec.getPayCountry());
        rbo.setProperty("payCurrency", rec.getPayCurrency());
        rbo.setProperty("reportFormat", rec.getReportFormat());
        rbo.setProperty("isInactive", rec.isInactive() ? "1" : "0");
        try {
            rbo.callMethod("putAffiliateAggregator");
            String errStat = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMsg = rbo.getProperty("svrMessage");
            if (errStat.equals("-1")) {
                String msg = "Error: " + errCode + " " + errMsg;
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("msgs", new FacesMessage(msg));
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateAggregatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the affiliateMasterId
     */
    public String getAggregatorID() {
        return aggregatorID;
    }

    /**
     * @param aggregatorID the affiliateMasterId to set
     */
    public void setAggregatorID(String aggregatorID) {
        this.aggregatorID = aggregatorID;
    }

}
