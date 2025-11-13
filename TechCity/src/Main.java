import vista.VentanaConfiguracion;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación.
 * Lanza la ventana de configuración en el hilo de eventos de Swing.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaConfiguracion v = new VentanaConfiguracion(); // Crear instancia de la ventana de configuración
            v.setVisible(true); // Mostrar la ventana de configuración
        });
    }
}