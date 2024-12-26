package com.hospital.negocio;

import com.hospital.modelos.SignosPaciente;
import com.hospital.repositorios.SignosPacientesRepository;

import java.util.List;

public class SignosPacientesFacade {
    private final SignosPacientesRepository repository;

    // Constructor para inyección de dependencias
    public SignosPacientesFacade(SignosPacientesRepository repository) {
        this.repository = repository;
    }

    // Validar datos del objeto SignosPaciente
    private void validarSignosPaciente(SignosPaciente signosPaciente) {
        if (signosPaciente == null) {
            throw new IllegalArgumentException("Los signos del paciente no pueden ser nulos.");
        }
        if (signosPaciente.getTemperatura() <= 0) {
            throw new IllegalArgumentException("La temperatura debe ser mayor a 0.");
        }
        if (signosPaciente.getPresion() == null || signosPaciente.getPresion().isEmpty()) {
            throw new IllegalArgumentException("La presión arterial no puede estar vacía.");
        }
    }

    // Guardar o actualizar SignosPaciente
    public void guardarSignosPaciente(SignosPaciente signosPaciente) {
        validarSignosPaciente(signosPaciente);
        if (signosPaciente.getId() > 0) {
            repository.actualizarSignosPaciente(signosPaciente);
        } else {
            repository.guardarSignosPaciente(signosPaciente);
        }
    }

    // Listar todos los registros de signos de pacientes
    public List<SignosPaciente> listarSignosPacientes() {
        return repository.listarTodos();
    }

    // Buscar SignosPaciente por ID
    public SignosPaciente buscarSignosPacientePorId(int idSignosPaciente) {
        if (idSignosPaciente <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0.");
        }
        return repository.buscarPorId(idSignosPaciente);
    }

    // Eliminar SignosPaciente
    public void eliminarSignosPaciente(int idSignosPaciente) {
        if (idSignosPaciente <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0.");
        }
        repository.eliminarSignosPaciente(idSignosPaciente);
    }
}
