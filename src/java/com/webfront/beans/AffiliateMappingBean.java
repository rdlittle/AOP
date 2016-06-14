/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.controller.AffiliateMasterController;
import com.webfront.model.AffiliateMapping;
import com.webfront.model.Mapping;
import com.webfront.util.JSFHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
@ViewScoped
public class AffiliateMappingBean {

    private final RedObject rbo = new RedObject("WDE", "Affiliates:Mapping");
    private final ArrayList<AffiliateMapping> mapping;
    private String affiliateMasterId;
    protected String _affiliateMasterId;
    private String mappingId;
    private boolean newField;
    private boolean fieldCommitted;
    private String fileType;
    private String fieldSeparator;
    private final ArrayList<String> fileTypes;
    private boolean hasHeader;
    private TreeNode tree;
    private String dataStartRow;
    private HashMap<String, String> fieldValues;

    public AffiliateMappingBean() {
        mapping = new ArrayList<>();
        affiliateMasterId = "";
        _affiliateMasterId = "";
        mappingId = "";
        newField = false;
        fieldCommitted = false;
        fileType = "";
        fieldSeparator = "";
        fileTypes = new ArrayList<>();
        tree = new DefaultTreeNode();
        dataStartRow = "0";
        fieldValues = new HashMap<>();
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
                        map.setMultiPart(uda.extract(4, f).toString().equals("1"));
                        map.setFieldSeparator(uda.extract(5, f).toString());
                        int subCount = uda.dcount(6,f);
                        for(int s = 1; s<=subCount ; s++) {
                            Mapping sm = new Mapping();
                            sm.setFieldKey(uda.extract(6, f, s).toString());
                            sm.setFieldValue(uda.extract(7, f, s).toString());
                            sm.setFieldLabel(uda.extract(8, f, s).toString());
                            map.getSubFields().add(sm);
                        }
                        map.setFieldSaved(true);
                        mapping.add(map);
                    }
                }
            } catch (RbException ex) {
                Logger.getLogger(AffiliateMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void onCellEdit(CellEditEvent event) {
        String oldValue = (String) event.getOldValue();
        String newValue = (String) event.getNewValue();
        if (!newValue.equals(oldValue)) {
            AffiliateMapping item = (AffiliateMapping) mapping.get(event.getRowIndex());
            item.setFieldKey(newValue);
            item.setFieldValue(fieldValues.get(newValue));
            
        }
    }

    public ArrayList<AffiliateMapping> getMapping() {
        return mapping;
    }

    public void setMapping() {
        if (getMappingId().isEmpty()) {
            return;
        }

        UniDynArray uda = new UniDynArray();
        int fieldCount = 1;
        for (AffiliateMapping map : mapping) {
            uda.insert(1, fieldCount, map.getFieldLabel());
            uda.insert(2, fieldCount, map.getFieldKey());
            uda.insert(3, fieldCount, map.getFieldValue());
            uda.insert(4, fieldCount, map.isMultiPart() ? "1" : "");
            uda.insert(5, fieldCount, map.getFieldSeparator() == null ? "" : map.getFieldSeparator());
            if (map.isMultiPart()) {
                int subFieldNum = 1;
                for(Mapping m : map.getSubFields()) {
                    uda.insert(6, fieldCount, subFieldNum, m.getFieldKey());
                    uda.insert(7, fieldCount, subFieldNum++, m.getFieldValue());
                }
            }
            fieldCount++;
        }
        rbo.setProperty("aggregatorId", getMappingId());
        rbo.setProperty("fileType", fileType);
        rbo.setProperty("fieldLabel", uda.extract(1));
        rbo.setProperty("fieldKey", uda.extract(2));
        rbo.setProperty("fieldValue", uda.extract(3));
        rbo.setProperty("multiPart", uda.extract(4));
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
            }
        } catch (RbException ex) {
            Logger.getLogger(AffiliateMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addField() {
        int nextColumn = getMapping().size();
        nextColumn++;
        setNewField(true);
        AffiliateMapping m = new AffiliateMapping();
        setNewField(true);
        setFieldCommitted(false);
        getMapping().add(m);
    }

    public void addSubField() {

    }

    public void removeField(AffiliateMapping se) {
        if (mapping.contains(se)) {
            mapping.remove(se);
            newField = false;
        }
    }

    public void commitField(AffiliateMapping am) {
        if (am.isFieldSaved()) {
            removeField(am);
        } else {
            saveField();
            am.setFieldSaved(true);
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
