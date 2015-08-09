/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import asjava.uniclientlibs.UniDynArray;
import com.webfront.model.SelectItem;
import com.webfront.model.UnitTestSuite;
import com.webfront.model.UnitTestSuiteSegment;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author rlittle
 */
@SessionScoped
@Named("utSuiteBean")
public class TestSuiteBean implements Serializable {

    private UnitTestSuite testSuite;
    private final ArrayList<SelectItem> suiteIdList;
    private String utSuiteId;
    private final WebDE webde = new WebDE();
    private boolean isNewSuite;
    private boolean changed;
    private boolean isNewItem;
    private UnitTestSuiteSegment segment;
    private ArrayList<UnitTestSuiteSegment> selectedRows;

    public TestSuiteBean() {
        testSuite = new UnitTestSuite();
        suiteIdList = new ArrayList<>();
        utSuiteId = "";
        isNewSuite = false;
        segment = new UnitTestSuiteSegment();
//        suiteSelector = new SelectOneMenu();
    }

    public void newSuite() {
        testSuite = new UnitTestSuite();
        Date d = Calendar.getInstance(Locale.getDefault()).getTime();
        SimpleDateFormat dfmt = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat tfmt = new SimpleDateFormat("HH:mm:ss");
        testSuite.setUtDate(dfmt.format(d));
        testSuite.setUtTime(tfmt.format(d));
        utSuiteId = "";
        setChanged(true);
    }

    public void newItem() {
        if (!"".equals(segment.getUtAcctName())) {
            if (!"".equals(segment.getUtFileName())) {
                if (!"".equals(segment.getUtProgName())) {
                    ArrayList<UnitTestSuiteSegment> list = testSuite.getUtSegment();
                    list.add(segment);
                    testSuite.setUtSegment(list);
                    setIsNewItem(false);
                    setChanged(true);
                    segment = new UnitTestSuiteSegment();
                }
            }
        } else {
            setIsNewItem(true);
        }
    }

