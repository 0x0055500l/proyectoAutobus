package vista;

import modelo.Autobus;
import modelo.Estacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana principal de simulación.
 * Contiene el mapa al centro, panel de datos a la derecha y controles al sur.
 */
public class VentanaSimulacion extends JFrame {

    private final JPanel panelPrincipal = new JPanel(new BorderLayout(8, 8));
    private final PanelMapa panelMapa = new PanelMapa();

    private final JTable tablaEstaciones = new JTable();
    private final JTable tablaAutobuses = new JTable();

    private final JButton btnPausar = new JButton("Pausar");
    private final JButton btnReanudar = new JButton("Reanudar");
    private final JButton btnSalir = new JButton("Salir");

    private DefaultTableModel modeloEstaciones;
    private DefaultTableModel modeloAutobuses;

    public VentanaSimulacion() {
        super("TechCity - Simulación de Red de Transporte Inteligente");
        construirUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 640);
        setLocationRelativeTo(null);
    }

    private void construirUI() {
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Centro: mapa
        panelPrincipal.add(panelMapa, BorderLayout.CENTER);

        // Derecha: tablas
        JPanel panelDerecha = new JPanel();
        panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));

        modeloEstaciones = new DefaultTableModel(new Object[]{"Estación", "Pasajeros esperando"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaEstaciones.setModel(modeloEstaciones);

        modeloAutobuses = new DefaultTableModel(new Object[]{"Bus", "Estación actual", "Ocupados", "Capacidad", "Disp."}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaAutobuses.setModel(modeloAutobuses);

        JScrollPane spEst = new JScrollPane(tablaEstaciones);
        spEst.setBorder(BorderFactory.createTitledBorder("Estaciones"));
        JScrollPane spBus = new JScrollPane(tablaAutobuses);
        spBus.setBorder(BorderFactory.createTitledBorder("Autobuses"));

        panelDerecha.add(spEst);
        panelDerecha.add(Box.createVerticalStrut(8));
        panelDerecha.add(spBus);

        panelPrincipal.add(panelDerecha, BorderLayout.EAST);

        // Sur: controles
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.add(btnPausar);
        panelSur.add(btnReanudar);
        panelSur.add(btnSalir);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    /**
     * Actualiza las tablas con la información actual.
     */
    public void actualizarTablas(List<Estacion> estaciones, List<Autobus> autobuses) {
        // Estaciones
        modeloEstaciones.setRowCount(0);
        for (Estacion e : estaciones) {
            modeloEstaciones.addRow(new Object[]{e.getNombre(), e.getPasajerosEsperando()});
        }

        // Autobuses
        modeloAutobuses.setRowCount(0);
        for (Autobus b : autobuses) {
            int disp = b.getCapacidadMaxima() - b.getPasajerosActuales();
            modeloAutobuses.addRow(new Object[]{
                    "Bus " + b.getId(),
                    b.getEstacionActual().getNombre(),
                    b.getPasajerosActuales(),
                    b.getCapacidadMaxima(),
                    disp
            });
        }
    }

    /**
     * Actualiza el mapa.
     */
    public void actualizarMapa(List<Estacion> estaciones, List<Autobus> autobuses) {
        panelMapa.setDatos(estaciones, autobuses);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JButton getBotonPausar() {
        return btnPausar;
    }

    public JButton getBotonReanudar() {
        return btnReanudar;
    }

    public JButton getBotonSalir() {
        return btnSalir;
    }
}