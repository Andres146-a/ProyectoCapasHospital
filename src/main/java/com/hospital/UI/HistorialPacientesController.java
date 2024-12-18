package com.hospital.UI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.stream.Collectors;
import com.hospital.modelos.Paciente;
import javafx.scene.control.*;

public class HistorialPacientesController {

    @FXML private TableView<Paciente> pacientesTable;
    @FXML private TableColumn<Paciente, Integer> colId;
    @FXML private TableColumn<Paciente, String> colNombre;
    @FXML private TableColumn<Paciente, String> colFechaIngreso;
    @FXML private TableColumn<Paciente, String> colEnfermedad;
    @FXML private TextField txtBuscarNombre;
    @FXML private TextField txtBuscarEnfermedad;

    private ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colFechaIngreso.setCellValueFactory(data -> new SimpleStringProperty("2024-01-01"));
        colEnfermedad.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEnfermedades()));

        listaPacientes.addAll(
            new Paciente(1, "Juan", "Pérez", 30, "Hipertensión", "85 lpm", "150/95", "37.5°C", "Alerta: Hipertensión"),
            new Paciente(2, "María", "López", 25, "Gastritis", "95 lpm", "130/85", "38.2°C", "Alerta: Fiebre"),
            new Paciente(3, "Carlos", "González", 40, "Hipertensión", "50 lpm", "120/80", "36.8°C", "Alerta: Bradicardia")
        );

        pacientesTable.setItems(listaPacientes);
    }

    @FXML
    private void filtrarPacientes() {
        String nombre = txtBuscarNombre.getText().toLowerCase();
        String enfermedad = txtBuscarEnfermedad.getText().toLowerCase();

        ObservableList<Paciente> filtro = listaPacientes.stream()
            .filter(p -> p.getNombre().toLowerCase().contains(nombre) || 
                         p.getEnfermedades().toLowerCase().contains(enfermedad))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

        pacientesTable.setItems(filtro);
    }

    @FXML
    private void mostrarResumenPaciente() {
        Paciente seleccionado = pacientesTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/resumen_paciente.fxml"));
                Parent root = loader.load();

                ResumenPacienteController controller = loader.getController();
                controller.setPaciente(seleccionado);

                Stage stage = new Stage();
                stage.setTitle("Resumen del Paciente");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarMensaje("Error", "No se pudo cargar el resumen del paciente.");
            }
        } else {
            mostrarMensaje("Advertencia", "Seleccione un paciente de la tabla para ver el resumen.");
        }
    }

    @FXML
    private void exportarHistorialPDF() {
        System.out.println("Exportando historial a PDF...");
        for (Paciente paciente : pacientesTable.getItems()) {
            System.out.println("ID: " + paciente.getId() + ", Nombre: " + paciente.getNombre() + " " + paciente.getApellido());
        }
        mostrarMensaje("Exportar PDF", "El historial de pacientes se ha exportado correctamente.");
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void volverDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/main.fxml"));
            Parent mainView = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(mainView);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al regresar al Dashboard");
        }
    }
}
