package modelo;

/**
 * Representa una estación dentro de la red de transporte.
 * Mantiene un conteo de pasajeros esperando.
 *
 * @author
 */
public class Estacion {
    private final int id;
    private String nombre;
    private int pasajerosEsperando;

    /**
     * Constructor.
     * @param id Identificador único de la estación
     * @param nombre Nombre de la estación
     */
    public Estacion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.pasajerosEsperando = 0;
    }

    /**
     * Agrega pasajeros a la estación.
     * @param cantidad cantidad de pasajeros a agregar (>=0)
     */
    public void agregarPasajeros(int cantidad) {
        if (cantidad < 0) return;
        this.pasajerosEsperando += cantidad;
    }

    /**
     * Retira pasajeros de la estación (sin quedar negativo).
     * @param cantidad cantidad a retirar
     * @return cantidad realmente retirada
     */
    public int retirarPasajeros(int cantidad) {
        if (cantidad <= 0) return 0;
        int retirados = Math.min(cantidad, this.pasajerosEsperando);
        this.pasajerosEsperando -= retirados;
        return retirados;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPasajerosEsperando() {
        return pasajerosEsperando;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPasajerosEsperando(int pasajerosEsperando) {
        this.pasajerosEsperando = Math.max(0, pasajerosEsperando);
    }

    @Override
    public String toString() {
        return "Estacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pasajerosEsperando=" + pasajerosEsperando +
                '}';
    }
}