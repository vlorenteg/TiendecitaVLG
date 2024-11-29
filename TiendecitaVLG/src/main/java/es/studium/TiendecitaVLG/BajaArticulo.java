package es.studium.TiendecitaVLG;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BajaArticulo extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JComboBox<Integer> comboArticuloId; // JComboBox para seleccionar el artículo
    private int articuloId;

    // Constructor
    public BajaArticulo() {
        setTitle("Baja Artículo");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Etiqueta de texto
        JLabel lblSeleccionarArticulo = new JLabel("Seleccione el artículo a eliminar:");
        lblSeleccionarArticulo.setBounds(10, 20, 250, 14);
        contentPanel.add(lblSeleccionarArticulo);

        // Crear JComboBox para seleccionar el artículo
        comboArticuloId = new JComboBox<>();
        comboArticuloId.setBounds(160, 20, 250, 20);
        contentPanel.add(comboArticuloId);

        // Cargar los IDs de los artículos
        cargarIdsArticulos();

        // Botón "Eliminar"
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(160, 60, 250, 23);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el ID seleccionado del JComboBox
                Integer selectedId = (Integer) comboArticuloId.getSelectedItem();
                if (selectedId != null) {
                    articuloId = selectedId.intValue(); // Convertir Integer a int

                    // Confirmar la eliminación
                    int confirmation = JOptionPane.showConfirmDialog(null, 
                        "¿Está seguro de que desea eliminar el artículo seleccionado?", 
                        "Confirmación de eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        eliminarArticulo(articuloId);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un artículo.");
                }
            }
        });
        contentPanel.add(btnEliminar);

        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton cancelButton = new JButton("Cancelar");
            cancelButton.setActionCommand("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose(); // Cierra el cuadro de diálogo
                }
            });
            buttonPane.add(cancelButton);
        }
    }

    // Método para cargar los IDs de los artículos en el JComboBox
    private void cargarIdsArticulos() {
        String query = "SELECT idArticulo FROM Articulos"; // Consulta SQL 
        try (Connection conn = Conexiones.conectar(); // Establece conexión
             PreparedStatement ps = conn.prepareStatement(query); // Prepara la consulta
             ResultSet rs = ps.executeQuery()) { // Ejecuta la consulta y guarda el resultado

        	// Lista para almacenar los IDs
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

    // Método para eliminar un artículo de la base de datos
    private void eliminarArticulo(int articuloId) {
        String query = "DELETE FROM Articulos WHERE idArticulo = ?";
        try (Connection conn = Conexiones.conectar();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, articuloId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "El artículo ha sido eliminado correctamente.");
                dispose(); // Cerrar el cuadro de diálogo después de eliminar
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el artículo.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el artículo.");
        }
    }

    // Método main para ejecutar la ventana
    public static void main(String[] args) {
        try {
            BajaArticulo dialog = new BajaArticulo();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

