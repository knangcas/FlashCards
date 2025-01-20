package flashcards.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private String username;

    private String password;

    private byte[] salt;
    private byte[] saltHash;

    public String getUsername() {
        return username;
    }

    public HashMap<Integer, FlashCardDeck> decks;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        decks = new HashMap<>();
        salt = new byte[16];
    }

    public void setByteLength1(int n){
        saltHash = new byte[n];
    }

    public void setSH(byte[] salt, byte[] saltHash){
        for (int i = 0; i < salt.length; i++) {
            this.salt[i] = salt[i];
        }
        for (int i = 0; i < saltHash.length; i++) {
            this.saltHash[i] = saltHash[i];
        }

    }

    public byte[] getSaltHash() {
        return this.saltHash;
    }

    public byte[] getSalt() {
        return this.salt;
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
