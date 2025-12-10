package com.blackjacksim;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.concurrent.Task;


public class BlackjackApp extends Application {

    // The VBox that holds either the status message or the final three-column HBox
    private VBox resultsPanel; 

    // Helper method to create a two-column GridPane for a group of metrics
    private GridPane createMetricGrid(String title) {
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10)); // Internal padding
        
        // Style the title of the group
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 0 0 5 0; -fx-underline: true;");
        grid.add(titleLabel, 0, 0, 2, 1); // Span two columns
        
        return grid;
    }

    // Helper method to add a key-value pair to a GridPane
    private void addMetric(GridPane grid, int row, String label, String value, String style) {
        Label labelNode = new Label(label);
        Label valueNode = new Label(value);
        
        // Apply optional styling to the value
        if (style != null && !style.isEmpty()) {
            valueNode.setStyle(style);
        }

        // Add to the grid (starting from row 1, since row 0 is the title)
        grid.add(labelNode, 0, row);
        grid.add(valueNode, 1, row);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        // --- Step 1: Input Controls ---

        Label startingBankrollLabel = new Label("Starting Bankroll:");
        TextField startingBankrollField = new TextField();
        startingBankrollField.setPromptText("Enter starting bankroll (e.g., 200000)");
        startingBankrollField.setText("");

        // Bet Spread Section
        Label betSpreadLabel = new Label("Bet Spread:");
        betSpreadLabel.setStyle("-fx-font-weight: bold;");

        HBox minBetBox = new HBox(10);
        Label minBetLabel = new Label("Min bet:");
        TextField minBetField = new TextField();
        minBetField.setPromptText("");
        minBetField.setText("");
        minBetField.setPrefWidth(100);
        minBetBox.getChildren().addAll(minBetLabel, minBetField);

        HBox tc1Box = new HBox(10);
        Label tc1Label = new Label("True Count 1+:");
        TextField tc1Field = new TextField();
        tc1Field.setPromptText("");
        tc1Field.setText("");
        tc1Field.setPrefWidth(100);
        tc1Box.getChildren().addAll(tc1Label, tc1Field);

        HBox tc2Box = new HBox(10);
        Label tc2Label = new Label("True Count 2+:");
        TextField tc2Field = new TextField();
        tc2Field.setPromptText("");
        tc2Field.setText("");
        tc2Field.setPrefWidth(100);
        tc2Box.getChildren().addAll(tc2Label, tc2Field);

        HBox tc3Box = new HBox(10);
        Label tc3Label = new Label("True Count 3+:");
        TextField tc3Field = new TextField();
        tc3Field.setPromptText("");
        tc3Field.setText("");
        tc3Field.setPrefWidth(100);
        tc3Box.getChildren().addAll(tc3Label, tc3Field);

        HBox tc4Box = new HBox(10);
        Label tc4Label = new Label("True Count 4+:");
        TextField tc4Field = new TextField();
        tc4Field.setPromptText("");
        tc4Field.setText("");
        tc4Field.setPrefWidth(100);
        tc4Box.getChildren().addAll(tc4Label, tc4Field);

        Label shoeSizeLabel = new Label("How many decks are in the shoe? (2-8):");
        TextField shoeSizeField = new TextField();
        shoeSizeField.setPromptText("Enter a number between 2 and 8");

        Label shuffleAtLabel = new Label("How many decks remain before shuffling?:");
        TextField shuffleAtField = new TextField();
        shuffleAtField.setPromptText("Enter a number greater than 0.5 and less than shoe size");

        Label numberOfShoesLabel = new Label("How many shoes to simulate?:");
        TextField numberOfShoesField = new TextField();
        numberOfShoesField.setPromptText("Enter a number greater than 1");

        Button runButton = new Button("Run Simulation");

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        
        Label progressLabel = new Label("Ready to simulate");
        
        // --- Results Panel (Replaces TextArea) ---
        resultsPanel = new VBox(5);
        Label initialStatusLabel = new Label("Input parameters and click 'Run Simulation' to see results.");
        resultsPanel.getChildren().add(initialStatusLabel);

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Hand Number");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Bankroll");

        LineChart<Number, Number> bankrollChart =
                new LineChart<>(xAxis, yAxis);

        bankrollChart.setTitle("Bankroll Over Time");
        bankrollChart.setCreateSymbols(false);
        bankrollChart.setStyle(".chart-series-line { -fx-stroke-width: 1px; }");

        
        // --- Step 2: Button Logic ---
        runButton.setOnAction(e -> {
            try {
                int shoeSize = Integer.parseInt(shoeSizeField.getText().trim());
                int numShoes = Integer.parseInt(numberOfShoesField.getText().trim());
                double shuffleAt = Double.parseDouble(shuffleAtField.getText().trim());
                double startingBankroll = Double.parseDouble(startingBankrollField.getText().trim());
                
                // Parse bet spread
                int minBet = Integer.parseInt(minBetField.getText().trim());
                int tc1Bet = Integer.parseInt(tc1Field.getText().trim());
                int tc2Bet = Integer.parseInt(tc2Field.getText().trim());
                int tc3Bet = Integer.parseInt(tc3Field.getText().trim());
                int tc4Bet = Integer.parseInt(tc4Field.getText().trim());

                // Input validation
                if (shoeSize < 2 || shoeSize > 8) {
                    resultsPanel.getChildren().clear();
                    resultsPanel.getChildren().add(new Label("Shoe size must be between 2 and 8."));
                    return;
                }
                if (numShoes < 1) {
                    resultsPanel.getChildren().clear();
                    resultsPanel.getChildren().add(new Label("Number of shoes simulated must be greater than 1"));
                    return;
                }
                if (shuffleAt < .5 || shuffleAt >= shoeSize) {
                    resultsPanel.getChildren().clear();
                    resultsPanel.getChildren().add(new Label("Shuffle point must be >=0.5 and < " + shoeSize + "."));
                    return;
                }
                if (startingBankroll <= 0) {
                    resultsPanel.getChildren().clear();
                    resultsPanel.getChildren().add(new Label("Starting bankroll must be greater than 0."));
                    return;
                }
                if (minBet <= 0 || tc1Bet <= 0 || tc2Bet <= 0 || tc3Bet <= 0 || tc4Bet <= 0) {
                    resultsPanel.getChildren().clear();
                    resultsPanel.getChildren().add(new Label("All bet amounts must be greater than 0."));
                    return;
                }

                // Disable button during simulation
                runButton.setDisable(true);
                progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                progressLabel.setText("Starting simulation...");
                
                // Clear results panel and show status message
                resultsPanel.getChildren().clear();
                resultsPanel.getChildren().add(new Label("Running simulation...\nThis may take a moment for large simulations."));

                // Run simulation in background thread
                Task<Table> simulationTask = new Task<Table>() {
                    @Override
                    protected Table call() throws Exception {
                        Thread.sleep(100);
                        
                        Table table = new Table(shoeSize);
                        table.setShuffleAt(shuffleAt);
                        table.setPlayerBankroll(startingBankroll);
                        
                        // Set bet spread (Corrected potential bug from original input code)
                        table.setBetSpread(minBet, tc1Bet, tc2Bet, tc3Bet, tc4Bet);
                        
                        updateMessage("Simulating " + numShoes + " shoes...");
                        
                        table.playShoe(numShoes, (current, total) -> {
                            updateProgress(current, total);
                            updateMessage("Simulating shoe " + current + " of " + total);
                        });
                        
                        updateProgress(1, 1);
                        updateMessage("Processing results...");
                        
                        return table;
                    }
                };

                // Bind progress bar and label to task
                progressBar.progressProperty().bind(simulationTask.progressProperty());
                progressLabel.textProperty().bind(simulationTask.messageProperty());

                // Handle completion
                simulationTask.setOnSucceeded(event -> {
                    progressBar.progressProperty().unbind();
                    progressLabel.textProperty().unbind();
                    
                    Table table = simulationTask.getValue();
                    
                    // --- Step 3: Compute Statistics ---
                    double totalProfit = table.endingBankroll - table.startingBankroll;
                    double finalBankroll = table.endingBankroll;
                    int hands = table.totalHands;

                    if (hands == 0) {
                        resultsPanel.getChildren().clear();
                        resultsPanel.getChildren().add(new Label("Simulation ran but produced 0 hands. Cannot compute statistics."));
                        runButton.setDisable(false);
                        return;
                    }

                    double evPerHand = totalProfit / hands;

                    // Average bet size
                    double sumBets = 0;
                    for (int b : table.betHistory)
                        sumBets += b;

                    double averageBet = table.betHistory.size() > 0
                            ? sumBets / table.betHistory.size()
                            : 0;

                    // Player edge
                    double playerEdge = (averageBet > 0)
                            ? evPerHand / averageBet
                            : 0;

                    // Variance of per-hand profit
                    double variance = 0;
                    double mean = evPerHand;

                    if (table.profitHistory.size() > 1) {
                        for (double p : table.profitHistory) {
                            variance += Math.pow(p - mean, 2);
                        }
                        variance /= table.profitHistory.size();
                    } else {
                        variance = 0;
                    }

                    // Risk of ruin
                    double ROR = 0;
                    if (variance > 0 && evPerHand > 0) {
                        ROR = Math.exp(
                                -1 * (2 * evPerHand * table.startingBankroll) / variance
                        );
                    } else if (variance == 0 && evPerHand > 0) {
                        ROR = 0.0;
                    } else if (evPerHand <= 0) {
                        ROR = 1.0; 
                    }
                    
                    // --- Step 4: Display Results in Three Columns ---
                    
                    // Clear the results panel
                    resultsPanel.getChildren().clear();
                    
                    // Main layout container for the three columns
                    HBox threeColumnLayout = new HBox(30); // 30px spacing between columns
                    threeColumnLayout.setPadding(new Insets(10, 0, 10, 0));
                    HBox.setHgrow(threeColumnLayout, javafx.scene.layout.Priority.ALWAYS);
                    
                    // 1. Column 1: Overview
                    GridPane overviewGrid = createMetricGrid("Simulation Overview");
                    addMetric(overviewGrid, 1, "Total Hands:", String.valueOf(hands), null);
                    addMetric(overviewGrid, 2, "Final Bankroll:", "$" + String.format("%,.2f", finalBankroll), "-fx-font-weight: bold;");
                    addMetric(overviewGrid, 3, "Total Profit:", (totalProfit >= 0 ? "+" : "-") + "$" + String.format("%,.2f", Math.abs(totalProfit)), totalProfit >= 0 ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                    HBox.setHgrow(overviewGrid, javafx.scene.layout.Priority.ALWAYS);

                    // 2. Column 2: Performance
                    GridPane performanceGrid = createMetricGrid("Performance Metrics");
                    addMetric(performanceGrid, 1, "Average Bet Size:", "$" + String.format("%,.2f", averageBet), null);
                    addMetric(performanceGrid, 2, "EV per Hand:", "$" + String.format("%,.4f", evPerHand), evPerHand >= 0 ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                    addMetric(performanceGrid, 3, "Player Edge:", String.format("%,.2f", playerEdge * 100) + "%", playerEdge >= 0 ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
                    HBox.setHgrow(performanceGrid, javafx.scene.layout.Priority.ALWAYS);
                    
                    // 3. Column 3: Risk & Volatility
                    GridPane riskGrid = createMetricGrid("Risk Profile");
                    addMetric(riskGrid, 1, "Variance (per hand):", String.format("%,.2f", variance), null);
                    addMetric(riskGrid, 2, "Risk of Ruin (ROR):", String.format("%,.2f", ROR * 100) + "%", ROR < 0.05 ? "-fx-text-fill: green;" : (ROR < 0.2 ? "-fx-text-fill: orange;" : "-fx-text-fill: red;"));
                    HBox.setHgrow(riskGrid, javafx.scene.layout.Priority.ALWAYS);

                    // Add the three grids to the main column layout
                    threeColumnLayout.getChildren().addAll(overviewGrid, performanceGrid, riskGrid);
                    
                    // Add the three-column layout to the main results panel
                    resultsPanel.getChildren().add(threeColumnLayout);
                    
                    // --- Update Chart --- (Existing logic)
                    bankrollChart.getData().clear();

                    XYChart.Series<Number, Number> series = new XYChart.Series<>();
                    series.setName("Bankroll");

                    // Sample data points to avoid overloading the chart
                    int dataSize = table.bankrollHistory.size();
                    int maxPoints = 1000;
                    int step = Math.max(1, dataSize / maxPoints);
                    
                    for (int i = 0; i < dataSize; i += step) {
                        series.getData().add(new XYChart.Data<>(i + 1, table.bankrollHistory.get(i)));
                    }
                    
                    // Always include the last point
                    if (dataSize > 0 && (dataSize - 1) % step != 0) {
                        series.getData().add(new XYChart.Data<>(dataSize, table.bankrollHistory.get(dataSize - 1)));
                    }

                    bankrollChart.getData().add(series);
                    
                    Platform.runLater(() -> {
                        series.getNode().setStyle("-fx-stroke-width: 1px;");
                    });
                    
                    progressLabel.setText("Simulation complete!");
                    runButton.setDisable(false);
                });

                simulationTask.setOnFailed(event -> {
                    progressBar.progressProperty().unbind();
                    progressLabel.textProperty().unbind();
                    
                    resultsPanel.getChildren().clear();
                    resultsPanel.getChildren().add(new Label("Error: " + simulationTask.getException().getMessage()));
                    
                    progressLabel.setText("Simulation failed");
                    runButton.setDisable(false);
                });

                // Start the task in a background thread
                Thread thread = new Thread(simulationTask);
                thread.setDaemon(true);
                thread.start();

            } catch (NumberFormatException ex) {
                resultsPanel.getChildren().clear();
                resultsPanel.getChildren().add(new Label("Please enter valid numbers for all fields."));
            }
        });

        // --- Step 5: Layout ---

        // 1. Create a GridPane for the main simulation inputs (Label, TextField)
        javafx.scene.layout.GridPane inputGrid = new javafx.scene.layout.GridPane();
        inputGrid.setHgap(10); // Horizontal gap
        inputGrid.setVgap(10); // Vertical gap
        inputGrid.setPadding(new Insets(10));

        // Add simulation parameters to the grid
        inputGrid.add(numberOfShoesLabel, 0, 0);
        inputGrid.add(numberOfShoesField, 1, 0);
        inputGrid.add(shoeSizeLabel, 0, 1);
        inputGrid.add(shoeSizeField, 1, 1);
        inputGrid.add(shuffleAtLabel, 0, 2);
        inputGrid.add(shuffleAtField, 1, 2);        

        // Set text fields to grow with the column
        javafx.scene.layout.GridPane.setHgrow(shoeSizeField, javafx.scene.layout.Priority.ALWAYS);
        javafx.scene.layout.GridPane.setHgrow(numberOfShoesField, javafx.scene.layout.Priority.ALWAYS);
        javafx.scene.layout.GridPane.setHgrow(shuffleAtField, javafx.scene.layout.Priority.ALWAYS);

        // 2. Group all Bet Spread HBoxes together in one large HBox
        HBox betSpreadGroup = new HBox(20); // 20px spacing between bet fields
        betSpreadGroup.setPadding(new Insets(10, 0, 10, 0)); // Top/bottom padding
        betSpreadGroup.getChildren().addAll(
            minBetBox, tc1Box, tc2Box, tc3Box, tc4Box
        );

        javafx.scene.layout.GridPane inputGrid1 = new javafx.scene.layout.GridPane();
        inputGrid1.setHgap(10); // Horizontal gap
        inputGrid1.setVgap(10); // Vertical gap
        inputGrid1.setPadding(new Insets(10));

        inputGrid1.add(startingBankrollLabel, 0, 0);
        inputGrid1.add(startingBankrollField, 1, 0);

        javafx.scene.layout.GridPane.setHgrow(startingBankrollField, javafx.scene.layout.Priority.ALWAYS);


        // 3. Create a VBox for the Run button, Progress Bar, and Progress Label
        VBox controlPanel = new VBox(5); // 5px spacing
        controlPanel.setPadding(new Insets(10, 0, 10, 0));
        controlPanel.getChildren().addAll(
            runButton,
            progressBar,
            progressLabel
        );


        // 4. Main VBox to hold the organized sections
        VBox root = new VBox(10); // 10px spacing between major sections
        root.setPadding(new Insets(10));

        root.getChildren().addAll(
            inputGrid,
            inputGrid1,
            betSpreadLabel,
            betSpreadGroup,
            controlPanel,
            resultsPanel, // Now using the VBox resultsPanel
            bankrollChart
        );

        // Set resultsPanel and bankrollChart to grow vertically
        VBox.setVgrow(resultsPanel, javafx.scene.layout.Priority.SOMETIMES); 
        VBox.setVgrow(bankrollChart, javafx.scene.layout.Priority.ALWAYS);


        Scene scene = new Scene(root, 700, 800); // Increased initial size for better fit
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack Simulator");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}