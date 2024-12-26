package com.hospital.negocio;

import com.hospital.modelos.ConsultaMedica;
import com.hospital.repositorios.ConsultaMedicaRepository;
import com.hospital.repositorios.ConsultaMedicaRepositoryMySQL;

import java.util.List;

public class ConsultasFacade {

    private final ConsultaMedicaRepository repository;

    // Constructor por defecto
    public ConsultasFacade() {
        this.repository = new ConsultaMedicaRepositoryMySQL();
    }

    // Guardar una consulta médica
    public void guardarConsulta(ConsultaMedica consulta) {
        if (consulta.getPaciente() == null || consulta.getDoctor() == null || consulta.getFecha() == null) {
            throw new IllegalArgumentException("La consulta debe tener paciente, doctor y fecha válidos.");
        }
        repository.guardarConsulta(consulta);
    }

    // Actualizar una consulta médica
    public void actualizarConsulta(ConsultaMedica consulta) {
        if (consulta.getId() <= 0) {
            throw new IllegalArgumentException("El ID de la consulta no es válido.");
        }
        repository.actualizarConsulta(consulta);
    }

    // Eliminar una consulta médica por ID
    public void eliminarConsulta(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID no es válido.");
        }
        repository.eliminarPorId(id);
    }
    public List<ConsultaMedica> buscarPorEnfermedad(String enfermedad) {
        if (enfermedad == null || enfermedad.isEmpty()) {
            throw new IllegalArgumentException("La enfermedad no puede estar vacía.");
        }
        try {
            return repository.buscarPorEnfermedad(enfermedad);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar consultas por enfermedad: " + e.getMessage(), e);
        }
    }
    
    
    // Listar todas las consultas médicas
    public List<ConsultaMedica> listarConsultas() {
        return repository.listarTodas();
    }

    // Buscar consulta por ID
    public ConsultaMedica buscarConsultaPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID no es válido.");
        }
        return repository.buscarPorId(id);
    }
}
