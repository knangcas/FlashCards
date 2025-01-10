package flashcards.Services.impl.DeckService;

import flashcards.Services.DeckService;
import flashcards.Services.impl.SQLVariables;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;

import java.sql.*;

public class SQLDeckImpl implements DeckService {

    Connection conn;

    public SQLDeckImpl() {}

    private void connect() {
        try {
            conn = DriverManager.getConnection(SQLVariables.SQLADDRESS, SQLVariables.SQLUSER, SQLVariables.SQLPASSWORD);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("could not connect");
        }

    }

    @Override
    public FlashCardDeck getDeck(String deckID) {

        connect();

        FlashCardDeck rval = new FlashCardDeck("");

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM CARDS WHERE deckID = ?";
        try {
            ps1 = conn.prepareStatement(query);
            ps1.setString(1, deckID);
            resultSet = ps1.executeQuery();

            //question, answer, deckID (FK), cardID (PK)
            while(resultSet.next()) {
                FlashCard flashCard;
                flashCard = new FlashCard(resultSet.getString(1), resultSet.getString(2));
                flashCard.setDeckID(deckID);
                flashCard.setCardID(resultSet.getString(4));
                rval.addFlashCard(flashCard);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rval;
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
