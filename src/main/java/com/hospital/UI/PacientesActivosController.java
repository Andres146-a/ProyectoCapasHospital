package com.hospital.UI;

import com.hospital.modelos.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PacientesActivosController {

    @FXML private TableView<Paciente> tablaPacientes;
    @FXML private TableColumn<Paciente, Number> idColumn;
    @FXML private TableColumn<Paciente, String> nombreColumn;
    @FXML private TableColumn<Paciente, Number> edadColumn;
    @FXML private Button btnGestionar;
    @FXML private Button btnRegresar; // Botón para regresar al dashboard

    private ObservableList<Paciente> pacientesActivos = FXCollections.observableArrayList();
    private MainController mainController; // Referencia al MainController
    private String rolUsuario; // Define si es 'Enfermero' o 'Doctor'

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setRolUsuario(String rol) {
        this.rolUsuario = rol;
        btnGestionar.setVisible("Enfermero".equals(rol));
    }

    @FXML
    public void initialize() {
        // Configuración de columnas
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        edadColumn.setCellValueFactory(cellData -> cellData.getValue().edadProperty());

        // Cargar datos de ejemplo
        pacientesActivos.addAll(
            new Paciente(1, "Juan", "Pérez", 30, "Hipertensión", "85 lpm", "150/95", "37.5°C", "Alerta: Hipertensión"),
            new Paciente(2, "María", "López", 25, "Gastritis", "95 lpm", "130/85", "38.2°C", "Alerta: Fiebre"),
            new Paciente(3, "Carlos", "González", 40, "Hipertensión", "50 lpm", "120/80", "36.8°C", "Alerta: Bradicardia")
        );

        tablaPacientes.setItems(pacientesActivos);
    }

    @FXML
    private void gestionarPaciente() {
        Paciente seleccionado = tablaPacientes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            System.out.println("Gestionando paciente: " + seleccionado.getNombre());
            // Aquí puedes redirigir a la vista de gestión de pacientes
        }
    }

    @FXML
    private void regresarDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal(); // Llama al método del MainController
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }
}

