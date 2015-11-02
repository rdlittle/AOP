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
public class UVException extends Exception {
    private int svrStatus;
    private String svrCtrlCode;
    private String svrCtrlName;
    private String svrMessage;

    public UVException() {
        svrStatus = 0;
        svrCtrlCode = "";
        svrCtrlName = "";
        svrMessage = "";
    }
    
    public UVException(String errStat, String errCode, String errName, String errMesg) {
        this();
        svrStatus = Integer.parseInt(errStat);
        svrCtrlCode = errCode;
        svrCtrlName = errName;
        svrMessage = errMesg;
    }
    
    /**
     * @return the svrStatus
     */
    public int getSvrStatus() {
        return svrStatus;
    }

    /**
     * @param svrStatus the svrStatus to set
     */
    public void setSvrStatus(String svrStatus) {
        this.svrStatus = Integer.parseInt(svrStatus);
    }

    /**
     * @param svrStatus the svrStatus to set
     */
    public void setSvrStatus(int svrStatus) {
        this.svrStatus = svrStatus;
    }

    /**
     * @return the svrCtrlCode
     */
    public String getSvrCtrlCode() {
        return svrCtrlCode;
    }

    /**
     * @param svrCtrlCode the svrCtrlCode to set
     */
    public void setSvrCtrlCode(String svrCtrlCode) {
        this.svrCtrlCode = svrCtrlCode;
    }

    /**
     * @return the svrCtrlName
     */
    public String getSvrCtrlName() {
        return svrCtrlName;
    }

    /**
     * @param svrCtrlName the svrCtrlName to set
     */
    public void setSvrCtrlName(String svrCtrlName) {
        this.svrCtrlName = svrCtrlName;
    }

    /**
     * @return the svrMessage
     */
    public String getSvrMessage() {
        return svrMessage;
    }

    /**
     * @param svrMessage the svrMessage to set
     */
    public void setSvrMessage(String svrMessage) {
        this.svrMessage = svrMessage;
    }
    
    @Override
    public String toString() {
        return Integer.toString(svrStatus)+" "+svrMessage;
    }
    
}
