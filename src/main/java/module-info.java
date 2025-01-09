module dealer.dealerproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens flashcards to javafx.fxml;
    exports flashcards;
    exports flashcards.controller;
    opens flashcards.controller to javafx.fxml;
}