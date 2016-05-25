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
public class AffiliateMapping {
    private String id;
    private String fieldNumber;
    private String fieldName;
    private String fieldContents;
    private String dataType;
    private String outputField;
    private boolean required;
    private boolean fieldSaved;

    public AffiliateMapping() {
        this.id = new String();
        this.fieldNumber = new String();
        this.fieldName=new String();
        this.fieldContents=new String();
        this.outputField=new String();
        this.dataType = new String();
        this.required=true;
        this.fieldSaved = false;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the fieldNames
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldNames to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the outputField
     */
    public String getOutputField() {
        return outputField;
    }

    /**
     * @param outputField the outputField to set
     */
    public void setOutputField(String outputField) {
        this.outputField = outputField;
    }

    /**
     * @return the exclude
     */
    public boolean getRequired() {
        return required;
    }

    /**
     * @param required the exclude to set
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return the fieldContents
     */
    public String getFieldContents() {
        return fieldContents;
    }

    /**
     * @param fieldContents the fieldContents to set
     */
    public void setFieldContents(String fieldContents) {
        this.fieldContents = fieldContents;
    }

    /**
     * @return the coumnNumber
     */
    public String getFieldNumber() {
        return fieldNumber;
    }

    /**
     * @param coumnNumber the coumnNumber to set
     */
    public void setFieldNumber(String coumnNumber) {
        this.fieldNumber = coumnNumber;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the fieldSaved
     */
    public boolean isFieldSaved() {
        return fieldSaved;
    }

    /**
     * @param fieldSaved the fieldSaved to set
     */
    public void setFieldSaved(boolean fieldSaved) {
        this.fieldSaved = fieldSaved;
    }

}
