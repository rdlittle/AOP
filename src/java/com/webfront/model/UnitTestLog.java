/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author rlittle
 */
public class UnitTestLog implements Serializable {
    private String logId;
    private String logName;
    private String logDate;
    private String logTime;
    private String total;
    private String passCount;
    private String failCount;
    private String passRate;
    private String failRate;
    private ArrayList<UnitTestLogData> passList;
    private ArrayList<UnitTestLogData> failList;
    
    public UnitTestLog() {
        logId = "";
        logName = "";
        logDate = "";
        logTime = "";
        total = "";
        passCount = "";
        failCount = "";
        passRate = "";
        failRate = "";
        passList = new ArrayList<>();
        failList = new ArrayList<>();
    }

    /**
     * @return the logId
     */
    public String getLogId() {
        return logId;
    }

    /**
     * @param logId the logId to set
     */
    public void setLogId(String logId) {
        this.logId = logId;
    }

    /**
     * @return the logName
     */
    public String getLogName() {
        return logName;
    }

    /**
     * @param logName the logName to set
     */
    public void setLogName(String logName) {
        this.logName = logName;
    }

    /**
     * @return the logDate
     */
    public String getLogDate() {
        return logDate;
    }

    /**
     * @param logDate the logDate to set
     */
    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    /**
     * @return the logTime
     */
    public String getLogTime() {
        return logTime;
    }

    /**
     * @param logTime the logTime to set
     */
    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return the pass
     */
    public String getPassCount() {
        return passCount;
    }

    /**
     * @param pass the pass to set
     */
    public void setPassCount(String pass) {
        this.passCount = pass;
    }

    /**
     * @return the fail
     */
    public String getFailCount() {
        return failCount;
    }

    /**
     * @param fail the fail to set
     */
    public void setFailCount(String fail) {
        this.failCount = fail;
    }

    /**
     * @return the passRate
     */
    public String getPassRate() {
        return passRate;
    }

    /**
     * @param passRate the passRate to set
     */
    public void setPassRate(String passRate) {
        this.passRate = passRate;
    }

    /**
     * @return the failRate
     */
    public String getFailRate() {
        return failRate;
    }

    /**
     * @param failRate the failRate to set
     */
    public void setFailRate(String failRate) {
        this.failRate = failRate;
    }

    /**
     * @return the logData
     */
    public ArrayList<UnitTestLogData> getPassList() {
        return passList;
    }

    /**
     * @param logData the logData to set
     */
    public void setPassList(ArrayList<UnitTestLogData> logData) {
        this.passList = logData;
    }
    
    /**
     * @return the logData
     */
    public ArrayList<UnitTestLogData> getFailList() {
        return failList;
    }

    /**
     * @param logData the logData to set
     */
    public void setFailList(ArrayList<UnitTestLogData> logData) {
        this.failList = logData;
    }    

}
