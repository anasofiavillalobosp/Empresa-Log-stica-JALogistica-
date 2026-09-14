/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Estudiantes
 */
public class Ruta {
    private String nombreRuta;
    private float kmRuta;
    private float tarifa;

    public Ruta(String nombreRuta, float kmRuta) {
        this.nombreRuta = nombreRuta;
        this.kmRuta = kmRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public float getKmRuta() {
        return kmRuta;
    }

    public void setKmRuta(float kmRuta) {
        this.kmRuta = kmRuta;
    }

    @Override
    public String toString() {
        return "{" + "nombreRuta= " + nombreRuta + ", kmRuta= " + kmRuta + " km" +'}';
    }
       
}
