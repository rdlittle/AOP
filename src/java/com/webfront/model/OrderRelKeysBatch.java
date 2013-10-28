/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.util.ArrayList;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author rlittle
 */
public class OrderRelKeysBatch extends ListDataModel<BatchItem> implements SelectableDataModel<BatchItem> {
    
    public OrderRelKeysBatch() {
        super();
    }
    public OrderRelKeysBatch(ArrayList<BatchItem> data) {
        super(data);
    }
    
    @Override
    public Object getRowKey(BatchItem t) {
        ArrayList<BatchItem> items=(ArrayList<BatchItem>)getWrappedData();
        for(BatchItem i: items) {
            if(t.equals(i)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public BatchItem getRowData(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
