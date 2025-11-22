package edu.utsa.cs3443.questlog.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class RatingHeartsController {

    @FXML private ToggleButton heart1;
    @FXML private ToggleButton heart2;
    @FXML private ToggleButton heart3;
    @FXML private ToggleButton heart4;
    @FXML private ToggleButton heart5;

    private int rating = 0;
    private boolean interactive = true;

    @FXML
    private void initialize() {
        updateHearts();
    }

    @FXML
    private void onHeartClicked() {
        if (!interactive) {
            return; // ignore clicks when in read-only mode
        }

        // figure out which heart was clicked
        ToggleButton source = null;
        if (heart1.isArmed()) source = heart1;
        else if (heart2.isArmed()) source = heart2;
        else if (heart3.isArmed()) source = heart3;
        else if (heart4.isArmed()) source = heart4;
        else if (heart5.isArmed()) source = heart5;

        if (source == null) return;

        if (source == heart1) rating = 1;
        else if (source == heart2) rating = 2;
        else if (source == heart3) rating = 3;
        else if (source == heart4) rating = 4;
        else if (source == heart5) rating = 5;

        updateHearts();
    }

    private void updateHearts() {
        ToggleButton[] hearts = {heart1, heart2, heart3, heart4, heart5};

        for (int i = 0; i < hearts.length; i++) {
            boolean filled = i < rating;

            hearts[i].setSelected(filled);
            hearts[i].setText(filled ? "♥" : "♡");
        }
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = Math.max(0, Math.min(5, rating));
        updateHearts();
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;

        ToggleButton[] hearts = {heart1, heart2, heart3, heart4, heart5};
        for (ToggleButton heart : hearts) {
            heart.setMouseTransparent(!interactive);
            heart.setFocusTraversable(false);
        }
    }
}
