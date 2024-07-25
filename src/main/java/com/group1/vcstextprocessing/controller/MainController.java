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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    @FXML
    public void onReplaceButtonClick() {
        String regexPattern = regexTextField.getText().trim();
        String replacement = replaceTextField.getText().trim();

        int selectedIndex = dataListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            showAlert("Replace Error", "Please select an item to replace text in.");
            return;
        }
        if (regexPattern.isEmpty()) {
            showAlert("Replace Error", "Regex pattern cannot be empty.");
            return;
        }
        if (replacement.isEmpty()) {
            showAlert("Replace Error", "Replacement text cannot be empty.");
            return;
        }

        try {
            DataItem selectedItem = dataManager.getAllData().get(selectedIndex);
            String modifiedText = selectedItem.getData().replaceAll(regexPattern, replacement);

            // Create a new DataItem with the replaced text and update it
            DataItem updatedItem = new DataItem(selectedItem.getId(), modifiedText);
            dataManager.updateData(selectedIndex, updatedItem);
            dataListView.getItems().set(selectedIndex, modifiedText);

            showAlert("Success", "Text replaced successfully in the selected item.");
        } catch (Exception e) {
            showAlert("Replace Error", "Error in replacing text: " + e.getMessage());
        }
    }

    @FXML
    public void onReplaceAllButtonClick() {
        String regexPattern = regexTextField.getText().trim();
        String replacement = replaceTextField.getText().trim();

        if (regexPattern.isEmpty() || replacement.isEmpty()) {
            showAlert("Replace Error", "Both regex pattern and replacement text must be provided.");
            return;
        }

        try {
            List<DataItem> modifiedData = regexProcessor.searchAndReplaceInCollection(dataManager.getAllData(), regexPattern, replacement);
            dataManager.setDataList(modifiedData);

            // Update ListView with the modified data
            dataListView.getItems().clear();
            dataListView.getItems().addAll(modifiedData.stream().map(DataItem::getData).toList());

            showAlert("Success", "All occurrences replaced successfully.");
        } catch (Exception e) {
            showAlert("Replace Error", "Error in replacing text: " + e.getMessage());
        }
    }

    @FXML
    public void onAddDataButtonClick() {
        String newData = dataInputField.getText();

        if (!newData.isEmpty()) {

            DataItem newItem = new DataItem(dataManager.getAllData().size() + 1, newData);
            dataManager.addData(newItem);
            dataListView.getItems().add(newData);
            dataInputField.clear();
        } else {
            showAlert("Input Error", "Input field cannot be empty.");
        }
    }
    @FXML
    public void onViewButtonClick() {
        String selectedText = dataListView.getSelectionModel().getSelectedItem();
        if (selectedText == null) {
            showAlert("View Error", "Please select an item to view.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("View Text");
        alert.setHeaderText(null);
        alert.setContentText(selectedText);
        alert.showAndWait();
    }

    @FXML
    public void onExportButtonClick() {
        String selectedText = dataListView.getSelectionModel().getSelectedItem();
        if (selectedText == null) {
            showAlert("Export Error", "Please select an item to export.");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Text File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
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
