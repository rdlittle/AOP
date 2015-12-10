/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans.testing;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.beans.AffiliateDetailBean;
import com.webfront.beans.AffiliateMasterBean;
import com.webfront.model.AopSourceDesc;
import com.webfront.model.Customer;
import com.webfront.model.SelectItem;
import com.webfront.model.UnitTestLog;
import com.webfront.model.UnitTestLogData;
import com.webfront.model.UnitTestSuite;
import com.webfront.util.JSFHelper;
import com.webfront.util.MVUtils;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

/**
 *
 * @author rlittle
 */
@Named("aopTestBean")
@SessionScoped
public class AopTestingBean implements Serializable {

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
    private String groupId;

//    @ManagedProperty(value = "#{affiliateMasterBean}")
    private AffiliateMasterBean masterBean;

    @ManagedProperty(value = "#{affiliateDetailBean}")
    private AffiliateDetailBean detailBean;

    private ArrayList<SelectItem> storeList;
    private ArrayList<SelectItem> idList;
    private final ArrayList<SelectItem> nameList;
    private ArrayList<SelectItem> dateList;
    private ArrayList<SelectItem> timeList;
    private final ArrayList<SelectItem> suiteIdList;
    private ArrayList<SelectItem> unitList;
    private final ArrayList<SelectItem> errorList;
    private ArrayList<AoTestDataUnit> dataUnitList;
    private ArrayList<AoTestDataUnit> selectedUnitList;
    private HashMap<String, String> groupList;
    private HashMap<String, String> commTypeMap;
    private ArrayList<String> countryList;
    private ArrayList<String> commTypeList;
    private DualListModel<String> countryPickList;
    private ArrayList<String> allCountries;

    private UnitTestLog log;
    int errStatus;
    String errMessage;
    String errCode;
    private HorizontalBarChartModel chartModel;
    private ChartSeries passSeries;
    private ChartSeries failSeries;
    private ArrayList<String> batchList;
    private boolean edit;
    private boolean rowSelected;
    private AoTestDataUnit selectedDataUnit;
    private AoTestDataGroup selectedTestDataGroup;

    public AopTestingBean() {
        groupId = "";
        sourceDesc = new AopSourceDesc();
        batchList = new ArrayList<>();
        groupList = new HashMap<>();
        selectedItems = new ArrayList<>();
        selectedUnitList = new ArrayList<>();
        storeList = new ArrayList<>();
        idList = new ArrayList<>();
        nameList = new ArrayList<>();
        dateList = new ArrayList<>();
        timeList = new ArrayList<>();
        testSuite = new UnitTestSuite();
        suiteIdList = new ArrayList<>();
        dataUnitList = new ArrayList<>();
        unitList = new ArrayList<>();
        errorList = new ArrayList<>();
        countryList = new ArrayList<>();
        commTypeList = new ArrayList<>();
        log = new UnitTestLog();
        userName = "";
        countryCode = "";
        commissionType = "";
        commTypeMap = new HashMap<>();
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
        edit = false;
        selectedDataUnit = new AoTestDataUnit();
        selectedTestDataGroup = new AoTestDataGroup();
        errorList.add(new SelectItem("1", "Invalid order date"));
        errorList.add(new SelectItem("6", "Store not found"));
        errorList.add(new SelectItem("9", "Invalid paying id"));
        errorList.add(new SelectItem("19", "No vendor order id"));
        errorList.add(new SelectItem("31", "Non-numeric SRP"));
        errorList.add(new SelectItem("102", "Order outside current year"));
        errorList.add(new SelectItem("103", "Duplicate order"));
        countryPickList = new DualListModel<>();
        allCountries = new ArrayList<>();
    }

