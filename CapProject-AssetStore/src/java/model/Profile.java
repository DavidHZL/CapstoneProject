/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Dadvid
 */
public class Profile {
    private int profileID;
    private String profileName;
    private String email;
    private String profileCaption;
    private int followerCount;
    private ArrayList<Post> posts;
    private Cart cart;
    
    public Profile() {}

    public Profile(int profileID, String profileName, String email, String profileCaption, int followerCount) {
        this.profileID = profileID;
        this.profileName = profileName;
        this.email = email;
        this.profileCaption = profileCaption;
        this.followerCount = followerCount;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileCaption() {
        return profileCaption;
    }

    public void setProfileCaption(String profileCaption) {
        this.profileCaption = profileCaption;
    }
    
    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }
    
    
}
