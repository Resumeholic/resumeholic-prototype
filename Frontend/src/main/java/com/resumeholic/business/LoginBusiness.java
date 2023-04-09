/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.business;

import com.resumeholic.helper.UserInfo;
import com.resumeholic.persistence.UserCRUD;

/**
 *
 * @author Anthony Than Poovong, Caleb Lam
 */
public class LoginBusiness {
    public static UserInfo find(String email, String password) {
        return UserCRUD.read(email, password);
    }
    
    public static UserInfo lookup(String email) {
        return UserCRUD.read(email);
    }
}
