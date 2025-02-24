package flashcards.controller;

import flashcards.MainWrapper;
import flashcards.Services.DeckService;
import flashcards.UserManagement;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import flashcards.model.exception.FlashCardDeckEmptyException;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.nio.channels.Channel;
import java.sql.SQLException;
import java.util.HashMap;

public class FlashCardWindowController {

    @FXML
    public Button nextButton;
    @FXML
    public Button skipButton;
    @FXML
    public Button restartButton;
    @FXML
    public Pane noCardsPane;

    @FXML
    private Label progressLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private Label deckNameLabel;

    @FXML
    private Label skippedLabel;

    private int progress;

    private int total;

    private FlashCardDeck deck;

    private FlashCard currentCard;

    @FXML
    private Pane selectDeckPane;

    @FXML
    private ListView<String> deckList;

    @FXML
    private Label quantityDeckLabel;

    @FXML
    private Button okButton;

    @FXML
    private Pane endPane;

    @FXML
    private Label deckSubjectLabel;

    @FXML
    private Pane flashPane;

    @FXML
    private Pane flashCardPane;

    @FXML
    private Label noDecksLabel;

    @FXML
    private Button skippedCardsButton;

    private int skipped;

    private HashMap<Integer, FlashCardDeck> deckMap;

    private HashMap<String, Integer> deckIdMap;



    DeckService deckService;


    public void initializeDeck(boolean offline) throws SQLException {
        //this.deck = deck;
        selectDeckPane.setVisible(true);
        flashPane.setVisible(false);
        endPane.setVisible(false);
        noDecksLabel.setVisible(false);
        noCardsPane.setVisible(false);
        if (!offline) {
            deckService = DeckService.getInstance(MainWrapper.SERVICE);
        } else {
            deckService = DeckService.getInstance("JSON");
        }
        deckMap = UserManagement.getActiveUser().getDecks();
        deckIdMap = new HashMap<>();

        for (int deckID: deckMap.keySet()) {
            deckIdMap.put(deckMap.get(deckID).getName(), deckID);
        }



        // temporary

        //deck = deckService.getDeck(1);
        populateDeckList();
        //FlashCard card1 = new FlashCard("What is a pointer?", "A pointer is a reference to an address in memory.");
        //FlashCard card2 = new FlashCard("Does python need semicolons?", "No");


    }

    private void populateDeckList() {
        deckList.getItems().addAll(deckIdMap.keySet());
        if (deckList.getItems().size() == 0) {
            deckList.setVisible(false);
            noDecksLabel.setVisible(true);
        }

        quantityDeckLabel.setText(deckList.getItems().size() + " Decks");
        deckList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (deckList.getSelectionModel().getSelectedItem() == null) {
                    okButton.setDisable(true);
                } else {
                    okButton.setDisable(false);
                }
            }
        });
    }



    public void test(MouseEvent mouseEvent) {
        flip();
        System.out.println("clicked");
    }

    private void flip() {

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(flashCardPane);
        rotateTransition.setDuration(Duration.millis(500));
        rotateTransition.setInterpolator(Interpolator.EASE_BOTH);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setByAngle(360);
        rotateTransition.play();

        if (currentCard.isQuestionSide()) {
            contentLabel.setText(currentCard.getAnswer());
            currentCard.setQuestionSide(false);
        } else {
            contentLabel.setText(currentCard.getQuestion());
            currentCard.setQuestionSide(true);
        }


    }


    public void nextCard(ActionEvent actionEvent) {
        deck.correctChoice();
        progress++;
        getNextCard();

    }

    public void getNextCard() {
        if (!deck.isEmpty()) {
            currentCard = deck.getCard();
            contentLabel.setText(currentCard.getQuestion());
            updateProgress();
        } else {
            flashPane.setVisible(false);
            endPane.setVisible(true);
            if (deck.getSkippedSize() > 0) {
                skippedCardsButton.setVisible(true);
            } else {
                skippedCardsButton.setVisible(false);
            }
        }
    }

    private void updateProgress() {
        progressLabel.setText("Card " + progress + " of " + total);
    }

    private void updateSkipped() {
        skippedLabel.setText("Skipped " + skipped + " Cards");
    }


    public void skipCard(ActionEvent actionEvent) {
        deck.skipCard();
        skipped++;
        updateSkipped();
        /*FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(flashCardPane);
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setInterpolator(Interpolator.EASE_BOTH);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();*/



        getNextCard();
    }

    public void shuffleAndRestart(ActionEvent actionEvent) {
        System.out.println(deck.toString());
        deck.shuffle();
        progress = 1;
        skipped = 0;
        currentCard = deck.getCard();
        contentLabel.setText(currentCard.getQuestion());
        updateSkipped();
        updateProgress();
        endPane.setVisible(false);
        flashPane.setVisible(true);
        System.out.println(deck.toString());
    }

    public void selectDeck(ActionEvent actionEvent) {
        if (endPane.isVisible()) {
            endPane.setVisible(false);
        }


        try {
            deck = deckService.getDeck(deckIdMap.get(deckList.getSelectionModel().getSelectedItem()));
            if (deck.isEmpty()) {
                throw new FlashCardDeckEmptyException();
            }
            deckNameLabel.setText(deck.getName());
            contentLabel.setText(deck.getCard().getQuestion());
            currentCard = deck.getCard();
            total = deck.getSize();
            progress = 1;
            skipped = 0;
            updateSkipped();
            updateProgress();
            if (deck.getSubject() != null) {
                deckSubjectLabel.setText(deck.getSubject());
            }
            selectDeckPane.setVisible(false);
            flashPane.setVisible(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (FlashCardDeckEmptyException fce) {
            selectDeckPane.setVisible(false);
            noCardsPane.setVisible(true);
        }



    }


    public void selectNewDeck(ActionEvent actionEvent) {
        selectDeckPane.setVisible(true);
        flashPane.setVisible(false);
        endPane.setVisible(false);
    }

    public void loadSkippedCards(ActionEvent actionEvent) {
        deck.moveSkipped();
        skipped = 0;
        progress = 1;
        total = deck.getSize();
        updateProgress();
        updateSkipped();
        currentCard = deck.getCard();
        endPane.setVisible(false);
        flashPane.setVisible(true);
    }



    public void noCardsBack(ActionEvent actionEvent) {
        selectDeckPane.setVisible(true);
        noCardsPane.setVisible(false);
    }
}
