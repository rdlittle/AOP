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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UISelectOne;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

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
    private String userName;
    private String countryCode;
    private String commissionType;
    private String vendorId;
    private String vendorDiv;
    private String storeId;
    @ManagedProperty(value = "#{affiliateMasterBean}")
    private AffiliateMasterBean masterBean;
    @ManagedProperty(value = "#{affiliateDetailBean}")
    private AffiliateDetailBean detailBean;
    private UISelectOne uiSelectVendor;
    private UISelectOne uiSelectStore;
    private ArrayList<SelectItem> storeList;

    int errStatus;
    String errMessage;
    String errCode;

    public AopTesting() {
        sourceDesc = new AopSourceDesc();
        selectedItems = new ArrayList<>();
        storeList = new ArrayList<>();
        userName = "";
        countryCode = "";
        commissionType = "";
        rbo = new RedObject("WDE", "AOP:Testing");
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
        rbo.setProperty("pcId", sourceDesc.getPcId());
        rbo.setProperty("orderTotal", sourceDesc.getOrderTotal());
        rbo.setProperty("orderDate", sourceDesc.dateAsString());
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

}
