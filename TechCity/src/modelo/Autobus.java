package modelo;

import java.util.List;
import java.util.Random;

/**
 * Representa un autobús que recorre una ruta de estaciones.
 * Realiza acciones de mover, recoger y dejar pasajeros.
 */
public class Autobus {
    private final int id;
    private final int capacidadMaxima;
    private int pasajerosActuales;
    private final List<Estacion> ruta;
    private int posicionActual; // índice en la ruta
    private int tiempoSiguienteEstacion; // ticks de espera (simulación de retraso)

    private final Random random = new Random();

    /**
     * Constructor.
     * @param id id del autobús
     * @param capacidadMaxima capacidad máxima de pasajeros
     * @param ruta lista de estaciones que conforman la ruta (circular)
     * @param posicionInicial índice inicial en la ruta
     */
    public Autobus(int id, int capacidadMaxima, List<Estacion> ruta, int posicionInicial) {
        if (ruta == null || ruta.isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede ser nula ni vacía");
        }
        this.id = id;
        this.capacidadMaxima = Math.max(0, capacidadMaxima);
        this.ruta = ruta;
        this.posicionActual = Math.floorMod(posicionInicial, ruta.size());
        this.pasajerosActuales = 0;
        this.tiempoSiguienteEstacion = 0;
    }

    /**
     * Mueve el autobús a la siguiente estación de la ruta si no está retrasado.
     * Si está retrasado, decrementa el tiempo de espera.
     * @return true si se movió, false si permaneció esperando
     */
    public boolean mover() {
        if (tiempoSiguienteEstacion > 0) {
            tiempoSiguienteEstacion--;
            return false;
        }
        posicionActual = (posicionActual + 1) % ruta.size();
        return true;
    }

    /**
     * Recoge pasajeros en la estación actual hasta completar la capacidad.
     * @return cantidad recogida
     */
    public int recogerPasajeros() {
        Estacion est = getEstacionActual();
        int espacioDisponible = capacidadMaxima - pasajerosActuales;
        if (espacioDisponible <= 0) return 0;

        int tomados = est.retirarPasajeros(espacioDisponible);
        pasajerosActuales += tomados;
        return tomados;
    }

    /**
     * Deja pasajeros en la estación actual.
     * Para simplificar, se simula que algunos pasajeros han llegado a su destino y bajan.
     * @return cantidad que bajó
     */
    public int dejarPasajeros() {
        if (pasajerosActuales <= 0) return 0;
        // Deja entre 0 y hasta el 30% de los pasajeros en promedio (al menos 0, a lo sumo todos).
        int maxBajan = Math.max(1, (int)Math.ceil(pasajerosActuales * 0.3));
        int bajan = Math.min(pasajerosActuales, random.nextInt(maxBajan + 1));
        pasajerosActuales -= bajan;
        // Nota: No se suman a la estación ya que se asume que llegaron a destino.
        return bajan;
    }

    /**
     * Indica un retraso adicional (en ticks/segundos).
     * @param segundos segundos a retrasar
     */
    public void retrasar(int segundos) {
        if (segundos > 0) {
            tiempoSiguienteEstacion += segundos;
        }
    }

    public Estacion getEstacionActual() {
        return ruta.get(posicionActual);
    }

    public Estacion getSiguienteEstacion() {
        return ruta.get((posicionActual + 1) % ruta.size());
    }

    public int getId() {
        return id;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getPasajerosActuales() {
        return pasajerosActuales;
    }

    public void setPasajerosActuales(int pasajerosActuales) {
        this.pasajerosActuales = Math.max(0, Math.min(pasajerosActuales, capacidadMaxima));
    }

    public List<Estacion> getRuta() {
        return ruta;
    }

    public int getPosicionActual() {
        return posicionActual;
    }

    public void setPosicionActual(int posicionActual) {
        this.posicionActual = Math.floorMod(posicionActual, ruta.size());
    }

    public int getTiempoSiguienteEstacion() {
        return tiempoSiguienteEstacion;
    }

    public void setTiempoSiguienteEstacion(int tiempoSiguienteEstacion) {
        this.tiempoSiguienteEstacion = Math.max(0, tiempoSiguienteEstacion);
    }

    @Override
    public String toString() {
        return "Autobus{" +
                "id=" + id +
                ", capacidadMaxima=" + capacidadMaxima +
                ", pasajerosActuales=" + pasajerosActuales +
                ", estacionActual=" + getEstacionActual().getNombre() +
                ", tiempoSiguienteEstacion=" + tiempoSiguienteEstacion +
                '}';
    }
}