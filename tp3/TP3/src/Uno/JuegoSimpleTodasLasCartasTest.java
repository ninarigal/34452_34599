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
    private Carta azul7;
    private Carta azul4;
    private Carta verde3;
    private Carta verde4;
    private Carta verde7;
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
    private CartaComodin comodin5;

    private List<Carta> mazoSimple;
    private List<Carta> mazoEmpiezaRoba2;
    private List<Carta> mazoEmpiezaReversa;
    private List<Carta> mazoEmpiezaSalteo;
    private Juego juego;
    private Juego juegoSimple;
    private Juego juegoEmpiezaRoba2;
    private Juego juegoEmpiezaReversa;
    private Juego juegoEmpiezaSalteo;

    @BeforeEach
    public void setUp() {
        rojo2 = CartaNumerada.with(2, "Rojo");
        rojo3 = CartaNumerada.with(3, "Rojo");
        rojo4 = CartaNumerada.with(4, "Rojo");
        rojo5 = CartaNumerada.with(5, "Rojo");
        verde2 = CartaNumerada.with(2, "Verde");
        verde3 = CartaNumerada.with(3, "Verde");
        verde7 = CartaNumerada.with(7, "Verde");
        verde4 = CartaNumerada.with(4, "Verde");
        azul2 = CartaNumerada.with(2, "Azul");
        azul7 = CartaNumerada.with(7, "Azul");
        azul4 = CartaNumerada.with(4, "Azul");
        amarillo2 = CartaNumerada.with(2, "Amarillo");
        amarillo3 = CartaNumerada.with(3, "Amarillo");
        amarillo4 = CartaNumerada.with(4, "Amarillo");

        rojoReversa = CartaReversa.with("Rojo");
        azulReversa = CartaReversa.with("Azul");
        amarilloReversa = CartaReversa.with("Amarillo");
        azulSalteo = CartaSalteo.with("Azul");
        amarilloSalteo = CartaSalteo.with("Amarillo");
        verdeSalteo = CartaSalteo.with("Verde");
        verdeRoba2 = CartaRoba2.with("Verde");
        amarilloRoba2 = CartaRoba2.with("Amarillo");
        azulRoba2 = CartaRoba2.with("Azul");

        comodin = CartaComodin.with();



        mazoSimple = List.of(rojo2, // inicial
                azul4, azulSalteo, rojoReversa, amarillo2, amarillo3, comodin, azul2, // A
                verde4, rojo4, azulRoba2, amarilloSalteo, verde7, verdeSalteo, comodin,  // B
                comodin, verdeRoba2, rojo3, azulReversa, azul7, amarilloRoba2, comodin, // C
                verde2, amarillo4, rojo5, comodin, amarilloReversa, verde3); // mazo
        mazoEmpiezaRoba2 = List.of(azulRoba2, azul2, rojo5, azul4,  comodin, azul7,  amarilloReversa, verdeSalteo, comodin );
        mazoEmpiezaSalteo = List.of(azulSalteo, azul2, rojo5, azul4,  comodin, azul7,  amarilloReversa, verdeSalteo, comodin );
        mazoEmpiezaReversa = List.of(azulReversa, azul2, rojo5, azul4,  comodin, azul7,  amarilloReversa, verdeSalteo, comodin );

        juego = new Juego(mazoSimple, 7, "A", "B", "C");
        juegoSimple = new Juego(mazoSimple, 1, "A", "B", "C");
        juegoEmpiezaRoba2 = new Juego(mazoEmpiezaRoba2, 2, "A", "B", "C");
        juegoEmpiezaReversa = new Juego(mazoEmpiezaReversa, 2, "A", "B", "C");
        juegoEmpiezaSalteo = new Juego(mazoEmpiezaSalteo, 2, "A", "B", "C");
    }

    //Reversa

    @Test
    public void turnosConReversaCorrecto() {
        assertEquals("Rojo", juego.jugar("A", rojoReversa).jugar("C", rojo3).colorCartaActual());
    }

    @Test
    public void turnosConReversaIncorrecto() {
        assertThrows(IllegalStateException.class, () -> {
            juego.jugar("A", rojoReversa).jugar("B", rojo4);
        });
    }

    @Test
    public void reversaAceptaComodin() {
        assertEquals("Verde", juego.jugar("A", rojoReversa).jugar("C", comodin.comoVerde()).colorCartaActual());

    }

    @Test
    public void reversaAceptaReversa() { // Se puede apoyar otra reversa de otro color
        assertEquals("Azul", juego.jugar("A", rojoReversa).jugar("C", azulReversa).jugar("A", azul4).colorCartaActual());
    }

    @Test
    public void reversaColorIncorrecto() { //No se puede apoyar otra carta de otro color que no sea reversa
        assertThrows(IllegalArgumentException.class, () -> {
            juego.jugar("A", rojoReversa).jugar("C", verdeRoba2);
        });
    }


    //Salteo
    @Test
    public void turnosConSalteoCorrecto() {
        assertEquals("Amarillo", juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", amarillo3).colorCartaActual());
    }

    @Test
    public void turnosConSalteoIncorrecto() {
        assertThrows(IllegalStateException.class, () -> {
            juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("C", comodin.comoAmarillo());
        });
    }

    @Test
    public void salteoAceptaComodin() {
        assertEquals("Rojo", juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", comodin.comoRojo()).jugar("B", rojo4).colorCartaActual());
    }

    @Test
    public void salteoAceptaSalteo() {
        assertEquals("Azul", juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", azulSalteo).colorCartaActual());
    }

    @Test
    public void salteoColorIncorrecto() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", comodin.comoVerde()).jugar("B", verdeSalteo).jugar("A", azul4);
        });
    }

    //Roba2
    @Test
    public void turnosConRoba2Correcto() {
        assertEquals("Verde", juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("B", verdeSalteo).colorCartaActual());
    }

    @Test
    public void turnosConRoba2Incorrecto() {
        assertThrows(IllegalStateException.class, () -> {
            juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("A", verde2);
        });
    }

    @Test
    public void Roba2AceptaComodin() {
        assertEquals("Rojo", juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("B", comodin.comoRojo()).jugar("C", rojo3).colorCartaActual());
    }

    @Test
    public void Roba2AceptaRoba2() {
        assertEquals("Azul", juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("B", azulRoba2).colorCartaActual());
    }

    @Test
    public void Roba2ColorIncorrecto() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("B", verde7);
        });
    }

    //Comodin
    @Test
    public void turnosConComodinCorrecto() {
        assertEquals("Verde", juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).colorCartaActual());
    }

    @Test
    public void comodinAceptaComodin() {
        assertEquals("Azul", juego.jugar("A", comodin.comoVerde()).jugar("B", comodin.comoAzul()).colorCartaActual());
    }

    @Test
    public void comodinColorIncorrecto() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.jugar("A", comodin.comoVerde()).jugar("B", rojo4);
        });
    }

    // Tomar del mazo
    @Test
    public void turnoConTomaDelMazoCorrecto() {
        assertEquals("Verde", juego.jugar("A", comodin.comoVerde()).jugar("B", comodin.comoAzul()).jugar("C", comodin.comoVerde()).tomar("A").jugar("A", verde2).colorCartaActual());
    }

    @Test
    public void turnoConTomaDelMazoYPasoDeTurno() {
        assertEquals("Rojo",
                juego.jugar("A", azul2)
                        .jugar("B", azulRoba2)
                        .jugar("A", azulSalteo)
                        .jugar("C", comodin.comoVerde())
                        .jugar("A", comodin.comoRojo())
                        .jugar("B", rojo4)
                        .jugar("C", comodin.comoAzul())
                        .jugar("A", azul4)
                        .jugar("B", verde4)
                        .jugar("C", verdeRoba2)
                        .jugar("B", verde7)
                        .jugar("C", azul7)
                        .tomar("A")
                        .pasarTurno("A")
                        .jugar("B", comodin.comoRojo())
                        .jugar("C", rojo3).colorCartaActual());
    }

    @Test
    public void pasoDeTurnoSinTomaIncorrecto() {
        assertThrows(IllegalStateException.class, () -> {
            juego.jugar("A", comodin.comoVerde()).jugar("B", comodin.comoAzul()).jugar("C", comodin.comoVerde()).pasarTurno("A");
        });
    }

    @Test
    public void ganadorAntesDeTerminarLanzaException() {
        assertThrows(IllegalStateException.class, () -> juego.ganador());
    }


    @Test
    public void ganadorDespuesDeUltimaCarta() {
        assertEquals("C", juegoSimple.tomar("A").pasarTurno("A").tomar("B").pasarTurno("B").jugar("C", rojoReversa).ganador());
    }


    @Test

    public void tomarCartaEnJuegoTerminado() {
        assertThrows(IllegalStateException.class, () -> juegoSimple.tomar("A").pasarTurno("A").tomar("B").pasarTurno("B").jugar("C", rojoReversa).tomar("B"));

    }

    @Test
    public void ganadorJuegoTerminado() {
        assertEquals("B",
                juego.jugar("A", azul2)
                        .jugar("B", azulRoba2)
                        .jugar("A", azulSalteo)
                        .jugar("C", comodin.comoVerde())
                        .jugar("A", comodin.comoRojo())
                        .jugar("B", rojo4)
                        .jugar("C", comodin.comoAzul())
                        .jugar("A", azul4)
                        .jugar("B", verde4)
                        .jugar("C", verdeRoba2)
                        .jugar("B", verde7)
                        .jugar("C", azul7)
                        .tomar("A")
                        .pasarTurno("A")
                        .jugar("B", comodin.comoRojo())
                        .jugar("C", rojo3)
                        .jugar("A", rojoReversa)
                        .jugar("C", azulReversa)
                        .jugar("A", comodin.comoAmarillo())
                        .jugar("B", amarilloSalteo)
                        .tomar("A")
                        .pasarTurno("A")
                        .jugar("B", verdeSalteo)
                        .ganador());
    }

    @Test
    public void jugarJuegoTerminado() {
        assertThrows( IllegalStateException.class, () ->
                juego.jugar("A", azul2)
                        .jugar("B", azulRoba2)
                        .jugar("A", azulSalteo)
                        .jugar("C", comodin.comoVerde())
                        .jugar("A", comodin.comoRojo())
                        .jugar("B", rojo4)
                        .jugar("C", comodin.comoAzul())
                        .jugar("A", azul4)
                        .jugar("B", verde4)
                        .jugar("C", verdeRoba2)
                        .jugar("B", verde7)
                        .jugar("C", azul7)
                        .tomar("A")
                        .pasarTurno("A")
                        .jugar("B", comodin.comoRojo())
                        .jugar("C", rojo3)
                        .jugar("A", rojoReversa)
                        .jugar("C", azulReversa)
                        .jugar("A", comodin.comoAmarillo())
                        .jugar("B", amarilloSalteo)
                        .tomar("A")
                        .pasarTurno("A")
                        .jugar("B", verdeSalteo)
                        .jugar("A", verde3));
    }



    @Test
    public void turnoIncorrectoJuegoEmpiezaRoba2(){
        assertThrows( IllegalStateException.class, () -> juegoEmpiezaRoba2.jugar("A", azul2));

    }

    @Test
    public void turnoCorrectoJuegoEmpiezaRoba2(){
        assertEquals("Azul", juegoEmpiezaRoba2.jugar("B", azul4).colorCartaActual());

    }


    @Test
    public void turnoIncorrectoJuegoEmpiezaSalteo(){
        assertThrows( IllegalStateException.class, () -> juegoEmpiezaSalteo.jugar("A", azul2));

    }

    @Test
    public void turnoCorrectoJuegoEmpiezaSalteo(){
        assertEquals("Azul", juegoEmpiezaSalteo.jugar("B", azul4).colorCartaActual());

    }

    @Test
    public void turnoIncorrectoJuegoEmpiezaReversa(){
        assertThrows( IllegalStateException.class, () -> juegoEmpiezaReversa.jugar("A", azul2).jugar("B", azul4));

    }

    @Test
    public void turnoCorrectoJuegoEmpiezaReversa(){
        assertEquals("Azul", juegoEmpiezaReversa.jugar("A", azul2).jugar("C", azul7).colorCartaActual());

    }




}

