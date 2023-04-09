/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.documentservice.persistence;

/**
 * Config class for connecting to the database
 * @author Caleb Lam
 */
class PersistenceConfig {
    public static final String DATABASE_URL = System.getenv("DB_URL");
    public static final String DATABASE_NAME = "resumeholic_document";
    public static final String DATABASE_TIMEZONE = "UTC";
    
}
