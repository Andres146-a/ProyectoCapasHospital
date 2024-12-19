package com.hospital.UI;
import javafx.event.ActionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.modelos.Consulta;
import com.hospital.modelos.Paciente;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ConsultaMedicaController {

    @FXML private TableView<Consulta> consultasTable;
    @FXML private TableColumn<Consulta, Integer> colId;
    @FXML private TableColumn<Consulta, String> colPaciente;
    @FXML private TableColumn<Consulta, String> colFechaIngreso;
    @FXML private TableColumn<Consulta, String> colEnfermedad;
    @FXML private TableColumn<Consulta, String> colDoctor;
    @FXML private TableColumn<Consulta, String> colDescripcion;
    @FXML private TextField txtBuscarNombre;
    @FXML private TextField txtBuscarEnfermedad;

    private ObservableList<Consulta> listaConsultas = FXCollections.observableArrayList();
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarDatosEjemplo();
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colPaciente.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getPaciente().getNombre() + " " + data.getValue().getPaciente().getApellido()));
        colFechaIngreso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha().toString()));
        colEnfermedad.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPaciente().getEnfermedades()));
        colDoctor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDoctor()));
        colDescripcion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));
    }

    private void cargarDatosEjemplo() {
        Paciente paciente1 = new Paciente(1, "Juan", "Pérez", 30, "Hipertensión", "85 lpm", "150/95", "37.5°C", "Alerta: Hipertensión");
        Paciente paciente2 = new Paciente(2, "María", "López", 25, "Gastritis", "95 lpm", "130/85", "38.2°C", "Alerta: Fiebre");
        Paciente paciente3 = new Paciente(3, "Carlos", "González", 40, "Hipertensión", "50 lpm", "120/80", "36.8°C", "Alerta: Bradicardia");

        listaConsultas.addAll(
            new Consulta(1, paciente1, "Dr. López", LocalDate.now(), "Consulta general"),
            new Consulta(2, paciente2, "Dra. Sánchez", LocalDate.now(), "Seguimiento"),
            new Consulta(3, paciente3, "Dr. López", LocalDate.now(), "Revisión general")
        );

        consultasTable.setItems(listaConsultas);
    }

    @FXML
    private void filtrarConsultas() {
        String nombre = txtBuscarNombre.getText().toLowerCase();
        String enfermedad = txtBuscarEnfermedad.getText().toLowerCase();

        ObservableList<Consulta> filtro = listaConsultas.stream()
            .filter(c -> c.getPaciente().getNombre().toLowerCase().contains(nombre) ||
                         c.getPaciente().getEnfermedades().toLowerCase().contains(enfermedad))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

        consultasTable.setItems(filtro);
    }

    @FXML
    private void mostrarResumenPaciente() {
        Consulta seleccionada = consultasTable.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/resumen_paciente.fxml"));
                Parent root = loader.load();

                ResumenPacienteController controller = loader.getController();
                controller.setPaciente(seleccionada.getPaciente());

                Stage stage = new Stage();
                stage.setTitle("Resumen del Paciente");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                mostrarMensaje("Error", "No se pudo cargar el resumen del paciente.");
                e.printStackTrace();
            }
        } else {
            mostrarMensaje("Advertencia", "Seleccione una consulta para ver el resumen.");
        }
    }

    @FXML
    private void exportarHistorialPDF() {
        try {
            List<Map<String, String>> data = listaConsultas.stream()
                .map(c -> {
                    Map<String, String> item = new HashMap<>();
                    item.put("ID", String.valueOf(c.getId()));
                    item.put("Paciente", c.getPaciente().getNombre() + " " + c.getPaciente().getApellido());
                    item.put("FechaIngreso", c.getFecha().toString());
                    item.put("Enfermedad", c.getPaciente().getEnfermedades());
                    item.put("Doctor", c.getDoctor());
                    item.put("Descripcion", c.getDescripcion());
                    return item;
                }).collect(Collectors.toList());

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(data);

            ProcessBuilder pb = new ProcessBuilder("python", "ruta_a_tu_script/export_to_pdf.py", jsonData);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            if (process.waitFor() == 0) {
                mostrarMensaje("Exportar PDF", "El historial se ha exportado correctamente.");
            } else {
                mostrarMensaje("Error", "No se pudo exportar el historial.");
            }
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error al exportar el historial.");
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void volverDashboard(ActionEvent event) {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            mostrarMensaje("Error", "MainController no está configurado.");
        }
    }
}
