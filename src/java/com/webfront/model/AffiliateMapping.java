/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author rlittle
 */
public class AffiliateMapping extends Mapping {

    private String id;
    private String aggregatorId;
    private String fileType;
    private String fieldSeparator;
    private boolean hasHeader;
    private String dataStartRow;
    private boolean fieldSaved;
    private int fieldCount;
    private ArrayList<Mapping> fields;
    private HashMap<String,Mapping> subFields;
    

    public AffiliateMapping() {
        id = "";
        dataStartRow = "0";
        fieldSaved = false;
        fields = new ArrayList<>();
        subFields = new HashMap<>();
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
     * @return the fieldSeparator
     */
    public String getFieldSeparator() {
        return fieldSeparator;
    }

    /**
     * @param fs the fieldSeparator to set
     */
    public void setFieldSeparator(String fs) {
        fieldSeparator = fs;
    }

    /**
     * @return the hasHeader
     */
    public boolean isHasHeader() {
        return hasHeader;
    }

    /**
     * @param hasHeader the hasHeader to set
     */
    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    /**
     * @return the saved
     */
    public boolean isFieldSaved() {
        return fieldSaved;
    }

    /**
     * @param saved the saved to set
     */
    public void setFieldSaved(boolean saved) {
        this.fieldSaved = saved;
    }

    /**
     * @return the subFields
     */
    public ArrayList<Mapping> getFields() {
        return fields;
    }

    /**
     * @param fields the subFields to set
     */
    public void setFields(ArrayList<Mapping> fields) {
        this.fields = fields;
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
     * @return the fieldCount
     */
    public int getFieldCount() {
        return fieldCount;
    }

    /**
     * @param fieldCount the fieldCount to set
     */
    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    /**
     * @return the subFields
     */
    public HashMap<String,Mapping> getSubFields() {
        return subFields;
    }

}
