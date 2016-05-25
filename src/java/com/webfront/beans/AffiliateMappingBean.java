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
import com.webfront.util.JSFHelper;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

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
    private String mappingId;
    private boolean newField;
    private boolean fieldCommitted;
    private String fileType;
    private String fieldSeparator;
    private ArrayList<String> fileTypes;
    private boolean hasHeader;

    public AffiliateMappingBean() {
        mapping = new ArrayList<>();
        affiliateMasterId = "";
        mappingId = "";
        newField = false;
        fieldCommitted = false;
        fileType = "";
        fieldSeparator = "";
        fileTypes = new ArrayList<>();
    }

    public ArrayList<AffiliateMapping> getMapping() {
        if (!affiliateMasterId.isEmpty() && !newField) {
            setMappingId(affiliateMasterId);
            mapping.clear();
//            RedObject rbo = new RedObject("WDE", "Affiliates:Mapping");
            rbo.setProperty("aggregatorId", affiliateMasterId);
            try {
                rbo.callMethod("getAffiliateMapping");
                int errStat = Integer.parseInt(rbo.getProperty("svrStatus"));
                if (errStat == -1) {
                    String errCode = rbo.getProperty("svrCtrlCode");
                    String errMsg = "[" + errCode + "] " + rbo.getProperty("svrMessage");
                    JSFHelper.sendFacesMessage(errMsg, "Error");
                } else {
                    UniDynArray uda = new UniDynArray();
                    uda.insert(1, rbo.getPropertyToDynArray("fieldName"));
                    uda.insert(2, rbo.getPropertyToDynArray("fieldNumber"));
                    uda.insert(3, rbo.getPropertyToDynArray("fieldContents"));
                    uda.insert(4, rbo.getPropertyToDynArray("outputField"));
                    uda.insert(5, rbo.getPropertyToDynArray("dataType"));
                    uda.insert(6, rbo.getPropertyToDynArray("isRequired"));
                    fileType = rbo.getProperty("fileType");
                    hasHeader = rbo.getProperty("hasHeader").equals("1");
                    fieldSeparator = rbo.getProperty("fieldSeparator");
                    int fieldCount = Integer.parseInt(rbo.getProperty("fieldCount"));
                    for (int f = 1; f <= fieldCount; f++) {
                        AffiliateMapping map = new AffiliateMapping();
                        map.setFieldName(uda.extract(1, f).toString());
                        map.setFieldNumber(uda.extract(2, f).toString());
                        map.setFieldContents(uda.extract(3, f).toString());
                        map.setOutputField(uda.extract(4, f).toString());
                        map.setDataType(uda.extract(5, f).toString());
                        map.setRequired(uda.extract(6, f).toString().equals("1"));
                        map.setFieldSaved(true);
                        mapping.add(map);
                    }
                    affiliateMasterId = "";
                }
            } catch (RbException ex) {
                Logger.getLogger(AffiliateMasterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mapping;
    }

    public void setMapping() {
        if (getMappingId().isEmpty()) {
            return;
        }

        UniDynArray uda = new UniDynArray();
        int fieldCount = 1;
        for (AffiliateMapping map : mapping) {
            uda.insert(1, fieldCount, map.getFieldName());
            uda.insert(2, fieldCount, map.getFieldNumber());
            uda.insert(3, fieldCount, map.getFieldContents());
            uda.insert(4, fieldCount, map.getOutputField());
            uda.insert(5, fieldCount, map.getDataType());
            uda.insert(6, fieldCount, map.getRequired() ? "1" : "0");

            fieldCount++;
        }
//        RedObject rbo = new RedObject("WDE", "Affiliates:Mapping");
        rbo.setProperty("aggregatorId", getMappingId());
        rbo.setProperty("fieldName", uda.extract(1));
        rbo.setProperty("fieldNumber", uda.extract(2));
        rbo.setProperty("fieldContents", uda.extract(3));
        rbo.setProperty("outputField", uda.extract(4));
        rbo.setProperty("dataType", uda.extract(5));
        rbo.setProperty("isRequired", uda.extract(6));
        rbo.setProperty("fileType", fileType);
        rbo.setProperty("hasHeader", hasHeader ? "1" : "0");
        rbo.setProperty("fieldSeparator", fieldSeparator);
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
        m.setFieldNumber(Integer.toString(nextColumn));
        setNewField(true);
        setFieldCommitted(false);
        getMapping().add(m);
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
        if (!mappingId.isEmpty()) {
            fileTypes.clear();
            rbo.setProperty("aggregatorId", mappingId);
            try {
                rbo.callMethod("getAffiliateMappingFileTypes");
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
        return fileTypes;
    }

    /**
     * @param fileTypes the fileTypes to set
     */
    public void setFileTypes(ArrayList<String> fileTypes) {
        this.fileTypes = fileTypes;
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

    @FacesConverter(forClass = AffiliateMapping.class)
    public class MappingConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            if (value == null) {
                return new AffiliateMapping();
            }
            for (AffiliateMapping am : mapping) {
                if (am.getFieldName().equalsIgnoreCase(value)) {
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
            return ((AffiliateMapping) value).getFieldName();
        }
    }
}
