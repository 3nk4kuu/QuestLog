package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private ComboBox<String> sortCombo;
    @FXML private ComboBox<String> statusFilterCombo;
    @FXML private FlowPane gamesFlowPane;

    @FXML
    private void initialize() {
        // TODO: pull current user, set welcomeLabel
        // TODO: populate sort/status combos
        // TODO: load GameEntry list and create GameCard nodes
    }

    @FXML
    private void onAddEntryClicked() {
        ScreenNavigator.showEntryEditor();
    }
}
