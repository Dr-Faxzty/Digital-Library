module come.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.manager to org.junit.platform.commons;
    opens com.example.model to com.google.gson;
    opens com.example.common.enums to com.google.gson;
    opens com.example.common.adapter to com.google.gson;
    exports com.example;
}