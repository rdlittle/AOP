/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.controller;

import asjava.uniclientlibs.UniDynArray;
import com.rs.u2.wde.redbeans.RbException;
import com.rs.u2.wde.redbeans.RedObject;
import com.webfront.model.AffiliateError;
import com.webfront.model.AffiliateOrder;
import com.webfront.model.ErrorControl;
import com.webfront.u2.DynArray;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author rlittle
 */
public class AffiliateOrderController {

    private DataController controller;
    ErrorControl errorMsgs;

    public AffiliateOrderController() {
        errorMsgs = new ErrorControl();
        init();
    }

    @PostConstruct
    private void init() {
        controller = new DataController();
        try {
            UniDynArray uda = controller.getFileItem("PARAMS", "AOP.ERROR.CONTROL", "");
            errorMsgs.setControl(uda);
        } catch (RbException ex) {
            Logger.getLogger(AffiliateOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataController getDataController() {
        return this.controller;
    }

    public AffiliateOrder getAffiliateOrder(String id) {
        AffiliateOrder ao = new AffiliateOrder();
        try {
            getDataController().setRbo(new RedObject("WDE", "AOP:AffiliateOrders"));
            RedObject r = getDataController().getRbo();
            r.setProperty("orderId", id);
            r.callMethod("getOrderDetail");
            ao.setId(id);
            ao.setCommissionTotal(Float.valueOf(r.getProperty("commissionTotal")));
            ao.setErrorCount(r.getProperty("errorCount"));
            ao.setFilingId(r.getProperty("filingId"));
            ao.setPayingId(r.getProperty("payingId"));
            ao.setPayingIdName(r.getProperty("payingIdName"));
            ao.setIbvTotal(r.getProperty("ibvTotal"));
            ao.setOrderDate(r.getProperty("orderDate"));
            ao.setOrderRef(r.getProperty("orderRef"));
            ao.setPlacementId(r.getProperty("placementId"));
            ao.setBatchId(r.getProperty("batchId"));
            ao.setPayingIdAddr1(r.getProperty("payingIdAddr1"));
            ao.setPayingIdAddr2(r.getProperty("payingIdAddr2"));
            ao.setPayingIdCityStZip(r.getProperty("payingIdCityStZip"));
            ao.setStoreId(r.getProperty("storeId"));
            ao.setStoreName(r.getProperty("storeName"));
            ao.setErrorCount(r.getProperty("errorCount"));
            ao.setErrorMessage(r.getProperty("errorMessage"));
            ao.setEntryDate(r.getProperty("entryDate"));
            ao.setVendorOrderNum(r.getProperty("vendorOrderNum"));
            ao.setVendorOrderDate(r.getProperty("vendorOrderDate"));
            ao.setVendorDiv(r.getProperty("vendorDiv"));
            ao.setVendorId(r.getProperty("vendorId"));
            int errCount = Integer.parseInt(ao.getErrorCount());
            ao.setHasErrors(errCount > 0);
            ao.setActualPlacementId1(r.getProperty("actualPlacmentId1"));
            ao.setActualPlacementId2(r.getProperty("actualPlacmentId2"));
            ao.setIbvPlacedAmt1(Float.valueOf(r.getProperty("ibvPlacedAmt1")));
            ao.setIbvPlacedAmt2(Float.valueOf(r.getProperty("ibvPlacedAmt2")));
            if (errCount > 0) {
                UniDynArray uda = r.getPropertyToDynArray("affiliateErrorsId");
                for (int e = 1; e <= errCount; e++) {
                    String aeId = uda.extract(1, e).toString();
                    AffiliateError aeRec = getAffiliateError(aeId);
                    if (aeRec != null) {
                        ao.getErrorList().add(aeRec);
                    }
                }
            }
        } catch (RbException ex) {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ao;
    }

    public String setAffiliateOrder(String orderId, AffiliateOrder order) {
        try {
            getDataController().setRbo(new RedObject("WDE", "AOP:AffiliateOrders"));
            RedObject r = getDataController().getRbo();
            r.setProperty("orderId", orderId);
            r.setProperty("payingId", order.getPayingId());
            r.callMethod("setAffiliateOrder");
            String errStat = r.getProperty("errStat");
            String errCode = r.getProperty("errCode");
            String errMsg = r.getProperty("errMsg");
            if (!errStat.isEmpty() || errStat.equals("-1")) {
                FacesMessage msg = new FacesMessage(errCode + " " + errMsg);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            }
        } catch (RbException ex) {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public AffiliateError getAffiliateError(String id) throws RbException {
        getDataController().setRbo(new RedObject("WDE", "UTILS:Files"));
        RedObject r = getDataController().getRbo();
        r.setProperty("fileName", "AFFILIATE.ERRORS");
        r.setProperty("id", id);
        r.setProperty("mustExist", "1");
        r.callMethod("getFileRec");
        String errStat = r.getProperty("errStat");
        String errCode = r.getProperty("errCode");
        String errMsg = r.getProperty("errMsg");
        if (errStat.equals("-1")) {
            throw new RbException(Integer.parseInt(errCode), errMsg);
        } else {
            UniDynArray uda;
            UniDynArray udaRaised;
            uda = r.getPropertyToDynArray("fileRec");
            udaRaised = new UniDynArray(DynArray.raise(uda));
            AffiliateError errObj = new AffiliateError(uda);
            int vals = udaRaised.dcount(58);
            vals = uda.dcount(1, 58);
            for (int val = 1; val <= vals; val++) {
                errCode = uda.extract(1, 59, val).toString();
                errMsg = uda.extract(1, 58, val).toString();
                errObj.getErrorMap().put(errCode, errMsg);
            }
            errObj.setId(id);
            String ln = id.substring(0, id.indexOf("*"));
            errObj.setLineNumber(ln);
            return errObj;
        }
    }

}
