/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.Campaign;
import com.webfront.model.VendorDetail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class CampaignBean {

    @ManagedProperty(value = "#{vendorDetail}")
    private VendorDetail detail;
    private ArrayList<Campaign> list;
    private Campaign activeCampaign;
    private String vendorMasterId;
    private String vendorDetailId;
    private Calendar calendar;
    private Date campaignStart;
    private Date campaignEnd;
    private boolean newCampaign;
    private String newCashback;

    public CampaignBean() {
        this.list = new ArrayList<>();
        this.activeCampaign = new Campaign();
        this.vendorMasterId = new String();
        this.vendorDetailId = new String();
        this.calendar = Calendar.getInstance();
        this.newCampaign = false;
        this.list = new ArrayList<>();
    }

    /**
     * @return the list
     */
    public ArrayList<Campaign> getList() {
        if (list != null && list.isEmpty()) {
            setList(new ArrayList<Campaign>());
        }
        return list;
    }

    /**
     * @param campaignList the list to set
     */
    public void setList(ArrayList<Campaign> campaignList) {
        if (this.detail != null && !this.detail.getId().isEmpty()) {
            RedObject rb = new RedObject("WDE", "AOP:Cashback");
            String sid = detail.getId();
            int idx = sid.indexOf("*");
            String ssid = sid.substring(idx + 1);
            getActiveCampaign();
            if (this.activeCampaign != null) {
                campaignList.add(this.activeCampaign);
            }
            rb.setProperty("vendorMasterId", detail.getVendorMasterId());
            rb.setProperty("vendorDetailId", ssid);
            try {
                rb.callMethod("getCampaignHist");
                String errStat = rb.getProperty("errStat").toString();
                String errCode = rb.getProperty("errCode").toString();
                String errMsg = rb.getProperty("errMsg").toString();
                if (errStat.equals("-1")) {

                } else {
                    int rows = Integer.valueOf(rb.getProperty("campaignCount").toString());
                    if (rows > 0) {
                        UniDynArray cbBase = rb.getPropertyToDynArray("cbBase");
                        UniDynArray cbIncrease = rb.getPropertyToDynArray("cbIncrease");
                        UniDynArray cbTotal = rb.getPropertyToDynArray("cbTotal");
                        UniDynArray startDate = rb.getPropertyToDynArray("startDate");
                        UniDynArray endDate = rb.getPropertyToDynArray("endDate");
                        UniDynArray date = rb.getPropertyToDynArray("date");
                        UniDynArray time = rb.getPropertyToDynArray("time");
                        UniDynArray user = rb.getPropertyToDynArray("user");
                        UniDynArray status = rb.getPropertyToDynArray("status");
                        for (int row = 1; row <= rows; row++) {
                            Campaign campaign = new Campaign();
                            campaign.setCbBase(cbBase.extract(1, row).toString());
                            campaign.setCbIncrease(cbIncrease.extract(1, row).toString());
                            campaign.setCbTotal(cbTotal.extract(1, row).toString());
                            campaign.setStartDate(startDate.extract(1, row).toString());
                            campaign.setEndDate(endDate.extract(1, row).toString());
                            campaign.setEditDate(date.extract(1, row).toString());
                            campaign.setEditTime(time.extract(1, row).toString());
                            campaign.setUser(user.extract(1, row).toString());
                            campaign.setStatus(status.extract(1, row).toString());
                            campaignList.add(campaign);
                        }
                    }
                }
            } catch (RbException ex) {
                Logger.getLogger(CampaignBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.list = campaignList;
    }

    public void changeCampaign(AjaxBehaviorEvent event) {
        if (this.detail != null) {
            if (this.vendorDetailId == null) {
                this.vendorDetailId = this.detail.getId();
            }

            if (!this.vendorDetailId.equals(this.detail.getId())) {
                setList(new ArrayList<Campaign>());
                this.vendorDetailId = this.detail.getId();
                this.newCampaign = false;
            }
        }
    }

    /**
     * @return the activeCampaign
     */
    public Campaign getActiveCampaign() {
        if (this.detail != null && !this.detail.getId().isEmpty()) {
            RedObject rb = new RedObject("WDE", "AOP:Cashback");
            String sid = detail.getId();
            int idx = sid.indexOf("*");
            String ssid = sid.substring(idx + 1);
            rb.setProperty("vendorMasterId", detail.getVendorMasterId());
            rb.setProperty("vendorDetailId", ssid);
            this.activeCampaign = new Campaign();
            try {
                rb.callMethod("getCampaign");
                String errStat = rb.getProperty("errStat").toString();
                String errCode = rb.getProperty("errCode").toString();
                String errMsg = rb.getProperty("errMsg").toString();
                if (errStat.equals("0")) {
                    this.activeCampaign.setCbBase(rb.getProperty("cbBase"));
                    this.activeCampaign.setCbIncrease(rb.getProperty("cbIncrease"));
                    this.activeCampaign.setCbTotal(rb.getProperty("cbTotal"));
                    this.activeCampaign.setStartDate(rb.getProperty("startDate"));
                    this.activeCampaign.setEndDate(rb.getProperty("endDate"));
                    this.activeCampaign.setEditDate(rb.getProperty("date"));
                    this.activeCampaign.setEditTime(rb.getProperty("editTime"));
                    this.activeCampaign.setUser(rb.getProperty("user"));
                    this.activeCampaign.setStatus(rb.getProperty("status"));
                } else {
                    this.activeCampaign = null;
                }
            } catch (RbException ex) {
                Logger.getLogger(CampaignBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.activeCampaign;
    }

    /**
     * @param activeCampaign the activeCampaign to set
     */
    public void setActiveCampaign(Campaign activeCampaign) {
        this.activeCampaign = activeCampaign;
    }

    /**
     * @return the vendorMasterId
     */
    public String getVendorMasterId() {
        return vendorMasterId;
    }

    /**
     * @param vendorMasterId the vendorMasterId to set
     */
    public void setVendorMasterId(String vendorMasterId) {
        this.vendorMasterId = vendorMasterId;
    }

    /**
     * @return the vendorDetailId
     */
    public String getVendorDetailId() {
        return vendorDetailId;
    }

    /**
     * @param vendorDetailId the vendorDetailId to set
     */
    public void setVendorDetailId(String vendorDetailId) {
        this.vendorDetailId = vendorDetailId;
    }

    /**
     * @return the detailBean
     */
    public VendorDetail getDetail() {
        return detail;
    }

    /**
     * @param detail the IBVDetail to set
     */
    public void setDetail(VendorDetail detail) {
        this.detail = detail;
    }

    /**
     * @return the calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * @param calendar the calendar to set
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * @return the campaignSart
     */
    public Date getCampaignStart() {
        return campaignStart;
    }

    /**
     * @param campaignStart the campaignSart to set
     */
    public void setCampaignStart(Date campaignStart) {
        this.campaignStart = campaignStart;
    }

    /**
     * @return the campaignEnd
     */
    public Date getCampaignEnd() {
        return campaignEnd;
    }

    /**
     * @param campaignEnd the campaignEnd to set
     */
    public void setCampaignEnd(Date campaignEnd) {
        this.campaignEnd = campaignEnd;
    }

    /**
     * @return the newCampaign
     */
    public boolean isNewCampaign() {
        return newCampaign;
    }

    /**
     * @param newCampaign the newCampaign to set
     */
    public void setNewCampaign(boolean newCampaign) {
        this.newCampaign = newCampaign;
    }

    public void createCampaign() {
        setNewCampaign(true);
    }

    public void saveCampaign() {
        if (this.newCampaign) {
            Campaign campaign = new Campaign();
            campaign.setStatus("0");
            campaign.setCbBase("2.00");
            campaign.setCbIncrease(this.getNewCashback());
            SimpleDateFormat dateFmt = new SimpleDateFormat("MM/dd/yy");
            SimpleDateFormat timeFmt = new SimpleDateFormat("kk:mm:ss");
            String sDate = dateFmt.format(this.campaignStart);
            String eDate = dateFmt.format(this.campaignEnd);
            String today = dateFmt.format(Calendar.getInstance().getTime());
            String time = timeFmt.format(Calendar.getInstance().getTime());
            campaign.setStartDate(sDate);
            campaign.setEndDate(eDate);
            String sid = detail.getId();
            int idx = sid.indexOf("*");
            String ssid = sid.substring(idx + 1);
            RedObject rb = new RedObject("WDE", "AOP:Cashback");
            rb.setProperty("ibvMasterId", this.detail.getVendorMasterId());
            rb.setProperty("ibvDetailId", ssid);
            rb.setProperty("startDate", sDate);
            rb.setProperty("endDate", eDate);
            rb.setProperty("cbIncrease", this.getNewCashback());
            rb.setProperty("cbBase", "2.00");
            rb.setProperty("date", today);
            rb.setProperty("time", time);
            rb.setProperty("status", "0");
            try {
                rb.callMethod(("setCampaign"));
                String errStat = rb.getProperty("errStat");
                String errCode = rb.getProperty("errCode");
                String errMsg = rb.getProperty("errMsg");
                if (errStat.equals("-1")) {
                    errMsg = "Error: " + errCode + " " + errMsg;
                    FacesMessage fmsg = new FacesMessage(errMsg);
                    fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage("msg", fmsg);
                } else {
                    setList(new ArrayList<Campaign>());
                    setNewCampaign(false);
                }
            } catch (RbException ex) {
                Logger.getLogger(CampaignBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the newCashback
     */
    public String getNewCashback() {
        return newCashback;
    }

    /**
     * @param newCashback the newCashback to set
     */
    public void setNewCashback(String newCashback) {
        this.newCashback = newCashback;
    }

}
