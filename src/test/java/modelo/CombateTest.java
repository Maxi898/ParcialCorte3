/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modelo;

/**
 *
 * @author Daniel Alejandro
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CombateTest {

    private Jugador a;
    private Jugador b;
    private Combate combate;

    @BeforeEach
    public void preparar() {
        a = new Jugador();
        a.iniciar("A");
        b = new Jugador();
        b.iniciar("B");
        combate = new Combate();
    }

    @Test
    public void lpInicialEs8000() {
        assertEquals(8000, a.lpActual());
    }

    @Test
    public void ataqueVsAtaqueGanaAtacante() {
        Carta atk = new MonstruoNormal("Dragon", 2000, 1000, Posicion.ATAQUE);
        Carta def = new MonstruoNormal("Mago", 1500, 1000, Posicion.ATAQUE);
        a.invocar(atk);
        b.invocar(def);
        combate.resolver(atk, a, def, b);
        assertEquals(7500, b.lpActual());
        assertEquals(8000, a.lpActual());
        assertFalse(b.tieneMonstruos());
        assertTrue(a.tieneMonstruos());
    }

    @Test
    public void ataqueVsAtaqueGanaDefensor() {
        Carta atk = new MonstruoNormal("Dragon", 1000, 1000, Posicion.ATAQUE);
        Carta def = new MonstruoNormal("Mago", 1800, 1000, Posicion.ATAQUE);
        a.invocar(atk);
        b.invocar(def);
        combate.resolver(atk, a, def, b);
        assertEquals(7200, a.lpActual());
        assertFalse(a.tieneMonstruos());
    }

    @Test
    public void ataqueVsAtaqueEmpate() {
        Carta atk = new MonstruoNormal("X", 1500, 0, Posicion.ATAQUE);
        Carta def = new MonstruoNormal("Y", 1500, 0, Posicion.ATAQUE);
        a.invocar(atk);
        b.invocar(def);
        combate.resolver(atk, a, def, b);
        assertEquals(8000, a.lpActual());
        assertEquals(8000, b.lpActual());
        assertFalse(a.tieneMonstruos());
        assertFalse(b.tieneMonstruos());
    }

    @Test
    public void ataqueVsDefensaDestruyeSinDanoLp() {
        Carta atk = new MonstruoNormal("X", 2000, 0, Posicion.ATAQUE);
        Carta def = new MonstruoNormal("Y", 0, 1000, Posicion.DEFENSA);
        a.invocar(atk);
        b.invocar(def);
        combate.resolver(atk, a, def, b);
        assertEquals(8000, b.lpActual());
        assertFalse(b.tieneMonstruos());
    }

    @Test
    public void ataqueVsDefensaMayorDanaAlAtacante() {
        Carta atk = new MonstruoNormal("X", 1000, 0, Posicion.ATAQUE);
        Carta def = new MonstruoNormal("Y", 0, 1600, Posicion.DEFENSA);
        a.invocar(atk);
        b.invocar(def);
        combate.resolver(atk, a, def, b);
        assertEquals(7400, a.lpActual());
        assertTrue(b.tieneMonstruos());
        assertTrue(a.tieneMonstruos());
    }

    @Test
    public void ataqueDirecto() {
        Carta atk = new MonstruoNormal("X", 1800, 0, Posicion.ATAQUE);
        a.invocar(atk);
        combate.resolver(atk, b);
        assertEquals(6200, b.lpActual());
    }

    @Test
    public void ataqueDirectoBloqueadoSiHayDefensores() {
        Carta atk = new MonstruoNormal("X", 1800, 0, Posicion.ATAQUE);
        b.invocar(new MonstruoNormal("Y", 100, 100, Posicion.DEFENSA));
        String r = combate.resolver(atk, b);
        assertTrue(r.startsWith("ERROR"));
        assertEquals(8000, b.lpActual());
    }

    @Test
    public void lpNuncaEsNegativo() {
        b.recibirDano(99999);
        assertEquals(0, b.lpActual());
        assertTrue(b.estaDerrotado());
    }
}
