package com.hospital.UI;

import com.hospital.modelos.Enfermero;
import com.hospital.negocio.EnfermeroFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EnfermerosController {

    @FXML private TextField nombreField;
    @FXML private TextField especialidadField;
    @FXML private TableView<Enfermero> enfermerosTable;

    @FXML private TableColumn<Enfermero, Number> idColumn;
    @FXML private TableColumn<Enfermero, String> nombreColumn;
    @FXML private TableColumn<Enfermero, String> especialidadColumn;
    @FXML private TableColumn<Enfermero, String> estadoColumn;

    private final ObservableList<Enfermero> enfermeros = FXCollections.observableArrayList();
    
    // Instancia de la fachada
    private final EnfermeroFacade enfermeroFacade = new EnfermeroFacade();

    @FXML
    public void initialize() {
        // Configurar las columnas para enlazar las propiedades
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        especialidadColumn.setCellValueFactory(data -> data.getValue().especialidadProperty());
        estadoColumn.setCellValueFactory(data -> data.getValue().estadoProperty());

        // Asignar la lista a la tabla
        enfermerosTable.setItems(enfermeros);

        // Cargar datos desde la base de datos
        cargarEnfermerosDesdeBD();
    }

    @FXML
    public void agregarEnfermero() {
        String nombre = nombreField.getText();
        String especialidad = especialidadField.getText();

        if (nombre.isEmpty() || especialidad.isEmpty()) {
            mostrarMensaje("Error", "Debe completar todos los campos.");
            return;
        }

        try {
            Enfermero nuevoEnfermero = new Enfermero(0, nombre, especialidad, "Disponible");
            enfermeroFacade.guardarEnfermero(nuevoEnfermero);
            cargarEnfermerosDesdeBD(); // Refrescar la tabla después de agregar
            mostrarMensaje("Éxito", "Enfermero agregado correctamente.");
            limpiarCampos();
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo agregar el enfermero: " + e.getMessage());
        }
    }

    @FXML
    public void eliminarEnfermero() {
        Enfermero seleccionado = enfermerosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Advertencia", "Seleccione un enfermero para eliminar.");
            return;
        }

        try {
            enfermeroFacade.eliminarEnfermero(seleccionado.getId());
            cargarEnfermerosDesdeBD(); // Refrescar la tabla después de eliminar
            mostrarMensaje("Éxito", "Enfermero eliminado correctamente.");
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo eliminar el enfermero: " + e.getMessage());
        }
    }

    private void cargarEnfermerosDesdeBD() {
        try {
            enfermeros.clear();
            enfermeros.addAll(enfermeroFacade.listarEnfermeros());
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudieron cargar los enfermeros: " + e.getMessage());
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        nombreField.clear();
        especialidadField.clear();
    }
}
