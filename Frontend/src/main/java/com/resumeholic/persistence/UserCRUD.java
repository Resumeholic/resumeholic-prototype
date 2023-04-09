/*
 * Anthony Thanpoovong & Caleb Lam
 * COE692 Persistance
 */
package com.resumeholic.persistence;

import java.sql.Statement;
import com.resumeholic.helper.UserInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * @author Anthony Than Poovong, Caleb Lam
 */
public class UserCRUD {

    private static Connection getCon() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String conString = "jdbc:mysql://" + PersistenceConfig.DATABASE_URL
                    + "/" + PersistenceConfig.DATABASE_NAME
                    + "?serverTimezone=" + PersistenceConfig.DATABASE_TIMEZONE;
            System.out.println("Frontend connection: " + conString);
            con = DriverManager.getConnection(conString, "root", "student");
            System.out.println("Connection established.");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
    
    public UserInfo create(UserInfo newUser) {
        try {
            Connection con = getCon();

            String q = "insert into users (email, password, firstName, lastName) values (\'" +
                    newUser.getEmail() + "\', \'" +
                    newUser.getPassword() + "\', \'" +
                    newUser.getFirstName() + "\', \'" +
                    newUser.getLastName() + "\')";
            PreparedStatement ps = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
            int affected = ps.executeUpdate();
            
            if (affected == 0) {
                throw new SQLException("Something went wrong and database cannot create resume");
            }
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new UserInfo(generatedKeys.getInt(1),
                            newUser.getEmail(),
                            newUser.getPassword(),
                            newUser.getFirstName(),
                            newUser.getLastName());
                }
            }
            
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return null;
    }

    public static UserInfo read(String email, String password) {
        UserInfo bean = null;
        try {
            Connection con = getCon();

            String q = "select * from users where email like \'" + email + "\' and password = \'" + password + "\'";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String pass = rs.getString("password");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                if (pass.equals(password)) {
                    bean = new UserInfo(id, email, password, firstName, lastName);
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return bean;
    }
    
    public static UserInfo read(String email) {
        UserInfo bean = null;
        try {
            Connection con = getCon();

            String q = "select * from users where email like ?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String pass = rs.getString("password");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                bean = new UserInfo(id, email, pass, firstName, lastName);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return bean;
    }
    
    public static void delete(int userId) {
        try {
            Connection con = getCon();
            String q = "delete from users where id = " + userId;
            PreparedStatement ps = con.prepareStatement(q);
            ps.executeUpdate();
            
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
