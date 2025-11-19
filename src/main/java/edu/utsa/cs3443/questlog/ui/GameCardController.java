package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameCardController {

    @FXML private ImageView coverImageView;
    @FXML private Label titleLabel;
    @FXML private Label platformLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;

    private GameEntry entry;

    public void setEntry(GameEntry entry) {
        this.entry = entry;
        titleLabel.setText(entry.getTitle());
        platformLabel.setText(entry.getPlatform().name());
        statusLabel.setText(entry.getStatus().name());
        // TODO: set progress, cover image path, click handler, etc.
    }

    @FXML
    private void initialize() {
        // optional: default styling
    }
}
