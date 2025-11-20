package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameCardController {

    @FXML private StackPane root;
    @FXML private ImageView coverImageView;
    @FXML private Label titleLabel;
    @FXML private Label platformLabel;
    @FXML private Label statusLabel;

    @FXML private Label heart1;
    @FXML private Label heart2;
    @FXML private Label heart3;
    @FXML private Label heart4;
    @FXML private Label heart5;

    private GameEntry entry;

    public void setEntry(GameEntry entry) {
        this.entry = entry;

        titleLabel.setText(entry.getTitle());
        platformLabel.setText(entry.getPlatform() != null ? entry.getPlatform().name() : "");
        statusLabel.setText(entry.getStatus() != null ? entry.getStatus().name() : "");

        updateHearts(entry.getRating());

        // clicking the card opens the editor
        root.setOnMouseClicked(e -> ScreenNavigator.showEntryEditor(this.entry));
    }

    private void updateHearts(int rating) {
        Label[] hearts = { heart1, heart2, heart3, heart4, heart5 };
        for (int i = 0; i < hearts.length; i++) {
            hearts[i].setText(i < rating ? "♥" : "♡");
        }
    }
}
