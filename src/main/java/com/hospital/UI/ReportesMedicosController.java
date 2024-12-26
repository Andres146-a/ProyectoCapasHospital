

// import com.hospital.repositorios.BaseController;
// public class ReportesMedicosController extends BaseController {

//     @FXML private Label lblTotalPacientes;
//     @FXML private Label lblConsultasMes;
//     @FXML
// private VBox alertasImportantes;
//     @FXML private TableView<Paciente> tablaAlertas;
//     @FXML private TableColumn<Paciente, String> colPaciente;
//     @FXML private TableColumn<Paciente, String> colFrecuenciaCardiaca;
//     @FXML private TableColumn<Paciente, String> colPresionArterial;
//     @FXML private TableColumn<Paciente, String> colTemperatura;
//     @FXML private TableColumn<Paciente, String> colAlerta;

//     @FXML private LineChart<String, Number> graficoEvolucion;

//     private final ObservableList<Paciente> listaAlertas = FXCollections.observableArrayList();
//     private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();

//     private final PacientesFacade pacientesFacade = new PacientesFacade();
//      // Lógica de negocio
//     private MainController mainController;
//     private int frecuenciaMin = 60; // Valor predeterminado
//     private int frecuenciaMax = 100; // Valor predeterminado
//     private int presionSistolicaMax = 140; // Valor predeterminado
//     private int presionDiastolicaMax = 90; // Valor predeterminado
//     private double temperaturaMax = 38.0; // Valor predeterminado

//     @FXML
//     public void initialize() {
//         configurarColumnasTabla();
//         cargarDatosIniciales();
//         verificarAlertas();
//         cargarGraficoEvolucion();
//         // cargarDatosSimulados();
//     }

//     private void configurarColumnasTabla() {
//         colPaciente.setCellValueFactory(data -> new SimpleStringProperty(
//             data.getValue().getNombre() + " " + data.getValue().getApellido()
//         ));
//         colFrecuenciaCardiaca.setCellValueFactory(data -> data.getValue().frecuenciaCardiacaProperty());
//         colPresionArterial.setCellValueFactory(data -> data.getValue().presionArterialProperty());
//         colTemperatura.setCellValueFactory(data -> data.getValue().temperaturaProperty());
//         colAlerta.setCellValueFactory(data -> data.getValue().alertaProperty());
//     }
   
//     private void cargarDatosIniciales() {
//         try {
//             listaPacientes.setAll(pacientesFacade.obtenerTodosLosPacientes());
//             lblTotalPacientes.setText(String.valueOf(listaPacientes.size()));
//             lblConsultasMes.setText(String.valueOf(calcularConsultasMensuales()));
//         } catch (Exception e) {
//             System.out.println("Error al cargar datos iniciales: " + e.getMessage());
//             lblTotalPacientes.setText("0");
//             lblConsultasMes.setText("0");
//             listaPacientes.clear();
//         }
//     }
    

//     private int calcularConsultasMensuales() {
       
//         return 120; 
//     }

//     @FXML
//     private void verificarAlertas() {
//         listaAlertas.clear();
//         for (Paciente p : listaPacientes) {
//             String alerta = generarAlerta(p);
//             if (!alerta.isEmpty()) {
//                 listaAlertas.add(new Paciente(
//                     p.getId(), p.getNombre(), p.getApellido(), p.getEdad(),
//                     p.getEnfermedades(), p.getFrecuenciaCardiaca(), p.getPresionArterial(),
//                     p.getTemperatura(), alerta
//                 ));
//             }
//         }
//         tablaAlertas.setItems(listaAlertas);
//         actualizarAlertasEnDashboard(listaAlertas);
//     }
    
//     private String generarAlerta(Paciente p) {
//         StringBuilder alerta = new StringBuilder();
    
