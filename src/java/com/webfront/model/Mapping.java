/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

/**
 *
 * @author rlittle
 */
public class Mapping {
    private String parentField;
    private String fieldLabel;
    private String fieldKey;
    private String fieldValue;
    private String fieldType;
    
    /**
     * @return the fieldLabel
     */
    public String getFieldLabel() {
        return fieldLabel;
    }

    /**
     * @param fieldLabel the fieldLabel to set
     */
    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    /**
     * @return the fieldKey
     */
    public String getFieldKey() {
        return fieldKey;
    }

    /**
     * @param fieldKey the fieldKey to set
     */
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    /**
     * @return the fieldValue
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * @param fieldValue the fieldValue to set
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    /**
     * @return the fieldType
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType the fieldType to set
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * @return the parentField
     */
    public String getParentField() {
        return parentField;
    }

    /**
     * @param parentField the parentField to set
     */
    public void setParentField(String parentField) {
        this.parentField = parentField;
    }
    
}
