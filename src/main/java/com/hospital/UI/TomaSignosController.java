// package com.hospital.UI;

// import com.hospital.modelos.TomaSignos;
// import com.hospital.negocio.TomaSignosFacade;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Node;
// import javafx.scene.Parent;
// import javafx.scene.control.*;
// import javafx.scene.control.cell.PropertyValueFactory;
// import javafx.stage.Stage;

// import java.io.IOException;
// import java.util.List;
// import java.util.stream.Collectors;

// public class TomaSignosController {

//     @FXML private ComboBox<String> pacienteComboBox;

//     @FXML private TextField frecuenciaCardiacaField;
//     @FXML private TextField presionArterialField;
//     @FXML private TextField temperaturaField;
//     @FXML private TextField frecuenciaRespiratoriaField;
//     @FXML private TextField oxigenacionField;
//     @FXML private TextField fechaHoraField;

//     @FXML private TableView<TomaSignos> tomaSignosTable;
//     @FXML private TableColumn<TomaSignos, Number> idColumn;
//     @FXML private TableColumn<TomaSignos, String> frecuenciaCardiacaColumn;
//     @FXML private TableColumn<TomaSignos, String> presionArterialColumn;
//     @FXML private TableColumn<TomaSignos, String> temperaturaColumn;
//     @FXML private TableColumn<TomaSignos, String> frecuenciaRespiratoriaColumn;
//     @FXML private TableColumn<TomaSignos, String> oxigenacionColumn;
//     @FXML private TableColumn<TomaSignos, String> fechaHoraColumn;

//     private final ObservableList<TomaSignos> listaSignosVitales = FXCollections.observableArrayList();
//     private final TomaSignosFacade tomaSignosFacade = new TomaSignosFacade();

//     @FXML
//     public void initialize() {
//         // Configurar las columnas de la tabla
//         idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//         frecuenciaCardiacaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuenciaCardiaca"));
//         presionArterialColumn.setCellValueFactory(new PropertyValueFactory<>("presionArterial"));
//         temperaturaColumn.setCellValueFactory(new PropertyValueFactory<>("temperatura"));
//         frecuenciaRespiratoriaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuenciaRespiratoria"));
//         oxigenacionColumn.setCellValueFactory(new PropertyValueFactory<>("oxigenacion"));
//         fechaHoraColumn.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));

//         // Poblar ComboBox con nombres de pacientes simulados
//         pacienteComboBox.getItems().addAll("Juan Pérez", "María López", "Carlos González");

//         // Configurar acción al seleccionar un paciente
//         pacienteComboBox.setOnAction(event -> cargarSignosVitales(pacienteComboBox.getValue()));

//         // Inicializar con datos vacíos
//         tomaSignosTable.setItems(listaSignosVitales);
//     }

//     private void cargarSignosVitales(String nombrePaciente) {
//         listaSignosVitales.clear();

//         // Obtener datos desde la capa de negocio y convertir si es necesario
//         List<TomaSignos> signosVitales = tomaSignosFacade.obtenerSignosPorPaciente(nombrePaciente)
//             .stream()
//             .map(signo -> new TomaSignos(
//                 signo.getId(),
//                 signo.getFrecuenciaCardiaca(),
//                 signo.getPresionArterial(),
//                 signo.getTemperatura(),
//                 signo.getFrecuenciaRespiratoria(),
//                 signo.getOxigenacion(),
//                 signo.getFechaHora()
//             ))
//             .collect(Collectors.toList());

//         listaSignosVitales.addAll(signosVitales);
//     }

//     @FXML
//     public void agregarTomaSignos() {
//         try {
//             String frecuenciaCardiaca = frecuenciaCardiacaField.getText();
//             String presionArterial = presionArterialField.getText();
//             String temperatura = temperaturaField.getText();
//             String frecuenciaRespiratoria = frecuenciaRespiratoriaField.getText();
//             String oxigenacion = oxigenacionField.getText();
//             String fechaHora = fechaHoraField.getText();

//             TomaSignos nuevaToma = new TomaSignos(
//                 listaSignosVitales.size() + 1,
//                 frecuenciaCardiaca,
//                 presionArterial,
//                 temperatura,
//                 frecuenciaRespiratoria,
//                 oxigenacion,
//                 fechaHora
//             );

//             tomaSignosFacade.registrarTomaSignos(nuevaToma);
//             listaSignosVitales.add(nuevaToma);

//             mostrarMensaje("Éxito", "Signo vital agregado correctamente.");
//             limpiarCampos();
//         } catch (Exception e) {
//             mostrarError("Error al agregar el signo vital: " + e.getMessage());
//         }
//     }

