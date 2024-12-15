package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contenidoPrincipal;

    @FXML
    public void mostrarGestionPacientes() {
        cargarVista("/com/hospital/ui/pacientes.fxml");
    }
    @FXML
public void mostrarReportes() {
    // Lógica para cargar la vista de reportes
    cargarVista("/com/hospital/ui/reportes.fxml");
}

    @FXML
    public void mostrarGestionEnfermeros() {
        cargarVista("/com/hospital/ui/enfermeros.fxml");
    }

    @FXML
    public void mostrarGestionSignosVitales() {
        cargarVista("/com/hospital/ui/signos_vital.fxml");
    }

    @FXML
    public void mostrarConsultasMedicas() {
        cargarVista("/com/hospital/ui/consultas.fxml");
    }

    @FXML
    public void salir() {
        System.exit(0);
    }

    private void cargarVista(String rutaFXML) {
        try {
            Node vista = FXMLLoader.load(getClass().getResource(rutaFXML));
            contenidoPrincipal.getChildren().setAll(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
