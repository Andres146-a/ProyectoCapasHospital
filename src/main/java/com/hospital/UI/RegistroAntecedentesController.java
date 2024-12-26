package com.hospital.UI;

import com.hospital.modelos.Antecedente;
import com.hospital.negocio.AntecedentesFacade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class RegistroAntecedentesController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextArea txtAntecedentesPersonales;

    @FXML
    private TextArea txtAntecedentesPatologicos;

    @FXML
    private TextArea txtAntecedentesClinicos;

    @FXML
    private TextArea txtAntecedentesGinecoObstetricos;

    private final AntecedentesFacade antecedentesFacade = new AntecedentesFacade();
    private int pacienteSeleccionadoId; // ID del paciente actual

    @FXML
    public void guardarAntecedente(ActionEvent event) {
        try {
            // Validar campos
            if (!validarCampos()) {
                mostrarAlertaError("Por favor, complete todos los campos obligatorios.");
                return;
            }

            // Guardar antecedentes personales
            if (!txtAntecedentesPersonales.getText().isEmpty()) {
                antecedentesFacade.guardarAntecedente(pacienteSeleccionadoId, "Personales", txtAntecedentesPersonales.getText());
            }

            // Guardar antecedentes patológicos
            if (!txtAntecedentesPatologicos.getText().isEmpty()) {
                antecedentesFacade.guardarAntecedente(pacienteSeleccionadoId, "Patológicos", txtAntecedentesPatologicos.getText());
            }

            // Guardar antecedentes clínicos
            if (!txtAntecedentesClinicos.getText().isEmpty()) {
                antecedentesFacade.guardarAntecedente(pacienteSeleccionadoId, "Clínicos", txtAntecedentesClinicos.getText());
            }

            // Guardar antecedentes gineco-obstétricos
            if (!txtAntecedentesGinecoObstetricos.getText().isEmpty()) {
                antecedentesFacade.guardarAntecedente(pacienteSeleccionadoId, "Gineco-Obstétricos", txtAntecedentesGinecoObstetricos.getText());
            }

            mostrarAlertaExito("Antecedentes guardados correctamente.");
            limpiarFormulario();

        } catch (Exception e) {
            mostrarAlertaError("Error al guardar los antecedentes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        return !(txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtEdad.getText().isEmpty());
    }

    private void limpiarFormulario() {
        txtAntecedentesPersonales.clear();
        txtAntecedentesPatologicos.clear();
        txtAntecedentesClinicos.clear();
        txtAntecedentesGinecoObstetricos.clear();
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setPacienteSeleccionadoId(int pacienteId) {
        this.pacienteSeleccionadoId = pacienteId;
    }
}
