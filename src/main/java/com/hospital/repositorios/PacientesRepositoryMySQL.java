package com.hospital.repositorios;

import com.hospital.modelos.Paciente;
import com.hospital.utilidades.DataBaseConnectiones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacientesRepositoryMySQL implements PacientesRepository {

    @Override
    public void guardarPaciente(Paciente paciente) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "INSERT INTO pacientes (nombre, apellido, edad) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setInt(3, paciente.getEdad());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el paciente: " + e.getMessage());
        }
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "UPDATE pacientes SET nombre = ?, apellido = ?, edad = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setInt(3, paciente.getEdad());
            stmt.setInt(4, paciente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el paciente: " + e.getMessage());
        }
    }

    @Override
    public void eliminarPaciente(int idPaciente) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "DELETE FROM pacientes WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idPaciente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el paciente: " + e.getMessage());
        }
    }

    @Override
    public Paciente buscarPorId(int idPaciente) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "SELECT * FROM pacientes WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Paciente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("edad")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el paciente: " + e.getMessage());
        }
        return null; // Si no se encuentra ningún paciente
    }

    @Override
    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "SELECT * FROM pacientes";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente paciente = new Paciente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("edad")
                );
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los pacientes: " + e.getMessage());
        }
        return pacientes;
    }
}
