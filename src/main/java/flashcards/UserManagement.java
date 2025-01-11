package flashcards;

import flashcards.Services.UserService;
import flashcards.model.User;
import flashcards.model.exception.FlashcardsConnectionException;

import java.sql.SQLException;
import java.util.HashMap;

public class UserManagement {

    private static HashMap<String, String> userMap;
    public static void loadUsers() {

        userMap = new HashMap<>();

        //temporary until DB implementation
        User user1 = new User("admin", "password");
        User user2 = new User("admin2", "password2");

        userMap.put(user1.getUsername(), user1.getPassword());
        userMap.put(user2.getUsername(), user2.getPassword());

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
