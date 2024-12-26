package com.hospital.UI;

import com.hospital.modelos.Paciente;
import com.hospital.negocio.PacientesFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PacientesInactivosController {

    @FXML private TableView<Paciente> pacientesTable;
    @FXML private TableColumn<Paciente, Number> idColumn;
    @FXML private TableColumn<Paciente, String> nombreColumn;
    @FXML private TableColumn<Paciente, Number> edadColumn;
    @FXML private TextField searchField;

    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    private final PacientesFacade pacientesFacade = new PacientesFacade();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        edadColumn.setCellValueFactory(cellData -> cellData.getValue().edadProperty());

        cargarPacientesInactivos();
        configurarBuscador();
    }

    private void cargarPacientesInactivos() {
        listaPacientes.clear();
        try {
            listaPacientes.addAll(pacientesFacade.listarInactivos());
            pacientesTable.setItems(listaPacientes);
        } catch (Exception e) {
            mostrarError("Error al cargar los pacientes inactivos: " + e.getMessage());
        }
    }

    private void configurarBuscador() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                pacientesTable.setItems(listaPacientes);
            } else {
                ObservableList<Paciente> filtrados = FXCollections.observableArrayList(
                    listaPacientes.filtered(p -> p.getNombre().toLowerCase().contains(newValue.toLowerCase()))
                );
                pacientesTable.setItems(filtrados);
            }
        });
    }

    @FXML
    private void activarPaciente() {
        Paciente pacienteSeleccionado = pacientesTable.getSelectionModel().getSelectedItem();
        if (pacienteSeleccionado == null) {
            mostrarAlerta("Advertencia", "Selecciona un paciente para activar.");
            return;
        }

        try {
            pacientesFacade.cambiarEstado(pacienteSeleccionado.getId(), true); // Activar paciente
            mostrarInformacion("Éxito", "Paciente activado correctamente.");
            cargarPacientesInactivos(); // Recargar lista
        } catch (Exception e) {
            mostrarError("Error al activar el paciente: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarInformacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void regresarDashboard() {
        // Implementar la lógica para regresar al dashboard principal
    }
}
