package es.studium.TiendecitaVLG;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ConsultaTicket extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea;
	
	// Método main para ejecutar la ventana
	public static void main(String[] args) {
		try {
			ConsultaTicket dialog = new ConsultaTicket();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Constructor
	public ConsultaTicket() {
        setTitle("Consulta Tickets");
        setBounds(100, 100, 500, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        // ScrollPane para manejar el contenido
        JScrollPane scrollPane = new JScrollPane();
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Area de texto para resultados
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);

        // Panel para botones inferiores
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        // Botón cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        buttonPane.add(closeButton);

        cargarDatos(); // Llama al método que obtiene los datos de la base de datos
    }
	
	// Método para cargar y mostrar los datos de la tabla `Tickets`
	private void cargarDatos() {
	    StringBuilder resultados = new StringBuilder(); // Objeto para construir el texto de salida
 
	    // Conecta con BD
	    try (Connection conn = Conexiones.conectar();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(
	        	// Consulta SQL con un JOIN entre `Tickets` y `Articulos`
	            "SELECT t.idTicket, t.fecha, a.descripcion AS articulos, t.total, t.cantidad " +
	            "FROM Tickets t " +
	            "JOIN Articulos a ON t.idArticuloFK = a.idArticulo")) {

	    	// Iterar sobre los resultados de la consulta
	        while (rs.next()) {
	            int id = rs.getInt("idTicket");
	            String fecha = rs.getString("fecha");
	            String articulos = rs.getString("articulos");
	            String total = rs.getString("total");
	            String cantidad = rs.getString("cantidad");

	            // Construir la línea de texto con los datos obtenidos
	            resultados.append("ID: ").append(id)
	                      .append(", Fecha: ").append(fecha)
	                      .append(", Artículos: ").append(articulos)
	                      .append(", Total: ").append(total)
	                      .append(", Cantidad: ").append(cantidad)
	                      .append("\n");
	        }

	    } catch (SQLException e) {
	        resultados.append("Error al consultar los tickets: ").append(e.getMessage());
	    }

	    textArea.setText(resultados.toString());
	}

}
