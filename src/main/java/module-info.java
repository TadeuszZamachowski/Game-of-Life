module com.example.gameoflife {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires eu.hansolo.tilesfx;

    opens com.example.gameoflife to javafx.fxml;
    exports com.example.gameoflife;
}