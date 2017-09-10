/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.controller.AffiliateAggregatorController;
import com.webfront.controller.AopQueueController;
import com.webfront.model.AffiliateMapping;
import com.webfront.model.ErrorObject;
import com.webfront.model.Mapping;
import com.webfront.util.JSFHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AffiliateMappingBean {

    private final RedObject rbo = new RedObject("WDE", "AFFILIATE:Mapping");
    private final ArrayList<AffiliateMapping> mapping;
    private String affiliateMasterId;
    protected String _affiliateMasterId;
    private String mappingId;
    private boolean newField;
    private boolean fieldCommitted;
    private boolean fileSaved;
    private String fileType;
    private String fieldSeparator;
    private final ArrayList<String> fileTypes;
    private boolean hasHeader;
    private TreeNode tree;
    private String dataStartRow;
    private HashMap<String, String> fieldValues;
    public ErrorObject errObj;
    private final ArrayList<AopQueueController.ColumnModel> columnHeaders;

    public AffiliateMappingBean() {
        mapping = new ArrayList<>();
        affiliateMasterId = "";
        _affiliateMasterId = "";
        mappingId = "";
        newField = false;
        fieldCommitted = false;
        fileSaved = true;
        fileType = "";
        fieldSeparator = "";
        fileTypes = new ArrayList<>();
        tree = new DefaultTreeNode();
        dataStartRow = "0";
        fieldValues = new HashMap<>();
        errObj = new ErrorObject();
        columnHeaders = new ArrayList<>();
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
                fieldValues.put(key, value);
            }
        } catch (RbException ex) {
            Logger.getLogger(WebDEBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAggregatorId() {
        _affiliateMasterId = affiliateMasterId;
        fileType = "";
        setFileTypes();
        mapping.clear();
    }

    public void changeFileType() {
        if (fileType.equals("-1") && mapping.size() > 0) {
            mapping.clear();
            getTree().getChildren().clear();
        } else if (!mappingId.isEmpty() && !newField) {
            mapping.clear();
            getTree().getChildren().clear();
            rbo.setProperty("aggregatorId", mappingId);
            rbo.setProperty("fileType", fileType);
            try {
                rbo.callMethod("getAffiliateMapping");
                int errStat = Integer.parseInt(rbo.getProperty("svrStatus"));
                if (errStat == -1) {
                    String errCode = rbo.getProperty("svrCtrlCode");
                    String errMsg = "[" + errCode + "] " + rbo.getProperty("svrMessage");
                    JSFHelper.sendFacesMessage(errMsg, "Error");
                } else {
                    UniDynArray uda = new UniDynArray();
                    uda.insert(1, rbo.getPropertyToDynArray("fieldLabel"));
                    uda.insert(2, rbo.getPropertyToDynArray("fieldKey"));
                    uda.insert(3, rbo.getPropertyToDynArray("fieldValue"));
                    uda.insert(4, rbo.getPropertyToDynArray("multiPart"));
                    uda.insert(5, rbo.getPropertyToDynArray("fieldSeparator"));
                    uda.insert(6, rbo.getPropertyToDynArray("subFieldKey"));
                    uda.insert(7, rbo.getPropertyToDynArray("subFieldValue"));
                    uda.insert(8, rbo.getPropertyToDynArray("subFieldLabel"));
                    fileType = rbo.getProperty("fileType");
                    dataStartRow = rbo.getPropertyToDynArray("dataStartRow").toString();
                    hasHeader = rbo.getProperty("hasHeader").equals("1");
                    int fieldCount = Integer.parseInt(rbo.getProperty("fieldCount"));
                    for (int f = 1; f <= fieldCount; f++) {
                        AffiliateMapping map = new AffiliateMapping();
                        map.setFieldLabel(uda.extract(1, f).toString());
                        map.setFieldKey(uda.extract(2, f).toString());
                        map.setFieldValue(uda.extract(3, f).toString());
                        map.setFieldSeparator(uda.extract(5, f).toString());
                        int subCount = uda.dcount(6, f);
                        for (int s = 1; s <= subCount; s++) {
                            Mapping sm = new Mapping();
                            sm.setFieldKey(uda.extract(6, f, s).toString());
                            sm.setFieldValue(uda.extract(7, f, s).toString());
                            sm.setFieldLabel(uda.extract(8, f, s).toString());
                            map.getFields().add(sm);
                        }
                        map.setFieldSaved(true);
                        fileSaved = true;
                        mapping.add(map);
                    }
                }
            } catch (RbException ex) {
                Logger.getLogger(AffiliateAggregatorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void onTextChange() {

    }

    public void onCellEdit(CellEditEvent event) {
        String oldValue = (String) event.getOldValue();
        String newValue = (String) event.getNewValue();
        if (!newValue.equals(oldValue)) {
            AffiliateMapping item = (AffiliateMapping) mapping.get(event.getRowIndex());
            item.setFieldKey(newValue);
            item.setFieldValue(fieldValues.get(newValue));
            fileSaved = false;
        }
    }

    public ArrayList<AffiliateMapping> getMapping() {
        return mapping;
    }

    public void readMapping(String aggregatorId) {
        rbo.setProperty("aggregatorId", aggregatorId);
        rbo.setProperty("fileType", "CSV");
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
                AffiliateMapping afMapping = new AffiliateMapping();
                UniDynArray oList = new UniDynArray();
                oList.insert(1, rbo.getPropertyToDynArray("fieldLabel"));
                oList.insert(2, rbo.getPropertyToDynArray("fieldKey"));
                oList.insert(3, rbo.getPropertyToDynArray("fieldValue"));
                oList.insert(4, rbo.getPropertyToDynArray("isRequired"));
                oList.insert(5, rbo.getProperty("fieldSeparator"));
                oList.insert(6, rbo.getPropertyToDynArray("subFieldKey"));
                oList.insert(7, rbo.getPropertyToDynArray("subFieldValue"));
                oList.insert(8, rbo.getPropertyToDynArray("subFieldSeparator"));
                oList.insert(9, rbo.getProperty("fileType"));
                oList.insert(10, rbo.getProperty("fieldCount"));
                oList.insert(11, rbo.getProperty("hasHeader"));
                oList.insert(12, rbo.getProperty("dataStartRow"));
                oList.insert(13, rbo.getProperty("subFieldCount"));
                int vals = Integer.parseInt(oList.extract(10).toString());
                for (int val = 1; val <= vals; val++) {
                    String label = oList.extract(1, val).toString();
                    String key = oList.extract(2, val).toString();
                    String value = oList.extract(3, val).toString();
                    boolean isRequired = (oList.extract(4, val).toString().equals(1));
                    UniDynArray uda = rbo.getPropertyToDynArray("subFieldCount");
                    int subVals = Integer.parseInt(uda.extract(1, val).toString());
                    String parentId;
                    parentId = "";
                    Mapping map = new Mapping();
                    map.setParentField(parentId);
                    map.setFieldLabel(label);
                    map.setFieldKey(key);
                    map.setFieldValue(value);
                    map.setIsRequired(isRequired);
                    map.setPosition(val);
                    map.setFieldType(Mapping.FieldType.SINGLE);
                    afMapping.getFields().add(map);
                    if (subVals > 0) {
                        parentId = key;
                        for (int subVal = 1; subVal <= subVals; subVal++) {
                            String subFieldKey = oList.extract(6, val, subVal).toString();
                            String subFieldValue = oList.extract(7, val, subVal).toString();
                            map = new Mapping();
                            map.setParentField(parentId);
                            map.setFieldKey(subFieldKey);
                            map.setFieldValue(subFieldValue);
                            map.setPosition(subVal);
                            map.setFieldType(Mapping.FieldType.MULTIPLE);
                            afMapping.getSubFields().put(parentId, map);
                        }
                    }
                    afMapping.setAggregatorId(aggregatorId);
                    afMapping.setHasHeader(hasHeader);
                    afMapping.setDataStartRow(dataStartRow);
                    int fieldSep = Integer.parseInt(oList.extract(5).toString());
                    Character c = (char) fieldSep;
                    afMapping.setFieldSeparator(c.toString());
                    afMapping.setFieldCount(vals);
                    getColumnHeaders().add(new AopQueueController.ColumnModel(label, key));
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMapping() {
        if (getMappingId().isEmpty()) {
            return;
        }
        UniDynArray uda = new UniDynArray();
        int fieldCount = 1;
        for (AffiliateMapping map : mapping) {
            uda.insert(1, fieldCount, map.getFieldLabel());
            String key = map.getFieldKey();
            String value = map.getFieldValue();
            if (key == null) {
                key = " ";
                value = " ";
            }
            uda.insert(2, fieldCount, key);
            uda.insert(3, fieldCount, value);
            uda.insert(5, fieldCount, map.getFieldSeparator() == null ? "" : map.getFieldSeparator());
            fieldCount++;
        }
        rbo.setProperty("aggregatorId", getMappingId());
        rbo.setProperty("fileType", fileType);
        rbo.setProperty("fieldLabel", uda.extract(1));
        rbo.setProperty("fieldKey", uda.extract(2));
        rbo.setProperty("fieldValue", uda.extract(3));
        rbo.setProperty("fieldSeparator", uda.extract(5));
        rbo.setProperty("hasHeader", hasHeader ? "1" : "0");
        rbo.setProperty("dataStartRow", dataStartRow);
        rbo.setProperty("subFieldKey", uda.extract(6));
        rbo.setProperty("subFieldValue", uda.extract(7));
        try {
            rbo.callMethod("putAffiliateMapping");
            int errStat = Integer.parseInt(rbo.getProperty("svrStatus"));
            if (errStat == -1) {
                String errCode = rbo.getProperty("svrCtrlCode");
                String errMsg = "[" + errCode + "] " + rbo.getProperty("svrMessage");
                JSFHelper.sendFacesMessage(errMsg, "Error");
            } else {
                JSFHelper.sendFacesMessage("msgs", mappingId + " saved", "Info");
                fileSaved = true;
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateAggregatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addField() {
        int nextColumn = getMapping().size();
        nextColumn++;
        setNewField(true);
        AffiliateMapping m = new AffiliateMapping();
        getMapping().add(m);
        setNewField(true);
        setFieldCommitted(false);
        fileSaved = false;
    }

    public void removeField(AffiliateMapping se) {
        if (mapping.contains(se)) {
            mapping.remove(se);
            newField = false;
            fileSaved = false;
        }
    }

    public void commitField(AffiliateMapping am) {
        if (am.isFieldSaved()) {
            removeField(am);
        } else {
            am.setFieldSaved(true);
//            saveField();
            setNewField(false);
            setFieldCommitted(true);
        }
    }

    public void saveField() {
        setNewField(false);
        setFieldCommitted(true);
        setMapping();
    }

    /**
     * @return the affiliateMasterId
     */
    public String getAffiliateMasterId() {
        if (affiliateMasterId.isEmpty()) {
            return _affiliateMasterId;
        }
        return affiliateMasterId;
    }

    /**
     * @param affiliateMasterId the affiliateMasterId to set
     */
    public void setAffiliateMasterId(String affiliateMasterId) {
        this.affiliateMasterId = affiliateMasterId;
    }

    /**
     * @return the newField
     */
    public boolean isNewField() {
        return newField;
    }

    /**
     * @param newField the newField to set
     */
    public void setNewField(boolean tf) {
        this.newField = tf;
    }

    /**
     * @return the fieldCommitted
     */
    public boolean isFieldCommitted() {
        return fieldCommitted;
    }

    /**
     * @param fieldCommitted the fieldCommitted to set
     */
    public void setFieldCommitted(boolean fieldCommitted) {
        this.fieldCommitted = fieldCommitted;
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
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the fieldSeparator
     */
    public String getFieldSeparator() {
        return fieldSeparator;
    }

    /**
     * @param fieldSeparator the fieldSeparator to set
     */
    public void setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    /**
     * @return the fileTypes
     */
    public ArrayList<String> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes() {
        if (!affiliateMasterId.isEmpty()) {
            mappingId = affiliateMasterId;
            affiliateMasterId = "";
            fileTypes.clear();
            rbo.setProperty("aggregatorId", mappingId);
            try {
                rbo.callMethod("getAffiliateMappingFileTypeList");
                int errStatus = Integer.parseInt(rbo.getProperty("svrStatus"));
                if (errStatus == -1) {
                    String errCode = rbo.getProperty("svrCtrlCode");
                    String errMsg = "[" + errCode + "] " + rbo.getProperty("svrMessage");
                    JSFHelper.sendFacesMessage(errMsg, "Error");
                } else {
                    UniDynArray uda = rbo.getPropertyToDynArray("fileType");
                    int cnt = uda.count(1);
                    for (int c = 1; c <= cnt; c++) {
                        fileTypes.add(uda.extract(1, c).toString());
                    }
                }

            } catch (RbException ex) {
                Logger.getLogger(AffiliateMappingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the hasHeader
     */
    public boolean getHasHeader() {
        return hasHeader;
    }

    /**
     * @param hasHeader the hasHeader to set
     */
    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    /**
     * @return the tree
     */
    public TreeNode getTree() {
        return tree;
    }

    /**
     * @param tree the tree to set
     */
    public void setTree(TreeNode tree) {
        this.tree = tree;
    }

    /**
     * @return the dataStartRow
     */
    public String getDataStartRow() {
        return dataStartRow;
    }

    /**
     * @param dataStartRow the dataStartRow to set
     */
    public void setDataStartRow(String dataStartRow) {
        this.dataStartRow = dataStartRow;
    }

    /**
     * @return the fieldValues
     */
    public HashMap<String, String> getFieldValues() {
        return fieldValues;
    }

    /**
     * @param fieldValues the fieldValues to set
     */
    public void setFieldValues(HashMap<String, String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    /**
     * @return the fileSaved
     */
    public boolean isFileSaved() {
        return fileSaved;
    }

    /**
     * @param fileSaved the fileSaved to set
     */
    public void setFileSaved(boolean fileSaved) {
        this.fileSaved = fileSaved;
    }

    /**
     * @return the columnHeaders
     */
    public ArrayList<AopQueueController.ColumnModel> getColumnHeaders() {
        return columnHeaders;
    }

    @FacesConverter(forClass = AffiliateMapping.class)
    public class MappingConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            if (value == null) {
                return new AffiliateMapping();
            }
            for (AffiliateMapping am : mapping) {
                if (am.getFieldKey().equalsIgnoreCase(value)) {
                    return am;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            if (value == null) {
                return "";
            }
            return ((AffiliateMapping) value).getFieldKey();
        }
    }
}
