package com.hospital.UI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.io.IOException;

import com.hospital.modelos.Paciente;
import com.hospital.repositorios.BaseController;

public class MainController {

  @FXML
private StackPane contenidoPrincipal;

private Node vistaPrincipal;

@FXML
public void initialize() {
    if (contenidoPrincipal != null && !contenidoPrincipal.getChildren().isEmpty()) {
        vistaPrincipal = contenidoPrincipal.getChildren().get(0); // Guarda la vista inicial
    } else {
        System.out.println("Error: contenidoPrincipal está vacío o null.");
    }
}



@FXML
public void onBotonClic(ActionEvent event) {
    if (contenidoPrincipal.getChildren().isEmpty()) {
        System.out.println("Error: contenidoPrincipal no tiene hijos.");
    } else {
        VBox vbox = (VBox) contenidoPrincipal.getChildren().get(0);
        if (vbox == null) {
            System.out.println("Error: vbox es null.");
        } else {
            vistaPrincipal = vbox;
            // Ahora puedes acceder a los hijos del contenidoPrincipal
            System.out.println("vistaPrincipal: " + vistaPrincipal);
        }
    }
}
@FXML
public void volverDashboard() {
    if (contenidoPrincipal != null) {
        // restaurarContenidoPrincipal();
        contenidoPrincipal.getChildren().setAll(vistaPrincipal);
    } else {
        System.out.println("Error: contenidoPrincipal es null.");
    }
}
public StackPane getContenidoPrincipal() {
    return contenidoPrincipal;
}
@FXML
public void mostrarAperturaFichaMedica() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/apertura_ficha.fxml"));
        Parent vista = loader.load();

        // Configura el controlador
        AperturaFichaController controller = loader.getController();
        controller.setMainController(this); // Pasar el MainController

        // Cargar la vista en el StackPane
        contenidoPrincipal.getChildren().setAll(vista);
    } catch (IOException e) {
        mostrarError("Error al cargar la vista de Apertura de Ficha Médica.");
        e.printStackTrace();
    }
}


@FXML
public void mostrarTomaSignos() {
    if (contenidoPrincipal != null) {
        cargarVista("/com/hospital/ui/toma_signos.fxml");
    } else {
        System.out.println("Error: contenidoPrincipal es null.");
    }
}
// @FXML
// public void mostrarHistorialPacientes() {
//     if (contenidoPrincipal != null) {
//         cargarVista("/com/hospital/ui/historial_pacientes.fxml");
//     } else {
//         System.out.println("Error: contenidoPrincipal es null.");
//     }
// }
@FXML
public void mostrarEnfermerosDisponibles() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/enfermeros_disponibles.fxml"));
        Parent vista = loader.load();

        // Configurar el controlador
        EnfermerosDisponiblesController controller = loader.getController();
        controller.setMainController(this);

        // Cargar la vista en el contenido principal
        contenidoPrincipal.getChildren().setAll(vista);
    } catch (IOException e) {
        mostrarError("No se pudo cargar la vista de Enfermeros Disponibles.");
        e.printStackTrace();
    }
}

@FXML
public void mostrarPacientesActivos() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/pacientes_activos.fxml"));
        Parent vista = loader.load();

        // Configura el controlador
        PacientesActivosController controller = loader.getController();
        controller.setMainController(this); // Configura la referencia al MainController

        // Cargar la vista en el contenido principal
        contenidoPrincipal.getChildren().setAll(vista);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public void eliminarAlertaManual(Paciente paciente) {
    alertasImportantes.getChildren().removeIf(node -> {
        if (node instanceof Label) {
            Label label = (Label) node;
            return label.getText().contains(paciente.getNombre() + " " + paciente.getApellido());
        }
        return false;
    });
    System.out.println("Alerta manual eliminada: " + paciente.getNombre() + " " + paciente.getApellido());
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

// private void mostrarMensaje(String titulo, String mensaje) {
//     Alert alert = new Alert(Alert.AlertType.ERROR);
//     alert.setTitle(titulo);
//     alert.setContentText(mensaje);
//     alert.showAndWait();
// }


@FXML
public void mostrarReportesMedicos() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/reportes_medicos.fxml"));
        Parent vista = loader.load();

        // Obtener el controlador de ReportesMedicosController
        ReportesMedicosController controller = loader.getController();

        // Pasar la referencia del MainController al ReportesMedicosController
        controller.setMainController(this);

        // Cargar la vista en el contenido principal
        contenidoPrincipal.getChildren().setAll(vista);
    } catch (IOException e) {
        mostrarError("Error al cargar la vista de Reportes Médicos.");
        e.printStackTrace();
    }
}


