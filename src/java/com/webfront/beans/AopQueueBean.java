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
import com.webfront.model.QueueError;
import com.webfront.model.QueueItem;
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
    private final RedObject rbo = new RedObject("WDE", "AFFILIATE:Queue");

    private final ArrayList<RunLevel> runLevels;
    private final HashMap<String, String> runLevelNames;
    private ArrayList<AopQueue> queueList;
    private ArrayList<AopQueue> selectedItems;
    private final ArrayList<QueueItem> queueTypes;
    private QueueItem queueType;
    private String _queueType;
    private boolean selected;
    @ManagedProperty(value = "#{downloadBean}")
    private DownloadBean downLoader;
    @ManagedProperty(value = "#{uploadBean}")
    private UploadBean uploader;
    @ManagedProperty(value = "#{aopUserBean}")
    private AopUserBean aopUser;
    @ManagedProperty(value = "#{affiliateMappingBean}")
    private AffiliateMappingBean mapping;
    private String affiliateMasterId;
    private String fileType;
    private UploadedFile file;
    private boolean running;
    private boolean showAll;
    private boolean admin;
    private boolean hasItems;
    private boolean changed;
    private ArrayList<QueueError> errorList;

    public AopQueueBean() {
        this.selectedItems = new ArrayList<>();
        this.queueList = new ArrayList<>();
        this.queueTypes = new ArrayList<>();
        this.errorList = new ArrayList<>();
        this.selected = false;
        this.queueType = new QueueItem();
        this.hasItems = false;
        runLevels = new ArrayList<>();
        runLevelNames = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        runLevels.clear();
    }

    public void rePoll() {
        setQueueList();
    }

    public String onGetErrorListing() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map<String, String> map = ctx.getExternalContext().getRequestParameterMap();
        if (!map.containsKey("aopQueueId")) {
            return "";
        }
        String queueId = map.get("aopQueueId");
        errorList.clear();
        errorList.addAll(controller.getErrorList(queueId));
        setChanged(false);
        return "/affiliate/queue/validationErrors?faces-redirect=true&id=" + queueId;
    }

    public ArrayList getErrorList() {
        return errorList;
    }

    public ArrayList<String> getColumnHeaders() {
        return controller.getColumnHeaders();
    }

    public void setErrorListing(String queueId) {

    }

    /**
     * @return the queueList
     */
    public ArrayList<? extends Queue> getQueueList() {
        return queueList;
    }

    public void setQueueList() {
        queueList.clear();
        ArrayList<AopQueue> list = (ArrayList<AopQueue>) controller.getQueueList(getQueueType().getQueueType(), aopUser.getUserName().toUpperCase(), showAll);
        queueList = list;
        this.running = controller.isMonitorRunning();
        this.hasItems = !this.queueList.isEmpty();
    }

    public boolean isRunning() {
        return controller.isMonitorRunning();
    }

    public void onBtnStartQueue() {
        controller.startQueue(getQueueType().getQueueType());
    }

    public void onEditError(CellEditEvent evt) {
        String oldValue = evt.getOldValue().toString();
        String newValue = evt.getNewValue().toString();
        if (!oldValue.equals(newValue)) {
            setChanged(true);
        }
    }

    public void onSaveReport() {
        controller.setErrorList(errorList);
        controller.saveReportEdit();
        setChanged(false);
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
            rbo.callMethod("setAffiliateQueueStatus");
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String onUpload() {
        boolean result;
        String runLevel = "0";
        String userId = aopUser.getUserName();

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
//        if (reqmap.containsKey("queueType")) {
//            queueType = reqmap.get("queueType");
//        }

        if (affiliateMasterId != null) {
            rbo.setProperty("aggregatorId", affiliateMasterId);
        }

        for (QueueItem item : queueTypes) {
            if (item.getQueueDesc().equalsIgnoreCase(getQueueType().getQueueDesc())) {
                setQueueType(item);
                break;
            }
        }
        rbo.setProperty("fileName", getFile().getFileName());
        rbo.setProperty("userName", userId);
        rbo.setProperty("queueType", getQueueType().getQueueType());
        rbo.setProperty("runLevel", runLevel);

        try {
            rbo.callMethod("postAffiliateQueue");
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
        return "/affiliate/queue/aopQueue?faces-redirect=true";
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
    public QueueItem getQueueType() {
        return queueType;
    }

    /**
     * @param qType the queueType to set 'C'heck or 'O'rder
     */
    public void setQueueType(String qType) {
        for (QueueItem item : queueTypes) {
            if (item.getQueueType().equalsIgnoreCase(qType)) {
                break;
            }
        }
        FacesContext fContext = FacesContext.getCurrentInstance();
        ExternalContext eContext = fContext.getExternalContext();
        String pi = fContext.getCurrentPhaseId().getName();
        setRunLevels();
        setQueueList();
    }

    public ArrayList<QueueItem> getQueueTypes() {
        try {
            rbo.callMethod("getAffiliateQueueUploadTypeList");
            int errStat = Integer.parseInt(rbo.getProperty("svrStatus"));
            if (errStat == -1) {
                String errCode = rbo.getProperty("svrCtrlCode");
                String errMsg = rbo.getProperty("svrMessage");
                errMsg = "Error: " + errCode + " " + errMsg;
                FacesMessage fmsg = new FacesMessage(errMsg);
                fmsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("msg", fmsg);
                return null;
            } else {
                queueTypes.clear();
                UniDynArray keys = rbo.getPropertyToDynArray("queueType");
                UniDynArray values = rbo.getPropertyToDynArray("queueDesc");
                UniDynArray groups = rbo.getPropertyToDynArray("queueGroup");
                int vals = keys.count(1);
                for (int val = 1; val <= vals; val++) {
                    String k = keys.extract(1, val).toString();
                    String v = values.extract(1, val).toString();
                    String g = groups.extract(1, val).toString();
                    QueueItem item = new QueueItem(k, v, g);
                    queueTypes.add(item);
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage fmsg = new FacesMessage(ex.getMessage());
            fmsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("msg", fmsg);
            return null;
        }
        return queueTypes;
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
        if (getQueueType().getQueueType().isEmpty() && _queueType.isEmpty()) {
            return;
        }
        if (getQueueType().getQueueType().equals(_queueType)) {
            return;
        }
        try {
            rbo.setProperty("queueType", getQueueType().getQueueType());
            rbo.callMethod("getAffiliateQueueRunLevelList");
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
            _queueType = getQueueType().getQueueType();
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getHasItems() {
        return hasItems;
    }

    /**
     * @return the showAll
     */
    public boolean isShowAll() {
        return showAll;
    }

    /**
     * @param showAll the showAll to set
     */
    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public void onClickShowAll() {
        setQueueList();
    }

    /**
     * @return the admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @return the user
     */
    public AopUserBean getAopUser() {
        return aopUser;
    }

    /**
     * @param u the user to set
     */
    public void setAopUser(AopUserBean u) {
        this.aopUser = u;
        admin = aopUser.hasAuthorizedRole("admin");
    }

    /**
     * @param queueType the queueType to set
     */
    public void setQueueType(QueueItem queueType) {
        this.queueType = queueType;
    }

    public void initQueue(String type) {
        if (type.isEmpty()) {
            return;
        }
        if (queueTypes.isEmpty()) {
            getQueueTypes();
        }
        for (QueueItem qt : queueTypes) {
            if (qt.getQueueType().equalsIgnoreCase(type)) {
                queueType = qt;
                break;
            }
        }
        setQueueList();
    }

    /**
     * @return the mapping
     */
    public AffiliateMappingBean getMapping() {
        return mapping;
    }

    /**
     * @param mapping the mapping to set
     */
    public void setMapping(AffiliateMappingBean mapping) {
        this.mapping = mapping;
    }

    /**
     * @return the changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * @param changed the changed to set
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public static class ErrorLine {

        public ErrorLine(String line) {

        }
    }
}
