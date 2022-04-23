/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Account;
import model.Post;
import model.Profile;

/**
 *
 * @author Dadvid
 */
public class AccountProfileDB {
    
    // Retrieves the current users profile
    public static Profile retrieveCurrentUserProfile(Account currentUser) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT ap.accountID, p.profileID, p.profileName, p.email, p.profileCaption, p.followerCount FROM accountprofile ap"
                + " JOIN profile p ON ap.profileID = p.profileID"
                + " WHERE ap.accountID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, currentUser.getAccountID());
            rs = ps.executeQuery();

            Profile userProfile = null;
            while (rs.next())
            {
                userProfile = new Profile();
                userProfile.setProfileID(rs.getInt("profileID"));
                userProfile.setProfileName(rs.getString("profileName"));
                userProfile.setEmail(rs.getString("email"));
                userProfile.setProfileCaption(rs.getString("profileCaption"));
                userProfile.setFollowerCount(rs.getInt("followerCount"));
            }
            
            return userProfile;
        } catch (SQLException ex) {
            throw ex;
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
    }
    
    // Retrieves the current users profile
    public static Profile retrieveAltProfileByID(int creatorID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT ap.accountID, p.profileID, p.profileName, p.email, p.profileCaption, p.followerCount FROM accountprofile ap"
                + " JOIN profile p ON ap.profileID = p.profileID"
                + " WHERE ap.accountID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, creatorID);
            rs = ps.executeQuery();

            Profile userProfile = null;
            while (rs.next())
            {
                userProfile = new Profile();
                userProfile.setProfileID(rs.getInt("profileID"));
                userProfile.setProfileName(rs.getString("profileName"));
                userProfile.setEmail(rs.getString("email"));
                userProfile.setProfileCaption(rs.getString("profileCaption"));
                userProfile.setFollowerCount(rs.getInt("followerCount"));
            }
            
            return userProfile;
        } catch (SQLException ex) {
            throw ex;
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
    }
    
    public static ArrayList<Post> retrieveAllPostsByProfileID(int profileID) throws SQLException, IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        FileOutputStream fs = null;
        
        String query = "SELECT * FROM post Join profilepost "
                + "ON post.postID = profilepost.postID "
                + "WHERE profilepost.profileID = ?";
        ArrayList <Post> postList = new ArrayList();
        try{
            statement = connection.prepareStatement(query);
            statement.setInt(1, profileID);
            resultSet = statement.executeQuery();
            
            Post post;
            while(resultSet.next()){
                
                File f = new File("c:\\Users\\Dadvid\\source\\repos\\CapstoneProject\\CapProject-AssetStore\\web\\resources\\"+resultSet.getString("imageName"));
                fs = new FileOutputStream(f);
                Blob blob = resultSet.getBlob("image");
                byte b[] = blob.getBytes(1, (int)blob.length());
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
    
    public static void addProfileCaption(String newCaption, int profileID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "Update profile Set profileCaption = ? "
                + "Where profileID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, newCaption);
            ps.setInt(2, profileID);
            
            ps.executeUpdate();
        } catch (SQLException sqlEx) {
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
    }
    
    public static void addFollowerToProfile(int followingProfileID, int followerCount) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "Update profile Set followerCount = ? "
                + "Where profileID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, followerCount);
            ps.setInt(2, followingProfileID);
            
            ps.executeUpdate();
        } catch (SQLException sqlEx) {
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
    }
    
    public static boolean checkFollowStatus(int followingProfileID, int currentProfileID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean status = false;
        
        String query
                = "SELECT * FROM profilefollower "
                + "WHERE followingProfileID = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, followingProfileID);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                int receivedFollowerID = rs.getInt("followerProfileID");
                
                if(currentProfileID != receivedFollowerID) {
                    status = false;
                } else {
                    status = true;
                    break;
                }
            }
            return status;
        } catch (SQLException sqlEx) {
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
    }
    
    public static void linkNewFollower(int followingProfileID, int currentProfileID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query
                = "INSERT INTO profilefollower (followerProfileID, followingProfileID) "
                + "VALUES (?, ?)";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, currentProfileID);
            ps.setInt(2, followingProfileID);
            
            ps.executeUpdate();
        } catch (SQLException sqlEx) {
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
    }
}
