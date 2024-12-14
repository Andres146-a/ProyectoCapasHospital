package com.hospital.UI;

import com.hospital.negocio.PacientesFacade;
import com.hospital.modelos.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class PacientesController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidoField;

    @FXML
    private TextField fechaNacimientoField;

    private final PacientesFacade pacientesFacade = new PacientesFacade();

    @FXML
    public void guardarPaciente() {
        try {
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String fechaNacimiento = fechaNacimientoField.getText();

            // Crear un objeto Paciente
            Paciente paciente = new Paciente(0, nombre, apellido, fechaNacimiento);

            // Llamar a la fachada para guardar
            pacientesFacade.guardarPaciente(paciente);

            mostrarMensaje("Éxito", "Paciente guardado correctamente.");
        } catch (Exception e) {
            mostrarMensaje("Error", e.getMessage());
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
