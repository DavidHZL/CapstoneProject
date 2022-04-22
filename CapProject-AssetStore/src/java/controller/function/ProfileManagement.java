/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.function;

import data.AccountProfileDB;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Account;
import model.Profile;

/**
 *
 * @author Dadvid
 */
public class ProfileManagement {
    
    public static Profile retrieveCurrentProfile(Account currentUser, ArrayList<String> errorList) throws SQLException {
        try {
            model.Profile userProfile = AccountProfileDB.retrieveCurrentUserProfile(currentUser);
            
            return userProfile;
        } catch (SQLException ex) {
            errorList.add(ex.getMessage());
            return null;
        }
    }
    
    public static Profile retrieveAltProfile(int creatorID, ArrayList<String> errorList) throws SQLException {
        try {
            model.Profile altProfile = AccountProfileDB.retrieveAltProfileByID(creatorID);
            
            return altProfile;
        } catch (SQLException ex) {
            errorList.add(ex.getMessage());
            return null;
        }
    }
    
}
