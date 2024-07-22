module com.group1.vcstextprocessing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.group1.vcstextprocessing to javafx.fxml;
    exports com.group1.vcstextprocessing;
}