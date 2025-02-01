package es.studium.TiendecitaVLG;

import java.awt.Desktop;
import java.io.File; 
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

// Conecta mi programa con la base de datos
public class Conexiones {
    private static final String URL = "jdbc:mysql://localhost:3306/tiendecitaVLG";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "Studium2023;";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void generarPDFarticulos() {
        Connection conexion = null;
        try {
            // Obtener conexión
            conexion = conectar();
            if (conexion == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            // Compilar el informe generando fichero jasper
            JasperCompileManager.compileReportToFile("./src/main/resources/informeArticulos.jrxml");
            System.out.println("Fichero informeArticulos.jasper generado CORRECTAMENTE!");

            // Objeto para guardar parámetros necesarios para el informe
            HashMap<String, Object> parametros = new HashMap<>();

            // Cargar el informe compilado
            JasperReport report = (JasperReport) JRLoader
                    .loadObjectFromFile("./src/main/resources/informeArticulos.jasper");

            // Completar el informe con los datos de la base de datos
            JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion);

            // Mostrar el informe en JasperViewer
            JasperViewer.viewReport(print, false);

            // Exportar a PDF
            JasperExportManager.exportReportToPdfFile(print, "./src/main/resources/informeArticulos.pdf");

            // Abrir el fichero PDF generado
            File path = new File("./src/main/resources/informeArticulos.pdf");
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        } finally {
            // Cerrar conexión
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    public static void generarPDFtickets(LocalDate fechaDesde, LocalDate fechaHasta) {
        Connection conexion = null;
        try {
            // Obtener conexión
            conexion = conectar();
            if (conexion == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            Date fechaDesdeSQL = Date.valueOf(fechaDesde);
            Date fechaHastaSQL = Date.valueOf(fechaHasta);

            // Compilar el informe generando fichero jasper
            JasperCompileManager.compileReportToFile("./src/main/resources/informeTickets.jrxml");
            System.out.println("Fichero informeTickets.jasper generado CORRECTAMENTE!");

            // Objeto para guardar parámetros necesarios para el informe
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("fechaDesde", fechaDesdeSQL);
            parametros.put("fechaHasta", fechaHastaSQL);

            // Cargar el informe compilado
            JasperReport report = (JasperReport) JRLoader
                    .loadObjectFromFile("./src/main/resources/informeTickets.jasper");

            // Completar el informe con los datos de la base de datos
            JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion);

            // Mostrar el informe en JasperViewer
            JasperViewer.viewReport(print, false);

            // Exportar a PDF
            JasperExportManager.exportReportToPdfFile(print, "./src/main/resources/informeTickets.pdf");

            // Abrir el fichero PDF generado
            File path = new File("./src/main/resources/informeTickets.pdf");
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        } finally {
            // Cerrar conexión
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}
