package flashcards.Services.impl.UserService;

import flashcards.Services.UserService;
import flashcards.model.User;
import java.sql.*;


import java.util.List;

public class SQLimpl implements UserService {
    final String URL = "com.mysql.cj.jdbc.Driver";

    final String SQLADDRESS = "jdbc:mysql://localhost:3306/flashcards";
    //this just connects to local sql server because AWS = $$
    final String SQLUSER = "root";

    final String SQLPASSWORD = "password2!";

    Connection conn;


    public SQLimpl() throws SQLException {

    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(SQLADDRESS, SQLUSER, SQLPASSWORD);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("could not connect");
        }

    }
    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(String username) throws SQLException {
        connect();
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
                    rval = new User(username, resultSet.getString(2));
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
}
