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

    /**
     * Creates a new instance of UploadBean
     */
    public UploadBean() {
        uploadDir = "/home/uvuser/EXPORT/";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String upload() {
        try {
            if ("-1".equals(vendorCode)) {
                FacesMessage msg = new FacesMessage("Vendor code is missing");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            } else {
                RedObject rb = new RedObject("WDE", "AOP:Queue");
                rb.setProperty("vendorMasterId", this.vendorCode);
                rb.setProperty("fileName", file.getFileName());
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
}
