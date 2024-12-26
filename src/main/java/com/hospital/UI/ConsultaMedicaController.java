package com.hospital.UI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.modelos.Antecedente;
import com.hospital.modelos.Consulta;
import com.hospital.modelos.ConsultaMedica;
import com.hospital.modelos.Paciente;
import com.hospital.negocio.AntecedentesFacade;
import com.hospital.negocio.ConsultaMedicaFacade;

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
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    @FXML private Label lblResultados;

    private ObservableList<Consulta> listaConsultas = FXCollections.observableArrayList();
    private final ConsultaMedicaFacade facade = new ConsultaMedicaFacade();
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
public void initialize() {
    System.out.println("Inicializando ConsultaMedicaController...");
    System.out.println("consultasTable: " + consultasTable);
    System.out.println("colId: " + colId);
    System.out.println("colPaciente: " + colPaciente);

    configurarColumnas();
    cargarConsultas();
}


    private void configurarColumnas() {
        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colPaciente.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getPaciente().getNombre() + " " + data.getValue().getPaciente().getApellido()));
        colFechaIngreso.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getFecha().toString()));
            colEnfermedad.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getPaciente().getEnfermedades()));
        
        colDoctor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDoctor()));
        colDescripcion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));
    }

    @FXML
    private void cargarConsultas() {
        try {
            List<ConsultaMedica> consultas = facade.listarTodas();
            List<Consulta> filtro = new ArrayList<>();
            listaConsultas.clear();
            consultas.forEach(consultaMedica -> {
                Consulta consulta = new Consulta(
                    consultaMedica.getId(),
                    consultaMedica.getPaciente(),
                    consultaMedica.getDoctor(),
                    consultaMedica.getFecha(),
                    consultaMedica.getDescripcion()
                );
                listaConsultas.add(consulta);
            });
            System.out.println("Número de resultados después del filtro: " + filtro.size());
            consultasTable.setItems(listaConsultas);
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudieron cargar las consultas desde la base de datos.");
            e.printStackTrace();
        }
    }
    @FXML
    private void buscarPorEnfermedad() {
        String enfermedad = txtBuscarEnfermedad.getText().trim();
        System.out.println("Buscando por enfermedad: " + enfermedad);
        
        if (enfermedad.isEmpty()) {
            System.out.println("Error: No se ingresó una enfermedad.");
            mostrarMensaje("Error", "Por favor, ingrese una enfermedad para buscar.");
            return;
        }
    
        try {
            System.out.println("Llamando a facade.buscarPorEnfermedad con: " + enfermedad);
            List<ConsultaMedica> consultas = facade.buscarPorEnfermedad(enfermedad);
            System.out.println("Total consultas recuperadas desde el repositorio: " + consultas.size());
            
            ObservableList<Consulta> listaFiltrada = FXCollections.observableArrayList();
            consultas.forEach(consultaMedica -> {
                Consulta consulta = new Consulta(
                    consultaMedica.getId(),
                    consultaMedica.getPaciente(),
                    consultaMedica.getDoctor(),
                    consultaMedica.getFecha(),
                    consultaMedica.getDescripcion()
                );
                System.out.println("Agregando consulta recuperada: ID=" + consulta.getId());
                listaFiltrada.add(consulta);
            });
    
            consultasTable.setItems(listaFiltrada);
    
            if (consultas.isEmpty()) {
                System.out.println("Sin resultados: No se encontraron consultas con la enfermedad: " + enfermedad);
                mostrarMensaje("Sin resultados", "No se encontraron consultas con la enfermedad: " + enfermedad);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar por enfermedad.");
            e.printStackTrace();
            mostrarMensaje("Error", "Ocurrió un error al buscar por enfermedad.");
        }
    }
    // @FXML
    // private void mostrarResumenPaciente() {
    //     Consulta seleccionada = consultasTable.getSelectionModel().getSelectedItem();
    //     if (seleccionada != null) {
    //         try {
    //             FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/resumen_paciente.fxml"));
    //             Parent root = loader.load();
    //             ResumenPacienteController controller = loader.getController();
    //             controller.setPaciente(seleccionada.getPaciente());

    //             Stage stage = new Stage();
    //             stage.setTitle("Resumen del Paciente");
    //             stage.setScene(new Scene(root));
    //             stage.initModality(Modality.APPLICATION_MODAL);
    //             stage.showAndWait();
    //         } catch (Exception e) {
    //             mostrarMensaje("Error", "No se pudo cargar el resumen del paciente.");
    //             e.printStackTrace();
    //         }
    //     } else {
    //         mostrarMensaje("Advertencia", "Seleccione una consulta para ver el resumen.");
    //     }
    // }
    private final AntecedentesFacade antecedentesFacade = new AntecedentesFacade();

    
    @FXML
    private TableColumn<Paciente, Integer> colPacienteId;
    
    @FXML
    private TableColumn<Paciente, String> colPacienteNombre;
    
    @FXML
    private TableColumn<Paciente, String> colPacienteApellido;
    
    // @FXML
    // private TableView<Paciente> tablaPacientes;@FXML@FXML
@FXML
public void mostrarResumenPaciente() {
    Consulta consultaSeleccionada = consultasTable.getSelectionModel().getSelectedItem();
    if (consultaSeleccionada == null) {
        mostrarAlerta("Advertencia", "Seleccione una consulta para ver el resumen.");
        return;
    }

    Paciente pacienteSeleccionado = consultaSeleccionada.getPaciente();
    if (pacienteSeleccionado == null) {
        mostrarAlerta("Advertencia", "No se encontró un paciente asociado a la consulta seleccionada.");
        return;
    }

    // Obtener antecedentes
    List<Antecedente> antecedentes = antecedentesFacade.obtenerAntecedentes(pacienteSeleccionado.getId());
    System.out.println("Antecedentes obtenidos para el paciente " + pacienteSeleccionado.getNombre() + ": " + antecedentes);

    StringBuilder antecedentesTexto = new StringBuilder();
    for (Antecedente antecedente : antecedentes) {
        antecedentesTexto.append(antecedente.getTipo()).append(": ").append(antecedente.getDescripcion()).append("\n");
    }

    Alert resumen = new Alert(Alert.AlertType.INFORMATION);
    resumen.setTitle("Resumen del Paciente");
    resumen.setHeaderText("Resumen del Paciente");
    resumen.setContentText(
        "Nombre: " + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellido() + "\n" +
        "Edad: " + pacienteSeleccionado.getEdad() + "\n" +
        "Antecedentes:\n" + (antecedentesTexto.length() > 0 ? antecedentesTexto.toString() : "Sin antecedentes registrados.")
    );
    resumen.showAndWait();
}

    
private void mostrarAlerta(String titulo, String mensaje) {
    // Create an alert dialog
    Alert alerta = new Alert(Alert.AlertType.WARNING);
    alerta.setTitle(titulo); // Set the title
    alerta.setHeaderText(null); // No header
    alerta.setContentText(mensaje); // Set the content text
    alerta.showAndWait(); // Display the alert
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
            "C:\\ruta\\a\\export_to_pdf.py", // Cambia esta ruta al script Python real
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
    private void volverDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            mostrarMensaje("Error", "El controlador principal no está configurado.");
        }
    }
    

