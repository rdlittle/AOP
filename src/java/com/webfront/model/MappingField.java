/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.util.Objects;

/**
 *
 * @author rlittle
 */
public class MappingField {

    public static enum FieldType {
        SINGLE, MULTIPLE;
    }
    public static enum FieldWeight {
        PRIMARY,SECONDARY
    }
    /*
    @param fieldLabel The column header or description of this field
    @param fieldKey The standard field name for this field
    @param fieldValue The order attribute for this field
    @param fieldType Either FieldType.SINGLE or FieldType.DOUBLE
    @param fieldWeight Either FieldWeight.PRIMARY or FieldWeight.SECONDARY
    */
    private String fieldLabel;
    private String fieldKey;
    private String fieldValue;
    private FieldType fieldType;
    private FieldWeight fieldWeight;
    private String fieldSource;
    private String fieldStrip;
    private String fieldSeparator;
    private String fieldExtract;
    
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
    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType the fieldType to set
     */
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
    
    /**
     * 
     * @param type 'S'ingle or 'M'ultipart
     */
    public void setFieldType(String type) {
        this.fieldType = type.equalsIgnoreCase("M") ? FieldType.MULTIPLE : FieldType.SINGLE;
    }

    /**
     * @return the fieldSource
     */
    public String getFieldSource() {
        return fieldSource;
    }

    /**
     * @param fieldSource the fieldSource to set
     */
    public void setFieldSource(String fieldSource) {
        this.fieldSource = fieldSource;
    }

    /**
     * @return the fieldStrip
     */
    public String getFieldStrip() {
        return fieldStrip;
    }

    /**
     * @param fieldStrip the fieldStrip to set
     */
    public void setFieldStrip(String fieldStrip) {
        this.fieldStrip = fieldStrip;
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
     * @return the fieldExtracion
     */
    public String getFieldExtract() {
        return fieldExtract;
    }

    /**
     * @param fieldExtracion the fieldExtracion to set
     */
    public void setFieldExtract(String fieldExtract) {
        this.fieldExtract = fieldExtract;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MappingField other = (MappingField) obj;
        if (!Objects.equals(this.fieldKey, other.fieldKey)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.fieldKey);
        hash = 53 * hash + Objects.hashCode(this.fieldValue);
        return hash;
    }    

    /**
     * @return the fieldWeight
     */
    public FieldWeight getFieldWeight() {
        return fieldWeight;
    }

    /**
     * @param fieldWeight the fieldWeight to set
     */
    public void setFieldWeight(FieldWeight fieldWeight) {
        this.fieldWeight = fieldWeight;
    }
    
    /**
     * @param weight 'P'rimary or 'S'econdary
     * 
     */
    public void setFieldWeight(String weight) {
        this.fieldWeight = weight.equalsIgnoreCase("S") ? FieldWeight.SECONDARY : FieldWeight.PRIMARY;
    }

}
