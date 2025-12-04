module questlog {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    // Allow JavaFX to load FXML controllers
    opens edu.utsa.cs3443.questlog to javafx.fxml;
    opens edu.utsa.cs3443.questlog.ui to javafx.fxml;

    // Export your main app package so JavaFX can access MainApp
    exports edu.utsa.cs3443.questlog;
}
