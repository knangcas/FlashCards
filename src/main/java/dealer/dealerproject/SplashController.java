package dealer.dealerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    Stage stage;

    @FXML
    public void logIn() {
        if (UserManagement.validateUser(usernameField.getText(), passwordField.getText())) {
            //switch scene, log in to app
            System.out.println("Logged in");
        } else {
            //show red text below login indicating incorrect password
            System.out.println("Failed");
        }


    }

    @FXML
    public void exit(ActionEvent event) {
        stage = (Stage) splashAP.getScene().getWindow();
        stage.close();
    }
}
