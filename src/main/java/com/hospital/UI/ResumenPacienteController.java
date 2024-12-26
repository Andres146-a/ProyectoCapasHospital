package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import com.hospital.modelos.Antecedente;
import com.hospital.modelos.Consulta;
import com.hospital.modelos.Paciente;
import com.hospital.modelos.TomaSignos;
import com.hospital.negocio.AntecedentesFacade;
import com.hospital.negocio.PacientesFacade;
import com.hospital.repositorios.AntecedentesRepositoryMySQL;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

public class ResumenPacienteController {

    @FXML private Label lblNombrePaciente;
    @FXML private Label lblEdadPaciente;
    @FXML private Label lblAntecedentes;
    @FXML private Label lblSignosVitales;
    @FXML private Label lblUltimaConsulta;
    @FXML private Label lblTratamientos;
    @FXML private Label lblAlertas;

    private MainController mainController;
    private PacientesFacade pacientesFacade = new PacientesFacade(); // Conexión a la lógica de negocio
    private AntecedentesFacade antecedentesFacade;
public ResumenPacienteController() {
    // Inicializa el facade con un repositorio real
    this.antecedentesFacade = new AntecedentesFacade(new AntecedentesRepositoryMySQL());
}

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente != null) {
            actualizarInformacion(paciente);
        } else {
            System.out.println("Paciente es null.");
        }
    }

   private void actualizarInformacion(Paciente paciente) {
    lblNombrePaciente.setText("Nombre: " + paciente.getNombre() + " " + paciente.getApellido());
    lblEdadPaciente.setText("Edad: " + (paciente.getEdad() > 0 ? paciente.getEdad() + " años" : "No registrada"));

    try {
        List<Antecedente> antecedentes = antecedentesFacade.obtenerAntecedentes(paciente.getId());
        String antecedentesTexto = antecedentes.isEmpty()
            ? "Sin antecedentes registrados."
            : antecedentes.stream()
                .map(a -> a.getTipo() + ": " + a.getDescripcion())
                .reduce("", (acum, item) -> acum + "\n" + item);

        lblAntecedentes.setText("Antecedentes:\n" + antecedentesTexto.trim());
    } catch (Exception e) {
        lblAntecedentes.setText("Error al cargar antecedentes.");
        e.printStackTrace();
    }

    lblSignosVitales.setText("Signos Vitales Recientes:\n" + obtenerSignosVitalesRecientes(paciente));
    lblUltimaConsulta.setText("Última Consulta: " + obtenerUltimaConsulta(paciente));
    lblTratamientos.setText("Tratamientos Actuales:\n" + (paciente.getEnfermedades() != null && !paciente.getEnfermedades().isEmpty() ? paciente.getEnfermedades() : "Sin tratamientos registrados."));
    lblAlertas.setText("Alertas: " + (paciente.getParametrosAlertas() != null && !paciente.getParametrosAlertas().isEmpty() ? obtenerAlertas(paciente) : "Sin alertas registradas."));
}

    private String obtenerSignosVitalesRecientes(Paciente paciente) {
        // Obtener el registro más reciente de signos vitales
        List<TomaSignos> registroSignos = paciente.getRegistroS();
        if (registroSignos == null || registroSignos.isEmpty()) {
            return "Sin registros de signos vitales.";
        }

        TomaSignos signosRecientes = registroSignos.get(registroSignos.size() - 1);
        return "- Frecuencia Cardíaca: " + (signosRecientes.getFrecuenciaCardiaca() != null 
                                            ? signosRecientes.getFrecuenciaCardiaca() + " lpm" : "Sin datos") + "\n" +
               "- Presión Arterial: " + (signosRecientes.getPresionArterial() != null 
                                         ? signosRecientes.getPresionArterial() + " mmHg" : "Sin datos") + "\n" +
               "- Temperatura: " + (signosRecientes.getTemperatura() != null 
                                    ? signosRecientes.getTemperatura() + "°C" : "Sin datos") + "\n" +
               "- Frecuencia Respiratoria: " + (signosRecientes.getFrecuenciaRespiratoria() != null 
                                                ? signosRecientes.getFrecuenciaRespiratoria() + " rpm" : "Sin datos") + "\n" +
               "- Oxigenación: " + (signosRecientes.getOxigenacion() != null 
                                    ? signosRecientes.getOxigenacion() + "%" : "Sin datos");
    }

    private String obtenerUltimaConsulta(Paciente paciente) {
        // Simulación de la última consulta, este dato debería venir de la base de datos
        return "18-12-2024 con Dra. Martínez."; // Cambiar a datos reales si están disponibles
    }

    private String obtenerAlertas(Paciente paciente) {
        StringBuilder alertas = new StringBuilder();
        paciente.getParametrosAlertas().forEach((parametro, valor) -> {
            alertas.append(parametro).append(": ").append(valor).append("\n");
        });
        return alertas.toString().trim();
    }

    @FXML
    private void regresarDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
            cerrarVentana();
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lblNombrePaciente.getScene().getWindow();
        stage.close();
    }
    @FXML
    private TableView<Consulta> consultasTable;
    @FXML
public void mostrarResumenPaciente() {
    Consulta consultaSeleccionada = consultasTable.getSelectionModel().getSelectedItem();
    if (consultaSeleccionada == null) {
        mostrarAlerta("Advertencia", "Seleccione una consulta para ver el resumen.");
        return;
    }

    Paciente pacienteSeleccionado = consultaSeleccionada.getPaciente();
    if (pacienteSeleccionado == null) {
        mostrarAlerta("Advertencia", "No hay paciente asociado a esta consulta.");
        return;
    }

    List<Antecedente> antecedentes = antecedentesFacade.obtenerAntecedentes(pacienteSeleccionado.getId());
    StringBuilder antecedentesTexto = new StringBuilder();
    for (Antecedente antecedente : antecedentes) {
        antecedentesTexto.append(antecedente.getTipo()).append(": ").append(antecedente.getDescripcion()).append("\n");
    }

    Alert resumen = new Alert(Alert.AlertType.INFORMATION);
    resumen.setTitle("Resumen del Paciente");
    resumen.setHeaderText("Resumen del Paciente");
    resumen.setContentText(
        "Nombre: " + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellido() + "\n" +
        "Edad: " + pacienteSeleccionado.getEdad() + "\n" +
        "Antecedentes:\n" + (antecedentesTexto.length() > 0 ? antecedentesTexto : "Sin antecedentes registrados.")
    );
    resumen.showAndWait();
}
private void mostrarAlerta(String titulo, String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.WARNING);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}


}