@FXML
public void agregarTomaSignos() {
    System.out.println("Método agregarTomaSignos ejecutado");
    // Implementa la lógica que necesites aquí
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


@FXML
public void mostrarConsultaMedica() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/consulta_medica.fxml"));
        Parent vista = loader.load();

        // Configurar el controlador
        ConsultaMedicaController controller = loader.getController();
        controller.setMainController(this);

        contenidoPrincipal.getChildren().setAll(vista); // Cargar la vista en el StackPane
    } catch (IOException e) {
        mostrarError("Error al cargar la vista de Consulta Médica.");
        e.printStackTrace();
    }
}
@FXML private VBox alertasImportantes;

/**
 * Actualiza la sección de Alertas Importantes en el MainController.
 */
public void agregarAlerta(ObservableList<Paciente> alertas) {
    alertasImportantes.getChildren().clear(); // Limpiar alertas anteriores
    for (Paciente p : alertas) {
        Label alertaLabel = new Label("⚠ Alerta: " + p.getAlerta() + " - " + p.getNombre());
        alertaLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        alertasImportantes.getChildren().add(alertaLabel);
    }
}

public void actualizarAlertasImportantes(ObservableList<Paciente> alertas) {
    alertasImportantes.getChildren().clear(); // Limpiar alertas anteriores

    for (Paciente p : alertas) {
        HBox alertaBox = new HBox(10); // Contenedor horizontal para la alerta y el botón
        Label alertaLabel = new Label("⚠ " + p.getNombre() + " " + p.getApellido() + ": " + p.getAlerta());
        alertaLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        // Botón para eliminar la alerta
        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setOnAction(event -> {
            System.out.println("Alerta manual eliminada: " + p.getNombre() + " " + p.getApellido());
            alertas.remove(p); // Eliminar de la lista
            actualizarAlertasImportantes(alertas); // Actualizar la vista
        });

        // Añadir etiqueta y botón al contenedor horizontal
        alertaBox.getChildren().addAll(alertaLabel, btnEliminar);

        // Añadir el contenedor al VBox principal
        alertasImportantes.getChildren().add(alertaBox);
    }
}

public void pacienteAtendido(Paciente paciente) {
    eliminarAlertaAutomatica(paciente);
    System.out.println("Paciente atendido: " + paciente.getNombre() + " " + paciente.getApellido());
}


private void cargarVista(String rutaFXML) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));

        // Configura el controlador existente para evitar múltiples instancias del MainController
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return this; // Reutiliza la instancia actual del MainController
            } else {
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Parent vista = loader.load();

        // Configurar el controlador de la vista cargada
        Object controlador = loader.getController();
        if (controlador instanceof BaseController) {
            ((BaseController) controlador).setMainController(this);
        }

        // Cargar la vista en el StackPane del MainController
        contenidoPrincipal.getChildren().setAll(vista);

    } catch (IOException e) {
        e.printStackTrace();
        mostrarError("Error al cargar la vista: " + rutaFXML);
    }
}



public void restaurarContenidoPrincipal() {
    if (vistaPrincipal != null) {
        contenidoPrincipal.getChildren().setAll(vistaPrincipal);
    } else {
        System.out.println("Error: vistaPrincipal es null.");
    }
}


    
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


}
