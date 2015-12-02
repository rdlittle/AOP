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
import com.webfront.model.SelectItem;
import com.webfront.util.JSFHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class CampaignBean {

    private ArrayList<Campaign> list;
    private List<Campaign> list2;
    private Campaign activeCampaign;
    private Campaign selectedCampaign;
    private String affiliateMasterId;
    private String affiliateDetailId;
    private Calendar calendar;
    private Date campaignStart;
    private Date campaignEnd;
    private boolean newCampaign;
    private String newCashback;
    private final ArrayList<SelectItem> campaignScope;
    private final ArrayList<SelectItem> storeList;
    private String scope;

    public CampaignBean() {
        list = new ArrayList<>();
        activeCampaign = new Campaign();
        affiliateMasterId = new String();
        affiliateDetailId = new String();
        calendar = Calendar.getInstance();
        newCampaign = false;
        list = new ArrayList<>();
        storeList = new ArrayList<>();
        campaignScope = new ArrayList<>();
        campaignScope.add(new SelectItem("C", "Current/Future"));
        campaignScope.add(new SelectItem("H", "Past"));
        campaignScope.add(new SelectItem("A", "All"));
        scope = "C";
    }
    
    public void init() {
        affiliateMasterId="";
        affiliateDetailId="";
        list.clear();
    }

    /**
     * @return the list
     */
    public ArrayList<Campaign> getList() {
        if (list != null && list.isEmpty()) {
            setList();
        }
        return list;
    }

   /**
     * @return the storeList
     */
    public ArrayList<SelectItem> getStoreList() {
        RedObject rb = new RedObject("WDE", "Affiliates:Detail");
        if (storeList != null) {
            if (!storeList.isEmpty()) {
                storeList.clear();
            }
        }
        rb.setProperty("masterId", affiliateMasterId);
        try {
            rb.callMethod("getAffiliateDetailList");
            String errStat = rb.getProperty("svrStatus");
            String errCode = rb.getProperty("svrCtrlCode");
            String errMsg = rb.getProperty("svrMessage");
            if (errStat.equals("-1")) {
                JSFHelper.sendFacesMessage(errCode+" "+errMsg);
            } else {
                UniDynArray keys = rb.getPropertyToDynArray("keyList");
                UniDynArray values = rb.getPropertyToDynArray("valueList");
                int vals = keys.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    String key = keys.extract(1, val).toString();
                    String value = values.extract(1, val).toString();
                    storeList.add(new SelectItem(key, value));
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateDetailBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storeList;
    }
    
    public void setList() {
        SimpleDateFormat dFmt = new SimpleDateFormat("MM/dd/yyyy");
        list.clear();
        if (affiliateMasterId != null && !affiliateMasterId.isEmpty()) {
            
            RedObject rb = new RedObject("WDE", "AOP:Cashback");
            String sid = affiliateDetailId;
            int idx = sid.indexOf("*");
            String ssid = "";
            if (idx != 0) {
                ssid = sid.substring(idx + 1);
            }
            rb.setProperty("affiliateMasterId", affiliateMasterId);
            rb.setProperty("affiliateDetailId", ssid);
            rb.setProperty("campaignScope", scope);
            if (campaignStart != null) {
                String sd = dFmt.format(campaignStart);
                rb.setProperty("startDate", dFmt.format(campaignStart));
            }
            if (campaignEnd != null) {
                rb.setProperty("endDate", dFmt.format(campaignEnd));
            }
            try {
                rb.callMethod("getCampaignHist");
                String errStat = rb.getProperty("svrStatus");
                String errCode = rb.getProperty("svrCtrlCode");
                String errMsg = rb.getProperty("svrMessage");
                if (errStat.equals("-1")) {
                    JSFHelper.sendFacesMessage(errCode + " " + errMsg);
                } else {
                    int rows = Integer.valueOf(rb.getProperty("campaignCount"));
                    if (rows > 0) {
                        UniDynArray idList = rb.getPropertyToDynArray("id");
                        UniDynArray cbBase = rb.getPropertyToDynArray("cbBase");
                        UniDynArray cbIncrease = rb.getPropertyToDynArray("cbIncrease");
                        UniDynArray cbTotal = rb.getPropertyToDynArray("cbTotal");
                        UniDynArray startDate = rb.getPropertyToDynArray("startDate");
                        UniDynArray endDate = rb.getPropertyToDynArray("endDate");
                        UniDynArray date = rb.getPropertyToDynArray("date");
                        UniDynArray time = rb.getPropertyToDynArray("time");
                        UniDynArray user = rb.getPropertyToDynArray("user");
                        UniDynArray status = rb.getPropertyToDynArray("status");
                        UniDynArray seqNums = rb.getPropertyToDynArray("seqNum");
                        UniDynArray storeIds = rb.getPropertyToDynArray("storeId");
                        UniDynArray storeNames = rb.getPropertyToDynArray("storeName");
                        for (int row = 1; row <= rows; row++) {
                            Campaign campaign = new Campaign();
                            campaign.setId(idList.extract(1, row).toString());
                            campaign.setCbBase(cbBase.extract(1, row).toString());
                            campaign.setCbIncrease(cbIncrease.extract(1, row).toString());
                            campaign.setCbTotal(cbTotal.extract(1, row).toString());
                            campaign.setStartDate(startDate.extract(1, row).toString());
                            campaign.setEndDate(endDate.extract(1, row).toString());
                            campaign.setEditDate(date.extract(1, row).toString());
                            campaign.setEditTime(time.extract(1, row).toString());
                            campaign.setUser(user.extract(1, row).toString());
                            campaign.setStatus(status.extract(1, row).toString());
                            campaign.setSeqNum(seqNums.extract(1, row).toString());
                            campaign.setStoreId(storeIds.extract(1, row).toString());
                            campaign.setStoreName(storeNames.extract(1, row).toString());
                            list.add(campaign);
                        }
                    }
                }
            } catch (RbException ex) {
                Logger.getLogger(CampaignBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void changeCampaign(AjaxBehaviorEvent event) {
        if (affiliateMasterId != null) {
            setList();
        }
    }
    
    public void getCampaignList(String masterId, String detailId) {
        affiliateMasterId = masterId;
        affiliateDetailId = detailId;
        setList();
    }

    public void deleteCampaign() {
        if (this.selectedCampaign != null) {
            RedObject rb = new RedObject("WDE", "AOP:Cashback");
            rb.setProperty("id", this.selectedCampaign.getId());
            try {
                rb.callMethod("deleteCampaign");
                String errStat = rb.getProperty("errStat");
                String errCode = rb.getProperty("errCode");
                String errMsg = rb.getProperty("errMsg");
                FacesMessage fmsg;
                Severity severity;
                if (errStat.equals("-1")) {
                    severity = FacesMessage.SEVERITY_ERROR;
                    errMsg = "Error: " + errCode + " " + errMsg;
                } else {
                    severity = FacesMessage.SEVERITY_INFO;
                    errMsg = "Campaign deleted";
                    list.remove(this.selectedCampaign);
                    this.selectedCampaign = null;
                }
                fmsg = new FacesMessage(errMsg);
                fmsg.setSeverity(severity);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } catch (RbException ex) {
                Logger.getLogger(CampaignBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the activeCampaign
     */
    public Campaign getActiveCampaign() {
        if (affiliateDetailId != null && !affiliateDetailId.isEmpty()) {
            RedObject rb = new RedObject("WDE", "AOP:Cashback");
            String sid = affiliateDetailId;
            int idx = sid.indexOf("*");
            String ssid = sid.substring(idx + 1);
            rb.setProperty("affiliateMasterId", affiliateMasterId);
            rb.setProperty("affiliateDetailId", ssid);
            this.activeCampaign = new Campaign();
            try {
                rb.callMethod("getCampaign");
                String errStat = rb.getProperty("errStat");
                String errCode = rb.getProperty("errCode");
                String errMsg = rb.getProperty("errMsg");
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
                    this.activeCampaign.setSeqNum(rb.getProperty("seqNum"));
                } else {
                    this.activeCampaign = null;
                }
            } catch (RbException ex) {
                Logger.getLogger(CampaignBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.activeCampaign;
    }
    
    public void onChangeMasterId(AjaxBehaviorEvent evt) {
        scope="C";
        getStoreList();
    }
    
    public void onClickListButton() {
        setList();
    }

    /**
     * @param activeCampaign the activeCampaign to set
     */
    public void setActiveCampaign(Campaign activeCampaign) {
        this.activeCampaign = activeCampaign;
    }

    /**
     * @return the affiliateMasterId
     */
    public String getAffiliateMasterId() {
        return affiliateMasterId;
    }

    /**
     * @param affiliateMasterId the affiliateMasterId to set
     */
    public void setAffiliateMasterId(String affiliateMasterId) {
        this.affiliateMasterId = affiliateMasterId;
    }

    /**
     * @return the affiliateDetailId
     */
    public String getAffiliateDetailId() {
        return affiliateDetailId;
    }

    /**
     * @param affiliateDetailId the affiliateDetailId to set
     */
    public void setAffiliateDetailId(String affiliateDetailId) {
        this.affiliateDetailId = affiliateDetailId;
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

    public void onCampaignCancel() {
        setNewCampaign(false);
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
            String sid = affiliateDetailId;
            int idx = sid.indexOf("*");
            String ssid = sid.substring(idx + 1);
            RedObject rb = new RedObject("WDE", "AOP:Cashback");
            rb.setProperty("affiliateMasterId", affiliateMasterId);
            rb.setProperty("affiliateDetailId", ssid);
            rb.setProperty("startDate", sDate);
            rb.setProperty("endDate", eDate);
            rb.setProperty("cbIncrease", this.getNewCashback());
            rb.setProperty("cbBase", "2.00");
            rb.setProperty("date", today);
            rb.setProperty("time", time);
            rb.setProperty("status", "0");
            rb.setProperty("seqNum", "");
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
                    setList();
                    setNewCampaign(false);
                }
            } catch (RbException ex) {
                Logger.getLogger(CampaignBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setCampaignStop() {
        FacesMessage fmsg = new FacesMessage("Campaign stopped");
        fmsg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage("msg", fmsg);
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

    /**
     * @return the selectedCampaign
     */
    public Campaign getSelectedCampaign() {
        return selectedCampaign;
    }

    /**
     * @param selectedCampaign the selectedCampaign to set
     */
    public void setSelectedCampaign(Campaign selectedCampaign) {
        this.selectedCampaign = selectedCampaign;
    }

    /**
     * @return the list2
     */
    public List<Campaign> getList2() {
        return list2;
    }

    /**
     * @param list2 the list2 to set
     */
    public void setList2(List<Campaign> list2) {
        this.list2 = list2;
    }

    public void onRowSelect(SelectEvent event) {
        selectedCampaign = (Campaign) event.getObject();
    }

    public void onRowUnselect(UnselectEvent event) {

    }

    /**
     * @return the campaignScope
     */
    public ArrayList<SelectItem> getCampaignScope() {
        return campaignScope;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

}
