/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.fnbo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author rlittle
 */
@Entity
@Table(name = "transaction")
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t"),
    @NamedQuery(name = "Transaction.findByUik", query = "SELECT t FROM Transaction t WHERE t.uik = :uik"),
    @NamedQuery(name = "Transaction.findByTransDate", query = "SELECT t FROM Transaction t WHERE t.transDate = :transDate"),
    @NamedQuery(name = "Transaction.findByArn", query = "SELECT t FROM Transaction t WHERE t.arn = :arn"),
    @NamedQuery(name = "Transaction.findByMemberId", query = "SELECT t FROM Transaction t WHERE t.memberId = :memberId"),
    @NamedQuery(name = "Transaction.findByMerchType", query = "SELECT t FROM Transaction t WHERE t.merchType = :merchType"),
    @NamedQuery(name = "Transaction.findByTransAmt", query = "SELECT t FROM Transaction t WHERE t.transAmt = :transAmt"),
    @NamedQuery(name = "Transaction.findByCardType", query = "SELECT t FROM Transaction t WHERE t.cardType = :cardType"),
    @NamedQuery(name = "Transaction.findByMerchName", query = "SELECT t FROM Transaction t WHERE t.merchName = :merchName"),
    @NamedQuery(name = "Transaction.findByReferenceNum", query = "SELECT t FROM Transaction t WHERE t.referenceNum = :referenceNum"),
    @NamedQuery(name = "Transaction.findByTransType", query = "SELECT t FROM Transaction t WHERE t.transType = :transType"),
    @NamedQuery(name = "Transaction.findByOriginalOrder", query = "SELECT t FROM Transaction t WHERE t.originalOrder = :originalOrder"),
    @NamedQuery(name = "Transaction.findByFileDate", query = "SELECT t FROM Transaction t WHERE t.fileDate = :fileDate"),
    @NamedQuery(name = "Transaction.findByLineNum", query = "SELECT t FROM Transaction t WHERE t.lineNum = :lineNum")})
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 31)
    @Column(name = "uik")
    private String uik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transDate")
    @Temporal(TemporalType.DATE)
    private Date transDate;
    @Size(max = 16)
    @Column(name = "arn")
    private String arn;
    @Size(max = 9)
    @Column(name = "memberId")
    private String memberId;
    @Column(name = "merchType")
    private Character merchType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "transAmt")
    private Float transAmt;
    @Column(name = "cardType")
    private Character cardType;
    @Size(max = 25)
    @Column(name = "merchName")
    private String merchName;
    @Size(max = 25)
    @Column(name = "referenceNum")
    private String referenceNum;
    @Size(max = 2)
    @Column(name = "transType")
    private String transType;
    @Size(max = 9)
    @Column(name = "originalOrder")
    private String originalOrder;
    @Column(name = "fileDate")
    @Temporal(TemporalType.DATE)
    private Date fileDate;
    @Column(name = "lineNum")
    private Integer lineNum;

    public Transaction() {
    }

    public Transaction(String uik) {
        this.uik = uik;
    }

    public Transaction(String uik, Date transDate) {
        this.uik = uik;
        this.transDate = transDate;
    }

    public String getUik() {
        return uik;
    }

    public void setUik(String uik) {
        this.uik = uik;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Character getMerchType() {
        return merchType;
    }

    public void setMerchType(Character merchType) {
        this.merchType = merchType;
    }

    public Float getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(Float transAmt) {
        this.transAmt = transAmt;
    }

    public Character getCardType() {
        return cardType;
    }

    public void setCardType(Character cardType) {
        this.cardType = cardType;
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }

    public String getReferenceNum() {
        return referenceNum;
    }

    public void setReferenceNum(String referenceNum) {
        this.referenceNum = referenceNum;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getOriginalOrder() {
        return originalOrder;
    }

    public void setOriginalOrder(String originalOrder) {
        this.originalOrder = originalOrder;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uik != null ? uik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.uik == null && other.uik != null) || (this.uik != null && !this.uik.equals(other.uik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webfront.fnbo.Transaction[ uik=" + uik + " ]";
    }
    
}
