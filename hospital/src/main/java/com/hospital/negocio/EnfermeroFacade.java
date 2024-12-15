package com.hospital.negocio;

import com.hospital.modelos.Enfermero;
import com.hospital.repositorios.EnfermerosRepository;
import com.hospital.repositorios.EnfermerosRepositoryMySQL;

import java.util.List;

public class EnfermeroFacade {
    private final EnfermerosRepository repository = new EnfermerosRepositoryMySQL();

    public void guardarEnfermero(Enfermero enfermero) {
        if (enfermero.getNombre() == null || enfermero.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del enfermero no puede estar vacío.");
        }
        if (enfermero.getEspecialidad() == null || enfermero.getEspecialidad().isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía.");
        }
        repository.guardarEnfermero(enfermero);
    }

    public List<Enfermero> listarEnfermeros() {
        return repository.listarTodos();
    }

    public void eliminarEnfermero(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del enfermero no es válido.");
        }
        repository.eliminarEnfermero(id);
    }
    public void actualizarEnfermero(Enfermero enfermero) {
        if (enfermero.getId() <= 0) {
            throw new IllegalArgumentException("El ID del enfermero no es válido.");
        }
        if (enfermero.getNombre() == null || enfermero.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del enfermero no puede estar vacío.");
        }
        if (enfermero.getEspecialidad() == null || enfermero.getEspecialidad().isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía.");
        }
        repository.actualizarEnfermero(enfermero);
    }
}
