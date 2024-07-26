# VCS-TextProcessing
## Overview
This application is designed for text manipulation tasks such as searching, matching, replacing text, and managing text entries. It provides a user-friendly interface built with JavaFX and supports various operations that are essential for handling and processing large amounts of textual data.

## Features

### Text Management
- **Add Data**: Users can add new text data to the system.
- **Update Data**: Existing text entries can be updated with new information.
- **Delete Data**: Users can remove unwanted text data from the system.
- **View Data**: Users can view specific text entries from the list.
- **Import Data**: Text data can be imported from a text file.
- **Export Data**: Selected text data can be exported to a text file.

### Text Processing
- **Search**: The application allows users to search within the text data using regular expressions. Search results highlight the matched text.
- **Match**: Users can perform pattern matching to check if the entire text entry matches a specified pattern.
- **Replace**: This feature enables users to replace occurrences of a specified pattern within the text data with a new substring.

## User Interface
The application features a main window with controls for text input, a list view to display text entries, and a text flow area for output that highlights search and match results. Interactive buttons are provided for each feature.

## Technical Details

### Models
**DataItem**: Represents a single piece of text data with an identifier.
**DataManager**: Manages a collection of DataItem objects, handling operations like add, update, and delete.
**RegexProcessor**: Handles regex-based operations such as search, match, and replace within the collection of data items.

### Controller
**MainController**: Manages user interactions, updates the UI based on user input, and calls upon the DataManager and RegexProcessor to perform data operations.

### Main and Launcher
**Main**: Sets up the JavaFX application, loading the main window and displaying it to the user.
**Launcher**: Entry point of the application, calling the main method from Main.

## Testing
**JUnit 5**: Comprehensive unit tests have been written for the DataItem, DataManager, and RegexProcessor classes to ensure functionality is working as expected. These tests check the validity of each feature, including object creation, equality, updates, deletions, and all regex operations.

## Installation
To run the application, ensure you have JavaFX configured on your IDE or runtime environment, clone the repository, and execute the Launcher class.

## Contributions
Contributions are welcome. Please fork the repository, make changes, and submit a pull request.