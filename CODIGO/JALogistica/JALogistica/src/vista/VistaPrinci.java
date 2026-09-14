/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.util.Scanner;

/**
 *
 * @author Estudiantes
 */
public class VistaPrinci {
    private Scanner teclado = new Scanner(System.in);

    // Dibuja el menú textualmente en la consola
    public void mostrarOpciones() {
        System.out.println("\n=========================================");
        System.out.println("        MENU DE JALogistica    ");
        System.out.println("=========================================");
        System.out.println(" Que desea hacer?");
        System.out.println(" 1. Registrar Conductor");
        System.out.println(" 2. Registrar Vehiculo");
        System.out.println(" 3. Registrar Rutas");
        System.out.println(" 4. Gestionar Recorrido"); 
        System.out.println(" 5. Ver Informacion");
        System.out.println(" 6. Salir de la aplicacion");
        System.out.println("=========================================");
        System.out.print(" Elige una opcion (1-6): ");
    }

    // Captura el número o frase que el usuario escribe en la consola
    //  LEER ENTERO
    public int leerEntero(String mensaje) {
        while (true) {// recorde usar try catch, porque aveces el usario se equivoca escibiendo los datos
            try {
                System.out.print(mensaje); 
                int numero = teclado.nextInt(); 
                teclado.nextLine(); 
                return numero; 
            } catch (Exception e) {
                teclado.nextLine(); 
                System.out.println( "Error: debe ingresar un numero entero." );
            } 
        } 
    }
    //  LEER FLOAT 
    public float leerFloat(String mensaje) {
        while (true) {
            try { 
                System.out.print(mensaje);
                float numero = teclado.nextFloat(); 
                teclado.nextLine();
                return numero; 
            } catch (Exception e) {
                teclado.nextLine(); 
                System.out.println( "Error: debe ingresar un numero." ); 
            } 
        } 
    }
    // LEER TEXTO 
    public String leerTexto(String mensaje) {
        System.out.print(mensaje); 
        return teclado.nextLine(); 
    } 
    // MOSTRAR MENSAJE
    public void mostrarMensaje(String mensaje) {
        System.out.println("\n" + mensaje); 
    } 
    // Métodos para imprimir las respuestas de cada opción
    // MOSTRAR CONDUCTORES 
    public void mostrarConductores(String[] conductores) {
        System.out.println("\n===== CONDUCTORES ====="); 
        for (int i = 0; i < conductores.length; i++) { 
            System.out.println( (i + 1) + ". " + conductores[i] ); 
        } 
    }
    // MOSTRAR VEHICULOS 
    public void mostrarVehiculos(String[] vehiculos) {
        System.out.println("\n===== VEHICULOS ====="); 
        for (int i = 0; i < vehiculos.length; i++) {
            System.out.println( (i + 1) + ". " + vehiculos[i] ); 
        }
    } 
    // MOSTRAR RUTAS
    public void mostrarRutas(String[] rutas) { 
        System.out.println("\n===== RUTAS ====="); 
        for (int i = 0; i < rutas.length; i++) { 
            System.out.println( (i + 1) + ". " + rutas[i] ); 
        }
    }// MOSTRAR RECORRIDO 
    public void mostrarRecorrido( int numero, String conductor, String vehiculo, String ruta,  float tiempo, float costo) { 
        System.out.println( "\n---------- RECORRIDO " + numero + " ----------" ); 
        System.out.println( "Conductor: " + conductor ); 
        System.out.println( "Vehiculo: " + vehiculo ); 
        System.out.println( "Ruta: " + ruta ); 
        System.out.println( "Tiempo estimado: " + tiempo + " horas" ); ;
        System.out.println( "Costo del recorrido: $" + costo );
    }
     // MENSAJE DE SALIDA //
    public void mostrarSalir() {
        System.out.println("\n-----------------------------------------");
        System.out.println("GRACIAS POR USAR LA APLICACION DE JALosgitica");
        System.out.println("-----------------------------------------");
    }
    
}


