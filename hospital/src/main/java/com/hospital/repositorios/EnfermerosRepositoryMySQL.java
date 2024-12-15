package com.hospital.repositorios;

import com.hospital.modelos.Enfermero;
import com.hospital.utilidades.DataBaseConnectiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EnfermerosRepositoryMySQL implements EnfermerosRepository {

    @Override
    public void guardarEnfermero(Enfermero enfermero) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "INSERT INTO enfermeros (nombre, especialidad) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, enfermero.getNombre());
            stmt.setString(2, enfermero.getEspecialidad());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el enfermero: " + e.getMessage());
        }
    }

    @Override
    public void actualizarEnfermero(Enfermero enfermero) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "UPDATE enfermeros SET nombre = ?, especialidad = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, enfermero.getNombre());
            stmt.setString(2, enfermero.getEspecialidad());
            stmt.setInt(3, enfermero.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el enfermero: " + e.getMessage());
        }
    }

    @Override
    public void eliminarEnfermero(int idEnfermero) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "DELETE FROM enfermeros WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idEnfermero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el enfermero: " + e.getMessage());
        }
    }

    @Override
    public Enfermero buscarPorId(int idEnfermero) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "SELECT * FROM enfermeros WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idEnfermero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Enfermero(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("especialidad")
                );
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el enfermero: " + e.getMessage());
        }
    }

    @Override
    public List<Enfermero> listarTodos() {
        List<Enfermero> enfermeros = new ArrayList<>();
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "SELECT * FROM enfermeros";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                enfermeros.add(new Enfermero(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("especialidad")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar enfermeros: " + e.getMessage());
        }
        return enfermeros;
    }
}
