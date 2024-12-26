package com.hospital.repositorios;

import com.hospital.modelos.TomaSignos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.hospital.utilidades.DatabaseConnection;

public class TomaSignosRepositoryMySQL implements TomaSignosRepository {

    @Override
    public void guardarTomaSignos(TomaSignos tomaSignos) {
        throw new UnsupportedOperationException("Use guardarTomaSignos(TomaSignos, String nombrePaciente) instead.");
    }

    public void guardarTomaSignos(TomaSignos tomaSignos, String nombrePaciente) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Validaciones antes de interactuar con la base de datos
            if (tomaSignos.getFrecuenciaRespiratoria() == null || tomaSignos.getFrecuenciaRespiratoria().isEmpty()) {
                throw new RuntimeException("La frecuencia respiratoria no puede ser nula o vacía.");
            }
            if (tomaSignos.getFrecuenciaCardiaca() == null || tomaSignos.getFrecuenciaCardiaca().isEmpty()) {
                throw new RuntimeException("La frecuencia cardíaca no puede ser nula o vacía.");
            }
            if (tomaSignos.getPresionArterial() == null || tomaSignos.getPresionArterial().isEmpty()) {
                throw new RuntimeException("La presión arterial no puede ser nula o vacía.");
            }
            if (tomaSignos.getTemperatura() == null || tomaSignos.getTemperatura().isEmpty()) {
                throw new RuntimeException("La temperatura no puede ser nula o vacía.");
            }
            if (tomaSignos.getOxigenacion() == null || tomaSignos.getOxigenacion().isEmpty()) {
                throw new RuntimeException("La oxigenación no puede ser nula o vacía.");
            }
            if (tomaSignos.getFechaHora() == null || tomaSignos.getFechaHora().isEmpty()) {
                throw new RuntimeException("La fecha y hora no puede ser nula o vacía.");
            }
    
            // Obtener el ID del paciente a partir del nombre
            String queryPaciente = "SELECT id FROM pacientes WHERE nombre = ?";
            PreparedStatement stmtPaciente = connection.prepareStatement(queryPaciente);
            stmtPaciente.setString(1, nombrePaciente);
            ResultSet rsPaciente = stmtPaciente.executeQuery();
    
            if (!rsPaciente.next()) {
                throw new RuntimeException("Paciente no encontrado: " + nombrePaciente);
            }
    
            int idPaciente = rsPaciente.getInt("id");
    
            // Verificar si ya existe un registro para este paciente
            String verificarExistencia = "SELECT COUNT(*) FROM signos_vitales WHERE id_paciente = ?";
            PreparedStatement stmtVerificar = connection.prepareStatement(verificarExistencia);
            stmtVerificar.setInt(1, idPaciente);
            ResultSet rsVerificar = stmtVerificar.executeQuery();
    
            rsVerificar.next();
            if (rsVerificar.getInt(1) > 0) {
                throw new RuntimeException("Ya existe un registro de signos vitales para este paciente.");
            }
    
            // Insertar los datos en la tabla signos_vitales
            String query = "INSERT INTO signos_vitales (id_paciente, frecuencia_cardiaca, presion_arterial, temperatura, frecuencia_respiratoria, oxigenacion, fecha_registro) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idPaciente);
            stmt.setString(2, tomaSignos.getFrecuenciaCardiaca());
            stmt.setString(3, tomaSignos.getPresionArterial());
            stmt.setString(4, tomaSignos.getTemperatura());
            stmt.setString(5, tomaSignos.getFrecuenciaRespiratoria());
            stmt.setString(6, tomaSignos.getOxigenacion());
            stmt.setString(7, tomaSignos.getFechaHora());
            stmt.executeUpdate();
    
            System.out.println("Toma de signos guardada exitosamente.");
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la toma de signos: " + e.getMessage());
        }
    }
    
    @Override
    public List<TomaSignos> listarTodos() {
        List<TomaSignos> listaSignos = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM signos_vitales";

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TomaSignos tomaSignos = new TomaSignos(
                    rs.getInt("id"),
                    rs.getString("frecuencia_cardiaca") != null ? rs.getString("frecuencia_cardiaca") : "Sin datos",
                    rs.getString("presion_arterial") != null ? rs.getString("presion_arterial") : "Sin datos",
                    rs.getString("temperatura") != null ? rs.getString("temperatura") : "Sin datos",
                    rs.getString("frecuencia_respiratoria") != null ? rs.getString("frecuencia_respiratoria") : "Sin datos",
                    rs.getString("oxigenacion") != null ? rs.getString("oxigenacion") : "Sin datos",
                    rs.getString("fecha_registro")
                );
                
                listaSignos.add(tomaSignos);
            }
            System.out.println("Tomas de signos listadas exitosamente.");
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las tomas de signos: " + e.getMessage());
        }
        return listaSignos;
    }

    @Override
    public TomaSignos buscarPorId(int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM signos_vitales WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TomaSignos(
                    rs.getInt("id"),
                    rs.getString("frecuencia_cardiaca"),
                    rs.getString("presion_arterial"),
                    rs.getString("temperatura"),
                    rs.getString("frecuencia_respiratoria"),
                    rs.getString("oxigenacion"),
                    rs.getString("fecha_registro")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar la toma de signos por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void actualizarTomaSignos(TomaSignos tomaSignos) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE signos_vitales SET frecuencia_cardiaca = ?, presion_arterial = ?, temperatura = ?, " +
                           "frecuencia_respiratoria = ?, oxigenacion = ?, fecha_registro = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, tomaSignos.getFrecuenciaCardiaca());
            stmt.setString(2, tomaSignos.getPresionArterial());
            stmt.setString(3, tomaSignos.getTemperatura());
            stmt.setString(4, tomaSignos.getFrecuenciaRespiratoria());
            stmt.setString(5, tomaSignos.getOxigenacion());
            stmt.setString(6, tomaSignos.getFechaHora());
            stmt.setInt(7, tomaSignos.getId());
            stmt.executeUpdate();
            System.out.println("Toma de signos actualizada exitosamente.");
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la toma de signos: " + e.getMessage());
        }
    }

    @Override
    public void eliminarPorId(int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM signos_vitales WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Toma de signos eliminada exitosamente.");
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la toma de signos: " + e.getMessage());
        }
    }

    @Override
    public List<TomaSignos> obtenerPorPaciente(String nombrePaciente) {
        List<TomaSignos> listaSignos = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT sv.* FROM signos_vitales sv " +
                           "JOIN pacientes p ON sv.id_paciente = p.id " +
                           "WHERE p.nombre = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nombrePaciente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TomaSignos tomaSignos = new TomaSignos(
                    rs.getInt("id"),
                    rs.getString("frecuencia_cardiaca"),
                    rs.getString("presion_arterial"),
                    rs.getString("temperatura"),
                    rs.getString("frecuencia_respiratoria"),
                    rs.getString("oxigenacion"),
                    rs.getString("fecha_registro")
                );
                listaSignos.add(tomaSignos);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las tomas de signos por paciente: " + e.getMessage());
        }
        return listaSignos;
    }

    @Override
    public List<String> obtenerTodosLosPacientes() {
        List<String> listaPacientes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT nombre FROM pacientes";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listaPacientes.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de pacientes: " + e.getMessage());
        }
        return listaPacientes;
    }
}
