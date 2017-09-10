/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import asjava.uniclientlibs.UniDynArray;
import com.webfront.beans.WebDEBean;
import com.webfront.controller.AffiliateAggregatorController;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public class Aggregator implements Serializable {

    @ManagedProperty(value = "#{affiliateAggregatorController}")
    private AffiliateAggregatorController controller;
    @ManagedProperty(value = "#{WebDEBean}")
    private WebDEBean webDeBean;
    protected String ID;
    protected String name;
    protected String invPrefix;
    protected String payCountry;
    protected String payCurrency;
    protected String reportFormat;
    protected String userName;
    protected String createDate;
    private boolean isInactive;
    protected String networkId;
    private UIComponent selector;

    public Aggregator() {

    }

    public Aggregator(UniDynArray am) {
//        ID = am.extract(1, 1).toString();
        name = am.extract(1, 1).toString();
        payCurrency = am.extract(1, 2).toString();
        isInactive = am.extract(1, 3).toString().equals("1");
        invPrefix = am.extract(1, 4).toString();
        payCountry = am.extract(1, 5).toString();
        reportFormat = am.extract(1, 6).toString();
    }

    public void changeVendor(AjaxBehaviorEvent event) {
        setID(ID);
        Aggregator rec = getController().getAggregator(ID);
        setName(rec.getName());
        setNetworkId(rec.getNetworkId());
        setInvPrefix(rec.getInvPrefix());
        setPayCountry(rec.getPayCountry());
        setPayCurrency(rec.getPayCurrency());
        setReportFormat(rec.getReportFormat());
        setUserName(rec.getUserName());
        setCreateDate(rec.getCreateDate());
        setIsInactive(rec.isInactive());
    }

    public void saveRecord() {
        getController().setAggregator(this);
    }

    public void ajaxHandler(AjaxBehaviorEvent event) {
        System.out.println("AffiliateMaster.ajaxHandler()");
    }

    public void changeCurrency(AjaxBehaviorEvent event) {
        this.setPayCurrency(payCurrency);
    }

    public void changeCountry(AjaxBehaviorEvent event) {
        System.out.println(event.toString());
    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the prefix
     */
    public String getInvPrefix() {
        return invPrefix;
    }

    /**
     * @param invPrefix the prefix to set
     */
    public void setInvPrefix(String invPrefix) {
        this.invPrefix = invPrefix;
    }

    /**
     * @return the country
     */
    public String getPayCountry() {
        return payCountry;
    }

    /**
     * @param payCountry the country to set
     */
    public void setPayCountry(String payCountry) {
        this.payCountry = payCountry;
    }

    /**
     * @return the currency
     */
    public String getPayCurrency() {
        return payCurrency;
    }

    /**
     * @param payCurrency the currency to set
     */
    public void setPayCurrency(String payCurrency) {
        this.payCurrency = payCurrency;
    }

    /**
     * @return the mappingId
     */
    public String getReportFormat() {
        return reportFormat;
    }

    /**
     * @param reportFormat the mappingId to set
     */
    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the controller
     */
    public AffiliateAggregatorController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(AffiliateAggregatorController controller) {
        this.controller = controller;
    }

    /**
     * @return the selector
     */
    public UIComponent getSelector() {
        return selector;
    }

    /**
     * @param selector the selector to set
     */
    public void setSelector(UIComponent selector) {
        this.selector = selector;
    }

    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * @return the webDeBean
     */
    public WebDEBean getWebDeBean() {
        return webDeBean;
    }

    /**
     * @param webDeBean the webDeBean to set
     */
    public void setWebDeBean(WebDEBean webDeBean) {
        this.webDeBean = webDeBean;
    }
    
    /**
     * @return the isInactive
     */
    public boolean isInactive() {
        return isInactive;
    }

    /**
     * @param isInactive the isInactive to set
     */
    public void setIsInactive(boolean isInactive) {
        this.isInactive = isInactive;
    }

}
