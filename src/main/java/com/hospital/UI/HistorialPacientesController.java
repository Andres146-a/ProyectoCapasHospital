package com.hospital.UI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.modelos.Consulta;
import com.hospital.modelos.Paciente;
import com.hospital.negocio.PacientesFacade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HistorialPacientesController {

    @FXML private TableView<Paciente> pacientesTable;
    @FXML private TableColumn<Paciente, Integer> colId;
    @FXML private TableColumn<Paciente, String> colNombre;
    @FXML private TableColumn<Paciente, String> colFechaIngreso;
    @FXML private TableColumn<Paciente, String> colEnfermedad;
    @FXML private TextField txtBuscarNombre;
    @FXML private TextField txtBuscarEnfermedad;

    private ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    private ObservableList<Consulta> listaConsultas = FXCollections.observableArrayList();
    // Instancia de la fachada de pacientes
    private final PacientesFacade pacientesFacade = new PacientesFacade();

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarPacientesDesdeBaseDeDatos();
    }
    private void configurarColumnas() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre() + " " + data.getValue().getApellido()));
    
        // Si no existe el método `getFechaIngreso`, puedes asignar un valor predeterminado o manejarlo dinámicamente.
        colFechaIngreso.setCellValueFactory(data -> {
            String fechaIngreso = "Fecha no disponible"; // Valor predeterminado
            // Si tienes una lógica alternativa para calcular o establecer la fecha de ingreso, implementala aquí.
            return new SimpleStringProperty(fechaIngreso);
        });
    
        colEnfermedad.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEnfermedades()));
    }
    

    private void cargarPacientesDesdeBaseDeDatos() {
        try {
            List<Paciente> pacientes = pacientesFacade.obtenerTodosLosPacientes();
            listaPacientes.setAll(pacientes);
            pacientesTable.setItems(listaPacientes);
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudieron cargar los pacientes: " + e.getMessage());
        }
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
    try {
        List<Map<String, String>> data = listaConsultas.stream()
            .map(c -> {
                Map<String, String> item = new HashMap<>();
                item.put("id", String.valueOf(c.getId()));
                item.put("paciente", c.getPaciente().getNombre() + " " + c.getPaciente().getApellido());
                item.put("fecha_ingreso", c.getFecha().toString());
                item.put("enfermedad", c.getPaciente().getEnfermedades());
                item.put("doctor", c.getDoctor());
                item.put("descripcion", c.getDescripcion());
                return item;
            }).collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(data);

        ProcessBuilder pb = new ProcessBuilder(
            "python",
            "C:\\Users\\Matias\\OneDrive\\UNIVERSIDAD\\4SEMESTRE\\Ingenieria del Software\\ProyectoCapasHospital\\hospital\\src\\main\\java\\com\\hospital\\UI",
            jsonData
        );
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
