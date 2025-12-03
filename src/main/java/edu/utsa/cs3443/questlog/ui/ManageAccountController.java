package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.service.AuthService;

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


    private final AuthService authService = AuthService.getInstance();

    @FXML
    private void initialize() {
        messageLabel.setText("");
        emailField.clear();
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showSettingsMenu();
    }

    @FXML
    private void onSaveEmailClicked() {
        String newEmail = emailField.getText().trim();
        messageLabel.setText("");

        if (newEmail.isEmpty()) {
            messageLabel.setText("Email cannot be empty.");
            return;
        }

        if (!newEmail.contains("@") || !newEmail.contains(".")) {
            messageLabel.setText("Please enter a valid email address.");
            return;
        }

        if (authService.getCurrentUser() == null) {
            messageLabel.setText("No user is currently logged in.");
            return;
        }

        String currentEmail = authService.getCurrentUser().getEmail();

        if (newEmail.equalsIgnoreCase(currentEmail)) {
            messageLabel.setText("That is already your current email.");
            return;
        }

        try {
            authService.updateEmail(newEmail);
            messageLabel.setText("Email updated.");
            emailField.clear();
        } catch (IllegalArgumentException ex) {
            messageLabel.setText(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Could not update email.");
        }

    }

    @FXML
    private void onSavePasswordClicked() {
        String pw = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        messageLabel.setText("");

        if (pw.isEmpty() || confirm.isEmpty()) {
            messageLabel.setText("Password fields cannot be empty.");
            return;
        }

        if (!pw.equals(confirm)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }

        try {
            authService.updatePassword(pw);
            messageLabel.setText("Password updated.");
            passwordField.clear();
            confirmPasswordField.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Could not update password.");
        }
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
            try {
                authService.deleteCurrentUser();
                ScreenNavigator.showLogin();
            } catch (Exception ex) {
                ex.printStackTrace();
                messageLabel.setText("Could not delete account.");
            }
        }
    }
}



