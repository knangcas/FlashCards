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
import javafx.scene.input.KeyEvent;
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
    private Button removeCardButton;

    @FXML
    private Button removeDeckButton;

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

    @FXML
    private Button saveCardButton;

    @FXML
    private Label successLabel;

    @FXML
    private Pane successPane;

    private List<FlashCard> cards;

    private List<Integer> userDecks;

    HashMap<Integer, FlashCardDeck> deckMap;
    HashMap<String, Integer> deckIdMap;









    public void initialize(User user) throws SQLException {
        deckService = DeckService.getInstance(MainWrapper.SERVICE);
        loggedInUser = user;
        userPopulateDeck();
        populateDeckList();
        editDeckButton.setDisable(true);
        currentDeckLabel.setText("");
        currentDeckSubjectLabel.setText("");
        successPane.setVisible(false);

        deckDetailsPane.setVisible(false);
        cardListPane.setVisible(false);
        cardPane.setVisible(false);
        saveCardButton.setDisable(true);
        removeDeckButton.setDisable(true);
        removeCardButton.setDisable(true);


    }

    private void userPopulateDeck() throws SQLException {
        List<Integer> decks = deckService.getDecks(loggedInUser);
        for (int i : decks) {
            System.out.println(i);
        }
        for (int i : decks) {
            if (!loggedInUser.getDecks().containsKey(i)) {
                loggedInUser.addDeck(i, deckService.getDeck(i));
                System.out.println(i);
            }
        }
    }




    private void populateDeckList() throws SQLException {
        deckList.getItems().clear();
        List<Integer> decks = deckService.getDecks(loggedInUser);
        quantityDeckLabel.setText(loggedInUser.getDeckQuantity() + " Decks");
        //userPopulateDeck();
        deckMap = loggedInUser.getDecks();
        deckIdMap = new HashMap<>();

        for (int deckID: deckMap.keySet()) {
            //deckMap.get(deckID).setDeckID(deckID);
            deckIdMap.put(deckMap.get(deckID).getName(), deckID);
        }

        for (FlashCardDeck fcd: deckMap.values()) {
            System.out.println("fffff " + fcd.getDeckID());
        }

        System.out.println(deckMap);

        for (String s: deckIdMap.keySet()) {
            System.out.println(s);
        }
        System.out.println("deckID of newest: " + deckIdMap.get("newer"));


        for (int deckID: decks) {
            System.out.println("dfdfd " + deckID);
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
                    removeDeckButton.setDisable(true);
                } else {
                    currentDeck = null;
                    System.out.println("deckCurrent: " + deckMap.get(deckIdMap.get((String)deckList.getSelectionModel().getSelectedItem())).getDeckID());
                    currentDeck = deckMap.get(deckIdMap.get(deckList.getSelectionModel().getSelectedItem()));
                    editDeckButton.setDisable(false);
                    deckDetailsPane.setVisible(false);
                    removeDeckButton.setDisable(false);
                    currentDeckLabel.setText("Selected Deck: " + currentDeck.getName());
                    if (currentDeck.getSubject() != null) {
                        currentDeckSubjectLabel.setText("Subject: " + currentDeck.getSubject());
                    }
                    cardListPane.setVisible(true);
                    cardList.setVisible(true);
                    System.out.println("Deck Selected Detaiils : " + currentDeck.getName() + " id: " + currentDeck.getDeckID());
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

        cards = currentDeck.getCardList();
        quantityCardLabel.setText(cards.size() + " Cards");

        for (int i = 0; i < cards.size(); i++) {
            cardList.getItems().add(cards.get(i).getQuestion());
        }



        cardList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (cardList.getSelectionModel().getSelectedItem() == null) {
                    cardPane.setVisible(false);
                    removeCardButton.setDisable(true);

                } else {
                    cardPane.setVisible(true);
                    removeCardButton.setDisable(false);

                    if (cards.get(cardList.getSelectionModel().getSelectedIndex()).getQuestion().equals(cardList.getSelectionModel().getSelectedItem())) {
                        System.out.println("passed");
                        System.out.println("cards.size = " + cards.size() + "selected index = " + cardList.getSelectionModel().getSelectedIndex());
                        currentCard = cards.get(cardList.getSelectionModel().getSelectedIndex());
                        populateQandA();
                    }
                }
            }
        });

    }

    private void updateCardList() {
        cards.clear();
        cards = currentDeck.getCardList();
        cardList.getItems().clear();

        for (int i = 0; i < cards.size(); i++) {
            cardList.getItems().add(cards.get(i).getQuestion());
        }
    }



    private void populateQandA() {
        questionTextArea.setText(currentCard.getQuestion());
        answerTextArea.setText(currentCard.getAnswer());
    }


    public void saveCard(ActionEvent actionEvent) {
        System.out.println("saved button clicked");
        if (currentCard != null) {
            currentCard.setQuestion(questionTextArea.getText());
            currentCard.setAnswer(answerTextArea.getText());
            try {
                if (deckService.updateCard(currentCard)) {
                    System.out.println("Updated Card " + currentCard.getCardID());
                    cardList.getItems().set(cardList.getSelectionModel().getSelectedIndex(), questionTextArea.getText());
                    //updateCardList();
                    //populateCards();
                    successLabelChange("Card Saved!");
                }
            } catch (SQLException e) {
                // TODO alert connection issues
            } catch (FlashCardNullException e) {
                // TODO do an alert saying card doesn't exist
            }
        } else {
            if (!questionTextArea.getText().isEmpty() && !answerTextArea.getText().isEmpty()) {
                FlashCard newCard = new FlashCard(questionTextArea.getText(), answerTextArea.getText());
                newCard.setDeckID(currentDeck.getDeckID());
                System.out.println("deckID is " + newCard.getDeckID());
                try {
                    deckService.addCard(newCard);
                    //currentDeck = deckService.getDeck(newCard.getDeckID());
                    addCardToDeck(newCard);
                    successLabelChange("Card Added!");
                    //populateCards();
                    //currentDeck.addFlashCard(newCard);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("todo alert");
            }
        }
    }

    private void addCardToDeck(FlashCard new_card) {
        currentDeck.addFlashCard(new_card);
        cardList.getItems().add(new_card.getQuestion());
        quantityCardLabel.setText(cardList.getItems().size() + " Cards");
    }

    public void addCard(ActionEvent actionEvent) {
        currentCard = null;
        cardList.getSelectionModel().clearSelection();
        cardPane.setVisible(true);
        questionTextArea.clear();
        answerTextArea.clear();
        saveCardButton.setDisable(true);

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
        deckMainLabel.setText("New Deck");


    }

    public void editDeck(ActionEvent actionEvent) {
        showDeckPane();
        deckMainLabel.setText("Edit Deck");

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
            int index = deckList.getSelectionModel().getSelectedIndex();
            deckList.getItems().set(index, deckNameField.getText());
            successLabelChange("Deck Updated!");

        } else {
            deckMainLabel.setText("New Deck");
            FlashCardDeck newDeck = new FlashCardDeck(deckNameField.getText());
            if (!deckSubjectField.getText().isEmpty()) {
                newDeck.setSubject(deckSubjectField.getText());
            }
            int deckID = deckService.createDeck(newDeck, loggedInUser.getUsername());
            if (deckID > 0) {
                deckDetailsPane.setVisible(false);
                newDeck.setDeckID(deckID);
                addToDeckList(newDeck);
                successLabelChange("Deck Created!");
                quantityDeckLabel.setText(deckList.getItems().size() + " Decks");
            }

        }
    }

    private void addToDeckList(FlashCardDeck deck) {

        deckMap.put(deck.getDeckID(),deck);
        deckIdMap.put(deck.getName(), deck.getDeckID());
        deckList.getItems().add(deck.getName());
        loggedInUser.addDeck(deck.getDeckID(),deck);

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

    private void successLabelChange(String s) {
        successLabel.setText(s);
        deckDetailsPane.setVisible(false);
        cardPane.setVisible(false);
        cardListPane.setVisible(false);
        successPane.setVisible(true);

    }

    public void checkFields(KeyEvent keyEvent) {
        if (!questionTextArea.getText().isEmpty() && !answerTextArea.getText().isEmpty()) {
            saveCardButton.setDisable(false);
        } else {
            saveCardButton.setDisable(true);
        }
    }

    public void successOK(ActionEvent actionEvent) {
        successPane.setVisible(false);
        if (successLabel.getText().equals("Card Added!") || successLabel.getText().equals("Card Saved!")) {
            successOK2();
        }

    }

    private void successOK2() {
        successPane.setVisible(false);
        cardListPane.setVisible(true);
        cardPane.setVisible(false);
        cardList.getSelectionModel().clearSelection();
    }
}
