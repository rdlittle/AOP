/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.beans;

import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author rlittle
 */
@ManagedBean
@ViewScoped
public class MonitorRequestBean {

    private String projectManager;
    private String developer;
    private String startDate;
    private String progName;
    private String progFunction;
    private ArrayList<String> dependencies;
    private ArrayList<String> files;
    private ArrayList<String> statements;
    private ArrayList<Program> calledPrograms;
    private ArrayList<Program> phantoms;
    private ArrayList<String> errors;
    private ArrayList<String> resolution;
    private String runTime;
    private String runDays;
    private String startTime;
    private String runFrequency;
    private String recoveryProcedure;
    private String successNotification;
    private String errorNotification;

    private String[] fields;
    private final int FIELD_COUNT = 19;

    public MonitorRequestBean() {
        projectManager = "";
        developer = "";
        startDate = "";
        progName = "";
        progFunction = "";
        dependencies = new ArrayList<>();
        files = new ArrayList<>();
        statements = new ArrayList<>();
        calledPrograms = new ArrayList<>();
        phantoms = new ArrayList<>();
        errors = new ArrayList<>();
        resolution = new ArrayList<>();
        runTime = "";
        runDays = "";
        startTime = "";
        runFrequency = "";
        recoveryProcedure = "";
        successNotification = "";
        errorNotification = "";
        
        fields = new String[FIELD_COUNT];
        fields[0] = "Project manager";
        fields[1] = "Developer";
        fields[2] = "Start date";
        fields[3] = "Program name";
        fields[4] = "Program function";
        fields[5] = "Dependencies";
        fields[6] = "Files updated";
        fields[7] = "Perform/Execute statements";
        fields[8] = "Called programs";
        fields[9] = "Phantoms";
        fields[10] = "Error conditions";
        fields[11] = "Error resolution";
        fields[12] = "Run time";
        fields[13] = "Run days";
        fields[14] = "Start time";
        fields[15] = "Run frequency";
        fields[16] = "Recovery procedure";
        fields[17] = "Completion notifications to";
        fields[18] = "Error notifications to";
    }
    
