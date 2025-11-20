package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.model.Status;
import edu.utsa.cs3443.questlog.service.EntryService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class StatsController {

    @FXML private Label totalGamesLabel;
    @FXML private Label completedLabel;
    @FXML private Label playingLabel;
    @FXML private Label backlogLabel;

    private final EntryService entryService = EntryService.getInstance();

    @FXML
    private void initialize() {
        updateStats();
    }

    private void updateStats() {
        List<GameEntry> entries = entryService.getAllEntries();

        int total = entries.size();
        int completed = 0;
        int playing = 0;
        int backlog = 0;

        for (GameEntry e : entries) {
            Status s = e.getStatus();
            if (s == null) continue;

            switch (s) {
                case COMPLETED -> completed++;
                case PLAYING -> playing++;
                case BACKLOG -> backlog++;
                default -> {
                    // ignore other statuses like DROPPED, ON_HOLD, etc. for now
                }
            }
        }

        totalGamesLabel.setText(String.valueOf(total));
        completedLabel.setText(String.valueOf(completed));
        playingLabel.setText(String.valueOf(playing));
        backlogLabel.setText(String.valueOf(backlog));
    }
}
