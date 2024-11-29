package es.studium.TiendecitaVLG;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AltaTicket extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_2;
    private JTextField textFieldCantidad;  // Campo de cantidad
    private JComboBox<String> comboBoxArticulos;  // JComboBox para artículos
    private double precioArticulo = 0.0; // Para almacenar el precio del artículo seleccionado

    // Método para iniciar
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AltaTicket frame = new AltaTicket();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Constructor
    public AltaTicket() {
        setTitle("Nuevo Ticket");
        setBounds(100, 100, 420, 350);  // Tamaño de la ventana
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Etiqueta nuevo ticket
        JLabel lblNewLabel = new JLabel("-Nuevo Ticket-");
        lblNewLabel.setBounds(160, 11, 115, 14);  // Ajustar la posición
        contentPane.add(lblNewLabel);

        // Etiqueta fecha
        JLabel lblNewLabel_1 = new JLabel("Fecha:");
        lblNewLabel_1.setBounds(142, 36, 89, 14);
        contentPane.add(lblNewLabel_1);

        // Campo de texto para la fecha
        textField = new JTextField();
        textField.setBounds(34, 61, 335, 20);  // Ajustar el tamaño
        contentPane.add(textField);
        textField.setColumns(10);

        // Etiqueta cantidad
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(142, 92, 89, 14);  // Ajusté la posición
        contentPane.add(lblCantidad);

        // Campo de texto para la cantidad
        textFieldCantidad = new JTextField();
        textFieldCantidad.setBounds(34, 117, 335, 20);  // Ajustar la posición
        textFieldCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarTotal();  // Calcular el total cada vez que se ingresa la cantidad
            }
        });
        contentPane.add(textFieldCantidad);
        textFieldCantidad.setColumns(10);

        // Etiqueta artículos
        JLabel lblNewLabel_2 = new JLabel("Artículos:");
        lblNewLabel_2.setBounds(142, 149, 89, 14);  // Ajusté la posición para que esté después de la cantidad
        contentPane.add(lblNewLabel_2);

        comboBoxArticulos = new JComboBox<>();
        comboBoxArticulos.setBounds(34, 174, 335, 20);  // Ajustar el tamaño
        comboBoxArticulos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String articuloSeleccionado = (String) comboBoxArticulos.getSelectedItem();
                precioArticulo = obtenerPrecioArticulo(articuloSeleccionado);
                actualizarTotal();  // Calcular el total cada vez que se selecciona un artículo
            }
        });
        contentPane.add(comboBoxArticulos);

        // Etiqueta total
        JLabel lblNewLabel_3 = new JLabel("Total:");
        lblNewLabel_3.setBounds(142, 200, 89, 14);  
        contentPane.add(lblNewLabel_3);

        textField_2 = new JTextField();
        textField_2.setBounds(34, 225, 335, 20);  // Ajustar la posición debajo de la cantidad
        textField_2.setEditable(false);  // El campo Total no debe ser editable
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        // Botón aceptar
        JButton btnNewButton = new JButton("Aceptar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Captura los valores ingresados
                String fecha = textField.getText();
                String articuloSeleccionado = (String) comboBoxArticulos.getSelectedItem();
                String total = textField_2.getText();
                String cantidadStr = textFieldCantidad.getText();

                // Maneja errores
                if (articuloSeleccionado == null || total.isEmpty() || cantidadStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona un artículo, completa el total y la cantidad.");
                    return;
                }

                // Reemplazar la coma por un punto en el total
                total = total.replace(",", ".");

                // Verificar que el total es un valor válido
                try {
                    if (Double.parseDouble(total) <= 0) {
                        JOptionPane.showMessageDialog(null, "El total calculado no es válido.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un valor numérico válido para el total.");
                    return;
                }

                // Obtener el idArticulo desde el JComboBox
                int idArticulo = obtenerIdArticulo(articuloSeleccionado);
                int cantidad = Integer.parseInt(cantidadStr); // Convertir cantidad a entero

                if (insertarTicket(fecha, idArticulo, total, cantidad)) {
                    JOptionPane.showMessageDialog(null, "Ticket añadido correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al añadir el ticket.");
                }
            }
        });
        btnNewButton.setBounds(160, 265, 89, 23);  // Ajustar la posición
        contentPane.add(btnNewButton);

        // Cargar los artículos en el JComboBox al iniciar la ventana
        cargarArticulos();
    }

    private void cargarArticulos() {
        try (Connection conn = Conexiones.conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT descripcion FROM Articulos");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String descripcion = rs.getString("descripcion");
                comboBoxArticulos.addItem(descripcion);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Método para obtener el ID del articulo a partir de su descripción
    private int obtenerIdArticulo(String descripcion) {
        int idArticulo = -1;
        try (Connection conn = Conexiones.conectar(); // Establece conexión
             PreparedStatement ps = conn.prepareStatement("SELECT idArticulo FROM Articulos WHERE descripcion = ?")) {
        	// Asigna el valor de `descripcion` al primer parámetro de la consulta SQL.
            ps.setString(1, descripcion);
            // Ejecuta la consulta y almacena el resultado en un `ResultSet`.
            try (ResultSet rs = ps.executeQuery()) {
            	// Si hay un resultado, extrae el valor de la columna "idArticulo".
                if (rs.next()) {
                    idArticulo = rs.getInt("idArticulo");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idArticulo;
    }

    private double obtenerPrecioArticulo(String descripcion) {
        double precio = 0.0; // Inicializa el precio del artículo con un valor predeterminado (0.0).
        try (Connection conn = Conexiones.conectar(); // Establece conexión
        	 // Prepara una consulta SQL para obtener el precio del artículo a partir de su descripción.
             PreparedStatement ps = conn.prepareStatement("SELECT precio FROM Articulos WHERE descripcion = ?")) {
        	// Asigna el valor de `descripcion` al primer parámetro de la consulta SQL.
            ps.setString(1, descripcion);
            // Ejecuta la consulta y almacena el resultado en un `ResultSet`.
            try (ResultSet rs = ps.executeQuery()) {
            	// Si hay un resultado, extrae el valor de la columna "precio".
                if (rs.next()) {
                    precio = rs.getDouble("precio");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return precio;
    }

    private void actualizarTotal() {
        // Obtiene el texto ingresado en el campo de cantidad.
        String cantidadStr = textFieldCantidad.getText();
        // Verifica que el campo de cantidad no esté vacío y que el precio del artículo sea mayor a 0.
        if (!cantidadStr.isEmpty() && precioArticulo > 0) {
            try {
                // Convierte la cantidad a un número entero.
                int cantidad = Integer.parseInt(cantidadStr);
                // Calcula el total multiplicando la cantidad por el precio del artículo.
                double total = precioArticulo * cantidad;
                // Establece el valor del total en el campo correspondiente, formateado con dos decimales.
                textField_2.setText(String.format("%.2f", total));
            } catch (NumberFormatException e) {
                // Muestra un mensaje de error si la cantidad no es un número válido.
                JOptionPane.showMessageDialog(this, "Por favor, introduce un valor válido para la cantidad.");
            }
        } else {
            // Si la cantidad está vacía o el precio es 0, establece el total como 0.00.
            textField_2.setText("0.00");
        }
    }

    private String convertirFecha(String fecha) {
        // Define el formato de fecha de entrada (dd/MM/yyyy) y salida (yyyy-MM-dd).
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Intenta convertir la fecha de entrada al formato de salida.
            Date date = formatoEntrada.parse(fecha);
            return formatoSalida.format(date);
        } catch (ParseException e) {
            // Muestra la pila de errores si la fecha no tiene el formato correcto.
            e.printStackTrace();
            return null;
        }
    }

    private boolean insertarTicket(String fecha, int idArticulo, String total, int cantidad) {
    	// Define la consulta SQL para insertar un nuevo ticket en la base de datos.
        String query = "INSERT INTO Tickets (fecha, idArticuloFK, total, cantidad) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexiones.conectar(); // Establece conexión
             PreparedStatement ps = conn.prepareStatement(query)) {

            String fechaConvertida = convertirFecha(fecha);
            if (fechaConvertida == null) {
                JOptionPane.showMessageDialog(this, "La fecha no tiene el formato correcto.");
                return false;
            }

            ps.setString(1, fechaConvertida);
            ps.setInt(2, idArticulo);  // Usar el idArticulo como FK
            ps.setBigDecimal(3, new java.math.BigDecimal(total));  // Convertir el total a BigDecimal
            ps.setInt(4, cantidad); // Asignar el valor de cantidad

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