//         // Validar presión arterial
//     String presionArterial = p.getPresionArterial();
//     if (presionArterial != null && presionArterial.matches("\\d+/\\d+")) {
//         try {
//             String[] presion = presionArterial.split("/");
//             int sistolica = Integer.parseInt(presion[0].trim());
//             int diastolica = Integer.parseInt(presion[1].trim());
//             if (sistolica > presionSistolicaMax || diastolica > presionDiastolicaMax) {
//                 alerta.append("Hipertensión ");
//             }
//         } catch (NumberFormatException e) {
//             System.out.println("Error al procesar presión arterial: " + presionArterial);
//         }
//     } else {
//         System.out.println("Presión arterial no válida para el paciente: " + p.getNombre());
//     }

    
//         // Validar frecuencia cardíaca
//         String frecuenciaCardiaca = p.getFrecuenciaCardiaca();
//         if (frecuenciaCardiaca != null && frecuenciaCardiaca.contains("lpm")) {
//             try {
//                 int frecuencia = Integer.parseInt(frecuenciaCardiaca.replace(" lpm", "").trim());
//                 if (frecuencia < 60) {
//                     alerta.append("Bradicardia ");
//                 } else if (frecuencia > 100) {
//                     alerta.append("Taquicardia ");
//                 }
//             } catch (NumberFormatException e) {
//                 System.out.println("Error al procesar frecuencia cardíaca: " + frecuenciaCardiaca);
//             }
//         }
    
//         // Validar temperatura
//         String temperatura = p.getTemperatura();
//         if (temperatura != null && temperatura.contains("°C")) {
//             try {
//                 double temp = Double.parseDouble(temperatura.replace("°C", "").trim());
//                 if (temp > 38) {
//                     alerta.append("Fiebre ");
//                 }
//             } catch (NumberFormatException e) {
//                 System.out.println("Error al procesar temperatura: " + temperatura);
//             }
//         }
    
//         // Retornar mensaje claro con el nombre del paciente
//     if (alerta.length() > 0) {
//         return p.getNombre() + " " + p.getApellido() + ": " + alerta.toString().trim();
//     }
//     return alerta.length() > 0 ? (p.getNombre() + " " + p.getApellido() + ": " + alerta.toString().trim()) : "";
//     }
    

//     private void actualizarAlertasEnDashboard(ObservableList<Paciente> alertas) {
//         if (mainController != null) {
//             mainController.actualizarAlertasImportantes(listaAlertas);
//         } else {
//             System.out.println("Error: MainController no está inicializado.");
//         }

//     }

//     @FXML
//     private void regresarAlDashboard() {
//         if (mainController != null) {
//             mainController.restaurarContenidoPrincipal();
//         } else {
//             mostrarMensaje("Error", "No se puede regresar al Dashboard.");
//         }
//     }

//     private void cargarGraficoEvolucion() {
//         XYChart.Series<String, Number> serie = new XYChart.Series<>();
//         serie.setName("Evolución de Frecuencia Cardíaca");
//         serie.getData().add(new XYChart.Data<>("Enero", 85));
//         serie.getData().add(new XYChart.Data<>("Febrero", 90));
//         serie.getData().add(new XYChart.Data<>("Marzo", 88));
//         serie.getData().add(new XYChart.Data<>("Abril", 92));

//         graficoEvolucion.getData().add(serie);
//     }
// public void actualizarAlertasImportantes(ObservableList<Paciente> alertas) {
//     alertasImportantes.getChildren().clear(); // Limpiar el VBox antes de actualizar

//     if (alertas.isEmpty()) {
//         Label sinAlertas = new Label("No hay alertas importantes.");
//         sinAlertas.setStyle("-fx-text-fill: green; -fx-font-size: 16px; -fx-font-weight: bold;");
//         alertasImportantes.getChildren().add(sinAlertas); // Mostrar mensaje de "sin alertas"
//         return;
//     }

//     for (Paciente paciente : FXCollections.observableArrayList(alertas)) { // Iterar sobre una copia para evitar problemas
//         HBox alertaBox = new HBox(10); // Contenedor horizontal para la alerta y el botón
//         alertaBox.setStyle("-fx-alignment: center-left;"); // Alineación opcional
//         alertaBox.setSpacing(10);

//         Label alertaLabel = new Label("⚠ " + paciente.getNombre() + " " + paciente.getApellido() + ": " + paciente.getAlerta());
//         alertaLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

//         Button btnEliminar = new Button("Eliminar");
//         btnEliminar.setStyle("-fx-background-color: #ff6666; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");
//         btnEliminar.setOnAction(event -> {
//             alertas.remove(paciente); // Eliminar de la lista
//             mostrarMensaje("Alerta Eliminada", "La alerta de " + paciente.getNombre() + " ha sido eliminada."); // Confirmación
//             actualizarAlertasImportantes(alertas); // Actualizar la vista
//         });

