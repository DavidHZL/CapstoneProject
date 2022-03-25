/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.function;

import data.AuthDB;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import model.Account;
import model.Profile;

/**
 *
 * @author Dadvid
 */
public class Authorization {

    public static Boolean IsValidLogin(String username, String password, ArrayList<String> errorList) {
        Boolean isValid = true;

        if (username.isEmpty()) {
            errorList.add("Please enter a Username");
            isValid = false;
        }

        if (password.isEmpty()) {
            errorList.add("Please enter a Password");
            isValid = false;
        }

        return isValid;
    }

    public static Boolean IsValidRegister(String username, String password, String email, ArrayList<String> errorList) {
        Boolean isValid = true;

        if (username.isEmpty()) {
            errorList.add("Please enter a Username");
            isValid = false;
        }

        if (password.isEmpty()) {
            errorList.add("Please enter a Password");
            isValid = false;
        }

        if (email.isEmpty()) {
            errorList.add("Please enter a Email");
            isValid = false;
        }

        return isValid;
    }

    public static Account authorizeUser(String username, String password, ArrayList<String> errorList) {
        try {
            return AuthDB.loginUser(username, password);
        } catch (SQLException ex) {
            errorList.add("Invalid Credentials");
            //errorList.add(ex.getMessage());
            return null;
        }
    }

    public static Account RegisterUser(String username, String password, String passwordCheck, String email, String accountType, ArrayList<String> errorList) {
        if (!password.equals(passwordCheck)) {
            errorList.add("Password do not match, please reenter");
            return null;
        } else {

            try {
                if (AuthDB.doesUserExist(username)) {
                    errorList.add("Username invalid");
                    return null;
                }
            } catch (SQLException ex) {
                errorList.add(ex.getMessage());
                return null;
            }

            Account user = new Account();
            user.setUsername(username);
            user.setAccountType(accountType);

            String hash;
            String salt = randomSalt();

            try {
                hash = AuthDB.hashPassword(password, salt);
            } catch (NoSuchAlgorithmException ex) {
                errorList.add("Error: Unable to encrypt password");
                return null;
            }

            try {
                String newUserIDString = AuthDB.createAccount(user, salt, hash);
                user.setAccountID(Integer.parseInt(newUserIDString));
                
                String newProfileIDString = AuthDB.createProfile(username, email, errorList);
                Profile profile = new Profile();
                profile.setProfileID(Integer.parseInt(newProfileIDString));
                profile.setProfileName(username);
                profile.setEmail(email);
                
                AuthDB.createAccountProfileLink(user, profile, errorList);
            } catch (SQLException ex) {
                errorList.add(ex.getMessage());
                return null;
            }

            return user;
        }
    }

    public static String randomSalt() {
        String alphanumericList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * alphanumericList.length());
            salt.append(alphanumericList.charAt(index));
        }
        return salt.toString();
    }
}
