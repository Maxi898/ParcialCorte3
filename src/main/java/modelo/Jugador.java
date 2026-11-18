/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Daniel Alejandro
 */
import java.util.ArrayList;

public class Jugador implements Daniable {

    public static final int LP_INICIAL = 8000;

    private String nombre;
    private int lp;
    private ArrayList<Carta> campo = new ArrayList<>();

    public void iniciar(String nombre) {
        this.nombre = nombre;
        this.lp = LP_INICIAL;
        campo.clear();
    }

    @Override
    public void recibirDano(int dano) {
        lp = lp - dano;
        if (lp < 0) {
            lp = 0;
        }
    }

    public int lpActual() { return lp; }
    public String nombreJugador() { return nombre; }
    public boolean estaDerrotado() { return lp <= 0; }

    public void invocar(Carta c) { campo.add(c); }
    public void destruir(Carta c) { campo.remove(c); }
    public boolean tieneMonstruos() { return !campo.isEmpty(); }
    public ArrayList<Carta> verCampo() { return campo; }
}
