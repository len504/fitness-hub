package com.gym;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App extends Application {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Starting Fitness Hub Application");
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        
        stage.setTitle("Fitness Hub - Gym Management System");
        stage.setScene(scene);
        stage.setMinWidth(1000);
        stage.setMinHeight(700);
        
        stage.show();
        logger.info("Application started successfully");
    }

    public static void main(String[] args) {
        launch();
    }
}