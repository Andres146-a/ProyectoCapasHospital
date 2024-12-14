package com.hospital.utilidades;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DataBaseConnectiones {public static Connection getConnection() {
    // código para establecer una conexión a la base de datos
    try {
        Connection conn = DriverManager.getConnection("url", "usuario", "contraseña");
        return conn;
    } catch (SQLException e) {
        throw new RuntimeException("Error al establecer la conexión", e);
    }
}
}
