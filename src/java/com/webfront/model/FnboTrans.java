/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author rlittle
 */
public class FnboTrans {

    private String id;
    private String arn;
    private String memberId;
    private String cardType;
    private Float transAmt;
    private String merchType;
    private String merchDesc;
    private String transCode;
    private String cardholderName;
    private Date transDate;
    private String orderId;
    private Float orderSrp;
    private Float orderCashback;

    final DecimalFormat decFormat = new DecimalFormat("###0.00");
    final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    public FnboTrans() {
        id = "";
        transDate = null;
        arn = "";
        memberId = "";
        cardType = "";
        transAmt = null;
        merchType = "";
        merchDesc = "";
        transCode = "";
        cardholderName = "";
        orderId = "";
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
    public String getTransDateAsString() {
        if(transDate==null) {
            return "";
        }
        return dateFormat.format(transDate);
    }
    
    public Date getTransDate() {
        return transDate;
    }

    /**
     * @param td
     */
    public void setTransDate(String td) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        String[] dateSeg = td.split("/");
        int mm = Integer.parseInt(dateSeg[0]);
        int dd = Integer.parseInt(dateSeg[1]);
        int yy = Integer.parseInt(dateSeg[2]);
        cal.set(Calendar.MONTH, mm-1);
        cal.set(Calendar.DAY_OF_MONTH, dd);
        cal.set(Calendar.YEAR, yy + 2000);
        this.transDate = cal.getTime();
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
     * @return the transAmt as a String value
     */
    public String getTransAmtAsString() {
        if (transAmt==null) {
            return "";
        }
        return decFormat.format(transAmt);
    }
    
    /**
     * @return the transAmt as a String value
     */
    public String getOrderSrpAsString() {
        if (orderSrp==null) {
            return "";
        }
        return decFormat.format(orderSrp);
    }    
    
    /**
     * @return the transAmt as a String value
     */
    public String getOrderCashbackAsString() {
        if (orderCashback==null) {
            return "";
        }
        return decFormat.format(orderCashback);
    }     
    
    public Float getTransAmt() {
        return transAmt;
    }

    /**
     * @param transAmt the transAmt to set
     */
    public void setTransAmt(String transAmt) {
        this.transAmt = Float.valueOf(transAmt);
    }

    /**
     * @return the merchType
     */
    public String getMerchType() {
        if (merchType.isEmpty()) {
            return "";
        }
        if (merchType.equals("M")) {
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
        if (transCode.isEmpty()) {
            return "";
        }
        if (transCode.equals("06")) {
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

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the orderSrp
     */
    public Float getOrderSrp() {
        return orderSrp;
    }

    /**
     * @param orderSrp the orderSrp to set
     */
    public void setOrderSrp(String orderSrp) {
        this.orderSrp = Float.valueOf(orderSrp);
    }

    /**
     * @return the orderCashback
     */
    public Float getOrderCashback() {
        return orderCashback;
    }

    /**
     * @param orderCashback the orderCashback to set
     */
    public void setOrderCashback(String orderCashback) {
        this.orderCashback = Float.valueOf(orderCashback);
    }

}
