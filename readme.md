# TechCity - Simulación de una Red de Transporte Inteligente

Aplicación de escritorio en Java Swing que simula una red de transporte en una ciudad ficticia. El usuario puede configurar la cantidad de estaciones y autobuses, observar su movimiento en un mapa y ver las métricas en tiempo real.

## Estructura

```
TechCity/
 ├── src/
 │    ├── modelo/
 │    │    ├── Estacion.java
 │    │    ├── Autobus.java
 │    │    └── Simulador.java
 │    │
 │    ├── vista/
 │    │    ├── VentanaConfiguracion.java
 │    │    ├── VentanaSimulacion.java
 │    │    └── PanelMapa.java
 │    │
 │    ├── controlador/
 │    │    └── ControladorSimulacion.java
 │    │
 │    └── Main.java
 │
 ├── resources/
 │    └── iconos/ (opcional)
 │
 └── README.md
```

## Requisitos

- Java 8 o superior
- No requiere frameworks externos

## Compilación y ejecución

Desde la carpeta `TechCity/`:

```bash
javac -d bin src/**/*.java
java -cp bin Main
```

Si usas Windows PowerShell, podrías compilar con:

```powershell
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName } | javac -d bin -classpath bin @-
java -cp bin Main
```

## Descripción funcional

- Ventana de configuración inicial para:
  - Número de estaciones
  - Número de autobuses
  - Capacidad máxima por autobús
- Ventana de simulación:
  - Mapa con estaciones (círculos) y conexiones (líneas)
  - Autobuses moviéndose (rectángulos)
  - Panel de datos con:
    - Pasajeros esperando por estación
    - Ocupación y disponibilidad por autobús
  - Botones “Pausar” y “Reanudar”

## Lógica de simulación

Cada segundo:

- Incrementa 0–5 pasajeros por estación
- Si la siguiente estación de un bus tiene >20 pasajeros, el bus gana 1 segundo de retraso
- El bus se mueve a la siguiente estación si no está retrasado
- Al llegar, deja una parte de pasajeros y recoge hasta su capacidad
- Se repinta el mapa y se actualizan las tablas

## Créditos

- Desarrollado para la especificación “Simulación de una Red de Transporte Inteligente – TechCity”
- Requisitos: Java 8+