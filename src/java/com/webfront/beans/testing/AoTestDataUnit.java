/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans.testing;

import com.webfront.model.SelectItem;
import java.util.ArrayList;

/**
 *
 * @author rlittle
 */
public class AoTestDataUnit {
    private String id;
    private String description;
    private String transType;
    private String lines;
    private String orders;
    private String requires;
    private String creditType;
    private String errorCodes;
    private String errorLines;
    private String errorSameLine;
    private ArrayList<SelectItem> errorList;
    private boolean hasErrors;

    public AoTestDataUnit() {
        id="";
        description="";
        transType="D";
        lines="1";
        orders="1";
        requires="";
        creditType="";
        errorCodes = "";
        errorLines = "0";
        errorSameLine = "1";
        errorList = new ArrayList<>();
        hasErrors = false;
    }
    
    public boolean getHasErrors() {
        return errorList.isEmpty();
    }
    
    public void setHasErrors() {
        hasErrors=errorList.isEmpty();
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the transType
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param transType the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * @return the lines
     */
    public String getLines() {
        return lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(String lines) {
        this.lines = lines;
    }

    /**
     * @return the orders
     */
    public String getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(String orders) {
        this.orders = orders;
    }

    /**
     * @return the requires
     */
    public String getRequires() {
        return requires;
    }

    /**
     * @param requires the requires to set
     */
    public void setRequires(String requires) {
        this.requires = requires;
    }

    /**
     * @return the creditType
     */
    public String getCreditType() {
        return creditType;
    }

    /**
     * @param creditType the creditType to set
     */
    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCodes() {
        return errorCodes;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCodes(String errorCode) {
        this.errorCodes = errorCode;
    }

    /**
     * @return the errorLines
     */
    public String getErrorLines() {
        return errorLines;
    }

    /**
     * @param errorLines the errorLines to set
     */
    public void setErrorLines(String errorLines) {
        this.errorLines = errorLines;
    }

    /**
     * @return the errorSameLine
     */
    public String getErrorSameLine() {
        return errorSameLine;
    }

    /**
     * @param errorSameLine the errorSameLine to set
     */
    public void setErrorSameLine(String errorSameLine) {
        this.errorSameLine = errorSameLine;
    }

    /**
     * @return the errorList
     */
    public ArrayList<SelectItem> getErrorList() {
        return errorList;
    }

    /**
     * @param errorList the errorList to set
     */
    public void setErrorList(ArrayList<SelectItem> errorList) {
        this.errorList = errorList;
    }
}
