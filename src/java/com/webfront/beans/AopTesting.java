/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.AopSourceDesc;
import com.webfront.model.Customer;
import com.webfront.model.SelectItem;
import com.webfront.model.UnitTestLog;
import com.webfront.model.UnitTestLogData;
import com.webfront.model.UnitTestSuite;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

/**
 *
 * @author rlittle
 */
@SessionScoped
@Named("aopTestBean")
public class AopTesting implements Serializable {

    private final RedObject rbo;
    private AopSourceDesc sourceDesc;
    private ArrayList<Customer> selectedItems;
    private UnitTestSuite testSuite;
    private String userName;
    private String countryCode;
    private String commissionType;
    private String vendorId;
    private String vendorDiv;
    private String storeId;
    private String utSuiteId;
    private String utLogName;
    private String utLogId;
    private String utLogDate;
    private String utLogTime;
    private int utLogCount;

    @ManagedProperty(value = "#{affiliateMasterBean}")
    private AffiliateMasterBean masterBean;
    @ManagedProperty(value = "#{affiliateDetailBean}")
    private AffiliateDetailBean detailBean;
    private UISelectOne uiSelectVendor;
    private UISelectOne uiSelectStore;

    private ArrayList<SelectItem> storeList;
    private ArrayList<SelectItem> idList;
    private ArrayList<SelectItem> nameList;
    private ArrayList<SelectItem> dateList;
    private ArrayList<SelectItem> timeList;
    private ArrayList<SelectItem> suiteIdList;

    private UnitTestLog log;
    int errStatus;
    String errMessage;
    String errCode;
    private HorizontalBarChartModel chartModel;
    private ChartSeries passSeries;
    private ChartSeries failSeries;

    public AopTesting() {
        sourceDesc = new AopSourceDesc();
        selectedItems = new ArrayList<>();
        storeList = new ArrayList<>();
        idList = new ArrayList<>();
        nameList = new ArrayList<>();
        dateList = new ArrayList<>();
        timeList = new ArrayList<>();
        testSuite = new UnitTestSuite();
        suiteIdList = new ArrayList<>();
        log = new UnitTestLog();
        userName = "";
        countryCode = "";
        commissionType = "";
        rbo = new RedObject("WDE", "AOP:Testing");
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
    }

