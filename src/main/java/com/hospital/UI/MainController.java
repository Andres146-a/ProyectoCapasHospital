package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;

public class MainController {

    @FXML private StackPane contenidoPrincipal;

    @FXML private Label lblPacientesActivos;
    @FXML private Label lblConsultasDia;
    @FXML private ListView<String> listAlertas;
    private Node vistaPrincipal;
    @FXML
    public void initialize() {
        // Configura los datos dinámicos solo una vez
        lblPacientesActivos.setText("120");
        lblConsultasDia.setText("35");
        listAlertas.getItems().addAll(
            "Medicamento A está agotado",
            "Paciente Juan Pérez tiene una cita sin confirmar",
            "Enfermero asignado a turno nocturno"
        );
    
        // Guarda la referencia de la vista principal para reutilizarla
        if (vistaPrincipal == null) {
            vistaPrincipal = contenidoPrincipal.getChildren().get(0); // Guarda el nodo inicial
        }
    }

    // Método genérico para cargar vistas dinámicas
    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Node vista = loader.load();

            // Configurar el controlador si es necesario
            Object controller = loader.getController();
            if (controller instanceof PacientesController) {
                ((PacientesController) controller).setMainController(this);
            }

            // Reemplazar el contenido del StackPane
            contenidoPrincipal.getChildren().setAll(vista);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar la vista: " + rutaFXML);
        }
    }

    // Método para restaurar el contenido original
    

    
    public void restaurarContenidoPrincipal() {
        // Limpia el StackPane y restaura la vista principal sin recargarla
        contenidoPrincipal.getChildren().clear();
        contenidoPrincipal.getChildren().add(vistaPrincipal);
    }
    
    

    // Métodos para navegación
    @FXML
    public void mostrarGestionPacientes() {
        cargarVista("/com/hospital/ui/pacientes.fxml");

    }

    @FXML
    public void mostrarGestionEnfermeros() {
        cargarVista("/com/hospital/ui/enfermeros.fxml");
    }

    @FXML
    public void mostrarSignosVitales() {
        cargarVista("/com/hospital/ui/signos_vital.fxml");
    }

    @FXML
    public void mostrarReportes() {
        cargarVista("/com/hospital/ui/reportes.fxml");
    }
    @FXML
public void volverDashboard() {
    restaurarContenidoPrincipal();
}


    // Método de error
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    public StackPane getContenidoPrincipal() {
        return contenidoPrincipal;
    }
    
    
}
