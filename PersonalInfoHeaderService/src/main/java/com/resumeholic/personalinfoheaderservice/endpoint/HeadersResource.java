/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.personalinfoheaderservice.endpoint;

import com.resumeholic.personalinfoheaderservice.business.PersonalInfoHeaderBusiness;
import com.resumeholic.personalinfoheaderservice.helper.PersonalInfoHeader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
@Path("headers")
public class HeadersResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HeadersResource
     */
    public HeadersResource() {
    }

    /**
     * Retrieves representation of an instance of com.resumeholic.personalinfoheaderservice.endpoint.HeadersResource
     * @param resumeId
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/")
    public String getXml(@QueryParam("resumeId") int resumeId) {
        //TODO return proper representation object
        PersonalInfoHeader header = PersonalInfoHeaderBusiness.findHeader(resumeId);
        if (header == null) {
            return null;
        }
        
        JAXBContext jaxbContext;
        try {
            System.out.println("Header found! ID: " + header.getId());
            jaxbContext = JAXBContext.newInstance(PersonalInfoHeader.class);
            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(header, sw);
            
            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(HeadersResource.class.getName()).log(Level.SEVERE, null, ex);
            return "an error occurred";
        }
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/")
    public String createNewHeader(@QueryParam("resumeId") int resumeId,
            @FormParam("firstname") String firstName,
            @FormParam("lastname") String lastName,
            @FormParam("email") String email,
            @FormParam("phone") String phoneNumber) {
        PersonalInfoHeader newHeader = PersonalInfoHeaderBusiness.createHeader(resumeId, firstName, lastName, email, phoneNumber);
        if (newHeader == null) {
            return "";
        }
        
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(PersonalInfoHeader.class);
            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(newHeader, sw);
            
            return sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(HeadersResource.class.getName()).log(Level.SEVERE, null, ex);
            return "an error occurred";
        }
    }
    
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/update")
    public String updateHeader(@QueryParam("resumeId") int resumeId,
            @FormParam("firstname") String firstName,
            @FormParam("lastname") String lastName,
            @FormParam("email") String email,
            @FormParam("phone") String phoneNumber) {
        boolean success = PersonalInfoHeaderBusiness.updateHeader(resumeId, firstName, lastName, email, phoneNumber);
        return success ? "Updated!" : "Update failed...";
    }
}
