module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens com.example.client.gui.controller to javafx.fxml;
    exports com.example.client;
}