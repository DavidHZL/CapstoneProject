/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.function;

import data.AccountProfileDB;
import data.PostDB;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Account;
import model.Post;
import model.Profile;

/**
 *
 * @author Dadvid
 */
public class PostManagement {
    public static Boolean storePost(Account currentUser, String caption, String description, String imageName, ArrayList<String> tagList, ArrayList<String> errorList) {
        if (validatePost(caption, description, imageName, errorList)){
            Post newPost = new Post();
            
            newPost.setCaption(caption);
            newPost.setDescription(description);
            newPost.setImageName(imageName);
            newPost.setLikes(0);
            newPost.tags = tagList;
            try {
                int postID = PostDB.addPost(newPost);
                newPost.setPostID(postID);
                
                Profile currentProfile = AccountProfileDB.retrieveCurrentUserProfile(currentUser);
                
                PostDB.establishProfilePostLink(currentProfile, newPost, errorList);
                return true;
            } catch (SQLException ex) {
                errorList.add("Error Adding Post to the Database");
                return false;
            }
        } else {
            return false;
        }  
    }
    
        protected static Boolean validatePost(String caption, String description, String imageName, ArrayList<String> errorList){
        
        Boolean isValid = true;
        
        if (caption.isEmpty() || caption.length() > 75 ){
            errorList.add("Please enter a caption that is less than 75 characters");
            isValid = false;
        }
        if (description.isEmpty() || description.length() > 255) {
            errorList.add("Please enter a description that is less than 255 characters");
            isValid = false;
        }
        if (imageName.isEmpty() || imageName.length() > 255) {
            errorList.add("Please select an image to upload");
            isValid = false;
        }
        
        return isValid;
    }
}
