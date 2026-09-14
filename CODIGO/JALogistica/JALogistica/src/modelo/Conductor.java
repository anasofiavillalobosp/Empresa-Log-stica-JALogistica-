/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Estudiantes
 */
public class Conductor {
    private String nomCond;
    private String C_C;

    public Conductor(String nomCond, String C_C) {
        this.nomCond = nomCond;
        this.C_C = C_C;
    }

    public String getNomCond() {
        return nomCond;
    }

    public void setNomCond(String nomCond) {
        this.nomCond = nomCond;
    }

    public String getC_C() {
        return C_C;
    }

    public void setC_C(String C_C) {
        this.C_C = C_C;
    }

    @Override
    public String toString() {
        return "{" + "nomConductor= " + nomCond + ", C_C= " + C_C + '}';
    }
       
}
