package dealer.dealerproject.controller;

import dealer.dealerproject.HelloApplication;
import dealer.dealerproject.UserManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button logInButton;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane splashAP;

    @FXML
    private Label invalidCredentials;

    Stage stage;

    Parent root;

    @FXML
    public void logIn() {
        if (UserManagement.validateUser(usernameField.getText(), passwordField.getText())) {
            //switch scene, log in to app
            System.out.println("Logged in");
            try {
                switchToMain();
            } catch (IOException exception) {
                System.out.println("Error occured, no such window exists");
            }
        } else {
            //show red text below login indicating incorrect password
            invalidCredentials.setText("Invalid Credentials");
            System.out.println("Failed");
        }

    }

    private void switchToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPage.fxml"));
        root = fxmlLoader.load();

        MainPageController mpc = fxmlLoader.getController();

        mpc.setWelcomeText(usernameField.getText());
        stage = new Stage();
        stage.setTitle("Dealership Management");
        stage.setScene(new Scene(root));
        stage.show();


        splashAP.getScene().getWindow().hide();

    }



    @FXML
    public void exit(ActionEvent event) {
        stage = (Stage) splashAP.getScene().getWindow();
        stage.close();
    }
}