//     @FXML
//     public void actualizarTomaSignos() {
//         TomaSignos seleccionado = tomaSignosTable.getSelectionModel().getSelectedItem();
//         if (seleccionado != null) {
//             try {
//                 seleccionado.setFrecuenciaCardiaca(frecuenciaCardiacaField.getText());
//                 seleccionado.setPresionArterial(presionArterialField.getText());
//                 seleccionado.setTemperatura(temperaturaField.getText());
//                 seleccionado.setFrecuenciaRespiratoria(frecuenciaRespiratoriaField.getText());
//                 seleccionado.setOxigenacion(oxigenacionField.getText());
//                 seleccionado.setFechaHora(fechaHoraField.getText());

//                 tomaSignosFacade.actualizarTomaSignos(seleccionado);
//                 tomaSignosTable.refresh();

//                 mostrarMensaje("Éxito", "Signo vital actualizado correctamente.");
//             } catch (Exception e) {
//                 mostrarError("Error al actualizar el signo vital: " + e.getMessage());
//             }
//         } else {
//             mostrarMensaje("Advertencia", "Seleccione un signo vital para actualizar.");
//         }
//     }

//     @FXML
//     public void eliminarTomaSignos() {
//         TomaSignos seleccionado = tomaSignosTable.getSelectionModel().getSelectedItem();
//         if (seleccionado != null) {
//             try {
//                 tomaSignosFacade.eliminarTomaSignos(seleccionado.getId());
//                 listaSignosVitales.remove(seleccionado);

//                 mostrarMensaje("Éxito", "Signo vital eliminado correctamente.");
//             } catch (Exception e) {
//                 mostrarError("Error al eliminar el signo vital: " + e.getMessage());
//             }
//         } else {
//             mostrarMensaje("Advertencia", "Seleccione un signo vital para eliminar.");
//         }
//     }

//     @FXML
//     public void volverDashboard(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/main.fxml"));
//             Parent mainView = loader.load();

//             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//             stage.getScene().setRoot(mainView);
//         } catch (IOException e) {
//             mostrarError("Error al regresar al Dashboard: " + e.getMessage());
//         }
//     }

//     private void mostrarMensaje(String titulo, String mensaje) {
//         Alert alert = new Alert(Alert.AlertType.INFORMATION);
//         alert.setTitle(titulo);
//         alert.setContentText(mensaje);
//         alert.showAndWait();
//     }

//     private void mostrarError(String mensaje) {
//         Alert alert = new Alert(Alert.AlertType.ERROR);
//         alert.setTitle("Error");
//         alert.setContentText(mensaje);
//         alert.showAndWait();
//     }

//     private void limpiarCampos() {
//         frecuenciaCardiacaField.clear();
//         presionArterialField.clear();
//         temperaturaField.clear();
//         frecuenciaRespiratoriaField.clear();
//         oxigenacionField.clear();
//         fechaHoraField.clear();
//     }
// }
package com.hospital.UI;

import com.hospital.modelos.TomaSignos;
import com.hospital.negocio.TomaSignosFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.*;
import java.io.IOException;
import java.util.List;

public class TomaSignosController {
    @FXML private TextField fechaHoraField;
    @FXML private ComboBox<String> pacienteComboBox;
    @FXML private TextField frecuenciaCardiacaField;
    @FXML private TextField presionArterialField;
    @FXML private TextField temperaturaField;
    @FXML private TextField frecuenciaRespiratoriaField;
    @FXML private TextField oxigenacionField;
    @FXML private TableView<TomaSignos> tomaSignosTable;
    @FXML private TableColumn<TomaSignos, Number> idColumn;
    @FXML private TableColumn<TomaSignos, String> frecuenciaCardiacaColumn;
    @FXML private TableColumn<TomaSignos, String> presionArterialColumn;
    @FXML private TableColumn<TomaSignos, String> temperaturaColumn;
    @FXML private TableColumn<TomaSignos, String> frecuenciaRespiratoriaColumn;
    @FXML private TableColumn<TomaSignos, String> oxigenacionColumn;
    @FXML private TableColumn<TomaSignos, String> fechaHoraColumn;
    private final ObservableList<TomaSignos> listaSignosVitales = FXCollections.observableArrayList();
    private final TomaSignosFacade tomaSignosFacade = new TomaSignosFacade();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        frecuenciaCardiacaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuenciaCardiaca"));
        presionArterialColumn.setCellValueFactory(new PropertyValueFactory<>("presionArterial"));
        temperaturaColumn.setCellValueFactory(new PropertyValueFactory<>("temperatura"));
        frecuenciaRespiratoriaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuenciaRespiratoria"));
        oxigenacionColumn.setCellValueFactory(new PropertyValueFactory<>("oxigenacion"));
        fechaHoraColumn.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        tomaSignosTable.setItems(listaSignosVitales);

        // Poblar ComboBox con nombres de pacientes desde la base de datos
        cargarPacientesEnComboBox();

