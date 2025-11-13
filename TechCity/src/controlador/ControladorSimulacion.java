package controlador;

import modelo.Autobus;
import modelo.Estacion;
import modelo.Simulador;
import vista.VentanaSimulacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador que coordina la comunicación entre la vista y el modelo.
 * Maneja los eventos de los botones y actualiza la GUI en cada tick.
 */
public class ControladorSimulacion implements Simulador.SimulacionListener {

    private final Simulador simulador;
    private final VentanaSimulacion vista;

    public ControladorSimulacion(Simulador simulador, VentanaSimulacion vista) {
        this.simulador = simulador;
        this.vista = vista;
        this.simulador.addListener(this);
        configurarEventos();
    }

    private void configurarEventos() {
        // Botón Pausar
        vista.getBotonPausar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulador.detenerSimulacion();
                actualizarBotones();
            }
        });

        // Botón Reanudar
        vista.getBotonReanudar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulador.iniciarSimulacion();
                actualizarBotones();
            }
        });

        // Botón Salir (si existe)
        if (vista.getBotonSalir() != null) {
            vista.getBotonSalir().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    simulador.detenerSimulacion();
                    SwingUtilities.getWindowAncestor(vista.getPanelPrincipal()).dispose();
                }
            });
        }
        actualizarBotones();
    }

    private void actualizarBotones() {
        boolean ejecutando = simulador.isEnEjecucion();
        vista.getBotonPausar().setEnabled(ejecutando);
        vista.getBotonReanudar().setEnabled(!ejecutando);
    }

    /**
     * Arranca la simulación y muestra la vista preparada.
     */
    public void iniciar() {
        // Inicializar tablas/mapa antes de arrancar
        onActualizar(simulador.getEstaciones(), simulador.getAutobuses());
        simulador.iniciarSimulacion();
        actualizarBotones();
    }

    @Override
    public void onActualizar(List<Estacion> estaciones, List<Autobus> autobuses) {
        // Esta llamada viene desde el hilo del Timer (EDT, ya que es Swing Timer)
        vista.actualizarTablas(estaciones, autobuses);
        vista.actualizarMapa(estaciones, autobuses);
    }
}