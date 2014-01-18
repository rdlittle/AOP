/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author rlittle
 */
@ManagedBean
@RequestScoped
public class IBVMaster {
    private String ID;
    private String name;
    private String category;
    private String type;
    private String prefix;
    private String country;
    private String currency;
    private String mappingId;
    private String dataFeedAccessType;
    private String dataFeedFormat;
    private String dataFeedURL;
    private String userName;
    private String password;
    private String createDate;
    private boolean active;
    private String nextDetailId;

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
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
     * @return the dataFeedAccessType
     */
    public String getDataFeedAccessType() {
        return dataFeedAccessType;
    }

    /**
     * @param dataFeedAccessType the dataFeedAccessType to set
     */
    public void setDataFeedAccessType(String dataFeedAccessType) {
        this.dataFeedAccessType = dataFeedAccessType;
    }

    /**
     * @return the dataFeedFormat
     */
    public String getDataFeedFormat() {
        return dataFeedFormat;
    }

    /**
     * @param dataFeedFormat the dataFeedFormat to set
     */
    public void setDataFeedFormat(String dataFeedFormat) {
        this.dataFeedFormat = dataFeedFormat;
    }

    /**
     * @return the dataFeedURL
     */
    public String getDataFeedURL() {
        return dataFeedURL;
    }

    /**
     * @param dataFeedURL the dataFeedURL to set
     */
    public void setDataFeedURL(String dataFeedURL) {
        this.dataFeedURL = dataFeedURL;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the nextDetailId
     */
    public String getNextDetailId() {
        return nextDetailId;
    }

    /**
     * @param nextDetailId the nextDetailId to set
     */
    public void setNextDetailId(String nextDetailId) {
        this.nextDetailId = nextDetailId;
    }
    
}
