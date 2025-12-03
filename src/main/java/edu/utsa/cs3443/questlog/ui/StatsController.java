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

    @FXML private Label statsTitleLabel;   // ADD THIS (matches FXML)
    @FXML private BarChart<String, Number> statusChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    private final EntryService entryService = EntryService.getInstance();

    // ADD THIS — username passed in from ScreenNavigator
    private String username;

    public void setUsername(String username) {
        this.username = username;
        statsTitleLabel.setText(username + "'s Stats");  // update UI title
    }

    @FXML
    private void initialize() {
        updateStats();
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);
        yAxis.setForceZeroInRange(true);
        statusChart.setCategoryGap(200);
        statusChart.setBarGap(-20);
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

        statusChart.getData().clear();

        XYChart.Series<String, Number> completedSeries = new XYChart.Series<>();
        completedSeries.setName("Completed");
        completedSeries.getData().add(new XYChart.Data<>("Completed", completed));

        XYChart.Series<String, Number> playingSeries = new XYChart.Series<>();
        playingSeries.setName("Playing");
        playingSeries.getData().add(new XYChart.Data<>("Playing", playing));

        XYChart.Series<String, Number> backlogSeries = new XYChart.Series<>();
        backlogSeries.setName("Backlog");
        backlogSeries.getData().add(new XYChart.Data<>("Backlog", backlog));

        statusChart.getData().addAll(completedSeries, playingSeries, backlogSeries);
    }
}
