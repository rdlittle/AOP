/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author rlittle
 */
public class UnitTestSuite {
    private String utSuiteId;
    private Date utDate;
    private String utTime;
    private String utTitle;
    private String utDesc;
    private ArrayList<UnitTestSuiteSegment> utSegment;

    public UnitTestSuite() {
        utSuiteId = "";
        utDate = Calendar.getInstance(Locale.getDefault()).getTime();
        utTime = "";
        utTitle = "";
        utDesc = "";
        utSegment = new ArrayList<>();
    }
    /**
     * @return the utSuiteId
     */
    public String getUtSuiteId() {
        return utSuiteId;
    }

    /**
     * @param utSuiteId the utSuiteId to set
     */
    public void setUtSuiteId(String utSuiteId) {
        this.utSuiteId = utSuiteId;
    }

    /**
     * @return the utDate
     */
    public Date getUtDate() {
        return utDate;
    }

    public String utDateAsString() {
        SimpleDateFormat dfmt = new SimpleDateFormat("MM/dd/yyyy");
        if(null==utDate) {
            return "";
        }
        return dfmt.format(utDate);
    }
    /**
     * @param utDate the utDate to set
     */
    public void setUtDate(Date utDate) {
        this.utDate = utDate;
    }
    
    public void setUtDate(String date) {
        
    }

    /**
     * @return the utTime
     */
    public String getUtTime() {
        return utTime;
    }
    
    /**
     * @param utTime the utTime to set
     */
    public void setUtTime(String utTime) {
        this.utTime = utTime;
    }

    /**
     * @return the utTitle
     */
    public String getUtTitle() {
        return utTitle;
    }

    /**
     * @param utTitle the utTitle to set
     */
    public void setUtTitle(String utTitle) {
        this.utTitle = utTitle;
    }

    /**
     * @return the utDesc
     */
    public String getUtDesc() {
        return utDesc;
    }

    /**
     * @param utDesc the utDesc to set
     */
    public void setUtDesc(String utDesc) {
        this.utDesc = utDesc;
    }

    /**
     * @return the utSegment
     */
    public ArrayList<UnitTestSuiteSegment> getUtSegment() {
//        System.out.println("UnitTestSuite.getUtSegment()");
        return utSegment;
    }

    /**
     * @param utSegment the utSegment to set
     */
    public void setUtSegment(ArrayList<UnitTestSuiteSegment> utSegment) {
//        System.out.println("UnitTestSuite.setUtSegment()");
        this.utSegment = utSegment;
    }
    
/**
     * @param segment the utSegment to set
     */
    public void addUtSegment(UnitTestSuiteSegment segment) {
//        System.out.println("UnitTestSuite.addUtSegment()");
        this.utSegment.add(segment);
    }    
    
}
