<div align="center">

<img src="resources/imagenes/amul.png" width="100%" style="max-width: 800px;" alt="AMUL Header"/>

## Empresa-Logistica-JALogistica-

**  Programación Avanzada** Universidad Distrital Francisco José De Caldas · Septiembre 2026

</div>

<div align="center"> <img src="https://img.shields.io/badge/Plataforma-Java-orange?style=flat-square" alt="Java"/> <img src="https://img.shields.io/badge/Arquitectura-MVC-2196F3?style=flat-square" alt="MVC"/> <img src="https://img.shields.io/badge/Interfaz-Consola-4CAF50?style=flat-square" alt="Consola"/> 
</div>

---

## 👥 Equipo

| Nombre | Código |
| :--- | :---: |
| Ana Sofía Villalobos Pérez | 20242020063 |

---

## 📌 Descripción del Proyecto

Este proyecto implementa un sistema de gestión logística de transporte desarrollado en Java, utilizando los principios de la **arquitectura Modelo-Vista-Controlador (MVC)**.

El sistema permite registrar y administrar conductores, vehículos y rutas, además de gestionar los recorridos realizados mediante la combinación de estos elementos.

Para cada recorrido registrado, el sistema calcula automáticamente el tiempo estimado de viaje a partir de los kilómetros de la ruta y la velocidad del vehículo, así como el costo del recorrido utilizando la tarifa por kilómetro del vehículo.

---

## ❓ Pregunta de Investigación

<div align="center">

> **¿Cómo desarrollar un sistema de gestión logística que permita administrar conductores, vehículos y rutas, facilitando el registro de recorridos y el cálculo automático de tiempos y costos de transporte?**

</div>

---

## 🏗️ Arquitectura del Sistema

El sistema está compuesto por **5 tipos de agentes** (`breeds`) que interactúan en un entorno geográfico abstracto inspirado en la red logística real de AMUL en India.

<div align="center">


```

🏡 Productores (Punjab / b / c)
↓
🚛 Vehículos (Camiones Cisterna)
↓
🏭 Distribuidor Principal (Delhi)
↓
🚛 Vehículos (Camiones Cisterna)
↓
🏪 Tiendas Finales (Noida / B / C)

```

</div>

El flujo es **unidireccional**: los lotes de leche nacen en los productores, son recogidos por los camiones cisterna, pasan por el distribuidor principal para control de calidad y almacenamiento, y finalmente se despachan a las tiendas finales.

---

## 🤖 Agentes del Sistema

### 1. 🏡 Agente `productor`

Representa el origen de la leche en las zonas rurales. La simulación incluye **3 productores** ubicados en distintas coordenadas del entorno (Punjab, b y c).

| Variable | Descripción |
| :--- | :--- |
| `tiene-lote?` | Indica si el productor ya tiene un lote activo esperando ser recogido (`true` / `false`) |

**Comportamiento:**
- Si `tiene-lote? = false`, genera un nuevo lote (`hatch-lotes`) con calidad inicial de **95%** y temperatura de **4°C**.
- Una vez creado el lote, `tiene-lote?` cambia a `true` hasta que un camión lo recoja, momento en que vuelve a `false`.
- El destino inicial de cada lote creado siempre es el **Distribuidor Principal (Delhi)**.

---

### 2. 🚛 Agente `vehiculo`

Representa los camiones cisterna refrigerados que transportan los lotes entre los nodos de la red. La simulación inicializa **3 vehículos**.

| Variable | Descripción |
| :--- | :--- |
| `lote-cargado` | Referencia al lote (`turtle`) que transporta actualmente. Valor `nobody` si está vacío |
| `estado-camion` | Estado actual: `"Esperando Carga"`, `"En Transito"` o `"En Alerta"` |

**Reglas: (prioridades de movimiento):**

1. **Si está vacío → Prioridad 1:** Busca lotes con estado `"Despachado"` en el Centro y se dirige a recogerlos.
2. **Si está vacío → Prioridad 2:** Si no hay lotes despachados, busca lotes con estado `"Creado"` en los productores.
3. **Si está cargado:** Se dirige al destino asignado al lote (`destino`). Al llegar:
   - Si el destino es un **Centro**: descarga el lote para control de calidad.
   - Si el destino es una **Tienda**: descarga el lote y registra el resultado final (éxito o fallo).

El camión se mueve a una velocidad constante definida por el parámetro `velocidad-vehiculo`.

---

