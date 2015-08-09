/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.controller;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.beans.BatchManagementBean;
import com.webfront.beans.BatchSummaryBean;
import com.webfront.beans.Config;
import com.webfront.beans.WebDEBean;
import com.webfront.model.AffiliateError;
import com.webfront.model.AffiliateOrder;
import com.webfront.model.BatchItem;
import com.webfront.u2.DynArray;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultTreeNode;

/**
 *
 * @author rlittle
 */
public final class DataController {

    BatchManagementBean mgmtBean;
    WebDEBean wdeBean;
    BatchTree batchTree;
    public Config config;
    protected ArrayList<BatchItem> batchList;
    private List<AffiliateOrder> orderList;
    public List<String> orderIdList;
    private String vendorId;
    private RedObject rbo;
    private BatchItem selectedItem;
    private BatchItem[] selectedItems;
    protected Float totalAppliedAmt;
    private Map map;
    private boolean toggleApproval;

    public DataController() {
        setMap(FacesContext.getCurrentInstance().getExternalContext().getSessionMap());
        config = new Config();
        wdeBean = (WebDEBean) getMap().get("webDEBean");
        mgmtBean = (BatchManagementBean) map.get("batchManagementBean");
        batchTree = (BatchTree) map.get("batchTree");
        vendorId = "";
        selectedItem = new BatchItem();
        setTotalAppliedAmt(Float.valueOf(0));
    }

