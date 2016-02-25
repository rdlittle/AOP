/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import javax.faces.application.FacesMessage;

/**
 *
 * @author rlittle
 */
public class ErrorObject {

    private int svrStatus;
    private String svrCtrlCode;
    private String svrMessage;

    public ErrorObject() {
        svrStatus = 0;
        svrCtrlCode = "";
        svrMessage = "";
    }

    public ErrorObject(String errStat, String errCode, String errMesg) {
        this();
        svrStatus = Integer.parseInt(errStat);
        svrCtrlCode = errCode;
        svrMessage = errMesg;
    }

    /**
     *
     * @return FacesMessage
     */
    public FacesMessage toFacesMessage() {
        FacesMessage fmsg = new FacesMessage(toSimpleString());
        fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
        return fmsg;
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
     * @return the svrMessage
     */
    public String getSvrMessage() {
        return svrMessage;
    }

    /**
     * @param svrMessage Verbose svrMessage to set
     */
    public void setSvrMessage(String svrMessage) {
        this.svrMessage = svrMessage;
    }

    @Override
    public String toString() {
        String msg = "svrStatus=" + Integer.toString(svrStatus) + " ";
        msg += "svrCtrlCode=" + svrCtrlCode + " ";
        msg += "svrMessage=" + svrMessage;
        return msg;
    }

    public String toSimpleString() {
        String msg = "svrCtrlCode=" + svrCtrlCode + " ";
        msg += "svrMessage=" + svrMessage;
        return msg;
    }

}
