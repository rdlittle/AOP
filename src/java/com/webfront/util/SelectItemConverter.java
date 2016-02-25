/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.util;

import com.webfront.model.SelectItem;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rlittle
 */
@ManagedBean(name = "selectItemConverterBean")
@FacesConverter(value = "selectItemConverter")
public class SelectItemConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.isEmpty()) {
            return null;
        }
        return new SelectItem(value, MVUtils.oConvDate(Integer.parseInt(value), "D2/"));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof SelectItem) {
            return ((SelectItem) value).getKey();
        }
        return null;
    }

}
