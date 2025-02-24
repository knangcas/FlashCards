package flashcards.Services.impl;

import flashcards.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonLoadSave {

    public static List<User> users;
    public static List<User> loadUsers() {
        File file;
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> rList = new ArrayList<>();
        try {
            file = new File("resources/users.json");
            List<User> userList;
            userList = mapper.readValue(file, new TypeReference<>() {});
            rList.addAll(userList);
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle better
        }
        for(User u: rList) {
            System.out.println(u.getUsername() + " " + u.getPassword());
        }
        return rList;

    }

}
