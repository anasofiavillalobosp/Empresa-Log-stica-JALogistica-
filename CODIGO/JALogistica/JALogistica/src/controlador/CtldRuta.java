/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.Conductor;
import modelo.Ruta;
import modelo.Vehiculo;
import vista.VistaPrinci;

/**
 *
 * @author Estudiantes
 */
public class CtldRuta {
    private VistaPrinci vista;
    private List<Ruta> rutas;

    // Datos de los recorridos realizados
    private List<Conductor> conductoresRecorrido;
    private List<Vehiculo> vehiculosRecorrido;
    private List<Ruta> rutasRecorrido;
    private List<Float> tiempos;
    private List<Float> costos;
    
    public CtldRuta(VistaPrinci vista, List<Ruta> rutas) {
    this.vista = vista; 
    this.rutas = rutas; 
    //Son nuevas listas para cuanles conductores y vehiculos fueron asigandos a una rutacons sus valores
    conductoresRecorrido = new ArrayList<>(); 
    vehiculosRecorrido = new ArrayList<>(); 
    rutasRecorrido = new ArrayList<>(); 
    tiempos = new ArrayList<>(); 
    costos = new ArrayList<>();
    
}
     // Aqui registro RUTAS
    public void registrarRuta() { 
        String nombre = vista.leerTexto( "Ingrese nombre de la ruta:(ejem: Bogota-Medellin) " ); 
        float km = vista.leerFloat( "Ingrese los kilometros de la ruta:(ejem: 400,0) " );
        if (km <= 0) { 
            vista.mostrarMensaje( "Los kilometros deben ser mayores que cero." );
            return; 
        }
        Ruta ruta = new Ruta(nombre, km); 
        rutas.add(ruta);
        vista.mostrarMensaje( "Ruta registrada correctamente." ); 
    }
    // Muestra datos de las rutas
    public void mostrarRutas() {
        if (rutas.isEmpty()) {
            vista.mostrarMensaje( "No hay rutas registradas." );// por si en el sistema aun no hay rutas
            return; 
        } 
        String[] datos = new String[rutas.size()]; 
        for (int i = 0; i < rutas.size(); i++) {
            datos[i] = rutas.get(i).toString(); //Usando el metodo to string muestra los datos de cada conductor
        }
        vista.mostrarRutas(datos); 
    }
    // GESTIONAR RECORRIDO, validamos si hay rutas, vehiculos y condutores. 
    public void gestionarRecorrido( List<Conductor> conductores, List<Vehiculo> vehiculos) {
        if (conductores.isEmpty()) {
            vista.mostrarMensaje( "Primero debe registrar un conductor." );            
        return; 
    }
        if (vehiculos.isEmpty()) {
            vista.mostrarMensaje( "Primero debe registrar un vehiculo." );
        return;
    }
        if (rutas.isEmpty()) {
            vista.mostrarMensaje( "Primero debe registrar una ruta." ); 
        return; 
    }  
    //Aqui escogemos para cada ruta un conductor y vehiculo
    //CONDUCTOR 
        String[] datosConductores = new String[conductores.size()]; 
        for (int i = 0; i < conductores.size(); i++) { 
            datosConductores[i] = conductores.get(i).toString(); 
    } 
        vista.mostrarConductores(datosConductores); 
        int conductorSeleccionado = vista.leerEntero( "Seleccione el conductor: " ); 
        if (conductorSeleccionado < 1 || conductorSeleccionado > conductores.size()) { 
            vista.mostrarMensaje( "Conductor no valido." );
            return; 
        } 
        Conductor conductor = conductores.get( conductorSeleccionado - 1 ); 
    // VEHICULO 
        String[] datosVehiculos = new String[vehiculos.size()];
        for (int i = 0; i < vehiculos.size(); i++) {
            datosVehiculos[i] = vehiculos.get(i).toString(); 
        } 
        vista.mostrarVehiculos(datosVehiculos);
        int vehiculoSeleccionado = vista.leerEntero( "Seleccione el vehiculo: " ); 
        if (vehiculoSeleccionado < 1 || vehiculoSeleccionado > vehiculos.size()) {
            vista.mostrarMensaje( "Vehiculo no valido." ); 
            return; 
        }
        Vehiculo vehiculo = vehiculos.get( vehiculoSeleccionado - 1 ); 
    // RUTA 
        String[] datosRutas = new String[rutas.size()]; 
        for (int i = 0; i < rutas.size(); i++) {
            datosRutas[i] = rutas.get(i).toString(); 
        } 
        vista.mostrarRutas(datosRutas);
        int rutaSeleccionada = vista.leerEntero( "Seleccione la ruta: " ); 
        if (rutaSeleccionada < 1 || rutaSeleccionada > rutas.size()) { 
            vista.mostrarMensaje( "Ruta no valida." );
            return; 
        }
        Ruta ruta = rutas.get( rutaSeleccionada - 1 );
        
        // VERIFICAR SI EL RECORRIDO YA EXISTE 
        for (int i = 0; i < rutasRecorrido.size(); i++) {
            if (conductoresRecorrido.get(i) == conductor && vehiculosRecorrido.get(i) == vehiculo && rutasRecorrido.get(i) == ruta) {
                vista.mostrarMensaje( "Este recorrido ya fue registrado." ); 
                return; } 
        }
    // Aqui calculamos el tiempo y el costo
    //TIEMPO 
        float tiempo = ruta.getKmRuta() / vehiculo.getVelocidad();
    //CALCULAR COSTO 
        float costo = ruta.getKmRuta() * vehiculo.getTarifaKm(); 
    //GUARDAR RECORRIDO
        conductoresRecorrido.add(conductor);
        vehiculosRecorrido.add(vehiculo); 
        rutasRecorrido.add(ruta);
        tiempos.add(tiempo); 
        costos.add(costo); 
        vista.mostrarMensaje( "Recorrido registrado correctamente." ); 
        vista.mostrarRecorrido( rutasRecorrido.size(), conductor.toString(), vehiculo.toString(), ruta.toString(), tiempo, costo); 
        } 
    // MOSTRAR RECORRIDOS
    public void mostrarRecorridos() { 
        if (rutasRecorrido.isEmpty()) {
        vista.mostrarMensaje("No hay recorridos registrados.");
        return;
        }
        
        for (int i = 0; i < rutasRecorrido.size(); i++) {
            Conductor conductor = conductoresRecorrido.get(i);
            Vehiculo vehiculo = vehiculosRecorrido.get(i);
            Ruta ruta = rutasRecorrido.get(i);
            vista.mostrarRecorrido( i + 1,conductor.toString(),vehiculo.toString(), ruta.toString(), tiempos.get(i), costos.get(i) );
        }
    }
}
