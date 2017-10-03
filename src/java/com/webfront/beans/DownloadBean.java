 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author rlittle
 */
@Named
@SessionScoped
public class DownloadBean implements Serializable {

    private StreamedContent content;
    private String fileName;
    private final String pathName = "/usr/local/dmcdev/AOP.REPORTS/";

    public DownloadBean() {
        fileName = "";
    }

    /**
     * @return the content
     */
    public StreamedContent getContent() {
        if (!"".equals(fileName)) {
            try {
                final String file = pathName + fileName;
                FileInputStream fiStream;
                fiStream = new FileInputStream(file);
                content = new DefaultStreamedContent(fiStream, "application/vnd-ms-excel", file);
            } catch (IOException ex) {
                Logger.getLogger(DownloadBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().responseComplete();
            return this.content;
        } else {
            return null;
        }
    }

    /**
     * @param content the content to set
     */
//    public void setContent(StreamedContent content) {
//        this.content = content;
//    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDownload() {
        Logger.getLogger(DownloadBean.class.getName()).log(Level.INFO, "DownloadBean.setDownload()");
    }
}
