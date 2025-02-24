package flashcards.controller;

import flashcards.HelloApplication;
import flashcards.MainWrapper;
import flashcards.Services.DeckService;
import flashcards.UserManagement;
import flashcards.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainPageController {

    @FXML
    private Label welcomeText;



    @FXML
    private BorderPane borderPane;

    private User loggedInUser;

    private boolean offline = false;




    public void setWelcomeText(String username) {
        welcomeText.setText("Welcome, " + username + "!");
    }

    public void initialize(User user) throws SQLException {
        DeckService deckService = DeckService.getInstance(MainWrapper.SERVICE);
        loggedInUser = user;
        welcomeText.setText("Welcome, " + loggedInUser.getUsername() + "!");
        List<Integer> decks = deckService.getDecks(user);

        for (int deckID : decks) {
            user.addDeck(deckID, deckService.getDeck(deckID));
        }

        //this makes it so that it opens up the study page right away, temporary until home page is made.
        try {
            studyButton(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize() throws SQLException {
        offline = true;
        DeckService deckService = DeckService.getInstance("JSON");
        loggedInUser = new User("Offline User", "offline");
        welcomeText.setText("Welcome!");
        List<Integer> decks = deckService.getDecks(loggedInUser);
        UserManagement.offlineUser();
        for (int deckID : decks) {
            loggedInUser.addDeck(deckID, deckService.getDeck(deckID));
        }

        //this makes it so that it opens up the study page right away, temporary until home page is made.
        try {
            studyButton(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void logOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("splash.fxml"));
        Parent root;
        root = fxmlLoader.load();

        //loads splash into root

        Stage stage = new Stage();
        stage.setTitle("Welcome");
        stage.setScene(new Scene(root));
        //sets scene to root
        stage.show();
        loggedInUser = null;

        //shows stage

        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        //hides window from which the event took place


    }

    public void studyButton(ActionEvent actionEvent) throws IOException, SQLException {
        //AnchorPane loader = FXMLLoader.load(HelloApplication.class.getResource("FlashCardWindow.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FlashCardWindow.fxml"));
        AnchorPane l = fxmlLoader.load();
        FlashCardWindowController flashCardWindowController = fxmlLoader.getController();
        if (!offline) {
            flashCardWindowController.initializeDeck(false);
        } else {
            flashCardWindowController.initializeDeck(true);
        }
        borderPane.setCenter(l);


    }

    public void manageDecks(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ManageFlash.fxml"));
        AnchorPane l = fxmlLoader.load();
        ManageFlashController manageFlashController = fxmlLoader.getController();
        manageFlashController.initialize(loggedInUser);
        borderPane.setCenter(l);
    }
}
