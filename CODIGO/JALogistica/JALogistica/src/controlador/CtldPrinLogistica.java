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
 * @author Usuario
 */
public class CtldPrinLogistica {

    private VistaPrinci vista; 
    private CtldConductor ctldConductor; 
    private CtldVehiculo ctldVehiculo; 
    private CtldRuta ctldRuta; 
    
    public CtldPrinLogistica() { 
    // Crear la vista Principal 
    vista = new VistaPrinci();
    // Listas principales de los datos de la empresa
    List<Conductor> conductores = new ArrayList<>();
    List<Vehiculo> vehiculos = new ArrayList<>();
    List<Ruta> rutas = new ArrayList<>();
    // Necesito crear los controladores para que comience el flujo completo 
    ctldConductor = new CtldConductor( vista, conductores );
    ctldVehiculo = new CtldVehiculo( vista, vehiculos ); 
    ctldRuta = new CtldRuta( vista, rutas ); 
    } 
    public void iniciarLogistica() {
        int opcion; 
        do {
            vista.mostrarOpciones();
            opcion = vista.leerEntero( "Elige una opcion: " );
            switch (opcion) {
                case 1:
                    ctldConductor.registrarConductor();
                break; 
                case 2: 
                    ctldVehiculo.registrarVehiculo(); 
                break; 
                case 3: 
                    ctldRuta.registrarRuta();
                break;
                case 4: 
                    ctldRuta.gestionarRecorrido( ctldConductor.getConductores(), ctldVehiculo.getVehiculos() );
                break; 
                case 5: 
                    mostrarInformacion(); 
                break; 
                case 6: 
                    vista.mostrarSalir(); 
                break; 
                default: vista.mostrarMensaje( "Opcion no valida." ); 
            } 
        } while (opcion != 6);
    } 
    private void mostrarInformacion() { 
        ctldConductor.mostrarConductores(); 
        ctldVehiculo.mostrarVehiculos();
        ctldRuta.mostrarRutas(); 
        ctldRuta.mostrarRecorridos(); } 
}

