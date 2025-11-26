package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AppearanceController {

    @FXML
    private ToggleButton darkModeToggle;

    @FXML
    private Label themeDescription;

    @FXML
    private ImageView themeIcon;  // Sun/Moon icon

    private Image sunImg;
    private Image moonImg;

    @FXML
    private void initialize() {

        // ----- Load images safely -----
        var sunUrl  = getClass().getResource("/data/images/sun.png");
        var moonUrl = getClass().getResource("/data/images/moon.png");

        System.out.println("sunUrl  = " + sunUrl);
        System.out.println("moonUrl = " + moonUrl);

        if (sunUrl != null && moonUrl != null) {
            sunImg  = new Image(sunUrl.toExternalForm());
            moonImg = new Image(moonUrl.toExternalForm());
        } else {
            System.err.println("ERROR: Could not load theme icons.");
            System.err.println("Make sure these files exist:");
            System.err.println("src/main/resources/data/images/sun.png");
            System.err.println("src/main/resources/data/images/moon.png");
        }

        // ----- Initialize toggle state -----
        boolean enabled = ScreenNavigator.isDarkMode();
        darkModeToggle.setSelected(enabled);

        updateToggleText();
        updateThemeIcon();  // only sets icon if images loaded
    }

    @FXML
    private void onDarkModeToggled() {
        boolean enabled = darkModeToggle.isSelected();
        ScreenNavigator.setDarkMode(enabled);

        updateToggleText();
        updateThemeIcon();
    }

    private void updateToggleText() {
        darkModeToggle.setText(
                darkModeToggle.isSelected() ? "Dark Mode: On" : "Dark Mode: Off"
        );
    }

    private void updateThemeIcon() {
        boolean dark = darkModeToggle.isSelected();

        if (themeIcon != null && sunImg != null && moonImg != null) {
            themeIcon.setImage(dark ? moonImg : sunImg);
        }
    }

    @FXML
    private void onBackClicked() {
        ScreenNavigator.showSettingsMenu();
    }
}
