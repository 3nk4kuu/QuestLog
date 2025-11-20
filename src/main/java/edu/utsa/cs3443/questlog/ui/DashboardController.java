package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.model.Platform;
import edu.utsa.cs3443.questlog.model.Status;
import edu.utsa.cs3443.questlog.service.EntryService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private ComboBox<String> sortCombo;
    @FXML private ComboBox<Platform> platformFilterCombo;
    @FXML private ComboBox<Status> statusFilterCombo;
    @FXML private FlowPane gamesFlowPane;

    private final EntryService entryService = EntryService.getInstance();

    @FXML
    private void initialize() {
        welcomeLabel.setText("Welcome back, User00");

        // SORT OPTIONS
        sortCombo.getItems().addAll(
                "Title (A–Z)",
                "Title (Z–A)",
                "Rating (High→Low)",
                "Rating (Low→High)",
                "Platform",
                "Status"
        );
        sortCombo.setPromptText("Sort By");
        sortCombo.valueProperty().addListener((obs, oldV, newV) -> refreshLibrary());

        // PLATFORM FILTER
        platformFilterCombo.getItems().add(null); // null = show all
        platformFilterCombo.getItems().addAll(Platform.values());
        platformFilterCombo.setPromptText("All Platforms");
        platformFilterCombo.valueProperty().addListener((o, ov, nv) -> refreshLibrary());

        // STATUS FILTER
        statusFilterCombo.getItems().add(null); // null = show all
        statusFilterCombo.getItems().addAll(Status.values());
        statusFilterCombo.setPromptText("All Statuses");
        statusFilterCombo.valueProperty().addListener((o, ov, nv) -> refreshLibrary());

        refreshLibrary();
    }

    private void refreshLibrary() {
        gamesFlowPane.getChildren().clear();

        List<GameEntry> entries = entryService.getAllEntries();

        // FILTERING
        Platform pFilter = platformFilterCombo.getValue();
        Status sFilter = statusFilterCombo.getValue();

        entries = entries.stream()
                .filter(e -> pFilter == null || e.getPlatform() == pFilter)
                .filter(e -> sFilter == null || e.getStatus() == sFilter)
                .collect(Collectors.toList());

        // SORTING
        String sort = sortCombo.getValue();
        if (sort != null) {
            switch (sort) {
                case "Title (A–Z)" -> entries.sort(Comparator.comparing(GameEntry::getTitle, String.CASE_INSENSITIVE_ORDER));
                case "Title (Z–A)" -> entries.sort(Comparator.comparing(GameEntry::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());
                case "Rating (High→Low)" -> entries.sort(Comparator.comparingInt(GameEntry::getRating).reversed());
                case "Rating (Low→High)" -> entries.sort(Comparator.comparingInt(GameEntry::getRating));
                case "Platform" -> entries.sort(Comparator.comparing(e -> e.getPlatform() != null ? e.getPlatform().name() : ""));
                case "Status" -> entries.sort(Comparator.comparing(e -> e.getStatus() != null ? e.getStatus().name() : ""));
            }
        }

        // RENDER GAME CARDS
        for (GameEntry entry : entries) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/dashboard/game_card.fxml")
                );
                Node cardNode = loader.load();
                GameCardController controller = loader.getController();
                controller.setEntry(entry);
                gamesFlowPane.getChildren().add(cardNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onAddEntryClicked() {
        ScreenNavigator.showEntryEditor(null);
    }

    @FXML
    private void onLogoutClicked() {
        ScreenNavigator.showLogin();
    }

}
