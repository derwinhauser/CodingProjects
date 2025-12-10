package com.blackjacksim;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.concurrent.Task;


public class BlackjackApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        // --- Step 1: Input Controls ---
        Label shoeSizeLabel = new Label("How many decks are in the shoe? (2-8):");
        TextField shoeSizeField = new TextField();
        shoeSizeField.setPromptText("Enter a number between 2 and 8");

        Label numberOfShoesLabel = new Label("How many shoes to simulate?:");
        TextField numberOfShoesField = new TextField();
        numberOfShoesField.setPromptText("Enter a number greater than 1");

        Label shuffleAtLabel = new Label("How many decks remain before shuffling?:");
        TextField shuffleAtField = new TextField();
        shuffleAtField.setPromptText("Enter a number greater than 0.5 and less than shoe size");

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

        Button runButton = new Button("Run Simulation");

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        
        Label progressLabel = new Label("Ready to simulate");
        
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

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
                    outputArea.setText("Shoe size must be between 2 and 8.");
                    return;
                }
                if (numShoes < 1) {
                    outputArea.setText("Number of shoes simulated must be greater than 1");
                    return;
                }
                if (shuffleAt < .5 || shuffleAt >= shoeSize) {
                    outputArea.setText("Shuffle point must be >=0.5 and < " + shoeSize + ".");
                    return;
                }
                if (startingBankroll <= 0) {
                    outputArea.setText("Starting bankroll must be greater than 0.");
                    return;
                }
                if (minBet <= 0 || tc1Bet <= 0 || tc2Bet <= 0 || tc3Bet <= 0 || tc4Bet <= 0) {
                    outputArea.setText("All bet amounts must be greater than 0.");
                    return;
                }

                // Disable button during simulation
                runButton.setDisable(true);
                progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                progressLabel.setText("Starting simulation...");
                outputArea.setText("Running simulation...\n\nThis may take a moment for large simulations.");

                // Run simulation in background thread
                Task<Table> simulationTask = new Task<Table>() {
                    @Override
                    protected Table call() throws Exception {
                        Thread.sleep(100);
                        
                        Table table = new Table(shoeSize);
                        table.setShuffleAt(shuffleAt);
                        table.setPlayerBankroll(startingBankroll);
                        
                        // Set bet spread
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
                        outputArea.setText("Simulation ran but produced 0 hands. Cannot compute statistics.");
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
                    }

                    // --- Step 4: Display Results ---
                    StringBuilder report = new StringBuilder();

                    report.append("=== Simulation Results ===\n\n");
                    report.append("Total Hands: " + hands + "\n");
                    report.append("Final Bankroll: $" + String.format("%.2f", finalBankroll) + "\n");
                    report.append("Total Profit: $" + String.format("%.2f", totalProfit) + "\n\n");

                    report.append("EV per Hand: $" + String.format("%.2f", evPerHand) + "\n");
                    report.append("Average Bet Size: $" + String.format("%.2f", averageBet) + "\n");
                    report.append("Player Edge: " + String.format("%.2f", playerEdge * 100) + "%\n\n");

                    report.append("Variance: " + String.format("%.2f", variance) + "\n");
                    report.append("Risk of Ruin: " + String.format("%.2f", ROR * 100) + "%\n");

                    outputArea.setText(report.toString());
                    
                    // --- Update Chart ---
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
                    
                    outputArea.setText("Error: " + simulationTask.getException().getMessage());
                    progressLabel.setText("Simulation failed");
                    runButton.setDisable(false);
                });

                // Start the task in a background thread
                Thread thread = new Thread(simulationTask);
                thread.setDaemon(true);
                thread.start();

            } catch (NumberFormatException ex) {
                outputArea.setText("Please enter valid numbers for all fields.");
            }
        });

        // --- Step 5: Layout ---
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        root.getChildren().addAll(
                shoeSizeLabel, shoeSizeField,
                numberOfShoesLabel, numberOfShoesField,
                shuffleAtLabel, shuffleAtField,
                startingBankrollLabel, startingBankrollField,
                betSpreadLabel,
                minBetBox,
                tc1Box,
                tc2Box,
                tc3Box,
                tc4Box,
                runButton,
                progressBar,
                progressLabel,
                outputArea,
                bankrollChart
        );

        Scene scene = new Scene(root, 500, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack Simulator");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}