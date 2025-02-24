package flashcards.Services.impl.DeckService;

import flashcards.Services.DeckService;
import flashcards.Services.impl.JsonLoadSave;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import flashcards.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JSONDeckImpl implements DeckService {

    @Override
    public FlashCardDeck getDeck(int deckID) throws SQLException {
        return JsonLoadSave.decks.get(deckID);
    }

    @Override
    public List<Integer> getDecks(User user) {
        Set<Integer> decks = JsonLoadSave.users.get(user.getUsername()).getDecks().keySet();
        return new ArrayList<>(decks);
    }

    @Override
    public int createDeck(FlashCardDeck deck, String username) throws SQLException {
        int id = JsonLoadSave.deckList.size() + 1;
        deck.setDeckID(id);
        deck.setUsername(username);
        JsonLoadSave.deckList.add(deck);
        JsonLoadSave.repopulateObjects();
        return JsonLoadSave.decks.get(id).getDeckID();
    }

    @Override
    public boolean deleteDeck(FlashCardDeck deck) throws SQLException {
        JsonLoadSave.decks.remove(deck.getDeckID());
        for (int i = 0; i < JsonLoadSave.cardList.size(); i++) {
            if (JsonLoadSave.cardList.get(i).getDeckID() == deck.getDeckID()) {
                JsonLoadSave.cardList.remove(i);
                i--;
            }
        }

        for (int i = 0; i < JsonLoadSave.deckList.size(); i++) {
            if (JsonLoadSave.deckList.get(i).getDeckID() == deck.getDeckID()) {
                JsonLoadSave.deckList.remove(i);
                break;
            }
        }
        JsonLoadSave.repopulateObjects();
        return false;
    }

    @Override
    public boolean updateDeck(FlashCardDeck deck) throws SQLException {
        for (FlashCardDeck fcd : JsonLoadSave.deckList) {
            if (fcd.getDeckID() == deck.getDeckID()) {
                fcd.setName(deck.getName());
                fcd.setSubject(deck.getSubject());
                break;
            }
        }
        JsonLoadSave.repopulateObjects();
        return false;
    }

    @Override
    public boolean updateCard(FlashCard card) throws SQLException {
        for (FlashCard fc: JsonLoadSave.cardList) {
            if (fc.getCardID() == card.getCardID()) {
                fc.setQuestion(card.getQuestion());
                fc.setAnswer(card.getAnswer());
                break;
            }
        }
        JsonLoadSave.repopulateObjects();
        return false;
    }

    @Override
    public boolean addCard(FlashCard card) throws SQLException {
        int id = JsonLoadSave.cardList.size() + 1;
        card.setCardID(id);
        JsonLoadSave.cardList.add(card);

        JsonLoadSave.repopulateObjects();
        return false;
    }

    @Override
    public boolean deleteCard(int cardID) throws SQLException {
        for (int i = 0; i < JsonLoadSave.cardList.size(); i++) {
            if (JsonLoadSave.cardList.get(i).getCardID() == cardID) {
                JsonLoadSave.cardList.remove(i);
                break;
            }
        }
        JsonLoadSave.repopulateObjects();
        return false;
    }
}
