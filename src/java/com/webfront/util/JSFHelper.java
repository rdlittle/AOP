/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.util;

import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author rlittle
 */
public class JSFHelper {

    private static HashMap<String, Severity> messageMap;
    
    public static enum MessageType {
        INFO,WARN,ERROR,FATAL;
    }
    
    private static HashMap<MessageType,String> errorMesgs;

    public JSFHelper() {
        errorMesgs = new HashMap<>();
        errorMesgs.put(MessageType.INFO, "Info");
        errorMesgs.put(MessageType.WARN, "Warning");
        errorMesgs.put(MessageType.ERROR, "Error");
        errorMesgs.put(MessageType.FATAL, "Fatal");
    }

    public static void sendFacesMessage(String errMsg) {
        sendFacesMessage(errMsg, "");
    }

    public static void sendFacesMessage(MessageType errorType, String msg, String target) {
        FacesMessage facesMsg = new FacesMessage();
        facesMsg.setDetail(msg);
        facesMsg.setSummary(errorMesgs.get(errorType));
        facesMsg.setSeverity(messageMap.get(errorMesgs.get(errorType)));
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(target, facesMsg);
    }
    
    public static void sendFacesMessage(String errMsg, String errorType) {
        if (errorType.isEmpty()) {
            errorType = "Info";
        }
        if(messageMap == null) {
            setMessageMap();
        }
        if (!messageMap.containsKey(errorType)) {
            errorType = "Error";
        }
        FacesMessage facesMsg = new FacesMessage();
        facesMsg.setDetail(errMsg);
        facesMsg.setSummary(errorType);
        facesMsg.setSeverity(messageMap.get(errorType));
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("msgs", facesMsg);
    }
    
    public static void sendFacesMessage(String target, String errMsg, String errorType) {
        if(target.isEmpty()) {
            target = "msgs";
        }
        if (errorType.isEmpty()) {
            errorType = "Info";
        }
        if(messageMap == null) {
            setMessageMap();
        }
        if (!messageMap.containsKey(errorType)) {
            errorType = "Error";
        }
        FacesMessage facesMsg = new FacesMessage();
        facesMsg.setDetail(errMsg);
        facesMsg.setSummary(errorType);
        facesMsg.setSeverity(messageMap.get(errorType));
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(target, facesMsg);
    }    

    private static void setMessageMap() {
        if (messageMap == null) {
            messageMap = new HashMap<>();
            messageMap.put("Error", FacesMessage.SEVERITY_ERROR);
            messageMap.put("Warning", FacesMessage.SEVERITY_WARN);
            messageMap.put("Info", FacesMessage.SEVERITY_INFO);
            messageMap.put("Fatal", FacesMessage.SEVERITY_FATAL);
        }
    }

}
