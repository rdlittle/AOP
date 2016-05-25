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
@Table(name = "enrollment")
@NamedQueries({
    @NamedQuery(name = "Enrollment.findAll", query = "SELECT e FROM Enrollment e"),
    @NamedQuery(name = "Enrollment.findByArn", query = "SELECT e FROM Enrollment e WHERE e.arn = :arn"),
    @NamedQuery(name = "Enrollment.findByLastName", query = "SELECT e FROM Enrollment e WHERE e.lastName = :lastName"),
    @NamedQuery(name = "Enrollment.findByFirstName", query = "SELECT e FROM Enrollment e WHERE e.firstName = :firstName"),
    @NamedQuery(name = "Enrollment.findByInitial", query = "SELECT e FROM Enrollment e WHERE e.initial = :initial"),
    @NamedQuery(name = "Enrollment.findByPrefix", query = "SELECT e FROM Enrollment e WHERE e.prefix = :prefix"),
    @NamedQuery(name = "Enrollment.findBySuffix", query = "SELECT e FROM Enrollment e WHERE e.suffix = :suffix"),
    @NamedQuery(name = "Enrollment.findByAddress1", query = "SELECT e FROM Enrollment e WHERE e.address1 = :address1"),
    @NamedQuery(name = "Enrollment.findByAddress2", query = "SELECT e FROM Enrollment e WHERE e.address2 = :address2"),
    @NamedQuery(name = "Enrollment.findByCity", query = "SELECT e FROM Enrollment e WHERE e.city = :city"),
    @NamedQuery(name = "Enrollment.findByState", query = "SELECT e FROM Enrollment e WHERE e.state = :state"),
    @NamedQuery(name = "Enrollment.findByZip", query = "SELECT e FROM Enrollment e WHERE e.zip = :zip"),
    @NamedQuery(name = "Enrollment.findByPhone1", query = "SELECT e FROM Enrollment e WHERE e.phone1 = :phone1"),
    @NamedQuery(name = "Enrollment.findByMemberId", query = "SELECT e FROM Enrollment e WHERE e.memberId = :memberId"),
    @NamedQuery(name = "Enrollment.findByEmail", query = "SELECT e FROM Enrollment e WHERE e.email = :email"),
    @NamedQuery(name = "Enrollment.findByAquisitionCode", query = "SELECT e FROM Enrollment e WHERE e.aquisitionCode = :aquisitionCode"),
    @NamedQuery(name = "Enrollment.findByFileDate", query = "SELECT e FROM Enrollment e WHERE e.fileDate = :fileDate"),
    @NamedQuery(name = "Enrollment.findByLineNum", query = "SELECT e FROM Enrollment e WHERE e.lineNum = :lineNum")})
public class Enrollment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "arn")
    private String arn;
    @Size(max = 20)
    @Column(name = "lastName")
    private String lastName;
    @Size(max = 15)
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "initial")
    private Character initial;
    @Size(max = 10)
    @Column(name = "prefix")
    private String prefix;
    @Size(max = 10)
    @Column(name = "suffix")
    private String suffix;
    @Size(max = 25)
    @Column(name = "address1")
    private String address1;
    @Size(max = 25)
    @Column(name = "address2")
    private String address2;
    @Size(max = 25)
    @Column(name = "city")
    private String city;
    @Size(max = 3)
    @Column(name = "state")
    private String state;
    @Size(max = 9)
    @Column(name = "zip")
    private String zip;
    @Size(max = 10)
    @Column(name = "phone1")
    private String phone1;
    @Size(max = 25)
    @Column(name = "memberId")
    private String memberId;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 120)
    @Column(name = "email")
    private String email;
    @Size(max = 6)
    @Column(name = "aquisitionCode")
    private String aquisitionCode;
    @Column(name = "fileDate")
    @Temporal(TemporalType.DATE)
    private Date fileDate;
    @Column(name = "lineNum")
    private Integer lineNum;

    public Enrollment() {
    }

    public Enrollment(String arn) {
        this.arn = arn;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Character getInitial() {
        return initial;
    }

    public void setInitial(Character initial) {
        this.initial = initial;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAquisitionCode() {
        return aquisitionCode;
    }

    public void setAquisitionCode(String aquisitionCode) {
        this.aquisitionCode = aquisitionCode;
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
        hash += (arn != null ? arn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enrollment)) {
            return false;
        }
        Enrollment other = (Enrollment) object;
        if ((this.arn == null && other.arn != null) || (this.arn != null && !this.arn.equals(other.arn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webfront.fnbo.Enrollment[ arn=" + arn + " ]";
    }
    
}
