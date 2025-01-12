package flashcards.controller;

import flashcards.MainWrapper;
import flashcards.Services.DeckService;
import flashcards.model.FlashCardDeck;
import flashcards.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.List;

public class ManageFlashController {

    private DeckService deckService;

    private User loggedInUser;

    @FXML
    private ListView deckList;



    public void initialize(User user) {
        deckService = DeckService.getInstance(MainWrapper.SERVICE);
        loggedInUser = user;
        populateDeckList();
    }


    private void populateDeckList() {
        List<String> decks = deckService.getDecks(loggedInUser);
        HashMap<String, FlashCardDeck> deckMap = loggedInUser.getDecks();
        for (String deckID: decks) {
            deckList.getItems().add(deckMap.get(deckID).getName());
        }

    }
}