    public void doLookup(AjaxBehaviorEvent event) {
        sourceDesc.getCustList().clear();
        try {
            rbo.setProperty("userName", getUserName());
            rbo.setProperty("countryCode", getCountryCode());
            rbo.setProperty("commissionType", getCommissionType());
            rbo.callMethod("getAopSourceDesc");
            errStatus = Integer.parseInt(rbo.getProperty("errStat"));
            errCode = rbo.getProperty("errCode");
            errMessage = rbo.getProperty("errMsg");
            setVendorId(rbo.getPropertyToDynArray("vendorId").toString());
            setVendorDiv(rbo.getPropertyToDynArray("vendorDiv").toString());
            setStoreId(rbo.getPropertyToDynArray("storeId").toString());
            UniDynArray pcIds = rbo.getPropertyToDynArray("pcId");
            UniDynArray sponsorIds = rbo.getPropertyToDynArray("sponsor");
            UniDynArray pcHomes = rbo.getPropertyToDynArray("pcHome");
            UniDynArray pcTypes = rbo.getPropertyToDynArray("pcType");
            UniDynArray orderTotals = rbo.getPropertyToDynArray("orderTotal");
            UniDynArray orderDates = rbo.getPropertyToDynArray("orderDate");
            if (!"".equals(vendorId) && !"".equals(vendorDiv)) {
                changeVendor();
                String vendDiv = vendorId + "*" + vendorDiv;
                vendorDiv = vendDiv;
            }
            int size = Integer.parseInt(rbo.getProperty("itemCount"));
            for (int i = 1; i <= size; i++) {
                Customer cust = new Customer();
                cust.setPcId(pcIds.extract(1, i).toString());
                cust.setPcHome(pcHomes.extract(1, i).toString());
                cust.setPcType(pcTypes.extract(1, i).toString());
                cust.setSponsor(sponsorIds.extract(1, i).toString());
                String d = orderDates.extract(1, i).toString();
                String[] dateSeg = d.split("/");
                int mm = Integer.parseInt(dateSeg[0]);
                int dd = Integer.parseInt(dateSeg[1]);
                int yy = Integer.parseInt(dateSeg[2]);
                Calendar cal = Calendar.getInstance(Locale.getDefault());
                cal.set(Calendar.MONTH, mm);
                cal.set(Calendar.DAY_OF_MONTH, dd);
                cal.set(Calendar.YEAR, yy + 2000);
                cust.setOrderDate(cal.getTime());
                cust.setOrderTotal(orderTotals.extract(1, i).toString());
                sourceDesc.getCustList().add(cust);
            }
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
    }

    public void addItem() {
        rbo.setProperty("userName", getUserName());
        rbo.setProperty("countryCode", getCountryCode());
        rbo.setProperty("commissionType", getCommissionType());
        rbo.setProperty("vendorId", getVendorId());
        rbo.setProperty("vendorDiv", getVendorDiv());
        rbo.setProperty("pcId", sourceDesc.getPcId());
        rbo.setProperty("orderTotal", sourceDesc.getOrderTotal());
        rbo.setProperty("orderDate", sourceDesc.dateAsString());
        rbo.setProperty("vendorId", getVendorId());
        rbo.setProperty("vendorDiv", getVendorDiv());
        rbo.setProperty("storeId", getStoreId());
        try {
            rbo.callMethod("setAopSourceDesc");
            errStatus = Integer.parseInt(rbo.getProperty("errStat"));
            errCode = rbo.getProperty("errCode");
            errMessage = rbo.getProperty("errMsg");
            if (errStatus != -1) {
                Customer cust = new Customer();
                cust.setPcId(sourceDesc.getPcId());
                sourceDesc.getCustList().add(cust);
            }
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
    }

    public void deleteItem() {
        rbo.setProperty("userName", getUserName());
        rbo.setProperty("countryCode", getCountryCode());
        rbo.setProperty("commissionType", getCommissionType());
        UniDynArray pcArray = new UniDynArray();
        for (Customer cust : sourceDesc.getCustList()) {
            pcArray.insert(1, -1, cust.getPcId());
        }
        rbo.setProperty("pcId", pcArray);
        rbo.setProperty("isRemoving", "1");
        try {
            rbo.callMethod("setAopSourceDesc");
            errStatus = Integer.parseInt(rbo.getProperty("errStat"));
            errCode = rbo.getProperty("errCode");
            errMessage = rbo.getProperty("errMsg");
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
    }

    public void changeVendor() {
        RedObject vendorRbo = new RedObject("WDE", "Affiliates:Detail");
        vendorRbo.setProperty("masterId", vendorId);
        storeList.clear();
        try {
            vendorRbo.callMethod("getAffiliateDetailList");
            errStatus = Integer.parseInt(rbo.getProperty("errStat"));
            errCode = vendorRbo.getProperty("errCode");
            errMessage = vendorRbo.getProperty("errMsg");
            UniDynArray keys = vendorRbo.getPropertyToDynArray("keyList");
            UniDynArray values = vendorRbo.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            for (int val = 1; val < vals; val++) {
                String k = keys.extract(1, val).toString();
                String v = values.extract(1, val).toString();
                storeList.add(new SelectItem(k, v));
            }
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
    }

    public void updateItem(Customer c) {
        rbo.setProperty("userName", getUserName());
        rbo.setProperty("countryCode", getCountryCode());
        rbo.setProperty("commissionType", getCommissionType());
        rbo.setProperty("pcId", c.getPcId());
        rbo.setProperty("orderTotal", c.getOrderTotal());
        rbo.setProperty("orderDate", c.dateAsString());
        try {
            rbo.callMethod("setAopSourceDesc");
            errStatus = Integer.parseInt(rbo.getProperty("errStat"));
            errCode = rbo.getProperty("errCode");
            errMessage = rbo.getProperty("errMsg");
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != oldValue) {
            int idx = event.getRowIndex();
            Customer cust = sourceDesc.getCustList().get(idx);
            cust.setAmount((String) newValue);
            updateItem(cust);
        }
    }

    /**
     * @return the sourceDesc
     */
    public AopSourceDesc getSourceDesc() {
        return sourceDesc;
    }

    /**
     * @param sourceDesc the sourceDesc to set
     */
    public void setSourceDesc(AopSourceDesc sourceDesc) {
        this.sourceDesc = sourceDesc;
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
     * @return the country
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param country the country to set
     */
    public void setCountryCode(String country) {
        this.countryCode = country;
    }

    /**
     * @return the commType
     */
    public String getCommissionType() {
        return commissionType;
    }

    /**
     * @param commType the commType to set
     */
    public void setCommissionType(String commType) {
        this.commissionType = commType;
    }

    /**
     * @return the vendorId
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return the vendorDiv
     */
    public String getVendorDiv() {
        return vendorDiv;
    }

    /**
     * @param vendorDiv the vendorDiv to set
     */
    public void setVendorDiv(String vendorDiv) {
        this.vendorDiv = vendorDiv;
    }

    /**
     * @return the storeId
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    /**
     * @return the uiSelectOne
     */
    public UISelectOne getUiSelectVendor() {
        System.out.println("aopTestBean.getUISelectVendor()");
        return uiSelectVendor;
    }

    /**
     * @param uiSelectOne the uiSelectOne to set
     */
    public void setUiSelectVendor(UISelectOne uiSelectOne) {
        this.uiSelectVendor = uiSelectOne;
    }

    /**
     * @return the uiSelectStore
     */
    public UISelectOne getUiSelectStore() {
        return uiSelectStore;
    }

    /**
     * @param uiSelectStore the uiSelectStore to set
     */
    public void setUiSelectStore(UISelectOne uiSelectStore) {
        this.uiSelectStore = uiSelectStore;
    }

    /**
     * @return the masterBean
     */
    public AffiliateMasterBean getMasterBean() {
        return masterBean;
    }

    /**
     * @param masterBean the masterBean to set
     */
    public void setMasterBean(AffiliateMasterBean masterBean) {
        this.masterBean = masterBean;
    }

    /**
     * @return the detailBean
     */
    public AffiliateDetailBean getDetailBean() {
        return detailBean;
    }

    /**
     * @param detailBean the detailBean to set
     */
    public void setDetailBean(AffiliateDetailBean detailBean) {
        this.detailBean = detailBean;
    }

    /**
     * @return the storeList
     */
    public ArrayList<SelectItem> getStoreList() {
        return storeList;
    }

    /**
     * @param storeList the storeList to set
     */
    public void setStoreList(ArrayList<SelectItem> storeList) {
        this.storeList = storeList;
    }

    /**
     * @return the selectedItems
     */
    public ArrayList<Customer> getSelectedItems() {
        return selectedItems;
    }

    /**
     * @param selectedItems the selectedItems to set
     */
    public void setSelectedItems(ArrayList<Customer> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public void onRowSelect(SelectEvent event) {
    }

    public void onRowUnselect(UnselectEvent event) {
    }

    /**
     * @return the idList
     */
    public ArrayList<SelectItem> getIdList() {
        return idList;
    }

    /**
     * @param idList the idList to set
     */
    public void setIdList(ArrayList<SelectItem> idList) {

        this.idList = idList;
    }

    public void onChangeName() {
        try {
            idList.clear();
            nameList.clear();
            dateList.clear();
            timeList.clear();
            RedObject rb = new RedObject("WDE", "Test:Unit");
            rb.setProperty("utName", utLogName);
            rb.callMethod("getUnitTest");
            errStatus = Integer.parseInt(rb.getProperty("errStat"));
            errCode = rb.getProperty("errCode");
            errMessage = rb.getProperty("errMsg");
            passSeries.set("%", 0);
            failSeries.set("%", 0);
            if (errStatus == -1) {
                String msg = "Error: [" + errCode + "] " + errMessage;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
            } else {
                UniDynArray ids = rb.getPropertyToDynArray("utLogId");
                UniDynArray names = rb.getPropertyToDynArray("utName");
                UniDynArray dates = rb.getPropertyToDynArray("utDate");
                UniDynArray times = rb.getPropertyToDynArray("utTime");
                int logCount = Integer.parseInt(rb.getProperty("utLogCount"));
                for (int i = 1; i <= logCount; i++) {
                    String id = ids.extract(1, i).toString();
                    String name = names.extract(1, i).toString();
                    String date = dates.extract(1, i).toString();
                    String time = times.extract(1, i).toString();
                    idList.add(new SelectItem(id, id));
                    nameList.add(new SelectItem(id, name));
                    dateList.add(new SelectItem(id, date));
                    timeList.add(new SelectItem(id, time));
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTesting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onChangeTime() {
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
        try {
            RedObject rb = new RedObject("WDE", "Test:Unit");
            rb.setProperty("utLogId", utLogTime);
            rb.setProperty("utLogName", utLogName);
            rb.setProperty("utLogDate", utLogDate);
            rb.setProperty("utLogTime", utLogTime);
            rb.callMethod("getUnitTestLog");
            errStatus = Integer.parseInt(rb.getProperty("errStat"));
            errCode = rb.getProperty("errCode");
            errMessage = rb.getProperty("errMsg");
            if (errStatus == -1) {
                String msg = "Error: [" + errCode + "] " + errMessage;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
            } else {
                log = new UnitTestLog();
                log.setLogId(rb.getProperty("utLogId"));
                log.setLogName(rb.getProperty("utName"));
                log.setLogDate(rb.getProperty("utDate"));
                log.setLogTime(rb.getProperty("utTime"));
                log.setTotal(rb.getProperty("utLogCount"));
                log.setPassCount(rb.getProperty("utPass"));
                log.setFailCount(rb.getProperty("utFail"));
                log.setPassRate(rb.getProperty("utPassRate"));
                log.setFailRate(rb.getProperty("utFailRate"));
                UniDynArray passList = rb.getPropertyToDynArray("utPassNum");
                UniDynArray passDesc = rb.getPropertyToDynArray("utPassDesc");
                UniDynArray passExpect = rb.getPropertyToDynArray("utPassExpect");
                UniDynArray passResult = rb.getPropertyToDynArray("utPassResult");
                UniDynArray failList = rb.getPropertyToDynArray("utFailNum");
                UniDynArray failDesc = rb.getPropertyToDynArray("utFailDesc");
                UniDynArray failExpect = rb.getPropertyToDynArray("utFailExpect");
                UniDynArray failResult = rb.getPropertyToDynArray("utFailResult");
                int passCount = Integer.parseInt(log.getPassCount());
                int failCount = Integer.parseInt(log.getFailCount());
                if (passCount > 0) {
                    for (int p = 1; p <= passCount; p++) {
                        String num = passList.extract(1, p).toString();
                        String desc = passDesc.extract(1, p).toString();
                        String expect = passExpect.extract(1, p).toString();
                        String result = passResult.extract(1, p).toString();
                        UnitTestLogData logData = new UnitTestLogData();
                        logData.setNum(num);
                        logData.setDesc(desc);
                        logData.setExpect(expect.equals("0") ? "fail" : "pass");
                        logData.setResult(result.equals("0") ? "fail" : "pass");
                        log.getPassList().add(logData);
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
                        logData.setExpect(expect.equals("0") ? "fail" : "pass");
                        logData.setResult(result.equals("0") ? "fail" : "pass");
                        log.getFailList().add(logData);
                    }
                }
                passSeries.set("%", Integer.parseInt(log.getPassRate()));
                failSeries.set("%", Integer.parseInt(log.getFailRate()));
            }

        } catch (RbException ex) {
            Logger.getLogger(AopTesting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setLogIds() {

    }

    /**
     * @return the nameList
     */
    public ArrayList<SelectItem> getNameList() {
        setNameList(null);
        return nameList;
    }

    /**
     * @param list the nameList to set
     */
    public void setNameList(ArrayList<SelectItem> list) {
        this.nameList.clear();
        try {
            RedObject rb = new RedObject("WDE", "Test:Unit");
            rb.setProperty("utName", "");
            rb.callMethod("getUnitTest");
            errStatus = Integer.parseInt(rb.getProperty("errStat"));
            errCode = rb.getProperty("errCode");
            errMessage = rb.getProperty("errMsg");
            if (errStatus == -1) {
                String msg = "Error: [" + errCode + "] " + errMessage;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
            } else {
                UniDynArray ids = rb.getPropertyToDynArray("utLogId");
                UniDynArray names = rb.getPropertyToDynArray("utName");
                int logCount = Integer.parseInt(rb.getProperty("utLogCount"));
                for (int i = 1; i <= logCount; i++) {
                    SelectItem se = new SelectItem(ids.extract(1, i).toString(), names.extract(1, i).toString());
                    this.nameList.add(se);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTesting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the dateList
     */
    public ArrayList<SelectItem> getDateList() {
        return dateList;
    }

    /**
     * @param dateList the dateList to set
     */
    public void setDateList(ArrayList<SelectItem> dateList) {
        this.dateList = dateList;
    }

    /**
     * @return the timeList
     */
    public ArrayList<SelectItem> getTimeList() {
        return timeList;
    }

    /**
     * @param timeList the timeList to set
     */
    public void setTimeList(ArrayList<SelectItem> timeList) {
        this.timeList = timeList;
    }

    /**
     * @return the utSuiteId
     */
    public String getUtSuiteId() {
        return utSuiteId;
    }

    /**
     * @param utSuiteId the utSuiteId to set
     */
    public void setUtSuiteId(String utSuiteId) {
        this.utSuiteId = utSuiteId;
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
        return utLogDate;
    }

    /**
     * @param utLogDate the utLogDate to set
     */
    public void setUtLogDate(String utLogDate) {
        this.utLogDate = utLogDate;
    }

    /**
     * @return the utLogTime
     */
    public String getUtLogTime() {
        return utLogTime;
    }

    /**
     * @param utLogTime the utLogTime to set
     */
    public void setUtLogTime(String utLogTime) {
        this.utLogTime = utLogTime;
    }

    /**
     * @return the utLogCount
     */
    public int getUtLogCount() {
        return utLogCount;
    }

    /**
     * @param utLogCount the utLogCount to set
     */
    public void setUtLogCount(int utLogCount) {
        this.utLogCount = utLogCount;
    }

    /**
     * @return the logList
     */
    public UnitTestLog getLog() {
        return log;
    }

    /**
     * @param log the UnitTestLog to set
     */
    public void setLog(UnitTestLog log) {
        this.log = log;
    }

    /**
     * @return the chartModel
     */
    public HorizontalBarChartModel getChartModel() {
        return chartModel;
    }

    /**
     * @param chartModel the chartModel to set
     */
    public void setChartModel(HorizontalBarChartModel chartModel) {
        this.chartModel = chartModel;
    }

    /**
     * @return the passSeries
     */
    public ChartSeries getPassSeries() {
        return passSeries;
    }

    /**
     * @param passSeries the passSeries to set
     */
    public void setPassSeries(ChartSeries passSeries) {
        this.passSeries = passSeries;
    }

    /**
     * @return the failSeries
     */
    public ChartSeries getFailSeries() {
        return failSeries;
    }

    /**
     * @param failSeries the failSeries to set
     */
    public void setFailSeries(ChartSeries failSeries) {
        this.failSeries = failSeries;
    }

    /**
     * @return the suiteList
     */
    public ArrayList<SelectItem> getSuiteIdList() {
        setSuiteIdList(null);
        return suiteIdList;
    }

    /**
     * @param sl - ignored
     */
    public void setSuiteIdList(ArrayList<SelectItem> sl) {
        try {
            suiteIdList.clear();
            RedObject rb = new RedObject("WDE", "Test:Unit");
            rb.setProperty("utSuiteId", "");
            rb.callMethod("getTestSuite");
            errStatus = Integer.parseInt(rb.getProperty("errStat"));
            errCode = rb.getProperty("errCode");
            errMessage = rb.getProperty("errMsg");
            if (errStatus == -1) {
                String msg = "Error: [" + errCode + "] " + errMessage;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
            } else {
                UniDynArray utSuiteIdList = rb.getPropertyToDynArray("suiteId");
                UniDynArray utDescList = rb.getPropertyToDynArray("utDate");
                int utCount = Integer.parseInt(rb.getProperty("utCount"));
                for(int ut=1; ut<=utCount; ut++) {
                    String id = utSuiteIdList.extract(1, ut).toString();
                    String desc = utDescList.extract(1,ut).toString();
                    SelectItem se = new SelectItem(id,desc);
                    suiteIdList.add(se);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTesting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the testSuite
     */
    public UnitTestSuite getTestSuite() {
        return testSuite;
    }

    /**
     * @param testSuite the testSuite to set
     */
    public void setTestSuite(UnitTestSuite testSuite) {
        this.testSuite = testSuite;
    }
}
