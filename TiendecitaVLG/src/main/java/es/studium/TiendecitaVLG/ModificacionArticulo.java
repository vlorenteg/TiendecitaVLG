package es.studium.TiendecitaVLG;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModificacionArticulo extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JComboBox<Integer> comboArticuloId; // JComboBox para seleccionar el ID del artículo
    private int articuloId;

    // Constructor donde inicializas la interfaz y recibes el ID del artículo
    public ModificacionArticulo(int articuloId) {
        this.articuloId = articuloId;  // Guardamos el ID recibido

        setTitle("Modificación Artículo");
        setBounds(100, 100, 450, 350);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Crear el JComboBox para seleccionar el ID del artículo
        JLabel lblArticuloId = new JLabel("ID Artículo:");
        lblArticuloId.setBounds(10, 20, 100, 14);
        contentPanel.add(lblArticuloId);

        comboArticuloId = new JComboBox<>();
        comboArticuloId.setBounds(120, 20, 300, 20);
        contentPanel.add(comboArticuloId);

        // Crear los campos de texto para la modificación
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(10, 60, 100, 14);
        contentPanel.add(lblDescripcion);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(120, 60, 300, 20);
        contentPanel.add(txtDescripcion);
        txtDescripcion.setColumns(10);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(10, 100, 100, 14);
        contentPanel.add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(120, 100, 300, 20);
        contentPanel.add(txtPrecio);
        txtPrecio.setColumns(10);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(10, 140, 100, 14);
        contentPanel.add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(120, 140, 300, 20);
        contentPanel.add(txtCantidad);
        txtCantidad.setColumns(10);

        // Botones para aceptar y cancelar
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("Aceptar");
            okButton.setActionCommand("OK");
            okButton.addActionListener(e -> modificarArticulo());
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);
        }
        {
            JButton cancelButton = new JButton("Cancelar");
            cancelButton.setActionCommand("Cancel");
            cancelButton.addActionListener(e -> dispose());
            buttonPane.add(cancelButton);
        }

        // Agregar un listener para cuando se selecciona un ID en el JComboBox
        comboArticuloId.addActionListener(e -> cargarDatosArticulo());

        // Cargar los IDs de los artículos al inicializar la ventana
        cargarIdsArticulos();
        // Cargar los datos del artículo seleccionado al abrir
        cargarDatosArticulo();
    }

    // Método para cargar los IDs de los artículos en el JComboBox
    private void cargarIdsArticulos() {
        String query = "SELECT idArticulo FROM Articulos"; 
        try (Connection conn = Conexiones.conectar(); // Conexión con BD
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            List<Integer> idsArticulos = new ArrayList<>();
            while (rs.next()) {
                idsArticulos.add(rs.getInt("idArticulo"));
            }

            // Llenar el JComboBox con los IDs de los artículos
            for (Integer id : idsArticulos) {
                comboArticuloId.addItem(id);
            }

            // Seleccionar el primer ID si hay datos
            if (!idsArticulos.isEmpty()) {
                comboArticuloId.setSelectedIndex(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar los datos del artículo seleccionado
    private void cargarDatosArticulo() {
        // Comprobar si el JComboBox tiene un item seleccionado antes de acceder a su valor
        if (comboArticuloId.getSelectedItem() != null) {
            articuloId = (Integer) comboArticuloId.getSelectedItem();
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado un artículo.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Si no hay un artículo seleccionado, salimos del método
        }

        String query = "SELECT descripcion, precio, cantidad FROM Articulos WHERE idArticulo = ?";

        try (Connection conn = Conexiones.conectar(); // Establece conexión
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, articuloId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Verifica si los campos de texto están correctamente inicializados
                    if (txtDescripcion != null) {
                        txtDescripcion.setText(rs.getString("descripcion"));
                    }
                    if (txtPrecio != null) {
                        txtPrecio.setText(String.valueOf(rs.getDouble("precio")));
                    }
                    if (txtCantidad != null) {
                        txtCantidad.setText(String.valueOf(rs.getInt("cantidad")));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del artículo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para modificar el artículo
    private void modificarArticulo() {
        String descripcion = txtDescripcion.getText();
        String precio = txtPrecio.getText();
        String cantidad = txtCantidad.getText();

        String query = "UPDATE Articulos SET descripcion = ?, precio = ?, cantidad = ? WHERE idArticulo = ?";
        try (Connection conn = Conexiones.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, descripcion);
            ps.setString(2, precio);
            ps.setString(3, cantidad);
            ps.setInt(4, articuloId); // Utiliza el ID proporcionado al crear la ventana

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Artículo modificado con éxito");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar el artículo");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el artículo");
        }
    }
}


