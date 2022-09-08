package com.paint.paint;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class Controller {

    @FXML
    protected void open() throws IOException { fileIO.open();}

    @FXML
    protected void save() { fileIO.save();}

    @FXML
    protected void saveAs() { fileIO.saveAs();}
}