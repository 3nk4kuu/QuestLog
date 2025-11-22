package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.ui.ScreenNavigator;
import edu.utsa.cs3443.questlog.model.GameEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import java.time.format.DateTimeFormatter;

import java.io.File;

public class EntryViewController {

    @FXML private ImageView coverImageView;
    @FXML private Label titleLabel;
    @FXML private Label metaLabel;
    @FXML private TextArea notesArea;
    @FXML private TextField statusField;
    @FXML private TextField platformField;
    @FXML private TextField startField;
    @FXML private TextField completedField;
    @FXML private RatingHeartsController ratingHeartsController;

    private GameEntry entry;

    public void setEntry(GameEntry entry) {
        this.entry = entry;
        titleLabel.setText(entry.getTitle());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        Integer year = (entry.getReleaseDate() == null) ? null : entry.getReleaseDate().getYear();
        String yearText = (year == null) ? "" : String.valueOf(year);
        metaLabel.setText(yearText);

        statusField.setText(entry.getStatus() == null ? "" : entry.getStatus().toString());
        platformField.setText(entry.getPlatform() == null ? "" : entry.getPlatform().toString());

        startField.setText(entry.getStartDate() == null ? "" : entry.getStartDate().format(formatter));
        completedField.setText(entry.getCompletionDate() == null ? "" : entry.getCompletionDate().format(formatter));

        notesArea.setText(entry.getNotes());

        if (ratingHeartsController != null) {
            ratingHeartsController.setRating(entry.getRating());
            ratingHeartsController.setInteractive(false);
        }

        if (entry.getCoverImagePath() != null) {
            File file = new File(entry.getCoverImagePath());
            if (file.exists()) {
                Image img = new Image(file.toURI().toString());
                coverImageView.setImage(img);
            }
        }
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showDashboard();
    }

    @FXML
    private void onEditClicked() {
        // For now just go to editor; later pass entry instance
        ScreenNavigator.showEntryEditor(entry);
    }
}
