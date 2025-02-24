package flashcards.Services.impl;

import flashcards.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonLoadSave {

    public static boolean INITIALIZED = false;

    public static void initialize() {
        users = new HashMap<>();
        decks = new HashMap<>();
        cardList = new ArrayList<>();
        userList = new ArrayList<>();
        deckList = new ArrayList<>();
        loadUsers();
        loadFlashCardDecks();
        loadCards();
        loadDecksToUser();
        INITIALIZED = true;
    }

    public static HashMap<String, User> users;
    public static HashMap<Integer, FlashCardDeck> decks;
    public static List<FlashCard> cardList;
    public static List<User> userList;

    public static List<FlashCardDeck> deckList;

    private static void loadUsers() {
        File file;
        ObjectMapper mapper = new ObjectMapper();

        List<User> uList;
        try {
            file = new File("resources/users.json");

            uList = mapper.readValue(file, new TypeReference<>() {});
            for(User u: uList) {
                users.put(u.getUsername(), u);
                userList.add((u));
            }
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle better
        }





    }

    private static void loadFlashCardDecks() {
        File file;
        ObjectMapper mapper = new ObjectMapper();

        List<FlashCardDeck> dList;
        try {
            file = new File("resources/decks.json");
            dList = mapper.readValue(file, new TypeReference<>() {});
            for (FlashCardDeck fcd : dList) {
                decks.put(fcd.getDeckID(), fcd);
                deckList.add(fcd);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle better
        }


    }

    private static void loadCards() {
        File file;
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<FlashCard> rList = new ArrayList<>();
        List<FlashCard> cList;
        try {
            file = new File("resources/cards.json");

            cList = mapper.readValue(file, new TypeReference<>() {});
            for (FlashCard card : cList) {
                decks.get(card.getDeckID()).addFlashCard(card);
                cardList.add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle better
        }




    }

    private static void loadDecksToUser(){
        for (Map.Entry<Integer, FlashCardDeck> deck : decks.entrySet()) {
            users.get(deck.getValue().getUsername()).addDeck(deck.getKey(), deck.getValue());
        }
    }

    public static void repopulateObjects() {
        saveUsers();
        saveDecks();
        saveFlashCards();
        decks = null;
        users = null;
        initialize();
    }

    public static void saveUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<UserShell> save = new ArrayList<>();
        for (User u: userList) {
            save.add(new UserShell(u.getUsername(), u.getPassword()));
        }

        try {
            objectMapper.writeValue(new File("resources/users.json"), save);
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle  better
        }
    }

    public static void saveFlashCards() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<FlashCardShell> save = new ArrayList<>();
        for (FlashCard fc : cardList) {
            save.add(new FlashCardShell(fc.getQuestion(), fc.getAnswer(), fc.getDeckID(), fc.getCardID()));
        }
        try {
            objectMapper.writeValue(new File("resources/cards.json"), save);
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle better
        }
    }

    public static void saveDecks() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<FlashCardDeckShell> save = new ArrayList<>();
        for(FlashCardDeck fcd: deckList) {
            save.add(new FlashCardDeckShell(fcd.getName(), fcd.getSubject(), fcd.getUsername(), fcd.getDeckID()));
        }
        try {
            objectMapper.writeValue(new File("resources/decks.json"), save);
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle
        }

    }





}
