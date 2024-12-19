package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.hospital.repositorios.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// import com.hospital.repositorios.BaseController;
public class GestionCategoriasController extends  BaseController{

    @FXML private TableView<String> tablaCategorias;
    @FXML private TableColumn<String, String> colCategoria;
    @FXML private TextField txtNuevaCategoria;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;

    private ObservableList<String> listaCategorias = FXCollections.observableArrayList();
    private MainController mainController; // Referencia al MainController

    @FXML
    public void initialize() {
        colCategoria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        listaCategorias.addAll("Personales", "Patológicos", "Quirúrgicos", "Gineco-Obstétricos");
        tablaCategorias.setItems(listaCategorias);
    }
    @FXML
    private void agregarCategoria() {
        String nuevaCategoria = txtNuevaCategoria.getText().trim();
        if (!nuevaCategoria.isEmpty() && !listaCategorias.contains(nuevaCategoria)) {
            listaCategorias.add(nuevaCategoria);
            txtNuevaCategoria.clear();
        } else {
            mostrarAlerta("Categoría inválida o ya existente.");
        }
    }

    @FXML
    private void eliminarCategoria() {
        String seleccion = tablaCategorias.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            listaCategorias.remove(seleccion);
        } else {
            mostrarAlerta("Selecciona una categoría para eliminar.");
        }
    }

    @FXML
    private void regresarAlDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal(); // Volver al Dashboard principal
        } else {
            mostrarAlerta("Error: No se puede regresar al Dashboard porque el controlador principal no está configurado.");
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
