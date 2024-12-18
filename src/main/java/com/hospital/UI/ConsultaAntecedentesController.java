package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.hospital.repositorios.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConsultaAntecedentesController extends BaseController{

    @FXML private TextField txtBuscarPaciente;
    @FXML private TableView<String> tablaAntecedentes;
    @FXML private TableColumn<String, String> colAntecedente;

    private ObservableList<String> listaAntecedentes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colAntecedente.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        // Simular datos iniciales
        listaAntecedentes.addAll("Alergia al polen", "Hipertensión", "Cirugía de rodilla");
        tablaAntecedentes.setItems(listaAntecedentes);
    }
    @FXML
    private void regresarAlDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }
    @FXML
    private void buscarAntecedentes() {
        String paciente = txtBuscarPaciente.getText().trim();
        if (!paciente.isEmpty()) {
            // Simular búsqueda en la base de datos
            System.out.println("Consultando antecedentes de: " + paciente);
            listaAntecedentes.clear();
            listaAntecedentes.addAll("Antecedente 1", "Antecedente 2", "Antecedente 3"); // Datos simulados
        } else {
            mostrarAlerta("Por favor, ingresa el nombre o cédula del paciente.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
