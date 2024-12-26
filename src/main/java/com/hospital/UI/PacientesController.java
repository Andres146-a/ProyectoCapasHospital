package com.hospital.UI;

import com.hospital.modelos.Paciente;
import com.hospital.negocio.PacientesFacade;
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
    private final PacientesFacade pacientesFacade = new PacientesFacade(); // Instancia de la fachada

    @FXML private TextField nombreField;
    @FXML private TextField searchField;
    @FXML private TableView<Paciente> pacientesTable;
    @FXML private TableColumn<Paciente, Integer> idColumn;
    @FXML private TableColumn<Paciente, String> nombreColumn;
    @FXML private TableColumn<Paciente, Integer> edadColumn;

    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        System.out.println("PacientesController inicializado correctamente");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        edadColumn.setCellValueFactory(new PropertyValueFactory<>("edad"));

        configurarBuscador();
        cargarPacientes();
    }
    private void configurarBuscador() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Paciente> filtrados = listaPacientes.filtered(
                paciente -> paciente.getNombre().toLowerCase().contains(newValue.toLowerCase())
            );
            pacientesTable.setItems(filtrados);
        });
    }
    private void cargarPacientes() {
        try {
            listaPacientes.setAll(pacientesFacade.obtenerTodosLosPacientes());
            pacientesTable.setItems(listaPacientes);
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error al cargar los pacientes: " + e.getMessage());
        }
    }

    // @FXML
    // public void buscarPaciente() {
    //     String nombreBuscado = nombreField.getText().trim();
    //     if (nombreBuscado.isEmpty()) {
    //         mostrarMensaje("Error", "Ingrese un nombre para buscar.");
    //         return;
    //     }

    //     try {
    //         ObservableList<Paciente> filtrados = listaPacientes.filtered(paciente ->
    //                 paciente.getNombre().toLowerCase().contains(nombreBuscado.toLowerCase())
    //         );

    //         if (filtrados.isEmpty()) {
    //             mostrarMensaje("Información", "No se encontraron pacientes con el nombre ingresado.");
    //         } else {
    //             pacientesTable.setItems(filtrados);
    //         }
    //     } catch (Exception e) {
    //         mostrarMensaje("Error", "Ocurrió un error al buscar pacientes: " + e.getMessage());
    //     }
    // }

    @FXML
    public void agregarPaciente() {
        System.out.println("Agregar paciente - Acción en desarrollo");
        mostrarMensaje("Información", "Funcionalidad de agregar paciente pendiente de implementación.");
    }

    @FXML
    public void actualizarPaciente() {
        Paciente seleccionado = pacientesTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                // Lógica para actualizar paciente (simulación por ahora)
                pacientesFacade.guardarPaciente(seleccionado);
                mostrarMensaje("Éxito", "Paciente actualizado correctamente.");
                cargarPacientes(); // Refrescar la tabla
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrió un error al actualizar el paciente: " + e.getMessage());
            }
        } else {
            mostrarMensaje("Error", "Seleccione un paciente para actualizar.");
        }
    }

    @FXML
    public void eliminarPaciente() {
        Paciente seleccionado = pacientesTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                pacientesFacade.eliminarPaciente(seleccionado.getId());
                listaPacientes.remove(seleccionado);
                mostrarMensaje("Éxito", "Paciente eliminado correctamente.");
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrió un error al eliminar el paciente: " + e.getMessage());
            }
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

