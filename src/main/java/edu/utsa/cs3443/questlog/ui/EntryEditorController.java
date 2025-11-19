package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.model.Status;
import edu.utsa.cs3443.questlog.model.Status;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

public class EntryEditorController {

    @FXML private Label headerLabel;
    @FXML private ImageView coverImageView;
    @FXML private TextField titleField;
    @FXML private ComboBox<Status> platformCombo;
    @FXML private ComboBox<Status> statusCombo;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker completionDatePicker;
    @FXML private TextArea notesArea;
    @FXML private Label errorLabel;

    private GameEntry editingEntry; // null = create mode

    @FXML
    private void initialize() {
        errorLabel.setVisible(false);
        platformCombo.getItems().setAll(Status.values());
        statusCombo.getItems().setAll(Status.values());
        headerLabel.setText("Create Entry");
    }

    public void setEditingEntry(GameEntry entry) {
        this.editingEntry = entry;
        if (entry != null) {
            headerLabel.setText("Edit Entry");
            titleField.setText(entry.getTitle());
            platformCombo.setValue(entry.getPlatform());
            statusCombo.setValue(entry.getStatus());
            startDatePicker.setValue(entry.getStartDate());
            completionDatePicker.setValue(entry.getCompletionDate());
            notesArea.setText(entry.getNotes());
            // TODO: load image
        }
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showDashboard();
    }

    @FXML
    private void onChangeCoverClicked() {
        // TODO: FileChooser + set image path on entry
    }

    @FXML
    private void onSaveClicked() {
        // TODO: validate, then create/update GameEntry via service
        ScreenNavigator.showDashboard();
    }
}
