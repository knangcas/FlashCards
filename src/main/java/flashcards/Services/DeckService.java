package flashcards.Services;

import flashcards.Services.impl.DeckService.SQLDeckImpl;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import flashcards.model.User;

import java.sql.SQLException;
import java.util.List;

public interface DeckService {

    public static DeckService getInstance(String service) {
        if (service.equals("SQL")) {
            return new SQLDeckImpl();
        }
        return null;
    }

    public FlashCardDeck getDeck(int deckID) throws SQLException;

    public List<Integer> getDecks(User user);

    public boolean createDeck(FlashCardDeck deck);

    public boolean deleteDeck(FlashCardDeck deck);

    public boolean updateDeck(FlashCardDeck deck) throws SQLException;

    public boolean updateCard(FlashCard card) throws SQLException;

    public boolean addCard(FlashCard card) throws SQLException;

    public boolean deleteCard(int cardID) throws SQLException;




}
