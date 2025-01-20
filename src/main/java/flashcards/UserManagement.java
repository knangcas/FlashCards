package flashcards;

import flashcards.Services.UserService;
import flashcards.model.User;
import flashcards.model.exception.FlashcardsConnectionException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.HashMap;

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

    public void saltPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = "password";
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1000, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        System.out.println(hash);
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
