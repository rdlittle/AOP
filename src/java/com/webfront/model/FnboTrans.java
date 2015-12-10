/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

/**
 *
 * @author rlittle
 */
public class FnboTrans {
    private String id;
    private String transDate;
    private String arn;
    private String memberId;
    private String cardType;
    private String transAmt;
    private String merchType;
    private String merchDesc;
    private String transCode;
    private String cardholderName;

    public FnboTrans() {
        id="";
        transDate = "";
        arn = "";
        memberId = "";
        cardType = "";
        transAmt = "";
        merchType = "";
        merchDesc = "";
        transCode = "";
        cardholderName = "";
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
     * @return the transDate
     */
    public String getTransDate() {
        return transDate;
    }

    /**
     * @param transDate the transDate to set
     */
    public void setTransDate(String transDate) {
        this.transDate = transDate;
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
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * @return the transAmt
     */
    public String getTransAmt() {
        return transAmt;
    }

    /**
     * @param transAmt the transAmt to set
     */
    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    /**
     * @return the merchType
     */
    public String getMerchType() {
        if (merchType.isEmpty()) {
            return "";
        }
        if(merchType.equals("M")) {
            return "MA/Shop";
        } 
        return "Regular";
    }

    /**
     * @param merchType the merchType to set
     */
    public void setMerchType(String merchType) {
        this.merchType = merchType;
    }

    /**
     * @return the merchDesc
     */
    public String getMerchDesc() {
        return merchDesc;
    }

    /**
     * @param merchDesc the merchDesc to set
     */
    public void setMerchDesc(String merchDesc) {
        this.merchDesc = merchDesc;
    }

    /**
     * @return the transCode
     */
    public String getTransCode() {
        if(transCode.isEmpty()) {
            return "";
        }
        if(transCode.equals("06")) {
            return "Credit";
        }
        return "Debit";
    }

    /**
     * @param transCode the transCode to set
     */
    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    /**
     * @return the cardholderName
     */
    public String getCardholderName() {
        return cardholderName;
    }

    /**
     * @param cardholderName the cardholderName to set
     */
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
    
}
