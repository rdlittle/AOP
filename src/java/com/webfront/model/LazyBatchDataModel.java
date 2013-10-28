/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author rlittle
 */
public class LazyBatchDataModel extends LazyDataModel<BatchItem> {

    private List<BatchItem> dataSource;

    public LazyBatchDataModel(List<BatchItem> ds) {
        this.dataSource = ds;
    }

    @Override
    public BatchItem getRowData(String rowKey) {
        for (BatchItem batchItem : dataSource) {
            if (batchItem.getId().equals(rowKey)) {
                return batchItem;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(BatchItem batchItem) {
        return batchItem.getId();
    }

    @Override
    public List<BatchItem> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<BatchItem> data = new ArrayList<>();

        //filter
        for (BatchItem batchItem : dataSource) {
            boolean match = true;

            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                try {
                    String filterProperty = it.next();
                    String filterValue = filters.get(filterProperty);
                    String fieldValue = String.valueOf(batchItem.getClass().getField(filterProperty).get(batchItem));

                    if (filterValue == null || fieldValue.startsWith(filterValue)) {
                        match = true;
                    } else {
                        match = false;
                        break;
                    }
                } catch (Exception e) {
                    match = false;
                }
            }

            if (match) {
                data.add(batchItem);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazyBatchSorter(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }
}
