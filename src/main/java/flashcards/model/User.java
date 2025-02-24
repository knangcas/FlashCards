package flashcards.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    private HashMap<Integer, FlashCardDeck> decks;

    public User(){}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        decks = new HashMap<>();
    }

    public HashMap<Integer, FlashCardDeck> getDecks() {
        return decks;
    }

    public void addDeck(int deckID, FlashCardDeck deck) {
        decks.put(deckID, deck);
    }

    public int getDeckQuantity() {
        return decks.size();
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
