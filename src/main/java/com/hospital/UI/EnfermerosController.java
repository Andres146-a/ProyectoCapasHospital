package com.hospital.UI;

import com.hospital.modelos.Enfermero;
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

    @FXML
    public void initialize() {
        // Configurar las columnas para enlazar las propiedades
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        especialidadColumn.setCellValueFactory(data -> data.getValue().especialidadProperty());
        estadoColumn.setCellValueFactory(data -> data.getValue().estadoProperty());

        // Asignar la lista a la tabla
        enfermerosTable.setItems(enfermeros);

        // Cargar datos de ejemplo
        cargarDatosPrueba();
    }

    @FXML
    public void agregarEnfermero() {
        String nombre = nombreField.getText();
        String especialidad = especialidadField.getText();

        if (nombre.isEmpty() || especialidad.isEmpty()) {
            mostrarMensaje("Error", "Debe completar todos los campos.");
            return;
        }

        Enfermero nuevoEnfermero = new Enfermero(enfermeros.size() + 1, nombre, especialidad, "Disponible");
        enfermeros.add(nuevoEnfermero);
        mostrarMensaje("Éxito", "Enfermero agregado correctamente.");
        limpiarCampos();
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

    private void cargarDatosPrueba() {
        enfermeros.add(new Enfermero(1, "Ana Torres", "Pediatría", "Disponible"));
        enfermeros.add(new Enfermero(2, "Luis Ramírez", "Urgencias", "Ocupado"));
        enfermeros.add(new Enfermero(3, "María Gómez", "Cardiología", "Disponible"));
    }
}
