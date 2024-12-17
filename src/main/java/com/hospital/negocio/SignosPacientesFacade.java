package com.hospital.negocio;

import com.hospital.modelos.SignosPaciente;
import com.hospital.repositorios.SignosPacientesRepository;
import com.hospital.repositorios.SignosPacientesRepositoryMySQL;

import java.util.List;

public class SignosPacientesFacade {
    private final SignosPacientesRepository repository = new SignosPacientesRepositoryMySQL();

    public void guardarSignosPaciente(SignosPaciente signosPaciente) {
        if (signosPaciente.getTemperatura() <= 0) {
            throw new IllegalArgumentException("La temperatura debe ser válida.");
        }
        if (signosPaciente.getPresion() == null || signosPaciente.getPresion().isEmpty()) {
            throw new IllegalArgumentException("La presión no puede estar vacía.");
        }
        repository.guardarSignosPaciente(signosPaciente);
    }

    public List<SignosPaciente> listarSignosPacientes() {
        return repository.listarTodos();
    }
}
