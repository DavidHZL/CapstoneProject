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
    
    public static ArrayList<Profile> retrieveTrendingProfiles(ArrayList<String> errorList){
        try {
            ArrayList<Profile> trendingProfileList = AccountProfileDB.retrieveTrendingProfiles();
            return trendingProfileList;
        } catch(SQLException ex) {
            errorList.add(ex.getMessage());
            return null;
        }
    }
    
    public static int addFollowerToProfile(Account currentUser, int followingProfileID, int followerCount, ArrayList<String> errorList) throws SQLException {
    
        try {
            model.Profile currentUserProfile = AccountProfileDB.retrieveCurrentUserProfile(currentUser);
            
            followerCount+=1;
            AccountProfileDB.addFollowerToProfile(followingProfileID, followerCount);

            AccountProfileDB.linkNewFollower(followingProfileID, currentUserProfile.getProfileID());

            return followerCount;
        } catch (SQLException ex) {
            errorList.add(ex.getMessage());
            return followerCount;
        }
    }
    
    public static boolean checkFollowStatus(Account currentUser, int followingProfileID, ArrayList<String> errorList) throws SQLException {
        
        try {
            model.Profile currentUserProfile = AccountProfileDB.retrieveCurrentUserProfile(currentUser);

            boolean status = AccountProfileDB.checkFollowStatus(followingProfileID, currentUserProfile.getProfileID());

            return status;
        } catch (SQLException ex) {
            errorList.add(ex.getMessage());
            return false;
        }
        
    }
    
    
}
