package com.resumeholic.personalinfoheaderservice.persistence;

import java.sql.Statement;
import com.resumeholic.personalinfoheaderservice.helper.PersonalInfoHeader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Anthony Than Poovong, Caleb Lam
 */
public class PersonalInfoHeaderCRUD {
    private static Connection getCon() {
        Connection con=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String conString = "jdbc:mysql://" + PersistenceConfig.DATABASE_URL
                    + "/" + PersistenceConfig.DATABASE_NAME
                    + "?serverTimezone=" + PersistenceConfig.DATABASE_TIMEZONE;
            System.out.println("PersonalInfoHeaderService connection: " + conString);
            con = DriverManager.getConnection(conString, "root", "student");
            System.out.println("Connection established.");
        } catch (Exception e) { System.out.println(e);}
        return con;
    }
    
    public static PersonalInfoHeader create(int resumeId, String firstName, String lastName, String email, String phoneNumber) {
        try {
            try(Connection con = getCon()) {
                String q = "insert into personalInformationHeaders (firstName, lastName, email, phoneNumber, resumeId) values " +
                        "(?, ?, ?, ?, ?);";
                PreparedStatement ps = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, phoneNumber);
                ps.setInt(5, resumeId);

                int affected = ps.executeUpdate();

                if (affected == 0) {
                    throw new SQLException("Something went wrong and database cannot create resume");
                }

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return new PersonalInfoHeader(generatedKeys.getInt(1),
                                resumeId,
                                firstName,
                                lastName,
                                email,
                                phoneNumber
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
    
    public static PersonalInfoHeader create(int resumeId, PersonalInfoHeader newHeader) {
        return create(resumeId,
                newHeader.getFirstName(),
                newHeader.getLastName(),
                newHeader.getEmail(),
                newHeader.getPhoneNumber()
        );
    } 
    
    public static boolean update(int resumeId, String firstName, String lastName, String email, String phoneNumber) {
        try {
            try(Connection con = getCon()) {
                String q = "update personalInformationHeaders set " +
                        "firstName = ?, " +
                        "lastName = ?, " +
                        "email = ?, " +
                        "phoneNumber = ? " + 
                        "where resumeId = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, phoneNumber);
                ps.setInt(5, resumeId);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean update(int resumeId, PersonalInfoHeader newHeader) {
        return update(resumeId, 
                newHeader.getFirstName(),
                newHeader.getLastName(),
                newHeader.getEmail(),
                newHeader.getPhoneNumber());
    }
    
    public static PersonalInfoHeader find(int resumeId) {
        PersonalInfoHeader header = null;
        try {
            try(Connection con = getCon()) {
                String q = "select * from personalInformationHeaders where resumeId = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setInt(1, resumeId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");

                    header = new PersonalInfoHeader(id, resumeId, firstName, lastName, email, phoneNumber);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return header;
    }
    
    public static PersonalInfoHeader read(int headerId) {
        PersonalInfoHeader header = null;
        try {
            try(Connection con = getCon()) {
                String q = "select * from personalInformationHeaders where id = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setInt(1, headerId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int resumeId = rs.getInt("resumeId");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");

                    header = new PersonalInfoHeader(headerId, resumeId, firstName, lastName, email, phoneNumber);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return header;
    }
    
    public static boolean delete(int headerId) {
        try {
            try(Connection con = getCon()) {

                String q = "delete from personalInformationHeaders where id = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setInt(1, headerId);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public static void deleteFromResume(int resumeId) {
        try {
            try(Connection con = getCon()) {

                String q = "delete from personalInformationHeaders where resumeId = ?";
                PreparedStatement ps = con.prepareStatement(q);
                ps.setInt(1, resumeId);
                ps.executeUpdate();
            
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
