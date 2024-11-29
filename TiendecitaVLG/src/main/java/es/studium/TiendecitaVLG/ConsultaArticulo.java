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
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ConsultaArticulo extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextArea textArea;

    // Método main para ejecutar la ventana
    public static void main(String[] args) {
        try {
            ConsultaArticulo dialog = new ConsultaArticulo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Constructor
    public ConsultaArticulo() {
        setTitle("Consulta Artículos");
        setBounds(100, 100, 500, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        // Agrega un scroll para el area de texto
        JScrollPane scrollPane = new JScrollPane();
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Inicializa el área de texto donde se mostrarán los datos
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea); // Agrega el área de texto al scroll

        // Panel para los botones de la parte inferior
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        // Botón para cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        buttonPane.add(closeButton);

        cargarDatos(); // Llama al método que carga los datos en el área de texto
    }

    // Método para cargar los datos de la tabla "Articulos" en el área de texto
    private void cargarDatos() {
        StringBuilder resultados = new StringBuilder();

        // Conecta a la BD y realiza la ocnsulta
        try (Connection conn = Conexiones.conectar(); // Establece conexión
             Statement stmt = conn.createStatement(); // Crea un objeto Statement para ejecutar la consulta
             ResultSet rs = stmt.executeQuery("SELECT * FROM Articulos")) { // Ejecuta la consulta

        	// Recorre los resultados obtenidos de la consulta
            while (rs.next()) {
                // Obtiene los datos de cada columna
                int id = rs.getInt("idArticulo");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");

                // Formatea los datos y los agrega al StringBuilder
                resultados.append("ID: ").append(id)
                          .append(", Descripción: ").append(descripcion)
                          .append(", Precio: ").append(precio)
                          .append(", Cantidad: ").append(cantidad)
                          .append("\n");
            }

        } catch (SQLException e) {
            resultados.append("Error al consultar los artículos: ").append(e.getMessage());
        }

        // Establece el texto del área de texto con los resultados obtenidos
        textArea.setText(resultados.toString());
    }
}

