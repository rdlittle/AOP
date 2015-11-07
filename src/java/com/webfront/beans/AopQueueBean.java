/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import com.webfront.controller.AopQueueController;
import com.webfront.model.AopQueue;
import com.webfront.model.UVException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class AopQueueBean implements Serializable {

    private final AopQueueController controller = new AopQueueController();
    private ArrayList<AopQueue> queueList;
    private ArrayList<AopQueue> selectedItems;
    private String queueType;
    private boolean selected;
    @ManagedProperty(value = "#{downloadBean}")
    private DownloadBean downLoader;
    private Integer statusLevel;

    public AopQueueBean() {
        this.selectedItems = new ArrayList<>();
        this.queueList = new ArrayList<>();
        this.selectedItems = new ArrayList<>();
        this.selected = false;
    }

    public void rePoll() {
        setQueueList();
    }

    /**
     * @return the queueList
     */
    public ArrayList<AopQueue> getQueueList() {
        if (this.queueList.isEmpty()) {
            setQueueList();
        }
        return this.queueList;
    }

    public void setQueueList() {
        this.queueList.clear();
        this.queueList = controller.getQueueList(queueType);
    }

    public void onCellEdit(CellEditEvent evt) {
        DataTable t = (DataTable) evt.getSource();
        AopQueue qi = (AopQueue) t.getRowData();
        qi.setQueueStatus(evt.getNewValue().toString());
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
        selected = true;
    }

    public void onRowUnselect(SelectEvent se) {
        selected = false;
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
     * @return the statusLevel
     */
    public Integer getStatusLevel() {
        return statusLevel;
    }

    /**
     * @param statusLevel the statusLevel to set
     */
    public void setStatusLevel(Integer statusLevel) {
        this.statusLevel = statusLevel;
    }
}
