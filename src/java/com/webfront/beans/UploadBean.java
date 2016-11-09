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
    private final String uploadDir = "/usr/local/dmcdev/REPORT.QUEUE/";

    public UploadedFile getFile() {
        return file;
    }
    
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean upload() {
        try {
            if (file == null || file.getFileName().isEmpty() || "".equals(file.getFileName())) {
                FacesMessage msg = new FacesMessage("Please select file for upload");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
            copyFile(file.getFileName(), file.getInputstream());
        } catch (IOException ex) {
            FacesMessage fmsg = new FacesMessage(ex.getMessage());
            fmsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("msg", fmsg);
            return false;
        }
        return true;
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
 
}
