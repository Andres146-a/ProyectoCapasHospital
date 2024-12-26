package com.hospital.UI;
import com.hospital.repositorios.BaseController;

import java.io.IOException;
import com.hospital.modelos.Paciente;
import com.hospital.negocio.AntecedentesFacade;
import com.hospital.negocio.PacientesFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IngresoPacientesController extends BaseController {

    @FXML private TextField txtCedula, txtNombre, txtApellidos, txtTelefono, txtEmail, txtEnfermedad;
    @FXML private TextArea txtDireccion, txtAntecedenteDetalle;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ComboBox<String> cbSexo, cbTipoAntecedente;
    // @FXML private TableView<Paciente> tablaPacientes;
    // @FXML private TableColumn<Paciente, Integer> colId;
    // @FXML private TableColumn<Paciente, String> colNombre, colApellido;

    private final PacientesFacade pacientesFacade = new PacientesFacade();
    private final AntecedentesFacade antecedentesFacade = new AntecedentesFacade();

    @FXML
    private void initialize() {
        cbSexo.getItems().addAll("Masculino", "Femenino");
        cbTipoAntecedente.getItems().addAll("Personales", "Patológicos", "Clínicos", "Gineco-Obstétricos");

        // configurarColumnas();
        cargarPacientes();

        cbTipoAntecedente.setOnAction(event -> manejarTipoAntecedente());
        txtAntecedenteDetalle.setVisible(false);
    }

    // private void configurarColumnas() {
    //     colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
    //     colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
    //     colApellido.setCellValueFactory(data -> data.getValue().apellidoProperty());
    // }

    private void cargarPacientes() {
        ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList(pacientesFacade.obtenerTodosLosPacientes());
        // tablaPacientes.setItems(listaPacientes);
    }

    @FXML
    private void guardarPaciente() {
        if (validarCampos()) { // Validar los campos obligatorios del paciente
            try {
                // Crear el objeto Paciente con los datos del formulario
                Paciente nuevoPaciente = new Paciente(
                    0, // ID inicial generado automáticamente por la base de datos
                    txtNombre.getText(),
                    txtApellidos.getText(),
                    calcularEdad(dpFechaNacimiento.getValue().getYear()), // Calcula la edad
                    txtEnfermedad.getText(),
                    txtCedula.getText(),
                    txtTelefono.getText(),
                    txtEmail.getText(),
                    txtDireccion.getText(),
                    "", // Alerta inicial vacía
                    true // Estado activo por defecto
                );
    
                // Guardar paciente en la base de datos
                int idPaciente = pacientesFacade.guardarPaciente(nuevoPaciente);
    
                if (idPaciente > 0) { // Si se genera un ID válido
                    // Verificar si hay antecedentes
                    String tipoAntecedente = cbTipoAntecedente.getValue();
                    String detalleAntecedente = txtAntecedenteDetalle.getText();
    
                    if (tipoAntecedente != null && !tipoAntecedente.isEmpty() &&
                        detalleAntecedente != null && !detalleAntecedente.trim().isEmpty()) {
                        
                        // Insertar antecedente en la base de datos
                        antecedentesFacade.guardarAntecedente(idPaciente, tipoAntecedente, detalleAntecedente);
    
                        mostrarAlertaExito("Paciente y antecedente guardados correctamente.");
                    } else {
                        mostrarAlertaExito("Paciente guardado correctamente. Sin antecedentes registrados.");
                    }
    
                    limpiarCampos(); // Limpia los campos tras guardar
                } else {
                    mostrarAlertaError("Error: No se pudo generar el ID del paciente.");
                }
            } catch (Exception e) {
                mostrarAlertaError("Error al guardar el paciente: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarAlertaError("Por favor, complete todos los campos obligatorios.");
        }
    }
        @FXML
    private void regresarAlDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            mostrarAlertaError("No se pudo regresar al Dashboard porque el controlador principal no está configurado.");
        }
    }
    
    @FXML
    private void guardarAntecedente() {
        if (validarCamposAntecedente()) {
            try {
                int idPaciente = obtenerIdPacienteSeleccionado();
                if (idPaciente <= 0) return;

                antecedentesFacade.guardarAntecedente(
                    idPaciente, 
                    cbTipoAntecedente.getValue(), 
                    txtAntecedenteDetalle.getText()
                );

                mostrarAlertaExito("Antecedente guardado correctamente.");
                txtAntecedenteDetalle.clear();
            } catch (Exception e) {
                mostrarAlertaError("Error al guardar el antecedente: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarAlertaError("Por favor, complete los campos del antecedente.");
        }
    }

    private int obtenerIdPacienteSeleccionado() {
        // Retorna directamente el ID de un campo de texto o combobox, si aplica.
        return Integer.parseInt(txtCedula.getText());
    }

    private boolean validarCampos() {
        return !txtCedula.getText().isEmpty() &&
               !txtNombre.getText().isEmpty() &&
               !txtApellidos.getText().isEmpty() &&
               dpFechaNacimiento.getValue() != null &&
               cbSexo.getValue() != null &&
               !txtEnfermedad.getText().isEmpty();
    }

    private boolean validarCamposAntecedente() {
        return cbTipoAntecedente.getValue() != null && !txtAntecedenteDetalle.getText().isEmpty();
    }

    private void manejarTipoAntecedente() {
        String tipoSeleccionado = cbTipoAntecedente.getValue();
        txtAntecedenteDetalle.setVisible(tipoSeleccionado != null);
        txtAntecedenteDetalle.setPromptText("Escriba los antecedentes " + (tipoSeleccionado != null ? tipoSeleccionado.toLowerCase() : ""));
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
        txtEnfermedad.clear();
        cbTipoAntecedente.setValue(null);
        txtAntecedenteDetalle.clear();
        txtAntecedenteDetalle.setVisible(false);
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

    private int calcularEdad(int anioNacimiento) {
        return java.time.Year.now().getValue() - anioNacimiento;
    }
}
