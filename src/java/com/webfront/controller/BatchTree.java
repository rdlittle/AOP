/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.controller;

import com.webfront.beans.BatchManagementBean;
import com.webfront.beans.WebDEBean;
import com.webfront.model.AffiliateOrder;
import com.webfront.model.BatchItem;
import com.webfront.model.TreeRow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author rlittle
 */
@ManagedBean
@SessionScoped
public final class BatchTree implements Serializable, ValueChangeListener {

    private AffiliateOrderController controller;
    private TreeNode root;
    private TreeNode selectedNode;
    private List<AffiliateOrder> orderList;
    private ArrayList<BatchItem> batchList;
    private Float totalAppliedAmount;
    private Float balance;
    private BatchItem selectedBatch;
    private Map map;
    public String vendorId;
    public String vendorDiv;
    WebDEBean wdeBean;
    BatchManagementBean mgmtBean;

    public BatchTree() {
        setController(new AffiliateOrderController());
        vendorId = "";
        setRoot(new DefaultTreeNode("root", null));
        setTotalAppliedAmount(Float.valueOf(0));
        setBalance(getController().getDataController().mgmtBean.getCheckAmt());
    }

    public void onApprovalToggle(AjaxBehaviorEvent evt) {
        Object obj = evt.getSource();
        if (obj instanceof SelectBooleanCheckbox) {
            SelectBooleanCheckbox cb = (SelectBooleanCheckbox) obj;
            String id = cb.getClientId();
            String[] vals = id.split("\\:");
            int rowNum = Integer.parseInt(vals[2]);
            setSelectedNode(root.getChildren().get(rowNum));
            setSelectedBatch(batchList.get(rowNum));
            rowSelector(null);
            BatchItem item = batchList.get(rowNum);
            String psName = item.getPsApprovalName();
            String psDate = item.getPsApprovalDate();
            TreeRow row = (TreeRow) getSelectedNode().getData();
            row.setPsApprovalName(psName);
            row.setPsApprovalDate(psDate);
            getController().getDataController().setApproval(item.getId(),"PS",item.getPsApprovalStatus());
            root.getChildren().set(rowNum, new DefaultTreeNode(row));
        }
    }

    public void rowSelector(SelectEvent evt) {
        String eDate = getController().getDataController().wdeBean.getTodayExternal();
        BatchItem item = getSelectedBatch();
        if (!item.getPsApprovalStatus()) {
            item.appliedAmt = item.commission;
            item.setPsApprovalStatus(true);
            item.setPsApprovalName("rlittle");
            item.setPsApprovalDate(eDate);
            Float amt = getTotalAppliedAmount();
            amt += item.appliedAmt;
            setTotalAppliedAmount(amt);
            amt = getController().getDataController().mgmtBean.getCheckAmt();
            amt -= item.appliedAmt;
            getController().getDataController().mgmtBean.setBalance(amt);
        } else {
            Float amt = getTotalAppliedAmount();
            Float bal = getController().getDataController().mgmtBean.getBalance();
            amt -= item.appliedAmt;
            bal += item.appliedAmt;
            getController().getDataController().mgmtBean.setBalance(bal);
            item.appliedAmt = Float.valueOf("0.00");
            item.setPsApprovalStatus(false);
            item.setPsApprovalName("");
            item.setPsApprovalDate("");
            setTotalAppliedAmount(amt);
        }
    }

    public void rowUnSelector(UnselectEvent evt) {
        BatchItem item = ((BatchItem) evt.getObject());
        String eDate = wdeBean.getTodayExternal();
        if (item.getPsApprovalStatus()) {
            setTotalAppliedAmount(getTotalAppliedAmount() - item.appliedAmt);
            item.appliedAmt = Float.valueOf("0.00");
            item.setPsApprovalStatus(false);
            item.setPsApprovalName("");
            item.setPsApprovalDate(null);
        }
    }

    public void updateOrder(String id) {
        String orderId = id;
    }

    public void deferOrder(String id) {
        String orderId = id;
        if(id != null && !id.isEmpty()) {
            id+="updated";
        }
    }

    public Float getBalance() {
        return getController().getDataController().mgmtBean.getBalance();
    }
    
