/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.util.Objects;

/**
 *
 * @author rlittle
 */
public class QueueItem {
    private String queueType;
    private String queueDesc;
    private String queueGroup;
    
    public QueueItem() {
        queueType = "";
        queueDesc = "";
        queueGroup = "";
    }
    
    public QueueItem(String t, String d, String g) {
        queueType = t;
        queueDesc = d;
        queueGroup = g;
    }

    /**
     * @return the queueType
     */
    public String getQueueType() {
        return queueType;
    }

    /**
     * @param queueType the queueType to set
     */
    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    /**
     * @return the queueDesc
     */
    public String getQueueDesc() {
        return queueDesc;
    }

    /**
     * @param queueDesc the queueDesc to set
     */
    public void setQueueDesc(String queueDesc) {
        this.queueDesc = queueDesc;
    }

    /**
     * @return the queueGroup
     */
    public String getQueueGroup() {
        return queueGroup;
    }

    /**
     * @param queueGroup the queueGroup to set
     */
    public void setQueueGroup(String queueGroup) {
        this.queueGroup = queueGroup;
    }
    
    @Override
    public String toString() {
        return queueDesc;
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode += queueDesc.hashCode()+queueType.hashCode();
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueueItem other = (QueueItem) obj;
        if (!Objects.equals(this.queueType, other.queueType)) {
            return false;
        }
        if (!Objects.equals(this.queueDesc, other.queueDesc)) {
            return false;
        }
        return true;
    }
}
