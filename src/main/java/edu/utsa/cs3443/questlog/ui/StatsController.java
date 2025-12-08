package edu.utsa.cs3443.questlog.ui;

import edu.utsa.cs3443.questlog.model.GameEntry;
import edu.utsa.cs3443.questlog.model.Status;
import edu.utsa.cs3443.questlog.service.AuthService;
import edu.utsa.cs3443.questlog.service.EntryService;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    @FXML private HBox customLegend;

    private final EntryService entryService = EntryService.getInstance();

    // ADD THIS — username passed in from ScreenNavigator
    private String username;

    public void setUsername(String username) {
        this.username = username;
        updateStatsTitle(username);
        updateStats();
        buildCustomLegend();
    }

    @FXML
    private void initialize() {
        if (this.username == null) {
            if (AuthService.getInstance().getCurrentUser() != null) {
                this.username = AuthService.getInstance().getCurrentUser().getUsername();
            } else {
                this.username = "User";
            }
        }
        updateStatsTitle(this.username);
        updateStats();


        // chart setup
        xAxis.setTickLabelsVisible(false);
        yAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        yAxis.setTickMarkVisible(false);
        yAxis.setForceZeroInRange(true);
        statusChart.applyCss();
        statusChart.layout();

        Region legendBox = (Region) statusChart.lookup(".chart-legend");

        if (legendBox != null) {
            legendBox.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-border-radius: 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 10 20 10 20;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
            );

            legendBox.lookupAll(".chart-legend-item")
                    .forEach(node -> node.setStyle("-fx-padding: 0 30 0 30;"));
        }


        // i couldn't get it to work in the css so i just did this
        if (ScreenNavigator.isDarkMode()) {
            summaryCard.setStyle("-fx-background-color: #2A2C31; -fx-background-radius: 10; -fx-padding: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);");
        } else {
            summaryCard.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);");
        }

        statusChart.setLegendVisible(false);
        buildCustomLegend();
    }

    private void updateStatsTitle(String username) {
        if (statsTitleFlow == null) return;

        statsTitleFlow.getChildren().clear();

        Text t1 = new Text(username);
        t1.setStyle("-fx-fill: #00FF00; -fx-font-weight: bold; -fx-font-size: 35px;");

        Text t2 = new Text("'s Stats");
        t2.setStyle(ScreenNavigator.isDarkMode() ? "-fx-fill: #EEEEEE; -fx-font-size: 35px;" : "-fx-fill: #1A1A1A; -fx-font-size: 35px;");

        statsTitleFlow.getChildren().addAll(t1, t2);
    }

    private void updateStats() {
        String userId = AuthService.getInstance().getCurrentUser().getUserId();
        List<GameEntry> entries = entryService.getEntriesForUser(userId);

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

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Completed", completed));
        series.getData().add(new XYChart.Data<>("Playing", playing));
        series.getData().add(new XYChart.Data<>("Backlog", backlog));

        statusChart.getData().add(series);
    }

    private void buildCustomLegend() {
        customLegend.getChildren().clear();
        customLegend.setSpacing(25);
        String textColor = ScreenNavigator.isDarkMode() ? "#E0E0E0" : "#1A1A1A";
        customLegend.setStyle("-fx-background-color: transparent");
        customLegend.setFillHeight(false);
        customLegend.setMaxWidth(Region.USE_PREF_SIZE);

        customLegend.getChildren().addAll(
                createLegendItem("#108E10", "Completed", textColor),
                createLegendItem("#759bff", "Playing", textColor),
                createLegendItem("#ffaa2b", "Backlog", textColor)
        );
    }

    private HBox createLegendItem(String color, String label, String textColor) {
        Region box = new Region();
        box.setPrefSize(16, 16);
        box.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-background-radius: 4;"
        );

        Label lbl = new Label(label);
        lbl.setStyle("-fx-text-fill: " + textColor + "; -fx-font-size: 20px;");

        HBox item = new HBox(8, box, lbl);
        item.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        return item;
    }

}
