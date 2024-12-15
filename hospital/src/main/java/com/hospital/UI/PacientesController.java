package com.hospital.UI;

import com.hospital.negocio.PacientesFacade;
import com.hospital.modelos.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PacientesController {

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

    private final PacientesFacade pacientesFacade = new PacientesFacade();

    @FXML
    public void buscarPaciente() {
        String nombre = nombreField.getText();
        if (nombre.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un nombre para buscar.");
            return;
        }
        // Lógica de búsqueda
        System.out.println("Buscando paciente con nombre: " + nombre);
    }

    @FXML
    public void agregarPaciente() {
        System.out.println("Agregar paciente");
        // Lógica para agregar un paciente
    }

    @FXML
    public void actualizarPaciente() {
        System.out.println("Actualizar paciente");
        // Lógica para actualizar un paciente
    }

    @FXML
    public void eliminarPaciente() {
        System.out.println("Eliminar paciente");
        // Lógica para eliminar un paciente
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
