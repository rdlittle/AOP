/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans.testing;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.SelectItem;
import com.webfront.model.UnitTestLog;
import com.webfront.model.UnitTestLogData;
import com.webfront.util.JSFHelper;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

/**
 *
 * @author rlittle
 */
@ManagedBean(name = "testLogBean")
@SessionScoped
public class TestLogBean {

    private String utLogName;
    private String utLogId;
    private String utLogDate;
    private String utLogTime;
    private int utLogCount;
    private UnitTestLog utLog;

    private final HorizontalBarChartModel chartModel;
    private final ChartSeries passSeries;
    private final ChartSeries failSeries;

    private final ArrayList<String> nameList;
    private final ArrayList<String> dateList;
    private final ArrayList<String> timeList;

    int errStatus;
    String errMessage;
    String errCode;

    public TestLogBean() {
        utLogId = "";
        utLogName = "";
        utLogDate = "";
        utLogTime = "";
        utLogCount = 0;
        utLog = new UnitTestLog();

        chartModel = new HorizontalBarChartModel();
        passSeries = new ChartSeries();
        passSeries.setLabel("Pass");
        passSeries.getData().put("%", 0);
        failSeries = new ChartSeries();
        failSeries.setLabel("Fail");
        failSeries.getData().put("%", 0);
        chartModel.addSeries(passSeries);
        chartModel.addSeries(failSeries);
        chartModel.setStacked(true);
        Axis axisx = chartModel.getAxis(AxisType.X);
        axisx.setMax(100);
        axisx.setLabel("Pass/Fail");

        nameList = new ArrayList<>();
        dateList = new ArrayList<>();
        timeList = new ArrayList<>();
    }

