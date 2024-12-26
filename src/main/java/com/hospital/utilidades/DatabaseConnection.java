package com.hospital.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/proyectoCapasHospital"; // Cambia localhost si usas otro host
    private static final String USER = "root"; // Cambia 'root' por tu usuario
    private static final String PASSWORD = "Admi1234"; // Cambia 'password' por tu contraseña

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Carga el driver de MySQL
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver de MySQL: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
