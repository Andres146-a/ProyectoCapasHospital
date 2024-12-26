package com.hospital.UI;

import com.hospital.modelos.Paciente;
import com.hospital.negocio.PacientesFacade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.hospital.repositorios.BaseController;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class MainController {

    // @FXML private StackPane contenidoPrincipal;
//     @FXML
// private StackPane contenidoPrincipal;

    @FXML
private VBox alertasVBox;

    private Node vistaPrincipal;

    // Instancia de la fachada de pacientes
    private final PacientesFacade pacientesFacade = new PacientesFacade();

    @FXML
    public void initialize() {
        if (contenidoPrincipal != null && !contenidoPrincipal.getChildren().isEmpty()) {
            vistaPrincipal = contenidoPrincipal.getChildren().get(0);
            System.out.println("Controlador MainController inicializado correctamente.");
        } else {
            System.out.println("Error: contenidoPrincipal está vacío o es null.");
        }
    }
    @FXML private VBox alertasImportantes;
    public void agregarAlerta(String mensaje) {
        if (alertasImportantes == null) {
            System.out.println("Error: alertasImportantes no está inicializado.");
            return;
        }
        Label nuevaAlerta = new Label(mensaje);
        nuevaAlerta.setStyle("-fx-border-color: red; -fx-padding: 5; -fx-background-color: #f8d7da;");
        alertasImportantes.getChildren().add(nuevaAlerta);
        System.out.println("Alerta agregada: " + mensaje);
    }
    
    
    // public StackPane getContenidoPrincipal() {
    //     return contenidoPrincipal;
    // }

    @FXML
    public void regresarDashboard() {
        if (vistaPrincipal != null) {
            contenidoPrincipal.getChildren().setAll(vistaPrincipal);
            System.out.println("Regresando al Dashboard desde MainController.");
        } else {
            mostrarError("No se puede regresar al Dashboard porque la vista principal es null.");
        }
    }
    
    @FXML
    private StackPane contenidoPrincipal;

    public StackPane getContenidoPrincipal() {
        return contenidoPrincipal;
    }

    public void restaurarContenidoPrincipal() {
        if (vistaPrincipal != null) {
            contenidoPrincipal.getChildren().setAll(vistaPrincipal);
        } else {
            System.out.println("Error: vistaPrincipal es null.");
        }
    }
    @FXML
    public void mostrarTomaSignos() {
        cargarVista("/com/hospital/ui/toma_signos.fxml", TomaSignosController.class);
    }
    
    @FXML
    public void mostrarAperturaFichaMedica() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/apertura_ficha.fxml"));
            Parent vista = loader.load();
    
            // Configurar el controlador
            AperturaFichaController controlador = loader.getController();
            controlador.setMainController(this); // Pasar referencia al MainController
    
            // Cargar la vista en el contenido principal
            contenidoPrincipal.getChildren().setAll(vista);
        } catch (IOException e) {
            mostrarError("Error al cargar la vista de Apertura de Ficha Médica: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
  
  
    @FXML
public void mostrarEnfermerosDisponibles() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/enfermeros_disponibles.fxml"));
        Parent vista = loader.load();

        // Obtener el controlador de la vista cargada
        EnfermerosDisponiblesController controlador = loader.getController();
        controlador.setMainController(this); // Pasar el MainController

        // Cargar la vista en el contenido principal
        contenidoPrincipal.getChildren().setAll(vista);
    } catch (IOException e) {
        mostrarError("Error al cargar la vista de Enfermeros Disponibles: " + e.getMessage());
        e.printStackTrace();
    }
}

