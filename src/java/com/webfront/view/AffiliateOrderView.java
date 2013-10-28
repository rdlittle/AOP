/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.view;

import com.webfront.controller.DataController;
import com.webfront.model.AffiliateOrder;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class AffiliateOrderView {

    private AffiliateOrder order;
    private DataController controller;

    public AffiliateOrderView() {
        setController(new DataController());
    }

    public String viewOrder(String orderId) {
        setOrder(getController().getAffiliateOrder(orderId));
        return "affiliateOrder.xhtml?orderId=" + orderId+"&faces-redirect=true";
    }

    public void handleReturn() {
        Map map = RequestContext.getCurrentInstance().getAttributes();
        String str = map.toString();
        str = "";
        RequestContext.getCurrentInstance().closeDialog("newxhtml?faces-redirect=true");
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
}
