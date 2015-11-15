/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RedObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author rlittle
 * This class is an attempt on creating a generic Web DE module but there is still much work to do, and this class is not ready for use.
 * The goal is to have one class that could handle all UniVerse I/O. A method from any other bean should be able to set a list of input and 
 * output properties, invoke a named method and retrieve all the output properties and error properties.
 * 
 */
@ManagedBean(name = "webde")
@SessionScoped
public class WebDE implements Serializable {

    private RedObject rbo;
    private String accountName;
    private String moduleName;
    private String methodName;
    private String className;

    private HashMap<String, UniDynArray> inProperties;

    private HashMap<String, UniDynArray> objMap;

    String inProperty;
    String inValue;

    private int errStatus;
    private String errMessage;
    private String errCode;
    
    public WebDE() {
        inProperties = new HashMap<>();
        accountName = null;
        methodName = null;
        moduleName = null;
        objMap = new HashMap<>();
    }

    private void setRbo() {
        this.rbo = new RedObject(this.accountName, this.moduleName + ":" + this.className);
    }

    public void setInProp(String s) {
        inProperty = s;
    }

    public void setInValue(String s) {
        inValue = s;
    }

    public String getInProp() {
        return inProperty;
    }

    public String getInValue() {
        return inValue;
    }

    public void call() throws Exception {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext eCtx = ctx.getExternalContext();
        Map<String, String> map = eCtx.getRequestParameterMap();
        if (map.containsKey("accountName")) {
            setAccountName((String) map.get("accountName"));
        }
        if (map.containsKey("moduleName")) {
            setModuleName((String) map.get("moduleName"));
        }
        if (map.containsKey("className")) {
            setClassName((String) map.get("className"));
        }
        if (map.containsKey("methodName")) {
            setMethodName((String) map.get("methodName"));
        }
        if (this.accountName == null) {
            return;
        }
        if (this.moduleName == null) {
            return;
        }
        if (this.className == null) {
            return;
        }
        setRbo();
        for (String key : this.inProperties.keySet()) {
            UniDynArray value = this.inProperties.get(key);
            getRbo().setProperty(key, value);
        }
        getRbo().callMethod(getMethodName());

        UniDynArray pNames = getRbo().getPnames();
        UniDynArray pValues = getRbo().getPvalues();
        int attrs = pNames.dcount();
        for (int attr = 1; attr<=attrs; attr++) {
            String pName = pNames.extract(attr).toString();
            UniDynArray uniDynarray = pValues.extract(attr);
            getObjectMap().put(pName, uniDynarray);
            if(pName.equals("errStat")) {
                setErrStatus(Integer.parseInt(uniDynarray.extract(1,1).toString()));
            }
            if(pName.equals("errCode")) {
                setErrCode(uniDynarray.extract(1,1).toString());
            }
            if(pName.equals("errMsg")) {
                setErrMessage(uniDynarray.extract(1,1).toString());
            }
        }
        
    }

    public void setObjectMap(HashMap<String, UniDynArray> inList) {
        this.objMap = inList;
    }

    public HashMap<String, UniDynArray> getObjectMap() {
        return this.objMap;
    }

    public void setAccountName(String arg) {
        this.accountName = arg;
    }

    public void setModuleName(String arg) {
        this.moduleName = arg;
    }

    public void setClassName(String arg) {
        this.className = arg;
    }

    public void setMethodName(String arg) {
        this.methodName = arg;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public String getClassName() {
        return this.className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public RedObject getRbo() {
        return this.rbo;
    }

    /**
     * @return the inProperties
     */
    public HashMap<String, UniDynArray> getInProperties() {
        return this.inProperties;
    }

    /**
     * @param map the inProperties to set
     */
    public void setInProperties(HashMap<String, UniDynArray> map) {
        this.inProperties = map;
    }

    /**
     * @return the errStatus
     */
    public int getErrStatus() {
        return errStatus;
    }

    /**
     * @param errStatus the errStatus to set
     */
    public void setErrStatus(int errStatus) {
        this.errStatus = errStatus;
    }

    /**
     * @return the errMessage
     */
    public String getErrMessage() {
        return errMessage;
    }

    /**
     * @param errMessage the errMessage to set
     */
    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @param errCode the errCode to set
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

}
