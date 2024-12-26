package com.hospital.repositorios;

import com.hospital.modelos.Paciente;
import com.hospital.modelos.TomaSignos;
import com.hospital.utilidades.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacientesRepositoryMySQL implements PacientesRepository {

    @Override
    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT id, nombre, apellido, edad, enfermedad, frecuencia_cardiaca, presion_arterial, " +
                       "temperatura, alerta, antecedentes, activo FROM pacientes";
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
                    rs.getString("alerta") != null ? rs.getString("alerta") : "Sin alerta",
                    rs.getString("antecedentes") != null ? rs.getString("antecedentes") : "Sin antecedentes",
                    rs.getBoolean("activo")
                );
                pacientes.add(paciente);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar todos los pacientes: " + e.getMessage(), e);
        }
        return pacientes;
    }

    
    
    @Override
    public List<Paciente> listarActivos() {
        List<Paciente> pacientesActivos = new ArrayList<>();
        String query = "SELECT id, nombre, apellido, edad, enfermedad, frecuencia_cardiaca, presion_arterial, " +
                       "temperatura, alerta, antecedentes, activo FROM pacientes WHERE activo = 1";
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
                    rs.getString("alerta") != null ? rs.getString("alerta") : "Sin alerta",
                    rs.getString("antecedentes") != null ? rs.getString("antecedentes") : "Sin antecedentes",
                    rs.getBoolean("activo")
                );
                pacientesActivos.add(paciente);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los pacientes activos: " + e.getMessage(), e);
        }
        return pacientesActivos;
    }
    
    @Override
    public List<Paciente> obtenerTodosLosPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT id, nombre, apellido, edad, enfermedad, frecuencia_cardiaca, presion_arterial, " +
                       "temperatura, antecedentes, activo, alerta FROM pacientes";
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
                    rs.getString("alerta") != null ? rs.getString("alerta") : "Sin alerta", // Maneja valores nulos para alerta
                    rs.getString("antecedentes") != null ? rs.getString("antecedentes") : "Sin antecedentes", // Maneja valores nulos para antecedentes
                    rs.getBoolean("activo") // Campo activo
                );
    
                pacientes.add(paciente);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los pacientes: " + e.getMessage(), e);
        }
        return pacientes;
    }
    
    
    @Override
    public List<Paciente> listarInactivos() {
        List<Paciente> pacientesInactivos = new ArrayList<>();
        String query = "SELECT id, nombre, apellido, edad, enfermedad, frecuencia_cardiaca, presion_arterial, " +
                       "temperatura, alerta, antecedentes, activo FROM pacientes WHERE activo = 0";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                System.out.println("Cargando paciente inactivo desde la base de datos: " + rs.getString("nombre"));
                Paciente paciente = new Paciente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getInt("edad"),
                    rs.getString("enfermedad"),
                    rs.getString("frecuencia_cardiaca"),
                    rs.getString("presion_arterial"),
                    rs.getString("temperatura"),
                    rs.getString("alerta") != null ? rs.getString("alerta") : "Sin alerta",
                    rs.getString("antecedentes") != null ? rs.getString("antecedentes") : "Sin antecedentes",
                    rs.getBoolean("activo")
                );
                pacientesInactivos.add(paciente);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los pacientes inactivos: " + e.getMessage(), e);
        }
        return pacientesInactivos;
    }
    public List<TomaSignos> obtenerSignosVitales(int pacienteId) {
        List<TomaSignos> signosVitales = new ArrayList<>();
        String query = "SELECT id, frecuencia_cardiaca, presion_arterial, temperatura, " +
                       "frecuencia_respiratoria, oxigenacion, fecha_registro " +
                       "FROM signos_vitales WHERE id_paciente = ? ORDER BY fecha_registro DESC";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pacienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TomaSignos signo = new TomaSignos(
                        rs.getInt("id"),                                // ID
                        rs.getString("frecuencia_cardiaca"),           // Frecuencia Cardíaca
                        rs.getString("presion_arterial"),              // Presión Arterial
                        rs.getString("temperatura"),                   // Temperatura
                        rs.getString("frecuencia_respiratoria"),       // Frecuencia Respiratoria
                        rs.getString("oxigenacion"),                   // Oxigenación
                        rs.getString("fecha_registro")                 // Fecha y Hora
                    );
                    signosVitales.add(signo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los signos vitales para el paciente con ID: " + pacienteId, e);
        }
    
        return signosVitales;
    }
    
    

    @Override
    public Paciente buscarPorId(int idPaciente) {
        String query = "SELECT id, nombre, apellido, edad, enfermedad, frecuencia_cardiaca, presion_arterial, " +
                       "temperatura, alerta, antecedentes, activo FROM pacientes WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idPaciente);
            try (ResultSet rs = stmt.executeQuery()) {
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
                        rs.getString("alerta") != null ? rs.getString("alerta") : "Sin alerta",
                        rs.getString("antecedentes") != null ? rs.getString("antecedentes") : "Sin antecedentes",
                        rs.getBoolean("activo")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el paciente por ID: " + e.getMessage(), e);
        }
        return null;
    }
    

    
