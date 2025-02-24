package flashcards.Services.impl;

import flashcards.model.FlashCardDeck;
import flashcards.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonLoadSave {

    public static void initialize() {
        users = loadUsers();
    }

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

    public static List<FlashCardDeck> loadFlashCardDecks() {
        File file;
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<FlashCardDeck> rList = new ArrayList<>();
        try {
            file = new File("resources/decks.json");
            List<FlashCardDeck> deckList;
            deckList = mapper.readValue(file, new TypeReference<>() {});
            rList.addAll(deckList);
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle better
        }
        for(FlashCardDeck fcd: rList) {
            System.out.println(fcd.getName() + " " + fcd.getDeckID());
        }
        return rList;
    }





}
