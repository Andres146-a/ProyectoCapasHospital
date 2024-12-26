package com.hospital.repositorios;

import com.hospital.modelos.Antecedente;
import com.hospital.modelos.Paciente;
import com.hospital.utilidades.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AntecedentesRepositoryMySQL implements AntecedentesRepository {

    @Override
    public void guardarAntecedente(Antecedente antecedente) {
        String query = "INSERT INTO antecedentes (id_paciente, tipo, descripcion) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, antecedente.getIdPaciente());
            stmt.setString(2, antecedente.getTipo());
            stmt.setString(3, antecedente.getDescripcion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el antecedente: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void actualizarAntecedente(Antecedente antecedente) {
        String query = "UPDATE antecedentes SET tipo = ?, descripcion = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, antecedente.getTipo());
            stmt.setString(2, antecedente.getDescripcion());
            stmt.setInt(3, antecedente.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el antecedente: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarAntecedente(int idAntecedente) {
        String query = "DELETE FROM antecedentes WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idAntecedente);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el antecedente: " + e.getMessage(), e);
        }
    }

    // @Override
    // public List<Antecedente> listarAntecedentesPorPaciente(int idPaciente) {
    //     List<Antecedente> antecedentes = new ArrayList<>();
    //     String query = "SELECT id, tipo, descripcion FROM antecedentes WHERE id_paciente = ?";
    //     try (Connection connection = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = connection.prepareStatement(query)) {

    //         stmt.setInt(1, idPaciente);
    //         ResultSet rs = stmt.executeQuery();

    //         while (rs.next()) {
    //             Antecedente antecedente = new Antecedente(
    //                 rs.getInt("id"),
    //                 idPaciente,
    //                 rs.getString("tipo"),
    //                 rs.getString("descripcion")
    //             );
    //             antecedentes.add(antecedente);
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException("Error al listar los antecedentes del paciente: " + e.getMessage(), e);
    //     }
    //     return antecedentes;
    // }
    // private void listarAntecedentes(int idPaciente) {
    //     try {
    //         List<Antecedente> antecedentes = antecedentesFacade.listarAntecedentesPorPaciente(idPaciente);
    //         antecedentes.forEach(antecedente -> {
    //             System.out.println("Antecedente: " + antecedente.getDescripcion());
    //             // Aquí puedes añadir la lógica para mostrar en la interfaz (TableView, TextArea, etc.)
    //         });
    //     } catch (Exception e) {
    //         mostrarAlertaError("Error al listar los antecedentes: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }
    @Override
    public List<Antecedente> listarAntecedentesPorPaciente(int idPaciente) {
        List<Antecedente> antecedentes = new ArrayList<>();
        String query = "SELECT id, tipo, descripcion FROM antecedentes WHERE id_paciente = ?";
        
        System.out.println("Ejecutando consulta para antecedentes: " + query);
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, idPaciente);
            System.out.println("ID del paciente: " + idPaciente);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Antecedente antecedente = new Antecedente(
                    rs.getInt("id"),
                    idPaciente,
                    rs.getString("tipo"),
                    rs.getString("descripcion")
                );
                antecedentes.add(antecedente);
                System.out.println("Antecedente encontrado: " + antecedente.getDescripcion());
            }
        } catch (SQLException e) {
            System.err.println("Error en listarAntecedentesPorPaciente: " + e.getMessage());
            e.printStackTrace();
        }
        return antecedentes;
    }
    
    public List<Paciente> listarPacientesConAntecedentes() {
        String query = "SELECT DISTINCT p.*, " +
                       "a.descripcion AS antecedentes " +
                       "FROM pacientes p " +
                       "JOIN antecedentes a ON p.id = a.id_paciente";
        List<Paciente> pacientes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                Paciente paciente = new Paciente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("edad"),
                    rs.getString("enfermedad"),
                    rs.getString("frecuencia_cardiaca"),
                    rs.getString("presion_arterial"),
                    rs.getString("temperatura"),
                    rs.getString("alerta"),
                    rs.getString("antecedentes"), // Este es el campo agregado
                    rs.getBoolean("activo")
                );
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pacientes con antecedentes: " + e.getMessage(), e);
        }
        return pacientes;
    }
    

    
}
