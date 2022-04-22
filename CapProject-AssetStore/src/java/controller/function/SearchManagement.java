/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.function;

import data.PostSearchDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Post;

/**
 *
 * @author Dadvid
 */
public class SearchManagement {
    public static ArrayList<Post> searchForPosts (String criteria, ArrayList<String> errorList){
        ArrayList<Post> masterPostList = new ArrayList();
        try {
            
            ArrayList<Post> postListByCaption = PostSearchDB.searchForPostsByCaption(criteria);
            
            for (Post cPost : postListByCaption){
                masterPostList.add(cPost);
            }
            
            // Currently only adds the Caption Items
            
//            ArrayList<Post> postListByDescription = PostSearchDB.searchForPostsByDescription(criteria);
//            
//            for (Post mPost : masterPostList){
//                for(Post dPost : postListByDescription){
//                    if (mPost.getPostID() == dPost.getPostID()) {
//                        
//                    } else {
//                        masterPostList.add(dPost);
//                    }
//                }
//                
//            }
            
            return masterPostList;
            
        } catch(SQLException | IOException ex) {
            errorList.add(ex.getMessage());
            return null;
        }
    }
}
