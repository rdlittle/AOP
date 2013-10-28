/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author rlittle
 */
public class LazyBatchSorter implements Comparator<BatchItem> {

    private String sortField;
    private SortOrder sortOrder;

    public LazyBatchSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(BatchItem o1, BatchItem o2) {
        try {
            Object value1 = o1.getClass().getField(this.sortField).get(o1);
            Object value2 = o2.getClass().getField(this.sortField).get(o2);

            int value = ((Comparable)value1).compareTo(value2);
           
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
