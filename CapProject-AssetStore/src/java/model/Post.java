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
public class Post {
    private int postID;
    private String imageName;
    private String caption;
    private String description;
    private int likes;
    public ArrayList<String> tags;
    //Add create by to model, javascript, and servlet
    
    public Post(){}

    public Post(int postID, String imageName, String caption, String description, int likes) {
        this.postID = postID;
        this.imageName = imageName;
        this.caption = caption;
        this.description = description;
        this.likes = likes;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
    
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

}
