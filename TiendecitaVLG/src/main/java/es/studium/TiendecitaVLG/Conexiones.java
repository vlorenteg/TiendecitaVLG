package es.studium.TiendecitaVLG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Conecta mi programa con la base de datos
public class Conexiones {
	private static String url = "jdbc:mysql://localhost:3306/tiendecitaVLG";
	private static String usuario = "root";
	private static String password = "Studium2023;";

	public static Connection conectar() {
		Connection conexion = null;
		try {
			conexion = DriverManager.getConnection(url, usuario, password);

		} catch (SQLException e) {
			System.out.println("Ocurrió un error al conectar con la base de datos"+e.getMessage());

		}
		return conexion;
	}
}
