/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import asjava.uniclientlibs.UniDynArray;
import java.util.LinkedHashMap;

/**
 *
 * @author rlittle
 */
public class AffiliateError extends AffiliateOrder {
    
    private String id;
    private LinkedHashMap<String,String> errorMap;
    private String errorMessages;
    private String lineNumber;
    
    public AffiliateError() {
        this.errorMap = new LinkedHashMap<>();
    }
    
    public AffiliateError(UniDynArray uda) {
        super();
        this.errorMap = new LinkedHashMap<>();
        String comm=uda.extract(1, 173).toString();
        this.setPayingId(uda.extract(1,8).toString());
        this.setCommissionTotal(Float.valueOf(uda.extract(1,173).toString()));
        this.setIbv(Float.valueOf(uda.extract(1, 149).toString())/100);
    }

    /**
     * @return the errorMap
     */
    public LinkedHashMap<String,String> getErrorMap() {
        return errorMap;
    }

    /**
     * @param errorMap the errorMap to set
     */
    public void setErrorMap(LinkedHashMap<String,String> errorMap) {
        this.errorMap = errorMap;
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the errorMessages
     */
    public String getErrorMessages() {
        this.errorMessages=new String();
        if(getErrorMap().size() > 0) {
            errorMessages="<ul>";
            for(String s : getErrorMap().values()) {
                errorMessages+="<li>"+s+"</li>";
            }
            errorMessages+="</ul>";
        }
        return errorMessages;
    }

    /**
     * @param errorMessages the errorMessages to set
     */
    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * @return the lineNumber
     */
    public String getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber the lineNumber to set
     */
    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    
}
