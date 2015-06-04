/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import java.util.HashMap;

/**
 *
 * @author rlittle
 */
public class Campaign {
    private String id;
    private String startDate;
    private String endDate;
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
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

}
