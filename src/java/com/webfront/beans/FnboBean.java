/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.FnboTrans;
import com.webfront.util.DateUtils;
import com.webfront.util.JSFHelper;
import com.webfront.util.MVUtils;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rlittle
 */
@ApplicationScoped
@Named(value = "fnboBean")
public class FnboBean implements Serializable {

    private FnboTrans transItem = null;
    private String transId = "";
    private String memberId = "";
    private String arn = "";
    private String searchType;
    private ArrayList<FnboTrans> transList;
    private String transTotal;
    private Date startDate;
    private Date endDate;
    private boolean maOnly;
    private String queueCount;
    private final ArrayList<String> queueList;
    private boolean running;
    private boolean hasItems;
    private String btnProcLabel;
    private final LocalDate fnboStartDate = LocalDate.of(2015, 7, 28);
    private final Date rightNow = Calendar.getInstance().getTime();

    /**
     * Creates a new instance of FnboBean
     */
    public FnboBean() {
        transId = "";
        transItem = new FnboTrans();
        transList = new ArrayList<>();
        queueList = new ArrayList<>();
    }

    /**
     */
    public void lookupTransItem() {
        if (!transId.isEmpty()) {
            RedObject rbo;
            rbo = new RedObject("WDE", "Bank:Fnbo");
            rbo.setProperty("transId", transId);
            try {
                rbo.callMethod("getBankFnboTrans");
                String eStat = rbo.getProperty("svrStatus");
                int errStatus = 0;
                if (!eStat.isEmpty()) {
                    errStatus = Integer.parseInt(eStat);
                }
                String errCode = rbo.getProperty("svrCtrlCode");
                String errMessage = rbo.getProperty("svrMessage");
                if (errStatus == -1) {
                    setTransItem(new FnboTrans());
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                    ctx.addMessage(null, msg);
                } else {
                    FnboTrans trans = new FnboTrans();
                    trans.setId(rbo.getProperty("transId"));
                    trans.setTransDate(rbo.getProperty("transDate"));
                    trans.setArn(rbo.getProperty("arn"));
                    trans.setMemberId(rbo.getProperty("memberId"));
                    trans.setCardType(rbo.getProperty("cardType"));
                    trans.setTransAmt(rbo.getProperty("transAmt"));
                    trans.setMerchType(rbo.getProperty("merchType"));
                    trans.setMerchDesc(rbo.getProperty("merchDesc"));
                    trans.setTransCode(rbo.getProperty("transCode"));
                    if (trans.getId().isEmpty()) {
                        transId = "";
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        FacesMessage msg = new FacesMessage("Transaction not found");
                        ctx.addMessage(null, msg);
                    }
                    setTransItem(trans);
                    memberId = trans.getMemberId();
                    arn = trans.getArn();
                }
            } catch (RbException ex) {
                Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String onCreateReport(ActionEvent event) {
        SimpleDateFormat dateFmt = new SimpleDateFormat("MM/dd/yy");
        RedObject rbo;
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> requestMap = ctx.getExternalContext().getRequestParameterMap();
        String src = "";
        transList.clear();
        if (requestMap.containsKey("javax.faces.source")) {
            src = (String) requestMap.get("javax.faces.source");
        }
        if (!"form:fnboInquiryButton".equals(src)) {
            if ("form:byArn".equals(src)) {
                setSearchType("ARN");
            } else {
                setSearchType("Member Id");
            }
        }
        rbo = new RedObject("WDE", "Bank:Fnbo");
        if (searchType.equals("ARN")) {
            rbo.setProperty("arn", getArn());
        } else {
            rbo.setProperty("memberId", getMemberId());
        }
        if (startDate != null) {
            String sDate = dateFmt.format(startDate);
            rbo.setProperty("startDate", dateFmt.format(startDate));
        }
        if (endDate != null) {
            String eDate = dateFmt.format(endDate);
            rbo.setProperty("endDate", dateFmt.format(endDate));
        }
        rbo.setProperty("maOnly", maOnly ? "1" : "0");
        try {
            rbo.callMethod("getBankFnboTransReport");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String svrCtrlCode = rbo.getProperty("svrCtrlCode");
            String svrMessage = rbo.getProperty("svrMessage");
            if (svrStatus == -1) {
                JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
            } else {
                DecimalFormat fmt = new DecimalFormat("#,##0.00");
                setTransTotal("0.00");
                Float totalAmt = new Float(0);
                UniDynArray trans = rbo.getPropertyToDynArray("transId");
                trans.insert(2, rbo.getPropertyToDynArray("transDate"));
                trans.insert(3, rbo.getPropertyToDynArray("arn"));
                trans.insert(4, rbo.getPropertyToDynArray("memberId"));
                trans.insert(5, rbo.getPropertyToDynArray("cardType"));
                trans.insert(6, rbo.getPropertyToDynArray("transAmt"));
                trans.insert(7, rbo.getPropertyToDynArray("merchType"));
                trans.insert(8, rbo.getPropertyToDynArray("merchDesc"));
                trans.insert(9, rbo.getPropertyToDynArray("transCode"));
                trans.insert(10, rbo.getPropertyToDynArray("cardHolderName"));
                startDate = MVUtils.oConvDate(rbo.getProperty("startDate"));
                endDate = MVUtils.oConvDate(rbo.getProperty("endDate"));
                int transCount = trans.dcount(1);
                for (int t = 1; t <= transCount; t++) {
                    FnboTrans fTrans = new FnboTrans();
                    fTrans.setId(trans.extract(1, t).toString());
                    fTrans.setTransDate(trans.extract(2, t).toString());
                    fTrans.setArn(trans.extract(3, t).toString());
                    fTrans.setMemberId(trans.extract(4, t).toString());
                    fTrans.setCardType(trans.extract(5, t).toString());
                    fTrans.setTransAmt(trans.extract(6, t).toString());
                    fTrans.setMerchType(trans.extract(7, t).toString());
                    fTrans.setMerchDesc(trans.extract(8, t).toString());
                    fTrans.setTransCode(trans.extract(9, t).toString());
                    fTrans.setCardholderName(trans.extract(10, t).toString());
                    transList.add(fTrans);
                    if (t == 1) {
                        setTransItem(fTrans);
                    }
                    if (fTrans.getTransAmt() == null) {
                        fTrans.setTransAmt("0.00");
                    }
                    Float amt = fTrans.getTransAmt();
                    totalAmt += amt;
                    setTransTotal(fmt.format(totalAmt));
                }
                return "/fnboTransReport.xhtml?faces-redirect=true";
            }
        } catch (RbException ex) {
            Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String onInquiryFormSubmit() {
        if (!transId.isEmpty()) {
            searchType = "Transaction";
            lookupTransItem();
            return "/fnboTrans.xhtml?faces-redirect=true";
        } else if (!arn.isEmpty()) {
            searchType = "ARN";
            return (onCreateReport(null));
        } else if (!memberId.isEmpty()) {
            searchType = "Member Id";
            return (onCreateReport(null));
        }
        return "";
    }

    public void onProcessQueue() {
        try {
            RedObject rbo;
            rbo = new RedObject("WDE", "Bank:Fnbo");
            rbo.callMethod("setBankFnboPhantom");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String svrCtrlCode = rbo.getProperty("svrCtrlCode");
            String svrMessage = rbo.getProperty("svrMessage");
            if (svrStatus == -1) {
                JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
            } else {
                setBtnProcLabel("Running");
                setRunning(true);
            }
        } catch (RbException ex) {
            Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onResetInputForm() {
        transItem = null;
        transId = "";
        memberId = "";
        arn = "";
        searchType = "";
        startDate = null;
        endDate = null;
        maOnly = false;
    }

    public void onRepollQueue() {
        setQueueList();
    }

    public void onSelectEndDate(SelectEvent event) {
        if (endDate != null) {
            if (startDate == null || startDate.after(endDate)) {
                LocalDate lsDate = DateUtils.ofUtilDate(endDate);
                lsDate = lsDate.minusMonths(1);
                startDate = DateUtils.ofLocalDate(lsDate);
            }
        }
    }

    public void onSelectStartDate(SelectEvent event) {
        LocalDate today = LocalDate.now();
        if (startDate != null) {
            if (startDate.before(DateUtils.ofLocalDate(fnboStartDate))) {
                startDate = DateUtils.ofLocalDate(fnboStartDate);
            }
            if (endDate == null || endDate.before(startDate)) {
                LocalDate ld = DateUtils.ofUtilDate(startDate);
                ld = ld.plusMonths(1);
                if(ld.isAfter(today)) {
                    endDate = DateUtils.ofLocalDate(today);
                } else {
                    endDate = DateUtils.ofLocalDate(ld);
                }
            }
        }
    }

    public void setQueueList() {
        queueList.clear();
        try {
            RedObject rbo;
            rbo = new RedObject("WDE", "Bank:Fnbo");
            rbo.callMethod("getBankFnboQueue");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String svrCtrlCode = rbo.getProperty("svrCtrlCode");
            String svrMessage = rbo.getProperty("svrMessage");
            if (svrStatus == -1) {
                JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
            } else {
                UniDynArray uda = rbo.getPropertyToDynArray("queueCount");
                uda.insert(2, rbo.getPropertyToDynArray("queueList"));
                String runFlag = rbo.getProperty("isRunning");
                queueCount = uda.extract(1).toString();
                int items = Integer.parseInt(queueCount);
                for (int i = 1; i <= items; i++) {
                    queueList.add(uda.extract(2, i).toString());
                }
                hasItems = (items > 0);
                running = (runFlag.equals("1"));
                if (running) {
                    setBtnProcLabel("Running");
                } else if (hasItems) {
                    setBtnProcLabel("Process");
                } else {
                    setBtnProcLabel("No Items");
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the transItem
     */
    public FnboTrans getTransItem() {
        return transItem;
    }

    public boolean getHasItems() {
        return hasItems;
    }

    public void setHasItems(boolean b) {
        hasItems = b;
    }

    public void setTransItem(FnboTrans item) {
        transItem = item;
    }

    public void onTransItemButtonClick() {
        lookupTransItem();
    }

    /**
     * @return the transId
     */
    public String getTransId() {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * @return the transList
     */
    public ArrayList<FnboTrans> getTransList() {
        return transList;
    }

    /**
     * @param transList the transList to set
     */
    public void setTransList(ArrayList<FnboTrans> transList) {
        this.transList = transList;
    }

    /**
     * @return the memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * @return the arn
     */
    public String getArn() {
        return arn;
    }

    /**
     * @param arn the arn to set
     */
    public void setArn(String arn) {
        this.arn = arn;
    }

    public Date getFnboStartDate() {
        return DateUtils.ofLocalDate(fnboStartDate);
    }
    
    public Date getRightNow() {
        return rightNow;
    }
    /**
     * @return the searchType
     */
    public String getSearchType() {
        return searchType;
    }

    /**
     * @param searchType the searchType to set
     */
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    /**
     * @return the transTotal
     */
    public String getTransTotal() {
        return transTotal;
    }

    /**
     * @param transTotal the transTotal to set
     */
    public void setTransTotal(String transTotal) {
        this.transTotal = transTotal;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the maOnly
     */
    public boolean isMaOnly() {
        return maOnly;
    }

    /**
     * @param maOnly the maOnly to set
     */
    public void setMaOnly(boolean maOnly) {
        this.maOnly = maOnly;
    }

    /**
     * @return the queueCount
     */
    public String getQueueCount() {
        return queueCount;
    }

    /**
     * @param queueCount the queueCount to set
     */
    public void setQueueCount(String queueCount) {
        this.queueCount = queueCount;
    }

    /**
     * @return the queueList
     */
    public ArrayList<String> getQueueList() {
        return queueList;
    }

    /**
     * @return the processed
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param processed the processed to set
     */
    public void setRunning(boolean processed) {
        this.running = processed;
    }

    /**
     * @return the btnProcLabel
     */
    public String getBtnProcLabel() {
        return btnProcLabel;
    }

    /**
     * @param btnProcLabel the btnProcLabel to set
     */
    public void setBtnProcLabel(String btnProcLabel) {
        this.btnProcLabel = btnProcLabel;
    }

}
