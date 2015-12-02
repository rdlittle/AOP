/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 *
 * @author rlittle
 */
public class Campaign {
    private String id;
    private String status;
    private String cbBase;
    private String cbIncrease;
    private String cbTotal;
    private String increasedCommisson;
    private String user;
    private String editDate;
    private String editTime;
    private HashMap<String,String> statusLabels;
    private boolean canStop;
    private boolean canDelete;
    private String seqNum;
    private String storeId;
    private String storeName;
    private Date startDate;
    private Date endDate;
    final SimpleDateFormat dFmt = new SimpleDateFormat("MM/dd/yyyy");
    
    public Campaign() {
        statusLabels=new HashMap<>();
        statusLabels.put("0","Not Started");
        statusLabels.put("1","Staging");
        statusLabels.put("2","Active");
        statusLabels.put("3","Ended");
        statusLabels.put("4","Cancelled");
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
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param date the startDate to set
     */
    public void setStartDate(String date) {
        startDate = toDate(date);
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param date the endDate to set
     */
    public void setEndDate(String date) {
        endDate = toDate(date);
    }

    /**
     * @return the status
     */
    public String getStatus() {
        if(this.status != null) {
            return statusLabels.get(status);
        }
        return "";
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the increasedCb
     */
    public String getCbIncrease() {
        return cbIncrease;
    }

    /**
     * @param increasedCb the increasedCb to set
     */
    public void setCbIncrease(String increasedCb) {
        this.cbIncrease = increasedCb;
    }

    /**
     * @return the totalCb
     */
    public String getCbTotal() {
        return cbTotal;
    }

    /**
     * @param totalCb the totalCb to set
     */
    public void setCbTotal(String totalCb) {
        this.cbTotal = totalCb;
    }

    /**
     * @return the increasedCommisson
     */
    public String getIncreasedCommisson() {
        return increasedCommisson;
    }

    /**
     * @param increasedCommisson the increasedCommisson to set
     */
    public void setIncreasedCommisson(String increasedCommisson) {
        this.increasedCommisson = increasedCommisson;
    }

    /**
     * @return the cbBase
     */
    public String getCbBase() {
        return cbBase;
    }

    /**
     * @param cbBase the cbBase to set
     */
    public void setCbBase(String cbBase) {
        this.cbBase = cbBase;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the editDate
     */
    public String getEditDate() {
        return editDate;
    }

    /**
     * @param editDate the editDate to set
     */
    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    /**
     * @return the editTime
     */
    public String getEditTime() {
        return editTime;
    }

    /**
     * @param editTime the editTime to set
     */
    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    /**
     * @return the statusLabels
     */
    public HashMap<String,String> getStatusLabels() {
        return statusLabels;
    }

    /**
     * @param statusLabels the statusLabels to set
     */
    public void setStatusLabels(HashMap<String,String> statusLabels) {
        this.statusLabels = statusLabels;
    }

    /**
     * @return the canStop
     */
    public boolean isCanStop() {
        int nbr=Integer.parseInt(this.status);
        return (nbr>0 && nbr<3);
    }

    /**
     * @param canStop the canStop to set
     */
    public void setCanStop(boolean canStop) {
        this.canStop = canStop;
    }
    
    public boolean isCanDelete() {
        return (this.status.equals("0"));
    }
    
    public void setCanDelete(Boolean b) {
        
    }

    /**
     * @return the seqNum
     */
    public String getSeqNum() {
        return seqNum;
    }

    /**
     * @param seqNum the seqNum to set
     */
    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    /**
     * @return the storeId
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
   
    public Date toDate(String strDate) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        String sd[] = strDate.split("\\/");
        int mm = Integer.parseInt(sd[0]);
        int dd = Integer.parseInt(sd[1]);
        int yy = Integer.parseInt(sd[2]);
        if(yy<100) {
            yy += 2000;
        }
        cal.set(yy, mm, dd);
        return cal.getTime();
    }

}
