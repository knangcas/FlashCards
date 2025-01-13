package flashcards.controller;

import flashcards.MainWrapper;
import flashcards.Services.DeckService;
import flashcards.model.FlashCardDeck;
import flashcards.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.List;

public class ManageFlashController {

    private DeckService deckService;

    private User loggedInUser;

    private FlashCardDeck currentDeck;

    @FXML
    private ListView deckList;

    @FXML
    private Label quantityDeckLabel;

    @FXML
    private ListView cardList;





    public void initialize(User user) {
        deckService = DeckService.getInstance(MainWrapper.SERVICE);
        loggedInUser = user;
        populateDeckList();
    }


    private void populateDeckList() {
        List<String> decks = deckService.getDecks(loggedInUser);
        quantityDeckLabel.setText(decks.size() + " Decks");
        HashMap<String, FlashCardDeck> deckMap = loggedInUser.getDecks();
        HashMap<String, String> deckIdMap = new HashMap<>();

        for (String s: deckMap.keySet()) {
            deckIdMap.put(deckMap.get(s).getName(), deckMap.get(s).getDeckID());
        }

        for (String deckID: decks) {
            deckList.getItems().add(deckMap.get(deckID).getName());
        }

        deckList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentDeck = deckMap.get(deckIdMap.get(deckList.getSelectionModel().getSelectedItem()));
            }
        });

    }

    private void populateCards() {

    }


}
