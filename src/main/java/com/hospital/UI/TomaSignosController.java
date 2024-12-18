package com.hospital.UI;

import java.io.IOException;

import com.hospital.modelos.SignoVital;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TomaSignosController {

    @FXML private ComboBox<String> pacienteComboBox;

    @FXML private TextField frecuenciaCardiacaField;
    @FXML private TextField presionArterialField;
    @FXML private TextField temperaturaField;
    @FXML private TextField frecuenciaRespiratoriaField;
    @FXML private TextField oxigenacionField;
    @FXML private TextField fechaHoraField;

    @FXML private TableView<SignoVital> tomaSignosTable;
    @FXML private TableColumn<SignoVital, Number> idColumn;
    @FXML private TableColumn<SignoVital, String> frecuenciaCardiacaColumn;
    @FXML private TableColumn<SignoVital, String> presionArterialColumn;
    @FXML private TableColumn<SignoVital, String> temperaturaColumn;
    @FXML private TableColumn<SignoVital, String> frecuenciaRespiratoriaColumn;
    @FXML private TableColumn<SignoVital, String> oxigenacionColumn;
    @FXML private TableColumn<SignoVital, String> fechaHoraColumn;

    private ObservableList<SignoVital> listaSignosVitales = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configura las columnas
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        frecuenciaCardiacaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuenciaCardiaca"));
        presionArterialColumn.setCellValueFactory(new PropertyValueFactory<>("presionArterial"));
        temperaturaColumn.setCellValueFactory(new PropertyValueFactory<>("temperatura"));
        frecuenciaRespiratoriaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuenciaRespiratoria"));
        oxigenacionColumn.setCellValueFactory(new PropertyValueFactory<>("oxigenacion"));
        fechaHoraColumn.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));

        // Poblar ComboBox con nombres de pacientes simulados
        pacienteComboBox.getItems().addAll("Juan Pérez", "María López", "Carlos González");

        // Configurar acción al seleccionar un paciente
        pacienteComboBox.setOnAction(event -> {
            String nombreSeleccionado = pacienteComboBox.getValue();
            cargarSignosVitales(nombreSeleccionado);
        });

        // Inicializar con datos vacíos
        tomaSignosTable.setItems(listaSignosVitales);
    }

    private void cargarSignosVitales(String nombrePaciente) {
        listaSignosVitales.clear();

        // Simula datos diferentes según el paciente seleccionado
        if ("Juan Pérez".equals(nombrePaciente)) {
            listaSignosVitales.add(new SignoVital(1, "80 lpm", "120/80 mmHg", "37 °C", "18 rpm", "98%", "2024-06-01 10:00"));
        } else if ("María López".equals(nombrePaciente)) {
            listaSignosVitales.add(new SignoVital(2, "75 lpm", "110/70 mmHg", "36.5 °C", "19 rpm", "99%", "2024-06-02 11:00"));
        } else if ("Carlos González".equals(nombrePaciente)) {
            listaSignosVitales.add(new SignoVital(3, "85 lpm", "130/85 mmHg", "38 °C", "20 rpm", "97%", "2024-06-03 12:00"));
        }

        tomaSignosTable.setItems(listaSignosVitales);
    }

    @FXML
    public void agregarTomaSignos() {
        System.out.println("Agregar nuevos signos vitales.");
    }

    @FXML
    public void actualizarTomaSignos() {
        System.out.println("Actualizar signos vitales seleccionados.");
    }

    @FXML
    public void eliminarTomaSignos() {
        System.out.println("Eliminar signos vitales seleccionados.");
    }

    @FXML
public void volverDashboard(ActionEvent event) {
    try {
        // Cargar la vista principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/main.fxml"));
        Parent mainView = loader.load();

        // Obtener la Stage actual a partir del evento
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(mainView);
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error al regresar al Dashboard");
    }
}
}
