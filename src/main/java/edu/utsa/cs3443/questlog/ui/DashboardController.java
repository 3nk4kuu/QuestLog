package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.service.EntryService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private ComboBox<String> sortCombo;
    @FXML private ComboBox<String> statusFilterCombo;
    @FXML private FlowPane gamesFlowPane;

    private final EntryService entryService = EntryService.getInstance();

    @FXML
    private void initialize() {
        welcomeLabel.setText("Welcome back, User00"); // later: current user

        // TODO: fill sort/status combos
        refreshLibrary();
    }

    private void refreshLibrary() {
        gamesFlowPane.getChildren().clear();

        for (GameEntry entry : entryService.getAllEntries()) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/dashboard/game_card.fxml")
                );
                Node cardNode = loader.load();
                GameCardController controller = loader.getController();
                controller.setEntry(entry); // will also wire click handler
                gamesFlowPane.getChildren().add(cardNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onAddEntryClicked() {
        // null → create mode
        ScreenNavigator.showEntryEditor(null);
    }
}
