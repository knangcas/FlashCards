package flashcards;

import flashcards.Services.UserService;
import flashcards.Services.impl.DeckService.SQLDeckImpl;
import flashcards.Services.impl.UserService.SQLUserImpl;
import flashcards.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Arrays;

public class MainWrapper {
    final public static String SERVICE = "SQL";
    public static void main(String[] args) throws SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        String password = "password";
        SecureRandom random = new SecureRandom();
        //User user = UserService.getInstance(SERVICE).getUser("admin");
        byte[] salt = new byte[16];
        //salt = user.getSalt();
        //byte[] sh = user.getSaltHash();
        salt[0] = -119;
        salt[1] = 47;
        salt[2] = -39;
        salt[3] = 5;
        salt[4] = 27;
        salt[5] = -106;
        salt[6] = 11;
        salt[7] = 18;
        salt[8] = 55;
        salt[9] = 71;
        salt[10] = 115;
        salt[11] = -75;
        salt[12] = -56;
        salt[13] = 15;
        salt[14] = 48;
        salt[15] = -23;


        //random.nextBytes(salt);

        System.out.println(Arrays.toString(salt));
        //System.out.println(new String(salt, StandardCharsets.UTF_8));

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
       messageDigest.update(salt);


        byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        System.out.println(Arrays.toString(hash));
        //System.out.println(hash.length);
        //System.out.println(sh.length);

        //System.out.println(new String(hash, StandardCharsets.UTF_8));

        //SQLUserImpl s = new SQLUserImpl();

        //s.updateUser(salt, hash, "admin");

        //HelloApplication.main(args);
    }
}