    public void onChangeUtLogDate(AjaxBehaviorEvent vce) {
        RedObject rb = new RedObject("WDE", "Test:Unit");
        rb.setProperty("utName", getUtLogName());
        rb.setProperty("utDate", getUtLogDate());
        try {
            rb.callMethod("getUnitTest");
            if (errStatus == -1) {
                errCode = rb.getProperty("svrCtrlCode");
                errMessage = rb.getProperty("svrmessage");
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                UniDynArray uda = rb.getPropertyToDynArray("utTime");
                this.getTimeList().clear();
                int timeCount = Integer.parseInt(rb.getProperty("utTimeCount"));
                for (int t = 1; t <= timeCount; t++) {
                    String time = uda.extract(1, t).toString();
                    if (!this.timeList.contains(time)) {
                        this.timeList.add(time);
                    }
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(TestLogBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onChangeUtLogName() {
        try {
            getDateList().clear();
            getTimeList().clear();
            getUtLog().getFailList().clear();
            getUtLog().getPassList().clear();
            setUtLogDate(null);
            setUtLogTime(null);
            if (getUtLogName().isEmpty()) {
                return;
            }
            RedObject rb = new RedObject("WDE", "Test:Unit");
            rb.setProperty("utName", getUtLogName());
            rb.callMethod("getUnitTest");
            errStatus = Integer.parseInt(rb.getProperty("errStat"));
            errCode = rb.getProperty("errCode");
            errMessage = rb.getProperty("errMsg");
            passSeries.set("%", 0);
            failSeries.set("%", 0);
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                UniDynArray ids = rb.getPropertyToDynArray("utLogId");
                UniDynArray names = rb.getPropertyToDynArray("utName");

                UniDynArray uda = rb.getPropertyToDynArray("utLogDateInt");
                uda.insert(2, rb.getPropertyToDynArray("utDate"));
                uda.insert(3, rb.getPropertyToDynArray("utLogTimeInt"));
                uda.insert(4, rb.getPropertyToDynArray("utTime"));
                int dateCount = Integer.parseInt(rb.getProperty("utDateCount"));

                int logCount = Integer.parseInt(rb.getProperty("utLogCount"));
                for (int i = 1; i <= logCount; i++) {
                    String name = names.extract(1, i).toString();
                    getNameList().add(name);
                }

                for (int d = 1; d <= dateCount; d++) {
                    String date = uda.extract(2, d).toString();
                    this.dateList.add(date);
                }

            }
        } catch (RbException ex) {
            Logger.getLogger(TestLogBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onChangeUtLogTime() {
        if (utLogName == null || utLogName.isEmpty()) {
            return;
        }
        if (utLogDate == null || utLogDate.isEmpty()) {
            return;
        }
        if (utLogTime == null || utLogTime.isEmpty()) {
            return;
        }
        passSeries.set("%", 0);
        failSeries.set("%", 0);
        getChartModel().clear();
        getUtLog().getFailList().clear();
        getUtLog().getPassList().clear();
        try {
            RedObject rb = new RedObject("WDE", "Test:Unit");
            rb.setProperty("utLogId", utLogId == null ? "" : utLogId);
            rb.setProperty("utName", utLogName);
            rb.setProperty("utDate", utLogDate);
            rb.setProperty("utTime", utLogTime);
            rb.callMethod("getUnitTestLog");
            errStatus = Integer.parseInt(rb.getProperty("svrStatus"));
            errCode = rb.getProperty("svrCtrlCode");
            errMessage = rb.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("messages", "Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                setUtLog(new UnitTestLog());
                getUtLog().setLogId(rb.getProperty("utLogId"));
                getUtLog().setLogName(rb.getProperty("utName"));
                getUtLog().setLogDate(rb.getProperty("utDate"));
                getUtLog().setLogTime(rb.getProperty("utTime"));
                getUtLog().setTotal(rb.getProperty("utLogCount"));
                getUtLog().setPassCount(rb.getProperty("utPass"));
                getUtLog().setFailCount(rb.getProperty("utFail"));
                getUtLog().setPassRate(rb.getProperty("utPassRate"));
                getUtLog().setFailRate(rb.getProperty("utFailRate"));
                UniDynArray passList = rb.getPropertyToDynArray("utPassNum");
                UniDynArray passDesc = rb.getPropertyToDynArray("utPassDesc");
                UniDynArray passExpect = rb.getPropertyToDynArray("utPassExpect");
                UniDynArray passResult = rb.getPropertyToDynArray("utPassResult");
                UniDynArray failList = rb.getPropertyToDynArray("utFailNum");
                UniDynArray failDesc = rb.getPropertyToDynArray("utFailDesc");
                UniDynArray failExpect = rb.getPropertyToDynArray("utFailExpect");
                UniDynArray failResult = rb.getPropertyToDynArray("utFailResult");
                int passCount = Integer.parseInt(getUtLog().getPassCount());
                int failCount = Integer.parseInt(getUtLog().getFailCount());
                if (passCount > 0) {
                    for (int p = 1; p <= passCount; p++) {
                        String num = passList.extract(1, p).toString();
                        String desc = passDesc.extract(1, p).toString();
                        String expect = passExpect.extract(1, p).toString();
                        String result = passResult.extract(1, p).toString();
                        UnitTestLogData logData = new UnitTestLogData();
                        logData.setNum(num);
                        logData.setDesc(desc);
                        logData.setExpect(expect.equals("utPass") ? "pass" : "fail");
                        logData.setResult(result.equals("utPass") ? "pass" : "fail");
                        getUtLog().getPassList().add(logData);
                    }
                }
                if (failCount > 0) {
                    for (int p = 1; p <= failCount; p++) {
                        String num = failList.extract(1, p).toString();
                        String desc = failDesc.extract(1, p).toString();
                        String expect = failExpect.extract(1, p).toString();
                        String result = failResult.extract(1, p).toString();
                        UnitTestLogData logData = new UnitTestLogData();
                        logData.setNum(num);
                        logData.setDesc(desc);
                        logData.setExpect(expect.equals("utPass") ? "pass" : "fail");
                        logData.setResult(result.equals("utPass") ? "pass" : "fail");
                        getUtLog().getFailList().add(logData);
                    }
                }
                passSeries.set("%", Float.valueOf(getUtLog().getPassRate()));
                failSeries.set("%", Float.valueOf(getUtLog().getFailRate()));
                getChartModel().addSeries(passSeries);
                getChartModel().addSeries(failSeries);
            }

        } catch (RbException ex) {
            Logger.getLogger(TestLogBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String onDeleteLog() {
        if (!utLogName.isEmpty() && !utLogDate.isEmpty() && !utLogTime.isEmpty()) {
            RedObject rb = new RedObject("WDE", "Test:Log");
            rb.setProperty("utName", utLogName);
            rb.setProperty("utDate", utLogDate);
            rb.setProperty("utTime", utLogTime);
            try {
                rb.callMethod("deleteTestLog");
                errStatus = Integer.parseInt(rb.getProperty("svrStatus"));
                errCode = rb.getProperty("svrCtrlCode");
                errMessage = rb.getProperty("svrMessage");
                if (errStatus == -1) {
                    JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
                } else {
                    if (timeList.contains(utLogTime)) {
                        timeList.remove(utLogTime);
                        utLogTime = "";
                    }
                    utLogCount = Integer.parseInt(rb.getProperty("utLogCount"));
                    if (utLogCount == 0) {
                        if (this.nameList.contains(utLogName)) {
                            this.nameList.remove(utLogName);
                        }
                        if (this.dateList.contains(utLogDate)) {
                            this.dateList.remove(utLogDate);
                        }
                        utLogDate = "";
                    }
//                    chartModel.clear();
                    for(ChartSeries cs : chartModel.getSeries()) {
                        cs.getData().put("%", 0);
                    }
                    getUtLog().getFailList().clear();
                    getUtLog().getPassList().clear();
                    JSFHelper.sendFacesMessage("Test log successfully deleted");
                }
            } catch (RbException ex) {
                Logger.getLogger(TestLogBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "/unitTestLog.xhtml?faces-redirect=true";
    }

    /**
     */
    public void setDateList() {
        if (this.utLogName.isEmpty()) {
            return;
        }
        RedObject rb = new RedObject("WDE", "Test:Unit");
        rb.setProperty("utName", utLogName);
        try {
            rb.callMethod("getUnitTest");
            errStatus = Integer.parseInt(rb.getProperty("svrStatus"));
            if (errStatus == -1) {
                errCode = rb.getProperty("svrCtrlCode");
                errMessage = rb.getProperty("svrmessage");
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                UniDynArray uda = rb.getPropertyToDynArray("utLogDateInt");
                uda.insert(2, rb.getPropertyToDynArray("utDate"));
                uda.insert(3, rb.getPropertyToDynArray("utLogTimeInt"));
                uda.insert(4, rb.getPropertyToDynArray("utTime"));
                int dateCount = Integer.parseInt(rb.getProperty("utDateCount"));
                int timeCount = Integer.parseInt(rb.getProperty("utTimeCount"));
                for (int d = 1; d <= dateCount; d++) {
                    String date = uda.extract(2, d).toString();
                    if (!this.dateList.contains(date)) {
                        this.dateList.add(date);
                    }
                }
                for (int t = 1; t <= timeCount; t++) {
                    String time = uda.extract(4, t).toString();
                    if (!this.timeList.contains(time)) {
                        this.timeList.add(time);
                    }
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(TestLogBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     */
    public void setNameList() {
        try {
            RedObject rb = new RedObject("WDE", "Test:Unit");
            rb.setProperty("utName", "");
            rb.callMethod("getUnitTest");
            errStatus = Integer.parseInt(rb.getProperty("errStat"));
            errCode = rb.getProperty("errCode");
            errMessage = rb.getProperty("errMsg");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                UniDynArray ids = rb.getPropertyToDynArray("utLogId");
                UniDynArray names = rb.getPropertyToDynArray("utName");
                int logCount = Integer.parseInt(rb.getProperty("utLogCount"));
                for (int i = 1; i <= logCount; i++) {
                    SelectItem se = new SelectItem(ids.extract(1, i).toString(), names.extract(1, i).toString());
                    String name = names.extract(1, i).toString();
                    if (!this.nameList.contains(name)) {
                        this.nameList.add(name);
                    }
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(TestLogBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    /**
//     * @param timeList the timeList to set
//     */
//    public void setTimeList(ArrayList<String> timeList) {
//        if (timeList != null) {
//            this.timeList = timeList;
//        }
//    }

    public boolean isLogSelected() {
        boolean logSelected = (!utLogName.isEmpty() && !utLogDate.isEmpty() && !utLogTime.isEmpty());
        return logSelected;
    }

    /**
     * @return the utLogName
     */
    public String getUtLogName() {
        return utLogName;
    }

    /**
     * @param utLogName the utLogName to set
     */
    public void setUtLogName(String utLogName) {
        this.utLogName = utLogName;
    }

    /**
     * @return the utLogId
     */
    public String getUtLogId() {
        return utLogId;
    }

    /**
     * @param utLogId the utLogId to set
     */
    public void setUtLogId(String utLogId) {
        this.utLogId = utLogId;
    }

    /**
     * @return the utLogDate
     */
    public String getUtLogDate() {
        if(utLogDate == null) {
            return "";
        }
        return utLogDate;
    }

    /**
     * @param logDate the utLogDate to set
     */
    public void setUtLogDate(String logDate) {
        if (logDate != null) {
            this.utLogDate = logDate;
        }
    }

    /**
     * @return the utLogTime
     */
    public String getUtLogTime() {
        if(utLogTime == null) {
            return "";
        }
        return utLogTime;
    }

    /**
     * @param logTime the utLogTime to set
     */
    public void setUtLogTime(String logTime) {
        if (logTime != null) {
            this.utLogTime = logTime;
        }
    }

    /**
     * @return the nameList
     */
    public ArrayList<String> getNameList() {
        setNameList();
        return nameList;
    }

    /**
     * @return the dateList
     */
    public ArrayList<String> getDateList() {
        return this.dateList;
    }

    /**
     * @return the timeList
     */
    public ArrayList<String> getTimeList() {
        return this.timeList;
    }

    /**
     * @return the chartModel
     */
    public HorizontalBarChartModel getChartModel() {
        return chartModel;
    }

    /**
     * @return the utLog
     */
    public UnitTestLog getUtLog() {
        return utLog;
    }

    /**
     * @param utLog the utLog to set
     */
    public void setUtLog(UnitTestLog utLog) {
        this.utLog = utLog;
    }
}
