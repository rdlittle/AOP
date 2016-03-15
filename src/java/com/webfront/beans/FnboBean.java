/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.Award;
import com.webfront.model.CardHolder;
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
    private String referenceNum;
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
    private final ArrayList<CardHolder> cardHolderList;
    private CardHolder cardHolder;
    private final ArrayList<CardHolder> selectedCardHolders = new ArrayList<>();
    String searchTarget;
    private boolean created;
    private boolean clearable;
    private boolean submitted;

    /**
     * Creates a new instance of FnboBean
     */
    public FnboBean() {
        transId = "";
        transItem = new FnboTrans();
        transList = new ArrayList<>();
        queueList = new ArrayList<>();
        cardHolderList = new ArrayList<>();
        cardHolder = new CardHolder();
        hasItems = false;
        referenceNum = "";
        created = false;
        clearable = false;
        submitted = false;
    }

    /**
     */
    public void lookupTransItem() {
        if (!transId.isEmpty()) {
            RedObject rbo;
            rbo = new RedObject("WDE", "Bank:Fnbo");
            rbo.setProperty("fnboXrefId", transId);
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
                    trans.setId(rbo.getProperty("fnboXrefId"));
                    trans.setTransDate(rbo.getProperty("transDate"));
                    trans.setArn(rbo.getProperty("arn"));
                    trans.setMemberId(rbo.getProperty("memberId"));
                    trans.setMerchType(rbo.getProperty("merchType"));
                    trans.setTransAmt(rbo.getProperty("transAmt"));
                    trans.setCardType(rbo.getProperty("cardType"));
                    trans.setMerchDesc(rbo.getProperty("merchDesc"));

                    trans.setTransCode(rbo.getProperty("transCode"));
                    trans.setCardholderName(rbo.getProperty("cardHolderName"));
                    trans.setAwardType(rbo.getProperty("awardType"));
                    trans.setFileDate(rbo.getProperty("fileDate"));
                    trans.setLineNum(rbo.getProperty("lineNum"));

                    UniDynArray awardArray = new UniDynArray();
                    awardArray.insert(1, rbo.getPropertyToDynArray("orderId"));
                    awardArray.insert(2, rbo.getPropertyToDynArray("orderSrp"));
                    awardArray.insert(3, rbo.getPropertyToDynArray("awardAmt"));
                    awardArray.insert(4, rbo.getPropertyToDynArray("referenceNum"));
                    awardArray.insert(5, rbo.getPropertyToDynArray("awardPct"));

                    for (int i = 1; i <= trans.getAwardCount(); i++) {
                        Award award = new Award();
                        award.setOrderNumber(awardArray.extract(1, i).toString());
                        award.setOrderSrp(awardArray.extract(2, i).toString());
                        award.setAwardAmt(awardArray.extract(3, i).toString());
                        award.setReferenceNum(awardArray.extract(4, i).toString());
                        award.setAwardPct(awardArray.extract(5, i).toString());
                        trans.addAward(award);
                    }
                    trans.setOriginalOrder(rbo.getProperty("originalOrder"));

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

    public String onTransReprocess() {
        RedObject rbo = new RedObject("WDE", "Bank:Fnbo");
        if(cardHolder.getMemberId().isEmpty() || cardHolder.getArn().isEmpty()) {
            JSFHelper.sendFacesMessage("Missing info", "Error");
            return "";
        }
        rbo.setProperty("arn", cardHolder.getArn());
        rbo.setProperty("memberId", cardHolder.getMemberId());        
        try {
            rbo.callMethod("putBankFnboReprocess");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String svrCtrlCode = rbo.getProperty("svrCtrlCode");
            String svrMessage = rbo.getProperty("svrMessage");
            if (svrStatus == -1) {
                JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
            } else {
                JSFHelper.sendFacesMessage("Reprocess OK", "Info");
                submitted = false;
                return "/fnboProcessing.xhtml?faces-redirect=true";
            }
        } catch (RbException ex) {
            Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String onTransSearch(ActionEvent event) {
        RedObject rbo = new RedObject("WDE", "Bank:Fnbo");
        rbo.setProperty("fnboXrefId", transId);
        rbo.setProperty("referenceNum", referenceNum);
        transList.clear();
        try {
            rbo.callMethod("getBankFnboTransSearch");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String svrCtrlCode = rbo.getProperty("svrCtrlCode");
            String svrMessage = rbo.getProperty("svrMessage");
            if (svrStatus == -1) {
                JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
            } else {
                buildReport(rbo);
            }
        } catch (RbException ex) {
            Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void onTransSearchReset(ActionEvent event) {
        transId = "";
        referenceNum = "";
        transItem = new FnboTrans();
        transList.clear();
    }

    public String onCreateReport(ActionEvent event) {
        SimpleDateFormat dateFmt = new SimpleDateFormat("MM/dd/yy");
        RedObject rbo;
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> requestMap = ctx.getExternalContext().getRequestParameterMap();
        String src = "";
        transList.clear();
        transId = "";

        if (requestMap.containsKey("searchType")) {
            setSearchType(requestMap.get("searchType"));
        }
        if (requestMap.containsKey("searchTarget")) {
            searchTarget = requestMap.get("searchTarget");
        }
        rbo = new RedObject("WDE", "Bank:Fnbo");
        if (searchType.equalsIgnoreCase("arn")) {
            rbo.setProperty("arn", searchTarget);
        } else {
            rbo.setProperty("memberId", searchTarget);
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
                buildReport(rbo);
                return "/fnboTransReport.xhtml?faces-redirect=true";
            }
        } catch (RbException ex) {
            Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void buildReport(RedObject rbo) {
        DecimalFormat fmt = new DecimalFormat("#,##0.00");
        setTransTotal("0.00");
        Float totalAmt = new Float(0);
        UniDynArray trans = rbo.getPropertyToDynArray("fnboXrefId");

        trans.insert(2, rbo.getPropertyToDynArray("transDate"));
        trans.insert(3, rbo.getPropertyToDynArray("arn"));
        trans.insert(4, rbo.getPropertyToDynArray("memberId"));
        trans.insert(5, rbo.getPropertyToDynArray("merchType"));
        trans.insert(6, rbo.getPropertyToDynArray("transAmt"));
        trans.insert(7, rbo.getPropertyToDynArray("cardType"));
        trans.insert(8, rbo.getPropertyToDynArray("merchDesc"));
        trans.insert(9, rbo.getPropertyToDynArray("transCode"));
        trans.insert(10, rbo.getPropertyToDynArray("cardHolderName"));
        trans.insert(13, rbo.getPropertyToDynArray("orderId"));
        trans.insert(14, rbo.getPropertyToDynArray("orderSrp"));
        trans.insert(15, rbo.getPropertyToDynArray("awardAmt"));
        trans.insert(16, rbo.getPropertyToDynArray("nameList"));
        trans.insert(17, rbo.getPropertyToDynArray("referenceNum"));
        trans.insert(18, rbo.getPropertyToDynArray("awardType"));
        trans.insert(19, rbo.getPropertyToDynArray("fileDate"));
        trans.insert(20, rbo.getPropertyToDynArray("lineNum"));
        trans.insert(21, rbo.getPropertyToDynArray("originalOrder"));
        trans.insert(23, rbo.getPropertyToDynArray("awardCount"));
        trans.insert(24, rbo.getPropertyToDynArray("awardPct"));
        startDate = MVUtils.oConvDate(rbo.getProperty("startDate")); // oList<11>
        endDate = MVUtils.oConvDate(rbo.getProperty("endDate"));     // oList<12>

        int transCount = trans.dcount(1);
        for (int t = 1; t <= transCount; t++) {
            FnboTrans fTrans = new FnboTrans();
            fTrans.setId(trans.extract(1, t).toString());
            fTrans.setTransDate(trans.extract(2, t).toString());
            fTrans.setArn(trans.extract(3, t).toString());
            fTrans.setMemberId(trans.extract(4, t).toString());
            fTrans.setMerchType(trans.extract(5, t).toString());
            fTrans.setTransAmt(trans.extract(6, t).toString());
            fTrans.setCardType(trans.extract(7, t).toString());
            fTrans.setMerchDesc(trans.extract(8, t).toString());
            fTrans.setTransRef(trans.extract(17, t).toString());
            fTrans.setTransCode(trans.extract(9, t).toString());
            fTrans.setCardholderName(trans.extract(16, t).toString());
            fTrans.setAwardType(trans.extract(18, t).toString());
            fTrans.setFileDate(trans.extract(19, t).toString());
            fTrans.setLineNum(trans.extract(20, t).toString());
            fTrans.setOriginalOrder(trans.extract(21, t).toString());

            int awardCount = Integer.parseInt(trans.extract(23, t).toString());
            for (int a = 1; a <= awardCount; a++) {
                Award award = new Award();
                award.setOrderNumber(trans.extract(13, t, a).toString());
                award.setOrderSrp(trans.extract(14, t, a).toString());
                award.setAwardAmt(trans.extract(15, t, a).toString());
                award.setReferenceNum(trans.extract(17, t, a).toString());
                award.setAwardPct(trans.extract(24, t, a).toString());
                fTrans.addAward(award);
            }

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
    }

    public void onCardHolderClearForm() {
        cardHolder = new CardHolder();
        cardHolderList.clear();
        hasItems = false;
        created = false;
        clearable = false;
        submitted = false;
    }

    public void onCardHolderCreate() {
        RedObject rbo;
        rbo = new RedObject("WDE", "Bank:Member");
        rbo.setProperty("memberId", cardHolder.getMemberId());
        rbo.setProperty("arn", cardHolder.getArn());
        rbo.setProperty("cardType", cardHolder.getCardType());
        try {
            rbo.callMethod("postBankFnboArn");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String svrCtrlCode = rbo.getProperty("svrCtrlCode");
            String svrMessage = rbo.getProperty("svrMessage");
            if (svrStatus == -1) {
                JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
            } else {
                JSFHelper.sendFacesMessage("Cardholder created", "Info");
                submitted = true;
            }
        } catch (RbException ex) {
            Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onCardHolderLookup() {
        if (cardHolder != null) {
            cardHolderList.clear();
            RedObject rbo;
            rbo = new RedObject("WDE", "Bank:Member");
            rbo.setProperty("arn", cardHolder.getArn());
            rbo.setProperty("memberId", cardHolder.getMemberId());
            rbo.setProperty("firstName", cardHolder.getFirstName());
            rbo.setProperty("lastName", cardHolder.getLastName());
            rbo.setProperty("address1", cardHolder.getAddress1());
            rbo.setProperty("emailAddress", cardHolder.getEmailAddress());
            try {
                rbo.callMethod("getBankFnboMember");
                int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
                String svrCtrlCode = rbo.getProperty("svrCtrlCode");
                String svrMessage = rbo.getProperty("svrMessage");
                if (svrStatus == -1) {
                    JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
                } else {
                    int itemCount = Integer.parseInt(rbo.getProperty("itemCount"));
                    hasItems = itemCount > 0;
                    UniDynArray oList = new UniDynArray();

                    oList.insert(1, rbo.getPropertyToDynArray("arn"));
                    oList.insert(2, rbo.getPropertyToDynArray("lastName"));
                    oList.insert(3, rbo.getPropertyToDynArray("firstName"));
                    oList.insert(4, rbo.getPropertyToDynArray("middleInitial"));
                    oList.insert(5, rbo.getPropertyToDynArray("prefix"));
                    oList.insert(6, rbo.getPropertyToDynArray("suffix"));
                    oList.insert(7, rbo.getPropertyToDynArray("address1"));
                    oList.insert(8, rbo.getPropertyToDynArray("address2"));
                    oList.insert(9, rbo.getPropertyToDynArray("city"));
                    oList.insert(10, rbo.getPropertyToDynArray("state"));
                    oList.insert(11, rbo.getPropertyToDynArray("zipCode"));
                    oList.insert(12, rbo.getPropertyToDynArray("phone1"));
                    oList.insert(13, rbo.getPropertyToDynArray("memberId"));
                    oList.insert(14, rbo.getPropertyToDynArray("emailAddress"));
                    oList.insert(15, rbo.getPropertyToDynArray("acquisitionCode"));
                    oList.insert(16, rbo.getPropertyToDynArray("fileDate"));

                    for (int i = 1; i <= itemCount; i++) {
                        CardHolder member = new CardHolder();
                        member.setArn(oList.extract(1, i).toString());
                        member.setLastName(oList.extract(2, i).toString());
                        member.setFirstName(oList.extract(3, i).toString());
                        member.setMiddleInitial(oList.extract(4, i).toString());
                        member.setPrefix(oList.extract(5, i).toString());
                        member.setSuffix(oList.extract(6, i).toString());
                        member.setAddress1(oList.extract(7, i).toString());
                        member.setAddress2(oList.extract(8, i).toString());
                        member.setCity(oList.extract(9, i).toString());
                        member.setState(oList.extract(10, i).toString());
                        member.setZipCode(oList.extract(11, i).toString());
                        member.setPhone1(oList.extract(12, i).toString());
                        member.setMemberId(oList.extract(13, i).toString());
                        member.setEmailAddress(oList.extract(14, i).toString());
                        member.setAcquisitionCode(oList.extract(15, i).toString());
                        member.setFileDate(oList.extract(16, i).toString());
                        cardHolderList.add(member);
                    }
                }
            } catch (RbException ex) {
                Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String onInquiryFormSubmit() {
        if (!searchType.isEmpty() && !searchTarget.isEmpty()) {
            return onCreateReport(null);
        }
        if (!transId.isEmpty()) {
            searchType = "Transaction";
            lookupTransItem();
            return "/fnboTrans.xhtml?faces-redirect=true";
        } else if (!arn.isEmpty()) {
            setSearchType("arn");
            searchTarget = arn;
            return (onCreateReport(null));
        } else if (!memberId.isEmpty()) {
            setSearchType("member");
            searchTarget = memberId;
            return (onCreateReport(null));
        }
        return "";
    }

    public String onTransEdit() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> map = ctx.getExternalContext().getRequestParameterMap();
        transId = map.get("transId");
        boolean found = false;
        for (FnboTrans trans : transList) {
            if (trans.getId().equals(transId)) {
                transItem = trans;
                found = true;
                break;
            }
        }
        if (!found) {
            lookupTransItem();
        }
        return "/fnboTransEdit.xhtml?faces-redirect=true";
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
        transItem = new FnboTrans();
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
                if (ld.isAfter(today)) {
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

    public void onSaveTransItem() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> map = ctx.getExternalContext().getRequestParameterMap();
        String ord1 = map.get("form:orderNum1");
        String ord2 = map.get("form:orderNum2");
        String srp1 = map.get("form:orderSrp1");
        String srp2 = map.get("form:orderSrp2");
        String amt1 = map.get("form:awardAmt1");
        String amt2 = map.get("form:awardAmt2");

        Award award = transItem.getAward1();
        award.setOrderNumber(ord1 == null || ord1.isEmpty() ? "" : ord1);
        award.setOrderSrp(srp1 == null || srp1.isEmpty() ? "" : srp1);
        award.setAwardAmt(amt1 == null || amt1.isEmpty() ? "" : amt1);
        if (!award.isEmpty()) {
            if (transItem.getAwardCount() == 0) {
                transItem.addAward(award);
            } else {
                transItem.setAward1(award);
            }
        }

        award = transItem.getAward2();
        award.setOrderNumber(ord2 == null ? "" : ord2);
        award.setOrderSrp(srp2 == null ? "" : srp2);
        award.setAwardAmt(amt2 == null ? "" : amt2);
        if (!award.isEmpty()) {
            if (transItem.getAwardCount() == 1) {
                transItem.addAward(award);
            } else {
                transItem.setAward2(award);
            }
        }

        RedObject rbo = new RedObject("WDE", "Bank:Fnbo");
        rbo.setProperty("fnboXrefId", transItem.getId());
        rbo.setProperty("transDate", transItem.getTransDateAsString());
        rbo.setProperty("arn", transItem.getArn());
        rbo.setProperty("memberId", transItem.getMemberId());
        rbo.setProperty("merchType", transItem.getMerchType());
        rbo.setProperty("transAmt", transItem.getTransAmtAsString());
        rbo.setProperty("cardType", transItem.getCardType());
        rbo.setProperty("merchDesc", transItem.getMerchDesc());
        rbo.setProperty("referenceNum", transItem.getTransRef());
        rbo.setProperty("transCode", transItem.getTransCode());
        UniDynArray awardArray = new UniDynArray();
        int a = 0;
        for (Award awd : transItem.getAwardList()) {
            a++;
            awardArray.insert(1, a, awd.getOrderNumber());
            awardArray.insert(2, a, awd.getOrderSrpAsString());
            awardArray.insert(3, a, awd.getAwardAmtAsString());
        }
        rbo.setProperty("orderId", awardArray.extract(1));
        rbo.setProperty("orderSrp", awardArray.extract(2));
        rbo.setProperty("awardAmt", awardArray.extract(3));
        rbo.setProperty("awardType", transItem.getAwardType());
        rbo.setProperty("fileDate", transItem.getFileDateAsString());
        rbo.setProperty("lineNum", transItem.getLineNum());
        rbo.setProperty("originalOrder", transItem.getOriginalOrder());
        rbo.setProperty("awardCount", Integer.toString(transItem.getAwardCount()));
        try {
            rbo.callMethod("postBankFnboTrans");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String svrCtrlCode = rbo.getProperty("svrCtrlCode");
            String svrMessage = rbo.getProperty("svrMessage");
            if (svrStatus == -1) {
                JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
            } else {
                JSFHelper.sendFacesMessage("Transaction updated");
            }
        } catch (RbException ex) {
            Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /**
     * @return the cardHolderList
     */
    public ArrayList<CardHolder> getCardHolderList() {
        return cardHolderList;
    }

    /**
     * @return the cardHolder
     */
    public CardHolder getCardHolder() {
        return cardHolder;
    }

    /**
     * @param cardHolder the cardHolder to set
     */
    public void setCardHolder(CardHolder cardHolder) {
        this.cardHolder = cardHolder;
    }

    public boolean getIsEmpty() {
        return hasItems;
    }

    /**
     * @return the selectedCardHolders
     */
    public ArrayList<CardHolder> getSelectedCardHolders() {
        return selectedCardHolders;
    }

    /**
     * @param clist
     */
    public void setSelectedCardHolders(ArrayList<CardHolder> clist) {
        if (clist != null) {
            this.cardHolderList.clear();
            this.cardHolderList.addAll(clist);
        }
    }

    /**
     * @return the referenceNum
     */
    public String getReferenceNum() {
        return referenceNum;
    }

    /**
     * @param referenceNum the referenceNum to set
     */
    public void setReferenceNum(String referenceNum) {
        this.referenceNum = referenceNum;
    }

    /**
     * @return the created
     */
    public boolean isCreated() {
        created = (!cardHolder.getArn().isEmpty() && !cardHolder.getMemberId().isEmpty() && !cardHolder.getCardType().isEmpty());
        clearable = (!cardHolder.getArn().isEmpty() || !cardHolder.getMemberId().isEmpty() || !cardHolder.getCardType().isEmpty());
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(boolean created) {
        this.created = created;
    }

    public void onTextEdit() {
        created = (!cardHolder.getArn().isEmpty() && !cardHolder.getMemberId().isEmpty() && !cardHolder.getCardType().isEmpty());
        clearable = (!cardHolder.getArn().isEmpty() || !cardHolder.getMemberId().isEmpty() || !cardHolder.getCardType().isEmpty());
    }

    /**
     * @return the clearable
     */
    public boolean isClearable() {

        return clearable;
    }

    /**
     * @param clearable the clearable to set
     */
    public void setClearable(boolean clearable) {
        this.clearable = clearable;
    }

    /**
     * @return the submitted
     */
    public boolean isSubmitted() {
        return submitted;
    }

    /**
     * @param submitted the submitted to set
     */
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

}
