/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.view;

import com.webfront.controller.DataController;
import com.webfront.model.AffiliateOrder;
import com.webfront.model.BatchItem;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class AffiliateOrderView {

    private AffiliateOrder order;
    private DataController controller;
    private List<BatchItem> batchList;
    private String newBatchId;
    private BatchItem newBatch;
    private boolean isSelected;

    public AffiliateOrderView() {
        setIsSelected(false);
        setController(new DataController());
        setBatchList(new LinkedList<BatchItem>());
    }

    public String viewOrder(String orderId) {
        setOrder(getController().getAffiliateOrder(orderId));
        return "affiliateOrder.xhtml?orderId=" + orderId+"&faces-redirect=true";
    }
    
    public void onRowSelect(SelectEvent evt) {
        setIsSelected(true);
    }
    
    public void onRowUnselect(UnselectEvent evt) {
        setIsSelected(false);
    }

    public void handleReturn() {
        RequestContext.getCurrentInstance().closeDialog("batchManager?faces-redirect=true");
    }
    
    public String prepareUpdate() {
        setBatchList(new LinkedList<BatchItem>());
        return "batchManager?faces-redirect=true";
    }

    public String cancel() {
        setBatchList(new LinkedList<BatchItem>());
        return "batchManager?faces-redirect=true";
    }    
    
    public String prepareDefer() {
        setBatchList(getController().getSelectBatchList(order.getVendorId(), order.getBatchId()));
        return "affiliateOrderDefer?faces-redirect=true";
    }
    
    public String doDefer() {
        return "";
    }
    /**
     * @return the order
     */
    public AffiliateOrder getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(AffiliateOrder order) {
        this.order = order;
    }

    /**
     * @return the controller
     */
    public DataController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(DataController controller) {
        this.controller = controller;
    }

    /**
     * @return the batchList
     */
    public List<BatchItem> getBatchList() {
        return batchList;
    }

    /**
     * @param batchList the batchList to set
     */
    public void setBatchList(List<BatchItem> batchList) {
        this.batchList = batchList;
    }

    /**
     * @return the newBatchId
     */
    public String getNewBatchId() {
        return newBatchId;
    }

    /**
     * @param newBatchId the newBatchId to set
     */
    public void setNewBatchId(String newBatchId) {
        this.newBatchId = newBatchId;
    }

    /**
     * @return the newBatch
     */
    public BatchItem getNewBatch() {
        return newBatch;
    }

    /**
     * @param newBatch the newBatch to set
     */
    public void setNewBatch(BatchItem newBatch) {
        this.newBatch = newBatch;
    }

    /**
     * @return the isSelected
     */
    public boolean isIsSelected() {
        return isSelected;
    }

    /**
     * @param isSelected the isSelected to set
     */
    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
}
