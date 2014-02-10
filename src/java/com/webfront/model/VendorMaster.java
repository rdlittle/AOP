/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.beans.WebDEBean;
import com.webfront.controller.VendorMasterController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class VendorMaster implements Serializable {

    @ManagedProperty(value = "#{vendorMasterController}")
    private VendorMasterController controller;
    protected String ID;
    protected String name;
    protected String category;
    protected String type;
    protected String prefix;
    protected String country;
    protected String currency;
    protected String mappingId;
    protected String dataFeedAccessType;
    protected String dataFeedFormat;
    protected String dataFeedURL;
    protected String userName;
    protected String password;
    protected String createDate;
    protected boolean active;
    protected String nextDetailId;
    protected String field1;
    protected HashMap<Integer, IbvMapping> fieldMap;
    private ArrayList<SelectItem> fieldMapList;
    protected boolean newColumn;

    public VendorMaster() {

    }

    public void addNewColumn() {
        int nextColumn = getFieldMap().size();
        nextColumn++;
        IbvMapping ibvMapping = new IbvMapping();
        ibvMapping.setId(Integer.toString(nextColumn));
        getFieldMap().put(nextColumn, ibvMapping);
        getFieldMapList().add(new SelectItem(Integer.toString(nextColumn), ibvMapping.getColumnName()));
        setNewColumn(true);
    }
    
    public void saveColumn() {
        setNewColumn(false);
    }

    public void changeVendor(AjaxBehaviorEvent event) {
        setID(ID);
//        RedObject rbo = new RedObject("WDE", "Vendor:Master");
//        rbo.setProperty("id", ID);
//        try {
//            rbo.callMethod("getMaster");
//        } catch (RbException ex) {
//            Logger.getLogger(VendorMaster.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String errStat = rbo.getProperty("errStat");
//        String errCode = rbo.getProperty("errCode");
//        String errMsg = rbo.getProperty("errMsg");
//        setName(rbo.getProperty("vendorName"));
//        setCategory(rbo.getProperty("category"));
//        setType(rbo.getProperty("type"));
//        setPrefix(rbo.getProperty("prefix"));
//        setCountry(rbo.getProperty("country"));
//        setCurrency(rbo.getProperty("currencyType"));
//        setMappingId(rbo.getProperty("mappingId"));
//        setDataFeedAccessType(rbo.getProperty("dataFeedAccessMethod"));
//        setDataFeedFormat(rbo.getProperty("dataFeedFormat"));
//        setDataFeedURL(rbo.getProperty("url"));
//        setUserName(rbo.getProperty("userName"));
//        setPassword(rbo.getProperty("password"));
//        setCreateDate(rbo.getProperty("createDate"));
//        setActive(rbo.getProperty("isActive").equals("1"));
//        setNextDetailId(rbo.getProperty("nextDetailId"));
//        setFieldMap(this.populateFieldMap(ID));
//        setFieldMapList(new ArrayList<SelectItem>());
//        ArrayList<SelectItem> list = new ArrayList<>();
//        for (Integer i : getFieldMap().keySet()) {
//            String colName = getFieldMap().get(i).getColumnName();
//            list.add(new SelectItem(i.toString(), colName));
//        }
//        setFieldMapList(list);
//        setNewColumn(false);
        VendorMaster rec=getController().getVendorMaster(ID);
        setName(rec.getName());
        setCategory(rec.getCategory());
        setType(rec.getType());
        setPrefix(rec.getPrefix());
        setCountry(rec.getCountry());
        setCurrency(rec.getCurrency());
        setMappingId(rec.getMappingId());
        setDataFeedAccessType(rec.getDataFeedAccessType());
        setDataFeedFormat(rec.getDataFeedFormat());
        setDataFeedURL(rec.getDataFeedURL());
        setUserName(rec.getUserName());
        setPassword(rec.getPassword());
        setCreateDate(rec.getCreateDate());
        setActive(rec.isActive());
        setNextDetailId(rec.getNextDetailId());
        setFieldMap(rec.getFieldMap());
        setFieldMapList(rec.getFieldMapList());
        setNewColumn(false);
    }

    public HashMap<Integer, IbvMapping> populateFieldMap(String id) {
        HashMap<Integer, IbvMapping> list = new HashMap<>();
        try {
            RedObject rbo = new RedObject("WDE", "UTILS:Files");
            rbo.setProperty("fileName", "IBV.MAPPING");
            rbo.setProperty("id", id);
            rbo.callMethod("getFileRec");
            String errStat = rbo.getProperty("errStat");
            String errCode = rbo.getProperty("errCode");
            String errMsg = rbo.getProperty("errMsg");
            UniDynArray uda = rbo.getPropertyToDynArray("fileRec");
            int vals = uda.dcount(1, 1);
            for (int val = 1; val <= vals; val++) {
                IbvMapping im = new IbvMapping();
                String fieldName = uda.extract(1, 1, val).toString();
                String excludeFlag = uda.extract(1, 4, val).toString();
                im.setColumnName(fieldName);
                im.setExclude(excludeFlag);
                list.put(Integer.valueOf(val), im);
            }

        } catch (RbException rbe) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, rbe);
        }
        return list;
    }

    public void ajaxHandler(AjaxBehaviorEvent event) {
        System.out.println("VendorMaster.ajaxHandler()");
    }

    public void changeCategory(AjaxBehaviorEvent event) {
        this.setCategory(category);
    }

    public void changeCurrency(AjaxBehaviorEvent event) {
        this.setCurrency(currency);
    }

    public void changeType(AjaxBehaviorEvent event) {
        this.setType(type);
    }

    public void changeCountry(AjaxBehaviorEvent event) {
        System.out.println(event.toString());
    }

    public void changeField(AjaxBehaviorEvent event) {
        System.out.println(event.toString());
        this.setType(type);
    }

    public String getField(Integer i) {
        if (null != getFieldMap()) {
            if (i <= getFieldMap().size()) {
                return getFieldMap().get(i).getColumnName();
            }
        }
        return "";
    }

    public void removeField(Integer i) {

    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the mappingId
     */
    public String getMappingId() {
        return mappingId;
    }

    /**
     * @param mappingId the mappingId to set
     */
    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    /**
     * @return the dataFeedAccessType
     */
    public String getDataFeedAccessType() {
        return dataFeedAccessType;
    }

    /**
     * @param dataFeedAccessType the dataFeedAccessType to set
     */
    public void setDataFeedAccessType(String dataFeedAccessType) {
        this.dataFeedAccessType = dataFeedAccessType;
    }

    /**
     * @return the dataFeedFormat
     */
    public String getDataFeedFormat() {
        return dataFeedFormat;
    }

    /**
     * @param dataFeedFormat the dataFeedFormat to set
     */
    public void setDataFeedFormat(String dataFeedFormat) {
        this.dataFeedFormat = dataFeedFormat;
    }

    /**
     * @return the dataFeedURL
     */
    public String getDataFeedURL() {
        return dataFeedURL;
    }

    /**
     * @param dataFeedURL the dataFeedURL to set
     */
    public void setDataFeedURL(String dataFeedURL) {
        this.dataFeedURL = dataFeedURL;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the nextDetailId
     */
    public String getNextDetailId() {
        return nextDetailId;
    }

    /**
     * @param nextDetailId the nextDetailId to set
     */
    public void setNextDetailId(String nextDetailId) {
        this.nextDetailId = nextDetailId;
    }

    /**
     * @return the field1
     */
    public String getField1() {
        return field1;
    }

    /**
     * @param field1 the field1 to set
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }

    public final void setFieldMap(HashMap<Integer, IbvMapping> fm) {
        this.fieldMap = fm;
    }

    /**
     * @return the fieldMap
     */
    public HashMap<Integer, IbvMapping> getFieldMap() {
        return fieldMap;
    }

    /**
     * @return the fieldMapList
     */
    public ArrayList<SelectItem> getFieldMapList() {
        return fieldMapList;
    }

    /**
     * @return the newColumn
     */
    public boolean isNewColumn() {
        return newColumn;
    }

    /**
     * @param newColumn the newColumn to set
     */
    public void setNewColumn(boolean newColumn) {
        this.newColumn = newColumn;
    }

    /**
     * @param fml
     */
    public final void setFieldMapList(ArrayList<SelectItem> fml) {
        this.fieldMapList = fml;
    }

    /**
     * @return the controller
     */
    public VendorMasterController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(VendorMasterController controller) {
        this.controller = controller;
    }

}
