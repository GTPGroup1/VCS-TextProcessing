package com.group1.vcstextprocessing.controller;

import com.group1.vcstextprocessing.model.DataItem;
import com.group1.vcstextprocessing.model.DataManager;
import com.group1.vcstextprocessing.model.RegexProcessor;
import com.group1.vcstextprocessing.model.RegexProcessor.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

public class MainController {

    @FXML
    public Button addDataButton;

    @FXML
    public Button replaceButton;

    @FXML
    TextField regexTextField;

    @FXML
    private TextField replaceTextField;

    @FXML
    TextFlow outputTextFlow; // Changed from TextArea to TextFlow


    @FXML
    TextField dataInputField;

    @FXML
    ListView<String> dataListView;

    @FXML
    private VBox root; // Ensure this is the root node of your scene


    RegexProcessor regexProcessor = new RegexProcessor();
    DataManager dataManager = new DataManager(); // Initialize DataManager


    @FXML
    public void initialize() {
        dataListView.getItems().addAll(dataManager.getAllData().stream().map(DataItem::getData).toList());

        // Add a mouse click listener to the root node
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (!(event.getTarget() instanceof ListView)) {
                dataListView.getSelectionModel().clearSelection();
            }
        });
    }

    @FXML
    public void onSearchButtonClick() {
        String regexPattern = regexTextField.getText().trim(); // Trim to remove any leading or trailing whitespace
        if (regexPattern.isEmpty()) {
            showAlert("Search Error", "Regex pattern cannot be empty.");
            return; // Exit the method if no regex is provided
        }

        try {
            List<MatchResult> matches = regexProcessor.searchWithPositions(dataManager.getAllData(), regexPattern);
            outputTextFlow.getChildren().clear(); // Clear previous output

            Text title = new Text(matches.isEmpty() ? "No Search Found!\n\n" : "Search Found:\n\n");
            title.setFill(matches.isEmpty() ? Color.RED : Color.BLUE); // Color change based on result
            outputTextFlow.getChildren().add(title);

            for (MatchResult match : matches) {
                highlightMatches(match);
            }
        } catch (Exception e) {
            outputTextFlow.getChildren().clear();
            outputTextFlow.getChildren().add(new Text("Error: " + e.getMessage()));
        }
    }

    private void highlightMatches(MatchResult match) {
        String line = match.line();
        int start = 0;

        for (int[] position : match.positions()) {
            int matchStart = position[0];
            int matchEnd = position[1];

            if (start < matchStart) {
                outputTextFlow.getChildren().add(new Text(line.substring(start, matchStart)));
            }

            Text matchingText = new Text(line.substring(matchStart, matchEnd));
            matchingText.setFill(Color.rgb(43, 14, 230, 1));
            matchingText.setFont(Font.font(14));
            outputTextFlow.getChildren().add(matchingText);

            start = matchEnd;
        }

        if (start < line.length()) {
            outputTextFlow.getChildren().add(new Text(line.substring(start)));
        }

        outputTextFlow.getChildren().add(new Text("\n"));
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
