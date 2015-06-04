/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.BatchItem;
import com.webfront.model.OrderRelKeysBatch;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class BatchSummaryBean implements Serializable {

    @ManagedProperty(value = "#{vendorCode}")
    private String vendorID;
    private RedObject rbo;
    private String totalCommission;
    private String totalAppliedAmt;
    private String checkAmt;
    private boolean qaRole;
    private String userId;
    private BatchItem selectedItem;
    private BatchItem[] selectedItems;
    private OrderRelKeysBatch batchList;
    BatchManagementBean mgmtBean;
    private SelectItem[] storeList;
    private List<BatchItem> filteredStores;

    public BatchSummaryBean() {
        filteredStores = new ArrayList<>();
        Map map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        mgmtBean = (BatchManagementBean) map.get("batchManagementBean");
        setCheckAmt(Float.toString(mgmtBean.getCheckAmt()));
        setQaRole(false);
        setUserId(mgmtBean.getUserId());
    }

    public void populateTree() {
        String newVendorId = mgmtBean.getMasterId();
        ArrayList<String> tempStoreList=new ArrayList<>();
        if (!newVendorId.equals(this.vendorID)) {
            try {
                setVendorID(mgmtBean.getMasterId());
                setRbo(new RedObject("WDE", "AOP:Batch"));
                ArrayList<BatchItem> tempList = new ArrayList<>();
                getRbo().setProperty("vendorID", getVendorID());
                getRbo().callMethod("getAOHeaders");
                String batches = new String(getRbo().getProperty("batchID").getBytes(Charset.forName("ISO8859-1")));
                UniDynArray batchIds = getRbo().getPropertyToDynArray("batchID");
                UniDynArray aoIDs = getRbo().getPropertyToDynArray("aoIDList");
                UniDynArray storeNames = getRbo().getPropertyToDynArray("storeNameList");
                UniDynArray processDates = getRbo().getPropertyToDynArray("processDateList");
                UniDynArray processTimes = getRbo().getPropertyToDynArray("processTimeList");
                UniDynArray commissions = getRbo().getPropertyToDynArray("commissionList");
                UniDynArray ibvs = getRbo().getPropertyToDynArray("IBV");
                UniDynArray appliedAmts = getRbo().getPropertyToDynArray("appliedAmtList");
                UniDynArray orderCounts = getRbo().getPropertyToDynArray("orderCountList");
                UniDynArray errorCounts = getRbo().getPropertyToDynArray("errorCountList");
                UniDynArray psApprovalStatus = getRbo().getPropertyToDynArray("psApprovalStatusList");
                UniDynArray psApprovalNames = getRbo().getPropertyToDynArray("psApprovalNameList");
                UniDynArray psApprovalDates = getRbo().getPropertyToDynArray("psApprovalDateList");
                UniDynArray qaApprovalStatus = getRbo().getPropertyToDynArray("qaApprovalStatusList");
                UniDynArray qaApprovalNames = getRbo().getPropertyToDynArray("qaApprovalNameList");
                UniDynArray qaApprovalDates = getRbo().getPropertyToDynArray("qaApprovalDateList");
                UniDynArray linkedKeys = getRbo().getPropertyToDynArray("linkedBatch");
                totalCommission = getRbo().getProperty("totalCommission");
                totalAppliedAmt = getRbo().getProperty("totalAppliedAmt");
                int vals = batchIds.dcount(1);
                String batchId;
                BatchItem batchItem;
                for (int val = 1; val < vals; val++) {
                    batchItem = new BatchItem();
                    batchId = batchIds.extract(1, val).toString();
                    batchItem.setId(batchId);
                    batchItem.setStoreName(storeNames.extract(1, val).toString());
                    batchItem.setProcessDate(processDates.extract(1, val).toString());
                    batchItem.setProcessTime(processTimes.extract(1, val).toString());
                    batchItem.setCommission(commissions.extract(1, val).toString());
                    batchItem.setAppliedAmt(appliedAmts.extract(1, val).toString());
                    String junk = psApprovalStatus.extract(1, val).toString();
                    int i = Integer.parseInt(junk);
                    if (i == 1) {
                        batchItem.setPsApprovalStatus(true);
                    } else {
                        batchItem.setPsApprovalStatus(false);
                    }
                    batchItem.setPsApprovalDate(psApprovalDates.extract(1, val).toString());
                    batchItem.setPsApprovalName(psApprovalNames.extract(1, val).toString());
                    batchItem.setQaApprovalStatus(qaApprovalStatus.extract(1, val).toString());
                    batchItem.setQaApprovalDate(qaApprovalDates.extract(1, val).toString());
                    batchItem.setQaApprovalName(qaApprovalNames.extract(1, val).toString());
                    batchItem.setOrderCount(orderCounts.extract(1, val).toString());
                    batchItem.setErrorCount(errorCounts.extract(1, val).toString());
                    batchItem.setLinkedKey(linkedKeys.extract(1, val).toString());
                    tempList.add(batchItem);
                    String storeName = batchItem.getStoreName();
                    if(!tempStoreList.contains(storeName)) {
                        tempStoreList.add(storeName);
                    }
                }
                setBatchList(new OrderRelKeysBatch(tempList));
                
                storeList=createFilterOptions(tempStoreList);
            } catch (RbException ex) {
                Logger.getLogger(BatchSummaryBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<BatchItem> getFilteredStores() {
        return filteredStores;
    }
    public void setFilteredStores(List<BatchItem> data) {
        filteredStores=data;
    }
    
    public SelectItem[] createFilterOptions(ArrayList<String> data) {
      SelectItem[] options = new SelectItem[data.size() + 1];  
  
        options[0] = new SelectItem("", "Select");  
        for(int i = 0; i < data.size(); i++) {  
            options[i + 1] = new SelectItem(data.get(i), data.get(i));  
        }  
  
        return options;          
    }
    
    public SelectItem[] getStoreList() {
        return storeList;
    }
    
    public String setApproval() throws RbException {
        if(getFilteredStores()!=null) {
            UniDynArray batchIds = new UniDynArray();
            int val = 1;
            for(BatchItem item : getFilteredStores()) {
                String id=item.getId();
                batchIds.insert(1,val++, id);
            }
            getRbo().setProperty("batchID", batchIds);
            getRbo().setProperty("userID", mgmtBean.getUserId());
            getRbo().setProperty("processStatus", "1");
            getRbo().setProperty("approvalRole", "PS");
            getRbo().callMethod("setAOBatchApproval");
            populateTree();
        }
        return "";
    }

    public UniDynArray getBatchDetail(String batchId) throws RbException {
        getRbo().setProperty("batchID", batchId);
        getRbo().callMethod("getBatchDetail");
        return getRbo().getPropertyToDynArray("aoIDList");
    }

    /**
     * @return the vendorID
     */
    public String getVendorID() {
        return vendorID;
    }

    /**
     * @param vendorID the vendorID to set
     */
    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    /**
     * @return the rbo
     */
    public RedObject getRbo() {
        return this.rbo;
    }

    /**
     * @param rbo the rbo to set
     */
    public void setRbo(RedObject rbo) {
        this.rbo = rbo;
    }

    /**
     * @return the batchList
     */
    public OrderRelKeysBatch getBatchList() {
        populateTree();
        return batchList;
    }
    
    /**
     * @param batchList the batchList to set
     */
    public void setBatchList(OrderRelKeysBatch batchList) {
        this.batchList = batchList;
    }

    /*
     * @return Total commission for all batches
     */
    public String getTotalCommission() {
        if (totalCommission == null || "".equals(totalCommission)) {
            return "0.00";
        }
        if (!totalCommission.startsWith("$")) {
            Float amt = Float.valueOf(totalCommission);
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            totalCommission = nf.format(amt);
        }
        return totalCommission;
    }

    /*
     * @param Total commission amount
     */
    public void setTotalCommission(String tc) {
        totalCommission = tc;
    }

    /*
     * @return Total applied amount
     */
    public String getTotalAppliedAmt() {
        if (totalAppliedAmt == null || "".equals(totalAppliedAmt)) {
            totalAppliedAmt = "0.00";
        }
        if (!totalAppliedAmt.startsWith("$")) {
            Float amt = Float.valueOf(totalAppliedAmt);
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            totalAppliedAmt = nf.format(amt);
        }
        return totalAppliedAmt;
    }

    /*
     * @param Total applied amount
     */
    public void setTotalAppliedAmt(String tc) {
        totalCommission = tc;
    }

    /**
     * @return the qaRole
     */
    public boolean isQaRole() {
        return qaRole;
    }

    public boolean getQaRole() {
        return qaRole;
    }

    /**
     * @param qaRole the qaRole to set
     */
    public void setQaRole(boolean val) {
        qaRole = val;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the checkAmt
     */
    public String getCheckAmt() {
        if (checkAmt == null || "".equals(checkAmt)) {
            return "0.00";
        }
        if (!checkAmt.startsWith("$")) {
            Float amt = Float.valueOf(checkAmt);
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            checkAmt = nf.format(amt);
        }
        return checkAmt;
    }

    /**
     * @param checkAmt the checkAmt to set
     */
    public void setCheckAmt(String checkAmt) {
        this.checkAmt = checkAmt;
    }

    public BatchItem[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(BatchItem[] items) {
        selectedItems = items;
    }

    /**
     * @return the selectedItem
     */
    public BatchItem getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(BatchItem selectedItem) {
        this.selectedItem = selectedItem;
    }
}
