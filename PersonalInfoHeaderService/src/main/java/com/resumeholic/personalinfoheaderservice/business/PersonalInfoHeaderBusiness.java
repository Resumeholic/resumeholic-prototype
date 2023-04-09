/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.personalinfoheaderservice.business;

import com.resumeholic.personalinfoheaderservice.helper.PersonalInfoHeader;
import com.resumeholic.personalinfoheaderservice.persistence.PersonalInfoHeaderCRUD;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Caleb Lam
 */
public class PersonalInfoHeaderBusiness {
    public static PersonalInfoHeader findHeader(int resumeId) {
        return PersonalInfoHeaderCRUD.find(resumeId);
    }
    
    public static PersonalInfoHeader getHeader(int resumeId) {
        return PersonalInfoHeaderCRUD.read(resumeId);
    }
    
    public static PersonalInfoHeader createHeader(int resumeId, String firstName, String lastName, String email, String phoneNumber) {
        return PersonalInfoHeaderCRUD.create(resumeId, firstName, lastName, email, phoneNumber);
    }
    
    public static PersonalInfoHeader createHeader(int resumeId, PersonalInfoHeader newHeader) {
        return PersonalInfoHeaderCRUD.create(resumeId, newHeader);
    }
    
    public static boolean updateHeader(int resumeId, String firstName, String lastName, String email, String phoneNumber) {
        boolean success = PersonalInfoHeaderCRUD.update(resumeId, firstName, lastName, email, phoneNumber);
        if (success) {
            sendUpdateMessage(resumeId);
        }
        return success;
    }
    
    public static boolean updateHeader(int resumeId, PersonalInfoHeader newHeader) {
        boolean success = PersonalInfoHeaderCRUD.update(resumeId, newHeader);
        if (success) {
            sendUpdateMessage(resumeId);
        }
        return success;
    }
    
    private static void sendUpdateMessage(int resumeId) {
        String msg = String.format("PersonalInfoHeaderSaved|%s|%s", resumeId, ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        try {
            Messaging.sendMessage(msg);
        } catch (IOException ex) {
            System.out.println("Cannot send message: " + msg);
            System.out.println(ex);
        }
    }
}
