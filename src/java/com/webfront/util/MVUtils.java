/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.util;

import asjava.uniclientlibs.UniDynArray;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author rlittle
 */
public class MVUtils {
    
    /**
     * 
     * @param uda A single multi-valued string
     * @return ArrayList of StringS
     */
    public static ArrayList<String> toArrayList(UniDynArray uda) {
        ArrayList<String> list = new ArrayList<>();
        int count = uda.dcount(1);
        for (int val=1; val<=count; val++) {
            list.add(uda.extract(1, val).toString());
        }
        return list;
    }

    /**
     * 
     * @param uda A dynamic array with at least two fields
     * @param k The field number to use for the "key"S of the HashMap
     * @param v The field number containing the "value"S of the HashMap
     * @return a HashMap of "key/value" pairs
     */
    public static HashMap<String,String> toKeyValue(UniDynArray uda, int k, int v) {
        HashMap<String,String> map = new HashMap<>();
        int vals = uda.dcount(k);
        for(int val=1; val<=vals; val++) {
            map.put(uda.extract(k,val).toString(), uda.extract(v, val).toString());
        }
        return map;
    }
    
    public static UniDynArray toMVArray(ArrayList<String> list) {
        UniDynArray uda = new UniDynArray();
        int val = 1;
        for(String s : list) {
            uda.insert(1, val, s);
            val++;
        }
        return uda;
    }
    
    public static UniDynArray toMVArray(ArrayList<String> list, int fieldNum) {
        UniDynArray uda = new UniDynArray();
        if(fieldNum==0) {
            fieldNum=1;
        }
        int val = 1;
        for(String s : list) {
            uda.insert(fieldNum, val, s);
            val++;
        }
        return uda;
    }
    
    public static UniDynArray toSVArray(ArrayList<String> list) {
        UniDynArray uda = new UniDynArray();
        int attr = 1;
        for(String s : list) {
            uda.insert(attr, s);
            attr++;
        }
        return uda;        
    }
    
}
