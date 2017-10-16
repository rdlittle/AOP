/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.AopQueue;
import com.webfront.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author rlittle
 */
@Named
@SessionScoped
public class ReportBean implements Serializable {

    private ArrayList<AopQueue> reportList = null;
    private AopQueue selectedItem = null;
    private ArrayList<SelectItem> reportTypeList = null;
    private final RedObject rbo = new RedObject("WDE", "AFFILIATE:Report");
    private Date date = null;
    @ManagedProperty(value = "#{downloadBean}")
    private DownloadBean downLoader;
    private StreamedContent report;
    
    public ReportBean() {
        if(this.downLoader == null) {
            this.downLoader = new DownloadBean();
        }
        if (this.reportList == null) {
            this.reportList = new ArrayList<>();
        }
        if (this.selectedItem == null) {
            this.selectedItem = new AopQueue();
        }
        if (this.reportTypeList == null) {
            this.reportTypeList = new ArrayList<>();
        }
        if (this.date == null) {
            date = Calendar.getInstance(Locale.US).getTime();
        }
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    public AopQueue getSelectedItem() {
        return this.selectedItem;
    }

    public ArrayList<AopQueue> getReportList() {
        return this.reportList;
    }

    public ArrayList<SelectItem> getReportTypeList() {
        setReportTypeList();
        return this.reportTypeList;
    }

    public void setSelectedItem() {

    }

    public void doReport() {
//        Logger.getLogger(ReportBean.class.getName()).log(Level.INFO, "ReportBean.doReport()");
        setReportList();
    }

    public StreamedContent getReport() {
        this.report = this.getDownLoader().getContent();
        return this.report;
    }
    /**
     *
     */
    public void setReportList() {
        if(this.selectedItem.getAggregatorId()==null) {
            this.selectedItem.setAggregatorId("");
        }
        if(this.selectedItem.getReportType()==null) {
            this.selectedItem.setReportType("");
        }
        rbo.setProperty("aggregatorId", this.selectedItem.getAggregatorId());
        rbo.setProperty("processDate", this.selectedItem.getCreateDate());
        rbo.setProperty("processTime", this.selectedItem.getCreateTime());
        rbo.setProperty("userId", this.selectedItem.getUserName());
        rbo.setProperty("reportType", this.selectedItem.getReportType());
        try {
            rbo.callMethod("getAffiliateReportList");
            String errStatus = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMessage = rbo.getProperty("svrMessage");
            String errName = rbo.getProperty("svrCtrlName");
            if ("-1".equals(errStatus)) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else {
                UniDynArray queueIdList = rbo.getPropertyToDynArray("queueId");
                UniDynArray aggList = rbo.getPropertyToDynArray("aggregatorId");
                UniDynArray successList = rbo.getPropertyToDynArray("successReportId");
                UniDynArray errorList = rbo.getPropertyToDynArray("errorReportId");
                UniDynArray titleList = rbo.getPropertyToDynArray("reportTitle");
                UniDynArray dateList = rbo.getPropertyToDynArray("processDate");
                UniDynArray timeList = rbo.getPropertyToDynArray("processTime");
                UniDynArray userList = rbo.getPropertyToDynArray("userId");
                int itemCount = queueIdList.dcount(1);
                this.reportList.clear();
                for (int i = 1; i <= itemCount; i++) {
                    AopQueue q = new AopQueue();
                    q.setAggregatorId(aggList.extract(1, i).toString());
                    q.setUserName(userList.extract(1, i).toString());
                    q.setCreateDate(dateList.extract(1, i).toString());
                    q.setCreateTime(timeList.extract(1, i).toString());
                    q.setSuccessReport(successList.extract(1, i).toString());
                    q.setErrorReport(errorList.extract(1, i).toString());
                    q.setReportTitle(titleList.extract(1, i).toString());
                    this.reportList.add(q);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setReportTypeList() {
        try {
            rbo.callMethod("getAffiliateReportTypeList");
            String errStatus = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMessage = rbo.getProperty("svrMessage");
            String errName = rbo.getProperty("svrCtrlName");
            if ("-1".equals(errStatus)) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else {
                UniDynArray reportTypes = rbo.getPropertyToDynArray("reportType");
                UniDynArray descriptions = rbo.getPropertyToDynArray("description");
                int itemCount = reportTypes.dcount(1);
                this.reportTypeList.clear();
                for (int i = 1; i <= itemCount; i++) {
                    SelectItem se = new SelectItem();
                    se.setKey(reportTypes.extract(1, i).toString());
                    se.setValue(descriptions.extract(1, i).toString());
                    this.reportTypeList.add(se);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the downLoader
     */
    public DownloadBean getDownLoader() {
        return this.downLoader;
    }

    /**
     * @param downLoader the downLoader to set
     */
    public void setDownLoader(DownloadBean downLoader) {
        this.downLoader = downLoader;
    }
}
