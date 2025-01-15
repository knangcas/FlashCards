package flashcards.controller;

import flashcards.MainWrapper;
import flashcards.Services.DeckService;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import flashcards.model.User;
import flashcards.model.exception.FlashCardNullException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ManageFlashController {

    private DeckService deckService;

    private User loggedInUser;

    private FlashCardDeck currentDeck;

    private FlashCard currentCard;

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

    @FXML
    private Pane deckDetailsPane;

    @FXML
    private TextField deckNameField;

    @FXML
    private TextField deckSubjectField;

    @FXML
    private Label deckMainLabel;

    @FXML
    private Label currentDeckSubjectLabel;

    @FXML
    private Button editDeckButton;







    public void initialize(User user) {
        deckService = DeckService.getInstance(MainWrapper.SERVICE);
        loggedInUser = user;
        populateDeckList();
        editDeckButton.setDisable(true);
        currentDeckLabel.setText("");
        currentDeckSubjectLabel.setText("");

        deckDetailsPane.setVisible(false);
        cardListPane.setVisible(false);
        cardPane.setVisible(false);
    }




    private void populateDeckList() {
        deckList.getItems().clear();
        List<Integer> decks = deckService.getDecks(loggedInUser);
        quantityDeckLabel.setText(decks.size() + " Decks");
        HashMap<Integer, FlashCardDeck> deckMap = loggedInUser.getDecks();
        HashMap<String, Integer> deckIdMap = new HashMap<>();

        for (int deckID: deckMap.keySet()) {
            deckIdMap.put(deckMap.get(deckID).getName(), deckID);
        }

        for (int deckID: decks) {
            deckList.getItems().add(deckMap.get(deckID).getName());
        }

        deckList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (deckList.getSelectionModel().getSelectedItem() == null) {
                    currentDeckLabel.setVisible(false);
                    cardListPane.setVisible(false);
                    cardPane.setVisible(false);
                    deckDetailsPane.setVisible(false);
                    currentDeckLabel.setText("");
                    currentDeckSubjectLabel.setText("");
                } else {
                    currentDeck = null;
                    currentDeck = deckMap.get(deckIdMap.get(deckList.getSelectionModel().getSelectedItem()));
                    editDeckButton.setDisable(false);
                    deckDetailsPane.setVisible(false);
                    currentDeckLabel.setText("Selected Deck: " + currentDeck.getName());
                    if (currentDeck.getSubject() != null) {
                        currentDeckSubjectLabel.setText("Subject: " + currentDeck.getSubject());
                    }
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

        cardList.getItems().clear();
        System.out.println(cardList.getItems().size());

        List<FlashCard> cards = currentDeck.getCardList();
        quantityCardLabel.setText(cards.size() + " Cards");

        for (int i = 0; i < cards.size(); i++) {
            cardList.getItems().add(cards.get(i).getQuestion());
        }

        cardList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (cardList.getSelectionModel().getSelectedItem() == null) {
                    cardPane.setVisible(false);
                } else {
                    cardPane.setVisible(true);


                    if (cards.get(cardList.getSelectionModel().getSelectedIndex()).getQuestion().equals(cardList.getSelectionModel().getSelectedItem())) {
                        currentCard = cards.get(cardList.getSelectionModel().getSelectedIndex());
                        populateQandA();
                    }
                }
            }
        });


    }

    private void populateQandA() {
        questionTextArea.setText(currentCard.getQuestion());
        answerTextArea.setText(currentCard.getAnswer());
    }


    public void saveCard(ActionEvent actionEvent) {
        currentCard.setQuestion(questionTextArea.getText());
        currentCard.setAnswer(answerTextArea.getText());
        try {
            if (deckService.updateCard(currentCard)) {
                System.out.println("Updated Card " + currentCard.getCardID());
            }
        } catch (SQLException e){
            // TODO alert connection issues
        } catch (FlashCardNullException e) {
            // TODO do an alert saying card doesn't exist
        }
    }

    public void addCard(ActionEvent actionEvent) {
        //addCard to currentDeck
    }

    public void removeCard(ActionEvent actionEvent) {
        //just remove card
    }

    public void removeDeck(ActionEvent actionEvent) {
        //cascade delete enabled in DB
        //if not, delete manually
    }

    public void addDeck(ActionEvent actionEvent) {
        showDeckPane();
        deckList.getSelectionModel().clearSelection();
        currentDeck = null;


    }

    public void editDeck(ActionEvent actionEvent) {
        showDeckPane();

    }

    private void showDeckPane() {
        deckDetailsPane.setVisible(true);
        cardPane.setVisible(false);
        cardListPane.setVisible(false);
    }


    public void saveDeck(ActionEvent actionEvent) throws SQLException {
        if (currentDeck != null) {
            deckMainLabel.setText("Edit Deck");
            if (!deckNameField.getText().isEmpty()) {
                currentDeck.setName(deckNameField.getText());
                currentDeck.setSubject(deckSubjectField.getText());
            }
            deckService.updateDeck(currentDeck);
        } else {
            deckMainLabel.setText("New Deck");
            FlashCardDeck newDeck = new FlashCardDeck(deckNameField.getText());
            if (!deckSubjectField.getText().isEmpty()) {
                newDeck.setSubject(deckSubjectField.getText());
            }
            deckService.createDeck(newDeck, loggedInUser.getUsername());

        }
    }

    public void cancelDeck(ActionEvent actionEvent) {
        deckDetailsPane.setVisible(false);
        if (currentDeck != null) {
            cardListPane.setVisible(true);
            if (currentCard != null) {
                cardPane.setVisible(true);
            }
        }
    }

}
