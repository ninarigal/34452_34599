package Uno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JuegoSimpleTodasLasCartasTest {
    private Carta rojo2;
    private Carta rojo3;
    private Carta rojo4;
    private Carta azul2;
    private Carta azul4;
    private Carta verde4;
    private Carta verde2;
    private Carta amarillo2;
    private Carta amarillo4;
    private Carta rojoReversa;
    private Carta azulSalteo;
    private Carta verdeRoba2;
    private CartaComodin comodin;
    private List<Carta> mazoSimple;
    private Juego juegoSimple;

    @BeforeEach
    public void setUp() {
        rojo2 = new CartaNumerada(2, "Rojo");
        rojo3 = new CartaNumerada(3, "Rojo");
        rojo4 = new CartaNumerada(4, "Rojo");
        verde2 = new CartaNumerada(2, "Verde");
        verde4 = new CartaNumerada(4, "Verde");
        azul2 = new CartaNumerada(2, "Azul");
        azul4 = new CartaNumerada(4, "Azul");
        amarillo2 = new CartaNumerada(2, "Amarillo");
        amarillo4 = new CartaNumerada(4, "Amarillo");
        rojoReversa = new CartaReversa("Rojo");
        azulSalteo = new CartaSalteo("Azul");
        verdeRoba2 = new CartaRoba2("Verde");
        comodin = new CartaComodin();
        mazoSimple = List.of(rojo2, azul4, verde4, rojoReversa, azulSalteo, rojo4,  azul2, comodin, verdeRoba2, rojo3, amarillo4, verde2);
        juegoSimple = new Juego(mazoSimple, 3, "A", "B", "C");
    }

    //Reversa

    @Test
    public void turnosConReversaCorrecto(){
        assertEquals("Rojo", juegoSimple.jugar("A", rojoReversa).jugar("C", rojo3 ).colorCartaActual());
    }

    @Test
    public void turnosConReversaIncorrecto(){
        assertThrows(IllegalStateException.class, () -> {juegoSimple.jugar("A", rojoReversa).jugar("B", rojo4 );});
    }

    @Test
    public void reversaAceptaComodin(){
        assertEquals("Verde", juegoSimple.jugar("A", rojoReversa).jugar("C", comodin.comoVerde() ).colorCartaActual());

    }

    //Test:
    //1) Se puede apoyar otra reversa de otro color
    //2) No se puede apoyar otra carta de otro color que no sea reversa

    //Salteo

    //Roba2

    //Comodin

    //Comprar del mazo

}

