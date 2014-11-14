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
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AopQueueBean {

    private ArrayList<AopQueue> queueList;
    private ArrayList<AopQueue> selectedItems = new ArrayList<>();

    /**
     * @return the queueList
     */
    public ArrayList<AopQueue> getQueueList() {
        this.queueList = new ArrayList<>();
        RedObject rb = new RedObject("WDE", "AOP:Queue");
        //rb.setProperty("vendorMasterId", "vendorMasterId");
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
                int vals = Integer.parseInt(rb.getProperty("itemCount").toString());
                if (vals > 0) {
                    UniDynArray vendorList = rb.getPropertyToDynArray("vendorMasterId");
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

                    for (int val = 1; val <= vals; val++) {
                        AopQueue aopQueue = new AopQueue();
                        aopQueue.setVendorMasterId(vendorList.extract(1, val).toString());
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
                        aopQueue.setRunLevel(runLevelList.extract(1,val).toString());
                        this.queueList.add(aopQueue);
                    }
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("AopQueBean.getQueList() exception: "+ex.toString());
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
            for (AopQueue item : selectedItems) {
                String fn = item.getFileName();
                if (item.isDeleteFlag()) {
                    System.out.println("Deleting " + fn);
                }
                if (item.isPreRunFlag()) {
                    System.out.println("Pre-run " + fn);
                }
                if (item.isRunFlag()) {
                    System.out.println("Run " + fn);
                }
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
}
