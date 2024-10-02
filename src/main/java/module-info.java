module com.css124.demo2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.css124.demo2 to javafx.fxml;
    exports com.css124.demo2;
}