//         alertaBox.getChildren().addAll(alertaLabel, btnEliminar);
//         alertasImportantes.getChildren().add(alertaBox); // Agregar la alerta al VBox
//     }
// }

// private void mostrarMensaje(String titulo, String mensaje) {
//     Alert alert = new Alert(Alert.AlertType.INFORMATION);
//     alert.setTitle(titulo);
//     alert.setHeaderText(null);
//     alert.setContentText(mensaje);
//     alert.showAndWait();
// }


//     public void setMainController(MainController mainController) {
//         this.mainController = mainController;
//     }
//     @FXML
//     private void configurarAlertas() {
//         Dialog<Map<String, String>> dialog = new Dialog<>();
//         dialog.setTitle("Configurar Alertas");
//         dialog.setHeaderText("Ajusta los umbrales para generar alertas");
    
//         TextField txtFrecuenciaMin = new TextField(String.valueOf(frecuenciaMin));
//         TextField txtFrecuenciaMax = new TextField(String.valueOf(frecuenciaMax));
//         TextField txtPresionSistolica = new TextField(String.valueOf(presionSistolicaMax));
//         TextField txtPresionDiastolica = new TextField(String.valueOf(presionDiastolicaMax));
//         TextField txtTemperaturaMax = new TextField(String.valueOf(temperaturaMax));
    
//         GridPane grid = new GridPane();
//         grid.setHgap(10);
//         grid.setVgap(10);
//         grid.add(new Label("Frecuencia Cardíaca Mínima (lpm):"), 0, 0);
//         grid.add(txtFrecuenciaMin, 1, 0);
//         grid.add(new Label("Frecuencia Cardíaca Máxima (lpm):"), 0, 1);
//         grid.add(txtFrecuenciaMax, 1, 1);
//         grid.add(new Label("Presión Sistólica Máxima (mmHg):"), 0, 2);
//         grid.add(txtPresionSistolica, 1, 2);
//         grid.add(new Label("Presión Diastólica Máxima (mmHg):"), 0, 3);
//         grid.add(txtPresionDiastolica, 1, 3);
//         grid.add(new Label("Temperatura Máxima (°C):"), 0, 4);
//         grid.add(txtTemperaturaMax, 1, 4);
    
//         dialog.getDialogPane().setContent(grid);
//         ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
//         dialog.getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);
    
//         dialog.setResultConverter(dialogButton -> {
//             if (dialogButton == guardarButtonType) {
//                 Map<String, String> parametros = new HashMap<>();
//                 parametros.put("frecuenciaMin", txtFrecuenciaMin.getText());
//                 parametros.put("frecuenciaMax", txtFrecuenciaMax.getText());
//                 parametros.put("presionSistolica", txtPresionSistolica.getText());
//                 parametros.put("presionDiastolica", txtPresionDiastolica.getText());
//                 parametros.put("temperaturaMax", txtTemperaturaMax.getText());
//                 return parametros;
//             }
//             return null;
//         });
    
//         Optional<Map<String, String>> result = dialog.showAndWait();
//         result.ifPresent(parametros -> {
//             frecuenciaMin = Integer.parseInt(parametros.get("frecuenciaMin"));
//             frecuenciaMax = Integer.parseInt(parametros.get("frecuenciaMax"));
//             presionSistolicaMax = Integer.parseInt(parametros.get("presionSistolica"));
//             presionDiastolicaMax = Integer.parseInt(parametros.get("presionDiastolica"));
//             temperaturaMax = Double.parseDouble(parametros.get("temperaturaMax"));
    
//             mostrarMensaje("Configuración Guardada", "Los nuevos parámetros de alerta han sido guardados.");
//             verificarAlertas(); // Generar alertas basadas en los nuevos parámetros
//         });
//     }
    
// // private void cargarDatosSimulados() {
// //     listaPacientes.addAll(
// //         new Paciente(1, "Juan", "Perez", 30, "Diabetes", "72 lpm", "120/80", "36.5°C", ""),
// //         new Paciente(2, "Ana", "Lopez", 25, "Hipertensión", "102 lpm", "150/95", "37.2°C", ""),
// //         new Paciente(3, "Luis", "Martinez", 40, "Obesidad", "58 lpm", "110/70", "38.5°C", ""),
// //         new Paciente(4, "Maria", "Gomez", 28, "Ninguna", "85 lpm", "130/85", "36.8°C", "")
// //     );
// //     lblTotalPacientes.setText(String.valueOf(listaPacientes.size()));
// //     verificarAlertas(); // Generar las alertas iniciales
// // }
// @FXML
// private void generarAlertaPorPaciente() {
//     if (listaPacientes.isEmpty()) {
//         mostrarMensaje("Error", "No hay pacientes disponibles para generar alertas.");
//         return;
//     }

