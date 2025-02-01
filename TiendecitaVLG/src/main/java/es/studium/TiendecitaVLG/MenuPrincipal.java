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
        setBounds(100, 100, 565, 250); // Posición y tamaño de la ventana inicial

        contentPane = new JPanel(); // Crea el panel principal
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Etiqueta artículos
        JLabel lblArticulos = new JLabel("Artículos:");
        lblArticulos.setBounds(10, 49, 76, 14);
        contentPane.add(lblArticulos);

        // Etiqueta Tickets
        JLabel lblTickets = new JLabel("Tickets:");
        lblTickets.setBounds(10, 93, 76, 14);
        contentPane.add(lblTickets);
        
     // Etiqueta Informes
        JLabel lblInformes = new JLabel("Informes:");
        lblInformes.setBounds(10, 137, 76, 14);
        contentPane.add(lblInformes);

        // Botón alta para artículos
        JButton btnAltaArticulo = new JButton("Alta");
        btnAltaArticulo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AltaArticulo altaArticuloFrame = new AltaArticulo();
                altaArticuloFrame.setVisible(true);
            }
        });
        btnAltaArticulo.setBounds(67, 45, 109, 23);
        contentPane.add(btnAltaArticulo);

        // Botón consulta para artículos
        JButton btnConsultaArticulo = new JButton("Consulta");
        btnConsultaArticulo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConsultaArticulo consultaArticuloDialogo = new ConsultaArticulo();
                consultaArticuloDialogo.setVisible(true);
            }
        });
        btnConsultaArticulo.setBounds(186, 45, 109, 23);
        contentPane.add(btnConsultaArticulo);

        // Botón modificación para artículos
        JButton btnModificacionArticulo = new JButton("Modificación");
        btnModificacionArticulo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SeleccionarArticuloModificacion seleccionarArticuloDialogo = new SeleccionarArticuloModificacion();
                seleccionarArticuloDialogo.setVisible(true);
            }
        });
        btnModificacionArticulo.setBounds(305, 45, 109, 23);
        contentPane.add(btnModificacionArticulo);

        // Botón baja para artículos
        JButton btnBajaArticulo = new JButton("Baja");
        btnBajaArticulo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BajaArticulo bajaArticuloDialogo = new BajaArticulo();
                bajaArticuloDialogo.setVisible(true);
            }
        });
        btnBajaArticulo.setBounds(423, 45, 109, 23);
        contentPane.add(btnBajaArticulo);

        // Botón alta para tickets
        JButton btnAltaTicket = new JButton("Alta");
        btnAltaTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AltaTicket altaTicketFrame = new AltaTicket();
                altaTicketFrame.setVisible(true);
            }
        });
        btnAltaTicket.setBounds(67, 89, 109, 23);
        contentPane.add(btnAltaTicket);

        // Botón consulta para tickets
        JButton btnConsultaTicket = new JButton("Consulta");
        btnConsultaTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConsultaTicket consultaTicketDialogo = new ConsultaTicket();
                consultaTicketDialogo.setVisible(true);
            }
        });
        btnConsultaTicket.setBounds(186, 89, 109, 23);
        contentPane.add(btnConsultaTicket);
        
        // Botón consulta informe artículos
        JButton btnInformeArticulos = new JButton("Informe Artículos");
        btnInformeArticulos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Conexiones.generarPDFarticulos();
            }
        });
        btnInformeArticulos.setBounds(67, 135, 150, 23);
        contentPane.add(btnInformeArticulos);

        // Botón consulta infomre tickets 
        JButton btnConsultaTickets = new JButton("Informe Tickets");
        btnConsultaTickets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VistaConsultaTickets vistaConsultaTicketsFrame = new VistaConsultaTickets();
                vistaConsultaTicketsFrame.setVisible(true);
            }
        });
        btnConsultaTickets.setBounds(230, 135, 150, 23);
        contentPane.add(btnConsultaTickets);
        
    }
}
