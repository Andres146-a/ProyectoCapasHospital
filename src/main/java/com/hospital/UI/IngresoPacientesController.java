package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.hospital.repositorios.BaseController;

import javafx.event.ActionEvent;

public class IngresoPacientesController extends BaseController{

    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ComboBox<String> cbSexo;
    @FXML private TextField txtOcupacion;
    @FXML private TextField txtTipoSangre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private TextArea txtDireccion;

    @FXML
    public void initialize() {
        cbSexo.getItems().addAll("Masculino", "Femenino");
    }
    @FXML
    private void regresarAlDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }
    
    @FXML
    private void guardarPaciente(ActionEvent event) {
        if (validarCampos()) {
            // Simular almacenamiento en la base de datos
            System.out.println("Paciente guardado correctamente:");
            System.out.println("Cédula: " + txtCedula.getText());
            System.out.println("Nombre: " + txtNombre.getText() + " " + txtApellidos.getText());
            System.out.println("Fecha de Nacimiento: " + dpFechaNacimiento.getValue());
            System.out.println("Sexo: " + cbSexo.getValue());
            System.out.println("Teléfono: " + txtTelefono.getText());
            // Limpiar los campos después de guardar
            limpiarCampos();
        } else {
            mostrarAlerta("Por favor, llena todos los campos obligatorios.");
        }
    }

    private boolean validarCampos() {
        return !txtCedula.getText().isEmpty() && !txtNombre.getText().isEmpty()
                && !txtApellidos.getText().isEmpty() && dpFechaNacimiento.getValue() != null
                && cbSexo.getValue() != null;
    }

    private void limpiarCampos() {
        txtCedula.clear();
        txtNombre.clear();
        txtApellidos.clear();
        dpFechaNacimiento.setValue(null);
        cbSexo.setValue(null);
        txtTelefono.clear();
        txtEmail.clear();
        txtDireccion.clear();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private MainController mainController;


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
