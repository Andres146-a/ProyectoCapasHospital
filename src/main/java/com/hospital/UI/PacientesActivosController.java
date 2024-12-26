package com.hospital.UI;

import com.hospital.modelos.Paciente;
import com.hospital.negocio.PacientesFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PacientesActivosController {

    @FXML
    private TableView<Paciente> pacientesTable;
    @FXML
    private TableColumn<Paciente, Number> idColumn;
    @FXML
    private TableColumn<Paciente, String> nombreColumn;
    @FXML
    private TableColumn<Paciente, Number> edadColumn;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> statusComboBox;

    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    private final PacientesFacade pacientesFacade = new PacientesFacade();

    @FXML
    public void initialize() {
        // Configure table columns
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        edadColumn.setCellValueFactory(data -> data.getValue().edadProperty());

        // Initialize ComboBox
        statusComboBox.setItems(FXCollections.observableArrayList("Activos", "Inactivos"));
        statusComboBox.getSelectionModel().selectFirst(); // Default to "Activos"

        // Load patients based on default selection
        cargarPacientesActivos();

        // Configure search functionality
        configurarBuscador();
    }

    private void cargarPacientesActivos() {
        listaPacientes.clear();
        listaPacientes.addAll(pacientesFacade.listarActivos());
        pacientesTable.setItems(listaPacientes);
    }

    private void cargarPacientesInactivos() {
        listaPacientes.clear();
        listaPacientes.addAll(pacientesFacade.listarInactivos());
        System.out.println("Pacientes inactivos cargados: " + listaPacientes.size());
        for (Paciente p : listaPacientes) {
            System.out.println("Paciente inactivo: " + p.getNombre() + " - " + p.getActivo());
        }
        pacientesTable.setItems(listaPacientes);
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
    public void filtrarPacientes() {
        String seleccion = statusComboBox.getSelectionModel().getSelectedItem();
        if (seleccion.equals("Activos")) {
            cargarPacientesActivos();
        } else if (seleccion.equals("Inactivos")) {
            cargarPacientesInactivos();
        }
    }

    @FXML
    public void toggleEstadoPaciente() {
        Paciente pacienteSeleccionado = pacientesTable.getSelectionModel().getSelectedItem();
        if (pacienteSeleccionado == null) {
            mostrarAlerta("Error", "Selecciona un paciente para cambiar su estado.");
            return;
        }
    
        // Alternar el estado del paciente basado en su estado actual
        boolean nuevoEstado = !pacienteSeleccionado.getActivo(); // Si está activo, lo desactiva, y viceversa
        pacientesFacade.cambiarEstado(pacienteSeleccionado.getId(), nuevoEstado);
    
        // Actualizar la tabla después del cambio
        filtrarPacientes();
    }
    
    

    

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    private void mostrarError(String mensaje) {
        mostrarAlerta("Error", mensaje);
    }
    @FXML
public void regresarDashboard() {
    if (mainController != null) {
        mainController.restaurarContenidoPrincipal();
    } else {
        mostrarError("MainController no está configurado.");
    }
}

    
}
