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
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean(name = "webde")
@SessionScoped
public class WebDE implements Serializable {

    private RedObject rbo;
    private String accountName;
    private String moduleName;
    private String methodName;
    private String className;

    private HashMap<String, String> inProperties;
    private HashMap<String, String> outProperties;

    private HashMap<String, Object> objMap;
    private ArrayList<Object> objList;
    private ArrayList<AopSourceDesc> sourceList;
    private ArrayList<AopSourceDesc> selectedItems;

    String inProperty;
    String inValue;

    int errStatus;
    String errMessage;
    String errCode;
    
    private AopSourceDesc item;

    public WebDE() {
        inProperties = new HashMap<>();
        outProperties = new HashMap<>();
        accountName = null;
        methodName = null;
        moduleName = null;
        objMap = new HashMap<>();
        objList = new ArrayList<>();
        sourceList = new ArrayList<>();
        selectedItems = new ArrayList<>();
        item = new AopSourceDesc();
    }

    private void setRbo() {
        this.rbo = new RedObject(this.accountName, this.moduleName + ":" + this.className);
    }

    public void setInProp(String s) {
        inProperty = s;
    }

    public void setInValue(String s) {
        inValue = s;
    }

    public String getInProp() {
        return inProperty;
    }

    public String getInValue() {
        return inValue;
    }

    public void call() throws Exception {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext eCtx = ctx.getExternalContext();
        Map<String, String> map = eCtx.getRequestParameterMap();
        if (map.containsKey("accountName")) {
            setAccountName((String) map.get("accountName"));
        }
        if (map.containsKey("moduleName")) {
            setModuleName((String) map.get("moduleName"));
        }
        if (map.containsKey("className")) {
            setClassName((String) map.get("className"));
        }
        if (map.containsKey("methodName")) {
            setMethodName((String) map.get("methodName"));
        }
        if (this.accountName == null) {
            return;
        }
        if (this.moduleName == null) {
            return;
        }
        if (this.className == null) {
            return;
        }
        setRbo();
        for (String key : this.inProperties.keySet()) {
            String value = this.inProperties.get(key);
            getRbo().setProperty(key, value);
        }
        getRbo().callMethod(getMethodName());

        Vector<String> propertyNames = getRbo().getPropertyNames();
        Vector<String> propertyValues = getRbo().getPropertyValues();
        objList.addAll(propertyValues);
        for (String pName : propertyNames) {
            String pValue = getRbo().getProperty(pName);
            UniDynArray uniDynarray = getRbo().getPropertyToDynArray(pName);
            outProperties.put(pName, pValue);
            getObjectMap().put(pName, uniDynarray);
        }

    }

    public ArrayList<AopSourceDesc> getSourceList() {
        return sourceList;
    }

    public void setSourceList(AjaxBehaviorEvent event) {
        sourceList = getAopSourceDesc();
    }

    public void buttonHandler() {
        sourceList = getAopSourceDesc();
    }

    public ArrayList<AopSourceDesc> getAopSourceDesc() {
        ArrayList<AopSourceDesc> list = new ArrayList<>();
        setAccountName("WDE");
        setModuleName("AOP");
        setClassName("Testing");
        setMethodName("getAopSourceDesc");
        setRbo();
        try {
            for (String key : this.inProperties.keySet()) {
                String value = this.inProperties.get(key);
                if (value != null) {
                    getRbo().setProperty(key, value);
                }
            }
            getRbo().callMethod(getMethodName());
            errStatus = Integer.parseInt(getRbo().getProperty("errStat"));
            errCode = getRbo().getProperty("errCode");
            errMessage = getRbo().getProperty("errMsg");
            UniDynArray pcIds = getRbo().getPropertyToDynArray("pcId");
            UniDynArray sponsorIds = getRbo().getPropertyToDynArray("sponsor");
            UniDynArray pcHomes = getRbo().getPropertyToDynArray("pcHome");
            UniDynArray pcTypes = getRbo().getPropertyToDynArray("pcType");
            UniDynArray orderTotals = getRbo().getPropertyToDynArray("orderTotal");
            UniDynArray orderDates = getRbo().getPropertyToDynArray("orderDate");
            int size = Integer.parseInt(getRbo().getProperty("itemCount"));
            for (int i = 1; i <= size; i++) {
                AopSourceDesc desc = new AopSourceDesc();
                System.out.println(pcIds.count(1));
                desc.setPcId(pcIds.extract(1, i).toString());
                desc.setPcHome(pcHomes.extract(1, i).toString());
                desc.setPcType(pcTypes.extract(1, i).toString());
                desc.setSponsor(sponsorIds.extract(1, i).toString());
                String d=orderDates.extract(1,i).toString();
                LocalDate localDate = LocalDate.parse(d);
                Calendar cal = Calendar.getInstance(Locale.getDefault());
                Date date = cal.getTime();
                date.setTime(localDate.toEpochDay());
                desc.setOrderDate(date);
                desc.setOrderTotal(orderTotals.extract(1, i).toString());
                list.add(desc);
            }
        } catch (RbException ex) {
            System.out.println(ex.toString());
        }
        return list;
    }
    
    public void addItem() {
        System.out.println("sourceList.length before add = "+sourceList.size());
        sourceList.add(item);
        item = new AopSourceDesc();
        System.out.println("sourceList.length after add = "+sourceList.size());
    }

    public void setObjectMap(HashMap<String, Object> inList) {
        this.objMap = inList;
    }

    public HashMap<String, Object> getObjectMap() {
        return this.objMap;
    }

    public void setObectMap(ArrayList<Object> obl) {
        this.objList = obl;
    }

    public ArrayList<Object> getObjectList() {
        return this.objList;
    }

    public void setAccountName(String arg) {
        this.accountName = arg;
    }

    public void setModuleName(String arg) {
        this.moduleName = arg;
    }

    public void setClassName(String arg) {
        this.className = arg;
    }

    public void setMethodName(String arg) {
        this.methodName = arg;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public String getClassName() {
        return this.className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public RedObject getRbo() {
        return this.rbo;
    }

    /**
     * @return the inProperties
     */
    public HashMap<String, String> getInProperties() {
        return this.inProperties;
    }

    /**
     * @param map the inProperties to set
     */
    public void setInProperties(HashMap<String, String> map) {
        this.inProperties = map;
    }

    /**
     * @return the outProperties value associated with the key
     */
    public HashMap<String, String> getOutProperties() {
        return this.outProperties;
    }

    /**
     * @param outProps the outProperties to set
     */
    public void setOutProperties(HashMap<String, String> outProps) {
        this.outProperties = outProps;
    }

    /**
     * @return the selectedItems
     */
    public ArrayList<AopSourceDesc> getSelectedItems() {
        return selectedItems;
    }

    /**
     * @param selectedItems the selectedItems to set
     */
    public void setSelectedItems(ArrayList<AopSourceDesc> selectedItems) {
        this.selectedItems = selectedItems;
    }

    /**
     * @return the item
     */
    public AopSourceDesc getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(AopSourceDesc item) {
        this.item = item;
    }

}
