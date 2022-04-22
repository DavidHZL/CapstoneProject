/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Post;

/**
 *
 * @author Dadvid
 */
public class PostSearchDB {
    public static ArrayList<Post> searchForPostsByCaption(String criteria) throws SQLException, IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        FileOutputStream fs = null;

        String query = "SELECT * FROM post WHERE caption LIKE ?";
        ArrayList<Post> postList = new ArrayList();
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, criteria+"%");
            resultSet = statement.executeQuery();

            Post post;
            while (resultSet.next()) {

                File f = new File("c:\\Users\\Dadvid\\source\\repos\\CapstoneProject\\CapProject-AssetStore\\web\\resources\\" + resultSet.getString("imageName"));
                fs = new FileOutputStream(f);
                Blob blob = resultSet.getBlob("image");
                byte b[] = blob.getBytes(1, (int) blob.length());
                fs.write(b);

                post = new Post();
                post.setPostID(resultSet.getInt("postID"));
                post.setImageName(resultSet.getString("imageName"));
                post.setCaption(resultSet.getString("caption"));
                post.setDescription(resultSet.getString("description"));
                post.setLikes(resultSet.getInt("likes"));
                post.setCreatorID(resultSet.getInt("creatorID"));
                post.setCreatorUserName(resultSet.getString("creatorUserName"));

                postList.add(post);
            }
        } catch (SQLException | IOException ex) {
            throw ex;
        }
        
        return postList;
    }
    
    public static ArrayList<Post> searchForPostsByDescription(String criteria) throws SQLException, IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        FileOutputStream fs = null;

        String query = "SELECT * FROM post WHERE description LIKE ?";
        ArrayList<Post> postList = new ArrayList();
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, "%"+criteria+"%");
            resultSet = statement.executeQuery();

            Post post;
            while (resultSet.next()) {

                File f = new File("c:\\Users\\Dadvid\\source\\repos\\CapstoneProject\\CapProject-AssetStore\\web\\resources\\" + resultSet.getString("imageName"));
                fs = new FileOutputStream(f);
                Blob blob = resultSet.getBlob("image");
                byte b[] = blob.getBytes(1, (int) blob.length());
                fs.write(b);

                post = new Post();
                post.setPostID(resultSet.getInt("postID"));
                post.setImageName(resultSet.getString("imageName"));
                post.setCaption(resultSet.getString("caption"));
                post.setDescription(resultSet.getString("description"));
                post.setLikes(resultSet.getInt("likes"));
                post.setCreatorID(resultSet.getInt("creatorID"));
                post.setCreatorUserName(resultSet.getString("creatorUserName"));

                postList.add(post);
            }
        } catch (SQLException | IOException ex) {
            throw ex;
        }
        
        return postList;
    }
}
