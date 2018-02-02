/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.controller.AopQueueController;
import com.webfront.model.AffiliateMapping;
import com.webfront.model.Aggregator;
import com.webfront.model.ErrorObject;
import com.webfront.model.MappingField;
import com.webfront.model.SelectItem;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AffiliateMappingBean {

    private final RedObject rbo = new RedObject("WDE", "AFFILIATE:Mapping");
    private AffiliateMapping affiliateMapping;
    private Aggregator aggregator;
    private String aggregatorId;
    protected String _aggregatorId;
    private final ArrayList<String> fileTypes;
    public ErrorObject errObj;
    private final ArrayList<MappingField> reportFields;
    private ArrayList<SelectItem> availableFields;
    private int index;
    private SelectItem[] list;

    private final int AGG_ID = 1;
    private final int HAS_HEADER = 2;
    private final int DATA_START_ROW = 3;
    private final int DATA_STRIP = 4;
    private final int FIELD_DELIMITER = 5;
    private final int FIELD_NUMBER = 6;
    private final int FIELD_LABEL = 7;
    private final int FIELD_KEY = 8;
    private final int FIELD_VALUE = 9;
    private final int FIELD_TYPE = 10;
    private final int FIELD_WEIGHT = 11;
    private final int FIELD_SOURCE = 12;
    private final int FIELD_STRIP = 13;
    private final int FIELD_SEPARATOR = 14;
    private final int FIELD_EXTRACT = 15;
    private final int MAX_FIELDS = 50;

    public AffiliateMappingBean() {
        affiliateMapping = new AffiliateMapping();
        aggregator = new Aggregator();
        aggregatorId = "";
        _aggregatorId = "";
        fileTypes = new ArrayList<>();
        errObj = new ErrorObject();
        availableFields = new ArrayList<>();
        reportFields = new ArrayList<>();
        index = 1;
        list = new SelectItem[MAX_FIELDS];
    }

    @PostConstruct
    public void init() {
        try {
            RedObject rb = new RedObject("WDE", "UTILS:Files");
            rb.setProperty("fileName", "PARAMS");
            rb.setProperty("id", "AFFILIATE.MAPPING.CONTROL");
            rb.setProperty("keyField", "1");
            rb.setProperty("valueField", "3");
            rb.callMethod("getSelectObject");
            String errStat = rb.getProperty("errStat");
            String errCode = rb.getProperty("errCode");
            String errMsg = rb.getProperty("errMsg");
            UniDynArray keys = rb.getPropertyToDynArray("keyList");
            UniDynArray values = rb.getPropertyToDynArray("valueList");
            int vals = keys.dcount(1);
            for (int i = 1; i <= vals; i++) {
                String key = keys.extract(1, i).toString();
                String value = values.extract(1, i).toString();
                SelectItem se = new SelectItem(key,value);
                getAvailableFields().add(se);
                list[i] = se;
            }
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<MappingField> getReportFields() {
        return this.reportFields;
    }
    /**
     * @return the index
     */
    public int getIndex() {
        return reportFields.size();
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    public void setAggregator() {
        _aggregatorId = getAggregatorId();
        RedObject aggrbo = new RedObject("WDE", "AFFILIATE:Aggregator");
        aggrbo.setProperty("aggregatorID", getAggregatorId());
        try {
            aggrbo.callMethod("getAffiliateAggregator");
            String errStat = aggrbo.getProperty("svrStatus");
            String errCode = aggrbo.getProperty("svrCtrlCode");
            String errMsg = aggrbo.getProperty("svrMessage");
            errObj = new ErrorObject(errStat, errCode, errMsg);
            if (-1 == errObj.getSvrStatus()) {
                FacesMessage fmsg = new FacesMessage(errObj.toString());
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                aggregator.setID(getAggregatorId());
                aggregator.setName(aggrbo.getProperty("aggregatorName"));
                UniDynArray uda = aggrbo.getPropertyToDynArray("reportFormat");
                int sz = uda.dcount(1);
                getFileTypes().clear();
                for(int s=1;s<=sz;s++) {
                    this.getFileTypes().add(uda.extract(1, s).toString());
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateMappingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AffiliateMapping getMapping() {
        return this.affiliateMapping;
    }

    public void onChangeFileType() {
        readMapping(getAggregatorId());
    }
    
    public void readMapping(String aggregatorId) {
        String fileType = affiliateMapping.getFileType();
        rbo.setProperty("aggregatorId", aggregatorId);
        rbo.setProperty("fileType", "TXT");
        UniDynArray oList = new UniDynArray();
        try {
            rbo.callMethod("getAffiliateMapping");
            String errStat = rbo.getProperty("svrStatus");
            String errCode = rbo.getProperty("svrCtrlCode");
            String errMsg = rbo.getProperty("svrMessage");
            errObj = new ErrorObject(errStat, errCode, errMsg);
            if (-1 == errObj.getSvrStatus()) {
                FacesMessage fmsg = new FacesMessage(errObj.toString());
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                this.affiliateMapping = new AffiliateMapping();

                oList.insert(AGG_ID, rbo.getProperty("aggregatorId"));
                oList.insert(HAS_HEADER, rbo.getProperty("hasHeader"));
                oList.insert(DATA_START_ROW, rbo.getProperty("dataStartRow"));
                oList.insert(DATA_STRIP, rbo.getProperty("dataStrip"));
                oList.insert(FIELD_DELIMITER, rbo.getProperty("fieldDelimiter"));
                oList.insert(FIELD_NUMBER, rbo.getPropertyToDynArray("fieldNumber"));
                oList.insert(FIELD_LABEL, rbo.getPropertyToDynArray("fieldLabel"));
                oList.insert(FIELD_KEY, rbo.getPropertyToDynArray("fieldKey"));
                oList.insert(FIELD_VALUE, rbo.getPropertyToDynArray("fieldValue"));
                oList.insert(FIELD_TYPE, rbo.getPropertyToDynArray("fieldType"));
                oList.insert(FIELD_WEIGHT, rbo.getPropertyToDynArray("fieldWeight"));
                oList.insert(FIELD_SOURCE, rbo.getPropertyToDynArray("fieldSource"));
                oList.insert(FIELD_STRIP, rbo.getPropertyToDynArray("fieldStrip"));
                oList.insert(FIELD_SEPARATOR, rbo.getPropertyToDynArray("fieldSeparator"));
                oList.insert(FIELD_EXTRACT, rbo.getPropertyToDynArray("fieldExtract"));
                
                reportFields.clear();
                int vals = oList.dcount(FIELD_LABEL);
                for (int val = 1; val <= vals; val++) {
                    String fieldNum = oList.extract(FIELD_NUMBER,val).toString();
                    String label = oList.extract(FIELD_LABEL, val).toString();
                    String key = oList.extract(FIELD_KEY, val).toString();
                    String value = oList.extract(FIELD_VALUE, val).toString();
                    String type = oList.extract(FIELD_TYPE, val).toString();
                    String weight = oList.extract(FIELD_WEIGHT, val).toString();
                    String source = oList.extract(FIELD_SOURCE, val).toString();
                    String strip = oList.extract(FIELD_STRIP, val).toString();
                    String separator = oList.extract(FIELD_SEPARATOR, val).toString();
                    String extract = oList.extract(FIELD_EXTRACT, val).toString();
                    
                    MappingField mf = new MappingField();
                    mf.setFieldLabel(label);
                    mf.setFieldKey(key);
                    mf.setFieldValue(value);
                    mf.setFieldType(type);
                    mf.setFieldWeight(weight);
                    mf.setFieldSource(source);
                    mf.setFieldStrip(strip);
                    mf.setFieldSeparator(separator);
                    mf.setFieldExtract(extract);
                    mf.setFieldNumber(fieldNum);
                    
                    this.affiliateMapping.getFieldMap().put(source, mf);
                    reportFields.add(mf);
                    
                }
                
                list = new SelectItem[MAX_FIELDS];
                for(MappingField mf : reportFields) {
                    String k = mf.getFieldKey();
                    String v = mf.getFieldValue();
                    SelectItem se = new SelectItem(k,v);
                    availableFields.remove(se);
                }
                index = 1;
                for (SelectItem se : availableFields) {
                    list[index++] = se;
                }
                this.affiliateMapping.setAggregatorId(aggregatorId);
                this.affiliateMapping.setFileType(fileType);
                this.affiliateMapping.setHasHeader(oList.extract(HAS_HEADER).toString().equals("1"));
                this.affiliateMapping.setDataStartRow(oList.extract(DATA_START_ROW).toString());
                this.affiliateMapping.setDataStrip(oList.extract(DATA_STRIP).toString());
                this.affiliateMapping.setFieldSeparator(oList.extract(FIELD_DELIMITER).toString());
                
            }

        } catch (RbException ex) {
            Logger.getLogger(AopQueueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onFieldDrop(DragDropEvent ddEvent) {
        SelectItem se = ((SelectItem) ddEvent.getData());
        availableFields.remove(se);
        boolean isFound = false;
        for(MappingField mf : reportFields) {
            if(mf.getFieldKey().equals(se.getKey())) {
                isFound = true;
                break;
            }
        }
        if(!isFound) {
            MappingField mf = new MappingField();
            mf.setFieldKey(se.getKey());
        }
    }

    /**
     * @return the availableFields
     */
    public ArrayList<SelectItem> getAvailableFields() {
        return availableFields;
    }

    /**
     * @param availableFields the availableFields to set
     */
    public void setAvailableFields(ArrayList<SelectItem> availableFields) {
        this.availableFields = availableFields;
    }

    /**
     * @return the aggregatorId
     */
    public String getAggregatorId() {
        return aggregatorId;
    }

    /**
     * @param aggregatorId the aggregatorId to set
     */
    public void setAggregatorId(String aggregatorId) {
        this.aggregatorId = aggregatorId;
    }

    /**
     * @return the fileTypes
     */
    public ArrayList<String> getFileTypes() {
        return fileTypes;
    }

    /**
     * @return the list
     */
    public SelectItem[] getList() {
        return list;
    }

}
