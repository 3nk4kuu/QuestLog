package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.model.Status;
import edu.utsa.cs3443.questlog.service.EntryService;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.util.List;

public class StatsController {

    @FXML private Label totalGamesLabel;
    @FXML private Label completedLabel;
    @FXML private Label playingLabel;
    @FXML private Label backlogLabel;

    @FXML private BarChart<String, Number> statusChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

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
            if (e.getStatus() == null) continue;

            switch (e.getStatus()) {
                case COMPLETED -> completed++;
                case PLAYING -> playing++;
                case BACKLOG -> backlog++;
            }
        }

        totalGamesLabel.setText(String.valueOf(total));
        completedLabel.setText(String.valueOf(completed));
        playingLabel.setText(String.valueOf(playing));
        backlogLabel.setText(String.valueOf(backlog));

        xAxis.getCategories().setAll("Completed", "Playing", "Backlog");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Completed", completed));
        series.getData().add(new XYChart.Data<>("Playing", playing));
        series.getData().add(new XYChart.Data<>("Backlog", backlog));

        statusChart.getData().clear();
        statusChart.getData().add(series);
    }
}
