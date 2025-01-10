package flashcards.Services.impl.DeckService;

import flashcards.Services.DeckService;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDeckImpl implements DeckService {

    final String URL = "com.mysql.cj.jdbc.Driver";

    final String SQLADDRESS = "jdbc:mysql://localhost:3306/flashcards";
    //this just connects to local sql server because AWS = $$
    final String SQLUSER = "root";

    final String SQLPASSWORD = "password2!";

    Connection conn;

    public SQLDeckImpl() {}

    private void connect() {
        try {
            conn = DriverManager.getConnection(SQLADDRESS, SQLUSER, SQLPASSWORD);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("could not connect");
        }

    }

    @Override
    public FlashCardDeck getDeck(String deckID) {
        return null;
    }

    @Override
    public boolean createDeck(FlashCardDeck deck) {
        return false;
    }

    @Override
    public boolean deleteDeck(FlashCardDeck deck) {
        return false;
    }

    @Override
    public boolean updateDeck(FlashCardDeck deck, FlashCard card) {
        return false;
    }
}
