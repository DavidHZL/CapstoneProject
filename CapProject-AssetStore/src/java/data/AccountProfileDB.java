/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;
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
}
