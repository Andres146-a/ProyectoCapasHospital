package com.hospital.negocio;

import com.hospital.modelos.CentroMedico;
import com.hospital.repositorios.CentroMedicoRepository;
import com.hospital.repositorios.CentroMedicoRepositoryMySQL;

import java.util.List;

public class CentroMedicoFacade {
    private final CentroMedicoRepository repository = new CentroMedicoRepositoryMySQL();


    public void guardarCentroMedico(CentroMedico centroMedico) {
        if (centroMedico.getNombre() == null || centroMedico.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del centro médico no puede estar vacío.");
        }
        if (centroMedico.getDireccion() == null || centroMedico.getDireccion().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía.");
        }
        repository.guardarCentroMedico(centroMedico);

    }

    public List<CentroMedico> listarCentrosMedicos() {
        return repository.listarTodos();
    }
}
