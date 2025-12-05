package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.User;
import edu.utsa.cs3443.questlog.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorLabel;

    private final AuthService authService = AuthService.getInstance();

    @FXML
    private void initialize() {
        errorLabel.setVisible(false);
        errorLabel.setText("");
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showLogin();
    }

    @FXML
    private void onRegisterClicked() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        clearError();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!password.equals(confirm)) {
            showError("Passwords do not match.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showError("Please enter a valid email address.");
            return;
        }

        try {
            User user = new User(null, username, email, password);
            authService.register(user);

            // auto-login and show dashboard after registering
            authService.login(username, password);
            ScreenNavigator.showDashboard();

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            // error message maybe
        }
    }

    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }
    }

    private void clearError() {
        if (errorLabel != null) {
            errorLabel.setText("");
            errorLabel.setVisible(false);
        }
    }
}
