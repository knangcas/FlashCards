package dealer.dealerproject;

import dealer.dealerproject.model.User;

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

    public static boolean validateUser(String username, String password) {
        if (userMap.containsKey(username)) {
            if (userMap.get(username).equals(password)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


}
