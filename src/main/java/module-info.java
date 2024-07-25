module com.group1.vcstextprocessing {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.group1.vcstextprocessing to javafx.fxml;
    exports com.group1.vcstextprocessing;
    exports com.group1.vcstextprocessing.controller to javafx.fxml;
    opens com.group1.vcstextprocessing.controller to javafx.fxml;
}

