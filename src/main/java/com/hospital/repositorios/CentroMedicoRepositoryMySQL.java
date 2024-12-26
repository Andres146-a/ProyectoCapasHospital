// package com.hospital.repositorios;

// import com.hospital.modelos.ConsultaMedica;
// import com.hospital.modelos.Paciente;
// import com.hospital.utilidades.DatabaseConnection;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// public class ConsultaMedicaRepositoryMySQL implements ConsultaMedicaRepository {

//     @Override
//     public void guardarConsulta(ConsultaMedica consulta) {
//         try (Connection connection = DatabaseConnection.getConnection()) {
//             String query = "INSERT INTO consultas_medicas (id_paciente, doctor, fecha, descripcion, diagnostico) VALUES (?, ?, ?, ?, ?)";
//             PreparedStatement stmt = connection.prepareStatement(query);
//             stmt.setInt(1, consulta.getPaciente().getId());
//             stmt.setString(2, consulta.getDoctor());
//             stmt.setDate(3, java.sql.Date.valueOf(consulta.getFecha()));
//             stmt.setString(4, consulta.getDescripcion());
//             stmt.setString(5, consulta.getDiagnostico());
//             stmt.executeUpdate();
//         } catch (Exception e) {
//             throw new RuntimeException("Error al guardar la consulta médica: " + e.getMessage());
//         }
//     }

//     public List<ConsultaMedica> obtenerPorPaciente(int idPaciente) {
//         List<ConsultaMedica> consultas = new ArrayList<>();
//         try (Connection connection = DatabaseConnection.getConnection()) {
//             String query = "SELECT c.id, c.doctor, c.fecha, c.descripcion, c.diagnostico, " +
//                            "p.id AS paciente_id, p.nombre, p.apellido, p.edad, p.enfermedad " +
//                            "FROM consultas_medicas c " +
//                            "JOIN pacientes p ON c.id_paciente = p.id " +
//                            "WHERE p.id = ?";
//             PreparedStatement stmt = connection.prepareStatement(query);
//             stmt.setInt(1, idPaciente);
//             ResultSet rs = stmt.executeQuery();
//             while (rs.next()) {
//                 Paciente paciente = new Paciente(
//                         rs.getInt("paciente_id"),
//                         rs.getString("nombre"),
//                         rs.getString("apellido"),
//                         rs.getInt("edad"),
//                         rs.getString("enfermedad"),
//                         null, null, null, null
//                 );
//                 ConsultaMedica consulta = new ConsultaMedica(
//                         rs.getInt("id"),
//                         paciente,
//                         rs.getString("doctor"),
//                         rs.getDate("fecha").toLocalDate(),
//                         rs.getString("descripcion"),
//                         rs.getString("diagnostico")
//                 );
//                 consultas.add(consulta);
//             }
//         } catch (Exception e) {
//             throw new RuntimeException("Error al obtener consultas médicas por paciente: " + e.getMessage());
//         }
//         return consultas;
//     }

//     @Override
//     public List<ConsultaMedica> obtenerPorFecha(LocalDate fecha) {
//         List<ConsultaMedica> consultas = new ArrayList<>();
//         try (Connection connection = DatabaseConnection.getConnection()) {
//             String query = "SELECT c.id, c.doctor, c.fecha, c.descripcion, c.diagnostico, " +
//                            "p.id AS paciente_id, p.nombre, p.apellido, p.edad, p.enfermedad " +
//                            "FROM consultas_medicas c " +
//                            "JOIN pacientes p ON c.id_paciente = p.id " +
//                            "WHERE c.fecha = ?";
//             PreparedStatement stmt = connection.prepareStatement(query);
//             stmt.setDate(1, java.sql.Date.valueOf(fecha));
//             ResultSet rs = stmt.executeQuery();
//             while (rs.next()) {
//                 Paciente paciente = new Paciente(
//                         rs.getInt("paciente_id"),
//                         rs.getString("nombre"),
//                         rs.getString("apellido"),
//                         rs.getInt("edad"),
//                         rs.getString("enfermedad"),
//                         null, null, null, null
//                 );
//                 ConsultaMedica consulta = new ConsultaMedica(
//                         rs.getInt("id"),
//                         paciente,
//                         rs.getString("doctor"),
//                         rs.getDate("fecha").toLocalDate(),
//                         rs.getString("descripcion"),
//                         rs.getString("diagnostico")
//                 );
//                 consultas.add(consulta);
//             }
//         } catch (Exception e) {
//             throw new RuntimeException("Error al obtener consultas médicas por fecha: " + e.getMessage());
//         }
//         return consultas;
//     }
// }
