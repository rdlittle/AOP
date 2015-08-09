/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import asjava.uniclientlibs.UniDynArray;
import java.util.HashMap;

/**
 *
 * @author rlittle
 */
public class ErrorControl {
    UniDynArray control;
    private HashMap<String,String> message;    
    
    public ErrorControl() {
        this.control = new UniDynArray();
        this.message = new HashMap<>();
    }
    
    public void setControl(UniDynArray uda) {
        this.control=uda;
        int values = uda.count(1);
        for (int value = 1; value <= values; value++) {
            String errNum = uda.extract(1, value).toString();
            String errMsg = uda.extract(2, value).toString();
            message.put(errNum,errMsg);
        }
    }
    
    public String getMessage(String errorCode) {
        if(message.containsKey(errorCode)) {
            return message.get(errorCode);
        }
        return "Unknown error occurred";
    }

    /**
     * @return the message
     */
    public HashMap<String,String> getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(HashMap<String,String> message) {
        this.message = message;
    }
}
