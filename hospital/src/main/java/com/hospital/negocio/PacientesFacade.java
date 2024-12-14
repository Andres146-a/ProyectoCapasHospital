package com.hospital.negocio;

import com.hospital.modelos.Paciente;
import com.hospital.repositorios.PacientesRepository;

public class PacientesFacade {
    private final PacientesRepository repository = new PacientesRepository();

    public void guardarPaciente(Paciente paciente) {
        if (paciente.getNombre() == null || paciente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente no puede estar vacío.");
        }
        if (paciente.getApellido() == null || paciente.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El apellido del paciente no puede estar vacío.");
        }
        // Enviar los datos al repositorio
        repository.guardar(paciente);
    }
}
