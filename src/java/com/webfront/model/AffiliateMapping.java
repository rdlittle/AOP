/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.util.ArrayList;

/**
 *
 * @author rlittle
 */
public class AffiliateMapping extends Mapping {

    private String id;
    private String fieldSeparator;
    private boolean hasHeader;
    private String dataStartRow;
    private boolean multiPart;
    private boolean fieldSaved;
    private ArrayList<Mapping> subFields;
    private String fileType;

    public AffiliateMapping() {
        id = "";
        subFields = new ArrayList<>();
        dataStartRow = "0";
        fieldSaved = false;
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
    public ArrayList<Mapping> getSubFields() {
        return subFields;
    }

    /**
     * @param subFields the subFields to set
     */
    public void setSubFields(ArrayList<Mapping> subFields) {
        this.subFields = subFields;
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
     * @return the multiPart
     */
    public boolean isMultiPart() {
        return multiPart;
    }

    /**
     * @param multiPart the multiPart to set
     */
    public void setMultiPart(boolean multiPart) {
        this.multiPart = multiPart;
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

}
