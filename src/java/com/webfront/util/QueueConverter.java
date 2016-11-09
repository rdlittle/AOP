/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.util;

import com.webfront.beans.AopQueueBean;
import com.webfront.model.QueueItem;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rlittle
 */
@ManagedBean
@FacesConverter(value = "queueItemConverter")
public class QueueConverter implements Converter {
    
    @ManagedProperty(value="#{aopQueueBean}")
    private AopQueueBean aopQueueBean;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for(QueueItem item : aopQueueBean.getQueueTypes()) {
            if(item.getQueueDesc().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(!(value instanceof QueueItem)) {
            return "";
        }
        QueueItem item = (QueueItem) value;
        return item.getQueueDesc();
    }

    /**
     * @param aopQueueBean the aopQueueBean to set
     */
    public void setAopQueueBean(AopQueueBean aopQueueBean) {
        this.aopQueueBean = aopQueueBean;
    }
    
}
