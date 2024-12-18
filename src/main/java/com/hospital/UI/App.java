package com.hospital.UI;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static MainController mainController;
    public static MainController getMainController() {
        return mainController;
    }
    @Override
    public void start(Stage stage) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/main.fxml"));
            Parent root = loader.load();
            mainController = loader.getController();
            System.out.println("FXML cargado correctamente.");

            // Crear la escena principal
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Hospital Management System");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