@FXML
private void filtrarConsultas() {
    String nombre = txtBuscarNombre.getText().trim();
    String enfermedad = txtBuscarEnfermedad.getText().trim();
    System.out.println("Texto ingresado para filtrar: Nombre='" + nombre + "', Enfermedad='" + enfermedad + "'");
    
    if (nombre.isEmpty() && enfermedad.isEmpty()) {
        System.out.println("Advertencia: No se ingresó ningún criterio de búsqueda.");
        mostrarMensaje("Advertencia", "Ingrese al menos un criterio de búsqueda.");
        return;
    }

    try {
        System.out.println("Obteniendo todas las consultas desde la fachada...");
        List<ConsultaMedica> consultas = facade.listarTodas(); // Obtener todas las consultas
        System.out.println("Total consultas recuperadas: " + consultas.size());
        
        List<ConsultaMedica> filtro = consultas.stream()
        .filter(c -> (nombre.isEmpty() || c.getPaciente().getNombre().toLowerCase().contains(nombre.toLowerCase())) &&
        (enfermedad.isEmpty() || c.getPaciente().getEnfermedades().toLowerCase().contains(enfermedad.toLowerCase())))

            .collect(Collectors.toList());

        System.out.println("Total consultas después del filtro: " + filtro.size());
        
        ObservableList<Consulta> listaFiltrada = FXCollections.observableArrayList();
        filtro.forEach(consultaMedica -> {
            Consulta consulta = new Consulta(
                consultaMedica.getId(),
                consultaMedica.getPaciente(),
                consultaMedica.getDoctor(),
                consultaMedica.getFecha(),
                consultaMedica.getDescripcion()
            );
            System.out.println("Agregando consulta filtrada: ID=" + consulta.getId());
            listaFiltrada.add(consulta);
        });

        consultasTable.setItems(listaFiltrada);
        lblResultados.setText(filtro.size() + " resultados encontrados");
        
        if (filtro.isEmpty()) {
            System.out.println("Sin resultados: No se encontraron consultas que coincidan con los criterios.");
            mostrarMensaje("Sin resultados", "No se encontraron consultas que coincidan con los criterios.");
        }
    } catch (Exception e) {
        System.out.println("Error al buscar consultas.");
        e.printStackTrace();
        mostrarMensaje("Error", "Ocurrió un error al buscar consultas.");
    }
}

}
