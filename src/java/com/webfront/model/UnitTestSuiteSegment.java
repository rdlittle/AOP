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

    private Integer segmentNum;
    private String utAcctName;
    private String utFileName;
    private String utProgName;

    public UnitTestSuiteSegment() {
        segmentNum = null;
        utAcctName = "";
        utFileName = "";
        utProgName = "";
    }

    public UnitTestSuiteSegment(String aName, String fName, String pName) {
        segmentNum = null;
        utAcctName = aName;
        utFileName = fName;
        utProgName = pName;
    }

    public UnitTestSuiteSegment(Integer id, String aName, String fName, String pName) {
        segmentNum = id;
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
//
//    /**
//     * @return the id
//     */
//    public Integer getSegmentNum() {
//        return segmentNum;
//    }

    public String getSegmentNum() {
        if (segmentNum == null) {
            return "";
        }
        return Integer.toString(segmentNum);
    }

    /**
     * @param id the id to set
     */
    public void setSegmentNum(Integer id) {
        if (id != null) {
            this.segmentNum = id;
        }
    }

    public void setSegmentNum(String n) {
        if (!n.isEmpty()) {
            this.segmentNum = Integer.parseInt(n);
        }
    }

}
