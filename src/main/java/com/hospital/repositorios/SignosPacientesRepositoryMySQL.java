package com.hospital.repositorios;

import com.hospital.modelos.SignosPaciente;
import com.hospital.utilidades.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SignosPacientesRepositoryMySQL implements SignosPacientesRepository {

    @Override
    public void guardarSignosPaciente(SignosPaciente signosPaciente) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO signos_pacientes (id_paciente, temperatura, presion) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, signosPaciente.getIdPaciente());
            stmt.setDouble(2, signosPaciente.getTemperatura());
            stmt.setString(3, signosPaciente.getPresion());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar los signos del paciente: " + e.getMessage());
        }
    }

    @Override
    public void actualizarSignosPaciente(SignosPaciente signosPaciente) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE signos_pacientes SET temperatura = ?, presion = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, signosPaciente.getTemperatura());
            stmt.setString(2, signosPaciente.getPresion());
            stmt.setInt(3, signosPaciente.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar los signos del paciente: " + e.getMessage());
        }
    }

    @Override
    public void eliminarSignosPaciente(int idSignosPaciente) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM signos_pacientes WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idSignosPaciente);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar los signos del paciente: " + e.getMessage());
        }
    }

    @Override
    public SignosPaciente buscarPorId(int idSignosPaciente) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM signos_pacientes WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idSignosPaciente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new SignosPaciente(
                    rs.getInt("id"),
                    rs.getInt("id_paciente"),
                    rs.getDouble("temperatura"),
                    rs.getString("presion")
                );
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar los signos del paciente: " + e.getMessage());
        }
    }

    @Override
    public List<SignosPaciente> listarTodos() {
        List<SignosPaciente> signos = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM signos_pacientes";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                signos.add(new SignosPaciente(
                    rs.getInt("id"),
                    rs.getInt("id_paciente"),
                    rs.getDouble("temperatura"),
                    rs.getString("presion")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los signos del paciente: " + e.getMessage());
        }
        return signos;
    }
}
