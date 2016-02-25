/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.io.Serializable;

/**
 *
 * @author rlittle
 */
public class SelectItem implements Serializable {
    private String key;
    private String value;
    public SelectItem() {
    }
    public SelectItem(String k, String v) {
        this.key=k;
        this.value=v;
    }
    public String getKey() {
        return this.key;
    }
    public String getValue() {
        return this.value;
    }
    public void setKey(String k) {
        this.key=k;
    }
    public void setValue(String v) {
        this.value=v;
    }
    @Override
    public String toString() {
        return this.getKey();
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (key != null ? key.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SelectItem)) {
            return false;
        }
        SelectItem other = (SelectItem) object;
        return (this.key != null || other.key == null) && (this.key == null || this.key.equals(other.key));
    }
    
}
