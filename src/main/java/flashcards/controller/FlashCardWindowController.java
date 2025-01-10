package flashcards.controller;

import flashcards.MainWrapper;
import flashcards.Services.DeckService;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class FlashCardWindowController {

    @FXML
    public Button nextButton;
    @FXML
    public Button skipButton;
    @FXML
    public Button restartButton;
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

    DeckService deckService;


    public void initializeDeck(FlashCardDeck deck2) throws SQLException {
        //this.deck = deck;

        deckService = DeckService.getInstance(MainWrapper.SERVICE);

        // temporary
        deck = deckService.getDeck("deck1");

        //FlashCard card1 = new FlashCard("What is a pointer?", "A pointer is a reference to an address in memory.");
        //FlashCard card2 = new FlashCard("Does python need semicolons?", "No");

        deckNameLabel.setText(deck.getName());
        contentLabel.setText(deck.getCard().getQuestion());
        currentCard = deck.getCard();
        total = deck.getSize();
        progress = 1;
    }

    public void test(MouseEvent mouseEvent) {
        flip();
        System.out.println("clicked");
    }

    private void flip() {
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
        if (!deck.isEmpty()) {
            currentCard = deck.getCard();
            contentLabel.setText(currentCard.getQuestion());
            progress++;
            updateProgress();
        } else {
            contentLabel.setText("YOU HAVE REACHED THE END BRUH");
        }

    }

    private void updateProgress() {
        progressLabel.setText("Card " + progress + " of " + total);
    }

    private void updateSkipped() {

    }


    public void skipCard(ActionEvent actionEvent) {

    }

    public void shuffleAndRestart(ActionEvent actionEvent) {
    }
}
