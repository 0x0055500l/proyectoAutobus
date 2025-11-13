package vista;

import controlador.ControladorSimulacion;
import modelo.Simulador;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana inicial de configuración.
 * Permite elegir número de estaciones, autobuses y capacidad, y lanzar la simulación.
 */
public class VentanaConfiguracion extends JFrame {

    private JSpinner spEstaciones;
    private JSpinner spAutobuses;
    private JSpinner spCapacidad;
    private JButton btnIniciar;

    public VentanaConfiguracion() {
        super("TechCity - Configuración");
        construirUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void construirUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        spEstaciones = new JSpinner(new SpinnerNumberModel(8, 1, 100, 1));
        spAutobuses = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        spCapacidad = new JSpinner(new SpinnerNumberModel(30, 1, 500, 1));
        btnIniciar = new JButton("Iniciar simulación");

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Número de estaciones:"), gbc);
        gbc.gridx = 1; gbc.gridy = row; panel.add(spEstaciones, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Número de autobuses:"), gbc);
        gbc.gridx = 1; gbc.gridy = row; panel.add(spAutobuses, gbc); row++;

        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Capacidad por autobús:"), gbc);
        gbc.gridx = 1; gbc.gridy = row; panel.add(spCapacidad, gbc); row++;

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = row; panel.add(btnIniciar, gbc);

        setContentPane(panel);

        btnIniciar.addActionListener(e -> iniciarSimulacion());
    }

    private void iniciarSimulacion() {
        int nEst = (Integer) spEstaciones.getValue();
        int nBus = (Integer) spAutobuses.getValue();
        int cap = (Integer) spCapacidad.getValue();

        try {
            Simulador simulador = new Simulador(nEst, nBus, cap);
            VentanaSimulacion ventanaSim = new VentanaSimulacion();
            ControladorSimulacion controlador = new ControladorSimulacion(simulador, ventanaSim);
            ventanaSim.setVisible(true);
            controlador.iniciar();
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de configuración", JOptionPane.ERROR_MESSAGE);
        }
    }
}