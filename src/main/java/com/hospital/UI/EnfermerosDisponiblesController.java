package com.hospital.UI;

import com.hospital.modelos.Enfermero;
import com.hospital.negocio.EnfermeroFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EnfermerosDisponiblesController implements Initializable {

    @FXML private TextField searchField; // TextField para la búsqueda
    @FXML private TableView<Enfermero> enfermerosTable;
    @FXML private TableColumn<Enfermero, Number> idColumn;
    @FXML private TableColumn<Enfermero, String> nombreColumn;
    @FXML private TableColumn<Enfermero, String> especialidadColumn;
    @FXML private TableColumn<Enfermero, String> estadoColumn;
    @FXML private ComboBox<String> estadoComboBox; // ComboBox para seleccionar "Activo" o "Inactivo"
    @FXML private Button actualizarEstadoButton;
    private final ObservableList<Enfermero> enfermerosDisponibles = FXCollections.observableArrayList();
  

    private final ObservableList<Enfermero> listaEnfermeros = FXCollections.observableArrayList();

    // Instancia de la fachada
    private final EnfermeroFacade enfermeroFacade = new EnfermeroFacade();

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar columnas
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        especialidadColumn.setCellValueFactory(data -> data.getValue().especialidadProperty());
        estadoColumn.setCellValueFactory(data -> data.getValue().estadoProperty());
        estadoComboBox.setItems(FXCollections.observableArrayList("Activos", "Inactivos"));
        estadoComboBox.setValue("Activos"); // Valor por defecto
        // Cargar datos y configurar buscador
        cargarEnfermerosDisponibles();
        configurarBuscador();
    }
    @FXML
    private void filtrarEnfermeros() {
        String seleccion = estadoComboBox.getSelectionModel().getSelectedItem();
        if ("Activos".equals(seleccion)) {
            cargarEnfermerosActivos();
        } else if ("Inactivos".equals(seleccion)) {
            cargarEnfermerosInactivos();
        }
    }
    
    @FXML
    private void cargarEnfermerosActivos() {
        try {
            listaEnfermeros.clear();
            listaEnfermeros.addAll(enfermeroFacade.listarActivos()); // Método que devuelve los enfermeros activos
            enfermerosTable.setItems(listaEnfermeros);
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar enfermeros activos", e.getMessage());
        }
    } 
    @FXML
    private void cargarEnfermerosInactivos() {
        try {
            listaEnfermeros.clear();
            listaEnfermeros.addAll(enfermeroFacade.listarInactivos()); // Método que devuelve los enfermeros inactivos
            enfermerosTable.setItems(listaEnfermeros);
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar enfermeros inactivos", e.getMessage());
        }
    }
    private void cargarEnfermerosDisponibles() {
        try {
            // Obtener todos los enfermeros desde la fachada
            List<Enfermero> todosEnfermeros = enfermeroFacade.listarEnfermeros();

            // Filtrar enfermeros disponibles
            enfermerosDisponibles.clear();
            enfermerosDisponibles.addAll(todosEnfermeros.stream()
                    .filter(enfermero -> "Disponible".equals(enfermero.getEstado()))
                    .toList());

            // Asignar los enfermeros disponibles a la tabla
            enfermerosTable.setItems(enfermerosDisponibles);
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar los enfermeros disponibles", e.getMessage());
        }
    }

    private void configurarBuscador() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Enfermero> filtrados = enfermerosDisponibles.filtered(enfermero ->
                    enfermero.getNombre().toLowerCase().contains(newValue.toLowerCase()) ||
                    enfermero.getEspecialidad().toLowerCase().contains(newValue.toLowerCase())
            );
            enfermerosTable.setItems(filtrados);
        });
    }
    @FXML
private void cambiarEstadoEnfermero() {
    Enfermero enfermeroSeleccionado = enfermerosTable.getSelectionModel().getSelectedItem();
    if (enfermeroSeleccionado == null) {
        mostrarMensajeError("Error", "Por favor selecciona un enfermero para actualizar su estado.");
        return;
    }

    String nuevoEstado = estadoComboBox.getValue();
    try {
        enfermeroFacade.actualizarEstado(enfermeroSeleccionado.getId(), nuevoEstado);
        enfermeroSeleccionado.setEstado(nuevoEstado); // Actualiza el estado en la tabla
        enfermerosTable.refresh(); // Refresca la tabla
        mostrarMensajeInfo("Éxito", "Estado del enfermero actualizado correctamente.");
    } catch (Exception e) {
        mostrarMensajeError("Error", "No se pudo actualizar el estado: " + e.getMessage());
    }
}

// Mensaje de información
private void mostrarMensajeInfo(String titulo, String mensaje) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setContentText(mensaje);
    alert.showAndWait();
}
    @FXML
    private void regresarDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            mostrarMensajeError("Error", "MainController no está configurado.");
        }
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método adicional para actualizar el estado de un enfermero
    @FXML
    private void actualizarEstadoEnfermero() {
        Enfermero seleccionado = enfermerosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensajeError("Error", "Debe seleccionar un enfermero para actualizar su estado.");
            return;
        }

        try {
            String nuevoEstado = "Ocupado".equals(seleccionado.getEstado()) ? "Disponible" : "Ocupado";
            enfermeroFacade.actualizarEstado(seleccionado.getId(), nuevoEstado);
            seleccionado.setEstado(nuevoEstado);
            enfermerosTable.refresh();
        } catch (Exception e) {
            mostrarMensajeError("Error al actualizar estado", e.getMessage());
        }
    }

    // Método adicional para refrescar la tabla
    @FXML
    private void refrescarTabla() {
        cargarEnfermerosDisponibles();
    }
}
