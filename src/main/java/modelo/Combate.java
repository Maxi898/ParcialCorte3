/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Daniel Alejandro
 */
public class Combate {

    private int danoUltimo;

    public int ultimoDano() { return danoUltimo; }

    // Ataque contra un monstruo
    public String resolver(Carta atacante, Jugador duenoAtq, Carta defensor, Jugador duenoDef) {
        danoUltimo = 0;
        if (atacante.getPosicion() != Posicion.ATAQUE) {
            return "ERROR: el atacante debe estar en posicion de ataque";
        }

        if (defensor.getPosicion() == Posicion.ATAQUE) {
            // Ataque vs Ataque
            int dif = atacante.getAtq() - defensor.getAtq();
            if (dif > 0) {
                duenoDef.recibirDano(dif);
                duenoDef.destruir(defensor);
                danoUltimo = dif;
                return "Defensor destruido";
            } else if (dif < 0) {
                duenoAtq.recibirDano(-dif);
                duenoAtq.destruir(atacante);
                danoUltimo = -dif;
                return "Atacante destruido";
            } else {
                duenoAtq.destruir(atacante);
                duenoDef.destruir(defensor);
                return "Ambos monstruos destruidos";
            }
        } else {
            // Ataque vs Defensa
            int dif = atacante.getAtq() - defensor.getDef();
            if (dif > 0) {
                duenoDef.destruir(defensor);
                return "Defensor destruido (sin dano a LP)";
            } else if (dif < 0) {
                duenoAtq.recibirDano(-dif);
                danoUltimo = -dif;
                return "Ataque rechazado: el atacante pierde LP";
            } else {
                return "Sin efecto";
            }
        }
    }

    // Ataque directo (sobrecarga)
    public String resolver(Carta atacante, Jugador duenoDef) {
        danoUltimo = 0;
        if (atacante.getPosicion() != Posicion.ATAQUE) {
            return "ERROR: el atacante debe estar en posicion de ataque";
        }
        if (duenoDef.tieneMonstruos()) {
            return "ERROR: el rival tiene monstruos, no hay ataque directo";
        }
        duenoDef.recibirDano(atacante.getAtq());
        danoUltimo = atacante.getAtq();
        return "Ataque directo";
    }
}
