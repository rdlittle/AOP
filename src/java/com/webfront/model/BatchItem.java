/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author rlittle
 */
public class BatchItem implements Serializable {
    private String id;
    public String storeName;
    private String storeID;
    private String vendorDiv;
    public String processDate;
    private String processTime;
    public Float commission;
    public String commissionAsString;
    private String IBV;
    public Float appliedAmt;
    public String appliedAmtAsString;
    private boolean psApprovalStatus;
    private String psApprovalName;
    private String psApprovalDate;
    private String qaApprovalStatus;
    private String qaApprovalName;
    private String qaApprovalDate;    
    private String orderCount;
    private String errorCount;
    private String batchSeq;
    private String linkedKey;
    private String vendorId;
    private ArrayList<String> aoIds;
    private ArrayList<String> orderIds;
    public String styleClass;
    
    public BatchItem() {
        this.commission=new Float(0);
    }
    
    public String getStyleClass() {
        if(appliedAmt != commission) {
            styleClass="redCell";
        } else {
            styleClass="whiteCell";
        }
        return styleClass;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return the storeID
     */
    public String getStoreID() {
        return storeID;
    }

    /**
     * @param storeID the storeID to set
     */
    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    /**
     * @return the vendorDiv
     */
    public String getVendorDiv() {
        return vendorDiv;
    }

    /**
     * @param vendorDiv the vendorDiv to set
     */
    public void setVendorDiv(String vendorDiv) {
        this.vendorDiv = vendorDiv;
    }

    /**
     * @return the processDate
     */
    public String getProcessDate() {
        return processDate;
    }

    /**
     * @param processDate the processDate to set
     */
    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    /**
     * @return the processTime
     */
    public String getProcessTime() {
        return processTime;
    }

    /**
     * @param processTime the processTime to set
     */
    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    /**
     * @return the commission
     */
    public Float getCommission() {
        return commission;
    }
    
    public String getCommissionAsString() {
        NumberFormat nf=NumberFormat.getCurrencyInstance();
        String comm=nf.format(commission);
        return comm;
    }

    /**
     * @param c the commission to set
     */
    public void setCommission(String c) {
        this.commission = Float.valueOf(c);
    }

    /**
     * @return the IBV
     */
    public String getIBV() {
        return IBV;
    }

    /**
     * @param IBV the IBV to set
     */
    public void setIBV(String IBV) {
        this.IBV = IBV;
    }
    
    /**
     * @return Amount applied to check
     */
    public Float getAppliedAmt() {
        return appliedAmt;
    }
    
    /**
     * @return Amount applied to check
     */
    public String getAppliedAmtAsString() {
        String amt=Float.toString(appliedAmt);
        if(amt == null || "".equals(amt)) {
            amt = "0.00";
        }
        if(!amt.startsWith("$")) {
            NumberFormat nf=NumberFormat.getCurrencyInstance();
            amt = nf.format(appliedAmt);
        }
        return amt;
    }    
    
    /*
     * @param The amount applied to the check
     */
    public void setAppliedAmt(String amt) {
        appliedAmt = Float.valueOf(amt);
    }

    /**
     * @return the psApprovalStatus
     */
    public boolean getPsApprovalStatus() {
        return psApprovalStatus;
    }

    /**
     * @param psApprovalStatus the psApprovalStatus to set
     */
    public void setPsApprovalStatus(boolean psApprovalStatus) {
        this.psApprovalStatus = psApprovalStatus;
    }
    public void rowSelect(SelectEvent evt) {
        String comp = evt.getComponent().getAttributes().toString();
    }
    
    /**
     * @return the psApprovalName
     */
    public String getPsApprovalName() {
        return psApprovalName;
    }

    /**
     * @param psApprovalName the psApprovalName to set
     */
    public void setPsApprovalName(String psApprovalName) {
        this.psApprovalName = psApprovalName;
    }

    /**
     * @return the psApprovalDate
     */
    public String getPsApprovalDate() {
        return psApprovalDate;
    }

    /**
     * @param psApprovalDate the psApprovalDate to set
     */
    public void setPsApprovalDate(String psApprovalDate) {
        this.psApprovalDate = psApprovalDate;
    }

    /**
     * @return the qaApprovalStatus
     */
    public String getQaApprovalStatus() {
        return qaApprovalStatus;
    }

    /**
     * @param qaApprovalStatus the qaApprovalStatus to set
     */
    public void setQaApprovalStatus(String qaApprovalStatus) {
        this.qaApprovalStatus = qaApprovalStatus;
    }

    /**
     * @return the qaApprovalName
     */
    public String getQaApprovalName() {
        return qaApprovalName;
    }

    /**
     * @param qaApprovalName the qaApprovalName to set
     */
    public void setQaApprovalName(String qaApprovalName) {
        this.qaApprovalName = qaApprovalName;
    }

    /**
     * @return the qaApprovalDate
     */
    public String getQaApprovalDate() {
        return qaApprovalDate;
    }

    /**
     * @param qaApprovalDate the qaApprovalDate to set
     */
    public void setQaApprovalDate(String qaApprovalDate) {
        this.qaApprovalDate = qaApprovalDate;
    }

    /**
     * @return the orderCount
     */
    public String getOrderCount() {
        return orderCount;
    }

    /**
     * @param orderCount the orderCount to set
     */
    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    /**
     * @return the errorCount
     */
    public String getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount the errorCount to set
     */
    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * @return the batchSeq
     */
    public String getBatchSeq() {
        return batchSeq;
    }

    /**
     * @param batchSeq the batchSeq to set
     */
    public void setBatchSeq(String batchSeq) {
        this.batchSeq = batchSeq;
    }

    /**
     * @return the linkedKey
     */
    public String getLinkedKey() {
        return linkedKey;
    }

    /**
     * @param linkedKey the linkedKey to set
     */
    public void setLinkedKey(String linkedKey) {
        this.linkedKey = linkedKey;
    }

    /**
     * @return the vendorId
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return the aoIds
     */
    public ArrayList<String> getAoIds() {
        return aoIds;
    }

    /**
     * @param aoIds the aoIds to set
     */
    public void setAoIds(ArrayList<String> aoIds) {
        this.aoIds = aoIds;
    }

    /**
     * @return the orderIds
     */
    public ArrayList<String> getOrderIds() {
        return orderIds;
    }

    /**
     * @param orderIds the orderIds to set
     */
    public void setOrderIds(ArrayList<String> orderIds) {
        this.orderIds = orderIds;
    }
    
    @Override
    public int hashCode() {
        int hash = 1;
        return hash * 31 + id.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof BatchItem)) {
            return false;
        }
        BatchItem item=(BatchItem) obj;
        return item.id.equals(this.id);
    }
    
    @Override
    public String toString() {
        String str = "BatchItem{";
        str+="id="+id+" , ";
        str+="storeName="+storeName+" , ";
        str+="processDate="+processDate+" , ";
        str+="processTime="+processTime+" , ";
        str+="commission="+commissionAsString+" , ";
        str+="IBV="+IBV+" , ";
        str+="appliedAmt="+appliedAmt+" , ";
        str+="psApprovalStatus="+psApprovalStatus+" , ";
        str+="psApprovalName="+psApprovalName+" , ";
        str+="psApprovalDate="+psApprovalDate+" , ";
        str+="qaApprovalStatus="+qaApprovalStatus+" , ";
        str+="qaApprovalName="+qaApprovalName+" , ";
        str+="qaApprovalDate="+qaApprovalDate+" , ";        
        str+="orderCount="+orderCount+" , ";
        str+="errorCount="+errorCount+" , ";
        str+="batchSeq"+getBatchSeq()+" , ";
        str+="linkedKey"+linkedKey+" , ";
        str+="vendorId"+vendorId+" , }";
        return str;
    }
}
