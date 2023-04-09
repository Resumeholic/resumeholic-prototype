/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.documentservice.endpoint;

import com.resumeholic.documentservice.business.DocumentBusiness;
import com.resumeholic.documentservice.helper.ResumeInfo;
import com.resumeholic.documentservice.helper.ResumeInfoList;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * REST Web Service
 *
 * @author student
 */
@Path("resumes")
public class ResumesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ResumesResource
     */
    public ResumesResource() {
    }

    /**
     * Retrieves representation of an instance of com.resumeholic.documentservice.endpoint.ResumesResource
     * @param id
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/{id}")
    public String getXml(@PathParam("id") int id) {
        //TODO return proper representation object
        ResumeInfo resume = DocumentBusiness.getResume(id);
        if (resume == null) {
            return "";
        }
        
        JAXBContext jaxbContext;
        try {
            System.out.println("Resume found! ID: " + resume.getId());
            jaxbContext = JAXBContext.newInstance(ResumeInfo.class);
            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(resume, sw);
            
            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(ResumesResource.class.getName()).log(Level.SEVERE, null, ex);
            return "an error occurred";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/")
    public String getAllXml(@QueryParam("authorId") int authorId) {
        ResumeInfoList resumes = DocumentBusiness.getResumes(authorId);
        if (resumes == null) {
            return "";
        }
        
        JAXBContext jaxbContext;
        try {
            System.out.println("Resumes found!");
            jaxbContext = JAXBContext.newInstance(ResumeInfoList.class);
            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(resumes, sw);
            
            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(ResumesResource.class.getName()).log(Level.SEVERE, null, ex);
            return "an error occurred";
        }
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/")
    public String createNewResume(@FormParam("authorid") int authorId, @FormParam("documentname") String documentName) {
        ResumeInfo newResume = DocumentBusiness.createResume(authorId, documentName);
        
        if (newResume == null) {
            return "";
        }
        
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ResumeInfo.class);
            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(newResume, sw);
            
            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(ResumesResource.class.getName()).log(Level.SEVERE, null, ex);
            return "an error occurred";
        }
    }
    
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/{id}")
    public String updateResume(@PathParam("id") int resumeId, @FormParam("documentname") String documentName) {
        boolean success = DocumentBusiness.updateResume(resumeId, documentName);
        return success ? "Updated!" : "Update failed...";
    }
}
