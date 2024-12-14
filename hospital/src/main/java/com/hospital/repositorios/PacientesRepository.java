package com.hospital.repositorios;

import com.hospital.modelos.Paciente;
import com.hospital.utilidades.DataBaseConnectiones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PacientesRepository {
    private final Connection conn = DataBaseConnectiones.getConnection();

    public void guardar(Paciente paciente) {
        String query = "INSERT INTO pacientes (nombre, apellido, fecha_nacimiento) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setString(3, paciente.getFechaNacimiento());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el paciente en la base de datos.");
        }
    }
}
