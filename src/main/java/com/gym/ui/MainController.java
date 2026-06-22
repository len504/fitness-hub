package com.gym.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML
    private VBox contentArea;

    @FXML
    public void initialize() {
        logger.info("MainController initialized");
    }
}