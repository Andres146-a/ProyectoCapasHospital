package com.hospital.UI;

import com.hospital.modelos.Enfermero;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EnfermerosController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField especialidadField;

    @FXML
    private TableView<Enfermero> enfermerosTable;

    @FXML
    private TableColumn<Enfermero, Integer> idColumn;

    @FXML
    private TableColumn<Enfermero, String> nombreColumn;

    @FXML
    private TableColumn<Enfermero, String> especialidadColumn;

    private final ObservableList<Enfermero> enfermeros = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar las columnas (enlazando propiedades con el modelo)
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        nombreColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        especialidadColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEspecialidad()));

        // Asignar la lista a la tabla
        enfermerosTable.setItems(enfermeros);

        // Cargar datos ficticios para probar la interfaz
        cargarDatosPrueba();
    }

    @FXML
    public void buscarEnfermero() {
        String nombre = nombreField.getText();
        if (nombre.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un nombre para buscar.");
            return;
        }
        System.out.println("Buscando enfermero con nombre: " + nombre);
        // Simular búsqueda
    }

    @FXML
    public void agregarEnfermero() {
        String nombre = nombreField.getText();
        String especialidad = especialidadField.getText();

        if (nombre.isEmpty() || especialidad.isEmpty()) {
            mostrarMensaje("Error", "Debe completar todos los campos.");
            return;
        }

        Enfermero nuevoEnfermero = new Enfermero(enfermeros.size() + 1, nombre, especialidad);
        enfermeros.add(nuevoEnfermero);
        mostrarMensaje("Éxito", "Enfermero agregado correctamente.");
        limpiarCampos();
    }

    @FXML
    public void actualizarEnfermero() {
        Enfermero seleccionado = enfermerosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Error", "Debe seleccionar un enfermero para actualizar.");
            return;
        }

        String nuevoNombre = nombreField.getText();
        String nuevaEspecialidad = especialidadField.getText();

        if (nuevoNombre.isEmpty() || nuevaEspecialidad.isEmpty()) {
            mostrarMensaje("Error", "Debe completar todos los campos.");
            return;
        }

        seleccionado.setNombre(nuevoNombre);
        seleccionado.setEspecialidad(nuevaEspecialidad);

        enfermerosTable.refresh();
        mostrarMensaje("Éxito", "Enfermero actualizado correctamente.");
        limpiarCampos();
    }

    @FXML
    public void eliminarEnfermero() {
        Enfermero seleccionado = enfermerosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("Error", "Debe seleccionar un enfermero para eliminar.");
            return;
        }

        enfermeros.remove(seleccionado);
        mostrarMensaje("Éxito", "Enfermero eliminado correctamente.");
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarDatosPrueba() {
        enfermeros.add(new Enfermero(1, "Juan Pérez", "Pediatría"));
        enfermeros.add(new Enfermero(2, "María Gómez", "Urgencias"));
        enfermeros.add(new Enfermero(3, "Luis Martínez", "Cardiología"));
    }

    private void limpiarCampos() {
        nombreField.clear();
        especialidadField.clear();
    }
}
