package com.hospital.UI;

import com.hospital.modelos.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GestionarPacienteController {

    @FXML private Label lblId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEdad;

    private Paciente paciente;

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;

        // Llena los campos con los datos del paciente seleccionado
        lblId.setText(String.valueOf(paciente.getId()));
        txtNombre.setText(paciente.getNombre());
        txtApellido.setText(paciente.getApellido());
        txtEdad.setText(String.valueOf(paciente.getEdad()));
    }

    @FXML
    public void guardarCambios() {
        // Actualiza los datos del paciente con los valores de los campos
        paciente.setNombre(txtNombre.getText());
        paciente.setApellido(txtApellido.getText());
        paciente.setEdad(Integer.parseInt(txtEdad.getText()));

        // Cierra la ventana
        cerrarVentana();
    }

    @FXML
    public void cancelar() {
        // Cierra la ventana sin realizar cambios
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
}
