/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.controller.AopQueueController;
import com.webfront.model.Aggregator;
import com.webfront.model.AffiliatePayment;
import com.webfront.model.AopQueue;
import com.webfront.model.UVException;
import com.webfront.util.JSFHelper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AffiliatePaymentBean {

    private String id;
    private String checkId;
    private String networkId;
    private String vendorId;
    private String checkAmount;
    private Date checkDate;
    private Date postDate;
    private String releasedAmount;
    private String balance;
    private UploadedFile fileName;
    private AffiliatePayment selectedItem;
    private ArrayList<AffiliatePayment> paymentList;
    @ManagedProperty(value = "#{uploadBean}")
    UploadBean uploader;
    public final RedObject rbo = new RedObject("WDE", "Affiliates:Payment");
    private final AopQueueController controller = new AopQueueController();

    public AffiliatePaymentBean() {
        selectedItem = new AffiliatePayment();
        id = "";
        checkId = "";
        networkId = "";
        vendorId = "";
        checkAmount = "";
        checkDate = Calendar.getInstance(Locale.getDefault()).getTime();
        paymentList = new ArrayList<>();
    }
    
    public void setUploader(UploadBean ub) {
        this.uploader = ub;
    }

    public UploadBean getUploader() {
        return this.uploader;
    }

    public String doUpload() {
        boolean result;
        getUploader().setFile(fileName);
        result = uploader.upload();
        if (!result) {
            return "";
        }
        AopQueue queueItem = new AopQueue();
        queueItem.setAggregatorId(this.networkId);
        queueItem.setCheckAmount(this.checkAmount);
        queueItem.setFileName(fileName.getFileName());
        queueItem.setQueueType("C");
        queueItem.setCheckId(this.checkId);
        queueItem.setCheckAmount(this.checkAmount);
        queueItem.setCheckDate(checkDateToString());
        queueItem.setLineCount("");
        queueItem.setOrderCount("");
        queueItem.setErrorReport("");
        queueItem.setSuccessReport("");
        controller.setQueueItem(queueItem);
        try {
            controller.createQueue();
        } catch (UVException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("msg", ex.toFacesMessage());
            Logger.getLogger(AffiliatePaymentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/affiliate/queue/aopQueue?faces-redirect=true";
    }

    /**
     * @return the selectedItem
     */
    public AffiliatePayment getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(AffiliatePayment selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * @return the paymentList
     */
    public ArrayList<AffiliatePayment> getPaymentList() {
        setPaymentList(null);
        return paymentList;
    }

    /**
     * @param paymentList the paymentList to set
     */
    public void setPaymentList(ArrayList<AffiliatePayment> paymentList) {
        Map<String,Object> map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if(map.containsKey("affiliateMaster")) {
            vendorId = ((Aggregator)map.get("affiliateMaster")).getID();
        }
        try {
            this.paymentList.clear();
            rbo.setProperty("networkId", selectedItem.getNetworkId());
            rbo.setProperty("vendorId", vendorId.isEmpty() ? "" : vendorId);
            rbo.setProperty("checkId", checkId);
            rbo.setProperty("paymentId", getId());
            rbo.callMethod("getPayment");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            if (svrStatus == -1) {
                String errCode = rbo.getProperty("svrCtrlCode");
                String errMessage = rbo.getProperty("svrMessage");
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                UniDynArray uda = rbo.getPropertyToDynArray("paymentId");
                uda.insert(2, rbo.getPropertyToDynArray("checkDate"));
                uda.insert(3, rbo.getPropertyToDynArray("userName"));
                uda.insert(4, rbo.getPropertyToDynArray("releasedAmount"));
                uda.insert(5, rbo.getPropertyToDynArray("balance"));
                uda.insert(6, rbo.getPropertyToDynArray("checkId"));
                uda.insert(7, rbo.getPropertyToDynArray("networkId"));
                uda.insert(8, rbo.getPropertyToDynArray("postDate"));
                uda.insert(9, rbo.getPropertyToDynArray("vendorId"));
                int pCount = uda.dcount(1);
                for (int val = 1; val <= pCount; val++) {
                    AffiliatePayment p = new AffiliatePayment();
                    p.setId(uda.extract(1, val).toString());
                    p.setAffiliateMasterId(vendorId);
                    p.setCheckDate(uda.extract(2, val).toString());
                    p.setUserName(uda.extract(3, val).toString());
                    p.setReleasedAmount(uda.extract(4, val).toString());
                    p.setBalance(uda.extract(5, val).toString());
                    p.setCheckId(uda.extract(6, val).toString());
                    p.setNetworkId(uda.extract(7, val).toString());
                    p.setPostDate(uda.extract(8, val).toString());
                    p.setAffiliateMasterId(uda.extract(9, val).toString());
                    this.paymentList.add(p);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliatePaymentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPayment() {
        try {
            rbo.setProperty("networkId", networkId);
            rbo.setProperty("checkId", checkId);
            rbo.setProperty("paymentId", getId());
            rbo.callMethod("getPayment");
            int errStat = Integer.parseInt(rbo.getProperty("errStat"));
            if (errStat < 0) {
                String errCode = rbo.getProperty("errCode");
                String errMsg = rbo.getProperty("errMsg");
                errMsg = "Error: " + errCode + " " + errMsg;
                FacesMessage fmsg = new FacesMessage(errMsg);
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                selectedItem = new AffiliatePayment();
                setId(rbo.getProperty("paymentId"));
                setCheckId(rbo.getProperty("checkId"));
                setCheckAmount(rbo.getProperty("checkAmount"));
                setCheckDate(rbo.getProperty("checkDate"));
                setPostDate(rbo.getProperty("postDate"));
                setReleasedAmount(rbo.getProperty("releasedAmount"));
                setBalance(rbo.getProperty("balance"));
                selectedItem.setNetworkId(rbo.getProperty("networkId"));
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliatePaymentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the checkId
     */
    public String getCheckId() {
        return checkId;
    }

    /**
     * @param checkId the checkId to set
     */
    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * @return the vendorId
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return the checkAmount
     */
    public String getCheckAmount() {
        return checkAmount;
    }

    /**
     * @param checkAmount the checkAmount to set
     */
    public void setCheckAmount(String checkAmount) {
        this.checkAmount = checkAmount;
    }

    /**
     * @return the checkDate
     */
    public Date getCheckDate() {
        return checkDate;
    }

    public String checkDateToString() {
        SimpleDateFormat dfmt = new SimpleDateFormat("MM/dd/yyyy");
        return dfmt.format(checkDate);
    }

    /**
     * @param checkDate the checkDate to set
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public void setCheckDate(String checkDate) {
        if (!"".equals(checkDate)) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
            try {
                this.checkDate = df.parse(checkDate);
            } catch (ParseException ex) {
                Logger.getLogger(AffiliatePaymentBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the postDate
     */
    public Date getPostDate() {
        return postDate;
    }

    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public void setPostDate(String postDate) {
        if (!"".equals(postDate)) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
            try {
                this.postDate = df.parse(postDate);
            } catch (ParseException ex) {
                Logger.getLogger(AffiliatePaymentBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * @return the releasedAmount
     */
    public String getReleasedAmount() {
        return releasedAmount;
    }

    /**
     * @param releasedAmount the releasedAmount to set
     */
    public void setReleasedAmount(String releasedAmount) {
        this.releasedAmount = releasedAmount;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the fileName
     */
    public UploadedFile getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(UploadedFile fileName) {
        this.fileName = fileName;
    }

}