//     // Crear el diálogo para seleccionar un paciente
//     Dialog<Paciente> dialog = new Dialog<>();
//     dialog.setTitle("Generar Alerta por Paciente");
//     dialog.setHeaderText("Selecciona un paciente y ajusta los umbrales");

//     // Crear el selector de pacientes
//     ComboBox<Paciente> comboPacientes = new ComboBox<>(listaPacientes);
//     comboPacientes.setPromptText("Selecciona un paciente");
//     comboPacientes.setCellFactory(p -> new ListCell<>() {
//         @Override
//         protected void updateItem(Paciente paciente, boolean empty) {
//             super.updateItem(paciente, empty);
//             setText(empty || paciente == null ? null : paciente.getNombre() + " " + paciente.getApellido());
//         }
//     });
//     comboPacientes.setButtonCell(new ListCell<>() {
//         @Override
//         protected void updateItem(Paciente paciente, boolean empty) {
//             super.updateItem(paciente, empty);
//             setText(empty || paciente == null ? null : paciente.getNombre() + " " + paciente.getApellido());
//         }
//     });

//     // Crear los campos para configurar los umbrales
//     TextField txtFrecuenciaMin = new TextField("60");
//     TextField txtFrecuenciaMax = new TextField("100");
//     TextField txtPresionSistolica = new TextField("140");
//     TextField txtPresionDiastolica = new TextField("90");
//     TextField txtTemperaturaMax = new TextField("38");

//     // Organizar los campos en un diseño
//     GridPane grid = new GridPane();
//     grid.setHgap(10);
//     grid.setVgap(10);
//     grid.add(new Label("Seleccionar Paciente:"), 0, 0);
//     grid.add(comboPacientes, 1, 0);
//     grid.add(new Label("Frecuencia Cardíaca Mínima (lpm):"), 0, 1);
//     grid.add(txtFrecuenciaMin, 1, 1);
//     grid.add(new Label("Frecuencia Cardíaca Máxima (lpm):"), 0, 2);
//     grid.add(txtFrecuenciaMax, 1, 2);
//     grid.add(new Label("Presión Sistólica Máxima (mmHg):"), 0, 3);
//     grid.add(txtPresionSistolica, 1, 3);
//     grid.add(new Label("Presión Diastólica Máxima (mmHg):"), 0, 4);
//     grid.add(txtPresionDiastolica, 1, 4);
//     grid.add(new Label("Temperatura Máxima (°C):"), 0, 5);
//     grid.add(txtTemperaturaMax, 1, 5);

//     dialog.getDialogPane().setContent(grid);

//     // Botones del diálogo
//     ButtonType generarButtonType = new ButtonType("Generar Alerta", ButtonBar.ButtonData.OK_DONE);
//     dialog.getDialogPane().getButtonTypes().addAll(generarButtonType, ButtonType.CANCEL);

//     dialog.setResultConverter(dialogButton -> {
//         if (dialogButton == generarButtonType && comboPacientes.getValue() != null) {
//             Paciente pacienteSeleccionado = comboPacientes.getValue();
//             Map<String, String> parametros = new HashMap<>();
//             parametros.put("frecuenciaMin", txtFrecuenciaMin.getText());
//             parametros.put("frecuenciaMax", txtFrecuenciaMax.getText());
//             parametros.put("presionSistolica", txtPresionSistolica.getText());
//             parametros.put("presionDiastolica", txtPresionDiastolica.getText());
//             parametros.put("temperaturaMax", txtTemperaturaMax.getText());
//             pacienteSeleccionado.setParametrosAlertas(parametros); // Guardar los parámetros en el paciente
//             return pacienteSeleccionado;
//         }
//         return null;
//     });

//     // Manejar el resultado
//     Optional<Paciente> result = dialog.showAndWait();
//     result.ifPresent(paciente -> {
//         // Generar alerta para el paciente seleccionado
//         String alerta = generarAlertaPorParametros(paciente, paciente.getParametrosAlertas());
//         paciente.setAlerta(alerta);

