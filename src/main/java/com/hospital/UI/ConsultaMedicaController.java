package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.time.LocalDate;

import com.hospital.modelos.Paciente;

public class ConsultaMedicaController {

    public static class Consulta {
        private final javafx.beans.property.IntegerProperty id;
        private final Paciente paciente; // Usar Paciente como atributo
        private final javafx.beans.property.StringProperty doctor;
        private final javafx.beans.property.StringProperty fecha;
        private final javafx.beans.property.StringProperty descripcion;
    
        public Consulta(int id, Paciente paciente, String doctor, String fecha, String descripcion) {
            this.id = new javafx.beans.property.SimpleIntegerProperty(id);
            this.paciente = paciente;
            this.doctor = new javafx.beans.property.SimpleStringProperty(doctor);
            this.fecha = new javafx.beans.property.SimpleStringProperty(fecha);
            this.descripcion = new javafx.beans.property.SimpleStringProperty(descripcion);
        }
    
        public int getId() { return id.get(); }
        public Paciente getPaciente() { return paciente; }
        public String getDoctor() { return doctor.get(); }
        public String getFecha() { return fecha.get(); }
        public String getDescripcion() { return descripcion.get(); }
    
        public javafx.beans.property.IntegerProperty idProperty() { return id; }
        public javafx.beans.property.StringProperty doctorProperty() { return doctor; }
        public javafx.beans.property.StringProperty fechaProperty() { return fecha; }
        public javafx.beans.property.StringProperty descripcionProperty() { return descripcion; }
    }
    
    @FXML private TableView<Consulta> consultasTable;
    @FXML private TableColumn<Consulta, Integer> idColumn;
    @FXML private TableColumn<Consulta, String> pacienteColumn;
    @FXML private TableColumn<Consulta, String> doctorColumn;
    @FXML private TableColumn<Consulta, String> fechaColumn;
    @FXML private TableColumn<Consulta, String> descripcionColumn;

    private MainController mainController; // Referencia al MainController

    private final ObservableList<Consulta> consultas = FXCollections.observableArrayList();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        // Crear pacientes de ejemplo
        Paciente paciente1 = new Paciente(1, "Juan", "Pérez", 30, "Hipertensión", "85 lpm", "150/95", "37.5°C", "Alerta: Hipertensión");
        Paciente paciente2 = new Paciente(2, "María", "López", 25, "Gastritis", "95 lpm", "130/85", "38.2°C", "Alerta: Fiebre");
        Paciente paciente3 =new Paciente(3, "Carlos", "González", 40, "Hipertensión", "50 lpm", "120/80", "36.8°C", "Alerta: Bradicardia");
    
        // Configurar las columnas de la tabla
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        pacienteColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getPaciente().getNombre() + " " + data.getValue().getPaciente().getApellido()
        ));
        doctorColumn.setCellValueFactory(data -> data.getValue().doctorProperty());
        fechaColumn.setCellValueFactory(data -> data.getValue().fechaProperty());
        descripcionColumn.setCellValueFactory(data -> data.getValue().descripcionProperty());
    
        // Agregar datos de ejemplo
        consultas.addAll(
            new Consulta(1, paciente1, "Dr. López", LocalDate.now().toString(), "Revisión general"),
            new Consulta(2, paciente2, "Dr. Sánchez", LocalDate.now().toString(), "Consulta de seguimiento"),
            new Consulta(3, paciente3, "Dra. Martínez", "2024-06-28", "Control de presión arterial")
        );
    
        // Asignar datos a la tabla
        consultasTable.setItems(consultas);
    
        // Configurar evento doble clic
        consultasTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                mostrarResumenPaciente();
            }
        });
    }
    
    @FXML
    private void mostrarResumenPaciente() {
        Consulta consultaSeleccionada = consultasTable.getSelectionModel().getSelectedItem();
        if (consultaSeleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/resumen_paciente.fxml"));
                Parent root = loader.load();
    
                ResumenPacienteController controller = loader.getController();
    
                // Pasar directamente el paciente seleccionado
                Paciente paciente = consultaSeleccionada.getPaciente();
                controller.setPaciente(paciente);
    
                Stage stage = new Stage();
                stage.setTitle("Resumen del Paciente");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal
                stage.show();
    
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarMensaje("Error", "Debe seleccionar una consulta para ver el resumen del paciente.");
        }
    }
    
    

private void mostrarMensaje(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setContentText(mensaje);
    alert.showAndWait();
}


    @FXML
    private void regresarDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal(); // Llama al método del MainController
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }
    
    // Alias: "volverDashboard" llama a regresarDashboard para mantener consistencia
    @FXML
    public void volverDashboard() {
        regresarDashboard();
    }
    


    // Clase interna para representar una consulta
    
}
