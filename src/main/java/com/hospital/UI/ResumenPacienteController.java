package com.hospital.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.hospital.modelos.Paciente;
import javafx.stage.Stage;
public class ResumenPacienteController {

    @FXML private Label lblNombrePaciente;
    @FXML private Label lblEdadPaciente;
    @FXML private Label lblAntecedentes;
    @FXML private Label lblSignosVitales;
    @FXML private Label lblUltimaConsulta;
    @FXML private Label lblTratamientos;

    private MainController mainController;

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
        lblEdadPaciente.setText("Edad: " + paciente.getEdad() + " años");

        // Información adicional (datos simulados)
        lblAntecedentes.setText("Antecedentes: Hipertensión, Diabetes Tipo 2.");
        lblSignosVitales.setText("Signos Vitales Recientes:\n- Frecuencia Cardíaca: 85 lpm\n- Presión Arterial: 120/80 mmHg\n- Temperatura: 37.2°C");
        lblUltimaConsulta.setText("Última Consulta: 18-12-2024 con Dra. Martínez.");
        lblTratamientos.setText("Tratamientos Actuales:\n- Paracetamol 500 mg, 3 veces al día.\n- Reposo absoluto.");
    }

    @FXML
    private void regresarDashboard() {
        if (mainController != null) {
            mainController.restaurarContenidoPrincipal();
            Stage stage = (Stage) lblNombrePaciente.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Error: MainController no está configurado.");
        }
    }
}
