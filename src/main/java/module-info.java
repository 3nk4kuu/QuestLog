module questlog {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.utsa.cs3443.questlog.ui to javafx.fxml;
    opens edu.utsa.cs3443.questlog.model to javafx.base;

    exports edu.utsa.cs3443.questlog;
   // exports edu.utsa.cs3443.questlog.ui;
}
