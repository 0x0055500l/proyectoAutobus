package modelo;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Motor de simulación. Mantiene listas de estaciones y autobuses,
 * y actualiza el estado cada segundo usando un Timer.
 */
public class Simulador {

    /**
     * Listener para notificar a la capa de presentación sobre cambios en la simulación.
     */
    public interface SimulacionListener {
        /**
         * Notificación de actualización periódica.
         * @param estaciones lista actual de estaciones (inmutable de cara al receptor)
         * @param autobuses lista actual de autobuses (inmutable de cara al receptor)
         */
        void onActualizar(List<Estacion> estaciones, List<Autobus> autobuses);
    }

    private final List<Estacion> estaciones;
    private final List<Autobus> autobuses;
    private Timer timer;
    private boolean enEjecucion;

    private final Random random = new Random();
    private final List<SimulacionListener> listeners = new ArrayList<>();

    /**
     * Crea el simulador con una cantidad de estaciones y autobuses, y capacidad máxima por autobús.
     * La ruta es circular pasando por todas las estaciones.
     *
     * @param numeroEstaciones cantidad de estaciones
     * @param numeroAutobuses cantidad de autobuses
     * @param capacidadMaximaPorAutobus capacidad de cada autobús
     */
    public Simulador(int numeroEstaciones, int numeroAutobuses, int capacidadMaximaPorAutobus) {
        if (numeroEstaciones <= 0) throw new IllegalArgumentException("Debe haber al menos 1 estación");
        if (numeroAutobuses <= 0) throw new IllegalArgumentException("Debe haber al menos 1 autobús");
        if (capacidadMaximaPorAutobus <= 0) throw new IllegalArgumentException("La capacidad debe ser positiva");

        // Crear estaciones
        List<Estacion> ests = new ArrayList<>();
        for (int i = 0; i < numeroEstaciones; i++) {
            ests.add(new Estacion(i, "Estación " + (i + 1)));
        }
        this.estaciones = Collections.unmodifiableList(ests); // ruta fija, referencias constantes

        // Crear autobuses con posiciones iniciales distribuidas
        List<Autobus> buses = new ArrayList<>();
        for (int i = 0; i < numeroAutobuses; i++) {
            int posicionInicial = (i * numeroEstaciones) / numeroAutobuses;
            buses.add(new Autobus(i + 1, capacidadMaximaPorAutobus, ests, posicionInicial));
        }
        this.autobuses = Collections.unmodifiableList(buses);

        this.enEjecucion = false;

        // Preparar timer cada 1 segundo
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarSimulacion();
            }
        });
        this.timer.setInitialDelay(1000);
    }

    /**
     * Inicia la simulación (o la reanuda si estaba pausada).
     */
    public void iniciarSimulacion() {
        if (!enEjecucion) {
            enEjecucion = true;
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    /**
     * Detiene/pausa la simulación.
     */
    public void detenerSimulacion() {
        enEjecucion = false;
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    /**
     * Lógica que corre cada segundo:
     * - Incrementa pasajeros en estaciones (0-5)
     * - Calcula retrasos si hay >20 pasajeros en la siguiente estación
     * - Mueve autobuses (si no están retrasados)
     * - Deja y recoge pasajeros
     * - Notifica a la vista
     */
    public void actualizarSimulacion() {
        // 1) Aumentar pasajeros en estaciones
        for (Estacion est : estaciones) {
            int inc = random.nextInt(6); // 0..5
            est.agregarPasajeros(inc);
        }

        // 2) Para cada autobús, evaluar retrasos y movimiento
        for (Autobus bus : autobuses) {
            // Si la siguiente estación está muy concurrida, agregar retraso
            Estacion siguiente = bus.getSiguienteEstacion();
            if (siguiente.getPasajerosEsperando() > 20) {
                bus.retrasar(1); // 1 segundo extra de retraso
            }

            // Mover si no está retrasado
            boolean seMovio = bus.mover();

            // Si se movió, deja y luego recoge pasajeros en la nueva estación
            if (seMovio) {
                bus.dejarPasajeros();
                bus.recogerPasajeros();
            }
        }

        // 3) Notificar a listeners
        notificarActualizacion();
    }

    private void notificarActualizacion() {
        // Ofrecer vistas inmutables para evitar modificaciones externas accidentales
        List<Estacion> copiaEst = Collections.unmodifiableList(new ArrayList<>(estaciones));
        List<Autobus> copiaBus = Collections.unmodifiableList(new ArrayList<>(autobuses));
        for (SimulacionListener l : listeners) {
            l.onActualizar(copiaEst, copiaBus);
        }
    }

    public void addListener(SimulacionListener listener) {
        if (listener != null) listeners.add(listener);
    }

    public void removeListener(SimulacionListener listener) {
        listeners.remove(listener);
    }

    public boolean isEnEjecucion() {
        return enEjecucion;
    }

    public List<Estacion> getEstaciones() {
        return estaciones;
    }

    public List<Autobus> getAutobuses() {
        return autobuses;
    }
}