    public void save() {
        try {
            webde.getInProperties().clear();
            webde.setAccountName("WDE");
            webde.setModuleName("Test");
            webde.setClassName("Suite");
            webde.getInProperties().put("utSuiteId", new UniDynArray(utSuiteId));
            webde.getInProperties().put("utTitle", new UniDynArray(testSuite.getUtTitle()));
            webde.getInProperties().put("utDesc", new UniDynArray(testSuite.getUtDesc()));
            webde.getInProperties().put("utDate", new UniDynArray(testSuite.utDateAsString()));
            webde.getInProperties().put("utTime", new UniDynArray(testSuite.getUtTime()));
            UniDynArray segs = new UniDynArray();
            int val = 1;
            for (UnitTestSuiteSegment utSegment : testSuite.getUtSegment()) {
                segs.insert(1, val, utSegment.getUtAcctName());
                segs.insert(2, val, utSegment.getUtFileName());
                segs.insert(3, val, utSegment.getUtProgName());
            }
            webde.getInProperties().put("utAccount", segs.extract(1));
            webde.getInProperties().put("utFileName", segs.extract(2));
            webde.getInProperties().put("utProgName", segs.extract(3));
            webde.setMethodName("setUnitTestSuite");
            webde.call();
            if (webde.errStatus == -1) {
                String msg = "Error: [" + webde.errCode + "] " + webde.errMessage;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
            } else {
                String uid = webde.getObjectMap().get("utSuiteId").toString();
                String title = testSuite.getUtTitle();
                if(isNewSuite) {
                    suiteIdList.add(new SelectItem(uid,title));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TestSuiteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        setChanged(false);
    }

    public void delete() {
        if (utSuiteId != null && !"".equals(utSuiteId) && !"-1".equals(utSuiteId)) {
            try {
                webde.setAccountName("WDE");
                webde.setModuleName("Test");
                webde.setClassName("Suite");
                webde.getInProperties().put("utSuiteId", new UniDynArray(utSuiteId));
                webde.setMethodName("deleteUnitTestSuite");
                webde.call();
                if (webde.errStatus == -1) {
                    String msg = "Error: [" + webde.errCode + "] " + webde.errMessage;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
                } else {
                    for(SelectItem se : suiteIdList) {
                        if(utSuiteId.equals(se.getKey())) {
                            suiteIdList.remove(se);
                            break;
                        }
                    }
                    String msg = "UT.SUITE "+utSuiteId+" deleted";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", msg));
                }
            } catch (Exception ex) {
                Logger.getLogger(TestSuiteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        newSuite();
        setChanged(false);
    }

    public void setSuite() {
        if (utSuiteId != null && !"".equals(utSuiteId) && !"-1".equals(utSuiteId)) {
            try {
                webde.setAccountName("WDE");
                webde.setModuleName("Test");
                webde.setClassName("Suite");
                webde.getInProperties().put("utSuiteId", new UniDynArray(utSuiteId));
                webde.setMethodName("getUnitTestSuite");
                webde.call();
                if (webde.errStatus == -1) {
                    String msg = "Error: [" + webde.errCode + "] " + webde.errMessage;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
                } else {
                    testSuite = new UnitTestSuite();
                    testSuite.setUtSuiteId(utSuiteId);
                    testSuite.setUtDate(webde.getObjectMap().get("utDate").extract(1, 1).toString());
                    testSuite.setUtTime(webde.getObjectMap().get("utTime").extract(1, 1).toString());
                    testSuite.setUtTitle(webde.getObjectMap().get("utTitle").extract(1, 1).toString());
                    testSuite.setUtDesc(webde.getObjectMap().get("utDesc").extract(1, 1).toString());
                    UniDynArray segAccount = webde.getObjectMap().get("utAccount");
                    UniDynArray segFile = webde.getObjectMap().get("utFileName");
                    UniDynArray segProg = webde.getObjectMap().get("utProgName");
                    int segCount = segProg.dcount(1);
                    for (int seg = 1; seg <= segCount; seg++) {
                        String a = segAccount.extract(1, seg).toString();
                        String f = segFile.extract(1, seg).toString();
                        String p = segProg.extract(1, seg).toString();
                        testSuite.addUtSegment(new UnitTestSuiteSegment(a, f, p));
                    }
                    setChanged(false);
                }
            } catch (Exception ex) {
                Logger.getLogger(TestSuiteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the testSuite
     */
    public UnitTestSuite getTestSuite() {
        return testSuite;
    }

    /**
     * @param ts the testSuite to set
     */
    public void setTestSuite(UnitTestSuite ts) {
        this.testSuite = ts;
    }

    /**
     * @return the suiteIdList
     */
    public ArrayList<SelectItem> getSuiteIdList() {
        if (suiteIdList.isEmpty()) {
            setSuiteIdList(null);
        }
        return suiteIdList;
    }

    /**
     * @param list the suiteIdList to set
     */
    public void setSuiteIdList(ArrayList<SelectItem> list) {
        try {
            suiteIdList.clear();
            webde.setAccountName("WDE");
            webde.setModuleName("Test");
            webde.setClassName("Suite");
            webde.setMethodName("getUnitTestSuite");
            webde.call();
            UniDynArray idList = webde.getObjectMap().get("utSuiteId");
            UniDynArray titleList = webde.getObjectMap().get("utTitle");
            int count = idList.dcount(1);
            for (int i = 1; i <= count; i++) {
                String id = idList.extract(1, i).toString();
                String title = titleList.extract(1, i).toString();
                SelectItem se = new SelectItem(id, title);
                if (!this.suiteIdList.contains(se)) {
                    this.suiteIdList.add(se);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TestSuiteBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the utSuiteId
     */
    public String getUtSuiteId() {
        return utSuiteId;
    }

    /**
     * @param utSuiteId the utSuiteId to set
     */
    public void setUtSuiteId(String utSuiteId) {
        this.utSuiteId = utSuiteId;
    }

    /**
     * @return the isNew
     */
    public boolean isIsNewSuite() {
        return isNewSuite;
    }

    /**
     * @param isNew the isNew to set
     */
    public void setIsNewSuite(boolean isNew) {
        this.isNewSuite = isNew;
    }

    /**
     * @return the isNewItem
     */
    public boolean isIsNewItem() {
//        System.out.println("utSuiteBean.getIsNewItem(" + isNewItem + ")");
        return isNewItem;
    }

    /**
     * @param isNewItem the isNewItem to set
     */
    public void setIsNewItem(boolean isNewItem) {
//        System.out.println("utSuiteBean.setIsNewItem(" + isNewItem + ")");
        this.isNewItem = isNewItem;
    }

    /**
     * @return the segment
     */
    public UnitTestSuiteSegment getSegment() {
//        System.out.println("utSuiteBean.getSegment()");
        return segment;
    }

    /**
     * @param segment the segment to set
     */
    public void setSegment(UnitTestSuiteSegment segment) {
//        System.out.println("utSuiteBean.setSegment()");
        this.segment = segment;
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
     * @return the selectedRows
     */
    public ArrayList<UnitTestSuiteSegment> getSelectedRows() {
        return selectedRows;
    }

    /**
     * @param selectedRows the selectedRows to set
     */
    public void setSelectedRows(ArrayList<UnitTestSuiteSegment> selectedRows) {
        this.selectedRows = selectedRows;
    }
    
}
