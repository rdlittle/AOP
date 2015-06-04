/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class UploadBean {

    private UploadedFile file;
    private String uploadDir;
    private String vendorCode;
    private String networkName;
    private String networkId;
    private String checkAmount;
    private String checkId;
    private String checkDate;
    private String keyStatus;

    /**
     * Creates a new instance of UploadBean
     */
    public UploadBean() {
//        uploadDir = "/home/uvuser/EXPORT/";
        
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String upload() {
        uploadDir = "/usr/local/dmcdev/AFFILIATE.PAYMENT/";
        try {
            if ("-1".equals(vendorCode)) {
                FacesMessage msg = new FacesMessage("Vendor code is missing");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            } else {
                RedObject rb = new RedObject("WDE", "AOP:Queue");
                rb.setProperty("affiliateMasterId", this.vendorCode);
                rb.setProperty("fileName", file.getFileName());
                rb.setProperty("networkId", this.networkId);
                rb.setProperty("checkAmount", this.checkAmount);
                rb.setProperty("checkId",this.checkId);
                try {
                    rb.callMethod("setQueue");
                    String errStat = rb.getProperty("errStat");
                    String errCode = rb.getProperty("errCode");
                    String errMsg = rb.getProperty("errMsg");
                    if (errStat.equals("-1")) {
                        errMsg = "Error: " + errCode + " " + errMsg;
                        FacesMessage fmsg = new FacesMessage(errMsg);
                        fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage("msg", fmsg);
                        return "";
                    }
                    copyFile(file.getFileName(), file.getInputstream());
                } catch (RbException ex) {
                    Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);
                    FacesMessage fmsg = new FacesMessage(ex.getMessage());
                    fmsg.setSeverity(FacesMessage.SEVERITY_FATAL);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage("msg", fmsg);
                    return "";
                }
            }
        } catch (IOException ex) {
            FacesMessage fmsg = new FacesMessage(ex.getMessage());
            fmsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("msg", fmsg);
            return "";
        }
        return "/aopQueue?faces-redirect=true";
    }

    public String postCheckReconFile() {
        uploadDir = "/usr/local/dmcdev/AFFILIATE.PAYMENT/";
        try {
            if(file==null || file.getFileName().isEmpty() || "".equals(file.getFileName())) {
                FacesMessage msg = new FacesMessage("Please select file for upload");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";                
            }
            if ("-1".equals(networkId)) {
                FacesMessage msg = new FacesMessage("Network ID is missing");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            }
            if (this.checkAmount == null || this.checkAmount.isEmpty()) {
                FacesMessage msg = new FacesMessage("Check amount is required");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            }
            RedObject rb = new RedObject("WDE", "AOP:Queue");
            rb.setProperty("fileName", file.getFileName());
            rb.setProperty("networkId", this.networkId);
            rb.setProperty("checkAmount", this.checkAmount);
            rb.setProperty("queueType", "payment");
            rb.setProperty("checkId",this.checkId);
            try {
                rb.callMethod("setQueue");
                String errStat = rb.getProperty("errStat");
                String errCode = rb.getProperty("errCode");
                String errMsg = rb.getProperty("errMsg");
                if (errStat.equals("-1")) {
                    errMsg = "Error: " + errCode + " " + errMsg;
                    FacesMessage fmsg = new FacesMessage(errMsg);
                    fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage("msg", fmsg);
                    return "";
                }
                copyFile(file.getFileName(), file.getInputstream());
            } catch (RbException ex) {
                Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage fmsg = new FacesMessage(ex.getMessage());
                fmsg.setSeverity(FacesMessage.SEVERITY_FATAL);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
                return "";
            }
        } catch (IOException ex) {
            FacesMessage fmsg = new FacesMessage(ex.getMessage());
            fmsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("msg", fmsg);
            return "";
        }
        return "/paymentQueue?faces-redirect=true";
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            try (OutputStream out = new FileOutputStream(new File(uploadDir + fileName))) {
                int read;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                in.close();
                out.flush();
            }

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return the vendorCode
     */
    public String getVendorCode() {
        return vendorCode;
    }

    /**
     * @param vendorCode the vendorCode to set
     */
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    /**
     * @return the networkId
     */
    public String getNetworkName() {
        return networkName;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkName(String networkId) {
        this.networkName = networkId;
    }

    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param networkId the networkdId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * @return the checkAmount
     */
    public String getCheckAmount() {
        return checkAmount;
    }

    /**
     * @param checkAmount the checkAmount to set
     */
    public void setCheckAmount(String checkAmount) {
        this.checkAmount = checkAmount;
    }

    /**
     * @return the checkId
     */
    public String getCheckId() {
        return checkId;
    }

    /**
     * @param checkId the checkId to set
     */
    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    /**
     * @return the checkDate
     */
    public String getCheckDate() {
        return checkDate;
    }

    /**
     * @param checkDate the checkDate to set
     */
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * @return the keyStatus
     */
    public String getKeyStatus() {
        return keyStatus;
    }

    /**
     * @param keyStatus the keyStatus to set
     */
    public void setKeyStatus(String keyStatus) {
        this.keyStatus = keyStatus;
    }
}
