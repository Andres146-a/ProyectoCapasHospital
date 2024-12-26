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

    public List<Enfermero> listarEnfermerosPorEstado(String estado) {
        if (estado == null || estado.isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }
        return repository.listarTodos().stream()
                .filter(e -> estado.equalsIgnoreCase(e.getEstado()))
                .toList();
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

    // Nuevo método para actualizar el estado del enfermero
    // public void actualizarEstado(int idEnfermero, String nuevoEstado) {
    //     if (nuevoEstado == null || nuevoEstado.isEmpty()) {
    //         throw new IllegalArgumentException("El estado no puede estar vacío.");
    //     }
    //     repository.actualizarEstado(idEnfermero, nuevoEstado);
    // }

    // Nuevo método para actualizar el estado del enfermero
    public void actualizarEstado(int idEnfermero, String nuevoEstado) {
        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }
        repository.actualizarEstado(idEnfermero, nuevoEstado);
    }

    // Nuevo método para listar enfermeros activos
    public List<Enfermero> listarActivos() {
        return listarEnfermerosPorEstado("Activo");
    }

    // Nuevo método para listar enfermeros inactivos
    public List<Enfermero> listarInactivos() {
        return listarEnfermerosPorEstado("Inactivo");
    }
    
}
