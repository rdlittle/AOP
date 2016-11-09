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
public abstract class Queue {

    private String id;
    private String fileName;
    private String createDate;
    private String createTime;
    private String userName;
    private String lineCount;
    private String queueType;
    private String queueGroup;
    private String queueDesc;
    private String errorReport;
    private String successReport;
    private boolean hasErrorReport;
    private boolean hasSuccessReport;
    private String runLevel;

    public Queue() {
        id = "";
        fileName = "";
        runLevel = "";
        createDate = "";
        createTime = "";
        userName = "";
        lineCount = "";
        queueType = "";
        queueGroup = "";
        queueDesc = "";
        hasErrorReport = false;
        hasSuccessReport = false;
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
     * @param status the runLevel to set
     */
    public void setRunLevel(String rl) {
        runLevel = rl;
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
    public String getLineCount() {
        return lineCount;
    }

    /**
     * @param itemCount the itemCount to set
     */
    public void setLineCount(String itemCount) {
        this.lineCount = itemCount;
    }

    /**
     * @return the queueType
     */
    public String getQueueType() {
        return queueType;
    }
    
    public String getRunLevel() {
        return runLevel;
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

    public boolean getHasErrorReport() {
        hasErrorReport = !errorReport.isEmpty();
        return hasErrorReport;
    }
    
    public boolean getHasSuccessReport() {
        hasSuccessReport = !successReport.isEmpty();
        return hasSuccessReport;
    }

    /**
     * @return the queueGroup
     */
    public String getQueueGroup() {
        return queueGroup;
    }

    /**
     * @param queueGroup the queueGroup to set
     */
    public void setQueueGroup(String queueGroup) {
        this.queueGroup = queueGroup;
    }

    /**
     * @return the queueDesc
     */
    public String getQueueDesc() {
        return queueDesc;
    }

    /**
     * @param queueDesc the queueDesc to set
     */
    public void setQueueDesc(String queueDesc) {
        this.queueDesc = queueDesc;
    }
    
}
