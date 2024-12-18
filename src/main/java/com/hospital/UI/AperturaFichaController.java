package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import com.hospital.repositorios.BaseController;
import java.io.IOException;

public class AperturaFichaController {

    private MainController mainController; // Referencia al MainController

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void gestionarCategorias() {
        System.out.println("Abriendo Gestión de Categorías de Antecedentes...");
        cargarVista("/com/hospital/ui/gestion_categorias.fxml");
    }

    @FXML
    private void ingresarPacientes() {
        System.out.println("Abriendo Ingreso de Pacientes...");
        cargarVista("/com/hospital/ui/ingreso_pacientes.fxml");
    }

    @FXML
    private void consultarAntecedentes() {
        System.out.println("Abriendo Consulta de Antecedentes...");
        cargarVista("/com/hospital/ui/consulta_antecedentes.fxml");
    }

    @FXML
    private void regresarDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }

    private void cargarVista(String rutaFXML) {
        if (mainController != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
                Parent vista = loader.load();
    
                // Configurar el controlador si es una subclase de BaseController
                BaseController controlador = loader.getController();
                controlador.setMainController(mainController);
    
                // Cargar la vista en el StackPane del MainController
                StackPane contenidoPrincipal = mainController.getContenidoPrincipal();
                contenidoPrincipal.getChildren().setAll(vista);
            } catch (IOException e) {
                e.printStackTrace();
                mostrarMensaje("Error", "No se pudo cargar la vista: " + rutaFXML);
            }
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }
    

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
