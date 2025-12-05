package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.model.Status;
import edu.utsa.cs3443.questlog.service.EntryService;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

public class StatsController {

    @FXML private Label totalGamesLabel;
    @FXML private Label completedLabel;
    @FXML private Label playingLabel;
    @FXML private Label backlogLabel;

    @FXML private Label statsTitleLabel;
    @FXML private BarChart<String, Number> statusChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private VBox summaryCard;
    @FXML private TextFlow statsTitleFlow;

    private final EntryService entryService = EntryService.getInstance();

    // ADD THIS — username passed in from ScreenNavigator
    private String username = "admin";

    public void setUsername(String username) {
        this.username = username;
        updateStatsTitle(username);
    }

    @FXML
    private void initialize() {
        if (username != null) {
            updateStatsTitle(username);
        }
        updateStats();

        // chart setup
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);
        yAxis.setForceZeroInRange(true);
        statusChart.applyCss();
        statusChart.layout();

        Region legendBox = (Region) statusChart.lookup(".chart-legend");

        if (legendBox != null) {
            legendBox.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-border-color: #D0D0D0;" +
                            "-fx-border-radius: 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 10 20 10 20;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
            );

            // Fix spacing between legend items
            legendBox.lookupAll(".chart-legend-item")
                    .forEach(node -> node.setStyle("-fx-padding: 0 30 0 30;"));
        }


        // i couldn't get it to work in the css so i just did this
        if (ScreenNavigator.isDarkMode()) {
            summaryCard.setStyle("-fx-background-color: #2A2C31; -fx-background-radius: 10; -fx-padding: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);");
        } else {
            summaryCard.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);");
        }
    }

    private void updateStatsTitle(String username) {
        statsTitleFlow.getChildren().clear();

        Text t1 = new Text(username);
        t1.setStyle("-fx-fill: #00FF00; -fx-font-weight: bold; -fx-font-size: 34px;");

        Text t2 = new Text("'s Stats");
        t2.setStyle(ScreenNavigator.isDarkMode()
                ? "-fx-fill: #EEEEEE; -fx-font-size: 34px;"
                : "-fx-fill: #1A1A1A; -fx-font-size: 34px;");

        statsTitleFlow.getChildren().addAll(t1, t2);
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
