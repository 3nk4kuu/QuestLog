package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class ManageAccountController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;

    @FXML
    private void initialize() {
        messageLabel.setText("");
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showSettingsMenu();
    }

    @FXML
    private void onSaveEmailClicked() {
        // TODO: call AuthService.updateEmail()
        messageLabel.setText("Email updated.");
    }

    @FXML
    private void onSavePasswordClicked() {
        // TODO: validate + update password
        messageLabel.setText("Password updated.");
    }

    @FXML
    private void onDeleteAccountClicked() {

        // --- Confirmation Dialog ---
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Account Deletion");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("This action cannot be undone.");

        // --- Load trashcan icon (same folder as sun/moon) ---
        var iconUrl = getClass().getResource("/data/images/trashcan.png");

        System.out.println("trashcan URL = " + iconUrl); // debug print

        if (iconUrl != null) {
            ImageView icon = new ImageView(iconUrl.toExternalForm());
            icon.setFitWidth(40);
            icon.setFitHeight(40);
            alert.setGraphic(icon); // *** replaces default blue ? icon ***
        } else {
            System.err.println("ERROR: trashcan.png not found in /data/images/");
        }

        // Dialog buttons
        ButtonType deleteButton = new ButtonType("Delete");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(deleteButton, cancelButton);

        // Show dialog
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == deleteButton) {
            // TODO: Delete user here
            System.out.println("Account deleted!");
            ScreenNavigator.showLogin();
        } else {
            System.out.println("Account deletion canceled.");
        }
    }
}



