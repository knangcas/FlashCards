package flashcards.Services.impl.DeckService;

import flashcards.Services.DeckService;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import flashcards.model.User;

import java.sql.SQLException;
import java.util.List;

public class JSONDeckImpl implements DeckService {

    @Override
    public FlashCardDeck getDeck(int deckID) throws SQLException {
        return null;
    }

    @Override
    public List<Integer> getDecks(User user) {
        return null;
    }

    @Override
    public int createDeck(FlashCardDeck deck, String username) throws SQLException {
        return 0;
    }

    @Override
    public boolean deleteDeck(FlashCardDeck deck) throws SQLException {
        return false;
    }

    @Override
    public boolean updateDeck(FlashCardDeck deck) throws SQLException {
        return false;
    }

    @Override
    public boolean updateCard(FlashCard card) throws SQLException {
        return false;
    }

    @Override
    public boolean addCard(FlashCard card) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteCard(int cardID) throws SQLException {
        return false;
    }
}
