package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.ui.ScreenNavigator;
import edu.utsa.cs3443.questlog.model.GameEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntryViewController {

    @FXML private ImageView coverImageView;
    @FXML private Label titleLabel;
    @FXML private Label metaLabel;
    @FXML private TextArea notesArea;


    // TODO: reference to rating component if needed

    private GameEntry entry;

    public void setEntry(GameEntry entry) {
        this.entry = entry;
        titleLabel.setText(entry.getTitle());

        Integer year = (entry.getReleaseDate() == null) ? null : entry.getReleaseDate().getYear();
        String yearText = (year == null) ? "" : String.valueOf(year);
        metaLabel.setText(yearText);

        notesArea.setText(entry.getNotes());
        // TODO: load cover image, rating, etc.
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
