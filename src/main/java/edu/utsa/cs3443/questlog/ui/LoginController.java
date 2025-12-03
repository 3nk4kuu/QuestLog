package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.User;
import edu.utsa.cs3443.questlog.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Label loginTitle; // added
    @FXML private ImageView loginLogo;

    private final AuthService authService = AuthService.getInstance();

    @FXML
    private void initialize() {
        // Hide error label
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
        if (loginLogo != null) {
            loginLogo.setImage(
                    new Image(getClass().getResource("/data/images/logo.png").toString())
            );
        }

        // Set vibrant green title programmatically
        if (loginTitle != null) {
            loginTitle.setStyle(
                    "-fx-text-fill: #55EB55; " +
                            "-fx-font-size: 80px; " +
                            "-fx-font-weight: bold;"
            );
        }
    }

    @FXML
    private void onLoginClicked() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // hides error label after failed login attempt
        if (errorLabel != null) {
            errorLabel.setVisible(false);
            errorLabel.setText("");
        }

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter a username and password.");
            return;
        }

        authService.login(username, password).ifPresentOrElse(this::onLoginSuccess, () -> showError("Invalid username or password."));
    }

    private void onLoginSuccess(User user) {
        ScreenNavigator.showDashboard();
    }

    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void onRegisterClicked() {
        ScreenNavigator.showRegister();
    }
}


