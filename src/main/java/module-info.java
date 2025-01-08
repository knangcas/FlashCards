module dealer.dealerproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens dealer.dealerproject to javafx.fxml;
    exports dealer.dealerproject;
    exports dealer.dealerproject.controller;
    opens dealer.dealerproject.controller to javafx.fxml;
}