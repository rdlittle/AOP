/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

/**
 *
 * @author rlittle
 */
public class RunLevel {
    
    private String level;
    private String name;

    public RunLevel() {
        
    }
    public RunLevel(String rl) {
        this.level=rl;
    }
    public RunLevel(String l, String n) {
        this.level=l;
        this.name=n;
    }
    /**
     * @return the runLevel
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param runLevel the runLevel to set
     */
    public void setLevel(String runLevel) {
        this.level = runLevel;
    }

    /**
     * @return the runLevelName
     */
    public String getName() {
        return name;
    }

    /**
     * @param runLevelName the runLevelName to set
     */
    public void setName(String runLevelName) {
        this.name = runLevelName;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RunLevel)) {
            return false;
        }
        RunLevel other = (RunLevel) obj;
        return (this.level != null || other.level == null) && (this.level == null || this.level.equals(other.level));        
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
}
