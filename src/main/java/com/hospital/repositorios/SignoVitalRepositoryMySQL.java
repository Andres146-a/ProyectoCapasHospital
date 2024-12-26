package com.hospital.repositorios;

import com.hospital.modelos.SignoVital;

import com.hospital.utilidades.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SignoVitalRepositoryMySQL implements SignoVitalRepository {

    @Override
    public void guardarSignoVital(SignoVital signoVital) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO signos_vitales (nombre, valor_normal) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, signoVital.getNombre());
            stmt.setString(2, signoVital.getValorNormal());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el signo vital: " + e.getMessage());
        }
    }

    @Override
    public void actualizarSignoVital(SignoVital signoVital) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE signos_vitales SET nombre = ?, valor_normal = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, signoVital.getNombre());
            stmt.setString(2, signoVital.getValorNormal());
            stmt.setInt(3, signoVital.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el signo vital: " + e.getMessage());
        }
    }
    @Override
public void cambiarEstado(int idPaciente, boolean activo) {
    try (Connection connection = DatabaseConnection.getConnection()) {
        String query = "UPDATE pacientes SET activo = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setBoolean(1, activo);
        stmt.setInt(2, idPaciente);
        stmt.executeUpdate();
        System.out.println("Estado del paciente actualizado exitosamente.");
    } catch (Exception e) {
        throw new RuntimeException("Error al actualizar el estado del paciente: " + e.getMessage());
    }
}

    @Override
    public void eliminarSignoVital(int idSignoVital) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM signos_vitales WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idSignoVital);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el signo vital: " + e.getMessage());
        }
    }

    @Override
    public SignoVital buscarPorId(int idSignoVital) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM signos_vitales WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idSignoVital);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new SignoVital(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("valor_normal")
                );
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el signo vital: " + e.getMessage());
        }
    }

    @Override
    public List<SignoVital> listarTodos() {
        List<SignoVital> signos = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM signos_vitales";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                signos.add(new SignoVital(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("valor_normal")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar signos vitales: " + e.getMessage());
        }
        return signos;
    }
}
