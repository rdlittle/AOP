/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.util.ArrayList;

/**
 *
 * @author rlittle
 */
public class AffiliateNetwork {
    private String networkId;
    private String networkName;
    private String networkCountry;
    private String prefix;
    
    public AffiliateNetwork() {
        networkId = "";
        networkName = "";
        networkCountry = "";
        prefix = "";
    }

    /**
     * @return the networkId
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @param networkId the networkId to set
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * @return the networkName
     */
    public String getNetworkName() {
        return networkName;
    }

    /**
     * @param networkName the networkName to set
     */
    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    /**
     * @return the networkCountry
     */
    public String getNetworkCountry() {
        return networkCountry;
    }

    /**
     * @param networkCountry the networkCountry to set
     */
    public void setNetworkCountry(String networkCountry) {
        this.networkCountry = networkCountry;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
