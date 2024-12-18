package com.hospital.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.hospital.modelos.Paciente;


public class ReportesMedicosController {

    @FXML private Label lblTotalPacientes;
    @FXML private Label lblConsultasMes;

    @FXML private TableView<Paciente> tablaAlertas;
    @FXML private TableColumn<Paciente, String> colPaciente;
    @FXML private TableColumn<Paciente, String> colFrecuenciaCardiaca;
    @FXML private TableColumn<Paciente, String> colPresionArterial;
    @FXML private TableColumn<Paciente, String> colTemperatura;
    @FXML private TableColumn<Paciente, String> colAlerta;

    @FXML private LineChart<String, Number> graficoEvolucion;

    private ObservableList<Paciente> listaAlertas = FXCollections.observableArrayList();
    private ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();

    private MainController mainController; // Referencia a MainController
   
    /**
     * Método para verificar los signos vitales y generar una alerta si es necesario.
     */
    private String verificarSignosVitales(Paciente p) {
        StringBuilder alerta = new StringBuilder();
    
        // Verificar presión arterial
        String[] presion = p.getPresionArterial().split("/");
        if (Integer.parseInt(presion[0]) > 140 || Integer.parseInt(presion[1]) > 90) {
            alerta.append("Hipertensión ");
        }
    
        // Verificar frecuencia cardíaca
        int frecuencia = Integer.parseInt(p.getFrecuenciaCardiaca().replace(" lpm", ""));
        if (frecuencia < 60) {
            alerta.append("Bradicardia ");
        } else if (frecuencia > 100) {
            alerta.append("Taquicardia ");
        }
    
        // Verificar temperatura
        double temperatura = Double.parseDouble(p.getTemperatura().replace("°C", ""));
        if (temperatura > 38) {
            alerta.append("Fiebre ");
        }
    
        return alerta.toString().trim(); // Devuelve la alerta acumulada
    }
    
    /**
     * Simula la obtención de una lista de pacientes.
     */
    private ObservableList<Paciente> obtenerListaPacientes() {
        // Simulamos una lista de pacientes (normalmente vendría de la base de datos)
        return FXCollections.observableArrayList(
            new Paciente(1, "Juan", "Pérez", 30, "Hipertensión", "85 lpm", "150/95", "37.5°C", ""),
            new Paciente(2, "María", "López", 25, "Gastritis", "95 lpm", "130/85", "38.2°C", ""),
            new Paciente(3, "Carlos", "González", 40, "Hipertensión", "50 lpm", "120/80", "36.8°C", "")
        );
    }
    
    /**
     * Simula la obtención del MainController.
     */
    private MainController obtenerMainController() {
        // Aquí deberías obtener la referencia del MainController (puedes usar inyección de dependencia si es necesario)
        return null;
    }
    
    
    @FXML
    public void initialize() {
        lblTotalPacientes.setText("50");
        lblConsultasMes.setText("120");

        colPaciente.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getNombre() + " " + data.getValue().getApellido()
        ));
        colFrecuenciaCardiaca.setCellValueFactory(data -> data.getValue().frecuenciaCardiacaProperty());
        colPresionArterial.setCellValueFactory(data -> data.getValue().presionArterialProperty());
        colTemperatura.setCellValueFactory(data -> data.getValue().temperaturaProperty());
        colAlerta.setCellValueFactory(data -> data.getValue().alertaProperty());

        // Datos simulados
        listaPacientes.addAll(
            new Paciente(1, "Juan", "Pérez", 30, "Hipertensión", "85 lpm", "150/95", "37.5°C", "Alerta: Hipertensión"),
            new Paciente(2, "María", "López", 25, "Gastritis", "95 lpm", "130/85", "38.2°C", "Alerta: Fiebre"),
            new Paciente(3, "Carlos", "González", 40, "Hipertensión", "50 lpm", "120/80", "36.8°C", "Alerta: Bradicardia")
        );

        verificarAlertas();
        tablaAlertas.setItems(listaAlertas);

        cargarGraficoEvolucion();
    }
    @FXML
    private void configurarAlertas() {
        verificarAlertas();
    }
    
    private void verificarAlertas() {
        listaAlertas.clear(); // Limpiar alertas previas
    
        for (Paciente p : listaPacientes) {
            String alerta = "";
            if (Integer.parseInt(p.getPresionArterial().split("/")[0]) > 140) {
                alerta = "Hipertensión";
            } else if (Integer.parseInt(p.getFrecuenciaCardiaca().replace(" lpm", "")) < 60) {
                alerta = "Bradicardia";
            } else if (Integer.parseInt(p.getFrecuenciaCardiaca().replace(" lpm", "")) > 100) {
                alerta = "Taquicardia";
            } else if (Double.parseDouble(p.getTemperatura().replace("°C", "")) > 38) {
                alerta = "Fiebre";
            }
    
            if (!alerta.isEmpty()) {
                listaAlertas.add(new Paciente(
                    p.getId(), p.getNombre(), p.getApellido(), p.getEdad(), p.getEnfermedades(),
                    p.getFrecuenciaCardiaca(), p.getPresionArterial(), p.getTemperatura(), alerta
                ));
            }
        }
    
        // Actualizar la tabla de alertas
        tablaAlertas.setItems(listaAlertas);
    
        if (mainController != null) {
            mainController.actualizarAlertasImportantes(listaAlertas);
        } else {
            System.out.println("Error: MainController no está inicializado.");
        }
        
    }
    
    

    @FXML
    private void volverDashboard(ActionEvent event) {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            System.out.println("Error: MainController no está inicializado.");
        }
    }
    
    
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    

    private void cargarGraficoEvolucion() {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Evolución de Frecuencia Cardíaca");

        serie.getData().add(new XYChart.Data<>("01-01", 85));
        serie.getData().add(new XYChart.Data<>("02-01", 90));
        serie.getData().add(new XYChart.Data<>("03-01", 88));
        serie.getData().add(new XYChart.Data<>("04-01", 95));

        graficoEvolucion.getData().add(serie);
    }

    public void setMainController(MainController mainController) {
        this.mainController = App.getMainController(); // Obtiene la instancia única de MainController
        if (this.mainController != null) {
            System.out.println("MainController asignado correctamente.");
        } else {
            System.out.println("Error: MainController sigue siendo null.");
        }
    }
    
    

    // private void mostrarMensaje(String titulo, String mensaje) {
    //     Alert alert = new Alert(Alert.AlertType.INFORMATION);
    //     alert.setTitle(titulo);
    //     alert.setContentText(mensaje);
    //     alert.showAndWait();
    // }
}
