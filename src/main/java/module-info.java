module flashcards {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    exports flashcards.model;
    requires com.fasterxml.jackson.databind;
    opens flashcards.model to com.fasterxml.jackson.databind;
    opens flashcards to javafx.fxml;
    exports flashcards;
    exports flashcards.controller;
    opens flashcards.controller to javafx.fxml;
    exports flashcards.Services;
    opens flashcards.Services to javafx.fxml;
}