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
public class AopQueue extends Queue {

    private String queueId;
    private String affiliateMasterId;
    private String path;
    private String errors;
    private String status;
    private String uploadDate;
    private String uploadTime;
    private boolean exclude;
    private boolean runFlag;
    private boolean preRunFlag;
    private boolean deleteFlag;
    private String lineCount;
    private String orderCount;
    HashMap<String, String> statusNames;
    HashMap<String, String> runStage;
    private String runLevel;
    private String checkId;
    private String checkAmount;
    private String checkDate;
    private String networkdId;
    private String networkName;
    private String networkCountry;
    private String networkId;

    public AopQueue() {
        this.runStage = new HashMap<>();
        this.runStage.put("0", "Pre-Run");
        this.runStage.put("1", "Run");
        this.runStage.put("2", "Delete");
        this.runLevel = "0";
        this.networkId = "";
        super.setQueueStatus("0");
    }

    /**
     * @return the affiliateMasterId
     */
    public String getAffiliateMasterId() {
        return affiliateMasterId;
    }

    /**
     * @param affiliateMasterId the affiliateMasterId to set
     */
    public void setAffiliateMasterId(String affiliateMasterId) {
        this.affiliateMasterId = affiliateMasterId;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the errors
     */
    public String getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(String errors) {
        this.errors = errors;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the uploadDate
     */
    public String getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * @return the exclude
     */
    public boolean isExclude() {
        return exclude;
    }

    /**
     * @param exclude the exclude to set
     */
    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    /**
     * @return the runFlag
     */
    public boolean isRunFlag() {
        return runFlag;
    }

    /**
     * @param runFlag the runFlag to set
     */
    public void setRunFlag(boolean runFlag) {
        this.runFlag = runFlag;
    }

    /**
     * @return the preRunFlag
     */
    public boolean isPreRunFlag() {
        return preRunFlag;
    }

    /**
     * @param preRunFlag the preRunFlag to set
     */
    public void setPreRunFlag(boolean preRunFlag) {
        this.preRunFlag = preRunFlag;
    }

    /**
     * @return the lineCount
     */
    public String getLineCount() {
        return lineCount;
    }

    /**
     * @param lineCount the lineCount to set
     */
    public void setLineCount(String lineCount) {
        this.lineCount = lineCount;
    }

    /**
     * @return the orderCount
     */
    public String getOrderCount() {
        return orderCount;
    }

    /**
     * @param orderCount the orderCount to set
     */
    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    /**
     * @return the uploadTime
     */
    public String getUploadTime() {
        return uploadTime;
    }

    /**
     * @param uploadTime the uploadTime to set
     */
    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * @return the deleteFlag
     */
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag the deleteFlag to set
     */
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRunStage() {
        return runStage.get(this.getRunLevel());
    }

    /**
     * @return the runLevel
     */
    public String getRunLevel() {
        return runLevel;
    }

    /**
     * @param runLevel the runLevel to set
     */
    public void setRunLevel(String runLevel) {
        this.runLevel = runLevel;
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

    /**
     * @return the networkdId
     */
    public String getNetworkdId() {
        return networkdId;
    }

    /**
     * @param networkdId the networkdId to set
     */
    public void setNetworkdId(String networkdId) {
        this.networkdId = networkdId;
    }

    /**
     * @return the networkName
     */
    public String getNetworkName() {
        return networkName;
    }

    /**
     * @param networkName the networkName to set
     */
    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    /**
     * @return the networkCountry
     */
    public String getNetworkCountry() {
        return networkCountry;
    }

    /**
     * @param networkCountry the networkCountry to set
     */
    public void setNetworkCountry(String networkCountry) {
        this.networkCountry = networkCountry;
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
     * @return the aoQueId
     */
    public String getQueueId() {
        return queueId;
    }

    /**
     * @param aoQueId the aoQueId to set
     */
    public void setQueueId(String aoQueId) {
        this.queueId = aoQueId;
    }

    /**
     * @return the checkDate
     */
    public String getCheckDate() {
        return checkDate;
    }

    /**
     * @param checkDate the checkDate to set
     */
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

}