### 3. 🏭 Agente `centro`

Nodo central de distribución y control de calidad. Representa el **Distribuidor Principal de Delhi**. Solo existe **1 centro** en la simulación.

**Reglas: (lógica aplicada sobre los lotes al llegar):**
- `SI calidad < calidad-minima` → El lote se **rechaza** (`rechazados + 1`) y se elimina del sistema (`die`).
- `SI calidad >= calidad-minima` → El lote es aceptado y pasa a estado `"En Centro"` para almacenamiento.
- Durante el almacenamiento, el lote permanece entre **4 y 12 horas** (definido por `tiempo-almacen`, aleatorio por lote), con una degradación de calidad por hora definida por `degradacion-almacen`.
- Al cumplir el tiempo de almacenamiento, el lote pasa a estado `"Despachado"` y se le asigna una tienda de destino aleatoria.

---

### 4. 🏪 Agente `tienda`

Punto de consumo final. Agente "sumidero" donde termina el ciclo del lote y se evalúan los resultados. La simulación inicializa **3 tiendas** (Noida, B y C).

**Reglas: (evaluación al recibir un lote):**

| Condición | Resultado |
| :---: | :---: |
| `tiempo-total < 32` **Y** `calidad >= calidad-final` | ✅ `exitosos + 1` |
| `tiempo-total >= 32` **O** `calidad < calidad-final` | ❌ `fallidos + 1` |

Tras la evaluación, el lote se elimina del sistema (`die`).

---

### 5. 📦 Agente `lote`

Representa la unidad de leche en tránsito. Es el agente central de la simulación: nace en un productor y muere al llegar a una tienda o ser rechazado en el centro.

| Variable | Valor inicial | Descripción |
| :--- | :---: | :--- |
| `calidad` | 95% | Porcentaje de calidad del producto |
| `temperatura` | 4°C | Temperatura de conservación |
| `tiempo-total` | 0 | Horas acumuladas desde la producción |
| `tiempo-almacenado` | 0 | Horas acumuladas dentro del Centro |
| `estado` | `"Creado"` | Estado actual del lote en la cadena |
| `destino` | Centro | Nodo destino asignado |
| `productor-origen` | — | Referencia al productor que lo generó |
| `meta-almacenado` | 4–12 h | Tiempo aleatorio de permanencia en el Centro |

**Estados posibles:** `"Creado"` → `"En Transito"` → `"En Centro"` → `"Despachado"` → `"En Transito"` → `"Entregado"` (o `"Rechazado"`)

**Reglas de degradación de calidad:**

- **En tránsito (normal):** `calidad = calidad - (degradacion-frio × temperatura-ambiente / 100)`
- **En tránsito (alerta):** Si `temperatura-ruta > 4°C`, la degradación sube a `degradacion-alerta` (entre 2% y 10%) y el lote se muestra en rojo.
- **En almacenamiento:** `calidad = calidad - degradacion-almacen` por cada hora en el centro.
- La calidad nunca baja de 0.

---

## ⚙️ Parámetros Configurables (Sliders)

| Parámetro | Descripción |
| :--- | :--- |
| `tiempo-maximo` | Duración total de la simulación en ticks (horas). Valor por defecto: **500** |
| `degradacion-frio` | Tasa de degradación base durante el transporte refrigerado. Valor por defecto: **0.5** |
| `temperatura-ambiente` | Temperatura ambiental exterior (°C). Valor por defecto: **25** |
| `temperatura-ruta` | Temperatura del lote durante el trayecto (°C). Si supera 4°C, activa modo alerta. Valor por defecto: **2** |
| `calidad-minima` | Umbral mínimo de calidad (%) para aceptar un lote en el Centro. Valor por defecto: **80** |
| `velocidad-vehiculo` | Velocidad de desplazamiento de los camiones (unidades/tick). Valor por defecto: **1.5** |
| `degradacion-alerta` | Degradación aplicada en modo alerta (temperatura > 4°C) |
| `degradacion-almacen` | Degradación por hora durante el almacenamiento en el Centro |
| `tiempo-almacen` | Tiempo de permanencia en el Centro (entre 4 y 12 horas, aleatorio por lote) |
| `calidad-final` | Umbral mínimo de calidad (%) exigido en la tienda para contar como éxito |

> **Nota:** `tiempo-maximo`, `degradacion-frio`, `temperatura-ambiente`, `temperatura-ruta`, `calidad-minima` y `velocidad-vehiculo` tienen valores por defecto en el código y se sobreescriben automáticamente si se crean los sliders correspondientes en la interfaz de NetLogo.

