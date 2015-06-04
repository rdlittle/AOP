/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import asjava.uniclientlibs.UniDynArray;
import com.webfront.controller.AffiliateMasterController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class AffiliateMaster implements Serializable {

    @ManagedProperty(value = "#{affiliateMasterController}")
    private AffiliateMasterController controller;
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
    protected String networkId;
    protected HashMap<Integer, AffiliateMapping> fieldMap;
    private ArrayList<SelectItem> fieldMapList;
    protected boolean newColumn;
    private Integer mfidx;
    private UIComponent selector;
    
    public AffiliateMaster() {

    }
    
    public AffiliateMaster(UniDynArray am) {
        ID = am.extract(1, 1).toString();
        name = am.extract(1,2).toString();
        category = am.extract(1,3).toString();
        type = am.extract(1,4).toString();
        prefix = am.extract(1,5).toString();
        country = am.extract(1,6).toString();
        currency = am.extract(1,7).toString();
        mappingId = am.extract(1,8).toString();
        dataFeedAccessType = am.extract(1,9).toString();
        dataFeedFormat = am.extract(1,10).toString();
        dataFeedURL = am.extract(1,11).toString();
        userName = am.extract(1,12).toString();
        password = am.extract(1,13).toString();
        createDate = am.extract(1,14).toString();
        active = am.extract(1,15).toString().equals("1");
        nextDetailId = am.extract(1,16).toString();
        newColumn = false;
    }

    public void addNewColumn() {
        int nextColumn = getFieldMap().size();
        nextColumn++;
        AffiliateMapping ibvMapping = new AffiliateMapping();
        ibvMapping.setId(Integer.toString(nextColumn));
        getFieldMap().put(nextColumn, ibvMapping);
        getFieldMapList().add(new SelectItem(Integer.toString(nextColumn), ibvMapping.getColumnName()));
        setNewColumn(true);
    }
    
    public void saveColumn() {
        setNewColumn(false);
    }

    public void fieldMapChangeListener(ValueChangeEvent vce) {
        String clientId=vce.getComponent().getClientId();
        String junk[]=clientId.split(":");
        String fieldNumberStr=junk[2];
        Integer fieldNumber=Integer.valueOf(fieldNumberStr);
        String value=vce.getNewValue().toString();
        this.fieldMap.get(fieldNumber+1).setColumnName(value);
        System.out.println(vce.toString());
    }
    
    public void changeVendor(AjaxBehaviorEvent event) {
        setID(ID);
        AffiliateMaster rec=getController().getAffiliateMaster(ID);
        setName(rec.getName());
        setCategory(rec.getCategory());
        setNetworkId(rec.getNetworkId());
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

    public void saveRecord() {
        getController().setAffiliateMaster(this);
    }
    
    public void ajaxHandler(AjaxBehaviorEvent event) {
        System.out.println("AffiliateMaster.ajaxHandler()");
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
        if (this.fieldMap != null) {
            i+=1;
            if (i <= getFieldMap().size() && i>=0) {
                return getFieldMap().get(i).getColumnName();
            }
        }
        return "";
    }
    
    public void setField(Integer i) {
        System.out.println(i);
        if (this.fieldMap != null) {
            i+=1;
            if (i <= getFieldMap().size() && i>=0) {
            }
        }
    }

    public void removeField(Integer i) {
        i+=1;
        if(i<= getFieldMap().size() && i>0) {
            HashMap<Integer,AffiliateMapping> map=getFieldMap();
            ArrayList<SelectItem> list=getFieldMapList();
            map.remove(i);
            SelectItem sel=list.get(i-1);
            list.remove(sel);
            setFieldMap(map);
            setFieldMapList(list);
        }
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

    public final void setFieldMap(HashMap<Integer, AffiliateMapping> fm) {
        this.fieldMap = fm;
    }

    /**
     * @return the fieldMap
     */
    public HashMap<Integer, AffiliateMapping> getFieldMap() {
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
    public AffiliateMasterController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(AffiliateMasterController controller) {
        this.controller = controller;
    }

    /**
     * @return the mfidx
     */
    public Integer getMfidx() {
        return mfidx;
    }

    /**
     * @param mfidx the mfidx to set
     */
    public void setMfidx(Integer mfidx) {
        this.mfidx = mfidx;
    }

    /**
     * @return the selector
     */
    public UIComponent getSelector() {
        return selector;
    }

    /**
     * @param selector the selector to set
     */
    public void setSelector(UIComponent selector) {
        this.selector = selector;
    }

    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

}
