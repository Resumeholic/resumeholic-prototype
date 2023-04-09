/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.business;

import com.resumeholic.helper.PersonalInfoHeader;
import com.resumeholic.helper.ResumeInfo;
import com.resumeholic.helper.ResumeInfoList;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author student
 */
public class Business {
    public static ResumeInfoList getResumes(int authorId, String token) throws IOException {
        if (token != null) {
            
            Client resumesClient = ClientBuilder.newClient();
            String documentService = System.getenv("DOCUMENT_SERVICE");
            System.out.println("DocumentService: " + documentService);
            WebTarget resumesWebTarget = resumesClient.target("http://" + documentService + "/DocumentService/webresources/resumes");
            InputStream inputStream = resumesWebTarget.queryParam("token", token)
                    .queryParam("authorId", authorId)
                    .request(MediaType.APPLICATION_XML)
                    .get(InputStream.class);
            String xml = IOUtils.toString(inputStream, "utf-8");
            ResumeInfoList resumes = resumesXMLToObjects(xml);
            
            Client headerClient = ClientBuilder.newClient();
            String headerService = System.getenv("PERSONAL_INFO_HEADER_SERVICE");
            WebTarget headerWebTarget = headerClient.target("http://" + headerService + "/PersonalInfoHeaderService/webresources/headers");
            for (ResumeInfo resume : resumes.getResumes()) {
                InputStream headerData = headerWebTarget.queryParam("token", token)
                        .queryParam("resumeId", resume.getId())
                        .request(MediaType.APPLICATION_XML).get(InputStream.class);
                try {
                    PersonalInfoHeader header = headerXMLToObject(IOUtils.toString(headerData, "utf-8"));
                    resume.setPersonalInfoHeader(header);
                } catch (Exception ex) {
                    System.out.println(ex);
                    resume.setPersonalInfoHeader(null);
                }
            }
            
            return resumes;
        }
        return null;
    }
    
    public static ResumeInfo getResume(int resumeId, String token) throws IOException {
        if (token != null) {
            
            Client resumesClient = ClientBuilder.newClient();
            String documentService = System.getenv("DOCUMENT_SERVICE");
            WebTarget resumesWebTarget = resumesClient.target("http://" + documentService + "/DocumentService/webresources/resumes");
            InputStream inputStream = resumesWebTarget.queryParam("token", token)
                    .path(Integer.toString(resumeId))
                    .request(MediaType.APPLICATION_XML)
                    .get(InputStream.class);
            String xml = IOUtils.toString(inputStream, "utf-8");
            ResumeInfo resume = resumeXMLToObject(xml);
            
            Client headerClient = ClientBuilder.newClient();
            String headerService = System.getenv("PERSONAL_INFO_HEADER_SERVICE");
            WebTarget headerWebTarget = headerClient.target("http://" + headerService + "/PersonalInfoHeaderService/webresources/headers");
            InputStream headerData = headerWebTarget.queryParam("token", token)
                        .queryParam("resumeId", resume.getId())
                        .request(MediaType.APPLICATION_XML).get(InputStream.class);
                try {
                    PersonalInfoHeader header = headerXMLToObject(IOUtils.toString(headerData, "utf-8"));
                    resume.setPersonalInfoHeader(header);
                } catch (Exception ex) {
                    System.out.println(ex);
                    resume.setPersonalInfoHeader(null);
                }
            
            return resume;
        }
        return null;
    }
    
    public static boolean saveResume(String token, ResumeInfo newResume) {
        return saveDocumentInfo(token, newResume) && 
                saveHeader(token, newResume.getPersonalInfoHeader());
    }
    
