package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import com.hospital.repositorios.BaseController;
import com.hospital.negocio.PacientesFacade;
import com.hospital.modelos.Paciente;

import java.io.IOException;
import java.util.List;

public class AperturaFichaController {
   @FXML
    private VBox contenidoPrincipal; // Agregar la anotación @FXML
    @FXML
    private TextField campoEnfermedad;
    private MainController mainController; // Referencia al MainController
    // private StackPane contenidoPrincipal;
    private Node vistaPrincipal;

    // Instancia de la fachada de pacientes
    private final PacientesFacade pacientesFacade = new PacientesFacade();

    @FXML
    public void initialize() {
        if (contenidoPrincipal != null && !contenidoPrincipal.getChildren().isEmpty()) {
            vistaPrincipal = contenidoPrincipal.getChildren().get(0); // Guarda la vista inicial
            System.out.println("contenidoPrincipal inicializado con éxito.");
        } else {
            System.out.println("Error: contenidoPrincipal está vacío o es null.");
        }
    }
    
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void restaurarContenidoPrincipal() {
        if (contenidoPrincipal != null && vistaPrincipal != null) {
            contenidoPrincipal.getChildren().setAll(vistaPrincipal);
        } else {
            System.out.println("Error: No se pudo restaurar el contenido principal porque contenidoPrincipal o vistaPrincipal es null.");
        }
    }

    @FXML
    private void gestionarCategorias() {
        System.out.println("Abriendo Gestión de Categorías de Antecedentes...");
        cargarVista("/com/hospital/ui/gestion_categorias.fxml");
    }

    @FXML
    private void ingresarPacientes() {
        System.out.println("Abriendo Ingreso de Pacientes...");
        cargarVista("/com/hospital/ui/ingreso_pacientes.fxml");
    }

    @FXML
    private void consultarAntecedentes() {
        System.out.println("Abriendo Consulta de Antecedentes...");
        cargarVista("/com/hospital/ui/consulta_antecedentes.fxml");
    }

    @FXML
    private void listarPacientes() {
        System.out.println("Listando todos los pacientes...");
        try {
            List<Paciente> pacientes = pacientesFacade.obtenerTodosLosPacientes();
            pacientes.forEach(p -> System.out.println("Paciente: " + p.getNombre() + " " + p.getApellido()));
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudieron listar los pacientes: " + e.getMessage());
        }
    }

  
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    @FXML
    private void guardarPaciente() {
        // Validar que la enfermedad no esté vacía
        if (campoEnfermedad.getText() == null || campoEnfermedad.getText().trim().isEmpty()) {
            mostrarError("Error al guardar el paciente", "El paciente debe tener al menos una enfermedad registrada.");
            return;
        }
    
        // Obtener el valor de la enfermedad
        String enfermedad = campoEnfermedad.getText().trim();
    
        // Guardar los datos del paciente (asegúrate de incluir la lógica necesaria)
        System.out.println("Enfermedad registrada: " + enfermedad);
        // Más lógica para guardar en la base de datos
    }
    

    @FXML
    private void regresarDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }

    private void cargarVista(String rutaFXML) {
        if (mainController != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
                Parent vista = loader.load();

                // Configurar el controlador si es una subclase de BaseController
                BaseController controlador = loader.getController();
                controlador.setMainController(mainController);

                // Cargar la vista en el StackPane del MainController
                StackPane contenidoPrincipal = mainController.getContenidoPrincipal();
                contenidoPrincipal.getChildren().setAll(vista);
            } catch (IOException e) {
                e.printStackTrace();
                mostrarMensaje("Error", "No se pudo cargar la vista: " + rutaFXML);
            }
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // public void setContenidoPrincipal(StackPane contenidoPrincipal) {
    //     this.contenidoPrincipal = contenidoPrincipal;
    // }
    
}
