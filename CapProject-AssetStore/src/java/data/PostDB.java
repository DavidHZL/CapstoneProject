/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
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

    public static ArrayList<Post> retrieveAllPosts() throws SQLException, IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        FileOutputStream fs = null;

        String query = "SELECT * FROM post";
        ArrayList<Post> postList = new ArrayList();
        try {
            statement = connection.prepareStatement(query);
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

                postList.add(post);
            }
        } catch (SQLException | IOException ex) {
            throw ex;
        } finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.close();
                    statement.close();
                }
                pool.freeConnection(connection);
            } catch (SQLException ex) {
                throw ex;
            }
        }
        return postList;
    }

    public static int addPost(Post post) throws SQLException, FileNotFoundException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int keyValue = 0;

        String query
                = "Insert into post (image, imageName, caption, description, likes) "
                + "values (?,?, ?, ?, ?)";
        try {
            File f = new File("c:\\Users\\Dadvid\\source\\repos\\CapstoneProject\\CapProject-AssetStore\\web\\resources\\" + post.getImageName());
            FileInputStream fs = new FileInputStream(f);

            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setBinaryStream(1, fs, (int) f.length());
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
        } catch (SQLException | FileNotFoundException sqlEx) {
            throw sqlEx;
        } finally {
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
    
    public static void updatePostCaption(int postID, String caption) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String query = "UPDATE post SET caption = ? WHERE postID = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, caption);            
            statement.setInt(2, postID);

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
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
    
    public static void updatePostDescription(int postID, String description) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String query = "UPDATE post SET description = ? WHERE postID = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, description);            
            statement.setInt(2, postID);

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
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
    
    public static void deletePostByID(int postID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String query = "DELETE FROM post WHERE postID = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, postID);

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.close();
                    statement.close();
                }
                pool.freeConnection(connection);
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }
    
    public static void addLikeToPost(int postID, int likes) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String query = "UPDATE post SET likes = ? WHERE postID = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, likes);            
            statement.setInt(2, postID);

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
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
