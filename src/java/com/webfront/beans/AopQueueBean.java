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
import org.primefaces.event.UnselectEvent;

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
    public ArrayList<? extends Queue> getQueueList() {
        if (this.queueList.isEmpty()) {
            setQueueList();
        }
        return this.queueList;
    }

    public void setQueueList() {
        this.queueList.clear();
        this.queueList = (ArrayList<AopQueue>) controller.getQueueList(queueType);
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
        RedObject rbo = new RedObject("WDE","AOP:Queue");
        UniDynArray uda = new UniDynArray();
        for(AopQueue q : queueList) {
            uda.insert(1,-1 , q.getQueueId());
            uda.insert(2,-1, "1");
        }
        rbo.setProperty("queueId", uda.extract(1));
        rbo.setProperty("queueStatus", uda.extract(2));
        try {
            rbo.callMethod("setAopQueueStatus");
        } catch (RbException ex) {
            Logger.getLogger(AopQueueBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
