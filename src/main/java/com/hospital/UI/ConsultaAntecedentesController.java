package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.hospital.negocio.AntecedentesFacade;
import com.hospital.negocio.PacientesFacade;
import com.hospital.modelos.Antecedente;
import com.hospital.modelos.Paciente;
import com.hospital.repositorios.AntecedentesRepository;
import com.hospital.repositorios.AntecedentesRepositoryMySQL;
import com.hospital.repositorios.BaseController;
import com.hospital.repositorios.ConsultaMedicaRepository;
import com.hospital.repositorios.ConsultaMedicaRepositoryMySQL;

import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class ConsultaAntecedentesController extends BaseController {
 private final AntecedentesRepository antecedentesRepository = new AntecedentesRepositoryMySQL();
    @FXML private TextField txtBuscarPaciente;
    @FXML private TableView<Antecedente> tablaAntecedentes;
    @FXML private TableColumn<Antecedente, String> colAntecedente;
    @FXML private Label lblAntecedentes;
   
    
    private ObservableList<Antecedente> listaAntecedentes;

    private final PacientesFacade pacientesFacade = new PacientesFacade();
    private final AntecedentesFacade antecedentesFacade = new AntecedentesFacade();

    @FXML
    public void initialize() {
        listaAntecedentes = FXCollections.observableArrayList();
    colAntecedente.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    tablaAntecedentes.setItems(listaAntecedentes);
    }

    @FXML
    private void regresarAlDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }

   @FXML
private void buscarAntecedentes() {
    String textoBusqueda = txtBuscarPaciente.getText().trim();
    System.out.println("Texto ingresado para buscar paciente: " + textoBusqueda);

    listaAntecedentes.clear();

    try {
        List<Paciente> pacientes;

        // Si el campo de búsqueda está vacío, buscar todos los pacientes con antecedentes
        if (textoBusqueda.isEmpty()) {
            pacientes = pacientesFacade.buscarPacientesConAntecedentes();
        } else if (textoBusqueda.matches("\\d+")) {
            // Buscar por ID (numérico)
            Paciente paciente = pacientesFacade.buscarPacientePorId(Integer.parseInt(textoBusqueda));
            pacientes = paciente != null ? List.of(paciente) : new ArrayList<>();
        } else {
            // Buscar por nombre
            pacientes = pacientesFacade.buscarPacientesPorNombre(textoBusqueda);
        }

        if (pacientes.isEmpty()) {
            mostrarAlerta("No se encontraron pacientes con el criterio proporcionado.");
        } else {
            for (Paciente paciente : pacientes) {
                List<Antecedente> antecedentes = antecedentesFacade.obtenerAntecedentes(paciente.getId());
                if (antecedentes.isEmpty()) {
                    System.out.println("Paciente: " + paciente.getNombre() + " " + paciente.getApellido() + " no tiene antecedentes.");
                } else {
                    antecedentes.forEach(listaAntecedentes::add);
                    System.out.println("Paciente: " + paciente.getNombre() + " " + paciente.getApellido() + " tiene antecedentes: " + antecedentes);
                }
            }
        }

        // Actualizar la tabla
        tablaAntecedentes.setItems(listaAntecedentes);

    } catch (Exception e) {
        System.err.println("Error al buscar antecedentes: " + e.getMessage());
        e.printStackTrace();
        mostrarAlerta("Error al buscar antecedentes: " + e.getMessage());
    }
}

    @FXML
    private void agregarAntecedente() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Agregar Antecedente");
        dialog.setHeaderText("Introduce un nuevo antecedente:");
        dialog.setContentText("Descripción:");
    
        dialog.showAndWait().ifPresent(descripcion -> {
            Antecedente nuevoAntecedente = new Antecedente(0, 1, "Nuevo", descripcion); // Ajustar valores
            antecedentesFacade.guardarAntecedente(1, "Nuevo", descripcion); // Cambiar ID del paciente
            listaAntecedentes.add(nuevoAntecedente);
        });
    }
    

    @FXML
    private void eliminarAntecedente() {
        Antecedente seleccionado = tablaAntecedentes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Por favor, selecciona un antecedente para eliminar.");
            return;
        }
    
        try {
            antecedentesFacade.eliminarAntecedente(seleccionado.getId());
            listaAntecedentes.remove(seleccionado);
        } catch (Exception e) {
            mostrarAlerta("Error al eliminar el antecedente: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
private final ConsultaMedicaRepository consultaMedicaRepository = new ConsultaMedicaRepositoryMySQL();

    @FXML
    private void actualizarAntecedente() {
        Antecedente seleccionado = tablaAntecedentes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Por favor, selecciona un antecedente para actualizar.");
            return;
        }

        // Mostrar un diálogo para que el usuario edite el antecedente
        TextInputDialog dialog = new TextInputDialog(seleccionado.getDescripcion());
        dialog.setTitle("Actualizar Antecedente");
        dialog.setHeaderText("Modifica la descripción del antecedente:");
        dialog.setContentText("Descripción:");

        dialog.showAndWait().ifPresent(nuevaDescripcion -> {
            try {
                // Crear un objeto actualizado y llamarlo al repositorio
                Antecedente actualizado = new Antecedente(
                        seleccionado.getId(),
                        seleccionado.getIdPaciente(),
                        seleccionado.getTipo(),
                        nuevaDescripcion
                );
                antecedentesRepository.actualizarAntecedente(actualizado);

                // Actualizar la tabla
                listaAntecedentes.set(listaAntecedentes.indexOf(seleccionado), actualizado);
                tablaAntecedentes.refresh();

                System.out.println("Antecedente actualizado correctamente.");
            } catch (Exception e) {
                mostrarAlerta("Error al actualizar el antecedente: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
@FXML
private void cargarPacientesConAntecedentes() {
    try {
        // Llama al método de la fachada para obtener los pacientes con antecedentes
        List<Paciente> pacientes = antecedentesFacade.listarPacientesConAntecedentes();

        if (pacientes.isEmpty()) {
            mostrarAlerta("No se encontraron pacientes con antecedentes.");
            return;
        }

        // Limpiar y actualizar la lista de pacientes
        listaAntecedentes.clear(); // Utiliza un ObservableList para la tabla de antecedentes
        for (Paciente paciente : pacientes) {
            List<Antecedente> antecedentes = antecedentesFacade.obtenerAntecedentes(paciente.getId());
            antecedentes.forEach(listaAntecedentes::add);
        }

        // Actualizar la tabla con los nuevos antecedentes
        tablaAntecedentes.setItems(listaAntecedentes);

    } catch (Exception e) {
        mostrarAlerta("Error al cargar pacientes con antecedentes: " + e.getMessage());
        e.printStackTrace();
    }
}


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
