/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.documentservice.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.resumeholic.documentservice.helper.ResumeInfo;
import com.resumeholic.documentservice.helper.ResumeInfoList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 *
 * @author Anthony Than Poovong, Caleb Lam
 */
public class ResumeCRUD {
    private static Connection getCon() {
        Connection con=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String conString = "jdbc:mysql://" + PersistenceConfig.DATABASE_URL
                    + "/" + PersistenceConfig.DATABASE_NAME
                    + "?serverTimezone=" + PersistenceConfig.DATABASE_TIMEZONE;
            System.out.println("DocumentService connection: " + conString);
            con = DriverManager.getConnection(conString, "root", "student");
            System.out.println("Connection established.");
        } catch (Exception e) { System.out.println(e);}
        return con;
    }
    
    public static ResumeInfo create(int authorId, String documentName) {
        try {
            try(Connection con = getCon()){
                Timestamp now = Timestamp.from(Instant.now(Clock.systemUTC()));
//                String q = "insert into resumes (documentName, createdDate, updatedDate, authorId) values (\'" +
//                        documentName + "\', " +
//                        "\'" + now + "\', " +
//                        "\'" + now + "\', " +
//                        authorId + ")";
                String q = "insert into resumes (documentName, createdDate, updatedDate, authorId) values (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, documentName);
                ps.setTimestamp(2, now);
                ps.setTimestamp(3, now);
                ps.setInt(4, authorId);
                int affected = ps.executeUpdate();

                if (affected == 0) {
                    throw new SQLException("Something went wrong and database cannot create resume");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new ResumeInfo(generatedKeys.getInt(1), authorId, documentName,
                                ZonedDateTime.ofInstant(now.toInstant(), ZoneId.of("UTC")),
                                ZonedDateTime.ofInstant(now.toInstant(), ZoneId.of("UTC")));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
    public static ResumeInfo create(int authorId, ResumeInfo newResumeInfo) {
        return create(authorId, newResumeInfo.getDocumentName());
    }
    
    public static boolean update(int resumeId, String documentName) {
        try {
            try(Connection con = getCon()){
                Timestamp now = Timestamp.from(Instant.now(Clock.systemUTC()));
//                String q = "update resumes set " +
//                        "documentName = \'" + documentName + "\', " +
//                        "updatedDate = \'" + Timestamp.from(Instant.now()) + "\' " +
//                        "where id = " + resumeId;
                String q = "update resumes set " +
                        "documentName = ?, " +
                        "updatedDate = ? " +
                        "where id = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setString(1, documentName);
                ps.setTimestamp(2, now);
                ps.setInt(3, resumeId);

                // currently applies to the main resume document only, not
                // references to its sections.
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean update(int resumeId, ZonedDateTime updateDateTime) {
        try {
            try(Connection con = getCon()){
                Timestamp timestamp = Timestamp.from(updateDateTime.toInstant());
//                String q = "update resumes set " +
//                        "documentName = \'" + documentName + "\', " +
//                        "updatedDate = \'" + Timestamp.from(Instant.now()) + "\' " +
//                        "where id = " + resumeId;
                String q = "update resumes set " +
                        "updatedDate = ? " +
                        "where id = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setTimestamp(1, timestamp);
                ps.setInt(2, resumeId);

                // currently applies to the main resume document only, not
                // references to its sections.
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean update(int resumeId, ResumeInfo newResumeInfo) {
        return update(resumeId, newResumeInfo.getDocumentName());
    }
    
    public static ResumeInfoList readAll(int userId) {
        ArrayList<ResumeInfo> resumes = new ArrayList<>();
        try {
            try(Connection con = getCon()){

                String q = "select * from resumes where authorId = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String documentName = rs.getString("documentName");
                    ZonedDateTime createdDate = ZonedDateTime.ofInstant(rs.getTimestamp("createdDate").toInstant(), ZoneId.of("UTC"));
                    ZonedDateTime updatedDate = ZonedDateTime.ofInstant(rs.getTimestamp("updatedDate").toInstant(), ZoneId.of("UTC"));
                    ResumeInfo r = new ResumeInfo(id, userId, documentName, createdDate, updatedDate);

                    resumes.add(r);
                }
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResumeInfoList(resumes);
    }
    
    public static ResumeInfo read(int resumeId) {
        ResumeInfo resume = null;
        try {
            try(Connection con = getCon()){

                String q = "select * from resumes where id = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setInt(1, resumeId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int authorId = rs.getInt("authorId");
                    String documentName = rs.getString("documentName");
                    ZonedDateTime createdDate = ZonedDateTime.ofInstant(rs.getTimestamp("createdDate").toInstant(), ZoneId.of("UTC"));
                    ZonedDateTime updatedDate = ZonedDateTime.ofInstant(rs.getTimestamp("updatedDate").toInstant(), ZoneId.of("UTC"));
                    System.out.println("Resume ID: " + resumeId + "; Created " + createdDate + "; Updated: " + updatedDate);
                    resume = new ResumeInfo(id, authorId, documentName, createdDate, updatedDate);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return resume;
    }
    
    public static boolean delete(int resumeId) {
        try {
            try(Connection con = getCon()){

                String q = "delete from resumes where id = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setInt(1, resumeId);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean deleteAll(int userId) {
        try {
            try(Connection con = getCon()){
                String q = "delete from resumes where authorId = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setInt(1, userId);
                ps.executeUpdate();
                
                // TODO: Delete other parts!
                
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
