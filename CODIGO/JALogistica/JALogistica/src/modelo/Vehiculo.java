/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Estudiantes
 */
public class Vehiculo {
    private String tipoVehi;
    private float velocidad;
    private String matricula;
    private String marca;
    private float tarifaKm;

    public Vehiculo(String tipoVehi, float velocidad, String matricula, String marca, float tarifaKm) {
        this.tipoVehi = tipoVehi;
        this.velocidad = velocidad;
        this.matricula = matricula;
        this.marca = marca;
        this.tarifaKm = tarifaKm;
    }

    public String getTipoVehi() {
        return tipoVehi;
    }

    public void setTipoVehi(String tipoVehi) {
        this.tipoVehi = tipoVehi;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getTarifaKm() {
        return tarifaKm;
    }

    public void setTarifaKm(float tarifaKm) {
        this.tarifaKm = tarifaKm;
    }

    @Override
    public String toString() {
        return "{" + "tipoVehiculo= " + tipoVehi + ", velocidad= " + velocidad + " Km/h" + ", matricula= " + matricula + ", marca= " + marca + ", tarifaKm= $" + tarifaKm + '}';
    }

    
       
}
