/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.controller;

/**
 *
 * @author rlittle
 */
import javax.faces.application.FacesMessage;  
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;  
  
import org.primefaces.model.UploadedFile;  
  
@ManagedBean
@SessionScoped
public final class UploadController {  
  
    private UploadedFile file;  
  
    public UploadedFile getFile() {  
        return file;  
    }  
  
    public void setFile(UploadedFile file) {  
        this.file = file;  
    }  
  
    public String upload() {  
        if(file != null) {  
            FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);  
            return "/faces/processReport?faces-redirect=true";
        }  
        return "/faces/processReport?faces-redirect=true";
    }  
}  