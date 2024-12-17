package com.hospital.repositorios;

import com.hospital.modelos.CentroMedico;
import com.hospital.utilidades.DataBaseConnectiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CentroMedicoRepositoryMySQL implements CentroMedicoRepository {

    @Override
    public void guardarCentroMedico(CentroMedico centroMedico) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "INSERT INTO centro_medico (nombre, direccion) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, centroMedico.getNombre());
            stmt.setString(2, centroMedico.getDireccion());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el centro médico: " + e.getMessage());
        }
    }

    @Override
    public void actualizarCentroMedico(CentroMedico centroMedico) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "UPDATE centro_medico SET nombre = ?, direccion = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, centroMedico.getNombre());
            stmt.setString(2, centroMedico.getDireccion());
            stmt.setInt(3, centroMedico.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el centro médico: " + e.getMessage());
        }
    }

    @Override
    public void eliminarCentroMedico(int idCentroMedico) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "DELETE FROM centro_medico WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idCentroMedico);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el centro médico: " + e.getMessage());
        }
    }

    @Override
    public CentroMedico buscarPorId(int idCentroMedico) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "SELECT * FROM centro_medico WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idCentroMedico);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CentroMedico(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("direccion")
                );
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el centro médico: " + e.getMessage());
        }
    }

    @Override
    public List<CentroMedico> listarTodos() {
        List<CentroMedico> centros = new ArrayList<>();
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "SELECT * FROM centro_medico";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                centros.add(new CentroMedico(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("direccion")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los centros médicos: " + e.getMessage());
        }
        return centros;
    }
}
