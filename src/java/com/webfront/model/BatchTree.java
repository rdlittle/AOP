/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webfront.model;

import org.primefaces.model.LazyDataModel;

/**
 *
 * @author rlittle
 */
public class BatchTree {
    private LazyDataModel model;
    public BatchTree() {
        
    }
    public LazyDataModel getModel() {
        return this.model;
    }
    public void setModel(LazyDataModel m) {
        this.model = m;
    }
}
