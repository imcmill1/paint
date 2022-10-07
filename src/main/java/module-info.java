module com.paint.paint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.commons.io;


    opens com.paint.paint to javafx.fxml;
    exports com.paint.paint;
}