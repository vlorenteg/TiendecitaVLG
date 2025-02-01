package es.studium.TiendecitaVLG;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VistaConsultaTickets extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    Font font1 = new Font("Segoe UI", Font.BOLD, 20);
    Font font2 = new Font("Segoe UI", Font.PLAIN, 18);
    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JLabel lblFechaDesde;
    private JLabel lblFechaHasta;
    private JButton btnAceptar;

    public VistaConsultaTickets() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("TICKETS: GENERAR INFORME");
        setBounds(100, 100, 369, 260);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(250, 240, 230));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("TICKETS: GENERAR INFORME");
        lblTitulo.setBounds(36, 10, 292, 30);
        lblTitulo.setFont(font1);
        contentPane.add(lblTitulo);

        JLabel lblFechasPrompt = new JLabel("Introduce las fechas:");
        lblFechasPrompt.setFont(font2);
        lblFechasPrompt.setBounds(91, 50, 182, 30);
        contentPane.add(lblFechasPrompt);

        txtFechaDesde = new JTextField();
        txtFechaDesde.setBounds(135, 90, 170, 30);
        txtFechaDesde.setText("dd/MM/yyyy");
        contentPane.add(txtFechaDesde);
        txtFechaDesde.setColumns(10);

        txtFechaHasta = new JTextField();
        txtFechaHasta.setBounds(135, 130, 170, 30);
        txtFechaHasta.setText("dd/MM/yyyy");
        contentPane.add(txtFechaHasta);
        txtFechaHasta.setColumns(10);

        lblFechaDesde = new JLabel("Desde:");
        lblFechaDesde.setFont(font2);
        lblFechaDesde.setBounds(45, 90, 80, 30);
        contentPane.add(lblFechaDesde);

        lblFechaHasta = new JLabel("Hasta:");
        lblFechaHasta.setFont(font2);
        lblFechaHasta.setBounds(45, 125, 80, 30);
        contentPane.add(lblFechaHasta);

        btnAceptar = new JButton("ACEPTAR");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strFechaDesde = txtFechaDesde.getText();
                String strFechaHasta = txtFechaHasta.getText();

                if (validarFecha(strFechaDesde) && validarFecha(strFechaHasta)) {
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date fechaDesde = formatoFecha.parse(strFechaDesde);
                        Date fechaHasta = formatoFecha.parse(strFechaHasta);

                        // Conversión de Date a LocalDate
                        LocalDate localFechaDesde = fechaDesde.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate localFechaHasta = fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        // Llamada al método corregido
                        Conexiones.generarPDFtickets(localFechaDesde, localFechaHasta);
                        setVisible(false);
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Error en la conversión de fechas.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fecha incorrecta.");
                    txtFechaDesde.setText("dd/MM/yyyy");
                    txtFechaHasta.setText("dd/MM/yyyy");
                }
            }
        });

        btnAceptar.setBounds(120, 175, 100, 30);
        contentPane.add(btnAceptar);

        setVisible(true);
    }

    private boolean validarFecha(String fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        formatoFecha.setLenient(false);
        try {
            formatoFecha.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