//         // Mostrar la alerta generada
//         mostrarMensaje("Alerta Generada", "Paciente: " + paciente.getNombre() + " " + paciente.getApellido() + "\nAlerta: " + alerta);

//         // Actualizar alertas visuales
//         listaAlertas.add(paciente);
//         actualizarAlertasEnDashboard(listaAlertas);
//     });
// }
// private String generarAlertaPorParametros(Paciente paciente, Map<String, String> parametros) {
//     StringBuilder alerta = new StringBuilder();

//     // Validar presión arterial
//     String presionArterial = paciente.getPresionArterial();
//     if (presionArterial != null && presionArterial.contains("/")) {
//         try {
//             String[] presion = presionArterial.split("/");
//             int sistolica = Integer.parseInt(presion[0].trim());
//             int diastolica = Integer.parseInt(presion[1].trim());
//             if (sistolica > Integer.parseInt(parametros.get("presionSistolica"))
//                     || diastolica > Integer.parseInt(parametros.get("presionDiastolica"))) {
//                 alerta.append("Hipertensión ");
//             }
//         } catch (NumberFormatException e) {
//             System.out.println("Error al procesar presión arterial: " + presionArterial);
//         }
//     }

//     // Validar frecuencia cardíaca
//     String frecuenciaCardiaca = paciente.getFrecuenciaCardiaca();
//     if (frecuenciaCardiaca != null && frecuenciaCardiaca.contains("lpm")) {
//         try {
//             int frecuencia = Integer.parseInt(frecuenciaCardiaca.replace(" lpm", "").trim());
//             if (frecuencia < Integer.parseInt(parametros.get("frecuenciaMin"))) {
//                 alerta.append("Bradicardia ");
//             } else if (frecuencia > Integer.parseInt(parametros.get("frecuenciaMax"))) {
//                 alerta.append("Taquicardia ");
//             }
//         } catch (NumberFormatException e) {
//             System.out.println("Error al procesar frecuencia cardíaca: " + frecuenciaCardiaca);
//         }
//     }

//     // Validar temperatura
//     String temperatura = paciente.getTemperatura();
//     if (temperatura != null && temperatura.contains("°C")) {
//         try {
//             double temp = Double.parseDouble(temperatura.replace("°C", "").trim());
//             if (temp > Double.parseDouble(parametros.get("temperaturaMax"))) {
//                 alerta.append("Fiebre ");
//             }
//         } catch (NumberFormatException e) {
//             System.out.println("Error al procesar temperatura: " + temperatura);
//         }
//     }

//     return alerta.toString().trim();
// }

// }
// package com.hospital.UI;

// import java.util.HashMap;
// import java.util.Map;
// import java.util.Optional;

