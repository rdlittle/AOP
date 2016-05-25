/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.fnbo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author rlittle
 */
@Entity
@Table(name = "award")
@NamedQueries({
    @NamedQuery(name = "Award.findAll", query = "SELECT a FROM Award a"),
    @NamedQuery(name = "Award.findByOrderId", query = "SELECT a FROM Award a WHERE a.orderId = :orderId"),
    @NamedQuery(name = "Award.findByOrderSrp", query = "SELECT a FROM Award a WHERE a.orderSrp = :orderSrp"),
    @NamedQuery(name = "Award.findByAwardAmt", query = "SELECT a FROM Award a WHERE a.awardAmt = :awardAmt"),
    @NamedQuery(name = "Award.findByUik", query = "SELECT a FROM Award a WHERE a.uik = :uik")})
public class Award implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "orderId")
    private String orderId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "orderSrp")
    private Float orderSrp;
    @Column(name = "awardAmt")
    private Float awardAmt;
    @Size(max = 31)
    @Column(name = "uik")
    private String uik;

    public Award() {
    }

    public Award(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Float getOrderSrp() {
        return orderSrp;
    }

    public void setOrderSrp(Float orderSrp) {
        this.orderSrp = orderSrp;
    }

    public Float getAwardAmt() {
        return awardAmt;
    }

    public void setAwardAmt(Float awardAmt) {
        this.awardAmt = awardAmt;
    }

    public String getUik() {
        return uik;
    }

    public void setUik(String uik) {
        this.uik = uik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Award)) {
            return false;
        }
        Award other = (Award) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webfront.fnbo.Award[ orderId=" + orderId + " ]";
    }
    
}
