package flashcards.Services;

import flashcards.Services.impl.UserService.JSONUserImpl;
import flashcards.Services.impl.UserService.SQLUserImpl;
import flashcards.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {


    public static UserService getInstance(String service) throws SQLException {
        if (service.equals("SQL")) {
            return new SQLUserImpl();
        }
        if (service.equals("JSON")) {
            return new JSONUserImpl();
        }
        return null;
    }

    public List<User> getUsers();

    public User getUser(String username) throws SQLException;

    public User createUser(String username, String password) throws SQLException;


    public User updateUserPassword(String username, String password) throws SQLException;

    public User deleteUser(String username);


}
