package flashcards;

import flashcards.Services.impl.JsonLoadSave;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {



        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("splash.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        if (MainWrapper.SERVICE.equals("JSON")) {
            JsonLoadSave.initialize();
        }
        stage.setTitle("FLASHCARDS");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        //test
    }

    public static void main(String[] args) {
        launch();
    }
}