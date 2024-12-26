package com.hospital.UI;

import com.hospital.negocio.CategoriasFacade;
import com.hospital.negocio.EnfermeroFacade;
import com.hospital.negocio.PacientesFacade;
import com.hospital.modelos.Paciente;
import com.hospital.modelos.Enfermero;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.hospital.repositorios.BaseController;

public class GestionCategoriasController extends BaseController {

    @FXML private TextField buscarPacienteNombre;
    @FXML private TextField buscarPacienteEnfermedad;
    @FXML private TableView<String> tablaCategorias;
    @FXML private TableColumn<String, String> colCategoria;
    @FXML private TextField txtNuevaCategoria;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;

    // Nuevos elementos para pacientes y enfermeros
    @FXML private TableView<Paciente> tablaPacientes;
    @FXML private TableColumn<Paciente, Number> idPacienteColumn;
    @FXML private TableColumn<Paciente, String> nombrePacienteColumn;
    @FXML private TableColumn<Paciente, String> enfermedadPacienteColumn;

    @FXML private TextField buscarEnfermeroNombre;
    @FXML private ComboBox<String> buscarEnfermeroEspecialidad;
    @FXML private TableView<Enfermero> tablaEnfermeros;
    @FXML private TableColumn<Enfermero, Number> idEnfermeroColumn;
    @FXML private TableColumn<Enfermero, String> nombreEnfermeroColumn;
    @FXML private TableColumn<Enfermero, String> especialidadEnfermeroColumn;
    

    private final ObservableList<String> listaCategorias = FXCollections.observableArrayList();
    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    private final ObservableList<Enfermero> listaEnfermeros = FXCollections.observableArrayList();

    // Instancias de las fachadas
    private final CategoriasFacade categoriasFacade = new CategoriasFacade();
    private final PacientesFacade pacientesFacade = new PacientesFacade();
    private final EnfermeroFacade enfermeroFacade = new EnfermeroFacade();
    private MainController mainController; // Referencia al MainController

    @FXML
    public void initialize() {
        // Configuración inicial de la tabla de categorías
        colCategoria.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        cargarCategoriasDesdeBD();

        // Configuración inicial para pacientes
        idPacienteColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nombrePacienteColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        enfermedadPacienteColumn.setCellValueFactory(data -> data.getValue().enfermedadesProperty());
        tablaPacientes.setItems(listaPacientes);

        // Configuración inicial para enfermeros
        idEnfermeroColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nombreEnfermeroColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        especialidadEnfermeroColumn.setCellValueFactory(data -> data.getValue().especialidadProperty());
        tablaEnfermeros.setItems(listaEnfermeros);

        buscarEnfermeroEspecialidad.setItems(FXCollections.observableArrayList(
                "Medicina General", "Ginecología", "Dermatología", "Cardiología", "Pediatría"
        ));

        cargarPacientesDesdeBD();
        cargarEnfermerosDesdeBD();
    }

    @FXML
    private void agregarCategoria() {
        String nuevaCategoria = txtNuevaCategoria.getText().trim();
        if (!nuevaCategoria.isEmpty() && !listaCategorias.contains(nuevaCategoria)) {
            try {
                categoriasFacade.guardarCategoria(nuevaCategoria);
                listaCategorias.add(nuevaCategoria);
                txtNuevaCategoria.clear();
                mostrarAlerta("Éxito", "Categoría agregada correctamente.");
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo agregar la categoría: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Advertencia", "Categoría inválida o ya existente.");
        }
    }

    @FXML
    private void eliminarCategoria() {
        String seleccion = tablaCategorias.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            try {
                categoriasFacade.eliminarCategoria(seleccion);
                listaCategorias.remove(seleccion);
                mostrarAlerta("Éxito", "Categoría eliminada correctamente.");
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo eliminar la categoría: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Advertencia", "Selecciona una categoría para eliminar.");
        }
    }
    @FXML
    private void abrirRegistroAntecedentes() {
        mainController.cambiarContenido("registro_antecedentes.fxml");
    }
   
    @FXML
    private void abrirGestionCategorias() {
        mainController.cambiarContenido("gestion_categorias.fxml");
    }
    
    @FXML
    private void regresarAlDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal(); // Volver al Dashboard principal
        } else {
            mostrarAlerta("Error", "No se puede regresar al Dashboard porque el controlador principal no está configurado.");
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void cargarCategoriasDesdeBD() {
        try {
            listaCategorias.clear();
            listaCategorias.addAll(categoriasFacade.listarCategorias());
            tablaCategorias.setItems(listaCategorias);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar las categorías: " + e.getMessage());
        }
    }

    private void cargarPacientesDesdeBD() {
        try {
            listaPacientes.clear();
            listaPacientes.addAll(pacientesFacade.obtenerTodosLosPacientes());
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los pacientes: " + e.getMessage());
        }
    }

    private void cargarEnfermerosDesdeBD() {
        try {
            listaEnfermeros.clear();
            listaEnfermeros.addAll(enfermeroFacade.listarEnfermeros());
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los enfermeros: " + e.getMessage());
        }
    }

    @FXML
    public void filtrarPacientes() {
        String nombre = buscarPacienteNombre.getText().toLowerCase();
        String enfermedad = buscarPacienteEnfermedad.getText().toLowerCase();

        tablaPacientes.setItems(listaPacientes.filtered(p ->
                (nombre.isEmpty() || p.getNombre().toLowerCase().contains(nombre)) &&
                (enfermedad.isEmpty() || p.getEnfermedades().toLowerCase().contains(enfermedad))
        ));
    }

    @FXML
    public void filtrarEnfermeros() {
        String nombre = buscarEnfermeroNombre.getText().toLowerCase();
        String especialidad = buscarEnfermeroEspecialidad.getValue();

        tablaEnfermeros.setItems(listaEnfermeros.filtered(e ->
                (nombre.isEmpty() || e.getNombre().toLowerCase().contains(nombre)) &&
                (especialidad == null || e.getEspecialidad().equalsIgnoreCase(especialidad))
        ));
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
