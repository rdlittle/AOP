/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.view;

import com.webfront.controller.DataController;
import com.webfront.model.AffiliateOrder;
import com.webfront.model.BatchItem;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
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
    private DataController controller;
    private List<BatchItem> batchList;
    private String newBatchId;
    private BatchItem newBatch;
    private boolean isSelected;
    private String validatePayId;
    private boolean fromErrorListing;
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
        setController(new DataController());
        setBatchList(new LinkedList<BatchItem>());
    }

    public String viewOrder(String orderId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String str = (String) facesContext.getExternalContext().getRequestHeaderMap().get("referer");
        setReferringPage(str);
        this.fromErrorListing = str.contains("affiliateErrorListing.xhtml");
        setOrder(getController().getAffiliateOrder(orderId));
        return "affiliateOrder.xhtml?orderId=" + orderId + "&faces-redirect=true";
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
        getController().setAffiliateOrder(order.getId(), order);
        return "batchManager?faces-redirect=true";
    }

    public String cancel() {
        if (isFromErrorListing()) {
            return "affiliateErrorListing.xhtml?faces-redirect=true";
        }
        setBatchList(new LinkedList<BatchItem>());
        return "batchManager?faces-redirect=true";
    }

    public String cancelDefer() {
        String orderId = order.getId();
        return "affiliateOrder.xhtml?orderId=" + orderId + "&faces-redirect=true";
    }

    public String prepareDefer() {
        setBatchList(getController().getSelectBatchList(order.getVendorId(), order.getBatchId()));
        return "affiliateOrderDefer?faces-redirect=true";
    }

    public String doDefer() {
        String orderId = order.getId();
        String batchId = newBatch.getId();
        String rmsg = getController().deferOrder(orderId, batchId);
        if (rmsg.equals("ok")) {
            getController().clearBatchTree();
            this.batchList = null;
            String rtn = "batchManager";
            rtn += "?faces-redirect=true";
            return rtn;
        }
        return "";
    }

    public String getValidatePayId(javax.faces.event.AjaxBehaviorEvent evt) {
        String id = order.getPayingId();
        String rmsg = getController().validatePayId(id);
        if ("OK".equalsIgnoreCase(rmsg)) {
            order.setHasErrors(false);
            order.setPayingId(id);
        }
        String payCid = new String();
        FacesMessage msg = new FacesMessage(rmsg);
        FacesContext ctx = FacesContext.getCurrentInstance();
        String cid = component.getClientId();
        UIComponent cmp = ctx.getViewRoot().findComponent("orderForm:payId");
        if (cmp != null) {
            payCid = cmp.getClientId();
        }
        ctx.addMessage(cid, msg);
        return "";
    }

    public void setValidatePayId(javax.faces.event.AjaxBehaviorEvent evt) {
        String id = order.getPayingId();
        String rmsg = getController().validatePayId(id);
        FacesMessage msg = new FacesMessage(rmsg);
        FacesContext ctx = FacesContext.getCurrentInstance();
        String cid = component.getClientId();
        UIComponent cmp = ctx.getViewRoot().findComponent("orderForm:payId");
        if (cmp != null) {
            String payCid = cmp.getClientId();
        }
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

}
