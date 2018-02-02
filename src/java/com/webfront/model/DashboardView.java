/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

/**
 *
 * @author rlittle
 */
@ManagedBean
@ViewScoped
public class DashboardView implements Serializable {
    private DashboardModel reportModel;
    private DashboardModel availableModel;

    @PostConstruct
    public void init() {
        setReportModel(new DefaultDashboardModel());
        setAvailableModel(new DefaultDashboardModel());
        
        DashboardColumn amCol1 = new DefaultDashboardColumn();
        DashboardColumn amCol2 = new DefaultDashboardColumn();
        DashboardColumn rmCol1 = new DefaultDashboardColumn();
        DashboardColumn rmCol2 = new DefaultDashboardColumn();
        
        amCol1.addWidget("SRP");
        amCol2.addWidget("VENDOR_ORDER_DATE");
        
        rmCol1.addWidget("col1");
        rmCol2.addWidget("col2");
        
        getAvailableModel().addColumn(amCol1);
        getAvailableModel().addColumn(amCol2);
        
        getReportModel().addColumn(rmCol1);
        getReportModel().addColumn(rmCol2);
        
    }
    
    public void handleReorder(DashboardReorderEvent evt) {
        String fromId = evt.getWidgetId();
    }
    /**
     * @param availableModel the availableModel to set
     */
    public void setAvailableModel(DashboardModel availableModel) {
        this.availableModel = availableModel;
    }

    /**
     * @return the availableModel
     */
    public DashboardModel getAvailableModel() {
        return availableModel;
    }

    /**
     * @param model the model to set
     */
    public void setReportModel(DashboardModel model) {
        this.reportModel = model;
    }

    /**
     * @return the model
     */
    public DashboardModel getReportModel() {
        return reportModel;
    }
}
