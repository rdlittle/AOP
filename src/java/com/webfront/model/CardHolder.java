/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webfront.model;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author rlittle
 */
public class CardHolder {

    private String arn;
    private String memberId;
    private String firstName;
    private String lastName;
    private String middleInitial;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String emailAddress;
    private String phone1;
    private String prefix;
    private String suffix;
    private String acquisitionCode;
    private String fileDate;

    public CardHolder() {
        arn = "";
        memberId = "";
        firstName = "";
        lastName = "";
        middleInitial = "";
        address1 = "";
        address2 = "";
        city = "";
        state = "";
        zipCode = "";
        emailAddress = "";
        phone1 = "";
        prefix = "";
        suffix = "";
        acquisitionCode = "";
        fileDate = "";
    }

    /**
     * @return the arn
     */
    public String getArn() {
        return arn;
    }

    /**
     * @param arn the arn to set
     */
    public void setArn(String arn) {
        this.arn = arn;
    }

    /**
     * @return the memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the middleInitial
     */
    public String getMiddleInitial() {
        return middleInitial;
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (!firstName.isEmpty()) {
            sb.append(firstName);
            if (!middleInitial.isEmpty() || !lastName.isEmpty()) {
                sb.append(" ");
            }
        }
        if (!middleInitial.isEmpty()) {
            sb.append(middleInitial);
            if (!lastName.isEmpty()) {
                sb.append(" ");
            }
        }
        if (!lastName.isEmpty()) {
            sb.append(lastName);
        }
        return sb.toString();
    }

    /**
     * @param middleInitial the middleInitial to set
     */
    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    /**
     * @return the address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 the address1 to set
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 the address2 to set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZipCode() {
        String mask = "#####-####";
        String result = zipCode;
        MaskFormatter maskFormatter;
        try {
            maskFormatter = new MaskFormatter(mask);
            maskFormatter.setValueContainsLiteralCharacters(false);
            result = maskFormatter.valueToString(zipCode);
        } catch (ParseException ex) {
            Logger.getLogger(CardHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * @param zip the zip to set
     */
    public void setZipCode(String zip) {
        this.zipCode = zip;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the phone1
     */
    public String getPhone1() {
        String phoneMask = "###-###-####";
        String result = phone1;
        MaskFormatter maskFormatter;
        try {
            maskFormatter = new MaskFormatter(phoneMask);
            maskFormatter.setValueContainsLiteralCharacters(false);
            result = maskFormatter.valueToString(phone1);
        } catch (ParseException ex) {
            Logger.getLogger(CardHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * @param phone1 the phone1 to set
     */
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
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

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return the aquisitionCode
     */
    public String getAcquisitionCode() {
        return acquisitionCode;
    }

    /**
     * @param acquisitionCode the acquisitionCode to set
     */
    public void setAcquisitionCode(String acquisitionCode) {
        this.acquisitionCode = acquisitionCode;
    }

    /**
     * @return the fileDate
     */
    public String getFileDate() {
        return fileDate;
    }

    /**
     * @param fileDate the fileDate to set
     */
    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

}
