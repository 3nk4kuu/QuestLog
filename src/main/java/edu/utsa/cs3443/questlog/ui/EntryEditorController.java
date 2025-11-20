package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.model.Platform;
import edu.utsa.cs3443.questlog.model.Status;
import edu.utsa.cs3443.questlog.service.EntryService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;

public class EntryEditorController {

    @FXML private Label headerLabel;
    @FXML private ImageView coverImageView;
    @FXML private TextField titleField;
    @FXML private ComboBox<Platform> platformCombo;
    @FXML private ComboBox<Status> statusCombo;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker completionDatePicker;
    @FXML private TextArea notesArea;
    @FXML private Label errorLabel;

    @FXML private ToggleButton eHeart1;
    @FXML private ToggleButton eHeart2;
    @FXML private ToggleButton eHeart3;
    @FXML private ToggleButton eHeart4;
    @FXML private ToggleButton eHeart5;

    @FXML private Button deleteButton;

    private int currentRating = 0; // 0–5
    private String selectedCoverPath;

    private final EntryService entryService = EntryService.getInstance();
    private GameEntry editingEntry; // null = create

    @FXML
    private void initialize() {
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }

        headerLabel.setText("Create Entry");

        platformCombo.getItems().setAll(Platform.values());
        statusCombo.getItems().setAll(Status.values());
        updateEditorHearts();

        // In create mode by default → hide Delete
        if (deleteButton != null) {
            deleteButton.setVisible(false);
        }
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

            currentRating = entry.getRating();
            updateEditorHearts();

            selectedCoverPath = entry.getCoverImagePath();
            loadCoverImage();

            if (deleteButton != null) {
                deleteButton.setVisible(true);
            }
        } else {
            headerLabel.setText("Create Entry");
            currentRating = 0;
            updateEditorHearts();
            selectedCoverPath = null;
            loadCoverImage();

            if (deleteButton != null) {
                deleteButton.setVisible(false);
            }
        }

        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showDashboard();
    }

    @FXML
    private void onLogoutClicked() {
        // Go back to start/login screen
        ScreenNavigator.showLogin();
    }

    @FXML
    private void onChangeCoverClicked() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Cover Image");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = chooser.showOpenDialog(coverImageView.getScene().getWindow());
        if (file != null) {
            selectedCoverPath = file.getAbsolutePath();
            loadCoverImage();
        }
    }

    @FXML
    private void onSaveClicked() {
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }

        String title = titleField.getText();
        Platform platform = platformCombo.getValue();
        Status status = statusCombo.getValue();
        LocalDate start = startDatePicker.getValue();
        LocalDate end = completionDatePicker.getValue();
        String notes = notesArea.getText();

        if (title == null || title.isBlank()) {
            showError("Title is required.");
            return;
        }

        if (editingEntry == null) {
            // Create mode
            GameEntry newEntry = new GameEntry(null); // id assigned in service
            newEntry.setTitle(title);
            newEntry.setPlatform(platform);
            newEntry.setStatus(status);
            newEntry.setStartDate(start);
            newEntry.setCompletionDate(end);
            newEntry.setNotes(notes);
            newEntry.setRating(currentRating);
            newEntry.setCoverImagePath(selectedCoverPath);

            entryService.save(newEntry);
        } else {
            // Edit mode: update fields
            editingEntry.setTitle(title);
            editingEntry.setPlatform(platform);
            editingEntry.setStatus(status);
            editingEntry.setStartDate(start);
            editingEntry.setCompletionDate(end);
            editingEntry.setNotes(notes);
            editingEntry.setRating(currentRating);
            editingEntry.setCoverImagePath(selectedCoverPath);

            entryService.save(editingEntry);
        }

        ScreenNavigator.showDashboard();
    }

    @FXML
    private void onDeleteClicked() {
        if (editingEntry != null) {
            // optional: confirm dialog later
            entryService.delete(editingEntry);
        }
        ScreenNavigator.showDashboard();
    }

    private void showError(String msg) {
        if (errorLabel != null) {
            errorLabel.setText(msg);
            errorLabel.setVisible(true);
        }
    }

    private void updateEditorHearts() {
        ToggleButton[] hearts = { eHeart1, eHeart2, eHeart3, eHeart4, eHeart5 };
        for (int i = 0; i < hearts.length; i++) {
            boolean filled = i < currentRating;
            hearts[i].setSelected(filled);
            hearts[i].setText(filled ? "♥" : "♡");
        }
    }

    @FXML
    private void onHeartClicked() {
        if (eHeart1.isArmed()) currentRating = 1;
        if (eHeart2.isArmed()) currentRating = 2;
        if (eHeart3.isArmed()) currentRating = 3;
        if (eHeart4.isArmed()) currentRating = 4;
        if (eHeart5.isArmed()) currentRating = 5;
        updateEditorHearts();
    }

    private void loadCoverImage() {
        if (selectedCoverPath != null && !selectedCoverPath.isBlank()) {
            File file = new File(selectedCoverPath);
            if (file.exists()) {
                coverImageView.setImage(new javafx.scene.image.Image(file.toURI().toString()));
            } else {
                coverImageView.setImage(null);
            }
        } else {
            coverImageView.setImage(null);
        }
    }
}
