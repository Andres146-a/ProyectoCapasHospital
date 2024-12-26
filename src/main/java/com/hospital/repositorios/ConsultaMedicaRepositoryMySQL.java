package com.hospital.repositorios;

import com.hospital.modelos.ConsultaMedica;
import com.hospital.modelos.Paciente;
import com.hospital.utilidades.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsultaMedicaRepositoryMySQL implements ConsultaMedicaRepository {

    // @Override
    // public void guardarConsulta(ConsultaMedica consultaMedica) {
    //     String query = "INSERT INTO consulta_medica (idPaciente, idDoctor, fecha, descripcion, diagnostico) VALUES (?, ?, ?, ?, ?)";
    //     try (Connection connection = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = connection.prepareStatement(query)) {
    //         stmt.setInt(1, consultaMedica.getPaciente().getId());
    //         stmt.setString(2, consultaMedica.getDoctor());
    //         stmt.setDate(3, java.sql.Date.valueOf(consultaMedica.getFecha()));
    //         stmt.setString(4, consultaMedica.getDescripcion());
    //         stmt.setString(5, consultaMedica.getDiagnostico());
    //         stmt.executeUpdate();
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error al guardar la consulta médica: " + e.getMessage(), e);
    //     }
    // }
    @Override
    public void guardarConsulta(ConsultaMedica consultaMedica) {
        String query = "INSERT INTO consulta_medica (paciente_id, doctor, fecha, descripcion, diagnostico) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, consultaMedica.getPaciente().getId());
            stmt.setString(2, consultaMedica.getDoctor());
            stmt.setDate(3, java.sql.Date.valueOf(consultaMedica.getFecha()));
            stmt.setString(4, consultaMedica.getDescripcion());
            stmt.setString(5, consultaMedica.getDiagnostico());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la consulta médica: " + e.getMessage(), e);
        }
    }

    // @Override
    // public List<ConsultaMedica> listarTodas() {
    //     List<ConsultaMedica> consultas = new ArrayList<>();
    //     String query = "SELECT " +
    //         "p.id AS paciente_id, " +
    //         "p.nombre AS paciente_nombre, " +
    //         "p.apellido AS paciente_apellido, " +
    //         "p.edad AS paciente_edad, " +
    //         "p.enfermedad AS paciente_enfermedad, " +
    //         "p.frecuencia_cardiaca AS paciente_frecuencia_cardiaca, " +
    //         "p.presion_arterial AS paciente_presion_arterial, " +
    //         "p.temperatura AS paciente_temperatura, " +
    //         "p.antecedentes AS paciente_antecedentes, " +
    //         "c.id AS consulta_id, " +
    //         "c.fecha, " +
    //         "c.doctor, " +
    //         "c.descripcion, " +
    //         "c.diagnostico " +
    //         "FROM consulta_medica c " +
    //         "JOIN pacientes p ON c.paciente_id = p.id;";
        
    //     try (Connection connection = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = connection.prepareStatement(query);
    //          ResultSet rs = stmt.executeQuery()) {
            
    //         while (rs.next()) {
    //             // Crear un objeto Paciente desde los datos obtenidos
    //             Paciente paciente = new Paciente(
    //                 rs.getInt("paciente_id"),
    //                 rs.getString("paciente_nombre"),
    //                 rs.getString("paciente_apellido"),
    //                 rs.getInt("paciente_edad"),
    //                 rs.getString("paciente_enfermedad"),
    //                 rs.getString("paciente_frecuencia_cardiaca"),
    //                 rs.getString("paciente_presion_arterial"),
    //                 rs.getString("paciente_temperatura"),
    //                 rs.getString("paciente_antecedentes")
    //             );
    
    //             // Crear un objeto ConsultaMedica solo si hay datos de consulta
    //             ConsultaMedica consulta = new ConsultaMedica(
    //                 rs.getInt("consulta_id"),
    //                 paciente,
    //                 rs.getString("doctor") != null ? rs.getString("doctor") : "Doctor no asignado",
    //                 rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : LocalDate.now(),
    //                 rs.getString("descripcion") != null ? rs.getString("descripcion") : "Descripción no disponible",
    //                 rs.getString("diagnostico") != null ? rs.getString("diagnostico") : "Diagnóstico no registrado"
    //             );
    
    //             consultas.add(consulta);
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException("Error al listar todas las consultas médicas: " + e.getMessage(), e);
    //     }
    //     return consultas;
    // }
    @Override
    public List<ConsultaMedica> listarTodas() {
        List<ConsultaMedica> consultas = new ArrayList<>();
        String query = "SELECT " +
                "p.id AS paciente_id, " +
                "p.nombre AS paciente_nombre, " +
                "p.apellido AS paciente_apellido, " +
                "COALESCE(p.edad, 0) AS paciente_edad, " +
                "COALESCE(p.enfermedad, 'Sin información') AS paciente_enfermedad, " +
                "COALESCE(p.frecuencia_cardiaca, 'Sin registro') AS paciente_frecuencia_cardiaca, " +
                "COALESCE(p.presion_arterial, 'Sin registro') AS paciente_presion_arterial, " +
                "COALESCE(p.temperatura, 'Sin registro') AS paciente_temperatura, " +
                "COALESCE(p.antecedentes, 'Sin antecedentes registrados') AS paciente_antecedentes, " +
                "COALESCE(p.alerta, 'Sin alerta') AS paciente_alerta, " +
                "p.activo AS paciente_activo, " +
                "c.id AS consulta_id, " +
                "COALESCE(c.fecha, NOW()) AS fecha, " +
                "COALESCE(c.doctor, 'No asignado') AS doctor, " +
                "COALESCE(c.descripcion, 'Sin descripción') AS descripcion, " +
                "COALESCE(c.diagnostico, 'Sin diagnóstico') AS diagnostico " +
                "FROM consulta_medica c " +
                "RIGHT JOIN pacientes p ON c.paciente_id = p.id";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                Paciente paciente = new Paciente(
                    rs.getInt("paciente_id"),
                    rs.getString("paciente_nombre"),
                    rs.getString("paciente_apellido"),
                    rs.getInt("paciente_edad"),
                    rs.getString("paciente_enfermedad"),
                    rs.getString("paciente_frecuencia_cardiaca"),
                    rs.getString("paciente_presion_arterial"),
                    rs.getString("paciente_temperatura"),
                    rs.getString("paciente_alerta"),
                    rs.getString("paciente_antecedentes"),
                    rs.getBoolean("paciente_activo")
                );
    
                ConsultaMedica consulta = new ConsultaMedica(
                    rs.getInt("consulta_id"),
                    paciente,
                    rs.getString("doctor"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getString("descripcion"),
                    rs.getString("diagnostico")
                );
    
                consultas.add(consulta);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar todas las consultas médicas: " + e.getMessage(), e);
        }
        return consultas;
    }
    
    
    // @Override
    // public ConsultaMedica buscarPorId(int id) {
    //     String query = "SELECT c.*, p.id AS paciente_id, p.nombre AS paciente_nombre, p.apellido AS paciente_apellido " +
    //                    "FROM consulta_medica c " +
    //                    "JOIN pacientes p ON c.paciente_id = p.id " +
    //                    "WHERE c.id = ?";
    //     try (Connection connection = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = connection.prepareStatement(query)) {
    //         stmt.setInt(1, id);
    //         ResultSet rs = stmt.executeQuery();
    //         if (rs.next()) {
    //             Paciente paciente = obtenerPacientePorId(rs.getInt("paciente_id"));
    //             return new ConsultaMedica(
    //                 rs.getInt("id"),
    //                 paciente,
    //                 rs.getString("doctor"),
    //                 rs.getDate("fecha").toLocalDate(),
    //                 rs.getString("descripcion"),
    //                 rs.getString("diagnostico")
    //             );
    //         }
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error al buscar consulta médica por ID: " + id + ". " + e.getMessage(), e);
    //     }
    //     return null;
    // }
    @Override
    public List<ConsultaMedica> buscarPorEnfermedad(String enfermedad) {
        List<ConsultaMedica> consultas = new ArrayList<>();
        String query = "SELECT " +
                       "c.id AS consulta_id, " +
                       "c.fecha, " +
                       "c.doctor, " +
                       "c.descripcion, " +
                       "c.diagnostico, " +
                       "p.id AS paciente_id, " +
                       "p.nombre AS paciente_nombre, " +
                       "p.apellido AS paciente_apellido, " +
                       "p.edad AS paciente_edad, " +
                       "p.enfermedad AS paciente_enfermedad, " +
                       "p.frecuencia_cardiaca, " +
                       "p.presion_arterial, " +
                       "p.temperatura, " +
                       "p.alerta, " +
                       "p.activo AS paciente_activo " +
                       "FROM consulta_medica c " +
                       "JOIN pacientes p ON c.paciente_id = p.id " +
                       "WHERE p.enfermedad LIKE ?";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            stmt.setString(1, "%" + enfermedad + "%");
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Paciente paciente = new Paciente(
                    rs.getInt("paciente_id"),
                    rs.getString("paciente_nombre"),
                    rs.getString("paciente_apellido"),
                    rs.getInt("paciente_edad"),
                    rs.getString("paciente_enfermedad"),
                    rs.getString("frecuencia_cardiaca"),
                    rs.getString("presion_arterial"),
                    rs.getString("temperatura"),
                    rs.getString("alerta"),
                    "Sin antecedentes", // Si es necesario agregar otro campo
                    rs.getBoolean("paciente_activo")
                );
    
                ConsultaMedica consulta = new ConsultaMedica(
                    rs.getInt("consulta_id"),
                    paciente,
                    rs.getString("doctor"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getString("descripcion"),
                    rs.getString("diagnostico")
                );
    
                consultas.add(consulta);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar consultas por enfermedad: " + e.getMessage(), e);
        }
        return consultas;
    }
    

    private Paciente obtenerPacientePorId(int id) throws SQLException {
        String query = "SELECT id, nombre, apellido, edad, enfermedad, frecuencia_cardiaca, presion_arterial, " +
                       "temperatura, alerta, antecedentes, activo FROM pacientes WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                return new Paciente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("edad"),
                    rs.getString("enfermedad"),
                    rs.getString("frecuencia_cardiaca"),
                    rs.getString("presion_arterial"),
                    rs.getString("temperatura"),
                    rs.getString("alerta"),
                    rs.getString("antecedentes"),
                    rs.getBoolean("activo")
                );
            }
        }
        return null;
    }
    
    @Override
    public void actualizarConsulta(ConsultaMedica consultaMedica) {
        String query = "UPDATE consulta_medica SET idPaciente = ?, idDoctor = ?, fecha = ?, descripcion = ?, diagnostico = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, consultaMedica.getPaciente().getId());
            stmt.setString(2, consultaMedica.getDoctor());
            stmt.setDate(3, java.sql.Date.valueOf(consultaMedica.getFecha()));
            stmt.setString(4, consultaMedica.getDescripcion());
            stmt.setString(5, consultaMedica.getDiagnostico());
            stmt.setInt(6, consultaMedica.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la consulta médica: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarPorId(int id) {
        String query = "DELETE FROM consulta_medica WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la consulta médica: " + e.getMessage(), e);
        }
    }

    // @Override
    // public List<ConsultaMedica> obtenerPorPaciente(int idPaciente) {
    //     List<ConsultaMedica> consultas = new ArrayList<>();
    //     String query = "SELECT c.*, p.id AS paciente_id, p.nombre AS paciente_nombre, p.apellido AS paciente_apellido " +
    //                    "FROM consulta_medica c " +
    //                    "JOIN pacientes p ON c.paciente_id = p.id " +
    //                    "WHERE p.id = ?";
    //     try (Connection connection = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = connection.prepareStatement(query)) {
    //         stmt.setInt(1, idPaciente);
    //         ResultSet rs = stmt.executeQuery();
    //         while (rs.next()) {
    //             Paciente paciente = obtenerPacientePorId(rs.getInt("paciente_id"));
    //             ConsultaMedica consulta = new ConsultaMedica(
    //                 rs.getInt("id"),
    //                 paciente,
    //                 rs.getString("doctor"),
    //                 rs.getDate("fecha").toLocalDate(),
    //                 rs.getString("descripcion"),
    //                 rs.getString("diagnostico")
    //             );
    //             consultas.add(consulta);
    //         }
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error al obtener consultas por paciente: " + e.getMessage(), e);
    //     }
    //     return consultas;
    // }

    // @Override
    // public List<ConsultaMedica> obtenerPorFecha(LocalDate fecha) {
    //     List<ConsultaMedica> consultas = new ArrayList<>();
    //     String query = "SELECT * FROM consulta_medica WHERE fecha = ?";
    //     try (Connection connection = DatabaseConnection.getConnection();
    //          PreparedStatement stmt = connection.prepareStatement(query)) {
    //         stmt.setDate(1, java.sql.Date.valueOf(fecha));
    //         ResultSet rs = stmt.executeQuery();
    //         while (rs.next()) {
    //             ConsultaMedica consulta = new ConsultaMedica(
    //                 rs.getInt("id"),
    //                 obtenerPacientePorId(rs.getInt("paciente_id")),
    //                 rs.getString("doctor"),
    //                 rs.getDate("fecha").toLocalDate(),
    //                 rs.getString("descripcion"),
    //                 rs.getString("diagnostico")
    //             );
    //             consultas.add(consulta);
    //         }
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error al obtener consultas por fecha: " + e.getMessage(), e);
    //     }
    //     return consultas;
    // }
    private Paciente obtenerPacienteDesdeResultSet(ResultSet rs) throws SQLException {
        return new Paciente(
            rs.getInt("paciente_id"),
            rs.getString("paciente_nombre"),
            rs.getString("paciente_apellido"),
            rs.getInt("paciente_edad"),
            rs.getString("paciente_enfermedad"),
            rs.getString("paciente_frecuencia_cardiaca"),
            rs.getString("paciente_presion_arterial"),
            rs.getString("paciente_temperatura"),
            rs.getString("paciente_alerta"),
            rs.getString("paciente_antecedentes"),
            rs.getBoolean("paciente_activo")
        );
    }
    

    @Override
    public ConsultaMedica buscarPorId(int id) {
        String query = "SELECT c.*, p.id AS paciente_id, p.nombre AS paciente_nombre, p.apellido AS paciente_apellido " +
                "FROM consulta_medica c " +
                "JOIN pacientes p ON c.paciente_id = p.id " +
                "WHERE c.id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Paciente paciente = obtenerPacienteDesdeResultSet(rs);
                return new ConsultaMedica(
                        rs.getInt("id"),
                        paciente,
                        rs.getString("doctor"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("descripcion"),
                        rs.getString("diagnostico")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar consulta médica por ID: " + e.getMessage(), e);
        }
        return null;
    }
    @Override
    public List<ConsultaMedica> obtenerPorPaciente(int idPaciente) {
        List<ConsultaMedica> consultas = new ArrayList<>();
        String query = "SELECT c.*, p.id AS paciente_id, p.nombre AS paciente_nombre, p.apellido AS paciente_apellido " +
                "FROM consulta_medica c " +
                "JOIN pacientes p ON c.paciente_id = p.id " +
                "WHERE p.id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente paciente = obtenerPacienteDesdeResultSet(rs);
                ConsultaMedica consulta = new ConsultaMedica(
                        rs.getInt("id"),
                        paciente,
                        rs.getString("doctor"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("descripcion"),
                        rs.getString("diagnostico")
                );
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener consultas por paciente: " + e.getMessage(), e);
        }
        return consultas;
    }
    @Override
    public List<ConsultaMedica> obtenerPorFecha(LocalDate fecha) {
        List<ConsultaMedica> consultas = new ArrayList<>();
        String query = "SELECT * FROM consulta_medica WHERE fecha = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente paciente = obtenerPacientePorId(rs.getInt("paciente_id"));
                ConsultaMedica consulta = new ConsultaMedica(
                        rs.getInt("id"),
                        paciente,
                        rs.getString("doctor"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("descripcion"),
                        rs.getString("diagnostico")
                );
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener consultas por fecha: " + e.getMessage(), e);
        }
        return consultas;
    }
}
