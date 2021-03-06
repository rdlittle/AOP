/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.AffiliateOrder;
import com.webfront.model.AffiliateDetail;
import com.webfront.model.Aggregator;
import com.webfront.model.AffiliatePayment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class BatchManagementBean implements Serializable {

    private String mgmtRole;
    private Float checkAmt;
    private String masterId;
    private String userId;
    private int internalDate;
    private String externalDate;
    private Float balance;
    private Float convertedAmount;
    private boolean errorsOnly;
    private boolean ordersOnly;
    private Date batchMonth;
    private String currencyType;
    private String batchType;
    private String orderNumber;
    private String vendorName;
    private String storeName;
    @ManagedProperty(value = "#{affiliateDetailBean}")
    private AffiliateDetailBean detail;
    @ManagedProperty(value = "#{affiliateMaster}")
    private Aggregator affiliateMaster;
    @ManagedProperty(value = "#{affiliateDetail}")
    private AffiliateDetail affiliateDetail;
    private ArrayList<AffiliateOrder> searchResults;

    private static final Map<String, String> roleItems;

    static {
        roleItems = new LinkedHashMap<>();
        roleItems.put("PS", "PS");
        roleItems.put("QA", "QA");
    }

    public BatchManagementBean() {
        mgmtRole = "PS";
        checkAmt = Float.valueOf("123456.78");
        balance = Float.valueOf("0.00");
        convertedAmount = Float.valueOf("0.00");
        currencyType = "USD";
        batchType = "NP";
        errorsOnly = false;
    }

    @PostConstruct
    public void init() {
        detail.setOrdersOnly(true);
    }

    public String nextPage() {
        String nextPage = "/affiliate/orders/batchManager?faces-redirect=true";
        return nextPage;
    }

    public void changeValue(AjaxBehaviorEvent event) {
        this.affiliateMaster.changeVendor(event);
        this.masterId = this.affiliateMaster.getID();

        this.detail.setMasterId(masterId);
        this.detail.changeMasterId(event);
        this.detail.setStoreList(new ArrayList<>());
        this.vendorName = this.affiliateMaster.getName();
    }

    public void onCheckNumClicked() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("checkSelector", options, null);
    }

    public void onCheckSelected(SelectEvent selEvent) {
        AffiliatePayment payment = (AffiliatePayment) selEvent.getObject();
    }

    public void selectCheckFromDialog(AffiliatePayment pay) {
        RequestContext.getCurrentInstance().closeDialog(pay);
    }

    public void doSearch() {
        this.searchResults = new ArrayList<>();
        RedObject rb = new RedObject("WDE", "Affiliates:Orders");
        rb.setProperty("affiliateOrderNum", orderNumber);
        if (this.getMasterId() != null) {
            rb.setProperty("masterId", this.getMasterId());
        }
        if (this.affiliateDetail != null && !this.affiliateDetail.getId().isEmpty()) {
            rb.setProperty("divId", this.affiliateDetail.getId());
        }
        try {
            rb.callMethod("getAoSearch");
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
                UniDynArray orderIdList = rb.getPropertyToDynArray("orderId");
                UniDynArray affiliateMasterIdList = rb.getPropertyToDynArray("masterId");
                UniDynArray vendorDivList = rb.getPropertyToDynArray("divId");
                UniDynArray vendorOrderNumList = rb.getPropertyToDynArray("affiliateOrderNum");
                UniDynArray orderRefList = rb.getPropertyToDynArray("orderRef");
                UniDynArray orderDateList = rb.getPropertyToDynArray("orderDate");
                UniDynArray payingIdList = rb.getPropertyToDynArray("payingId");
                UniDynArray filingIdList = rb.getPropertyToDynArray("filingId");
                UniDynArray orderTotalList = rb.getPropertyToDynArray("orderTotal");
                UniDynArray ibvTotalList = rb.getPropertyToDynArray("ibvTotal");
                UniDynArray placementIdList = rb.getPropertyToDynArray("placementId");
                UniDynArray errorCountList = rb.getPropertyToDynArray("errorCount");
                UniDynArray commissionTotalList = rb.getPropertyToDynArray("commissionTotal");
                UniDynArray isHistoryList = rb.getPropertyToDynArray("isHistory");
                UniDynArray storeNameList = rb.getPropertyToDynArray("storeName");
                int vals = orderIdList.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    AffiliateOrder ao = new AffiliateOrder();
                    ao.setId(orderIdList.extract(1, val).toString());
                    ao.setVendorId(affiliateMasterIdList.extract(1, val).toString());
                    ao.setVendorDiv(vendorDivList.extract(1, val).toString());
                    ao.setVendorOrderNum(vendorOrderNumList.extract(1, val).toString());
                    ao.setOrderRef(orderRefList.extract(1, val).toString());
                    ao.setOrderDate(orderDateList.extract(1, val).toString());
                    ao.setPayingId(payingIdList.extract(1, val).toString());
                    ao.setFilingId(filingIdList.extract(1, val).toString());
                    ao.setOrderTotal(orderTotalList.extract(1, val).toString());
                    ao.setIbvTotal(ibvTotalList.extract(1, val).toString());
                    ao.setPlacementId(placementIdList.extract(1, val).toString());
                    ao.setErrorCount(errorCountList.extract(1, val).toString());
                    ao.setCommissionTotal(Float.valueOf(commissionTotalList.extract(1, val).toString()));
                    ao.setIsHistory("1".equals(isHistoryList.extract(1, val).toString()));
                    ao.setStoreName(storeNameList.extract(1, val).toString());
                    this.searchResults.add(ao);
                }
            }

        } catch (RbException ex) {
            Logger.getLogger(BatchManagementBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the checkAmt
     */
    public Float getCheckAmt() {
        return checkAmt;
    }

    /**
     * @param checkAmt the checkAmt to set
     */
    public void setCheckAmt(Float checkAmt) {
        this.checkAmt = checkAmt;
    }

    /**
     * @return the affiliateMasterId
     */
    public String getMasterId() {
        return masterId;
    }

    /**
     * @param affiliateMasterId the affiliateMasterId to set
     */
    public void setMasterId(String affiliateMasterId) {
        this.masterId = affiliateMasterId;
    }

    public Map<String, String> getMgmtRoles() {
        return roleItems;
    }

    public String getMgmtRole() {
        return mgmtRole;
    }

    public void setMgmtRole(String role) {
        this.mgmtRole = role;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the internalDate
     */
    public int getInternalDate() {
        return internalDate;
    }

    /**
     * @param internalDate the internalDate to set
     */
    public void setInternalDate(int internalDate) {
        this.internalDate = internalDate;
    }

    /**
     * @return the externalDate
     */
    public String getExternalDate() {
        return externalDate;
    }

    /**
     * @param externalDate the externalDate to set
     */
    public void setExternalDate(String externalDate) {
        this.externalDate = externalDate;
    }

    /**
     * @return the balance
     */
    public Float getBalance() {
        return this.balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(Float balance) {
        this.balance = balance;
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
     * @return the batchMonth
     */
    public Date getBatchMonth() {
        return batchMonth;
    }

    /**
     * @param batchMonth the batchMonth to set
     */
    public void setBatchMonth(Date batchMonth) {
        this.batchMonth = batchMonth;
    }

    /**
     * @return the currencyType
     */
    public String getCurrencyType() {
        return currencyType;
    }

    /**
     * @param currencyType the currencyType to set
     */
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    /**
     * @return the batchType
     */
    public String getBatchType() {
        return batchType;
    }

    /**
     * @param batchType the batchType to set
     */
    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    /**
     * @return the convertedAmount
     */
    public Float getConvertedAmount() {
        return convertedAmount;
    }

    /**
     * @param convertedAmount the convertedAmount to set
     */
    public void setConvertedAmount(Float convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    /**
     * @return the orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        setStoreName(detail.getStoreName());
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    /**
     * @return the affiliateMaster
     */
    public Aggregator getAffiliateMaster() {
        return affiliateMaster;
    }

    /**
     * @param affiliateMaster the affiliateMaster to set
     */
    public void setAffiliateMaster(Aggregator affiliateMaster) {
        this.affiliateMaster = affiliateMaster;
    }

    /**
     * @return the searchResults
     */
    public ArrayList<AffiliateOrder> getSearchResults() {
        return searchResults;
    }

    /**
     * @param searchResults the searchResults to set
     */
    public void setSearchResults(ArrayList<AffiliateOrder> searchResults) {
        this.searchResults = searchResults;
    }

    /**
     * @return the affiliateDetail
     */
    public AffiliateDetail getAffiliateDetail() {
        return affiliateDetail;
    }

    /**
     * @param affiliateDetail the affiliateDetail to set
     */
    public void setAffiliateDetail(AffiliateDetail affiliateDetail) {
        this.affiliateDetail = affiliateDetail;
    }

    /**
     * @return the ordersOnly
     */
    public boolean isOrdersOnly() {
        return ordersOnly;
    }

    /**
     * @param ordersOnly the ordersOnly to set
     */
    public void setOrdersOnly(boolean ordersOnly) {
        this.ordersOnly = ordersOnly;
    }

}