    public void addUnit() {
        if (selectedDataUnit.getDescription() == null || "".equals(selectedDataUnit.getDescription())) {
            return;
        }
        if (selectedDataUnit.getTransType() == null) {
            return;
        }
        if (selectedDataUnit.getRequires() == null) {
            selectedDataUnit.setRequires("");
        }
        if (selectedDataUnit.getCreditType() == null) {
            selectedDataUnit.setCreditType("");
        }
        if (selectedDataUnit.getErrorCodes() == null) {
            selectedDataUnit.setErrorCodes("");
        }
        if (selectedDataUnit.getErrorLines() == null) {
            selectedDataUnit.setErrorLines("");
        }
        rbo.setProperty("unitId", "");
        rbo.setProperty("unitDesc", selectedDataUnit.getDescription());
        rbo.setProperty("unitTransType", selectedDataUnit.getTransType());
        rbo.setProperty("unitLinesPerOrder", selectedDataUnit.getLines());
        rbo.setProperty("unitOrdersPerCust", selectedDataUnit.getOrders());
        rbo.setProperty("unitRequires", selectedDataUnit.getRequires());
        rbo.setProperty("unitCreditType", selectedDataUnit.getCreditType());
        rbo.setProperty("errorCodes", selectedDataUnit.getErrorCodes());
        rbo.setProperty("errorLines", selectedDataUnit.getErrorLines());
        rbo.setProperty("errorSameLine", selectedDataUnit.getErrorSameLine() ? "1" : "0");
        rbo.setProperty("suppressSrp", selectedDataUnit.isSrpSuppress() ? "1" : "0");
        rbo.setProperty("suppressComm", selectedDataUnit.isCommSuppress() ? "1" : "0");
        try {
            rbo.callMethod("putAopTestDataUnit");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(errCode + ": " + errMessage);
                ctx.addMessage(null, msg);
            } else {
                selectedDataUnit.setId(rbo.getProperty("unitId"));
                dataUnitList.add(selectedDataUnit);
                selectedDataUnit = new AoTestDataUnit();
                unitList.add(new SelectItem(selectedDataUnit.getId(), selectedDataUnit.getDescription()));
                setEdit(false);
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readSourceDesc(AjaxBehaviorEvent event) {
        sourceDesc.getCustList().clear();
        try {
            System.out.println("AopTestBean.readSourceDesc()");
            String commType = getCommissionType();
            if (masterBean != null) {
                masterBean.setCountryCode(countryCode);
                masterBean.setCommType(commTypeMap.get(commType));
            }
            rbo.setProperty("userName", getUserName());
            rbo.setProperty("countryCode", getCountryCode());
            if (commType != null && commTypeMap.containsKey(commType)) {
                rbo.setProperty("commissionType", commTypeMap.get(commType));
            } else {
                rbo.setProperty("commissionType", "");
            }
            rbo.callMethod("getAopSourceDesc");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            setVendorId(rbo.getPropertyToDynArray("vendorId").toString());
            setVendorDiv(rbo.getPropertyToDynArray("vendorDiv").toString());
            setStoreId(rbo.getPropertyToDynArray("storeId").toString());

            // countries this user is set up for
            UniDynArray availCountries = rbo.getPropertyToDynArray("availCountries");
            setCountryList(MVUtils.toArrayList(availCountries));
            // commissions this user is set up for
            UniDynArray availCommTypes = rbo.getPropertyToDynArray("availCommTypes");
            setCommTypeList(MVUtils.toArrayList(availCommTypes));

            UniDynArray pcIds = rbo.getPropertyToDynArray("pcId");
            UniDynArray sponsorIds = rbo.getPropertyToDynArray("sponsor");
            UniDynArray pcHomes = rbo.getPropertyToDynArray("pcHome");
            UniDynArray pcTypes = rbo.getPropertyToDynArray("pcType");
            UniDynArray orderTotals = rbo.getPropertyToDynArray("orderTotal");
            UniDynArray orderDates = rbo.getPropertyToDynArray("orderDate");

            availCommTypes = rbo.getPropertyToDynArray("commissionType");
            availCommTypes.insert(2, rbo.getPropertyToDynArray("commissionDesc"));
            allCountries = MVUtils.toArrayList(rbo.getPropertyToDynArray("countryCode"));
            commTypeMap = MVUtils.toKeyValue(availCommTypes, 2, 1);

            if (!"".equals(vendorId) && !"".equals(vendorDiv)) {
                onChangeVendor();
                String vendDiv = vendorId + "*" + vendorDiv;
                vendorDiv = vendDiv;
            }
            int size = Integer.parseInt(rbo.getProperty("itemCount"));
            boolean isNew = true;
            if ("0".equals(rbo.getProperty("isNew"))) {
                isNew = false;
            }
            sourceDesc.setNewRecord(isNew);
            sourceDesc.setChanged(false);
            for (int i = 1; i <= size; i++) {
                Customer cust = new Customer();
                cust.setPcId(pcIds.extract(1, i).toString());
                cust.setPcHome(pcHomes.extract(1, i).toString());
                cust.setPcType(pcTypes.extract(1, i).toString());
                cust.setSponsor(sponsorIds.extract(1, i).toString());
                String d = orderDates.extract(1, i).toString();
                Calendar cal = Calendar.getInstance(Locale.getDefault());
                if (d != null && !d.isEmpty()) {
                    String[] dateSeg = d.split("/");
                    int mm = Integer.parseInt(dateSeg[0]);
                    int dd = Integer.parseInt(dateSeg[1]);
                    int yy = Integer.parseInt(dateSeg[2]);
                    cal.set(Calendar.MONTH, mm);
                    cal.set(Calendar.DAY_OF_MONTH, dd);
                    cal.set(Calendar.YEAR, yy + 2000);
                }
                cust.setOrderDate(cal.getTime());
                cust.setOrderTotal(orderTotals.extract(1, i).toString());
                sourceDesc.getCustList().add(cust);
            }
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Adds a customer to a source descriptor record
     */
    public void addCustToSourceDesc() {
        rbo.setProperty("userName", getUserName());
        rbo.setProperty("countryCode", getCountryCode());
        rbo.setProperty("commissionType", commTypeMap.get(getCommissionType()));
        rbo.setProperty("pcId", sourceDesc.getPcId());
        rbo.setProperty("orderTotal", sourceDesc.getOrderTotal());
        rbo.setProperty("orderDate", sourceDesc.dateAsString());
        rbo.setProperty("pcHome", "");
        rbo.setProperty("pcType", "");
        rbo.setProperty("exchRate", "");
        rbo.setProperty("cashback", "");
        rbo.setProperty("ibv", "");
        try {
            rbo.callMethod("putAopSourceDesc");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage(errCode + ": " + errMessage, "Error");
            } else {
                JSFHelper.sendFacesMessage("Customer sucessfully added.");
                sourceDesc.setPcId("");
                sourceDesc.setOrderTotal("");
                readSourceDesc(null);
            }
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Deletes a customer from a source descriptor record
     */
    public void deleteCustFromSourceDesc() {
        rbo.setProperty("userName", getUserName());
        rbo.setProperty("countryCode", getCountryCode());
        rbo.setProperty("commissionType", commTypeMap.get(getCommissionType()));
        UniDynArray pcArray = new UniDynArray();
        for (Customer cust : selectedItems) {
            pcArray.insert(1, -1, cust.getPcId());
        }
        rbo.setProperty("pcId", pcArray);
        rbo.setProperty("isRemoving", "1");

        try {
            rbo.callMethod("setAopSourceDesc");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage(errCode + ": " + errMessage, "Error");
            } else {
                JSFHelper.sendFacesMessage("Customer successully removed");
                selectedItems.clear();
                readSourceDesc(null);
            }
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
    }

    public void removeUnitFromGroup() {
        for (AoTestDataUnit unit : selectedUnitList) {
            selectedTestDataGroup.removeUnit(unit.getId());
        }
        selectedUnitList.clear();
        setRowSelected(false);
    }

    public void deleteUnit() {
        UniDynArray ilist = new UniDynArray();
        for (AoTestDataUnit unit : selectedUnitList) {
            ilist.insert(1, -1, unit.getId());
        }
        rbo.setProperty("unitId", ilist);
        try {
            rbo.callMethod("deleteTestingAoUnit");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage(errCode + ": " + errMessage, "Error");
            } else {
                JSFHelper.sendFacesMessage("Data unit successfully deleted");
                dataUnitList.removeAll(selectedUnitList);
                selectedUnitList.clear();
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
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
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage(errCode + ": " + errMessage, "Error");
            } else {
                JSFHelper.sendFacesMessage("Update successful");
            }
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

    public void getTestDataGroups() {
        rbo.setProperty("groupId", "");
        try {
            rbo.callMethod("getAopTestGroup");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage(errCode + ": " + errMessage, "Error");
            } else {
                JSFHelper.sendFacesMessage("Update successful");
                UniDynArray keys = rbo.getPropertyToDynArray("groupId");
                UniDynArray values = rbo.getPropertyToDynArray("groupDesc");
                int valueCount = keys.dcount(1);
                for (int val = 1; val <= valueCount; val++) {
                    groupList.put(values.extract(1, val).toString(), keys.extract(1, val).toString());
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the dataUnitList
     */
    public ArrayList<AoTestDataUnit> getDataUnitList() {
        if (dataUnitList.isEmpty()) {
            System.out.println("AopTestingBean.getDataUnitList()");
            readTestDataUnits();
        }
        return dataUnitList;
    }

    /**
     * @param dataUnitList the dataUnitList to set
     */
    public void setDataUnitList(ArrayList<AoTestDataUnit> dataUnitList) {
        this.dataUnitList = dataUnitList;
    }

    public void readTestDataUnits() {
        System.out.println("AopTestingBean.readTestDataUnits()");
        rbo.setProperty("unitId", "");
        try {
            rbo.callMethod("getAopTestUnit");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                UniDynArray unitId = rbo.getPropertyToDynArray("unitId");
                UniDynArray unitDesc = rbo.getPropertyToDynArray("unitDesc");
                UniDynArray unitTransType = rbo.getPropertyToDynArray("unitTransType");
                UniDynArray unitLinesPerOrder = rbo.getPropertyToDynArray("unitLinesPerOrder");
                UniDynArray unitOrdersPerCust = rbo.getPropertyToDynArray("unitOrdersPerCust");
                UniDynArray unitRequires = rbo.getPropertyToDynArray("unitRequires");
                UniDynArray unitCreditType = rbo.getPropertyToDynArray("unitCreditType");
                UniDynArray unitErrorCodes = rbo.getPropertyToDynArray("errorCodes");
                UniDynArray unitErrorLines = rbo.getPropertyToDynArray("errorLines");
                UniDynArray unitSameLine = rbo.getPropertyToDynArray("errorSameLine");
                UniDynArray unitSrpSuppress = rbo.getPropertyToDynArray("suppressSrp");
                UniDynArray unitCommSuppress = rbo.getPropertyToDynArray("suppressComm");
                int vals = Integer.parseInt(rbo.getProperty("unitCount"));
                for (int val = 1; val <= vals; val++) {
                    AoTestDataUnit unit = new AoTestDataUnit();
                    unit.setId(unitId.extract(1, val).toString());
                    unit.setDescription(unitDesc.extract(1, val).toString());
                    unit.setTransType(unitTransType.extract(1, val).toString());
                    unit.setLines(unitLinesPerOrder.extract(1, val).toString());
                    unit.setOrders(unitOrdersPerCust.extract(1, val).toString());
                    unit.setRequires(unitRequires.extract(1, val).toString());
                    unit.setCreditType(unitCreditType.extract(1, val).toString());
                    unit.setErrorCodes(unitErrorCodes.extract(1, 1, val).toString());
                    unit.setErrorLines(unitErrorLines.extract(1, val).toString());
                    unit.setSrpSuppress(unitSrpSuppress.extract(1, val).toString().equals("1") ? true : false);
                    unit.setCommSuppress(unitCommSuppress.extract(1, val).toString().equals("1") ? true : false);
                    dataUnitList.add(unit);
                    unitList.add(new SelectItem(unit.getId(), unit.getDescription()));
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onChangeDataGroup() {
        if (groupId.equals("-1")) {
            selectedTestDataGroup = new AoTestDataGroup();
            return;
        }
        rbo.setProperty("groupId", groupId);
        try {
            rbo.callMethod("getAopTestGroup");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                LinkedHashMap<String, AoTestDataUnit> unitMap = new LinkedHashMap<>();
                HashMap<String, String> batchMap = new HashMap<>();
                HashMap<String, String> overloadMap = new HashMap<>();
                dataUnitList.clear();
                selectedTestDataGroup = new AoTestDataGroup();
                selectedTestDataGroup.setGroupId(groupId);
                int unitCount = Integer.parseInt(rbo.getProperty("groupUnitCount"));
                UniDynArray groupDescs = rbo.getPropertyToDynArray("groupDesc");
                UniDynArray createDates = rbo.getPropertyToDynArray("groupDate");
                UniDynArray unitIds = rbo.getPropertyToDynArray("groupUnits");
                UniDynArray unitDescs = rbo.getPropertyToDynArray("unitDesc");
                UniDynArray transTypes = rbo.getPropertyToDynArray("unitTransType");
                UniDynArray unitLines = rbo.getPropertyToDynArray("unitLinesPerOrder");
                UniDynArray unitOrders = rbo.getPropertyToDynArray("unitOrdersPerCust");
                UniDynArray unitRequires = rbo.getPropertyToDynArray("unitRequires");
                UniDynArray credType = rbo.getPropertyToDynArray("unitCreditType");
                UniDynArray newBatch = rbo.getPropertyToDynArray("groupNewBatch");
                UniDynArray overload = rbo.getPropertyToDynArray("groupOverRefund");
                selectedTestDataGroup.setDescription(groupDescs.extract(1, 1).toString());
                selectedTestDataGroup.setCreateDate(createDates.extract(1, 1).toString());
                for (int u = 1; u <= unitCount; u++) {
                    String unitId = unitIds.extract(1, 1, u).toString();
                    String createBatch = "Y".equals(newBatch.extract(1, 1, u).toString()) ? "Y" : "N";
                    String refundOverload = "Y".equals(overload.extract(1, 1, u).toString()) ? "Y" : "N";
                    selectedDataUnit = new AoTestDataUnit();
                    selectedDataUnit.setId(unitId);
                    selectedDataUnit.setDescription(unitDescs.extract(1, 1, u).toString());
                    selectedDataUnit.setTransType(transTypes.extract(1, 1, u).toString());
                    selectedDataUnit.setLines(unitLines.extract(1, 1, u).toString());
                    selectedDataUnit.setOrders(unitOrders.extract(1, 1, u).toString());
                    selectedDataUnit.setRequires(unitRequires.extract(1, 1, u).toString());
                    selectedDataUnit.setCreditType(credType.extract(1, 1, u).toString());
                    batchMap.put(unitId, createBatch);
                    overloadMap.put(unitId, refundOverload);
                    unitMap.put(unitId, selectedDataUnit);
                }
                selectedTestDataGroup.setNewBatch(batchMap);
                selectedTestDataGroup.setOverloadedRefund(overloadMap);
                selectedTestDataGroup.setUnits(unitMap);
                selectedTestDataGroup.setLongText(rbo.getProperty("longText"));
                selectedDataUnit = new AoTestDataUnit();
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onChangeDataUnit() {
        for (AoTestDataUnit u : dataUnitList) {
            if (u.getId().equals(selectedDataUnit.getId())) {
                selectedTestDataGroup.addUnit(u.getId(), u);
                selectedDataUnit = new AoTestDataUnit();
                return;
            }
        }
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
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
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
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
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
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
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
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onChangeTransType() {
        System.out.println("AopTestingBean.onChangeTransType(): " + selectedDataUnit.getTransType());
    }

    public void onChangeVendor() {
        RedObject vendorRbo = new RedObject("WDE", "Affiliates:Detail");
        if (masterBean != null) {
            masterBean.setCommType(commissionType);
            masterBean.setCountryCode(countryCode);
        }
        vendorRbo.setProperty("masterId", vendorId);
        storeList.clear();
        sourceDesc.setChanged(true);
        try {
            vendorRbo.callMethod("getAffiliateDetailList");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = vendorRbo.getProperty("svrCtrlCode");
            errMessage = vendorRbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                UniDynArray keys = vendorRbo.getPropertyToDynArray("keyList");
                UniDynArray values = vendorRbo.getPropertyToDynArray("valueList");
                int vals = keys.dcount(1);
                for (int val = 1; val < vals; val++) {
                    String k = keys.extract(1, val).toString();
                    String v = values.extract(1, val).toString();
                    storeList.add(new SelectItem(k, v));
                }
            }
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
    }

    public String onCreateDataGroup() {
        groupId = "";
        Date date = Calendar.getInstance(Locale.getDefault()).getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        selectedTestDataGroup = new AoTestDataGroup();
        selectedTestDataGroup.setCreateDate(df.format(date));
        setEdit(true);
        return "/testGroupSetup.xhtml?faces-redirect=true";
    }

    public void onDeleteDataGroup() {
        rbo.setProperty("groupId", groupId);
        try {
            rbo.callMethod("deleteAopTestGroup");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage,"Error");
            } else {
                selectedTestDataGroup = new AoTestDataGroup();
                groupList.remove(groupId);
                groupId = "-1";
                getTestDataGroups();
                JSFHelper.sendFacesMessage("Data group successfully deleted");
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onSaveDataGroup() {
        rbo.setProperty("groupId", groupId);
        rbo.setProperty("groupDesc", selectedTestDataGroup.getDescription());
        rbo.setProperty("groupDate", selectedTestDataGroup.getCreateDate());
        rbo.setProperty("longText", selectedTestDataGroup.getLongText());
        UniDynArray units = new UniDynArray();
        int val = 1;
        for (AoTestDataUnit unit : selectedTestDataGroup.getUnits()) {
            String unitId = unit.getId();
            units.insert(1, val, unitId);
            units.insert(2, val, selectedTestDataGroup.getNewBatch().get(unitId));
            units.insert(3, val, selectedTestDataGroup.getOverloadRefund().get(unitId));
            val++;
        }
        rbo.setProperty("groupUnits", units.extract(1));
        rbo.setProperty("groupNewBatch", units.extract(2));
        rbo.setProperty("groupOverRefund", units.extract(3));
        try {
            rbo.callMethod("putAopTestGroup");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage,"Error");
            } else {
                groupId = rbo.getProperty("groupId");
                selectedTestDataGroup.setChanged(false);
                JSFHelper.sendFacesMessage("Data group successfully saved");
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onSaveSourceDesc() {
        rbo.setProperty("userName", getUserName());
        rbo.setProperty("countryCode", getCountryCode());
        String commType = getCommissionType();
        if (commType != null && commTypeMap.containsKey(commType)) {
            rbo.setProperty("commissionType", commTypeMap.get(commType));
        } else {
            rbo.setProperty("commissionType", "");
        }

        UniDynArray custArray = new UniDynArray();
        int val = 1;
        for (Customer cust : sourceDesc.getCustList()) {
            custArray.insert(1, val, cust.getPcId());
            custArray.insert(2, val, cust.getPcHome());
            custArray.insert(3, val, cust.getPcType());
            custArray.insert(4, val, cust.getOrderDate());
            custArray.insert(5, val, cust.getOrderTotal());
        }
        rbo.setProperty("pcId", custArray.extract(1));
        rbo.setProperty("pcHome", custArray.extract(2));
        rbo.setProperty("pcType", custArray.extract(3));
        rbo.setProperty("orderDate", custArray.extract(4));
        rbo.setProperty("orderTotal", custArray.extract(5));
        rbo.setProperty("exchRate", "");
        rbo.setProperty("cashback", "");
        rbo.setProperty("ibv", "");
        rbo.setProperty("isRemoving", "");
        rbo.setProperty("vendorId", getVendorId());
        rbo.setProperty("vendorDiv", getVendorDiv());
        rbo.setProperty("storeId", "");

        try {
            rbo.callMethod("putAopSourceDesc");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage,"Error");
            } else {
                JSFHelper.sendFacesMessage("Saved successfully");
                sourceDesc.setNewRecord(false);
                sourceDesc.setChanged(false);
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onAddUnit() {

    }

    public void onChangeLines() {

    }

    public void onSelectErrorCode(ValueChangeEvent vce) {
        selectedDataUnit.setHasErrors();
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
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage,"Error");
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
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage,"Error");
            } else {
                UniDynArray utSuiteIdList = rb.getPropertyToDynArray("suiteId");
                UniDynArray utDescList = rb.getPropertyToDynArray("utDate");
                int utCount = Integer.parseInt(rb.getProperty("utCount"));
                for (int ut = 1; ut <= utCount; ut++) {
                    String id = utSuiteIdList.extract(1, ut).toString();
                    String desc = utDescList.extract(1, ut).toString();
                    SelectItem se = new SelectItem(id, desc);
                    suiteIdList.add(se);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createOrders() {
        idList.clear();
        rbo.setProperty("siteCountry", countryCode);
        rbo.setProperty("siteType", "");
        rbo.setProperty("langCode", "");
        rbo.setProperty("groupId", groupId);
        rbo.setProperty("commissionType", commTypeMap.get(commissionType));
        rbo.setProperty("userName", userName);
        try {
            rbo.callMethod("setAopSourceData");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            if (errStatus == -1) {
                errCode = rbo.getProperty("svrCtrlCode");
                errMessage = rbo.getProperty("svrmessage");
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage,"Error");
            } else {
                UniDynArray oList1 = rbo.getPropertyToDynArray("batchId");
                UniDynArray oList2 = rbo.getPropertyToDynArray("queueId");
                int vals = oList1.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    String key = oList2.extract(1, val).toString();
                    String value = oList1.extract(1, val).toString();
                    idList.add(new SelectItem(key, value));
                }
                countryCode = "";
                groupId = "";
                commissionType = "";
                userName = "";
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the masterBean
     */
    public AffiliateMasterBean getMasterBean() {
        return masterBean;
    }

    /**
     * @param master
     */
    @Inject
    public void setMasterBean(AffiliateMasterBean master) {
        masterBean = master;
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
        String id = event.getComponent().getId();
        if (id.equals("baseCountry")) {
            countryCode = event.getObject().toString();
            readSourceDesc(null);
        } else {
            setRowSelected(selectedUnitList.isEmpty());
        }
    }

    public void onRowUnselect(UnselectEvent event) {
        String id = event.getComponent().getId();
        if (id.equals("baseCountry")) {
            countryCode = "";
            readSourceDesc(null);
        } else {
            setRowSelected(selectedUnitList.isEmpty());
        }
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

    /**
     * @return the groupList
     */
    public HashMap<String, String> getGroupList() {
        getTestDataGroups();
        return groupList;
    }

    /**
     * @param groupList the groupList to set
     */
    public void setGroupList(HashMap<String, String> groupList) {
        this.groupList = groupList;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the batchList
     */
    public ArrayList<String> getBatchList() {
        return batchList;
    }

    /**
     * @param batchList the batchList to set
     */
    public void setBatchList(ArrayList<String> batchList) {
        this.batchList = batchList;
    }

    /**
     * @return the edit
     */
    public boolean isEdit() {
        return edit;
    }

    /**
     * @param bedit the edit to set
     */
    public void setEdit(boolean bedit) {
        this.edit = bedit;
    }

    public void toggleEdit(ActionEvent actionEvent) {
        setEdit(!edit);
    }

    /**
     * @return the selectedDataUnit
     */
    public AoTestDataUnit getSelectedDataUnit() {
        return selectedDataUnit;
    }

    /**
     * @param selectedDataUnit the selectedDataUnit to set
     */
    public void setSelectedDataUnit(AoTestDataUnit selectedDataUnit) {
        this.selectedDataUnit = selectedDataUnit;
    }

    /**
     * @return the selectedUnitList
     */
    public ArrayList<AoTestDataUnit> getSelectedUnitList() {
        return selectedUnitList;
    }

    /**
     * @param selectedUnitList the selectedUnitList to set
     */
    public void setSelectedUnitList(ArrayList<AoTestDataUnit> selectedUnitList) {
        this.selectedUnitList = selectedUnitList;
    }

    /**
     * @return the rowSelected
     */
    public boolean isRowSelected() {
        setRowSelected(selectedUnitList.size() == 0);
        return rowSelected;
    }

    public void setRowSelected(boolean rowsel) {
        this.rowSelected = rowsel;
    }

    /**
     * @return the selectedTestDataGroup
     */
    public AoTestDataGroup getSelectedTestDataGroup() {
        return selectedTestDataGroup;
    }

    /**
     * @param selectedTestDataGroup the selectedTestDataGroup to set
     */
    public void setSelectedTestDataGroup(AoTestDataGroup selectedTestDataGroup) {
        this.selectedTestDataGroup = selectedTestDataGroup;
    }

    /**
     * @return the unitList
     */
    public ArrayList<SelectItem> getUnitList() {
        return unitList;
    }

    /**
     * @param unitList the unitList to set
     */
    public void setUnitList(ArrayList<SelectItem> unitList) {
        this.unitList = unitList;
    }

    /**
     * @return the errorList
     */
    public ArrayList<SelectItem> getErrorList() {
        return errorList;
    }

    /**
     * @return the countryList
     */
    public ArrayList<String> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(ArrayList<String> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the commTypeList
     */
    public ArrayList<String> getCommTypeList() {
        return commTypeList;
    }

    /**
     * @param commTypeList the commTypeList to set
     */
    public void setCommTypeList(ArrayList<String> commTypeList) {
        this.commTypeList = commTypeList;
    }

    public ArrayList<String> getCommissionDescriptions() {
        ArrayList<String> list = new ArrayList<>();
        for (String s : commTypeMap.keySet()) {
            list.add(s);
        }
        return list;
    }

    public DualListModel getCountries() {
        return countryPickList;
    }

    public void setCountries(DualListModel model) {
        countryPickList = model;
    }

    /**
     * @return the allCountries
     */
    public ArrayList<String> getAllCountries() {
        return allCountries;
    }

    /**
     * @param allCountries the allCountries to set
     */
    public void setAllCountries(ArrayList<String> allCountries) {
        this.allCountries = allCountries;
    }

}
