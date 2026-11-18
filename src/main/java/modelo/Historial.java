/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Daniel Alejandro
 */


public class Historial {

    public static final String ARCHIVO = "historial_combate.txt";

    public static String formatear(String atacante, String defensor, int dano, String resultado) {
        return "Atacante: " + atacante + " | Defensor: " + defensor
                + " | Dano: " + dano + " | Resultado: " + resultado;
    }

    public static void registrar(String linea) throws IOException {
        FileWriter fw = new FileWriter(ARCHIVO, true); // true = agregar al final
        fw.write(linea + "\n");
        fw.close();
    }
}
