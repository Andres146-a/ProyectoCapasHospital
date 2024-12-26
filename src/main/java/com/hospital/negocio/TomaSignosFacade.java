package com.hospital.negocio;

import com.hospital.modelos.SignoVital;
import com.hospital.modelos.TomaSignos;
import com.hospital.repositorios.TomaSignosRepository;
import com.hospital.repositorios.TomaSignosRepositoryMySQL;

import java.util.ArrayList;
import java.util.List;

public class TomaSignosFacade {
    private final TomaSignosRepository repository;

    // Constructor para inyección de dependencias
    public TomaSignosFacade(TomaSignosRepository repository) {
        this.repository = repository;
    }

    // Constructor por defecto
    public TomaSignosFacade() {
        this.repository = new TomaSignosRepositoryMySQL();
    }

    // Registrar una nueva toma de signos
    public void registrarTomaSignos(TomaSignos tomaSignos, String nombrePaciente) {
        if (nombrePaciente == null || nombrePaciente.isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente no puede estar vacío.");
        }
        try {
            validarTomaSignos(tomaSignos);
            repository.guardarTomaSignos(tomaSignos, nombrePaciente);
            System.out.println("Toma de signos registrada exitosamente.");
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar la toma de signos: " + e.getMessage());
        }
    }
    

    // Obtener todas las tomas de signos
    public List<TomaSignos> obtenerTodasLasTomas() {
        try {
            return repository.listarTodos();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las tomas de signos: " + e.getMessage());
        }
    }

    // Buscar toma de signos por ID
    public TomaSignos buscarTomaSignosPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        }
        try {
            return repository.buscarPorId(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar la toma de signos por ID: " + e.getMessage());
        }
    }

    // Eliminar toma de signos por ID
    public void eliminarTomaSignos(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        }
        try {
            repository.eliminarPorId(id);
            System.out.println("Toma de signos eliminada exitosamente.");
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la toma de signos: " + e.getMessage());
        }
    }

    // Actualizar toma de signos
    public void actualizarTomaSignos(TomaSignos tomaSignos) {
        validarTomaSignos(tomaSignos);
        if (tomaSignos.getId() <= 0) {
            throw new IllegalArgumentException("El ID de la toma de signos debe ser mayor que 0.");
        }
        try {
            repository.actualizarTomaSignos(tomaSignos);
            System.out.println("Toma de signos actualizada exitosamente.");
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la toma de signos: " + e.getMessage());
        }
    }

    // Validar toma de signos
    private void validarTomaSignos(TomaSignos tomaSignos) {
        if (tomaSignos == null) {
            throw new IllegalArgumentException("La toma de signos no puede ser nula.");
        }
        if (tomaSignos.getFrecuenciaCardiaca() == null || tomaSignos.getFrecuenciaCardiaca().isEmpty()) {
            throw new IllegalArgumentException("La frecuencia cardiaca no puede estar vacía.");
        }
        if (tomaSignos.getTemperatura() == null || tomaSignos.getTemperatura().isEmpty()) {
            throw new IllegalArgumentException("La temperatura no puede estar vacía.");
        }
        if (tomaSignos.getPresionArterial() == null || tomaSignos.getPresionArterial().isEmpty()) {
            throw new IllegalArgumentException("La presión arterial no puede estar vacía.");
        }
        if (tomaSignos.getFechaHora() == null || tomaSignos.getFechaHora().isEmpty()) {
            throw new IllegalArgumentException("La fecha y hora no pueden estar vacías.");
        }
    }

    // Obtener signos por paciente
    public List<TomaSignos> obtenerSignosPorPaciente(String nombrePaciente) {
        if (nombrePaciente == null || nombrePaciente.isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente no puede estar vacío.");
        }
        try {
            return repository.obtenerPorPaciente(nombrePaciente); // Llamar al repositorio
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los signos vitales para el paciente: " + e.getMessage());
        }
    }
    
    public List<String> obtenerTodosLosPacientes() {
        try {
            return repository.obtenerTodosLosPacientes();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de pacientes: " + e.getMessage());
        }
    }
}
