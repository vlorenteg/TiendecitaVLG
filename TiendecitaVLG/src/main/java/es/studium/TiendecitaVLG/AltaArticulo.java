package es.studium.TiendecitaVLG;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AltaArticulo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	// Método main para iniciar la aplicación
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { // Ejecuta la interfaz gráfica
			public void run() {
				try {
					AltaArticulo frame = new AltaArticulo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor
	public AltaArticulo() {
		setTitle("Nuevo Artículo"); // Título
		setBounds(100, 100, 369, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Etiqueta nuevo articulo
		JLabel lblNewLabel = new JLabel("-Nuevo artículo-");
		lblNewLabel.setBounds(130, 11, 110, 14);
		contentPane.add(lblNewLabel);
		
		// Etiqueta descripción
		JLabel lblNewLabel_1 = new JLabel("Descripción:");
		lblNewLabel_1.setBounds(140, 36, 100, 14);
		contentPane.add(lblNewLabel_1);
		
		// Campo de texto para la descripción
		textField = new JTextField();
		textField.setBounds(44, 68, 278, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		// Etiqueta para precio
		JLabel lblNewLabel_2 = new JLabel("Precio:");
		lblNewLabel_2.setBounds(140, 101, 89, 14);
		contentPane.add(lblNewLabel_2);
		
		// Campo de texto para el precio
		textField_1 = new JTextField();
		textField_1.setBounds(44, 126, 278, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		// Etiqueta cantidad
		JLabel lblNewLabel_3 = new JLabel("Cantidad:");
		lblNewLabel_3.setBounds(140, 157, 89, 14);
		contentPane.add(lblNewLabel_3);
		
		// Campo de texto para la cantidad
		textField_2 = new JTextField();
		textField_2.setBounds(44, 182, 278, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		// Botón aceptar
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Captura los valores ingresados
				String descripcion = textField.getText();
		        String precio = textField_1.getText();
		        String cantidad = textField_2.getText();
		        
		        // Llama al método para insertar el artículo en la BD
		        if (insertarArticulo(descripcion, precio, cantidad)) {
                    JOptionPane.showMessageDialog(null, "Artículo añadido correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al añadir el artículo.");
                }
			}
		});
		btnNewButton.setBounds(126, 213, 89, 23);
		contentPane.add(btnNewButton);
	}
	
	 // Método para insertar un artículo en la BD
	 private boolean insertarArticulo(String descripcion, String precio, String cantidad) {
	        String query = "INSERT INTO Articulos (descripcion, precio, cantidad) VALUES (?, ?, ?)"; // Consulta SQL para insertar datos
	        try (Connection conn = Conexiones.conectar(); // Establece conexión con BD
	             PreparedStatement ps = conn.prepareStatement(query)) { // Prepara la consulta SQL

	            ps.setString(1, descripcion); // Establece valor para descripción
	            ps.setBigDecimal(2, new java.math.BigDecimal(precio)); // Convierte el precio en BigDecimal y lo asigna
	            ps.setInt(3, Integer.parseInt(cantidad)); // Convierte la cantidad a entero y lo asigna

	            ps.executeUpdate(); // Ejecuta la consulta
	            return true; // Devuelve true si se inserta correctamente

	        } catch (SQLException ex) { // Maneja errores SQL
	            ex.printStackTrace();
	            return false;
	        } catch (NumberFormatException ex) { // Maneja errores númericos
	            JOptionPane.showMessageDialog(this, "Por favor, introduce valores numéricos válidos para el precio y la cantidad.");
	            return false;
	        }
	    }
}
