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
import com.webfront.beans.AggregatorBean;
import com.webfront.model.AopSourceDesc;
import com.webfront.model.Customer;
import com.webfront.model.SelectItem;
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
import java.util.Map;
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
import org.primefaces.model.DualListModel;

/**
 *
 * @author rlittle
 */
@Named("aopTestBean")
@SessionScoped
public class AopTestingBean implements Serializable {

    /**
     * @return the selectedCustomer
     */
    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * @param selectedCustomer the selectedCustomer to set
     */
    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

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
    private String unitId;

    private AggregatorBean masterBean;

    @ManagedProperty(value = "#{affiliateDetailBean}")
    private AffiliateDetailBean detailBean;

    private ArrayList<SelectItem> storeList;
    private ArrayList<SelectItem> idList;
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

    int errStatus;
    String errMessage;
    String errCode;
    private ArrayList<String> batchList;
    private boolean edit;
    private boolean rowSelected;
    private AoTestDataUnit selectedDataUnit;
    private AoTestDataGroup selectedTestDataGroup;
    private Customer selectedCustomer;

    public AopTestingBean() {
        groupId = "";
        unitId = "";
        sourceDesc = new AopSourceDesc();
        batchList = new ArrayList<>();
        groupList = new HashMap<>();
        selectedItems = new ArrayList<>();
        selectedUnitList = new ArrayList<>();
        storeList = new ArrayList<>();
        idList = new ArrayList<>();
        testSuite = new UnitTestSuite();
        suiteIdList = new ArrayList<>();
        dataUnitList = new ArrayList<>();
        unitList = new ArrayList<>();
        errorList = new ArrayList<>();
        countryList = new ArrayList<>();
        commTypeList = new ArrayList<>();
        userName = "";
        countryCode = "";
        commissionType = "";
        commTypeMap = new HashMap<>();
        rbo = new RedObject("WDE", "AOP:Testing");
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
        utLogName = "";
        utLogDate = "";
        utLogTime = "";
        selectedCustomer = new Customer();
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
                rbo.setProperty("commissionType", commType);
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
        rbo.setProperty("pcId", selectedCustomer.getPcId());
        rbo.setProperty("orderTotal", selectedCustomer.getOrderTotal());
        rbo.setProperty("orderDate", selectedCustomer.dateAsString());
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
                selectedCustomer.setPcId("");
                selectedCustomer.setOrderTotal("");
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
        rbo.setProperty("commissionType", getCommissionType());
        UniDynArray pcArray = new UniDynArray();
        int val=1;
        int deleteFlag;
        int includeFlag;
        for(Customer c : sourceDesc.getCustList()) {
            deleteFlag = 0;
            includeFlag = 1;
            if(selectedItems.contains(c)) {
                deleteFlag = 1;
            }
            pcArray.insert(1, val, c.getPcId());
            pcArray.insert(2, val++, deleteFlag);
        }
        selectedItems.stream().forEach((cust) -> {
            pcArray.insert(1, -1, cust.getPcId());
        });
        rbo.setProperty("pcId", pcArray.extract(1));
        rbo.setProperty("isRemoving", pcArray.extract(2));

        try {
            rbo.callMethod("putAopSourceDesc");
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
        selectedUnitList.stream().forEach((unit) -> {
            ilist.insert(1, -1, unit.getId());
        });
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
//            System.out.println("AopTestingBean.getDataUnitList()");
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
//        System.out.println("AopTestingBean.readTestDataUnits()");
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
                    unit.setSrpSuppress(unitSrpSuppress.extract(1, val).toString().equals("1"));
                    unit.setCommSuppress(unitCommSuppress.extract(1, val).toString().equals("1"));
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
                HashMap<String, Boolean> batchMap = new HashMap<>();
                HashMap<String, Boolean> overloadMap = new HashMap<>();
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
                    String createBatch = newBatch.extract(1, 1, u).toString();
                    String refundOverload = overload.extract(1, 1, u).toString();
                    selectedDataUnit = new AoTestDataUnit();
                    selectedDataUnit.setId(unitId);
                    selectedDataUnit.setDescription(unitDescs.extract(1, 1, u).toString());
                    selectedDataUnit.setTransType(transTypes.extract(1, 1, u).toString());
                    selectedDataUnit.setLines(unitLines.extract(1, 1, u).toString());
                    selectedDataUnit.setOrders(unitOrders.extract(1, 1, u).toString());
                    selectedDataUnit.setRequires(unitRequires.extract(1, 1, u).toString());
                    selectedDataUnit.setCreditType(credType.extract(1, 1, u).toString());
                    batchMap.put(unitId, createBatch.equals("1"));
                    overloadMap.put(unitId, refundOverload.equals("1"));
                    unitMap.put(unitId, selectedDataUnit);
                }
                selectedTestDataGroup.setNewBatch(batchMap);
                selectedTestDataGroup.setOverloadedRefund(overloadMap);
                selectedTestDataGroup.setUnitMap(unitMap);
                selectedTestDataGroup.setLongText(rbo.getProperty("longText"));
                selectedTestDataGroup.getUnitList().addAll(unitMap.values());
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

    public String onCreateDataGroup(ActionEvent event) {
        groupId = "";
        Date date = Calendar.getInstance(Locale.getDefault()).getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        selectedTestDataGroup = new AoTestDataGroup();
        selectedTestDataGroup.setCreateDate(df.format(date));
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> map = ctx.getExternalContext().getRequestParameterMap();
        setEdit(true);
        return "/affiliate/testing/testGroupSetup.xhtml?faces-redirect=true";
    }

    public String onEditDataGroup(ActionEvent event) {
        return "/affiliate/testing/testGroupSetup.xhtml?faces-redirect=true";
    }

    public void onDeleteDataGroup() {
        rbo.setProperty("groupId", groupId);
        try {
            rbo.callMethod("deleteAopTestGroup");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            errCode = rbo.getProperty("svrCtrlCode");
            errMessage = rbo.getProperty("svrMessage");
            if (errStatus == -1) {
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
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

    public String onSaveDataGroup(ActionEvent event) {
        rbo.setProperty("groupId", groupId);
        rbo.setProperty("groupDesc", selectedTestDataGroup.getDescription());
        rbo.setProperty("groupDate", selectedTestDataGroup.getCreateDate());
        rbo.setProperty("longText", selectedTestDataGroup.getLongText());
        UniDynArray units = new UniDynArray();
        int val = 1;
        for (AoTestDataUnit unit : selectedTestDataGroup.getUnitMap()) {
            unitId = unit.getId();
            units.insert(1, val, unitId);
            units.insert(2, val, selectedTestDataGroup.getNewBatch());
            units.insert(3, val, selectedTestDataGroup.getOverloadRefund());
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
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                groupId = rbo.getProperty("groupId");
                selectedTestDataGroup.setChanged(false);
                setEdit(false);
                JSFHelper.sendFacesMessage("Data group successfully saved");
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/affiliate/testing/aoTestGroup.xhtml?faces-redirect=true";
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
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
            } else {
                JSFHelper.sendFacesMessage("Saved successfully");
                sourceDesc.setNewRecord(false);
                sourceDesc.setChanged(false);
            }
        } catch (RbException ex) {
            Logger.getLogger(AopTestingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onSelectDataUnit() {
        if (!unitId.isEmpty()) {
            for (AoTestDataUnit u : dataUnitList) {
                if (unitId.equals(u.getId())) {
                    selectedDataUnit = u;
                    selectedTestDataGroup.setUnit(u);
                    return;
                }
            }
        }
    }

    public void onSelectVendorDiv() {
        for (SelectItem se : storeList) {
            if (se.getLabel().equalsIgnoreCase(vendorDiv)) {
                storeId = se.getKey();
                break;
            }
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
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
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
        rbo.setProperty("commissionType", commissionType);
        rbo.setProperty("userName", userName);
        try {
            rbo.callMethod("setAopTestData");
            errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
            if (errStatus == -1) {
                errCode = rbo.getProperty("svrCtrlCode");
                errMessage = rbo.getProperty("svrMessage");
                JSFHelper.sendFacesMessage("Error: [" + errCode + "] " + errMessage, "Error");
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
        if (commType != null) {
            this.commissionType = commType.substring(0, 1).toUpperCase();
        }
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
    public AggregatorBean getMasterBean() {
        return masterBean;
    }

    /**
     * @param master
     */
    @Inject
    public void setMasterBean(AggregatorBean master) {
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

    public void onRowSelect() {
        setRowSelected(selectedUnitList.isEmpty());
    }

    public void onRowUnselect() {
        setRowSelected(selectedUnitList.isEmpty());
    }

    public void onCustRowSelect() {
        setRowSelected(selectedUnitList.isEmpty());
    }

    public void onCustRowUnselect() {
        setRowSelected(selectedUnitList.isEmpty());
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

    public void init() {
        groupId = "-1";
        selectedTestDataGroup = new AoTestDataGroup();
        setEdit(false);
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

    /**
     * @return the unitId
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

}
