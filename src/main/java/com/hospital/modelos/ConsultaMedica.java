package com.hospital.modelos;
import java.time.LocalDate; 
public class ConsultaMedica extends Consulta {
    private String diagnostico;

    public ConsultaMedica(int id, Paciente paciente, String doctor, LocalDate fecha, String descripcion, String diagnostico) {
        super(id, paciente, doctor, fecha, descripcion);
        this.diagnostico = diagnostico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return super.toString() + ", diagnostico='" + diagnostico + "'";
    }
}
