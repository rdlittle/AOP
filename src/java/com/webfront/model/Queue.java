/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
         equ QUEUE.STATUS.INIT           to 0
         equ QUEUE.STATUS.VALIDATE       to 1
         equ QUEUE.STATUS.AO.CREATE      to 2
         equ QUEUE.STATUS.REFUND.PROCESS to 3
         equ QUEUE.STATUS.ORDER.CREATE   to 4
         equ QUEUE.STATUS.REPORT         to 5
         equ QUEUE.STATUS.DELETE         to 6
         equ QUEUE.STATUS.ERROR          to 7
         equ QUEUE.STATUS.HOLD           to 9
 */
package com.webfront.model;

import java.util.HashMap;

/**
 *
 * @author rlittle
 */
public abstract class Queue {

    private String id;
    private String vendorCode;
    private String fileName;
    private String queueStatus;
    private String createDate;
    private String createTime;
    private String userName;
    private String itemCount;
    private String errorCount;
    private String runDate;
    private String runTime;
    private String queueType;
    private final HashMap<String, String> statusNames;
    private String errorReport;
    private String successReport;
    private String checkId;
    private String checkAmount;

    public Queue() {
        id = "";
        vendorCode = "";
        fileName = "";
        queueStatus = "";
        createDate = "";
        createTime = "";
        userName = "";
        itemCount = "";
        errorCount = "";
        runDate = "";
        runTime = "";
        queueType = "";
        statusNames = new HashMap<>();
        statusNames.put("0", "Pending");
        statusNames.put("1", "Validating");
        statusNames.put("2", "Building");
        statusNames.put("3", "Processing Refunds");
        statusNames.put("4", "Creating Orders");
        statusNames.put("5", "Reports Ready");
        statusNames.put("6", "Deleting");
        statusNames.put("7", "Error");
        statusNames.put("9", "On Hold");
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
     * @return the vendorCode
     */
    public String getVendorCode() {
        return vendorCode;
    }

    /**
     * @param vendorCode the vendorCode to set
     */
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the queueStatus
     */
    public String getQueueStatus() {
        return queueStatus;
    }

    /**
     * @param status the queueStatus to set
     */
    public void setQueueStatus(String status) {
        this.queueStatus = status;
    }

    public String getQueueStatusName() {
        if (!statusNames.containsKey(this.queueStatus)) {
            this.queueStatus = "0";
        }
        return statusNames.get(this.queueStatus);
    }
    
    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the itemCount
     */
    public String getItemCount() {
        return itemCount;
    }

    /**
     * @param itemCount the itemCount to set
     */
    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    /**
     * @return the errorCount
     */
    public String getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount the errorCount to set
     */
    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * @return the runDate
     */
    public String getRunDate() {
        return runDate;
    }

    /**
     * @param runDate the runDate to set
     */
    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    /**
     * @return the runTime
     */
    public String getRunTime() {
        return runTime;
    }

    /**
     * @param runTime the runTime to set
     */
    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    /**
     * @return the queueType
     */
    public String getQueueType() {
        return queueType;
    }

    /**
     * @param queueType the queueType to set
     */
    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Queue)) {
            return false;
        }
        Queue other = (Queue) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     * @return the errorReport
     */
    public String getErrorReport() {
        return errorReport;
    }

    /**
     * @param errorReport the errorReport to set
     */
    public void setErrorReport(String errorReport) {
        this.errorReport = errorReport;
    }

    /**
     * @return the successReport
     */
    public String getSuccessReport() {
        return successReport;
    }

    /**
     * @param successReport the successReport to set
     */
    public void setSuccessReport(String successReport) {
        this.successReport = successReport;
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
    
}
