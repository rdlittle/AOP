/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans.testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author rlittle
 */
public class AoTestDataGroup {
 
    private String groupId;
    private String createDate;
    private String description;
    private HashMap<String,Boolean> newBatch;
    private HashMap<String,Boolean> overloadRefund;
    
    private LinkedHashMap<String,AoTestDataUnit> unitMap;
    private ArrayList<AoTestDataUnit> unitList;
    private boolean multiBatch;
    private boolean changed;
    private boolean addingRow;
    private int row;
    private AoTestDataUnit unit;
    private AoTestDataUnit selectedUnit;
    private String rowIndex;
    private String longText;
    private String batchType;
    
    public AoTestDataGroup() {
        groupId = "";
        createDate = "";
        description = "";
        newBatch = new HashMap<>();
        overloadRefund = new HashMap<>();
        unitMap = new LinkedHashMap<>();
        multiBatch = false;
        changed = false;
        row = 0;
        addingRow = false;
        unit = new AoTestDataUnit();
        unitList = new ArrayList<>();
        longText = "";
        batchType = "";
    }

    /**
     * @return the unitMap
     */
    public ArrayList<AoTestDataUnit> getUnitMap() {
        ArrayList<AoTestDataUnit> list = new ArrayList<>();
        list.addAll(unitMap.values());
        return list;
    }
    
    public void removeUnit(String id) {
        if(unitMap.containsKey(id)) {
            unitMap.remove(id);
            newBatch.remove(id);
            overloadRefund.remove(id);
        }
        setChanged(true);
    }
    
    public void addRow() {
        setRow(getRow() + 1);
        unit = new AoTestDataUnit();
        unitMap.put(Integer.toString(getRow()), unit);
        setAddingRow(true);
    }
    
    public void commitRow() {
        unitMap.put(Integer.toString(getRow()),getUnit());
        setAddingRow(false);
        setChanged(true);
    }
    
    public void addUnit(String id,AoTestDataUnit u) {
        unitMap.put(id, u);
        setChanged(true);
    }
    
    public void onChangeDesc(AjaxBehaviorEvent abe) {
        setChanged(true);
    }
    
    public void onReset() {
        unitMap.clear();
        unitList.clear();
        row=1;
        unit = new AoTestDataUnit();
        setAddingRow(false);
        setChanged(false);
    }
    
    public void removeRow() {
        System.out.println(row);
        this.unit=new AoTestDataUnit();
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the newBatch
     */
    public HashMap<String,Boolean> getNewBatch() {
        return newBatch;
    }
    
    public void setNewBatch(HashMap<String,Boolean> map) {
        this.newBatch=map;
    }

    /**
     * @return the overloadRefund
     */
    public HashMap<String,Boolean> getOverloadRefund() {
        return overloadRefund;
    }
    
    /**
     *
     * @param map the HashMap<String,String> to set
     */
    public void setOverloadedRefund(HashMap<String,Boolean> map) {
        this.overloadRefund=map;
    }

    /**
     * @param unitMap the unitMap to set
     */
    public void setUnitMap(LinkedHashMap<String,AoTestDataUnit> unitMap) {
        this.unitMap = unitMap;
    }

    /**
     * @return the changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * @param changed the changed to set
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * @return the multiBatch
     */
    public boolean isMultiBatch() {
        return multiBatch;
    }

    /**
     * @param multiBatch the multiBatch to set
     */
    public void setMultiBatch(boolean multiBatch) {
        this.multiBatch = multiBatch;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the unit
     */
    public AoTestDataUnit getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(AoTestDataUnit unit) {
        this.unit = unit;
    }

    /**
     * @return the addingRow
     */
    public boolean isAddingRow() {
        return addingRow;
    }

    /**
     * @param addingRow the addingRow to set
     */
    public void setAddingRow(boolean addingRow) {
        this.addingRow = addingRow;
    }

    /**
     * @return the rowIndex
     */
    public String getRowIndex() {
        return rowIndex;
    }

    /**
     * @param rowIndex the rowIndex to set
     */
    public void setRowIndex(String rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * @return the selectedUnit
     */
    public AoTestDataUnit getSelectedUnit() {
        System.out.println("AoTestDataGroup.getSelectedUnit(): "+selectedUnit.getId());
        return selectedUnit;
    }

    /**
     * @param selectedUnit the selectedUnit to set
     */
    public void setSelectedUnit(AoTestDataUnit selectedUnit) {
        System.out.println("AoTestDataGroup.setSelectedUnit(): "+selectedUnit.getId());
        this.selectedUnit = selectedUnit;
    }

    /**
     * @return the unitList
     */
    public ArrayList<AoTestDataUnit> getUnitList() {
        return unitList;
    }

    /**
     * @param unitList the unitList to set
     */
    public void setUnitList(ArrayList<AoTestDataUnit> unitList) {
        this.unitList = unitList;
    }

    /**
     * @return the longText
     */
    public String getLongText() {
        return longText;
    }

    /**
     * @param longText the longText to set
     */
    public void setLongText(String longText) {
        this.longText = longText;
    }

    /**
     * @return the batchType
     */
    public String getBatchType() {
        return batchType;
    }

    /**
     * @param batchType the batchType to set
     */
    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }
    
}
