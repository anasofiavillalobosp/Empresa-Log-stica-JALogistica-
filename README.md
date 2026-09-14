<div align="center">

<img src="resources/imagenes/amul.png" width="100%" style="max-width: 800px;" alt="AMUL Header"/>

## Empresa-Logistica-JALogistica-

**  Programación Avanzada** Universidad Distrital Francisco José De Caldas · Septiembre 2026

</div>

<div align="center">
  <img src="https://img.shields.io/badge/Plataforma-NetLogo-4CAF50?style=flat-square" alt="NetLogo"/>
  <img src="https://img.shields.io/badge/Paradigma-ABM%20%2F%20MOBA-2196F3?style=flat-square" alt="ABM"/>
  <img src="https://img.shields.io/badge/Agentes-5%20tipos-FF9800?style=flat-square" alt="Agentes"/>
  <img src="https://img.shields.io/badge/Calidad%20inicial-95%25-brightgreen?style=flat-square" alt="Calidad"/>
  <img src="https://img.shields.io/badge/Tiempo%20límite-32%20horas-red?style=flat-square" alt="Tiempo"/>
</div>

---

## 👥 Equipo

| Nombre | Código |
| :--- | :---: |
| Ana Sofía Villalobos Pérez | 20242020063 |

---

## 📌 Descripción del Proyecto

Este proyecto implementa una **simulación multi-agente (MOBA)** del proceso de transporte y distribución de leche dentro de la cadena de suministro de **AMUL**, una de las cooperativas lácteas más grandes del mundo, con sede en India.

La simulación modela el recorrido del producto desde las zonas rurales de producción (**Punjab**) hasta las tiendas finales de consumo (**Noida, B y C**), pasando por un distribuidor principal (**Delhi**). En tiempo real se monitorizan variables críticas como **calidad del producto**, **temperatura** y **tiempos de entrega**, permitiendo identificar cuellos de botella y puntos de degradación en la cadena de frío.

---

## ❓ Pregunta de Investigación

<div align="center">

> **¿Cómo optimizar el proceso de transporte de leche en la cadena de suministro de AMUL para minimizar los tiempos de transporte y evitar la pérdida de calidad (cadena de frío)?**

</div>

---

## 🎯 Objetivo General

Diseñar y ejecutar una simulación basada en agentes que permita identificar cuellos de botella logísticos, medir el impacto de fallos en la cadena de frío, y proponer estrategias de optimización para garantizar que el producto llegue a su destino final dentro de los estándares de calidad y tiempo establecidos.

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
AMUL_MOBA/
│
├── README.md
├── docs/
│   ├── AMUL_MOBA.docx
│   └── diagramas/
│       ├── red_geografica.png
│       ├── diagrama_secuencia.png
│       ├── modelos_estado.png
│       └── diagrama_global.png
│
├── simulacion/
│   └── AMUL_MOBA.nlogo
│
└── resultados/
    └── metricas.csv

```

---

## 🚀 Cómo Ejecutar la Simulación

1. Descargar e instalar **[NetLogo](https://ccl.northwestern.edu/netlogo/)**.
2. Abrir el archivo `simulacion/AMUL_MOBA.nlogo`.
3. Crear los sliders en la interfaz para los parámetros configurables listados arriba (o usar los valores por defecto del código).
4. Añadir monitores para las variables globales: `rechazados`, `fallidos`, `exitosos`, `tiempo-global`, `count-lotes`.
5. Presionar **Setup** para inicializar el entorno y los agentes.
6. Presionar **Go** (en modo *forever*) para correr la simulación.
7. Observar en tiempo real la etiqueta de calidad sobre cada lote y los contadores en los monitores.

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
