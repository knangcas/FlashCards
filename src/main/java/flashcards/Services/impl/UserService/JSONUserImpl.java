package flashcards.Services.impl.UserService;

import flashcards.Services.UserService;
import flashcards.Services.impl.JsonLoadSave;
import flashcards.model.User;

import java.sql.SQLException;
import java.util.List;

public class JSONUserImpl implements UserService {



    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(String username) throws SQLException {
        return JsonLoadSave.users.get(username);
    }

    @Override
    public User createUser(String username, String password) throws SQLException {
        User newUser = new User(username,password);
        JsonLoadSave.userList.add(newUser);
        JsonLoadSave.repopulateObjects();
        return newUser;
    }

    @Override
    public User updateUserPassword(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public User deleteUser(String username) {
        return null;
    }
}