    public List<BatchItem> getBatchList() {
        String newVendorId = mgmtBean.getMasterId();
        String vendorDiv = mgmtBean.getDetail().getAffiliateDetailId();
        if(vendorDiv.equals("0")) {
            vendorDiv="";
        } else {
            String divNum=vendorDiv.substring(vendorDiv.indexOf("*")+1,vendorDiv.length());
            vendorDiv=divNum;
        }
        if(this.batchTree==null) {
            this.batchList=null;
            this.orderList=null;
        }
        if (!newVendorId.equals(this.vendorId) ||this.batchList == null || this.batchList.isEmpty()) {
            setRbo(new RedObject("WDE", "Affiliates:Headers"));
            try {
                vendorId = newVendorId;
                ArrayList<BatchItem> tempList = new ArrayList<>();
                getRbo().setProperty("masterId", vendorId);
                getRbo().setProperty("divId", vendorDiv);
                getRbo().callMethod("getHeaders");
                String batches = new String(getRbo().getProperty("batchID").getBytes(Charset.forName("ISO8859-1")));
                UniDynArray batchIds = getRbo().getPropertyToDynArray("batchID");
                UniDynArray aoIDs = getRbo().getPropertyToDynArray("aoIDList");
                UniDynArray storeNames = getRbo().getPropertyToDynArray("storeNameList");
                UniDynArray storeIds = getRbo().getPropertyToDynArray("storeId");
                UniDynArray vendorDivs = getRbo().getPropertyToDynArray("divId");
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
                UniDynArray batchSequences = getRbo().getPropertyToDynArray("seq");

                int vals = batchIds.dcount(1);
                String batchId;
                BatchItem batchItem;
                for (int val = 1; val < vals; val++) {
                    batchItem = new BatchItem();
                    batchId = batchIds.extract(1, val).toString();
                    batchItem.setId(batchId);
                    batchItem.setStoreName(storeNames.extract(1, val).toString());
                    batchItem.setStoreID(storeIds.extract(1, val).toString());
                    batchItem.setVendorDiv(vendorDivs.extract(1, val).toString());
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
                    batchItem.setBatchSeq(batchSequences.extract(1,val).toString());
                    setTotalAppliedAmt((Float) (getTotalAppliedAmt() + batchItem.getAppliedAmt()));
                    tempList.add(batchItem);
                }
                setBatchList(tempList);

            } catch (RbException ex) {
                Logger.getLogger(BatchSummaryBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return batchList;
    }

    public void setBatchList(ArrayList<BatchItem> list) {
        this.batchList = list;
    }
    
    public void clearBatchTree() {
        if(this.batchList != null) {
            this.batchList.clear();
        }
        this.batchTree.setBatchList(null);
        this.batchTree.setRoot(new DefaultTreeNode());
    }

    public List<BatchItem> getSelectBatchList(String vendorId, String batchId) {
        List<BatchItem> list = new ArrayList<>();
        try {
            setRbo(new RedObject("WDE", "Affiliates:Headers"));
            getRbo().setProperty("masterId", vendorId);
            getRbo().setProperty("batchID", batchId);
            getRbo().callMethod("getHeaders");
            UniDynArray batchIds = getRbo().getPropertyToDynArray("batchID");
            UniDynArray aoIDs = getRbo().getPropertyToDynArray("aoIDList");
            UniDynArray storeNames = getRbo().getPropertyToDynArray("storeNameList");
            UniDynArray storeIds = getRbo().getPropertyToDynArray("storeId");
            UniDynArray vendorDivs = getRbo().getPropertyToDynArray("divId");
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
            UniDynArray batchSequences = getRbo().getPropertyToDynArray("seq");

            int vals = batchIds.dcount(1);

            BatchItem batchItem;
            for (int val = 1; val < vals; val++) {
                batchItem = new BatchItem();
                batchId = batchIds.extract(1, val).toString();
                batchItem.setId(batchId);
                batchItem.setStoreName(storeNames.extract(1, val).toString());
                batchItem.setStoreID(storeIds.extract(1, val).toString());
                batchItem.setVendorDiv(vendorDivs.extract(1, val).toString());
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
                batchItem.setBatchSeq(batchSequences.extract(1,val).toString());
                list.add(batchItem);
            }
        } catch (RbException ex) {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<String> getOrderIdList(String batchId) {
        ArrayList<String> list = new ArrayList<>();
        if (getSelectedItem() != null) {
            setRbo(new RedObject("WDE", "AOP:AffiliateOrders"));
            getRbo().setProperty("batchId", batchId);
            try {
                getRbo().callMethod("getOrderList");
                UniDynArray uda = getRbo().getPropertyToDynArray("orderIds");
                int attrs = uda.dcount(1);
                for (int attr = 1; attr < attrs; attr++) {
                    list.add(uda.extract(1, attr).toString());
                }
            } catch (RbException ex) {
                Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        orderIdList = list;
        return orderIdList;
    }

    public List<AffiliateOrder> getOrderList(String batchID) {
        ArrayList<AffiliateOrder> list = new ArrayList<>();
        setRbo(new RedObject("WDE", "AOP:AffiliateOrders"));
        getRbo().setProperty("batchId", batchID);
        try {
            getRbo().callMethod("getOrders");
            AffiliateOrder ao = new AffiliateOrder();
            UniDynArray orderIds = getRbo().getPropertyToDynArray("orderId");
            UniDynArray orderRefs = getRbo().getPropertyToDynArray("orderRef");
            UniDynArray orderDates = getRbo().getPropertyToDynArray("orderDate");
            UniDynArray payingIds = getRbo().getPropertyToDynArray("payingId");
            UniDynArray filingIds = getRbo().getPropertyToDynArray("filingId");
            UniDynArray orderTotals = getRbo().getPropertyToDynArray("orderTotal");
            UniDynArray ibvTotals = getRbo().getPropertyToDynArray("ibvTotal");
            UniDynArray placementIds = getRbo().getPropertyToDynArray("placementId");
            UniDynArray errorCounts = getRbo().getPropertyToDynArray("errorCount");
            UniDynArray vendorOrderNums = getRbo().getPropertyToDynArray("vendorOrderNum");
            UniDynArray commissions = getRbo().getPropertyToDynArray("commissionTotal");
            int values = orderIds.dcount(1);
            for (int val = 1; val <= values; val++) {
                ao.setId(orderIds.extract(1, val).toString());
                ao.setFilingId(filingIds.extract(1, val).toString());
                ao.setPayingId(payingIds.extract(1, val).toString());
                ao.setOrderTotal(orderTotals.extract(1, val).toString());
                ao.setOrderDate(orderDates.extract(1, val).toString());
                ao.setOrderRef(orderRefs.extract(1, val).toString());
                ao.setIbvTotal(ibvTotals.extract(1, val).toString());
                ao.setCommissionTotal(Float.valueOf(commissions.extract(1, val).toString()));
                ao.setPlacementId(placementIds.extract(1, val).toString());
                ao.setVendorOrderNum(vendorOrderNums.extract(1, val).toString());
                ao.setErrorCount(errorCounts.extract(1, val).toString());
                ao.setHasErrors(false);
                if (ao.getErrorCount() != null) {
                    if (!"0".equals(ao.getErrorCount())) {
                        ao.setHasErrors(true);
                    }
                }
                list.add(ao);
                ao = new AffiliateOrder();
            }
            orderList = list;
        } catch (RbException ex) {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderList;
    }

    public String deferOrder(String orderId, String batchId) {
        try {
            setRbo(new RedObject("WDE", "AOP:AffiliateOrders"));
            RedObject r=getRbo();
            r.setProperty("orderId", orderId);
            r.setProperty("batchId", batchId);
            r.callMethod("setOrderDefer");
            String errStat = r.getProperty("errStat");
            String errCode = r.getProperty("errCode");
            String errMsg = r.getProperty("errMsg");
            if(!errStat.isEmpty() || errStat.equals("-1")) {
                FacesMessage msg = new FacesMessage(errCode+" "+errMsg);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            }
        } catch (RbException ex) {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ok";
    }

    public void setApproval(String batchID, String role, boolean tf) {
        String approvalFlag = tf ? "1" : "0";
        setRbo(new RedObject("WDE", "AOP:Batch"));
        getRbo().setProperty("batchID", batchID);
        getRbo().setProperty("userID",mgmtBean.getUserId());
        getRbo().setProperty("processStatus", approvalFlag);
        getRbo().setProperty("approvalRole", role);
        try {
            getRbo().callMethod("setAOBatchApproval");
        } catch (RbException ex) {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String validatePayId(String id) {
        setRbo(new RedObject("WDE","Orders:Validation"));
        getRbo().setProperty("id", id);
        try {
            getRbo().callMethod("getValidatePayId");
            boolean isCust = "1".equals(getRbo().getProperty("isCust").toString());
            boolean isEzCust = "1".equals(getRbo().getProperty("isEzCust").toString());
            boolean isRep = "1".equals(getRbo().getProperty("isRep").toString());
            boolean isDist = "1".equals(getRbo().getProperty("isDist").toString());
            String errStat = getRbo().getProperty("errStat");
            String errCode = getRbo().getProperty("errCode");
            String errMsg = getRbo().getProperty("errMsg");
            if(errStat.equals("-1")) {
                return "Error: "+errCode+" "+errMsg;
            }
        } catch (RbException ex) {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "OK";
    }
    
    public boolean isValidOrder(AffiliateOrder order) {
        setRbo(new RedObject("WDE","Affiliates:Orders"));
        
        return true;
    }
    
    public UniDynArray getFileItem(String fileName, String itemId, String fieldNum) throws RbException {
        setRbo(new RedObject("WDE","UTILS:File"));
        UniDynArray fileItem = new UniDynArray();
        int score = 0;
        if (fileName !=null) {
            getRbo().setProperty("fileName",fileName);
            score += 1;
        }
        if (itemId != null) {
            getRbo().setProperty("itemId", itemId);
            score += 1;
        }
        if (fieldNum == null) {
            fieldNum = "";
        }  
        getRbo().setProperty("fieldNum",fieldNum);
        if (score == 2) {
            getRbo().callMethod("getUtilFileRec");
            String errStatus = getRbo().getProperty("errStat").toString();
            String errCode = getRbo().getProperty("errCode").toString();
            String errMesg = getRbo().getProperty("errMesg").toString();
            if(errStatus.equals("-1")) {
                return null;
            }
            fileItem = getRbo().getPropertyToDynArray("fileItem");
        }
        return fileItem;
    }
    
    public void setFileItem(String fileName, String itemId, String fieldNum, boolean retainLock, boolean mustExist, UniDynArray fileRec) throws RbException {
        setRbo(new RedObject("WDE","UTILS:File"));
        int score = 0;
        if (fileName !=null) {
            getRbo().setProperty("fileName",fileName);
            score += 1;
        }
        if (itemId != null) {
            getRbo().setProperty("itemId", itemId);
            score += 1;
        }
        if (fieldNum == null) {
            fieldNum = "";
        }
        getRbo().setProperty("fieldNum", fieldNum);
        getRbo().setProperty("retainLock", retainLock ? "1" : "0" );
        getRbo().setProperty("mustExist", mustExist ? "1" : "0");
        if (fileRec==null) {
            fileRec = new UniDynArray();
        }
        if(score==2) {
            getRbo().callMethod("setUtilFileRec");
        }
    }
    
    /**
     * @return the rbo
     */
    public RedObject getRbo() {
        return rbo;
    }

    /**
     * @param rbo the rbo to set
     */
    public void setRbo(RedObject rbo) {
        this.rbo = rbo;
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

    /**
     * @return the selectedItems
     */
    public BatchItem[] getSelectedItems() {
        return selectedItems;
    }

    /**
     * @param selectedItems the selectedItems to set
     */
    public void setSelectedItems(BatchItem[] selectedItems) {
        this.selectedItems = selectedItems;
    }

    /**
     * @return the totalAppliedAmt
     */
    public Float getTotalAppliedAmt() {
        return totalAppliedAmt;
    }

    /**
     * @param totalAppliedAmt the totalAppliedAmt to set
     */
    public void setTotalAppliedAmt(Float totalAppliedAmt) {
        this.totalAppliedAmt = totalAppliedAmt;
    }

    /**
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * @return the toggleApproval
     */
    public boolean isToggleApproval() {
        return toggleApproval;
    }

    /**
     * @param toggleApproval the toggleApproval to set
     */
    public void setToggleApproval(boolean toggleApproval) {
        this.toggleApproval = toggleApproval;
    }

}
