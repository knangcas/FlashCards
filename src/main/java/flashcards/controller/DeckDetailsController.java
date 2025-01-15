package flashcards.controller;

import flashcards.model.FlashCardDeck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DeckDetailsController {

    @FXML
    private TextField deckNameField;

    @FXML
    private TextField deckSubjectField;

    @FXML
    private Label deckDetailsLabel;

    @FXML
    private Button saveButton;

    private boolean isNewDeck;


    public void initialize(FlashCardDeck deck) {
        deckNameField.setText(deck.getName());
        deckSubjectField.setText(deck.getSubject());
        isNewDeck = false;
    }

    public void initialize() {
        deckDetailsLabel.setText("New Deck");
        saveButton.setText("OK");
        isNewDeck = true;
    }

    public void saveButton(ActionEvent actionEvent) {


    }

    public void cancelButton(ActionEvent actionEvent) {
    }
}
