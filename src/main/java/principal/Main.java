/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

/**
 *
 * @author Daniel Alejandro
 */
import controlador.Controlador;
import java.util.ArrayList;
import modelo.Carta;
import modelo.Jugador;
import vista.VistaBatalla;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            Jugador j1 = new Jugador();
            j1.iniciar("Jugador 1");
            Jugador j2 = new Jugador();
            j2.iniciar("Jugador 2");

            VistaBatalla vista = new VistaBatalla();
            Controlador control = new Controlador();
            control.iniciar(vista, j1, j2);
            vista.setLocationRelativeTo(null);
            vista.setVisible(true);
        });
    }

    // Reporte 1: monstruos en el campo de un jugador
    public static String reporteCampo(Jugador j) {
        String r = "--- Campo de " + j.nombreJugador() + " (LP " + j.lpActual() + ") ---\n";
        if (j.verCampo().isEmpty()) {
            r = r + "(sin monstruos)\n";
        }
        for (Carta c : j.verCampo()) {
            r = r + c.describir() + "\n";
        }
        return r;
    }

    // Reporte 2: ranking de monstruos por ATQ de ambos jugadores
    public static String reporteRanking(Jugador a, Jugador b) {
        ArrayList<Carta> todas = new ArrayList<>();
        todas.addAll(a.verCampo());
        todas.addAll(b.verCampo());
        todas.sort((x, y) -> y.getAtq() - x.getAtq());

        String r = "--- Ranking por ATQ ---\n";
        int pos = 1;
        for (Carta c : todas) {
            r = r + pos + ". " + c.getNombre() + " (" + c.getAtq() + ")\n";
            pos++;
        }
        return r;
    }
}