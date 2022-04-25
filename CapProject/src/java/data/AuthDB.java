/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Account;
import model.Profile;

/**
 *
 * @author Dadvid
 */
public class AuthDB {
    public static String createAccount(Account user, String salt, String hash) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        String keyValue = "";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String query
                = "INSERT INTO account (username, accountType, salt, hash) "
                + "VALUES (?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getAccountType());
            statement.setString(3, salt);
            statement.setString(4, hash);
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.next();
                    keyValue = resultSet.getString(1);
                    
                    resultSet.close();
                    statement.close();
                }
                pool.freeConnection(connection);
            } catch (SQLException ex) {
                throw ex;
            }
        }
        return keyValue;
    }
    
    public static String createProfile (String profileName, String email, ArrayList<String> errorList ) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        String keyValue = "";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String query
                = "INSERT INTO profile (profileName, email) "
                + "VALUES (?, ?)";
        
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, profileName);
            statement.setString(2, email);
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.next();
                    keyValue = resultSet.getString(1);
                    
                    resultSet.close();
                    statement.close();
                }
                pool.freeConnection(connection);
            } catch (SQLException ex) {
                throw ex;
            }
        }
        
        return keyValue;
    }
    
    public static void createAccountProfileLink(Account account, Profile profile, ArrayList<String> errorList) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        String query
                = "INSERT INTO accountprofile (accountID, profileID) "
                + "VALUES (?, ?)";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, account.getAccountID());
            statement.setInt(2, profile.getProfileID());
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

     public static Account loginUser(String username, String password) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Account user = null;

        String query = "SELECT * FROM account WHERE username = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            resultSet.next();

            if (compareHash(password, resultSet.getString("salt"), resultSet.getString("hash"))) {
                user = new Account();

                user.setAccountID(resultSet.getInt("accountID"));
                user.setUsername(resultSet.getString("userName"));
                user.setAccountType(resultSet.getString("accountType"));
            }
            
            return user;
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
    
    public static Boolean doesUserExist(String userName) throws SQLException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM account WHERE username = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            resultSet = statement.executeQuery();

            return resultSet.next();
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
    
    // Password Hashing methods
    private static Boolean compareHash(String passwordInput, String salt, String hashStored){
        try {
            String hashInput = hashPassword(passwordInput, salt);
            return hashInput.equals(hashStored);
        } catch (NoSuchAlgorithmException ex) {
            return false;
        }
    }
    
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException{
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            md.update(salt.getBytes());
            md.update(password.getBytes());
            
            byte[] byteHash = md.digest();
            return bytesToHex(byteHash);
        } catch (NoSuchAlgorithmException ex) {
            throw ex;
        }
    }
    
   /* This is a borrowed method for changing a byte array to a hex string
    * https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    */
    
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
