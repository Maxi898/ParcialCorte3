/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Daniel Alejandro
 */
public abstract class Carta {
    protected String nombre;
    protected int atq;
    protected int def;
    protected Posicion posicion;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getAtq() { return atq; }
    public void setAtq(int atq) { this.atq = atq; }
    public int getDef() { return def; }
    public void setDef(int def) { this.def = def; }
    public Posicion getPosicion() { return posicion; }
    public void setPosicion(Posicion posicion) { this.posicion = posicion; }

    // Polimorfismo de sobreescritura: cada subclase lo implementa
    public abstract String describir();

    @Override
    public String toString() {
        return nombre + " [" + posicion + "] ATQ/" + atq + " DEF/" + def;
    }
}
