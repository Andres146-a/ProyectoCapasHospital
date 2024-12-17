package com.hospital.repositorios;

import com.hospital.modelos.Paciente;
import com.hospital.utilidades.DataBaseConnectiones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface PacientesRepository {
    void guardarPaciente(Paciente paciente); 
    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(int idPaciente);
    Paciente buscarPorId(int idPaciente);
    List<Paciente> listarTodos();
}