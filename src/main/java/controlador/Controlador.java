/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import modelo.*;
import vista.VistaBatalla;
/**
 *
 * @author Daniel Alejandro
 */
  public class Controlador {

    private VistaBatalla vista;
    private Jugador j1;
    private Jugador j2;
    private Combate combate = new Combate();
    private DefaultListModel<String> modelo1 = new DefaultListModel<>();
    private DefaultListModel<String> modelo2 = new DefaultListModel<>();

    public void iniciar(VistaBatalla v, Jugador a, Jugador b) {
        vista = v;
        j1 = a;
        j2 = b;
        vista.getListaJ1().setModel(modelo1);
        vista.getListaJ2().setModel(modelo2);
        vista.getBtnAgregar().addActionListener(e -> agregarMonstruo());
        vista.getBtnAtacar().addActionListener(e -> atacar());
        vista.getBtnReportes().addActionListener(e -> verReportes());
        refrescar();
    }

    private void agregarMonstruo() {
        String nombre = vista.getTxtNombre().getText().trim();
        if (nombre.isEmpty()) {
            vista.mostrarMensaje("Escribe el nombre del monstruo");
            return;
        }
        int atq;
        int def;
        try {
            atq = Integer.parseInt(vista.getTxtAtq().getText().trim());
            def = Integer.parseInt(vista.getTxtDef().getText().trim());
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("ATQ y DEF deben ser numeros enteros");
            return;
        }
        if (atq < 0 || def < 0) {
            vista.mostrarMensaje("ATQ y DEF no pueden ser negativos");
            return;
        }

        Posicion pos = Posicion.ATAQUE;
        if (vista.getCmbPosicion().getSelectedIndex() == 1) {
            pos = Posicion.DEFENSA;
        }

        Carta carta;
        if (vista.getCmbTipo().getSelectedIndex() == 0) {
            carta = new MonstruoNormal(nombre, atq, def, pos);
        } else {
            MonstruoEfecto m = new MonstruoEfecto();
            m.setNombre(nombre);
            m.setAtq(atq);
            m.setDef(def);
            m.setPosicion(pos);
            carta = m;
        }

        Jugador dueno = j1;
        if (vista.getCmbJugador().getSelectedIndex() == 1) {
            dueno = j2;
        }
        dueno.invocar(carta);
        vista.agregarLog("Invocado: " + carta.describir() + " (" + dueno.nombreJugador() + ")");

        vista.getTxtNombre().setText("");
        vista.getTxtAtq().setText("");
        vista.getTxtDef().setText("");
        refrescar();
    }

    private void atacar() {
        if (j1.estaDerrotado() || j2.estaDerrotado()) {
            vista.mostrarMensaje("El duelo ya termino");
            return;
        }

        // El jugador activo es el atacante
        Jugador atk = j1;
        Jugador def = j2;
        JList<String> listaAtk = vista.getListaJ1();
        JList<String> listaDef = vista.getListaJ2();
        if (vista.getCmbJugador().getSelectedIndex() == 1) {
            atk = j2;
            def = j1;
            listaAtk = vista.getListaJ2();
            listaDef = vista.getListaJ1();
        }

        int ia = listaAtk.getSelectedIndex();
        if (ia < 0) {
            vista.mostrarMensaje("Selecciona el monstruo atacante en la lista del jugador activo");
            return;
        }
        Carta cAtk = atk.verCampo().get(ia);

        String resultado;
        String nombreDef;
        if (def.tieneMonstruos()) {
            int id = listaDef.getSelectedIndex();
            if (id < 0) {
                vista.mostrarMensaje("Selecciona el monstruo objetivo del rival");
                return;
            }
            Carta cDef = def.verCampo().get(id);
            nombreDef = cDef.getNombre();
            resultado = combate.resolver(cAtk, atk, cDef, def);
        } else {
            nombreDef = def.nombreJugador();
            resultado = combate.resolver(cAtk, def);
        }

        if (resultado.startsWith("ERROR")) {
            vista.mostrarMensaje(resultado);
            return;
        }

        String linea = Historial.formatear(cAtk.getNombre(), nombreDef, combate.ultimoDano(), resultado);
        vista.agregarLog(linea);
        try {
            Historial.registrar(linea);
        } catch (IOException ex) {
            vista.mostrarMensaje("No se pudo escribir el historial: " + ex.getMessage());
        }

        refrescar();

        if (j1.estaDerrotado()) {
            vista.mostrarMensaje("Gana " + j2.nombreJugador());
        } else if (j2.estaDerrotado()) {
            vista.mostrarMensaje("Gana " + j1.nombreJugador());
        }
    }

    private void verReportes() {
        vista.agregarLog(Main.reporteCampo(j1));
        vista.agregarLog(Main.reporteCampo(j2));
        vista.agregarLog(Main.reporteRanking(j1, j2));
    }

    private void refrescar() {
        modelo1.clear();
        for (Carta c : j1.verCampo()) {
            modelo1.addElement(c.toString());
        }
        modelo2.clear();
        for (Carta c : j2.verCampo()) {
            modelo2.addElement(c.toString());
        }
        vista.getLblLp1().setText(j1.nombreJugador() + " LP: " + j1.lpActual());
        vista.getLblLp2().setText(j2.nombreJugador() + " LP: " + j2.lpActual());
    }
}
