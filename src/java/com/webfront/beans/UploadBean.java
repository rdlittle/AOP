/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        uploadDir="/home/uvuser/EXPORT/";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String upload() {
        try {
            FacesMessage msg = new FacesMessage("Processing", "Vendor: "+getVendorCode()+" Report: "+file.getFileName() + " ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            copyFile(file.getFileName(), file.getInputstream());
            return "/processReport?faces-redirect=true";
        } catch (IOException e) {
        }
        return "";
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
