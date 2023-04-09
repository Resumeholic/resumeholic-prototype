/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anthony Than Poovong, Caleb Lam
 */
@XmlRootElement(name = "header")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalInfoHeader {
    int id, resumeId;
    String firstName, lastName, email, phoneNumber;

    public PersonalInfoHeader(int id, int resumeId, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.resumeId = resumeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    public PersonalInfoHeader() {
        this.id = 0;
        this.resumeId = 0;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phoneNumber = "";
    }

    public int getId() {
        return id;
    }

    public int getResumeId() {
        return resumeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
