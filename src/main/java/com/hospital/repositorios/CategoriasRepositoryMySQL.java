package com.hospital.repositorios;

import com.hospital.utilidades.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriasRepositoryMySQL implements CategoriasRepository {

    @Override
    public void guardarCategoria(String categoria) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO categorias (nombre) VALUES (?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, categoria);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la categoría: " + e.getMessage());
        }
    }

    @Override
    public void eliminarCategoria(String categoria) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM categorias WHERE nombre = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, categoria);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la categoría: " + e.getMessage());
        }
    }

    @Override
    public List<String> listarCategorias() {
        List<String> categorias = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT nombre FROM categorias";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categorias.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las categorías: " + e.getMessage());
        }
        return categorias;
    }
}
