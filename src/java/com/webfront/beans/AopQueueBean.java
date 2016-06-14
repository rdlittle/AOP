/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.controller.AopQueueController;
import com.webfront.model.AopQueue;
import com.webfront.model.Queue;
import com.webfront.model.RunLevel;
import com.webfront.model.UVException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AopQueueBean implements Serializable {

    private final AopQueueController controller = new AopQueueController();
    private final RedObject rbo = new RedObject("WDE", "AOP:Queue");

    private final ArrayList<RunLevel> runLevels;
    private final HashMap<String, String> runLevelNames;
    private ArrayList<AopQueue> queueList;
    private ArrayList<AopQueue> selectedItems;
    private String queueType;
    private boolean selected;
    @ManagedProperty(value = "#{downloadBean}")
    private DownloadBean downLoader;
    @ManagedProperty(value = "#{uploadBean}")
    private UploadBean uploader;
    private String affiliateMasterId;
    private String fileType;
    private UploadedFile file;

    public AopQueueBean() {
        this.selectedItems = new ArrayList<>();
        this.queueList = new ArrayList<>();
        this.selected = false;
        runLevels = new ArrayList<>();
        runLevelNames = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        setRunLevels();
    }

    public void rePoll() {
        setQueueList();
    }

    /**
     * @return the queueList
     */
    public ArrayList<? extends Queue> getQueueList() {
        return this.queueList;
    }

    public void setQueueList() {
        this.queueList.clear();
        this.queueList = (ArrayList<AopQueue>) controller.getQueueList(queueType);
    }

    public void onCellEdit(CellEditEvent evt) {
        String newValue = evt.getNewValue().toString();
        String oldValue = evt.getOldValue().toString();

        if (newValue.equals(oldValue)) {
            return;
        }
        DataTable t = (DataTable) evt.getSource();
        AopQueue qi = (AopQueue) t.getRowData();
        qi.setRunLevel(evt.getNewValue().toString());

        controller.setQueueItem(qi);
        try {
            controller.updateQueue();
        } catch (UVException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("msg", ex.toFacesMessage());
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelect(SelectEvent se) {
//        System.out.println("AopQueueBean.onRowSelect(): select is "+selectedItems.isEmpty());
        selected = !selectedItems.isEmpty();
    }

    public void onRowUnselect(UnselectEvent se) {
//        System.out.println("AopQueueBean.onRowUnSelect(): select is "+selectedItems.isEmpty());
        selected = !selectedItems.isEmpty();
    }

    public boolean getHasSelection() {
//        System.out.println("AopQueueBean.getHasSelection(): select is "+selectedItems.isEmpty());
        return selectedItems.isEmpty();
    }

    public void onClickProcess() {
        setSelected(false);
        UniDynArray uda = new UniDynArray();
        for (AopQueue q : queueList) {
            uda.insert(1, -1, q.getId());
            uda.insert(2, -1, "1");
        }
        rbo.setProperty("queueId", uda.extract(1));
        rbo.setProperty("queueStatus", uda.extract(2));
        try {
            rbo.callMethod("setAopQueueStatus");
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String onUpload() {
        boolean result;
        String runLevel = "0";
        String userId = "system";
        queueType = "AO";

        FacesContext fContext = FacesContext.getCurrentInstance();
        ExternalContext eContext = fContext.getExternalContext();
        Map<String, String> reqmap = eContext.getRequestParameterMap();
        if (reqmap.containsKey("form1:vendorID_input")) {
            affiliateMasterId = reqmap.get("form1:vendorID_input");
        }
        if (reqmap.containsKey("runLevel")) {
            runLevel = reqmap.get("runLevel");
        }
        if (reqmap.containsKey("userId")) {
            userId = reqmap.get("userId");
        }
        if (reqmap.containsKey("queueType")) {
            queueType = reqmap.get("queueType");
        }

        rbo.setProperty("affiliateMasterId", affiliateMasterId);
        rbo.setProperty("fileName", getFile().getFileName());
        rbo.setProperty("userName", userId);
        rbo.setProperty("queueType", queueType);
        rbo.setProperty("runLevel", runLevel);

        try {
            rbo.callMethod("postAopQueue");
            int errStat = Integer.parseInt(rbo.getProperty("svrStatus"));
            if (errStat == -1) {
                String errCode = rbo.getProperty("svrCtrlCode");
                String errMsg = rbo.getProperty("svrMessage");
                errMsg = "Error: " + errCode + " " + errMsg;
                FacesMessage fmsg = new FacesMessage(errMsg);
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
                return "";
            } else {
                getUploader().setFile(getFile());
                result = uploader.upload(); // Uploads file to /usr/local/dmcdev/AOP.UPLOAD/
                if (!result) {
                    return "";
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage fmsg = new FacesMessage(ex.getMessage());
            fmsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("msg", fmsg);
            return "";
        }
        return "/affiliate/orders/aopQueue?faces-redirect=true";
    }

    /**
     * @return the selectedItems
     */
    public ArrayList<AopQueue> getSelectedItems() {
        return selectedItems;
    }

    /**
     * @param selectedItems the selectedItems to set
     */
    public void setSelectedItems(ArrayList<AopQueue> selectedItems) {
        this.selectedItems = selectedItems;
    }

    /**
     * @return the queueType
     */
    public String getQueueType() {
        return queueType;
    }

    /**
     * @param qType the queueType to set 'C'heck or 'O'rder
     */
    public void setQueueType(String qType) {
        this.queueType = qType;
        FacesContext fContext = FacesContext.getCurrentInstance();
        ExternalContext eContext = fContext.getExternalContext();
        String pi = fContext.getCurrentPhaseId().getName();
        setQueueList();
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the downLoader
     */
    public DownloadBean getDownLoader() {
        return downLoader;
    }

    /**
     * @param downLoader the downLoader to set
     */
    public void setDownLoader(DownloadBean downLoader) {
        this.downLoader = downLoader;
    }

    /**
     * @return the uploader
     */
    public UploadBean getUploader() {
        return uploader;
    }

    /**
     * @param uploader the uploader to set
     */
    public void setUploader(UploadBean uploader) {
        this.uploader = uploader;
    }

    /**
     * @return the affiliateMasterId
     */
    public String getAffiliateMasterId() {
        return affiliateMasterId;
    }

    /**
     * @param affiliateMasterId the affiliateMasterId to set
     */
    public void setAffiliateMasterId(String affiliateMasterId) {
        this.affiliateMasterId = affiliateMasterId;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the file
     */
    public UploadedFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * @return the runLevelNames
     */
    public ArrayList<RunLevel> getRunLevels() {
        if (runLevels.isEmpty()) {
            setRunLevels();
        }
        return runLevels;
    }

    public String getRunLevelName(String key) {
        if (runLevelNames.containsKey(key)) {
            return runLevelNames.get(key);
        }
        return "";
    }

    public void setRunLevels() {
        try {
            rbo.callMethod("getAopQueueRunLevelList");
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
                UniDynArray uda = rbo.getPropertyToDynArray("runLevel");
                uda.insert(2, rbo.getPropertyToDynArray("runLevelName"));
                runLevels.clear();
                int vals = uda.dcount(1);
                for (int val = 1; val <= vals; val++) {
                    RunLevel rl = new RunLevel();
                    rl.setLevel(uda.extract(1, val).toString());
                    rl.setName(uda.extract(2, val).toString());
                    runLevels.add(rl);
                    runLevelNames.put(rl.getLevel(), rl.getName());
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
