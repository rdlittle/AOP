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
public class AopQueue extends Queue {

    private String aggregatorId;
    private String aggregatorName;
    private String lineCount;    
    private String orderCount;
    private String errorCount;
    private String checkId;
    private String checkAmount;
    private String checkDate;
    private boolean disabled;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String completionStatus;
    private String runLevel;
    private String reportType;
    private String reportTitle;
    private String successReport;
    private String errorReport;

    public AopQueue() {
        this.aggregatorId="";
        this.aggregatorName="";
        this.lineCount="";
        this.orderCount="";
        this.errorCount="";
        this.checkId="";
        this.checkAmount="";
        this.disabled=false;
        this.startDate="";
        this.endDate="";
        this.startTime="";
        this.endTime="";
        this.completionStatus="";
        this.runLevel="";
        this.reportType="";
        this.reportTitle="";
        this.successReport="";
        this.errorReport="";
    }

    /**
     * @return the aggregatorId
     */
    public String getAggregatorId() {
        return aggregatorId;
    }

    /**
     * @param aId the AFFILIATE.AGGREGATOR id to set
     */
    public void setAggregatorId(String aId) {
        this.aggregatorId = aId;
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
     * @return the aggregatorName
     */
    public String getAggregatorName() {
        return aggregatorName;
    }

    /**
     * @param aggregatorName the aggregatorName to set
     */
    public void setAggregatorName(String aggregatorName) {
        this.aggregatorName = aggregatorName;
    }

    /**
     * @return the disable
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * @param disable the disable to set
     */
    public void setDisabled(boolean disable) {
        this.disabled = disable;
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
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the completionStatus
     */
    public String getCompletionStatus() {
        return completionStatus;
    }

    /**
     * @param completionStatus the completionStatus to set
     */
    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
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
     * @return the reportType
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * @param reportType the reportType to set
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * @return the reportTitle
     */
    public String getReportTitle() {
        return reportTitle;
    }

    /**
     * @param reportTitle the reportTitle to set
     */
    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
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

}
