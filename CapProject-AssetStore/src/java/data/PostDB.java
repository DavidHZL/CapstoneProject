/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Post;
import model.Profile;

/**
 *
 * @author Dadvid
 */
public class PostDB {
    public static int addPost(Post post) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        FileInputStream fs = null;
        int keyValue = 0;

        String query
                = "Insert into post (image, imageName, caption, description, likes) "
                + "values (?,?, ?, ?, ?)";
        try {
            File f = new File("c:\\uploadedFiles\\"+post.getImageName());
            fs = new FileInputStream(f);
            
            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setBinaryStream(1,fs,(int)f.length());
            ps.setString(2, post.getImageName());
            ps.setString(3, post.getCaption());
            ps.setString(4, post.getDescription());
            ps.setInt(5, 0);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            if (rs != null) {
                rs.next();
                keyValue = rs.getInt(1);
            }
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PostDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (ps != null & rs != null) {
                    ps.close();
                    rs.close();
                }
                pool.freeConnection(connection);
            } catch (SQLException ex) {
                throw ex;
            }
        }
        return keyValue;
    }
    
        public static void establishProfilePostLink(Profile profile, Post post, ArrayList<String> errorList) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String query
                = "INSERT INTO profilepost (profileID, postID) "
                + "VALUES (?, ?)";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, profile.getProfileID());
            statement.setInt(2, post.getPostID());
            statement.executeUpdate();
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.close();
                    statement.close();
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                throw e;
            }
        }
    }
}
