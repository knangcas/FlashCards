module flashcards {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens flashcards to javafx.fxml;
    exports flashcards;
    exports flashcards.controller;
    opens flashcards.controller to javafx.fxml;
    exports flashcards.Services;
    opens flashcards.Services to javafx.fxml;
}