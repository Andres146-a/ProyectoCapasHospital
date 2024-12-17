package com.hospital.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnectiones {
    private static final String URL = "jdbc:mysql://localhost:3306/proyectoCapasHospital"; // Cambia "nombre_base_datos"
    private static final String USER = "root"; // Cambia al usuario que estás usando
    private static final String PASSWORD = "Admi1234"; // Cambia a tu contraseña

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Asegúrate de que el driver está disponible
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver no encontrado", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error al establecer la conexión", e);
        }
    }
}