    public static boolean saveDocumentInfo(String token, ResumeInfo newResumeInfo) {
        if (token != null) {
            Client resumesClient = ClientBuilder.newClient();
            String documentService = System.getenv("DOCUMENT_SERVICE");
            WebTarget resumesWebTarget = resumesClient.target("http://" + documentService + "/DocumentService/webresources/resumes");
            Form docForm = new Form();
            docForm.param("documentname", newResumeInfo.getDocumentName());
            Response docChangeResp = resumesWebTarget.queryParam("token", token)
                    .path(Integer.toString(newResumeInfo.getId()))
                    .request(MediaType.TEXT_HTML)
                    .post(Entity.form(docForm), Response.class);
            System.out.println("SAVE DOCUMENT status: " + docChangeResp.getStatus());
            return docChangeResp.getStatus() == 200;
        }
        
        return false;
    }
    
    public static boolean saveHeader(String token, PersonalInfoHeader newHeader) {
        if (token != null) {
            Client headerClient = ClientBuilder.newClient();
            String headerService = System.getenv("PERSONAL_INFO_HEADER_SERVICE");
            WebTarget headerWebTarget = headerClient.target("http://" + headerService + "/PersonalInfoHeaderService/webresources/headers");
            Form headerForm = new Form();
            headerForm.param("firstname", newHeader.getFirstName());
            headerForm.param("lastname", newHeader.getLastName());
            headerForm.param("email", newHeader.getEmail());
            headerForm.param("phone", newHeader.getPhoneNumber());
            Response headerChangeRep = headerWebTarget.queryParam("token", token)
                    .path("update")
                    .queryParam("resumeId", newHeader.getResumeId())
                    .request(MediaType.TEXT_HTML)
                    .post(Entity.form(headerForm), Response.class);
            System.out.println("SAVE HEADER status: " + headerChangeRep.getStatus());
            return headerChangeRep.getStatus() == 200;
        }
        
        return false;
    }
    
    public static ResumeInfo createResume(String token, int authorId, String docName, String firstName, String lastName, String email, String phoneNumber) throws IOException {
        if (token != null) {
            Client resumeClient = ClientBuilder.newClient();
            String documentService = System.getenv("DOCUMENT_SERVICE");
            WebTarget resumeWebTarget = resumeClient.target("http://" + documentService + "/DocumentService/webresources/resumes");
            Form docForm = new Form();
            docForm.param("authorid", Integer.toString(authorId));
            docForm.param("documentname", docName);
            InputStream newDocStream = resumeWebTarget.queryParam("token", token)
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.form(docForm), InputStream.class);
            String newDocXML = IOUtils.toString(newDocStream, "utf-8");
            ResumeInfo newResume = resumeXMLToObject(newDocXML);
            
            Client headerClient = ClientBuilder.newClient();
            String headerService = System.getenv("PERSONAL_INFO_HEADER_SERVICE");
            WebTarget headerWebTarget = headerClient.target("http://" + headerService + "/PersonalInfoHeaderService/webresources/headers");
            Form headerForm = new Form();
            headerForm.param("firstname", firstName);
            headerForm.param("lastname", lastName);
            headerForm.param("email", email);
            headerForm.param("phone", phoneNumber);
            InputStream newHeaderStream = headerWebTarget.queryParam("token", token)
                    .queryParam("resumeId", newResume.getId())
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.form(headerForm), InputStream.class);
            String newHeaderXML = IOUtils.toString(newHeaderStream, "utf-8");
            PersonalInfoHeader newHeader = headerXMLToObject(newHeaderXML);
            
            newResume.setPersonalInfoHeader(newHeader);
            return newResume;
        }
        
        return null;
    }
    
    private static ResumeInfoList resumesXMLToObjects(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ResumeInfoList.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            ResumeInfoList resumes = (ResumeInfoList) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return resumes;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static ResumeInfo resumeXMLToObject(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ResumeInfo.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            ResumeInfo resume = (ResumeInfo) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return resume;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static PersonalInfoHeader headerXMLToObject(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(PersonalInfoHeader.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            PersonalInfoHeader header = (PersonalInfoHeader) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return header;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
