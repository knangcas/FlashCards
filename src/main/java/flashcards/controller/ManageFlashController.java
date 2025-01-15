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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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






    public void initialize(User user) {
        deckService = DeckService.getInstance(MainWrapper.SERVICE);
        loggedInUser = user;
        populateDeckList();



        cardListPane.setVisible(false);
        cardPane.setVisible(false);
    }




    private void populateDeckList() {
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
                } else {
                    currentDeck = null;
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
        //pop-up window prompting for deck name (required)
        //and subject (optional)
        //add to DB
    }
}
