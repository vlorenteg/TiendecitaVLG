package es.studium.TiendecitaVLG;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane; // Panel principal

   
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() { // Ejecuta la interfaz gráfica
            public void run() {
                try {
                    MenuPrincipal frame = new MenuPrincipal(); // Crea una instancia de la ventana principal
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Constructor
    public MenuPrincipal() {
        setTitle("TiendecitaVLG"); // Título
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierre de ventana
        setBounds(100, 100, 565, 200); // Posición y tamaño de la ventana inicial
        
        contentPane = new JPanel(); // Crea el panel principal
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Etiqueta articulos
        JLabel lblNewLabel = new JLabel("Artículos:");
        lblNewLabel.setBounds(10, 49, 76, 14);
        contentPane.add(lblNewLabel);

        // Etiqueta Tickets
        JLabel lblNewLabel_1 = new JLabel("Tickets:");
        lblNewLabel_1.setBounds(10, 93, 76, 14);
        contentPane.add(lblNewLabel_1);

        // Botón alta para artículos
        JButton btnNewButton = new JButton("Alta");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AltaArticulo altaArticuloFrame = new AltaArticulo();
                altaArticuloFrame.setVisible(true);
            }
        });
        btnNewButton.setBounds(67, 45, 109, 23);
        contentPane.add(btnNewButton);

        // Botón consulta para artículos
        JButton btnNewButton_1 = new JButton("Consulta");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConsultaArticulo consultaArticuloDialogo = new ConsultaArticulo();
                consultaArticuloDialogo.setVisible(true);
            }
        });
        btnNewButton_1.setBounds(186, 45, 109, 23);
        contentPane.add(btnNewButton_1);

        // Botón modificación para artículos
        JButton btnNewButton_2 = new JButton("Modificación");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Al hacer clic en "Modificación", abrir el diálogo para seleccionar el artículo
                SeleccionarArticuloModificacion seleccionarArticuloDialogo = new SeleccionarArticuloModificacion();
                seleccionarArticuloDialogo.setVisible(true);
            }
        });
        btnNewButton_2.setBounds(305, 45, 109, 23);
        contentPane.add(btnNewButton_2);

        // Botón baja para artículos
        JButton btnNewButton_3 = new JButton("Baja");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BajaArticulo bajaArticuloDialogo = new BajaArticulo();
                bajaArticuloDialogo.setVisible(true);
            }
        });
        btnNewButton_3.setBounds(423, 45, 109, 23);
        contentPane.add(btnNewButton_3);

        // Botón alta para tickets
        JButton btnNewButton_4 = new JButton("Alta");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AltaTicket altaTicketFrame = new AltaTicket();
                altaTicketFrame.setVisible(true);
            }
        });
        btnNewButton_4.setBounds(67, 89, 109, 23);
        contentPane.add(btnNewButton_4);

        // Botón consulta para tickets
        JButton btnNewButton_5 = new JButton("Consulta");
        btnNewButton_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConsultaTicket consultaTicketDialogo = new ConsultaTicket();
                consultaTicketDialogo.setVisible(true);
            }
        });
        btnNewButton_5.setBounds(186, 89, 109, 23);
        contentPane.add(btnNewButton_5);
    }
}