@Override
public void actualizarAlerta(int idPaciente, String alerta) {
    String query = "UPDATE pacientes SET alerta = ? WHERE id = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement stmt = connection.prepareStatement(query)) {

        stmt.setString(1, alerta);
        stmt.setInt(2, idPaciente);
        stmt.executeUpdate();

    } catch (Exception e) {
        throw new RuntimeException("Error al actualizar la alerta del paciente: " + e.getMessage(), e);
    }
}

@Override
public List<Paciente> buscarPorEnfermedad(String enfermedad) {
    List<Paciente> pacientes = new ArrayList<>();
    String query = "SELECT id, nombre, apellido, edad, enfermedad, frecuencia_cardiaca, presion_arterial, " +
                   "temperatura, antecedentes, alerta, activo FROM pacientes WHERE enfermedad LIKE ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement stmt = connection.prepareStatement(query)) {

        stmt.setString(1, "%" + enfermedad + "%"); // Busca coincidencias parciales
        ResultSet rs = stmt.executeQuery();

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
                rs.getString("alerta") != null ? rs.getString("alerta") : "Sin alerta", // Maneja valores nulos para alerta
                rs.getString("antecedentes") != null ? rs.getString("antecedentes") : "Sin antecedentes", // Maneja valores nulos para antecedentes
                rs.getBoolean("activo") // Incluye el estado activo
            );

            // Agrega los antecedentes si existen
            String antecedentes = rs.getString("antecedentes");
            if (antecedentes != null && !antecedentes.trim().isEmpty()) {
                paciente.agregarAntecedente(antecedentes);
            }

            pacientes.add(paciente);
        }
    } catch (Exception e) {
        throw new RuntimeException("Error al buscar pacientes por enfermedad: " + e.getMessage(), e);
    }
    return pacientes;
}


    @Override
    public void cambiarEstado(int idPaciente, boolean activo) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE pacientes SET activo = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setBoolean(1, activo);
            stmt.setInt(2, idPaciente);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el estado del paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public int guardarPaciente(Paciente paciente) {
        String query = "INSERT INTO pacientes (nombre, apellido, edad, enfermedad, estado, fecha_registro, antecedentes) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
    
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setInt(3, paciente.getEdad());
            stmt.setString(4, paciente.getEnfermedades());
            stmt.setBoolean(5, true); // Estado por defecto: activo
            stmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis())); // Fecha actual
            stmt.setString(7, ""); // Antecedentes iniciales vacíos
            stmt.setString(7, paciente.getAntecedentes() != null
            ? String.join(", ", paciente.getAntecedentes()) // Convierte la lista en una cadena separada por comas
            : ""); // Antecedentes proporcionados o vacíos
    
            stmt.executeUpdate();
    
            // Obtener el ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna el ID generado
                } else {
                    throw new SQLException("No se generó un ID para el paciente.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el paciente: " + e.getMessage(), e);
        }
    }
    
    

    @Override
    public void actualizarPaciente(Paciente paciente) {
        String query = "UPDATE pacientes SET nombre = ?, apellido = ?, edad = ?, enfermedad = ?, " +
        "frecuencia_cardiaca = ?, presion_arterial = ?, temperatura = ?, alerta = ? WHERE id = ?";
try (Connection connection = DatabaseConnection.getConnection();
PreparedStatement stmt = connection.prepareStatement(query)) {

stmt.setString(1, paciente.getNombre());
stmt.setString(2, paciente.getApellido());
stmt.setInt(3, paciente.getEdad());
stmt.setString(4, paciente.getEnfermedades());
stmt.setString(5, paciente.getFrecuenciaCardiaca());
stmt.setString(6, paciente.getPresionArterial());
stmt.setString(7, paciente.getTemperatura());
stmt.setString(8, paciente.getAlerta());
stmt.setInt(9, paciente.getId());

stmt.executeUpdate();
} catch (Exception e) {
throw new RuntimeException("Error al actualizar el paciente: " + e.getMessage(), e);
}
    }
    
    


    @Override
    public void eliminarPaciente(int idPaciente) {
        String query = "DELETE FROM pacientes WHERE id = ?";
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            stmt.setInt(1, idPaciente);
    
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el paciente: " + e.getMessage(), e);
        }
    }
    @Override
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
                rs.getString("enfermedades"),
                rs.getString("frecuencia_cardiaca"),
                rs.getString("presion_arterial"),
                rs.getString("temperatura"),
                rs.getString("alerta"),
                rs.getString("antecedentes"), // Campo agregado para los antecedentes
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
