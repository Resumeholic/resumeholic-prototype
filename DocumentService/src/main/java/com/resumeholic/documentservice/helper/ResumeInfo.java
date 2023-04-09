/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.documentservice.helper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Anthony Than Poovong, Caleb Lam
 */
@XmlRootElement(name = "resume")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResumeInfo {

    int id, authorId;
    String documentName;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    ZonedDateTime createdDate, updatedDate;
    
    public ResumeInfo() {
        this.id = 0;
        this.authorId = 0;
        this.documentName = "";
        this.createdDate = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.of("UTC"));
        this.updatedDate = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.of("UTC"));
    }

    public ResumeInfo(int id, int authorId, String documentName, ZonedDateTime createdDate, ZonedDateTime updatedDate) {
        this.id = id;
        this.authorId = authorId;
        this.documentName = documentName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public int getId() {
        return id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    
    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
