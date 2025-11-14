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

Creamos el bin con:
```bash
mkdir -p bin
```

y compilamos si usamos powershell:
```bash
javac -d bin (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

si usamos CMD sera:
```bash
for /R %f in (src*.java) do @echo %f>>sources.txt
```

si lo hacemos desde linux sera:
```bash
javac -d bin $(find src -name "*.java")
```

por ultimo ejecutamos el programa
```bash
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

### Pasos para NetBeans

✅ CÓMO IMPORTAR Y EJECUTAR TU PROYECTO TECHCITY EN NETBEANS

NetBeans NO acepta proyectos sueltos por carpetas… necesita una estructura de proyecto NetBeans propia. solo hay que crear un proyecto vacío y copiar el código.

Aquí estan los pasos exactos 👇

## 🧱 1️⃣ Crear un nuevo proyecto Java en NetBeans

- Abre NetBeans.

- Ve a:
`File → New Project`

- Selecciona:

`Java with Ant
Java Application`

- Presiona Next.

- Escribe:

`Project Name: TechCity`

`Project Location: donde quieras`

- DESMARCA esta opción:
`✔ Create Main Class → desactívala`

- Presiona Finish.

- Esto creará la estructura:

TechCity/
 ├── src/
 ├── build.xml
 ├── manifest.mf
 └── nbproject/

## 📂 2️⃣ Copiar tu código fuente dentro del proyecto NetBeans

- El proyecto original tiene:

```
TechCity/src/modelo/
TechCity/src/vista/
TechCity/src/controlador/
TechCity/src/Main.java
```

- Ahora:

- Copia esas carpetas dentro del src del proyecto NetBeans:

- Tu nuevo NetBeans debe quedar así:

NetBeansProjects/TechCity/src/
 ├── modelo/
 ├── vista/
 ├── controlador/
 └── Main.java


## ➡️ Puedes copiar/pegar las carpetas manualmente desde el Explorador de Windows.

## 🔧 3️⃣ Actualizar los paquetes (si es necesario)

- En NetBeans:

`Abre cada .java`

- Revisa si arriba dice algo como:

`package modelo;`

- SI las carpetas coinciden con los paquetes → NetBeans lo reconocerá automáticamente.

`Si falta, agrégalo donde corresponda.`

- Ejemplo:

`En /modelo/Estacion.java:`

`package modelo;`


`En /vista/PanelMapa.java:`

`package vista;`


- Y así con todos.

## 🚀 4️⃣ Ejecutar el proyecto

- En NetBeans:

- Clic derecho en el proyecto TechCity

- Selecciona Properties

`Ir a Run`

- En "Main Class", selecciona tu clase:

`Main`

- Aceptar.

- Ahora presiona:

▶ RUN (o F6)

Y tu simulador debe abrirse como en tu versión compilada manualmente.
