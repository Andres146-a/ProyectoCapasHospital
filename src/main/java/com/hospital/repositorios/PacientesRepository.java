package com.hospital.repositorios;

import com.hospital.modelos.Paciente;
import com.hospital.modelos.TomaSignos;

import java.util.List;

public interface PacientesRepository {
    int guardarPaciente(Paciente paciente); // Insertar nuevo
    void actualizarPaciente(Paciente paciente); // Actualizar existente
    void eliminarPaciente(int idPaciente); // Eliminar por ID
    Paciente buscarPorId(int idPaciente); // Buscar por ID
    List<Paciente> listarTodos(); // Listar todos
    List<Paciente> listarActivos(); // Listar solo los activos
    void cambiarEstado(int idPaciente, boolean activo); // Cambiar el estado de un paciente
    List<Paciente> listarInactivos(); // Listar solo los inactivos
    List<Paciente> obtenerTodosLosPacientes();
    void actualizarAlerta(int idPaciente, String alerta); // Método para actualizar la alerta
    List<Paciente> buscarPorEnfermedad(String enfermedad);
 List<TomaSignos> obtenerSignosVitales(int pacienteId);
 List<Paciente> listarPacientesConAntecedentes();
}
