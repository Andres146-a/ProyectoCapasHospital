package com.hospital.repositorios;

import com.hospital.modelos.Enfermero;
import com.hospital.utilidades.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnfermerosRepositoryMySQL implements EnfermerosRepository {

    @Override
    public void guardarEnfermero(Enfermero enfermero) {
        String query = "INSERT INTO enfermeros (nombre, especialidad, estado) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, enfermero.getNombre());
            stmt.setString(2, enfermero.getEspecialidad());
            stmt.setString(3, enfermero.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el enfermero: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarEnfermero(Enfermero enfermero) {
        String query = "UPDATE enfermeros SET nombre = ?, especialidad = ?, estado = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, enfermero.getNombre());
            stmt.setString(2, enfermero.getEspecialidad());
            stmt.setString(3, enfermero.getEstado());
            stmt.setInt(4, enfermero.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el enfermero: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarEnfermero(int idEnfermero) {
        String query = "DELETE FROM enfermeros WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEnfermero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el enfermero: " + e.getMessage(), e);
        }
    }

    @Override
    public Enfermero buscarPorId(int idEnfermero) {
        String query = "SELECT * FROM enfermeros WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEnfermero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Enfermero(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el enfermero por ID: " + e.getMessage(), e);
        }
        return null; // Devuelve null si no se encuentra el enfermero
    }

    @Override
    public List<Enfermero> listarTodos() {
        List<Enfermero> enfermeros = new ArrayList<>();
        String query = "SELECT * FROM enfermeros";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Enfermero enfermero = new Enfermero(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    rs.getString("estado")
                );
                enfermeros.add(enfermero);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los enfermeros: " + e.getMessage(), e);
        }
        return enfermeros;
    }

    @Override
    public void actualizarEstado(int idEnfermero, String nuevoEstado) {
        String query = "UPDATE enfermeros SET estado = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idEnfermero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado del enfermero: " + e.getMessage(), e);
        }
    }
}