@FXML
public void mostrarConsultaMedica() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/consulta_medica.fxml"));
        Parent vista = loader.load();

        // Configurar el controlador de la vista cargada
        ConsultaMedicaController controller = loader.getController();
        controller.setMainController(this); // Enlazar al MainController

        // Cargar la vista en el contenido principal
        contenidoPrincipal.getChildren().setAll(vista);

    } catch (IOException e) {
        mostrarError("Error al cargar la vista de Consultas Médicas.");
        e.printStackTrace();
    }
}

    @FXML
    public void mostrarConsultasHoy() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/consulta_medica.fxml"));
            Parent vista = loader.load();
    
            // Configurar el controlador
            ConsultaMedicaController controller = loader.getController();
            controller.setMainController(this);
    
            // Cargar la vista de Consultas Médicas
            contenidoPrincipal.getChildren().setAll(vista);
        } catch (IOException e) {
            mostrarError("Error al cargar la vista de Consultas Médicas.");
            e.printStackTrace();
        }
    }
    // private Pane rootPane;
    @FXML 
    private BorderPane rootPane;
    public void mostrarReportesMedicos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/UI/reportes_medicos.fxml"));
            Parent root = loader.load();
    
            // Obtén el controlador de la vista cargada
            ReportesMedicosController reportesController = loader.getController();
            reportesController.setMainController(this); // Pasa la instancia de MainController
    
            // Carga la vista en el contenido principal
            contenidoPrincipal.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Error al cargar reportes_medicos.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    

public void actualizarAlertasEnMain(List<Paciente> alertas) {
    alertasImportantes.getChildren().clear();
    if (alertas.isEmpty()) {
        Label noAlertas = new Label("No hay alertas importantes.");
        noAlertas.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        alertasImportantes.getChildren().add(noAlertas);
        return;
    }

    for (Paciente paciente : alertas) {
        HBox alertaBox = new HBox(10);
        alertaBox.setStyle("-fx-alignment: center-left;");

        Label alertaLabel = new Label("\u26A0 " + paciente.getNombre() + " " + paciente.getApellido() + ": " + paciente.getAlerta());
        alertaLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        alertaBox.getChildren().add(alertaLabel);
        alertasImportantes.getChildren().add(alertaBox);
    }
}

// Método para recibir las alertas desde ReportesMedicosController
public void recibirAlertasDesdeReportes(List<Paciente> alertas) {
    actualizarAlertasEnMain(alertas);
}
public void actualizarAlertas(ObservableList<String> alertas) {
    alertasImportantes.getChildren().clear();

    if (alertas.isEmpty()) {
        Label sinAlertas = new Label("No hay alertas importantes.");
        sinAlertas.setStyle("-fx-text-fill: green; -fx-font-size: 16px; -fx-font-weight: bold;");
        alertasImportantes.getChildren().add(sinAlertas);
        return;
    }

    for (String alerta : alertas) {
        Label alertaLabel = new Label("⚠️ " + alerta);
        alertaLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        alertasImportantes.getChildren().add(alertaLabel);
    }
}


    
    private <T> void cargarVista(String rutaFXML, Class<T> controladorClase) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent vista = loader.load();

            // Configurar el controlador
            Object controlador = loader.getController();
            if (controlador instanceof BaseController) {
                ((BaseController) controlador).setMainController(this);
            }

            // Cargar la vista en el contenido principal
            contenidoPrincipal.getChildren().setAll(vista);
        } catch (IOException e) {
            mostrarError("Error al cargar la vista: " + rutaFXML);
            e.printStackTrace();
        }
    }
    
//     @FXML
//     private void asignarAlerta() {
//     if (listaPacientes.isEmpty()) {
//         mostrarMensaje("Error", "No hay pacientes disponibles para asignar alertas.");
//         return;
//     }

//     Dialog<Paciente> dialog = new Dialog<>();
//     dialog.setTitle("Asignar Alerta a Paciente");
//     dialog.setHeaderText("Selecciona un paciente para asignarle una alerta específica.");

//     // Crear un ComboBox para seleccionar un paciente
//     ComboBox<Paciente> comboPacientes = new ComboBox<>(listaPacientes);
//     comboPacientes.setPromptText("Selecciona un paciente");
//     comboPacientes.setCellFactory(p -> new ListCell<>() {
//         @Override
//         protected void updateItem(Paciente paciente, boolean empty) {
//             super.updateItem(paciente, empty);
//             setText(empty || paciente == null ? null : paciente.getNombre() + " " + paciente.getApellido());
//         }
//     });

//     // Campo de texto para ingresar la alerta
//     TextField txtAlerta = new TextField();
//     txtAlerta.setPromptText("Ingresa la alerta");

