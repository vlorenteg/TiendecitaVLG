package es.studium.TiendecitaVLG;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeleccionarArticuloModificacion extends JDialog {
    private static final long serialVersionUID = 1L;
    private JPanel contentPanel = new JPanel();
    private JComboBox<Integer> comboArticuloId;
    private int articuloId;

    public SeleccionarArticuloModificacion() {
        setTitle("Seleccionar Artículo");
        setBounds(100, 100, 450, 150);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblSeleccionarArticulo = new JLabel("Seleccione el artículo:");
        lblSeleccionarArticulo.setBounds(10, 20, 150, 14);
        contentPanel.add(lblSeleccionarArticulo);

        // Crear el JComboBox para seleccionar el artículo
        comboArticuloId = new JComboBox<>();
        comboArticuloId.setBounds(160, 20, 250, 20);
        contentPanel.add(comboArticuloId);

        // Cargar los IDs de los artículos
        cargarIdsArticulos();

        // Botón de "Modificar"
        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBounds(160, 60, 250, 23);
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el ID seleccionado y abrir la ventana de modificación
                Integer selectedId = (Integer) comboArticuloId.getSelectedItem();
                if (selectedId != null) {
                    articuloId = selectedId.intValue(); // Convertir Integer a int
                    // Aquí pasamos el ID del artículo al constructor de ModificacionArticulo
                    ModificacionArticulo modificacionArticuloDialogo = new ModificacionArticulo(articuloId);
                    modificacionArticuloDialogo.setVisible(true);
                    dispose(); // Cierra la ventana de selección
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un artículo.");
                }
            }
        });
        contentPanel.add(btnModificar);
    }

    // Método para cargar los IDs de los artículos en el JComboBox
    private void cargarIdsArticulos() {
        String query = "SELECT idArticulo FROM Articulos";
        try (Connection conn = Conexiones.conectar();
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
}

