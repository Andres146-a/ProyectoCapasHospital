package com.hospital.repositorios;

import com.hospital.modelos.TomaSignos;
import com.hospital.utilidades.DataBaseConnectiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TomaSignosRepositoryMySQL implements TomaSignosRepository {

    @Override
    public void guardarTomaSignos(TomaSignos tomaSignos) {
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "INSERT INTO toma_signos (idPaciente, fecha, pulso, presion, temperatura) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, tomaSignos.getIdPaciente());
            stmt.setDate(2, java.sql.Date.valueOf(tomaSignos.getFecha()));
            stmt.setInt(3, tomaSignos.getPulso());
            stmt.setString(4, tomaSignos.getPresion());
            stmt.setDouble(5, tomaSignos.getTemperatura());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la toma de signos: " + e.getMessage());
        }
    }

    @Override
    public List<TomaSignos> listarTodos() {
        List<TomaSignos> listaSignos = new ArrayList<>();
        try (Connection connection = DataBaseConnectiones.getConnection()) {
            String query = "SELECT * FROM toma_signos";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TomaSignos tomaSignos = new TomaSignos(
                    rs.getInt("id"),
                    rs.getInt("idPaciente"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getInt("pulso"),
                    rs.getString("presion"),
                    rs.getDouble("temperatura")
                );
                listaSignos.add(tomaSignos);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las tomas de signos: " + e.getMessage());
        }
        return listaSignos;
    }
}
