/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import java.util.HashMap;
import javax.faces.event.ActionEvent;

/**
 *
 * @author rlittle
 */
public class AopQueue {
    private String vendorMasterId;
    private String path;
    private String errors;
    private String status;
    private String uploadDate;
    private String uploadTime;
    private String fileName;
    private boolean exclude;
    private String userName;
    private boolean runFlag;
    private boolean preRunFlag;
    private boolean deleteFlag;
    private String lineCount;
    private String orderCount;
    HashMap<String,String> statusNames;
    HashMap<String,String> runStage;
    private String runLevel;

    public AopQueue() {
        this.statusNames=new HashMap<>();
        this.statusNames.put("0","Pending");
        this.statusNames.put("1","Processing");
        this.statusNames.put("2","Completed");
        this.statusNames.put("4","Error");
        this.runStage=new HashMap<>();
        this.runStage.put("0", "Pre-Run");
        this.runStage.put("1", "Run");
        this.runStage.put("2", "Delete");
        this.runLevel="0";
    }
    /**
     * @return the vendorMasterId
     */
    public String getVendorMasterId() {
        return vendorMasterId;
    }

    /**
     * @param vendorMasterId the vendorMasterId to set
     */
    public void setVendorMasterId(String vendorMasterId) {
        this.vendorMasterId = vendorMasterId;
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
    
    public String getStatusName() {
        return this.statusNames.get(this.status);
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
    
}
