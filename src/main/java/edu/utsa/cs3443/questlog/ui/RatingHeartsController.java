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

    @FXML
    private void initialize() {
        updateHearts();
    }

    @FXML
    private void onHeartClicked() {
        // Determine which heart was clicked
        if (heart1.isArmed()) rating = 1;
        if (heart2.isArmed()) rating = 2;
        if (heart3.isArmed()) rating = 3;
        if (heart4.isArmed()) rating = 4;
        if (heart5.isArmed()) rating = 5;
        updateHearts();
    }

    private void updateHearts() {
        heart1.setSelected(rating >= 1);
        heart2.setSelected(rating >= 2);
        heart3.setSelected(rating >= 3);
        heart4.setSelected(rating >= 4);
        heart5.setSelected(rating >= 5);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = Math.max(0, Math.min(5, rating));
        updateHearts();
    }
}
