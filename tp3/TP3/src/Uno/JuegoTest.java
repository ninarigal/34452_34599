package Uno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.smartcardio.Card;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class JuegoTest {
    private Carta rojo2;
    private Carta rojo4;
    private Carta azul4;
    private Carta verde4;
    private Carta azul2;
    private List<Carta> mazoSimple;
    private Juego juegoSimple;

    @BeforeEach
    public void setUp() {
        rojo2 = new CartaNumerada(2, "Rojo");
        rojo4 = new CartaNumerada(4, "Rojo");
        azul4 = new CartaNumerada(4, "Azul");
        verde4 = new CartaNumerada(4, "Verde");
        azul2 = new CartaNumerada(2, "Azul");
        mazoSimple = List.of(rojo2, rojo4, azul4, verde4, azul2);
        juegoSimple = new Juego(mazoSimple, 2, "A", "B");
    }
    @Test
    public void testCartaInicialALaVistaColor(){
        assertEquals("Rojo", juegoSimple.colorCartaActual());
    }
 //   @Test
 //   public void testCartaInicialALaVistaNumero(){
 //       assertEquals(2, juegoSimple.numeroCartaActual());
 //   }
    @Test
    public void unaJugadaValidaJugadorAColor(){
        assertEquals("Rojo",juegoSimple.jugar("A", rojo4).colorCartaActual());
    }
//    @Test
//    public void unaJugadaValidaJugadorANumero(){
//        assertEquals(4, juegoSimple.jugar("A", rojo4).numeroCartaActual());
//    }
    @Test
    public void unaJugadaInvalidaJugadorA(){
        assertThrows(IllegalArgumentException.class, () -> {juegoSimple.jugar("A", azul4);});
    }
    @Test
    public void unaCartaInvalidaJugadorA(){
        assertThrows(IllegalArgumentException.class, () -> {juegoSimple.jugar("A", verde4);});
    }
    @Test
    public void dosCartasValidasJugadorAJugadorB(){
        assertThrows(IllegalArgumentException.class, () -> {juegoSimple.jugar("A", azul4).jugar("B", azul2);});
    }
    @Test
    public void juegoSimpleColorJugadorAJugadorB(){
        assertEquals("Azul", juegoSimple.jugar("A", rojo4).jugar("B", verde4).jugar("A", azul4).jugar("B", azul2).colorCartaActual());
    }

    @Test
    public void jugadorACartaRepetida(){
        assertThrows(IllegalArgumentException.class, () -> {juegoSimple.jugar("A", rojo4).jugar("B", verde4).jugar("A", rojo4);});

    }

    @Test
    void testTurnoIncorrecto() {
        assertThrows(IllegalStateException.class, () -> {
            juegoSimple.jugar("B", azul2);
        });
    }
}
