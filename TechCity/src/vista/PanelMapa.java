package vista;

import modelo.Autobus;
import modelo.Estacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Panel que dibuja el mapa: estaciones (nodos), conexiones (líneas) y autobuses (rectángulos).
 */
public class PanelMapa extends JPanel {

    private List<Estacion> estaciones = Collections.emptyList();
    private List<Autobus> autobuses = Collections.emptyList();

    // Posiciones calculadas de cada estación (index -> Point)
    private final Map<Integer, Point> posicionesEstaciones = new HashMap<>();

    public PanelMapa() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 400));
        setDoubleBuffered(true);
    }

    /**
     * Recibe los datos y solicita repintar.
     */
    public void setDatos(List<Estacion> estaciones, List<Autobus> autobuses) {
        this.estaciones = estaciones != null ? estaciones : Collections.emptyList();
        this.autobuses = autobuses != null ? autobuses : Collections.emptyList();
        recalcularPosiciones();
        repaint();
    }

    private void recalcularPosiciones() {
        posicionesEstaciones.clear();
        int n = estaciones.size();
        if (n == 0) return;

        // Distribuir en círculo
        int w = Math.max(1, getWidth());
        int h = Math.max(1, getHeight());
        int radio = (int) (Math.min(w, h) * 0.4);
        int cx = w / 2;
        int cy = h / 2;

        for (int i = 0; i < n; i++) {
            double ang = 2 * Math.PI * i / n;
            int x = cx + (int) (radio * Math.cos(ang));
            int y = cy + (int) (radio * Math.sin(ang));
            posicionesEstaciones.put(i, new Point(x, y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Antialiasing
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujar conexiones de la ruta (asumimos ruta circular por índice)
            dibujarConexiones(g2);

            // Dibujar estaciones
            dibujarEstaciones(g2);

            // Dibujar autobuses
            dibujarAutobuses(g2);
        } finally {
            g2.dispose();
        }
    }

    private void dibujarConexiones(Graphics2D g2) {
        int n = estaciones.size();
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(180, 180, 180));
        for (int i = 0; i < n; i++) {
            Point a = posicionesEstaciones.get(i);
            Point b = posicionesEstaciones.get((i + 1) % n);
            if (a != null && b != null) {
                g2.drawLine(a.x, a.y, b.x, b.y);
            }
        }
    }

    private void dibujarEstaciones(Graphics2D g2) {
        int n = estaciones.size();
        int r = 14;
        for (int i = 0; i < n; i++) {
            Point p = posicionesEstaciones.get(i);
            if (p == null) continue;
            Estacion est = estaciones.get(i);

            // Color según nivel de pasajeros
            int espera = est.getPasajerosEsperando();
            Color c = espera > 20 ? new Color(220, 80, 80) : new Color(80, 140, 220);
            g2.setColor(c);
            g2.fillOval(p.x - r, p.y - r, 2 * r, 2 * r);
            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawOval(p.x - r, p.y - r, 2 * r, 2 * r);

            // Nombre
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12f));
            String nombre = est.getNombre() + " (" + espera + ")";
            int sw = g2.getFontMetrics().stringWidth(nombre);
            g2.drawString(nombre, p.x - sw / 2, p.y - r - 6);
        }
    }

    private void dibujarAutobuses(Graphics2D g2) {
        int wBus = 18;
        int hBus = 12;

        int idx = 0;
        for (Autobus bus : autobuses) {
            Estacion estActual = bus.getEstacionActual();

            // Buscar índice de estación actual por id
            int index = Math.max(0, estActual.getId());
            Point p = posicionesEstaciones.get(index);
            if (p == null) continue;

            // Offset para que varios buses en la misma estación no se encimen
            int offset = (idx % 4) * 5 - 8;
            int x = p.x + offset - wBus / 2;
            int y = p.y + offset - hBus / 2;

            // Color del bus
            g2.setColor(new Color(60, 180, 75, 220));
            g2.fillRoundRect(x, y, wBus, hBus, 6, 6);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRoundRect(x, y, wBus, hBus, 6, 6);

            // ID del bus
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 10f));
            String text = String.valueOf(bus.getId());
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 4, y + hBus - 3);

            idx++;
        }
    }
}