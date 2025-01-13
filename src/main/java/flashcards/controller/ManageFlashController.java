package flashcards.controller;

import flashcards.MainWrapper;
import flashcards.Services.DeckService;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import flashcards.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

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

    @FXML
    private Label currentDeckLabel;

    @FXML
    private Label quantityCardLabel;

    @FXML
    private TextArea questionTextArea;

    @FXML
    private TextArea answerTextArea;

    @FXML
    private Label questionLabel;

    @FXML
    private Label answerLabel;

    @FXML
    private Pane cardPane;

    @FXML
    private Pane cardListPane;






    public void initialize(User user) {
        deckService = DeckService.getInstance(MainWrapper.SERVICE);
        loggedInUser = user;
        populateDeckList();
        cardListPane.setVisible(false);
        cardPane.setVisible(false);
    }


    private void populateDeckList() {
        List<String> decks = deckService.getDecks(loggedInUser);
        quantityDeckLabel.setText(decks.size() + " Decks");
        HashMap<String, FlashCardDeck> deckMap = loggedInUser.getDecks();
        HashMap<String, String> deckIdMap = new HashMap<>();

        for (String s: deckMap.keySet()) {
            deckIdMap.put(deckMap.get(s).getName(), s);
        }

        for (String deckID: decks) {
            deckList.getItems().add(deckMap.get(deckID).getName());
        }

        deckList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (deckList.getSelectionModel().getSelectedItem() == null) {
                    currentDeckLabel.setVisible(false);
                    cardListPane.setVisible(false);
                    cardPane.setVisible(false);
                } else {
                    currentDeck = deckMap.get(deckIdMap.get(deckList.getSelectionModel().getSelectedItem()));
                    currentDeckLabel.setText("Current Deck: " + currentDeck.getName());
                    cardListPane.setVisible(true);
                    cardList.setVisible(true);
                    populateCards();

                }
            }
        });

    }

    private void populateCards() {


        //TODO have to populate cards
        //easiest way i can think of is make another list
        //within deck that has a copy of all of the cards
        //within the internal stacks of the data structure


    }




}
