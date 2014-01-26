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
import com.webfront.model.IBVDetail;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class CampaignBean {

    @ManagedProperty(value = "#{iBVDetail}")
    private IBVDetail detail;
    private ArrayList<Campaign> list;
    private Campaign activeCampaign;
    private String ibvMasterId;
    private String ibvDetailId;

    public CampaignBean() {
        this.list = new ArrayList<>();
        this.activeCampaign = new Campaign();
        this.ibvMasterId = new String();
        this.ibvDetailId = new String();
    }

    /**
     * @return the list
     */
    public ArrayList<Campaign> getList() {
        if(list != null && !list.isEmpty()) {
            list.clear();
        }
        if (this.detail != null && !this.detail.getId().isEmpty()) {
            RedObject rb = new RedObject("WDE", "AOP:Cashback");
            String sid = detail.getId();
            int idx = sid.indexOf("*");
            String ssid = sid.substring(idx + 1);
            rb.setProperty("ibvMasterId", detail.getIbvMasterId());
            rb.setProperty("ibvDetailId", ssid);
            try {
                rb.callMethod("getCampaignHist");
                String errStat = rb.getProperty("errStat").toString();
                String errCode = rb.getProperty("errCode").toString();
                String errMsg = rb.getProperty("errMsg").toString();
                int rows = Integer.valueOf(rb.getProperty("campaignCount").toString());
                getActiveCampaign();
                if(this.activeCampaign != null) {
                    list.add(this.activeCampaign);
                }
                if (rows > 0) {
                    UniDynArray cbBase=rb.getPropertyToDynArray("cbBase");
                    UniDynArray cbIncrease=rb.getPropertyToDynArray("cbIncrease");
                    UniDynArray cbTotal=rb.getPropertyToDynArray("cbTotal");
                    UniDynArray startDate=rb.getPropertyToDynArray("startDate");
                    UniDynArray endDate=rb.getPropertyToDynArray("endDate");
                    UniDynArray date=rb.getPropertyToDynArray("date");
                    UniDynArray time=rb.getPropertyToDynArray("time");
                    UniDynArray user=rb.getPropertyToDynArray("user");
                    UniDynArray status=rb.getPropertyToDynArray("status");
                    for (int row = 1; row <= rows; row++) {
                        Campaign campaign = new Campaign();
                        campaign.setCbBase(cbBase.extract(1,row).toString());
                        campaign.setCbIncrease(cbIncrease.extract(1,row).toString());
                        campaign.setCbTotal(cbTotal.extract(1,row).toString());
                        campaign.setStartDate(startDate.extract(1, row).toString());
                        campaign.setEndDate(endDate.extract(1, row).toString());
                        campaign.setEditDate(date.extract(1,row).toString());
                        campaign.setEditTime(time.extract(1, row).toString());
                        campaign.setUser(user.extract(1,row).toString());
                        campaign.setStatus(status.extract(1, row).toString());
                        list.add(campaign);
                    }
                }
            } catch (RbException ex) {
                Logger.getLogger(CampaignBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
        /**
         * @param list the list to set
         */
    public void setList(ArrayList<Campaign> list) {
        this.list = list;
    }

    public void changeCampaign(AjaxBehaviorEvent event) {
        System.out.println(event.toString());
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
            rb.setProperty("ibvMasterId", detail.getIbvMasterId());
            rb.setProperty("ibvDetailId", ssid);
            try {
                rb.callMethod("getCampaign");
                String errStat = rb.getProperty("errStat").toString();
                String errCode = rb.getProperty("errCode").toString();
                String errMsg = rb.getProperty("errMsg").toString();
                String s = rb.getProperty("startDate").toString();
                this.activeCampaign.setCbBase(rb.getProperty("cbBase"));
                this.activeCampaign.setCbIncrease(rb.getProperty("cbIncrease"));
                this.activeCampaign.setCbTotal(rb.getProperty("cbTotal"));
                this.activeCampaign.setStartDate(rb.getProperty("startDate"));
                this.activeCampaign.setEndDate(rb.getProperty("endDate"));
                this.activeCampaign.setEditDate(rb.getProperty("date"));
                this.activeCampaign.setEditTime(rb.getProperty("editTime"));
                this.activeCampaign.setUser(rb.getProperty("user"));
                this.activeCampaign.setStatus(rb.getProperty("status"));
            } catch (RbException ex) {
                Logger.getLogger(CampaignBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return activeCampaign;
    }

    /**
     * @param activeCampaign the activeCampaign to set
     */
    public void setActiveCampaign(Campaign activeCampaign) {
        this.activeCampaign = activeCampaign;
    }

    /**
     * @return the ibvMasterId
     */
    public String getIbvMasterId() {
        return ibvMasterId;
    }

    /**
     * @param ibvMasterId the ibvMasterId to set
     */
    public void setIbvMasterId(String ibvMasterId) {
        this.ibvMasterId = ibvMasterId;
    }

    /**
     * @return the ibvDetailId
     */
    public String getIbvDetailId() {
        return ibvDetailId;
    }

    /**
     * @param ibvDetailId the ibvDetailId to set
     */
    public void setIbvDetailId(String ibvDetailId) {
        this.ibvDetailId = ibvDetailId;
    }

    /**
     * @return the detailBean
     */
    public IBVDetail getDetail() {
        return detail;
    }

    /**
     * @param detailBean the detailBean to set
     */
    public void setDetail(IBVDetail detail) {
        this.detail = detail;
    }

}
