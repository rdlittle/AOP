/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.view;

import com.webfront.controller.AffiliateOrderController;
import com.webfront.model.AffiliateOrder;
import com.webfront.model.BatchItem;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class AffiliateOrderView implements Serializable {

    private AffiliateOrder order;

    private AffiliateOrderController aoController;
    private List<BatchItem> batchList;
    private String newBatchId;
    private BatchItem newBatch;
    private boolean isSelected;

    private boolean fromErrorListing;
    private boolean fromSearchScreen;
    private String referringPage;

    private UIComponent component;

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public AffiliateOrderView() {
        setIsSelected(false);
        setBatchList(new LinkedList<BatchItem>());
        init();
    }

    @PostConstruct
    public void init() {
        order = new AffiliateOrder();
        aoController = new AffiliateOrderController();
    }

    public String viewOrder(String orderId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String refPage = facesContext.getViewRoot().getViewId();
        setReferringPage(refPage);
        this.fromErrorListing = refPage.contains("affiliateErrorListing.xhtml");
        this.fromSearchScreen = refPage.contains("batchManagementInputForm.xhtml");
        setOrder(getAoController().getAffiliateOrder(orderId));
        return "/affiliate/orders/order.xhtml?orderId=" + orderId + "&faces-redirect=true";
    }

    public String validateOrder() {

        return "";
    }

    public void onRowSelect(SelectEvent evt) {
        setIsSelected(true);
    }

    public void onRowUnselect(UnselectEvent evt) {
        setIsSelected(false);
    }

    public void handleReturn() {
        RequestContext.getCurrentInstance().closeDialog("/affiliate/orders/batchManager?faces-redirect=true");
    }

    public String prepareUpdate() {
        if (order.isHasErrors()) {
            if (fromErrorListing || fromSearchScreen) {
                return getReferringPage() + "?faces-redirect=true";
            }
        }
        setBatchList(new LinkedList<BatchItem>());
        getAoController().setAffiliateOrder(order.getId(), order);
        return "/affiliate/orders/batchManager?faces-redirect=true";
    }

    public String cancel() {
        if (fromErrorListing || fromSearchScreen) {
            return getReferringPage() + "?faces-redirect=true";
        }
        setBatchList(new LinkedList<BatchItem>());
        return "/affiliate/orders/batchManager?faces-redirect=true";
    }

    public String cancelDefer() {
        String orderId = order.getId();
        return "/affiliate/orders/order.xhtml?orderId=" + orderId + "&faces-redirect=true";
    }

    public String prepareDefer() {
        setBatchList(getAoController().getDataController().getSelectBatchList(order.getVendorId(), order.getBatchId()));
        return "/affiliate/orders/orderDefer?faces-redirect=true";
    }

    public String doDefer() {
        String orderId = order.getId();
        String batchId = newBatch.getId();
        String rmsg = getAoController().getDataController().deferOrder(orderId, batchId);
        if (rmsg.equals("ok")) {
            getAoController().getDataController().clearBatchTree();
            this.batchList = null;
            String rtn = "/affiliate/orders/batchManager";
            rtn += "?faces-redirect=true";
            return rtn;
        }
        return "";
    }

    public void getValidatePayId() {
        String id = order.getPayingId();
        String rmsg = getAoController().getDataController().validatePayId(id);
        if ("OK".equalsIgnoreCase(rmsg)) {
            order.setHasErrors(false);
            order.setPayingId(id);
        }
        String cid = component.getClientId();
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(rmsg);
        ctx.addMessage(cid, msg);
    }

    public void setValidatePayId(javax.faces.event.AjaxBehaviorEvent evt) {
        String id = order.getPayingId();
        String rmsg = getAoController().getDataController().validatePayId(id);
        FacesMessage msg = new FacesMessage(rmsg);
        FacesContext ctx = FacesContext.getCurrentInstance();
        String cid = component.getClientId();
        ctx.addMessage(cid, msg);
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
    public AffiliateOrderController getAoController() {
        return aoController;
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

    /**
     * @return the fromErrorListing
     */
    public boolean isFromErrorListing() {
        return fromErrorListing;
    }

    /**
     * @param fromErrorListing the fromErrorListing to set
     */
    public void setFromErrorListing(boolean fromErrorListing) {
        this.fromErrorListing = fromErrorListing;
    }

    /**
     * @return the referringPage
     */
    public String getReferringPage() {
        return referringPage;
    }

    /**
     * @param referringPage the referringPage to set
     */
    public void setReferringPage(String referringPage) {
        this.referringPage = referringPage;
    }

    /**
     * @return the fromSearchScreen
     */
    public boolean isFromSearchScreen() {
        return fromSearchScreen;
    }

    /**
     * @param fromSearchScreen the fromSearchScreen to set
     */
    public void setFromSearchScreen(boolean fromSearchScreen) {
        this.fromSearchScreen = fromSearchScreen;
    }

}
