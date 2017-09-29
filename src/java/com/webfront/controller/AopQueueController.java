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
import com.webfront.model.Queue;
import com.webfront.model.QueueError;
import com.webfront.model.QueueItem;
import com.webfront.model.UVException;
import java.io.Serializable;
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

    private final RedObject rb = new RedObject("WDE", "AFFILIATE:Queue");
    private AopQueue queueItem;
    private final ArrayList<Queue> queueList;
    public ErrorObject errObj;
    private boolean monitorRunning;
    protected boolean showAll;
    protected String userName;
    private QueueItem queueType;
    private final ArrayList<ColumnModel> columnHeaders;
    private final ArrayList<QueueError> errorList;
    private String queueId;

    public AopQueueController() {
        errObj = new ErrorObject();
        this.queueList = new ArrayList<>();
        showAll = false;
        userName = "";
        columnHeaders = new ArrayList<>();
        errorList = new ArrayList<>();
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
        rb.setProperty("userID", userName);
        rb.setProperty("showAll", showAll ? "1" : "0");

        try {
            rb.callMethod("getAffiliateQueue");
            String errStat = rb.getProperty("svrStatus");
            String errCode = rb.getProperty("svrCtrlCode");
            String errMsg = rb.getProperty("svrMessage");
            errObj = new ErrorObject(errStat, errCode, errMsg);
            if (-1 == errObj.getSvrStatus()) {
                FacesMessage fmsg = new FacesMessage(errObj.toString());
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                int queueCount = Integer.parseInt(rb.getProperty("queueCount"));
                if (queueCount > 0) {
                    this.queueList.clear();
                    UniDynArray queueIdList = rb.getPropertyToDynArray("queueID");
                    UniDynArray affiliateMasterList = rb.getPropertyToDynArray("aggregatorID");
                    UniDynArray fileNameList = rb.getPropertyToDynArray("fileName");
                    UniDynArray uploadDateList = rb.getPropertyToDynArray("uploadDate");
                    UniDynArray uploadTimeList = rb.getPropertyToDynArray("uploadTime");
                    UniDynArray userNameList = rb.getPropertyToDynArray("userID");
                    UniDynArray runLevelList = rb.getPropertyToDynArray("runLevel");
                    UniDynArray lineCountList = rb.getPropertyToDynArray("lineCount");
                    UniDynArray orderCountList = rb.getPropertyToDynArray("orderCount");
                    UniDynArray errorCountList = rb.getPropertyToDynArray("errorCount");

                    UniDynArray startDateList = rb.getPropertyToDynArray("startDate");
                    UniDynArray startTimeList = rb.getPropertyToDynArray("startTime");
                    UniDynArray endDateList = rb.getPropertyToDynArray("endDate");
                    UniDynArray endTimeList = rb.getPropertyToDynArray("endTime");
                    UniDynArray completionStatusList = rb.getPropertyToDynArray("completionStatus");
                    boolean isAdmin = rb.getProperty("isAdmin").equals("1");

                    UniDynArray checkIdList = rb.getPropertyToDynArray("checkID");
                    UniDynArray checkAmountList = rb.getPropertyToDynArray("checkAmount");
                    UniDynArray queueTypeList = rb.getPropertyToDynArray("queueType");
                    UniDynArray errorReportList = rb.getPropertyToDynArray("errorReport");
                    UniDynArray successReportList = rb.getPropertyToDynArray("successReport");
                    UniDynArray aggregatorNameList = rb.getPropertyToDynArray("aggregatorName");
//                    UniDynArray disableList = rb.getPropertyToDynArray("isDisabled");

                    monitorRunning = rb.getProperty("monitorRunning").equals("1");

                    for (int val = 1; val <= queueCount; val++) {
                        AopQueue item = new AopQueue();
                        item.setId(queueIdList.extract(1, val).toString());
                        item.setAggregatorId(affiliateMasterList.extract(1, val).toString());
                        item.setFileName(fileNameList.extract(1, val).toString());
                        item.setErrorCount(errorCountList.extract(1, val).toString());
                        item.setRunLevel(runLevelList.extract(1, val).toString());
                        item.setEndDate(uploadDateList.extract(1, val).toString());
                        item.setEndTime(uploadTimeList.extract(1, val).toString());
                        item.setUserName(userNameList.extract(1, val).toString());
                        item.setLineCount(lineCountList.extract(1, val).toString());
                        item.setQueueType(queueTypeList.extract(1, val).toString());
                        item.setErrorReport(errorReportList.extract(1, val).toString());
                        item.setSuccessReport(successReportList.extract(1, val).toString());
                        item.setOrderCount(orderCountList.extract(1, val).toString());
                        item.setCheckAmount(checkAmountList.extract(1, val).toString());
                        item.setCheckId(checkIdList.extract(1, val).toString());
                        item.setAggregatorName(aggregatorNameList.extract(1, val).toString());
//                        item.setDisabled(disableList.extract(1, val).toString().equals("1"));
                        if (startDateList != null) {
                            String sDate = startDateList.extract(1, val).toString();
                            if (sDate != null && !sDate.isEmpty()) {
                                item.setStartDate(sDate);
                            }
                        }
                        if (startTimeList != null) {
                            String sTime = startTimeList.extract(1, val).toString();
                            if (sTime != null && !sTime.isEmpty()) {
                                item.setStartTime(sTime);
                            }
                        }
                        if (endDateList != null) {
                            String eDate = endDateList.extract(1, val).toString();
                            if (eDate != null && !eDate.isEmpty()) {
                                item.setEndDate(eDate);
                            }
                        }
                        if (endTimeList != null) {
                            String eTime = endTimeList.extract(1, val).toString();
                            if (eTime != null && !eTime.isEmpty()) {
                                item.setEndTime(eTime);
                            }
                        }
                        if (completionStatusList != null) {
                            item.setCompletionStatus(completionStatusList.extract(1, val).toString());
                        }
                        this.queueList.add(item);
                    }
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("AopQueueController.setQueueList(" + queueType + "): Exception " + ex.toString());
        }
    }

    public void toggleQueue(String queueType) {
        if (monitorRunning) {
            stopQueue("");
            return;
        }
        try {
            rb.setProperty("operation", "1");
            rb.callMethod("setAffiliateQueuePhantom");
            String errStat = rb.getProperty("svrStatus");
            String errCode = rb.getProperty("svrCtrlCode");
            String errMsg = rb.getProperty("svrMessage");
            errObj = new ErrorObject(errStat, errCode, errMsg);
            if (-1 == errObj.getSvrStatus()) {
                FacesMessage fmsg = new FacesMessage(errObj.toString());
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                setMonitorRunning(true);
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("AopQueueController.startQueue(" + queueType + "): Exception " + ex.toString());
        }
    }

    public void stopQueue(String queueType) {
        try {
            rb.setProperty("operation", "0");
            rb.callMethod("setAffiliateQueuePhantom");
            String errStat = rb.getProperty("svrStatus");
            String errCode = rb.getProperty("svrCtrlCode");
            String errMsg = rb.getProperty("svrMessage");
            errObj = new ErrorObject(errStat, errCode, errMsg);
            if (-1 == errObj.getSvrStatus()) {
                FacesMessage fmsg = new FacesMessage(errObj.toString());
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                setMonitorRunning(false);
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("AopQueueController.stopQueue(" + queueType + "): Exception " + ex.toString());
        }
    }

    public ArrayList<? extends Queue> getQueueList(String queueType, String user, boolean show) {
        this.userName = user;
        this.showAll = show;
        setQueueList(queueType);
        return this.queueList;
    }

    public void createQueue() throws UVException {
        rb.setProperty("queueId", "");
        rb.setProperty("queueStatus", "");

        rb.setProperty("aggregatorId", queueItem.getAggregatorId());
        rb.setProperty("fileName", queueItem.getFileName());
        rb.setProperty("userName", queueItem.getUserName());

        rb.setProperty("checkId", queueItem.getCheckId());
        rb.setProperty("checkAmount", queueItem.getCheckAmount());
        rb.setProperty("checkDate", queueItem.getCheckDate());

        rb.setProperty("queueType", queueItem.getQueueType());

        try {
            rb.callMethod("postAffiliateQueue");
            errObj = new ErrorObject(rb.getProperty("svrStatus"), rb.getProperty("svrCtrlCode"), rb.getProperty("svrMessage"));
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

        rb.setProperty("queueID", queueItem.getId());
        rb.setProperty("runLevel", queueItem.getRunLevel());

        try {
            rb.callMethod("putAffiliateQueue");
            errObj = new ErrorObject(rb.getProperty("svrStatus"), rb.getProperty("svrCtrlCode"), rb.getProperty("svrMessage"));
            if (-1 == errObj.getSvrStatus()) {
                throw new UVException(errObj);
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteQueue() {

    }

    /**
     * @return the monitorRunning
     */
    public boolean isMonitorRunning() {
        return this.monitorRunning;
    }

    /**
     * @param monitorRunning the monitorRunning to set
     */
    public void setMonitorRunning(boolean monitorRunning) {
        this.monitorRunning = monitorRunning;
    }

    /**
     * @return the queueType
     */
    public QueueItem getQueueType() {
        return queueType;
    }

    /**
     * @param queueType the queueType to set
     */
    public void setQueueType(QueueItem queueType) {
        this.queueType = queueType;
    }

    /**
     * @return the columnHeaders
     */
    public ArrayList getColumnHeaders() {
        return columnHeaders;
    }

    public void setErrorList(ArrayList<QueueError> l) {
        errorList.clear();
        errorList.addAll(l);
    }

    public void setColumnHeaders(String aggregatorId) {
        rb.setProperty("aggregatorId", aggregatorId);
        rb.setProperty("fileType", "CSV");
        try {
            rb.callMethod("getAffiliateMapping");
            String errStat = rb.getProperty("svrStatus");
            String errCode = rb.getProperty("svrCtrlCode");
            String errMsg = rb.getProperty("svrMessage");
            errObj = new ErrorObject(errStat, errCode, errMsg);
            if (-1 == errObj.getSvrStatus()) {
                FacesMessage fmsg = new FacesMessage(errObj.toString());
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                columnHeaders.clear();
                UniDynArray oList = new UniDynArray();
                oList.insert(1, rb.getPropertyToDynArray("fieldLabels"));
                oList.insert(2, rb.getPropertyToDynArray("fieldKeys"));
                int vals = oList.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    String label = oList.extract(1, val).toString();
                    String value = oList.extract(2, val).toString();
                    columnHeaders.add(new ColumnModel(label, value));
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param id
     * @return the errorList
     */
    public ArrayList<QueueError> getErrorList(String id) {
        RedObject ro = new RedObject("WDE", "AFFILIATE:Report");
        ro.setProperty("queueId", id);
        queueId = id;
        try {
            ro.callMethod("getAffiliateReportErrors");
            String errStat = rb.getProperty("svrStatus");
            String errCode = rb.getProperty("svrCtrlCode");
            String errMsg = rb.getProperty("svrMessage");
            errObj = new ErrorObject(errStat, errCode, errMsg);
            if (-1 == errObj.getSvrStatus()) {
                FacesMessage fmsg = new FacesMessage(errObj.toString());
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                UniDynArray oList = new UniDynArray();
                oList.insert(1, ro.getPropertyToDynArray("lineNumber"));
                oList.insert(2, ro.getPropertyToDynArray("commKey"));
                oList.insert(3, ro.getPropertyToDynArray("payingId"));
                oList.insert(4, ro.getPropertyToDynArray("commission"));
                oList.insert(5, ro.getPropertyToDynArray("storeId"));
                oList.insert(6, ro.getPropertyToDynArray("storeName"));
                oList.insert(7, ro.getPropertyToDynArray("srp"));
                oList.insert(8, ro.getPropertyToDynArray("orderDate"));
                oList.insert(9, ro.getPropertyToDynArray("orderId"));
                oList.insert(10, ro.getPropertyToDynArray("errorCode"));
                oList.insert(11, ro.getPropertyToDynArray("errorMessage"));
                oList.insert(12, ro.getPropertyToDynArray("tierKey"));
                oList.insert(13, ro.getPropertyToDynArray("sku"));
                oList.insert(14, ro.getPropertyToDynArray("isDisabled"));
                String lc = ro.getProperty("lineCount");
                int lineCount;
                if (lc == null) {
                    lineCount = oList.dcount(1);
                } else {
                    lineCount = Integer.parseInt(lc);
                }

                errorList.clear();
                for (int e = 1; e <= lineCount; e++) {
                    String lineNumber = oList.extract(1, e).toString();
                    String commKey = oList.extract(2, e).toString();
                    String payingId = oList.extract(3, e).toString();
                    String reportedComm = oList.extract(4, e).toString();
                    String storeId = oList.extract(5, e).toString();
                    String storeName = oList.extract(6, e).toString();
                    String srp = oList.extract(7, e).toString();
                    String orderDate = oList.extract(8, e).toString();
                    String orderId = oList.extract(9, e).toString();
                    String errorCode = oList.extract(10, e).toString();
                    String errorMessage = oList.extract(11, e).toString();
                    String tierKey = oList.extract(12, e).toString();
                    String sku = oList.extract(13, e).toString();
                    String isDisabled = oList.extract(14, e).toString();

                    QueueError qe = new QueueError();
                    qe.setLineNumber(lineNumber);
                    qe.setCommKey(commKey);
                    qe.setPayingId(payingId);
                    qe.setReportedComm(reportedComm);
                    qe.setStoreId(storeId);
                    qe.setStoreName(storeName);
                    qe.setSrp(srp);
                    qe.setSku(sku);
                    qe.setTierKey(tierKey);
                    qe.setOrderDate(orderDate);
                    qe.setOrderId(orderId);
                    qe.setDisabled(isDisabled.equals("1"));
                    qe.setErrorCode(errorCode);
                    qe.setErrorText(errorMessage);
                    errorList.add(qe);
                }

            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return errorList;
    }

    public void saveReportEdit() {
        RedObject ro = new RedObject("WDE", "AFFILIATE:Report");
        UniDynArray iList = new UniDynArray();
        int val = 1;
        for (QueueError item : errorList) {
            iList.insert(1, val, Integer.toString(item.getLineNumber()));
            iList.insert(2, val, item.getCommKey());
            iList.insert(3, val, item.getPayingId());
            iList.insert(4, val, item.getReportedComm());
            iList.insert(5, val, item.getStoreId());
            iList.insert(6, val, item.getStoreName());
            iList.insert(7, val, item.getSrp());
            iList.insert(8, val, item.getOrderDate());
            iList.insert(9, val, item.getOrderId());
            iList.insert(12, val, item.getTierKey());
            iList.insert(13, val, item.getSku());
            val++;
        }
        ro.setProperty("queueId", queueId);
        ro.setProperty("lineNumber", iList.extract(1));
        ro.setProperty("commKey", iList.extract(2));
        ro.setProperty("payingId", iList.extract(3));
        ro.setProperty("commission", iList.extract(4));
        ro.setProperty("storeId", iList.extract(5));
        ro.setProperty("storeName", iList.extract(6));
        ro.setProperty("srp", iList.extract(7));
        ro.setProperty("orderDate", iList.extract(8));
        ro.setProperty("orderId", iList.extract(9));
        ro.setProperty("tierKey", iList.extract(10));
        ro.setProperty("sku", iList.extract(11));
        try {
            ro.callMethod("putAffiliateReportEdit");
        } catch (RbException ex) {
            Logger.getLogger(AopQueueController.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage fmsg = new FacesMessage(ex.getMessage());
            fmsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("msg", fmsg);
        }
        FacesMessage fmsg = new FacesMessage("Report saved");
        fmsg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage("msg", fmsg);
    }

    static public class ColumnModel implements Serializable {

        private String header;
        private String property;

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

}
