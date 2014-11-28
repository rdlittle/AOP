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
    private String columnName;
    private String outputField;
    private String exclude;

    public AffiliateMapping() {
        this.id=new String();
        this.columnName=new String();
        this.outputField=new String();
        this.exclude=new String();
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
     * @return the columnNames
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnNames to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
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
    public String getExclude() {
        return exclude;
    }

    /**
     * @param exclude the exclude to set
     */
    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    
}