        // Configurar acción al seleccionar un paciente
        pacienteComboBox.setOnAction(event -> cargarSignosVitales(pacienteComboBox.getValue()));
    }

    private void cargarPacientesEnComboBox() {
        try {
            List<String> pacientes = tomaSignosFacade.obtenerTodosLosPacientes();
            pacienteComboBox.getItems().clear();
            pacienteComboBox.getItems().addAll(pacientes);
        } catch (Exception e) {
            mostrarError("Error al cargar pacientes: " + e.getMessage());
        }
    }
    

    private void cargarSignosVitales(String nombrePaciente) {
        System.out.println("Paciente seleccionado: " + nombrePaciente);
        listaSignosVitales.clear();
        try {
            List<TomaSignos> signosVitales = tomaSignosFacade.obtenerSignosPorPaciente(nombrePaciente);
            System.out.println("Signos vitales obtenidos: " + signosVitales);
            listaSignosVitales.addAll(signosVitales);
        } catch (Exception e) {
            mostrarError("Error al cargar signos vitales: " + e.getMessage());
        }
    }
    
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    @FXML
    public void actualizarTomaSignos() {
        TomaSignos seleccionado = tomaSignosTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                seleccionado.setFrecuenciaCardiaca(frecuenciaCardiacaField.getText());
                seleccionado.setPresionArterial(presionArterialField.getText());
                seleccionado.setTemperatura(temperaturaField.getText());
                seleccionado.setFrecuenciaRespiratoria(frecuenciaRespiratoriaField.getText());
                seleccionado.setOxigenacion(oxigenacionField.getText());
                seleccionado.setFechaHora(fechaHoraField.getText());
    
                tomaSignosFacade.actualizarTomaSignos(seleccionado);
                tomaSignosTable.refresh();
    
                mostrarMensaje("Éxito", "Signo vital actualizado correctamente.");
            } catch (Exception e) {
                mostrarError("Error al actualizar el signo vital: " + e.getMessage());
            }
        } else {
            mostrarMensaje("Advertencia", "Seleccione un signo vital para actualizar.");
        }
    }
    
    @FXML
    public void agregarTomaSignos() {
        try {
            // Capturar datos de la interfaz
            String nombrePaciente = pacienteComboBox.getValue();
            String frecuenciaCardiaca = frecuenciaCardiacaField.getText();
            String presionArterial = presionArterialField.getText();
            String temperatura = temperaturaField.getText();
            String frecuenciaRespiratoria = frecuenciaRespiratoriaField.getText();
            String oxigenacion = oxigenacionField.getText();
            String fechaHora = fechaHoraField.getText();
    
            // Validar datos ingresados
            if (nombrePaciente == null || nombrePaciente.isEmpty() ||
                frecuenciaCardiaca == null || frecuenciaCardiaca.isEmpty() ||
                presionArterial == null || presionArterial.isEmpty() ||
                temperatura == null || temperatura.isEmpty() ||
                frecuenciaRespiratoria == null || frecuenciaRespiratoria.isEmpty() ||
                oxigenacion == null || oxigenacion.isEmpty() ||
                fechaHora == null || fechaHora.isEmpty()) {
                mostrarError("Todos los campos deben estar llenos.");
                return;
            }
    
            // Crear el objeto TomaSignos con los datos capturados
            TomaSignos nuevaToma = new TomaSignos(0, frecuenciaCardiaca, presionArterial, temperatura, frecuenciaRespiratoria, oxigenacion, fechaHora);
    
            // Guardar los datos en la base de datos a través de la fachada
            tomaSignosFacade.registrarTomaSignos(nuevaToma, nombrePaciente);
    
            // Mostrar mensaje de éxito
            mostrarMensaje("Éxito", "Signo vital agregado correctamente.");
    
            // Limpiar los campos después de agregar
            limpiarCampos();
        } catch (Exception e) {
            mostrarError("Error al agregar el signo vital: " + e.getMessage());
        }
    }
    

    @FXML
public void eliminarTomaSignos() {
    TomaSignos seleccionado = tomaSignosTable.getSelectionModel().getSelectedItem();
    if (seleccionado != null) {
        try {
            tomaSignosFacade.eliminarTomaSignos(seleccionado.getId());
            listaSignosVitales.remove(seleccionado);
            mostrarMensaje("Éxito", "Signo vital eliminado correctamente.");
        } catch (Exception e) {
            mostrarError("Error al eliminar el signo vital: " + e.getMessage());
        }
    } else {
        mostrarMensaje("Advertencia", "Seleccione un signo vital para eliminar.");
    }
}
        @FXML
public void volverDashboard(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hospital/ui/main.fxml"));
        Parent mainView = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(mainView));
        stage.show();
    } catch (IOException e) {
        mostrarError("Error al regresar al Dashboard: " + e.getMessage());
    }
}

    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        frecuenciaCardiacaField.clear();
        presionArterialField.clear();
        temperaturaField.clear();
        frecuenciaRespiratoriaField.clear();
        oxigenacionField.clear();
    }
}
