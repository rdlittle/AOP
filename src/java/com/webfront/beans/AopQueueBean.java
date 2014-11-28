/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.AopQueue;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AopQueueBean {

    private ArrayList<AopQueue> queueList;
    private ArrayList<AopQueue> selectedItems = new ArrayList<>();
    private String queueType;

    public ArrayList<AopQueue> getPaymentQueueList() {
        queueType = "payment";
        return getQueueList();
    }
    /**
     * @return the queueList
     */
    public ArrayList<AopQueue> getQueueList() {
        this.queueList = new ArrayList<>();
        RedObject rb = new RedObject("WDE", "AOP:Queue");
        rb.setProperty("queueType", queueType);
        //rb.setProperty("affiliateMasterId", "affiliateMasterId");
        //rb.setProperty("userName", "userName");
        //rb.setProperty("processStatus", "processStatus");
        try {
            rb.callMethod("getQueue");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            if (errStat.equals("-1")) {
                errMsg = "Error: " + errCode + " " + errMsg;
                FacesMessage fmsg = new FacesMessage(errMsg);
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                int vals = Integer.parseInt(rb.getProperty("itemCount"));
                if (vals > 0) {
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
                    UniDynArray runLevelList = rb.getPropertyToDynArray("runLevel");
                    UniDynArray checkAmountList = rb.getPropertyToDynArray("checkAmount");
                    UniDynArray networkIdList = rb.getPropertyToDynArray("networkId");
                    UniDynArray networkNameList = rb.getPropertyToDynArray("networkName");
                    UniDynArray networkCountryList = rb.getPropertyToDynArray("networkCountry");

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
                        aopQueue.setRunLevel(runLevelList.extract(1, val).toString());
                        aopQueue.setCheckAmount(checkAmountList.extract(1, val).toString());
                        aopQueue.setNetworkdId(networkIdList.extract(1, val).toString());
                        aopQueue.setNetworkName(networkNameList.extract(1, val).toString());
                        aopQueue.setNetworkCountry(networkCountryList.extract(1, val).toString());
                        this.queueList.add(aopQueue);
                    }
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("AopQueBean.getQueList() exception: " + ex.toString());
        }
        return this.queueList;
    }

    /**
     * @param queueList the queueList to set
     */
    public void setQueueList(ArrayList<AopQueue> queueList) {
        this.queueList = queueList;
    }

    public void processSelected(ActionEvent actionEvent) {
        if (selectedItems != null) {
            try {
                UniDynArray fileNames = new UniDynArray();
                UniDynArray affiliateMasterList = new UniDynArray();
                UniDynArray processLevels = new UniDynArray();
                int i = 0;
                for (AopQueue item : selectedItems) {
                    i++;
                    fileNames.insert(1, i, item.getFileName());
                    affiliateMasterList.insert(1, i, item.getAffiliateMasterId());
                    processLevels.insert(1, i, item.getStatus());
                }
                RedObject rb = new RedObject("WDE", "AOP:Queue");
                rb.setProperty("fileName", fileNames.toString());
                rb.setProperty("affiliateMasterId", affiliateMasterList.toString());
                rb.setProperty("runLevel", processLevels.toString());
                rb.callMethod("setQueue");
                UniDynArray errStat = rb.getPropertyToDynArray("errStat");
                UniDynArray errCode = rb.getPropertyToDynArray("errCode");
                UniDynArray errMsg = rb.getPropertyToDynArray("errMsg");
                int errCount = errStat.count(1);
                String errMsgs="";
                if (errCount > 0) {
                    for(int e=0; i<= errCount; e++) {
                        errMsgs+=fileNames.extract(1, e).toString()+": ";
                        errMsgs+=errMsg.extract(1, e).toString()+"\n";
                    }
                    FacesMessage fmsg = new FacesMessage(errMsgs);
                    fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage("msg", fmsg);
                }
            } catch (RbException ex) {
                Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void toggleSelect() {
        String summary = "Checkbox event";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
        //System.out.println(selectedItems.size());
    }

//    public void toggleSelect(ValueChangeEvent vce) {
//        String summary = "Checkbox event";
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
//        //System.out.println(selectedItems.size());
//    }
    /**
     * @return the selectedItems
     */
    public ArrayList<AopQueue> getSelectedItems() {
        return selectedItems;
    }

    /**
     * @param selectedItems the selectedItems to set
     */
    public void setSelectedItems(ArrayList<AopQueue> selectedItems) {
        this.selectedItems = selectedItems;
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
}