// import com.hospital.modelos.Paciente;
// import com.hospital.negocio.PacientesFacade;
// import javafx.beans.property.SimpleStringProperty;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.fxml.FXML;
// import javafx.scene.chart.LineChart;
// import javafx.scene.chart.XYChart;
// import javafx.scene.control.*;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.VBox;
package com.hospital.UI;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hospital.modelos.Paciente;
import com.hospital.negocio.PacientesFacade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReportesMedicosController {

    @FXML private Label lblTotalPacientes;
    @FXML private Label lblConsultasMes;
    @FXML private VBox alertasImportantes;
    @FXML private TableView<Paciente> tablaAlertas;
    @FXML private TableColumn<Paciente, String> colPaciente;
    @FXML private TableColumn<Paciente, String> colFrecuenciaCardiaca;
    @FXML private TableColumn<Paciente, String> colPresionArterial;
    @FXML private TableColumn<Paciente, String> colTemperatura;
    @FXML private TableColumn<Paciente, String> colAlerta;
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtApellidos;
    
    @FXML
    private DatePicker dpFechaNacimiento;
    
    @FXML
    private TextField txtEnfermedad;
    
    @FXML private LineChart<String, Number> graficoEvolucion;

    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    private final ObservableList<Paciente> listaAlertas = FXCollections.observableArrayList();
    private final PacientesFacade pacientesFacade = new PacientesFacade();

    @FXML
    public void initialize() {
        configurarColumnasTabla();
        cargarDatosIniciales();
        verificarAlertas();
        cargarGraficoEvolucion();
    }

    private void configurarColumnasTabla() {
        colPaciente.setCellValueFactory(data -> new SimpleStringProperty(
            data.getValue().getNombre() + " " + data.getValue().getApellido()
        ));
        colFrecuenciaCardiaca.setCellValueFactory(data -> data.getValue().frecuenciaCardiacaProperty());
        colPresionArterial.setCellValueFactory(data -> data.getValue().presionArterialProperty());
        colTemperatura.setCellValueFactory(data -> data.getValue().temperaturaProperty());
        colAlerta.setCellValueFactory(data -> data.getValue().alertaProperty());
    }

    private void cargarDatosIniciales() {
        try {
            listaPacientes.setAll(pacientesFacade.obtenerTodosLosPacientes());
            lblTotalPacientes.setText(String.valueOf(listaPacientes.size()));
            lblConsultasMes.setText(String.valueOf(calcularConsultasMensuales()));
        } catch (Exception e) {
            System.out.println("Error al cargar datos iniciales: " + e.getMessage());
            lblTotalPacientes.setText("0");
            lblConsultasMes.setText("0");
            listaPacientes.clear();
        }
    }

    private int calcularConsultasMensuales() {
        return 120; // Valor simulado
    }
    
    private void verificarAlertas() {
        listaAlertas.clear();
        Map<String, String> parametros = pacientesFacade.obtenerParametrosConfigurados();
    
        for (Paciente paciente : listaPacientes) {
            String alerta = pacientesFacade.generarAlerta(paciente, parametros);
            if (!alerta.isEmpty()) {
                paciente.setAlerta(alerta);
                listaAlertas.add(paciente);
            }
        }
    
        tablaAlertas.setItems(listaAlertas);
    
        // Actualizar alertas en el Dashboard principal
        if (mainController != null) {
            mainController.actualizarAlertasImportantes(listaAlertas);
        } else {
            System.out.println("No se pudo actualizar el Dashboard principal: mainController es null.");
        }
    }
    private MainController mainController;

    // public void setMainController(MainController mainController) {
    //     this.mainController = mainController;
    // }
    
  
//  public void regresarAlDashboard() {
//     try {
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/UI/dashboard.fxml"));
//         Parent root = loader.load();

//         // Obtén el Stage actual y reemplaza la escena
//         Stage stage = (Stage) tablaAlertas.getScene().getWindow();
//         stage.setScene(new Scene(root));
//         stage.show();
//     } catch (IOException e) {
//         System.err.println("Error al cargar el dashboard: " + e.getMessage());
//         e.printStackTrace();
//     }
// }


@FXML
private void regresarDashboard() {
    if (mainController != null) {
        mainController.restaurarContenidoPrincipal();
        System.out.println("Regresando al Dashboard desde ReportesMedicosController.");
    } else {
        mostrarMensaje("Error", "El controlador principal no está configurado.");
        System.err.println("Error: mainController es null en ReportesMedicosController.");
    }
}

private void mostrarAlertaExito(String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle("Éxito");
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}

private void mostrarAlertaError(String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setTitle("Error");
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}

private void limpiarCampos() {
    txtNombre.clear();
    txtApellidos.clear();
    dpFechaNacimiento.setValue(null);
    txtEnfermedad.clear();
}


    private void actualizarAlertasEnDashboard(ObservableList<Paciente> alertas) {
        if (mainController != null) {
            mainController.actualizarAlertasImportantes(alertas);
        } else {
            System.out.println("No se pudo actualizar el Dashboard principal: mainController es null.");
        }
    }
    private boolean validarCampos() {
        return !txtNombre.getText().trim().isEmpty() &&
               !txtApellidos.getText().trim().isEmpty() &&
               dpFechaNacimiento.getValue() != null &&
               !txtEnfermedad.getText().trim().isEmpty();
    }
    private int calcularEdad(int anioNacimiento) {
        // Obtén el año actual
        int anioActual = java.time.LocalDate.now().getYear();
    
        // Calcula la edad
        return anioActual - anioNacimiento;
    }
    
    private void cargarGraficoEvolucion() {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Evolución de Frecuencia Cardíaca");
        serie.getData().add(new XYChart.Data<>("Enero", 85));
        serie.getData().add(new XYChart.Data<>("Febrero", 90));
        serie.getData().add(new XYChart.Data<>("Marzo", 88));
        serie.getData().add(new XYChart.Data<>("Abril", 92));

        graficoEvolucion.getData().add(serie);
    }
    @FXML
    private void guardarPaciente() {
        if (validarCampos()) { // Validar los campos obligatorios del paciente
            try {
                // Crear el objeto Paciente con los datos del formulario
                Paciente nuevoPaciente = new Paciente(
                    0, // ID inicial generado automáticamente por la base de datos
                    txtNombre.getText().trim(),
                    txtApellidos.getText().trim(),
                    calcularEdad(dpFechaNacimiento.getValue().getYear()), // Calcula la edad
                    txtEnfermedad.getText().trim(),
                    "", // Frecuencia cardíaca inicial vacía
                    "", // Presión arterial inicial vacía
                    "", // Temperatura inicial vacía
                    "", // Alerta inicial vacía
                    "", // Antecedentes iniciales vacíos
                    true // Estado activo por defecto
                );
    
                // Guardar paciente en la base de datos
                int idPaciente = pacientesFacade.guardarPaciente(nuevoPaciente);
    
                if (idPaciente > 0) { // Si se genera un ID válido
                    mostrarAlertaExito("Paciente guardado correctamente con ID: " + idPaciente);
                } else {
                    mostrarAlertaError("Error: No se pudo generar el ID del paciente.");
                }
    
                limpiarCampos(); // Limpia los campos tras guardar
            } catch (IllegalArgumentException e) {
                mostrarAlertaError("Validación fallida: " + e.getMessage());
            } catch (Exception e) {
                mostrarAlertaError("Error al guardar el paciente: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarAlertaError("Por favor, complete todos los campos obligatorios.");
        }
    }
    

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
public void actualizarTablaAlertas() {
    List<Paciente> pacientes = pacientesFacade.listarPacientesEnRiesgo();
    ObservableList<Paciente> pacientesEnRiesgo = FXCollections.observableArrayList(pacientes);

    tablaAlertas.setItems(pacientesEnRiesgo);
}
private List<Paciente> getPacientesEnRiesgo() {
    return tablaAlertas.getItems().stream().collect(Collectors.toList());
}

@FXML
private VBox alertasImportantesVBox;


@FXML
private void filtrarPacientesPorNombre() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Filtrar Pacientes");
    dialog.setHeaderText("Búsqueda por Nombre");
    dialog.setContentText("Ingrese el nombre del paciente:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(nombre -> {
        ObservableList<Paciente> pacientesFiltrados = listaPacientes.filtered(paciente ->
            paciente.getNombre().toLowerCase().contains(nombre.toLowerCase())
        );

        if (pacientesFiltrados.isEmpty()) {
            mostrarMensaje("Sin Resultados", "No se encontraron pacientes con el nombre: " + nombre);
        } else {
            tablaAlertas.setItems(pacientesFiltrados);
        }
    });
}

public void setMainController(MainController mainController) {
    this.mainController = mainController;
    System.out.println("MainController configurado correctamente en ReportesMedicosController.");
}
@FXML
private void mostrarTodosLosPacientes() {
    tablaAlertas.setItems(listaPacientes); // Vuelve a mostrar la lista completa
}
@FXML
private void asignarAlerta() {
    // Obtener el paciente seleccionado en la tabla
    Paciente pacienteSeleccionado = tablaAlertas.getSelectionModel().getSelectedItem();

    if (pacienteSeleccionado == null) {
        mostrarAlertaError("Por favor, seleccione un paciente para asignar una alerta.");
        return;
    }

    // Crear un diálogo para ingresar la alerta
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Asignar Alerta");
    dialog.setHeaderText("Asignar alerta a: " + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellido());
    dialog.setContentText("Ingrese la alerta:");

    // Mostrar el diálogo y esperar el resultado
    Optional<String> result = dialog.showAndWait();

    if (result.isPresent() && !result.get().trim().isEmpty()) {
        // Actualizar la alerta en el modelo del paciente
        String nuevaAlerta = result.get().trim();
        pacienteSeleccionado.setAlerta(nuevaAlerta);

        // Guardar la alerta en la base de datos
        try {
            pacientesFacade.actualizarAlerta(pacienteSeleccionado.getId(), nuevaAlerta);
            mostrarAlertaExito("Alerta asignada correctamente al paciente.");
            actualizarTablaAlertas(); // Refresca la tabla para reflejar el cambio
        } catch (Exception e) {
            mostrarAlertaError("Error al asignar la alerta: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        mostrarAlertaError("La alerta no puede estar vacía.");
    }
}

}
