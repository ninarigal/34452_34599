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
    private Carta rojo5;
    private Carta azul2;
    private Carta azul3;
    private Carta azul4;
    private Carta verde4;
    private Carta verde3;
    private Carta verde2;
    private Carta amarillo2;
    private Carta amarillo3;
    private Carta amarillo4;
    private Carta rojoReversa;
    private Carta azulReversa;
    private Carta amarilloReversa;
    private Carta azulSalteo;
    private Carta amarilloSalteo;
    private Carta verdeSalteo;
    private Carta verdeRoba2;
    private Carta amarilloRoba2;
    private Carta azulRoba2;

    private CartaComodin comodin;
    private CartaComodin comodin2;
    private CartaComodin comodin3;
    private CartaComodin comodin4;

    private List<Carta> mazoSimple;
    private Juego juegoSimple;

    @BeforeEach
    public void setUp() {
        rojo2 = new CartaNumerada(2, "Rojo");
        rojo3 = new CartaNumerada(3, "Rojo");
        rojo4 = new CartaNumerada(4, "Rojo");
        rojo5 = new CartaNumerada(5, "Rojo");
        verde2 = new CartaNumerada(2, "Verde");
        verde3 = new CartaNumerada(3, "Verde");
        verde4 = new CartaNumerada(4, "Verde");
        azul2 = new CartaNumerada(2, "Azul");
        azul3 = new CartaNumerada(3, "Azul");
        azul4 = new CartaNumerada(4, "Azul");
        amarillo2 = new CartaNumerada(2, "Amarillo");
        amarillo3 = new CartaNumerada(3, "Amarillo");
        amarillo4 = new CartaNumerada(4, "Amarillo");

        rojoReversa = new CartaReversa("Rojo");
        azulReversa = new CartaReversa("Azul");
        amarilloReversa = new CartaReversa("Amarillo");
        azulSalteo = new CartaSalteo("Azul");
        amarilloSalteo = new CartaSalteo("Amarillo");
        verdeSalteo = new CartaSalteo("Verde");
        verdeRoba2 = new CartaRoba2("Verde");
        amarilloRoba2 = new CartaRoba2("Amarillo");
        azulRoba2 = new CartaRoba2("Azul");

        comodin = new CartaComodin();
        comodin2 = new CartaComodin();
        comodin3 = new CartaComodin();
        comodin4 = new CartaComodin();


        mazoSimple = List.of(rojo2, // inicial
                azul4, azulSalteo, rojoReversa, amarillo2, amarillo3, comodin2, azul2, // A
                verde4, rojo4, azulRoba2, amarilloSalteo, verde3, verdeSalteo, comodin3,  // B
                comodin, verdeRoba2, rojo3, azulReversa, azul3, amarilloRoba2, amarilloReversa, // C
                amarillo4, verde2, rojo5, comodin4); // mazo
        juegoSimple = new Juego(mazoSimple, 7, "A", "B", "C");
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

    @Test
    public void reversaAceptaReversa(){ // Se puede apoyar otra reversa de otro color
        assertEquals("Azul", juegoSimple.jugar("A", rojoReversa).jugar("C", azulReversa).jugar("A", azul4).colorCartaActual());
    }

    @Test
    public void reversaColorIncorrecto(){ //No se puede apoyar otra carta de otro color que no sea reversa
        assertThrows(IllegalArgumentException.class, () -> {juegoSimple.jugar("A", rojoReversa).jugar("C", verdeRoba2 );});
    }


    //Salteo
    @Test
    public void turnosConSalteoCorrecto(){
        assertEquals("Amarillo", juegoSimple.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", amarillo3).colorCartaActual());
    }

    @Test
    public void turnosConSalteoIncorrecto(){
        assertThrows(IllegalStateException.class, () -> {juegoSimple.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("C", comodin.comoAmarillo());});
    }

    @Test
    public void salteoAceptaComodin(){
        assertEquals("Rojo", juegoSimple.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", comodin2.comoRojo()).jugar("B", rojo4).colorCartaActual());
    }

    @Test
    public void salteoAceptaSalteo(){
        assertEquals("Azul", juegoSimple.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", azulSalteo).colorCartaActual());
    }

    @Test
    public void salteoColorIncorrecto(){
        assertThrows(IllegalArgumentException.class, () -> {juegoSimple.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", comodin2.comoVerde()).jugar("B", verdeSalteo).jugar("A", azul4);});
    }

    //Roba2
    @Test
    public void turnosConRoba2Correcto(){
        assertEquals( "Verde", juegoSimple.jugar("A", comodin2.comoVerde()).jugar("B", verde3).jugar("C", verdeRoba2).jugar("B", verdeSalteo).colorCartaActual());
    }

    @Test
    public void turnosConRoba2Incorrecto(){
        assertThrows(IllegalStateException.class, () -> {juegoSimple.jugar("A", comodin2.comoVerde()).jugar("B", verde3).jugar("C", verdeRoba2).jugar("A", verde2);});
    }

    @Test
    public void Roba2AceptaComodin(){
        assertEquals( "Rojo", juegoSimple.jugar("A", comodin2.comoVerde()).jugar("B", verde3).jugar("C", verdeRoba2).jugar("B", comodin3.comoRojo()).jugar("C", rojo3).colorCartaActual());
    }

    @Test
    public void Roba2AceptaRoba2(){
        assertEquals( "Azul", juegoSimple.jugar("A", comodin2.comoVerde()).jugar("B", verde3).jugar("C", verdeRoba2).jugar("B", azulRoba2).colorCartaActual());
    }

    @Test
    public void Roba2ColorIncorrecto(){
        assertThrows(IllegalArgumentException.class, () -> {juegoSimple.jugar("A", comodin2.comoVerde()).jugar("B", verde3).jugar("C", verdeRoba2).jugar("B", verde3);});
    }

    //Comodin
    @Test
    public void turnosConComodinCorrecto(){
        assertEquals( "Verde", juegoSimple.jugar("A", comodin2.comoVerde()).jugar("B", verde3).colorCartaActual());
    }

    @Test
    public void comodinAceptaComodin(){
        assertEquals( "Azul", juegoSimple.jugar("A", comodin2.comoVerde()).jugar("B", comodin3.comoAzul()).colorCartaActual());
    }

    @Test
    public void comodinColorIncorrecto(){
        assertThrows(IllegalArgumentException.class, () -> {juegoSimple.jugar("A", comodin2.comoVerde()).jugar("B", rojo4);});
    }

    // Robar del mazo



}

