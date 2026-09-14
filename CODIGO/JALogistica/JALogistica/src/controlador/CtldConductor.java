/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import modelo.Conductor;
import vista.VistaPrinci;

/**
 *
 * @author Usuario
 */
public class CtldConductor {
    private VistaPrinci vista;
    private List<Conductor> conductores; 
    
    public CtldConductor( VistaPrinci vista, List<Conductor> conductores) {       
        this.vista = vista; 
        this.conductores = conductores; 
    } 
    // Aqui registro CONDUCTOR 
    public void registrarConductor() { 
        String nombre = vista.leerTexto( "Ingrese nombre del conductor:(ejem: Carlos Perez) "  ); 
        String cedula = vista.leerTexto( "Ingrese cedula:(ejem: 10203040) "  ); //no hay restricciones porque hay personas con 8 o 10 dicgitos de C.C
        Conductor conductor = new Conductor(nombre, cedula); 
        conductores.add(conductor); 
        vista.mostrarMensaje( "Conductor registrado correctamente." ); 
    } 
    // Muestra datos de conductores 
    public void mostrarConductores() {
        if (conductores.isEmpty()) {// por si en el sistema aun no hay conductores
            vista.mostrarMensaje( "No hay conductores registrados." );
            return; 
        } 
        String[] datos = new String[conductores.size()]; 
        for (int i = 0; i < conductores.size(); i++) {
            datos[i] = conductores.get(i).toString(); //Usando el metodo to string muestra los datos de cada conductor
        } 
        vista.mostrarConductores(datos); 
    } 
    
    public List<Conductor> getConductores() {
        return conductores; 
    } 
}