    public void setData(ArrayList<BatchItem> list) {
        TreeRow row;
        for (BatchItem item : list) {
            row = new TreeRow();
            row.setIsBatch(true);
            row.setIsOrder(false);
            row.setId(item.getId());
            row.setBatchId(item.getId());
            row.setDescription(item.getStoreName());
            row.setIbv(item.getIBV());
            row.setCommission(item.getCommissionAsString());
            row.setProcessDate(item.getProcessDate());
            row.setPayingId("-");
            row.setOrderCount(item.getOrderCount());
            row.setErrorCount(item.getErrorCount());
            row.setAppliedAmount(item.getAppliedAmtAsString());
            row.setPsApproval(item.getPsApprovalStatus());
            row.setPsApprovalDate(item.getPsApprovalDate());
            row.setPsApprovalName(item.getPsApprovalName());
            row.setNumber(item.getStoreName());
            row.setDivisionPlacement(item.getVendorDiv());
            row.setNumber(item.getStoreID());
            TreeNode node = new DefaultTreeNode(row, root);
            root.getChildren().add(node);
            
            Float amt=item.getAppliedAmt();
            if(amt != null && amt!= 0) {
                updateTotalAppliedAmount(amt);
                Float bal=getBalance();
                bal-=amt;
                setBalance(bal);
                getController().getDataController().mgmtBean.setBalance(bal);
            }
        }
        RequestContext rc=RequestContext.getCurrentInstance();
        rc.update("mainGrid:header:appliedAmt");
        rc.update("balance");
    }

    public void getDetail(NodeSelectEvent evt) {
        TreeRow item = (TreeRow) getSelectedNode().getData();
        int idx = root.getChildren().indexOf(getSelectedNode());
        orderList = getController().getDataController().getOrderList(item.getId());
        TreeRow row;
        for (AffiliateOrder order : orderList) {
            row = new TreeRow();
            row.setIsOrder(true);
            row.setIsBatch(false);
            row.setId(order.getId());
            row.setBatchId(item.getId());
            row.setCommission(order.getCommissionAsString());
            row.setIbv(order.getIbvTotal());
            row.setPayingId(order.getPayingId());
            row.setFilingId(order.getFilingId());
            row.setProcessDate(order.getOrderDate());
            row.setVendorOrderDate(order.getOrderDate());
            row.setOrderCount("1");
            row.setErrorCount(order.getErrorCount());
            row.setAppliedAmount(order.getCommissionAsString());
            row.setPsApproval(item.getPsApproval());
            row.setPsApprovalDate(item.getPsApprovalDate());
            row.setPsApprovalName(item.getPsApprovalName());
            row.setDescription(order.getVendorOrderNum());
            row.setNumber(order.getPayingId());
            row.setDivisionPlacement(order.getPlacementId());
            row.setMaOrderRef(order.getOrderRef());
            TreeNode parent = root.getChildren().get(idx);
            parent.getChildren().add(new DefaultTreeNode(row));
        }
    }

    public List<AffiliateOrder> getOrderList() {
        return orderList;
    }

    public Float getTotalAppliedAmount() {
        return this.totalAppliedAmount;
    }

    public void setTotalAppliedAmount(Float amt) {
        this.totalAppliedAmount = amt;
    }
    private void updateTotalAppliedAmount(Float amt) {
        this.totalAppliedAmount += amt;
    }

    public AffiliateOrder getAffiliateOrder(String id) {
        if (id == null || id.isEmpty()) {
            return new AffiliateOrder();
        }
        return getController().getAffiliateOrder(id);
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        if (root.getChildCount() == 0) {
            this.batchList= new ArrayList<>();
            this.batchList = new ArrayList<>(getController().getDataController().getBatchList());
            setData(batchList);
        }
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the selectedNode
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * @param selectedNode the selectedNode to set
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
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
     * @param batchList the batchList to set
     */
    public void setBatchList(ArrayList<BatchItem> batchList) {
        this.batchList = batchList;
    }

    /**
     * @return the selectedBatch
     */
    public BatchItem getSelectedBatch() {
        return selectedBatch;
    }

    /**
     * @param selectedBatch the selectedBatch to set
     */
    public void setSelectedBatch(BatchItem selectedBatch) {
        this.selectedBatch = selectedBatch;
    }

    /**
     * @return the controller
     */
    public AffiliateOrderController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(AffiliateOrderController controller) {
        this.controller = controller;
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("appliedAmt");
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(Float balance) {
        this.balance = balance;
        getController().getDataController().mgmtBean.setBalance(balance);
    }
}
