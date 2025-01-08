package dealer.dealerproject.controller;

import dealer.dealerproject.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    @FXML
    private Label welcomeText;

    public void setWelcomeText(String username) {
        welcomeText.setText("Welcome, " + username + "!");
    }



    @FXML
    public void logOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("splash.fxml"));
        Parent root;
        root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setTitle("Welcome");
        stage.setScene(new Scene(root));
        stage.show();

        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();


    }
}
