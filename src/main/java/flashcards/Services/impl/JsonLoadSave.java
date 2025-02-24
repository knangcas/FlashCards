package flashcards.Services.impl;

import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import flashcards.model.User;

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

    public static void initialize() {
        users = new HashMap<>();
        decks = new HashMap<>();
        loadUsers();
        loadFlashCardDecks();
        loadCards();
        loadDecksToUser();
    }

    public static HashMap<String, User> users;
    public static HashMap<Integer, FlashCardDeck> decks;
    public static List<FlashCard> cardList;
    public static List<User> userList;

    public static List<FlashCardDeck> deckList;

    private static void loadUsers() {
        File file;
        ObjectMapper mapper = new ObjectMapper();

        List<User> userList;
        try {
            file = new File("resources/users.json");

            userList = mapper.readValue(file, new TypeReference<>() {});
            for(User u: userList) {
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

        List<FlashCardDeck> deckList;
        try {
            file = new File("resources/decks.json");
            deckList = mapper.readValue(file, new TypeReference<>() {});
            for (FlashCardDeck fcd : deckList) {
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
        List<FlashCard> cardList;
        try {
            file = new File("resources/cards.json");

            cardList = mapper.readValue(file, new TypeReference<>() {});
            for (FlashCard card : cardList) {
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

        try {
            objectMapper.writeValue(new File("resources/users.json"), userList);
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle  better
        }
    }

    public static void saveFlashCards() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File("resources/cards.json"), cardList);
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle better
        }
    }

    public static void saveDecks() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File("resources/decks.json"), deckList);
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle
        }

    }





}
