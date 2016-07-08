/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AopUserBean implements Serializable {

    private String userName;
    private String password;
    private boolean loggedIn;
    private ArrayList<String> roles;

    public AopUserBean() {
        userName = "";
        password = "";
        loggedIn = false;
        roles = new ArrayList<>();
    }

    public String doLogin() {
        RedObject rbo = new RedObject("WDE", "AOP:Utils");
        rbo.setProperty("userId", userName);
        try {
            rbo.callMethod("getLogin");
            int errStat = Integer.parseInt(rbo.getProperty("svrStatus"));
            if (errStat == -1) {
                String errCode = rbo.getProperty("svrCtrlCode");
                String errMsg = rbo.getProperty("svrMessage");
                errMsg = "Error: " + errCode + " " + errMsg;
                FacesMessage fmsg = new FacesMessage(errMsg);
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
            } else {
                loggedIn = rbo.getProperty("isAuthorized").equals("1");
                UniDynArray uda = rbo.getPropertyToDynArray("roles");
                int cnt = uda.dcount(1);
                for (int c = 1; c <= cnt; c++) {
                    roles.add(uda.extract(1, c).toString());
                } 
                if (!loggedIn) {
                    return "loginError";
                } else {
                    return "index";
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopUserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public boolean hasAuthorizedRole(String role) {
        return roles.contains(role.toUpperCase());
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * @return the roles
     */
    public ArrayList<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
