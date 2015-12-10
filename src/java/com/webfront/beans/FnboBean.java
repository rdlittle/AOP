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
import com.webfront.util.JSFHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author rlittle
 */
@SessionScoped
@Named(value = "fnboBean")
public class FnboBean implements Serializable {

    private FnboTrans transItem = null;
    private String transId = "";
    private String memberId = "";
    private String arn = "";
    private String searchType;
    private ArrayList<FnboTrans> transList;
    private String transTotal;

    /**
     * Creates a new instance of FnboBean
     */
    public FnboBean() {
        transId = "";
        transItem = new FnboTrans();
        transList = new ArrayList<>();
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
        RedObject rbo;
        FacesContext ctx = FacesContext.getCurrentInstance();
        String src = (String) ctx.getExternalContext().getRequestParameterMap().get("javax.faces.source");
        if ("form:byArn".equals(src)) {
            setSearchType("ARN");
        } else {
            setSearchType("Member Id");
        }
        rbo = new RedObject("WDE", "Bank:Fnbo");
        if (searchType.equals("ARN")) {
            rbo.setProperty("arn", getArn());
        } else {
            rbo.setProperty("memberId", getMemberId());
        }
        try {
            rbo.callMethod("getBankFnboTransReport");
            int svrStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            String svrCtrlCode = rbo.getProperty("svrCtrlCode");
            String svrMessage = rbo.getProperty("svrMessage");
            if (svrStatus == -1) {
                JSFHelper.sendFacesMessage(svrCtrlCode + ": " + svrMessage, "Error");
            } else {
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
                    if(fTrans.getTransAmt().isEmpty()) {
                        fTrans.setTransAmt("0.00");
                    }
                    Float amt = Float.parseFloat(fTrans.getTransAmt());
                    totalAmt += amt;
                    setTransTotal(totalAmt.toString());
                }
                return "/fnboTransReport.xhtml?faces-redirect=true";
            }
        } catch (RbException ex) {
            Logger.getLogger(FnboBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * @return the transItem
     */
    public FnboTrans getTransItem() {
        return transItem;
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

}
