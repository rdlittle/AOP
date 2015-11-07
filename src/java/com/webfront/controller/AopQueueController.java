/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.controller;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.beans.AopQueueBean;
import com.webfront.model.AopQueue;
import com.webfront.model.ErrorObject;
import com.webfront.model.UVException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author rlittle
 */
public class AopQueueController {

    private final RedObject rb = new RedObject("WDE", "AOP:Queue");
    private AopQueue queueItem;
    private final ArrayList<AopQueue> queueList;
    public ErrorObject errObj;

    public AopQueueController() {
        errObj = new ErrorObject();
        this.queueList = new ArrayList<>();
    }

    /**
     * @return the queueItem
     */
    public AopQueue getQueueItem() {
        return this.queueItem;
    }

    /**
     * @param queueItem the queueItem to set
     */
    public void setQueueItem(AopQueue queueItem) {
        this.queueItem = queueItem;
    }

    private void setQueueList(String queueType) {
        rb.setProperty("queueType", queueType);
        rb.setProperty("queueId", "");
        rb.setProperty("queueStatus", "");
        
        try {
            rb.callMethod("getQueue");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            errObj = new ErrorObject(errStat,errCode,errMsg);
            if (-1 == errObj.getSvrStatus()) {
                FacesMessage fmsg = new FacesMessage(errObj.toString());
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                int vals = Integer.parseInt(rb.getProperty("queueCount"));
                if (vals > 0) {
                    this.queueList.clear();
                    UniDynArray affiliateMasterList = rb.getPropertyToDynArray("affiliateMasterId");
                    UniDynArray fileNameList = rb.getPropertyToDynArray("fileName");
                    UniDynArray pathList = rb.getPropertyToDynArray("path");
                    UniDynArray errorCountList = rb.getPropertyToDynArray("errorCount");
                    UniDynArray processStatusList = rb.getPropertyToDynArray("processStatus");
                    UniDynArray excludeFlagList = rb.getPropertyToDynArray("excludeFlag");
                    UniDynArray uploadDateList = rb.getPropertyToDynArray("uploadDate");
                    UniDynArray userNameList = rb.getPropertyToDynArray("userName");
                    UniDynArray lineCountList = rb.getPropertyToDynArray("lineCount");
                    UniDynArray orderCountList = rb.getPropertyToDynArray("orderCount");
                    UniDynArray uploadTimeList = rb.getPropertyToDynArray("uploadTime");
                    UniDynArray queueStatus = rb.getPropertyToDynArray("queueStatus");
                    UniDynArray checkAmountList = rb.getPropertyToDynArray("checkAmount");
                    UniDynArray networkIdList = rb.getPropertyToDynArray("networkId");
                    UniDynArray networkNameList = rb.getPropertyToDynArray("networkName");
                    UniDynArray networkCountryList = rb.getPropertyToDynArray("networkCountry");
                    UniDynArray checkIdList = rb.getPropertyToDynArray("checkId");
                    UniDynArray queueIdList = rb.getPropertyToDynArray("queueId");
                    UniDynArray queueTypeList = rb.getPropertyToDynArray("queueType");
                    UniDynArray errorReportList = rb.getPropertyToDynArray("errorReport");
                    UniDynArray successReportList = rb.getPropertyToDynArray("successReport");

                    for (int val = 1; val <= vals; val++) {
                        AopQueue aopQueue = new AopQueue();
                        aopQueue.setAffiliateMasterId(affiliateMasterList.extract(1, val).toString());
                        aopQueue.setFileName(fileNameList.extract(1, val).toString());
                        aopQueue.setPath(pathList.extract(1, val).toString());
                        aopQueue.setErrors(errorCountList.extract(1, val).toString());
                        aopQueue.setStatus(processStatusList.extract(1, val).toString());
                        aopQueue.setExclude("1".equals(excludeFlagList.extract(1, val).toString()));
                        aopQueue.setUploadDate(uploadDateList.extract(1, val).toString());
                        aopQueue.setUserName(userNameList.extract(1, val).toString());
                        aopQueue.setLineCount(lineCountList.extract(1, val).toString());
                        aopQueue.setOrderCount(orderCountList.extract(1, val).toString());
                        aopQueue.setUploadTime(uploadTimeList.extract(1, val).toString());
                        aopQueue.setQueueStatus(queueStatus.extract(1, val).toString());
                        aopQueue.setCheckAmount(checkAmountList.extract(1, val).toString());
                        aopQueue.setNetworkdId(networkIdList.extract(1, val).toString());
                        aopQueue.setNetworkName(networkNameList.extract(1, val).toString());
                        aopQueue.setNetworkCountry(networkCountryList.extract(1, val).toString());
                        aopQueue.setCheckId(checkIdList.extract(1, val).toString());
                        aopQueue.setQueueId(queueIdList.extract(1, val).toString());
                        aopQueue.setQueueType(queueTypeList.extract(1, val).toString());
                        aopQueue.setErrorReport(errorReportList.extract(1, val).toString());
                        aopQueue.setSuccessReport(successReportList.extract(1, val).toString());
                        this.queueList.add(aopQueue);
                    }
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("AopQueueController.setQueueList(" + queueType + "): Exception " + ex.toString());
        }
    }

    public ArrayList<AopQueue> getQueueList(String queueType) {
        setQueueList(queueType);
        return this.queueList;
    }

    public void createQueue() throws UVException {
        rb.setProperty("queueId", "");
        rb.setProperty("queueStatus", "");
        
        rb.setProperty("affiliateMasterId", queueItem.getAffiliateMasterId());
        rb.setProperty("fileName",queueItem.getFileName());
        rb.setProperty("userName", queueItem.getUserName());
        
        rb.setProperty("networkId", queueItem.getNetworkdId());
        rb.setProperty("checkAmount", queueItem.getCheckAmount());
        rb.setProperty("queueType", queueItem.getQueueType());
        rb.setProperty("checkId", queueItem.getCheckId());
        
        rb.setProperty("lineCount", queueItem.getLineCount());
        rb.setProperty("orderCount", queueItem.getOrderCount());
        rb.setProperty("errorCount", queueItem.getErrors());
        
        rb.setProperty("errorReport", queueItem.getErrorReport());
        rb.setProperty("successReport", queueItem.getSuccessReport());
        try {
            rb.callMethod("postAopQueue");
            errObj = new ErrorObject(rb.getProperty("svrStatus"),rb.getProperty("svrCtrlCode"),rb.getProperty("svrMessage"));
            if (-1 == errObj.getSvrStatus()) {
                throw new UVException(errObj);
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public void readQueue() {

    }

    public void updateQueue() throws UVException {
        
        rb.setProperty("queueId", queueItem.getQueueId());
        rb.setProperty("queueStatus", queueItem.getQueueStatus());
        
        rb.setProperty("affiliateMasterId", queueItem.getAffiliateMasterId());
        rb.setProperty("fileName",queueItem.getFileName());
        rb.setProperty("userName", queueItem.getUserName());
        
        rb.setProperty("networkId", queueItem.getNetworkdId());
        rb.setProperty("checkAmount", queueItem.getCheckAmount());
        rb.setProperty("queueType", queueItem.getQueueType());
        rb.setProperty("checkId", queueItem.getCheckId());
        
        rb.setProperty("lineCount", queueItem.getLineCount());
        rb.setProperty("orderCount", queueItem.getOrderCount());
        rb.setProperty("errorCount", queueItem.getErrors());
        
        rb.setProperty("errorReport", queueItem.getErrorReport());
        rb.setProperty("successReport", queueItem.getSuccessReport());
        try {
            rb.callMethod("postAopQueue");
            errObj = new ErrorObject(rb.getProperty("svrStatus"),rb.getProperty("svrCtrlCode"),rb.getProperty("svrMessage"));
            if (-1 == errObj.getSvrStatus()) {
                throw new UVException(errObj);
            }            
        } catch (RbException ex) {
            Logger.getLogger(AopQueueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteQueue() {

    }
}
