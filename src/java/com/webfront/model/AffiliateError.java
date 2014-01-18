/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import java.util.LinkedHashMap;

/**
 *
 * @author rlittle
 */
public class AffiliateError {
    
    private String id;
    private LinkedHashMap<String,String> errorMap;
    
    public AffiliateError() {
        setErrorMap(new LinkedHashMap<String,String>());
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
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
    
}
