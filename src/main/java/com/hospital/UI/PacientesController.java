package com.hospital.UI;

import com.hospital.modelos.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PacientesController {

    private MainController mainController; // Referencia al MainController

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML private TextField nombreField;

    @FXML private TableView<Paciente> pacientesTable;
    @FXML private TableColumn<Paciente, Integer> idColumn;
    @FXML private TableColumn<Paciente, String> nombreColumn;
    @FXML private TableColumn<Paciente, Integer> edadColumn;

    private ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println("PacientesController inicializado correctamente");

        // Configurar columnas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        edadColumn.setCellValueFactory(new PropertyValueFactory<>("edad"));

        // Datos de ejemplo para la tabla
        listaPacientes.addAll(
            new Paciente(1, "Juan", "Pérez", 30, "Hipertensión", "85 lpm", "150/95", "37.5°C", "Alerta: Hipertensión"),
            new Paciente(2, "María", "López", 25, "Gastritis", "95 lpm", "130/85", "38.2°C", "Alerta: Fiebre"),
            new Paciente(3, "Carlos", "González", 40, "Hipertensión", "50 lpm", "120/80", "36.8°C", "Alerta: Bradicardia")
);


        pacientesTable.setItems(listaPacientes);
    }

    @FXML
    public void buscarPaciente() {
        String nombreBuscado = nombreField.getText();
        if (nombreBuscado.isEmpty()) {
            mostrarMensaje("Error", "Ingrese un nombre para buscar.");
            return;
        }

        // Filtrar pacientes
        ObservableList<Paciente> filtrados = listaPacientes.filtered(paciente ->
                paciente.getNombre().toLowerCase().contains(nombreBuscado.toLowerCase())
        );

        if (filtrados.isEmpty()) {
            mostrarMensaje("Información", "No se encontraron pacientes con el nombre ingresado.");
        } else {
            pacientesTable.setItems(filtrados);
        }
    }

    @FXML
    public void agregarPaciente() {
        System.out.println("Agregar paciente - Acción pendiente");
        mostrarMensaje("Información", "Funcionalidad de agregar paciente aún no implementada.");
    }

    @FXML
    public void actualizarPaciente() {
        System.out.println("Actualizar paciente - Acción pendiente");
        mostrarMensaje("Información", "Funcionalidad de actualizar paciente aún no implementada.");
    }

    @FXML
    public void eliminarPaciente() {
        Paciente seleccionado = pacientesTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaPacientes.remove(seleccionado);
            mostrarMensaje("Éxito", "Paciente eliminado correctamente.");
        } else {
            mostrarMensaje("Error", "Seleccione un paciente para eliminar.");
        }
    }

    @FXML
    public void volverDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            mostrarMensaje("Error", "No se puede regresar al dashboard principal.");
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
