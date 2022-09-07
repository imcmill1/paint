module com.paint.paint {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.paint.paint to javafx.fxml;
    exports com.paint.paint;
}