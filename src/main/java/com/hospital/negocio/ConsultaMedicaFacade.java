package com.hospital.negocio;

import com.hospital.modelos.ConsultaMedica;
import com.hospital.repositorios.ConsultaMedicaRepository;
import com.hospital.repositorios.ConsultaMedicaRepositoryMySQL;

import java.time.LocalDate;
import java.util.List;

public class ConsultaMedicaFacade {
    private final ConsultaMedicaRepository repository;

    // Constructor que permite inyectar el repositorio
    public ConsultaMedicaFacade(ConsultaMedicaRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo.");
        }
        this.repository = repository;
    }
    public List<ConsultaMedica> buscarPorEnfermedad(String enfermedad) {
        if (enfermedad == null || enfermedad.isEmpty()) {
            throw new IllegalArgumentException("La enfermedad no puede estar vacía.");
        }
        return repository.buscarPorEnfermedad(enfermedad);
    }
    
    // Constructor sin parámetros (usa una implementación predeterminada)
    public ConsultaMedicaFacade() {
        this.repository = new ConsultaMedicaRepositoryMySQL();
    }

    public List<ConsultaMedica> obtenerConsultasPorPaciente(int idPaciente) {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser un número positivo.");
        }
        try {
            return repository.obtenerPorPaciente(idPaciente);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener consultas para el paciente con ID: " + idPaciente, e);
        }
    }

    public List<ConsultaMedica> obtenerConsultasPorFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        try {
            return repository.obtenerPorFecha(fecha);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener consultas para la fecha: " + fecha, e);
        }
    }
    public List<ConsultaMedica> listarTodas() {
        try {
            return repository.listarTodas();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar todas las consultas médicas.", e);
        }
    }
}
