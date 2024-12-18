package com.hospital.UI;

import com.hospital.modelos.Enfermero;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class EnfermerosDisponiblesController implements Initializable {

    @FXML private TableView<Enfermero> enfermerosTable;
    @FXML private TableColumn<Enfermero, Number> idColumn;
    @FXML private TableColumn<Enfermero, String> nombreColumn;
    @FXML private TableColumn<Enfermero, String> especialidadColumn;
    @FXML private TableColumn<Enfermero, String> estadoColumn;

    private ObservableList<Enfermero> enfermerosDisponibles = FXCollections.observableArrayList();

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar columnas
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        especialidadColumn.setCellValueFactory(data -> data.getValue().especialidadProperty());
        estadoColumn.setCellValueFactory(data -> data.getValue().estadoProperty());

        // Cargar datos estáticos
        cargarDatosPrueba();

        // Filtrar solo enfermeros disponibles
        ObservableList<Enfermero> disponibles = enfermerosDisponibles.filtered(enf -> enf.getEstado().equals("Disponible"));
        enfermerosTable.setItems(disponibles);
    }

    private void cargarDatosPrueba() {
        enfermerosDisponibles.add(new Enfermero(1, "Ana Torres", "Pediatría", "Disponible"));
        enfermerosDisponibles.add(new Enfermero(2, "Luis Ramírez", "Urgencias", "Ocupado"));
        enfermerosDisponibles.add(new Enfermero(3, "Carla Gómez", "Cardiología", "Disponible"));
    }

    @FXML
    private void regresarDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }
    private ObservableList<Enfermero> listaEnfermeros = FXCollections.observableArrayList();
    @FXML
public void mostrarEnfermerosDisponibles() {
    ObservableList<Enfermero> disponibles = FXCollections.observableArrayList();

    // Filtra solo los enfermeros con estado 'Disponible'
    for (Enfermero e : listaEnfermeros) {
        if ("Disponible".equals(e.getEstado())) {
            disponibles.add(e);
        }
    }
    enfermerosTable.setItems(disponibles);
}

}
