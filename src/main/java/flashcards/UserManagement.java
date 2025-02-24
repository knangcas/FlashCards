package flashcards;

import flashcards.Services.UserService;
import flashcards.Services.impl.JsonLoadSave;
import flashcards.model.User;
import flashcards.model.exception.FlashcardsConnectionException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class UserManagement {

    private static User activeUser;

    private static HashMap<String, String> userMap;
    public static void loadUsers() {

        userMap = new HashMap<>();

        //temporary until DB implementation
        User user1 = new User("admin", "password");
        User user2 = new User("admin2", "password2");





        userMap.put(user1.getUsername(), user1.getPassword());
        userMap.put(user2.getUsername(), user2.getPassword());

    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void offlineUser() throws SQLException {
        UserService userService = UserService.getInstance("JSON");
        assert userService != null;
        activeUser = userService.getUser("Offline User");
    }

    public static boolean validateUser(String username, String password) throws SQLException {

        //TODO salt passwords

        UserService userService = UserService.getInstance(MainWrapper.SERVICE);

        User user = null;
        if (userService != null) {
            try {
                user = userService.getUser(username);
            } catch (FlashcardsConnectionException e) {
                throw new FlashcardsConnectionException();
            }
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    activeUser = user;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        return false;

    }


}
