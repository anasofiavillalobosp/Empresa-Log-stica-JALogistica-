/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import modelo.Vehiculo;
import vista.VistaPrinci;

/**
 *
 * @author Usuario
 */
public class CtldVehiculo {
    private VistaPrinci vista;
    private List<Vehiculo> vehiculos; 
    
    public CtldVehiculo( VistaPrinci vista, List<Vehiculo> vehiculos) { 
        this.vista = vista; 
        this.vehiculos = vehiculos; 
    } 
    // Aqui registro VEHICULO 
    public void registrarVehiculo() { 
        String tipo = vista.leerTexto( "Ingrese tipo de vehiculo:(ejem: Moto, Auto, Remolque, etc) " ); 
        float velocidad = vista.leerFloat( "Ingrese velocidad prom:(ejem: 36.0, 42.50) " ); //Buscamos un caso normal(osea cuanto se demora en prom en llegar)
         if (velocidad <= 0) { 
            vista.mostrarMensaje( "La velocidad debe ser mayor que cero." );
            return; 
        }
        String matricula = vista.leerTexto( "Ingrese matricula:(ejem: ABC123) " ); 
        String marca = vista.leerTexto( "Ingrese marca:(ejem: Suzuki, Ford, etc) " ); 
        float tarifaKm = vista.leerFloat( "Ingrese tarifa por kilometro:(ejem: 2500.0, 5000.0) " );
        Vehiculo vehiculo = new Vehiculo( tipo, velocidad, matricula, marca, tarifaKm ); 
        vehiculos.add(vehiculo); 
        vista.mostrarMensaje( "Vehiculo registrado correctamente." ); 
    } 
    // Muestra datos de vehiculos 
    public void mostrarVehiculos() { 
        if (vehiculos.isEmpty()) { 
            vista.mostrarMensaje( "No hay vehiculos registrados." ); // por si en el sistema aun no hay vehiculos
            return; 
        } 
        String[] datos = new String[vehiculos.size()]; 
        for (int i = 0; i < vehiculos.size(); i++) { 
            datos[i] = vehiculos.get(i).toString();  //Usando el metodo to string muestra los datos de cada vehiculo
        }
        vista.mostrarVehiculos(datos); 
    } 
    public List<Vehiculo> getVehiculos() { 
        return vehiculos; 
    } 
}

