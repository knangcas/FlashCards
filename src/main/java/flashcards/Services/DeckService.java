package flashcards.Services;

import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;

public interface DeckService {

    public static DeckService getInstance(String service) {
        if (service.equals("SQL")) {
            //
        }
        return null;
    }

    public FlashCardDeck getDeck(String deckID);

    public boolean createDeck(FlashCardDeck deck);

    public boolean deleteDeck(FlashCardDeck deck);

    public boolean updateDeck(FlashCardDeck deck, FlashCard card);


}
