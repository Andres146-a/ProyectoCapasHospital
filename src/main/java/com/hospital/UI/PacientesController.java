package com.hospital.UI;

import com.hospital.modelos.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PacientesController {

    private MainController mainController; // Referencia al MainController

    // Método para establecer la referencia al MainController
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private TextField nombreField;

    @FXML
    private TableView<Paciente> pacientesTable;

    @FXML
    private TableColumn<Paciente, Integer> idColumn;

    @FXML
    private TableColumn<Paciente, String> nombreColumn;

    @FXML
    private TableColumn<Paciente, Integer> edadColumn;

    @FXML
    public void initialize() {
        System.out.println("PacientesController inicializado correctamente");
        // Aquí puedes inicializar la tabla si es necesario.
    }

    @FXML
    public void volverDashboard() {
        if (mainController != null) {
            // Llama al método del MainController para restaurar el StackPane
            mainController.restaurarContenidoPrincipal();
        } else {
            mostrarMensaje("Error", "No se puede regresar al dashboard principal.");
        }
    }

    @FXML
    public void buscarPaciente() {
        String nombre = nombreField.getText();
        if (nombre.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un nombre para buscar.");
            return;
        }
        System.out.println("Buscando paciente con nombre: " + nombre);
        // Aquí implementa lógica real para buscar un paciente.
    }

    @FXML
    public void agregarPaciente() {
        System.out.println("Agregar paciente");
        // Lógica para agregar un paciente
    }

    @FXML
    public void actualizarPaciente() {
        System.out.println("Actualizar paciente");
        // Implementar lógica para actualizar el paciente
    }

    @FXML
    public void eliminarPaciente() {
        System.out.println("Eliminar paciente");
        // Implementar lógica para eliminar el paciente
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
