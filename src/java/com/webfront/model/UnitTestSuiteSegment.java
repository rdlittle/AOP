/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.io.Serializable;

/**
 *
 * @author rlittle
 */
public class UnitTestSuiteSegment implements Serializable {
    private String utAcctName;
    private String utFileName;
    private String utProgName;

    public UnitTestSuiteSegment() {
        utAcctName = "";
        utFileName = "";
        utProgName = "";
    }
    public UnitTestSuiteSegment(String aName, String fName, String pName) {
        utAcctName = aName;
        utFileName = fName;
        utProgName = pName;
    }
    /**
     * @return the utAcctName
     */
    public String getUtAcctName() {
        return utAcctName;
    }

    /**
     * @param utAcctName the utAcctName to set
     */
    public void setUtAcctName(String utAcctName) {
        this.utAcctName = utAcctName;
    }

    /**
     * @return the utFileName
     */
    public String getUtFileName() {
        return utFileName;
    }

    /**
     * @param utFileName the utFileName to set
     */
    public void setUtFileName(String utFileName) {
        this.utFileName = utFileName;
    }

    /**
     * @return the utProgName
     */
    public String getUtProgName() {
        return utProgName;
    }

    /**
     * @param utProgName the utProgName to set
     */
    public void setUtProgName(String utProgName) {
        this.utProgName = utProgName;
    }
    
}
