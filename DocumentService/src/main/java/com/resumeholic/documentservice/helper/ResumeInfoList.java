/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.documentservice.helper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author student
 */
@XmlRootElement(name = "resumes")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResumeInfoList {
    @XmlElement(name = "resume")
    private List<ResumeInfo> resumes = new ArrayList<>();

    public ResumeInfoList() {}
    
    public ResumeInfoList(List<ResumeInfo> resumes) {
        this.resumes = resumes;
    }
    
    public List<ResumeInfo> getResumes() {
        return resumes;
    }

    public void setResumes(List<ResumeInfo> resumes) {
        this.resumes = resumes;
    }
    
    
}