    public String doReport() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        Map<String,String> map = ectx.getRequestParameterMap();
        if(!map.containsKey("projectManager")) {
            map.put("projectManager", projectManager);
        }
        return "/printdoc?faces-redirect=true";
    }

    public String[] getReport() {
        int sz = FIELD_COUNT;
        String report[] = new String[sz];
        for (int i = 0; i < sz; i++) {
            String value="";
            
            switch (i) {
                case 0:
                    value = projectManager;
                    break;
                case 1:
                    value = developer;
                    break;                    
                case 2:
                    value = startDate;
                    break;                                        
                case 3:
                    value = progName;
                    break;                                        
                case 4:
                    value = progFunction;
                    break;                                        
                case 5:
                    for(String s : dependencies) {
                        value = s+"\\n";
                    }
                    break;                                        
                case 6:
                    for(String s : files) {
                        value = s+"\\n";
                    }
                    break;
                case 7:
                    for(String s : statements) {
                        value = s+"\\n";
                    }
                    break;                    
                case 8:
                    for(Program p : calledPrograms) {
                        value = p.getName()+" "+p.getDescription();
                    }
                    break;                    
                case 9:
                    for(Program p : phantoms) {
                        value = p.getName()+" "+p.getDescription();
                    }
                    break;                    
                case 10:
                    for(String s : errors) {
                        value = s+"\n";
                    }
                    break;                    
                case 11:
                    for(String s : resolution) {
                        value = s+"\n";
                    }
                    break;                    
                case 12:
                    value = runTime;
                    break;                    
                case 13:
                    value = runDays;
                    break;                    
                case 14:
                    value = startTime;
                    break;                    
                case 15:
                    value = runFrequency;
                    break;                    
                case 16:
                    value = recoveryProcedure;
                    break;                    
                case 17:
                    value = successNotification;
                    break;                    
                case 18:
                    value = errorNotification;
                    break;
                default:
                    value = "";
            }
            report[i] = fields[i]+": "+value;
            System.out.println(report[i]);
        }
        return report;
    }

    public void addSub() {
//        calledPrograms.add(new Program("name","description"));
        calledPrograms.add(new Program());
    }
    
    public void onSubEdit(RowEditEvent event) {
    }
    
    public void onSubCancelEdit(RowEditEvent evt) {
        
    }
    
    public void onPhantomEdit(RowEditEvent evt) {
        
    }
    
    public void onPhantomCancelEdit(RowEditEvent evt) {
        
    }
    
    public void addPhantom() {
        phantoms.add(new Program());
    }
    /**
     * @return the projectManager
     */
    public String getProjectManager() {
        return projectManager;
    }

    /**
     * @param projectManager the projectManager to set
     */
    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    /**
     * @return the developer
     */
    public String getDeveloper() {
        return developer;
    }

    /**
     * @param developer the developer to set
     */
    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the progName
     */
    public String getProgName() {
        return progName;
    }

    /**
     * @param progName the progName to set
     */
    public void setProgName(String progName) {
        this.progName = progName;
    }

    /**
     * @return the progFunction
     */
    public String getProgFunction() {
        return progFunction;
    }

    /**
     * @param progFunction the progFunction to set
     */
    public void setProgFunction(String progFunction) {
        this.progFunction = progFunction;
    }

    /**
     * @return the dependencies
     */
    public ArrayList<String> getDependencies() {
        return dependencies;
    }

    /**
     * @param dependencies the dependencies to set
     */
    public void setDependencies(ArrayList<String> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * @return the files
     */
    public ArrayList<String> getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }

    /**
     * @return the statements
     */
    public ArrayList<String> getStatements() {
        return statements;
    }

    /**
     * @param statements the statements to set
     */
    public void setStatements(ArrayList<String> statements) {
        this.statements = statements;
    }

    /**
     * @return the calledPrograms
     */
    public ArrayList<Program> getCalledPrograms() {
        return calledPrograms;
    }

    /**
     * @param calledPrograms the calledPrograms to set
     */
    public void setCalledPrograms(ArrayList<Program> calledPrograms) {
        this.calledPrograms = calledPrograms;
    }

    /**
     * @return the phantoms
     */
    public ArrayList<Program> getPhantoms() {
        return phantoms;
    }

    /**
     * @param phantoms the phantoms to set
     */
    public void setPhantoms(ArrayList<Program> phantoms) {
        this.phantoms = phantoms;
    }

    /**
     * @return the errors
     */
    public ArrayList<String> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    /**
     * @return the resolution
     */
    public ArrayList<String> getResolution() {
        return resolution;
    }

    /**
     * @param resolution the resolution to set
     */
    public void setResolution(ArrayList<String> resolution) {
        this.resolution = resolution;
    }

    /**
     * @return the runTime
     */
    public String getRunTime() {
        return runTime;
    }

    /**
     * @param runTime the runTime to set
     */
    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    /**
     * @return the runDays
     */
    public String getRunDays() {
        return runDays;
    }

    /**
     * @param runDays the runDays to set
     */
    public void setRunDays(String runDays) {
        this.runDays = runDays;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the runFrequency
     */
    public String getRunFrequency() {
        return runFrequency;
    }

    /**
     * @param runFrequency the runFrequency to set
     */
    public void setRunFrequency(String runFrequency) {
        this.runFrequency = runFrequency;
    }

    /**
     * @return the recoveryProcedure
     */
    public String getRecoveryProcedure() {
        return recoveryProcedure;
    }

    /**
     * @param recoveryProcedure the recoveryProcedure to set
     */
    public void setRecoveryProcedure(String recoveryProcedure) {
        this.recoveryProcedure = recoveryProcedure;
    }

    /**
     * @return the successNotification
     */
    public String getSuccessNotification() {
        return successNotification;
    }

    /**
     * @param successNotification the successNotification to set
     */
    public void setSuccessNotification(String successNotification) {
        this.successNotification = successNotification;
    }

    /**
     * @return the errorNotification
     */
    public String getErrorNotification() {
        return errorNotification;
    }

    /**
     * @param errorNotification the errorNotification to set
     */
    public void setErrorNotification(String errorNotification) {
        this.errorNotification = errorNotification;
    }

    public class Program {

        private String name;
        private String description;

        public Program() {
            name = "";
            description = "";
        }

        public Program(String n, String d) {
            name = n;
            description = d;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
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

    }
}
