package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.File;

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

    @FXML
    public void initialize() {
        // Clip the cover image to match rounded card corners
        Rectangle clip = new Rectangle(coverImageView.getFitWidth(), coverImageView.getFitHeight());
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        coverImageView.setClip(clip);
    }

    public void setEntry(GameEntry entry) {
        this.entry = entry;

        titleLabel.setText(entry.getTitle());
        platformLabel.setText(entry.getPlatform() != null ? entry.getPlatform().getShortCode() : "");
        statusLabel.setText(entry.getStatus() != null ? entry.getStatus().name() : "");

        String path = entry.getCoverImagePath();
        if (path != null && !path.isBlank()) {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                coverImageView.setImage(new Image(imgFile.toURI().toString()));
            } else {
                coverImageView.setImage(null);
            }
        } else {
            coverImageView.setImage(null);
        }

        updateHearts(entry.getRating());

        // clicking the card opens the editor -> changed to open entry view
        root.setOnMouseClicked(e -> ScreenNavigator.showEntryView(this.entry));
    }

    private void updateHearts(int rating) {
        Label[] hearts = { heart1, heart2, heart3, heart4, heart5 };
        for (int i = 0; i < hearts.length; i++) {
            hearts[i].setText(i < rating ? "♥" : "♡");
        }
    }

}
