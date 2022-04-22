/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.function;

import data.AccountProfileDB;
import data.PostDB;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public static ArrayList<Post> retrieveAllPosts(ArrayList<String> errorList) throws IOException {
        try {
            ArrayList<Post> postList = PostDB.retrieveAllPosts();

            return postList;
        } catch (SQLException | IOException ex) {
            errorList.add(ex.getMessage());
            return null;
        }
    }
    
    public static ArrayList<Post> retrieveAllPostsByProfileID(Profile userProfile, ArrayList<String> errorList) throws IOException{
        try {
            ArrayList<Post> userPostList = AccountProfileDB.retrieveAllPostsByProfileID(userProfile.getProfileID());
            
            return userPostList;
        } catch(SQLException ex) {
            errorList.add(ex.getMessage());
            return null;
        }
    }

    public static Boolean storePost(Account currentUser, String caption, String description, String imageName, ArrayList<String> errorList) throws FileNotFoundException {
        if (validatePost(caption, description, imageName, errorList)) {
            Post newPost = new Post();

            newPost.setCaption(caption);
            newPost.setDescription(description);
            newPost.setImageName(imageName);
            newPost.setLikes(0);
            newPost.setCreatorID(currentUser.getAccountID());
            newPost.setCreatorUserName(currentUser.getUsername());
            try {
                int postID = PostDB.addPost(newPost);
                newPost.setPostID(postID);

                Profile currentProfile = AccountProfileDB.retrieveCurrentUserProfile(currentUser);

                PostDB.establishProfilePostLink(currentProfile, newPost, errorList);
                return true;
            } catch (SQLException | FileNotFoundException ex) {
                errorList.add(ex.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static Profile editPost(Account currentUser, int postID, String caption, String description, ArrayList<String> errorList) throws IOException{
        if(validateEdit(caption, description, errorList)) {
            try {
                PostDB.updatePostCaption(postID, caption);
                PostDB.updatePostDescription(postID, description);
                
                model.Profile userProfile = ProfileManagement.retrieveCurrentProfile(currentUser, errorList);
                ArrayList<Post> userPostsList = retrieveAllPostsByProfileID(userProfile, errorList);
                
                userProfile.posts = userPostsList;
                
                return userProfile;
            } catch (SQLException | IOException ex){
                errorList.add(ex.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
    
    public static Profile deletePost(Account currentUser, int postID, ArrayList<String> errorList) throws SQLException, IOException {
        try {
            PostDB.deletePostProfileLink(postID);
            PostDB.deletePostByID(postID);
            
            model.Profile userProfile = ProfileManagement.retrieveCurrentProfile(currentUser, errorList);
            ArrayList<Post> userPostsList = retrieveAllPostsByProfileID(userProfile, errorList);
                
            userProfile.posts = userPostsList;
                
            return userProfile;
        } catch(SQLException | IOException ex) {
            errorList.add(ex.getMessage());
            return null;
        }
    }
    
    protected static Boolean validatePost(String caption, String description, String imageName, ArrayList<String> errorList) {

        Boolean isValid = true;

        if (caption.isEmpty() || caption.length() > 75) {
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
    
    protected static Boolean validateEdit(String caption, String description, ArrayList<String> errorList) {

        Boolean isValid = true;

        if (caption.isEmpty() || caption.length() > 75) {
            errorList.add("Please enter a caption that is less than 75 characters");
            isValid = false;
        }
        if (description.isEmpty() || description.length() > 255) {
            errorList.add("Please enter a description that is less than 255 characters");
            isValid = false;
        }

        return isValid;
    }
    
    public static int addLike(int postID, int likes, ArrayList<String> errorList) {
        likes += 1;
        try {
            PostDB.addLikeToPost(postID, likes);
        } catch(SQLException ex) {
            errorList.add(ex.getMessage());
        }
        
        return likes;
    }
}