---

## 📏 Métricas Globales (Monitores)

| Variable | Descripción |
| :--- | :--- |
| `rechazados` | Lotes rechazados en el Distribuidor por baja calidad |
| `fallidos` | Lotes que llegaron a la tienda fuera de tiempo o con calidad insuficiente |
| `exitosos` | Lotes entregados correctamente (`tiempo < 32 h` y `calidad >= calidad-final`) |
| `tiempo-global` | Tick actual (hora de simulación) |
| `count-lotes` | Número de lotes activos en el sistema en cada momento |

---

## 📏 Criterios de Éxito y Fallo

| Resultado | Condición |
| :---: | :--- |
| ✅ **Éxito** | `tiempo-total < 32 horas` **Y** `calidad final >= calidad-final` |
| ❌ **Fallo** | `tiempo-total >= 32 horas` **O** `calidad final < calidad-final` |
| 🚫 **Rechazo** | `calidad < calidad-minima` al llegar al Distribuidor |

---

## 🗺️ Entorno de Simulación

<div align="center">

> **1 tick = 1 hora real**

| Nodo | Coordenadas (x, y) |
|:---|:---:|
| Productor Punjab | (0, 15) |
| Productor B | (-12, 15) |
| Productor C | (4, 12) |
| Distribuidor Principal (Delhi) | (0, 0) |
| Tienda Noida | (0, -15) |
| Tienda B | (-12, -15) |
| Tienda C | (12, -15) |

</div>

---

## ⚙️ Tecnología y Herramientas

| Ítem | Detalle |
| :---: | :---: |
| **Paradigma** | Simulación Basada en Agentes (ABM) |
| **Plataforma** | NetLogo |
| **Enfoque** | MOBA (Multi-Object Based Architecture) |
| **Metodología** | Análisis y Diseño de Sistemas |

---

## 📁 Estructura del Proyecto

```text
JALogistica/
│
├── src/
│    │
│    ├── controlador/ │ │ ├── CtlPrincipal.java │ │ ├── CtldConductor.java │ │ ├── CtldVehiculo.java │ │ └── CtldRuta.java │ │ │ ├── modelo/ │ │ ├── Conductor.java │ │ ├── Vehiculo.java │ │ └── Ruta.java │ │ │ ├── vista/ │ │ └── VistaPrinci.java │ │ │ └── JALogistica.java │ ├── README.md │ └── docs/ └── diagramas/ └── ...

```

---

## 🚀 Cómo Ejecutar la Simulación

Abrir el proyecto JALogistica en NetBeans.
Verificar que los paquetes modelo, vista y controlador estén correctamente organizados.
Ejecutar la clase JALogistica.
El programa iniciará el CtlPrincipal.
Utilizar el menú principal para seleccionar las diferentes opciones.
Registrar los conductores, vehículos y rutas necesarios.
Registrar los recorridos seleccionando los elementos disponibles.
Consultar los recorridos y los cálculos realizados.

---

## 📈 Indicadores Generados

Al finalizar la simulación se pueden extraer los siguientes indicadores:

| Indicador | Descripción |
| --- | --- |
| ⏱️ Tiempo total de tránsito | Horas desde el productor hasta la tienda por lote |
| 🧪 Calidad final del producto | Porcentaje al llegar a la tienda |
| 🚫 Tasa de rechazo | Lotes rechazados en el Distribuidor sobre el total generado |
| ✅ Tasa de éxito | Lotes exitosos sobre el total procesado |
| ❌ Tasa de fallo | Lotes fallidos (tiempo o calidad) sobre el total entregado |
| 🌡️ Puntos críticos de degradación | Tramos donde la calidad cae más según el modo de alerta |

---

<h2>1. Diagrama de Clases</h2>
<p align="center">
  <img src="docs/diagramas/Estados.jpg" width="800">
</p>

---

## 📚 Referencias

* Cooperativa AMUL — [amul.com](https://www.amul.com)
* NetLogo — [ccl.northwestern.edu/netlogo](https://ccl.northwestern.edu/netlogo/)
* Metodología de Simulación Basada en Agentes (ABM)
* Cadena de frío en productos lácteos — FAO / OMS

---

**Universidad Distrital Francisco José De Caldas** Análisis y Diseño de Sistemas · Mayo 2026
