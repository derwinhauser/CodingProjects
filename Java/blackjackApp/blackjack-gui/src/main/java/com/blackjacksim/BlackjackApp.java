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

        Label numberOfShoesLabel = new Label("How many shoes to simulate? (1-10000):");
        TextField numberOfShoesField = new TextField();
        numberOfShoesField.setPromptText("Enter a number between 1 and 10000");

        Label shuffleAtLabel = new Label("Shuffle at (must be >1 and < shoe size):");
        TextField shuffleAtField = new TextField();
        shuffleAtField.setPromptText("Enter shuffle point");

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
        bankrollChart.setCreateSymbols(false); // Remove data point symbols for cleaner look
        bankrollChart.setStyle(".chart-series-line { -fx-stroke-width: 1px; }");

        


        // --- Step 2: Button Logic ---
        runButton.setOnAction(e -> {
            try {
                int shoeSize = Integer.parseInt(shoeSizeField.getText().trim());
                int numShoes = Integer.parseInt(numberOfShoesField.getText().trim());
                double shuffleAt = Double.parseDouble(shuffleAtField.getText().trim());

                // Input validation
                if (shoeSize < 2 || shoeSize > 8) {
                    outputArea.setText("Shoe size must be between 2 and 8.");
                    return;
                }
                if (numShoes < 1 || numShoes > 10000) {
                    outputArea.setText("Number of shoes must be between 1 and 10000.");
                    return;
                }
                if (shuffleAt < 1 || shuffleAt >= shoeSize) {
                    outputArea.setText("Shuffle point must be >1 and < " + shoeSize + ".");
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
                        // Small delay to allow UI to update before heavy computation
                        Thread.sleep(100);
                        
                        Table table = new Table(shoeSize);
                        table.setShuffleAt(shuffleAt);
                        
                        // Update message
                        updateMessage("Simulating " + numShoes + " shoes...");
                        
                        // Run the simulation with progress callback
                        table.playShoe(numShoes, (current, total) -> {
                            updateProgress(current, total);
                            updateMessage("Simulating shoe " + current + " of " + total);
                        });
                        
                        // Set to complete
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
                    // Unbind progress properties
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

                    for (int i = 0; i < table.bankrollHistory.size(); i++) {
                        series.getData().add(new XYChart.Data<>(i + 1, table.bankrollHistory.get(i)));
                    }

                    bankrollChart.getData().add(series);
                    
                    // Make the line thinner - apply after chart renders
                    Platform.runLater(() -> {
                        series.getNode().setStyle("-fx-stroke-width: 1px;");
                    });
                    
                    progressLabel.setText("Simulation complete!");
                    runButton.setDisable(false);
                });

                simulationTask.setOnFailed(event -> {
                    // Unbind progress properties
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
                runButton,
                progressBar,
                progressLabel,
                outputArea,
                bankrollChart
        );

        Scene scene = new Scene(root, 450, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack Simulator");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}