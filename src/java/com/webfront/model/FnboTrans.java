/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private String transRef;
    private String uik;
    private String cardType;
    private Float transAmt;
    private String merchType;
    private String merchDesc;
    private String transCode;
    private String cardholderName;
    private Date transDate;
    private String orderId;       // MV
    private Float orderSrp;       // MV
    private Float orderCashback;  // MV
    private Float awardAmt;       // MV
    private String awardType;
    private Date fileDate;
    private String lineNum;
    private final ArrayList<Award> awardList;
    private String originalOrder;
    private Float orderSrpTotal;
    private Float awardTotal;
    private Float awardPctTotal;
    private Float transBalance;

    final DecimalFormat decFormat = new DecimalFormat("###0.00");
    final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    public FnboTrans() {
        id = "";
        transDate = null;
        arn = "";
        memberId = "";
        transRef = "";
        uik = "";
        cardType = "";
        transAmt = null;
        merchType = "";
        merchDesc = "";
        transCode = "";
        cardholderName = "";
        orderId = "";
        awardType = "";
        awardList = new ArrayList<>();
        orderSrpTotal = new Float(0.0);
        awardTotal = new Float(0.0);
        awardPctTotal = new Float(0.0);
    }

    /**
     * @return the id
     */
    public String getId() {
        if (id.isEmpty()) {
            return getUik();
        } else {
            return this.id;
        }
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
        if (transDate == null) {
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
        cal.set(Calendar.MONTH, mm - 1);
        cal.set(Calendar.DAY_OF_MONTH, dd);
        cal.set(Calendar.YEAR, yy + 2000);
        this.transDate = cal.getTime();
    }

    public void addAward(Award a) {
        awardList.add(a);
        orderSrpTotal += a.getOrderSrp();
        awardTotal += a.getAwardAmt();
        awardPctTotal += a.getAwardPct();
    }

    public Award getAward1() {
        if(awardList.isEmpty() || awardList.get(0)==null) {
            return new Award();
        }
        return awardList.get(0);
    }

    public Award getAward2() {
        if(awardList.isEmpty() || awardList.size()<2 || awardList.get(1)==null) {
            return new Award();
        }        
        return awardList.get(1);
    }

    public void setAward1(Award awd) {
        awardList.set(0, awd);
    }

    public void setAward2(Award awd) {
        awardList.set(1, awd);
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
        if (transAmt == null) {
            return "";
        }
        return decFormat.format(transAmt);
    }

    /**
     * @return the transAmt as a String value
     */
    public String getOrderSrpAsString() {
        if (orderSrp == null) {
            return "";
        }
        return decFormat.format(orderSrp);
    }

    /**
     * @return the transAmt as a String value
     */
    public String getOrderCashbackAsString() {
        if (orderCashback == null) {
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
        return merchType;
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
        return transCode;
    }
    
    /**
     * @return the transCode
     */
    public String getTransCodeDesc() {
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
        if(transCode.equalsIgnoreCase("Debit")) {
            transCode = "05";
        } else {
            if(transCode.equalsIgnoreCase("Credit")) {
                transCode = "06";
            }
        }
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

    /**
     * @return the transRef
     */
    public String getTransRef() {
        return transRef;
    }

    /**
     * @param transRef the transRef to set
     */
    public void setTransRef(String transRef) {
        this.transRef = transRef;
    }

    /**
     * @return the uik
     */
    public String getUik() {
        return uik;
    }

    /**
     * @param uik the uik to set
     */
    public void setUik(String uik) {
        this.uik = uik;
        setId(uik);
    }

    /**
     * @return the awardAmt
     */
    public Float getAwardAmt() {
        return awardAmt;
    }

    public String getAwardAmtAsString() {
        if (awardAmt == null) {
            return "";
        }
        return decFormat.format(awardAmt);
    }

    /**
     * @param amt the awardAmt to set
     */
    public void setAwardAmt(String amt) {
        this.awardAmt = Float.valueOf(amt);
    }

    /**
     * @return the awardType
     */
    public String getAwardType() {
        return awardType;
    }

    /**
     * @param awardType the awardType to set
     */
    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    /**
     * @return the fileDate
     */
    public Date getFileDate() {
        return fileDate;
    }

    public String getFileDateAsString() {
        if (fileDate == null) {
            return "";
        }
        return dateFormat.format(fileDate);
    }

    /**
     * @param fDate
     */
    public void setFileDate(String fDate) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        String[] dateSeg = fDate.split("/");
        int mm = Integer.parseInt(dateSeg[0]);
        int dd = Integer.parseInt(dateSeg[1]);
        int yy = Integer.parseInt(dateSeg[2]);
        cal.set(Calendar.MONTH, mm - 1);
        cal.set(Calendar.DAY_OF_MONTH, dd);
        cal.set(Calendar.YEAR, yy + 2000);
        this.fileDate = cal.getTime();
    }

    /**
     * @return the lineNum
     */
    public String getLineNum() {
        return lineNum;
    }

    /**
     * @param lineNum the lineNum to set
     */
    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * @return the awardCount
     */
    public int getAwardCount() {
        return awardList.size();
    }

    public ArrayList<Award> getAwardList() {
        return awardList;
    }

    public Float getOrderSrpTotal() {
        return orderSrpTotal;
    }

    public Float getAwardTotal() {
        return awardTotal;
    }

    public Float getAwardPctTotal() {
        return awardPctTotal;
    }

    /**
     * @return the originalOrder
     */
    public String getOriginalOrder() {
        return originalOrder;
    }

    /**
     * @param originalOrder the originalOrder to set
     */
    public void setOriginalOrder(String originalOrder) {
        this.originalOrder = originalOrder;
    }

    /**
     * @return the transBalance
     */
    public Float getTransBalance() {
        if(awardList.isEmpty() || awardList.size() ==0 ) {
            return transAmt;
        }
        if(awardList.size()==2) {
            Float srp1 = awardList.get(0).getOrderSrp();
            Float srp2 = awardList.get(1).getOrderSrp();
            transBalance = srp1 - srp2;
        } else {
            transBalance = transAmt - awardList.get(0).getOrderSrp();
        }
        return transBalance;
    }
}
