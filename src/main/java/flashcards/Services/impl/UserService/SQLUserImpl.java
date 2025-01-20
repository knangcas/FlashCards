package flashcards.Services.impl.UserService;

import flashcards.Services.UserService;
import flashcards.Services.impl.SQLVariables;
import flashcards.model.User;
import flashcards.model.exception.FlashcardsConnectionException;

import java.sql.*;


import java.util.List;

public class SQLUserImpl implements UserService {

    Connection conn;


    public SQLUserImpl() throws SQLException {

    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(SQLVariables.SQLADDRESS, SQLVariables.SQLUSER, SQLVariables.SQLPASSWORD);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new FlashcardsConnectionException("Failed to connect");
        }

    }
    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(String username) throws SQLException {
        try {
            connect();
        } catch (FlashcardsConnectionException e) {
            throw new FlashcardsConnectionException("");
        }
        User rval = null;
        String password;
        String query = "SELECT * FROM USERS WHERE username = ?";
        PreparedStatement preparedStatement = null;
        String corrected = username;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, corrected);
            //Statement statement = conn.createStatement();
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println(preparedStatement.toString());

            while (resultSet.next()) {
                if (resultSet.getString(1).equals(username)) {
                    rval = new User(username, "whatever");
                    rval.setByteLength1(resultSet.getBytes(3).length);
                    System.out.println("from db salthashlength: " + resultSet.getBytes(3).length);
                    rval.setSH(resultSet.getBytes(2), resultSet.getBytes(3));
                    return rval;
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("There was an error fetching data");

            //could probably do an alert popup too
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }


        }
        return null;
    }

    @Override
    public User createUser(String username, String password) throws SQLException {

        connect();
        String query = "INSERT INTO USERS VALUES (?, ?)";
        PreparedStatement preparedStatement = null;
        //prepared for anti sql injection

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            System.out.println(preparedStatement.executeUpdate());
            conn.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already exists");
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return getUser(username);
    }

    @Override
    public User updateUserPassword(String username, String password) throws SQLException {
        connect();
        String query = "UPDATE USERS SET password = ? WHERE username = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException();
            }
            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            //TODO
        }  finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return getUser(username);
    }

    @Override
    public User deleteUser(String username) {
        //TODO
        //cascade effect, because cards FK = deck, and deck FK = user.
        //delete user = delete cards, decks, then user.


        return null;
    }

    public void addUser(byte[] salt, byte[] saltHash, String username) throws SQLException {
        connect();

        String query = "INSERT INTO USERS (username, salt, saltHash) VALUES(?,?,?)";
        PreparedStatement preparedStatement = null;
        int result;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setBytes(2,salt);
            preparedStatement.setBytes(3,saltHash);
            result = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public void updateUser(byte[] salt, byte[] saltHash, String username) throws SQLException {
        connect();

        String query = "UPDATE users SET salt = ?, saltHash = ? WHERE username = ?";

        PreparedStatement preparedStatement = null;
        int result;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(3, username);
            preparedStatement.setBytes(1,salt);
            preparedStatement.setBytes(2,saltHash);
            result = preparedStatement.executeUpdate();

            System.out.println("thisresult :" + result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
