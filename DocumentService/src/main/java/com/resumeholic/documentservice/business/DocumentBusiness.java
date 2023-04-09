/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.documentservice.business;

import com.resumeholic.documentservice.helper.ResumeInfo;
import com.resumeholic.documentservice.helper.ResumeInfoList;
import com.resumeholic.documentservice.persistence.ResumeCRUD;
import java.util.List;

/**
 *
 * @author Caleb Lam
 */
public class DocumentBusiness {
    public static ResumeInfoList getResumes(int authorId) {
        return ResumeCRUD.readAll(authorId);
    }
    
    public static ResumeInfo getResume(int resumeId) {
        return ResumeCRUD.read(resumeId);
    }
    
    public static ResumeInfo createResume(int authorId, String documentName) {
        return ResumeCRUD.create(authorId, documentName);
    }
    
    public static boolean updateResume(int resumeId, String documentName) {
        return ResumeCRUD.update(resumeId, documentName);
    }
}