//     // Contenedor para el ComboBox y el campo de texto
//     VBox content = new VBox(10, new Label("Paciente:"), comboPacientes, new Label("Alerta:"), txtAlerta);
//     dialog.getDialogPane().setContent(content);

//     ButtonType asignarButtonType = new ButtonType("Asignar", ButtonBar.ButtonData.OK_DONE);
//     dialog.getDialogPane().getButtonTypes().addAll(asignarButtonType, ButtonType.CANCEL);

//     dialog.setResultConverter(dialogButton -> {
//         if (dialogButton == asignarButtonType && comboPacientes.getValue() != null && !txtAlerta.getText().isEmpty()) {
//             Paciente paciente = comboPacientes.getValue();
//             paciente.setAlerta(txtAlerta.getText());
//             return paciente;
//         }
//         return null;
//     });

//     Optional<Paciente> result = dialog.showAndWait();
//     result.ifPresent(paciente -> {
//         listaAlertas.add(paciente);
//         tablaAlertas.setItems(listaAlertas);
//         mostrarMensaje("Éxito", "Alerta asignada correctamente al paciente " + paciente.getNombre());
//         if (mainController != null) {
//             mainController.actualizarAlertasImportantes(listaAlertas); // Actualiza las alertas en el Dashboard principal
//         }
//     });
// }

// private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
// private final ObservableList<Paciente> listaAlertas = FXCollections.observableArrayList();

@FXML
private TableView<Paciente> tablaAlertas;
@FXML

private MainController mainController;
@FXML
private VBox alertasImportantesVBox;
public void actualizarAlertasImportantes(ObservableList<Paciente> pacientes) {
    if (alertasImportantesVBox == null) {
        System.err.println("alertasImportantesVBox no está inicializado.");
        return;
    }

    alertasImportantesVBox.getChildren().clear();
    for (Paciente paciente : pacientes) {
        Label alertaLabel = new Label(
            String.format("Paciente: %s %s - Alerta: %s", 
                          paciente.getNombre(), 
                          paciente.getApellido(), 
                          paciente.getAlerta())
        );
        alertaLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        alertasImportantesVBox.getChildren().add(alertaLabel);
    }
}



public void mostrarVistaPrincipal() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/UI/main.fxml"));
        Parent vistaPrincipal = loader.load();

        // Reemplazar todo el contenido del StackPane con la nueva vista
        contenidoPrincipal.getChildren().setAll(vistaPrincipal);
    } catch (IOException e) {
        System.err.println("Error al cargar la vista principal: " + e.getMessage());
    }
}


@FXML
public void mostrarPacientesInactivos() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/pacientes_inactivos.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Pacientes Inactivos");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void mostrarMensaje(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}

    public void agregarTomaSignos() {
        // Ejemplo de integración con la lógica de negocio
        System.out.println("Método agregarTomaSignos ejecutado");
    }

    public void pacienteAtendido(Paciente paciente) {
        eliminarAlertaAutomatica(paciente);
        System.out.println("Paciente atendido: " + paciente.getNombre() + " " + paciente.getApellido());
    }

    public void eliminarAlertaAutomatica(Paciente paciente) {
        alertasImportantes.getChildren().removeIf(node -> {
            if (node instanceof Label) {
                Label label = (Label) node;
                return label.getText().contains(paciente.getNombre() + " " + paciente.getApellido());
            }
            return false;
        });
        System.out.println("Alerta automática eliminada: " + paciente.getNombre() + " " + paciente.getApellido());
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void mostrarPacientesActivos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/pacientes_activos.fxml"));
            Parent vista = loader.load();
    
            // Configura el controlador de la vista
            PacientesActivosController controlador = loader.getController();
            controlador.setMainController(this); // Pasa la referencia al MainController
    
            // Carga la vista en el contenido principal
            contenidoPrincipal.getChildren().setAll(vista);
        } catch (IOException e) {
            mostrarError("Error al cargar la vista de Pacientes Activos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void cambiarContenido(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node node = loader.load();
            contenidoPrincipal.getChildren().clear();
            contenidoPrincipal.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el contenido: " + e.getMessage());
        }
    }
    
    @FXML
    private void abrirRegistroAntecedentes() {
        cambiarContenido("registro_antecedentes.fxml");
    }
    
   
}
