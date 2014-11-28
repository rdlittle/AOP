/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.AffiliateOrder;
import java.io.Serializable;
import java.util.ArrayList;
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
public class AffiliateOrderBean implements Serializable {

    @ManagedProperty(value = "#{affiliateDetailBean}")
    private AffiliateDetailBean detail;
    private String affiliateMasterId;
    private String affiliateDetailId;
    private boolean errorsOnly;

    private ArrayList<AffiliateOrder> orderList;

    public AffiliateOrderBean() {
        this.errorsOnly = true;
    }

    /**
     * @return the orderList
     */
    public ArrayList<AffiliateOrder> getOrderList() {
        return this.orderList;
    }

    public void setOrderList() {
        this.orderList = new ArrayList<>();
        this.orderList.clear();
        if (affiliateMasterId != null && !affiliateMasterId.isEmpty()) {
            RedObject rb = new RedObject("WDE", "AOP:AffiliateOrders");
            rb.setProperty("vendorId", affiliateMasterId);
            String vendorDiv = this.affiliateDetailId;
            if (vendorDiv != null && !"".equals(vendorDiv)) {
                int idx = vendorDiv.indexOf("*");
                if (idx > 0) {
                    vendorDiv = vendorDiv.substring(idx + 1);
                    rb.setProperty("vendorDiv", vendorDiv);
                }
            }
            rb.setProperty("errorsOnly", errorsOnly ? "1" : "0");
            try {
                rb.callMethod("getOrders");
                String errStat = rb.getProperty("errStat");
                String errCode = rb.getProperty("errCode");
                String errMsg = rb.getProperty("errMsg");
                if (errStat.equals("-1")) {
                    errMsg = "Error: " + errCode + " " + errMsg;
                    FacesMessage fmsg = new FacesMessage(errMsg);
                    fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage("msgs", fmsg);
                } else {
                    UniDynArray orderIdList = rb.getPropertyToDynArray("orderId");
                    UniDynArray orderRefList = rb.getPropertyToDynArray("orderRef");
                    UniDynArray orderDateList = rb.getPropertyToDynArray("orderDate");
                    UniDynArray payingIdList = rb.getPropertyToDynArray("payingId");
                    UniDynArray filingIdList = rb.getPropertyToDynArray("filingId");
                    UniDynArray orderTotalList = rb.getPropertyToDynArray("orderTotal");
                    UniDynArray ibvTotalList = rb.getPropertyToDynArray("ibvTotal");
                    UniDynArray placementIdList = rb.getPropertyToDynArray("placementId");
                    UniDynArray errorCountList = rb.getPropertyToDynArray("errorCount");
                    UniDynArray vendorOrderNumList = rb.getPropertyToDynArray("vendorOrderNum");
                    UniDynArray commissionTotalList = rb.getPropertyToDynArray("commissionTotal");
                    UniDynArray storeNameList = rb.getPropertyToDynArray("storeName");

                    int vals = orderIdList.dcount(1);
                    for (int val = 1; val <= vals; val++) {
                        AffiliateOrder ao = new AffiliateOrder();
                        ao.setId(orderIdList.extract(1, val).toString());
                        ao.setOrderRef(orderRefList.extract(1, val).toString());
                        ao.setOrderDate(orderDateList.extract(1, val).toString());
                        ao.setPayingId(payingIdList.extract(1, val).toString());
                        ao.setFilingId(filingIdList.extract(1, val).toString());
                        ao.setOrderTotal(orderTotalList.extract(1, val).toString());
                        ao.setIbvTotal(ibvTotalList.extract(1, val).toString());
                        ao.setPlacementId(placementIdList.extract(1, val).toString());
                        ao.setErrorCount(errorCountList.extract(1, val).toString());
                        ao.setVendorOrderNum(vendorOrderNumList.extract(1, val).toString());
                        ao.setCommissionTotal(Float.valueOf(commissionTotalList.extract(1, val).toString()));
                        ao.setStoreName(storeNameList.extract(1, val).toString());
                        this.orderList.add(ao);
                    }
                }
            } catch (RbException ex) {
                Logger.getLogger(AffiliateOrderBean.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage fmsg = new FacesMessage(ex.getMessage());
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            }
        }
    }

    /**
     * @param orderList the orderList to set
     */
    public void setOrderList(ArrayList<AffiliateOrder> orderList) {
        this.orderList = orderList;
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
     * @return the errorsOnly
     */
    public boolean isErrorsOnly() {
        return errorsOnly;
    }

    /**
     * @param errorsOnly the errorsOnly to set
     */
    public void setErrorsOnly(boolean errorsOnly) {
        this.errorsOnly = errorsOnly;
    }

    /**
     * @return the detail
     */
    public AffiliateDetailBean getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(AffiliateDetailBean detail) {
        this.detail = detail;
    }

    public void changeHandler(AjaxBehaviorEvent event) {
        if (this.detail != null) {
            if (this.detail.getAffiliateMasterId() != null && !"".equals(this.detail.getAffiliateMasterId())) {
                this.setAffiliateMasterId(this.detail.getAffiliateMasterId());
            }
            if (this.detail.getAffiliateDetailId() != null && !"".equals(this.detail.getAffiliateDetailId())) {
                this.setAffiliateDetailId(this.detail.getAffiliateDetailId());
                detail.changeStore(event);
            }
            setOrderList();
        }
    }
}
