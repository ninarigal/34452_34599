package Uno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JuegoTests {
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

    private List<Carta> mazoBasico;
    private List<Carta> mazoSimple;
    private List<Carta> mazoEmpiezaRoba2;
    private List<Carta> mazoEmpiezaReversa;
    private List<Carta> mazoEmpiezaSalteo;

    private Juego juegoBasico2Cartas;
    private Juego juegoBasico3Cartas;
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


        mazoBasico = List.of(rojo2,
                rojo4, azul4, verde4,
                azul2, verde4, azul7,
                verde2, verde3);
        mazoSimple = List.of(rojo2, // inicial
                azul4, azulSalteo, rojoReversa, amarillo2, amarillo3, comodin, azul2, // A
                verde4, rojo4, azulRoba2, amarilloSalteo, verde7, verdeSalteo, comodin,  // B
                comodin, verdeRoba2, rojo3, azulReversa, azul7, amarilloRoba2, comodin, // C
                verde2, amarillo4, rojo5, comodin, amarilloReversa, verde3); // mazo
        mazoEmpiezaRoba2 = List.of(azulRoba2, azul2, rojo5, azul4,  comodin, azul7,  amarilloReversa, verdeSalteo, comodin );
        mazoEmpiezaSalteo = List.of(azulSalteo, azul2, rojo5, azul4,  comodin, azul7,  amarilloReversa, verdeSalteo, comodin );
        mazoEmpiezaReversa = List.of(azulReversa,
                azul2, rojo5,
                azul4,  comodin,
                azul7,  amarilloReversa,
                verdeSalteo, comodin );

        juegoBasico2Cartas = new Juego(mazoBasico, 2, "A", "B");
        juegoBasico3Cartas = new Juego(mazoBasico, 3, "A", "B");
        juego = new Juego(mazoSimple, 7, "A", "B", "C");
        juegoSimple = new Juego(mazoSimple, 1, "A", "B", "C");
        juegoEmpiezaRoba2 = new Juego(mazoEmpiezaRoba2, 2, "A", "B", "C");
        juegoEmpiezaReversa = new Juego(mazoEmpiezaReversa, 2, "A", "B", "C");
        juegoEmpiezaSalteo = new Juego(mazoEmpiezaSalteo, 2, "A", "B", "C");
    }

    @Test
    public void testCartaInicialALaVistaColor(){
        assertEquals(rojo2, juegoBasico3Cartas.cartaActual());
    }

    @Test
    public void unaJugadaValidaJugadorAColor(){
        assertEquals(rojo4,juegoBasico3Cartas.jugar("A", rojo4).cartaActual());
    }

    @Test
    public void unaJugadaInvalidaJugadorA(){
        assertThrows(IllegalArgumentException.class, () -> {juegoBasico3Cartas.jugar("A", azul4);});
    }

    @Test
    public void unaCartaInvalidaJugadorA(){
        assertThrows(IllegalArgumentException.class, () -> {juegoBasico3Cartas.jugar("A", verde4);});
    }

    @Test
    public void dosCartasValidasJugadorAJugadorB(){
        assertThrows(IllegalArgumentException.class, () -> {juegoBasico3Cartas.jugar("A", azul4).jugar("B", azul2);});
    }
    @Test
    public void juegoSimpleColorJugadorAJugadorB(){
        assertEquals(verde4, juegoBasico3Cartas.jugar("A", rojo4).jugar("B", verde4).cartaActual());
    }

    @Test
    public void jugadorACartaRepetida(){
        assertThrows(IllegalArgumentException.class, () -> {juegoBasico3Cartas.jugar("A", rojo4).jugar("B", verde4).jugar("A", rojo4);});
    }

    @Test
    void testTurnoIncorrecto() {
        assertThrows(IllegalStateException.class, () -> {juegoBasico3Cartas.jugar("B", azul2);});
    }

    @Test
    public void turnosConReversaCorrecto() {
        assertEquals(rojo3, juego.jugar("A", rojoReversa).jugar("C", rojo3).cartaActual());
    }

    @Test
    public void turnosConReversaIncorrecto() {
        assertThrows(IllegalStateException.class, () -> {
            juego.jugar("A", rojoReversa).jugar("B", rojo4);
        });
    }

    @Test
    public void reversaAceptaComodin() {
        assertEquals(comodin.comoVerde(), juego.jugar("A", rojoReversa).jugar("C", comodin.comoVerde()).cartaActual());

    }

    @Test
    public void reversaAceptaReversa() {
        assertEquals(azul4, juego.jugar("A", rojoReversa).jugar("C", azulReversa).jugar("A", azul4).cartaActual());
    }

    @Test
    public void reversaColorIncorrecto() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.jugar("A", rojoReversa).jugar("C", verdeRoba2);
        });
    }

    @Test
    public void turnosConSalteoCorrecto() {
        assertEquals(amarillo3, juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", amarillo3).cartaActual());
    }

    @Test
    public void turnosConSalteoIncorrecto() {
        assertThrows(IllegalStateException.class, () -> {
            juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("C", comodin.comoAmarillo());
        });
    }

    @Test
    public void salteoAceptaComodin() {
        assertEquals(rojo4, juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", comodin.comoRojo()).jugar("B", rojo4).cartaActual());
    }

    @Test
    public void salteoAceptaSalteo() {
        assertEquals(azulSalteo, juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", azulSalteo).cartaActual());
    }

    @Test
    public void salteoColorIncorrecto() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.jugar("A", amarillo2).jugar("B", amarilloSalteo).jugar("A", comodin.comoVerde()).jugar("B", verdeSalteo).jugar("A", azul4);
        });
    }

    @Test
    public void turnosConRoba2Correcto() {
        assertEquals(verdeSalteo, juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("B", verdeSalteo).cartaActual());
    }

    @Test
    public void turnosConRoba2Incorrecto() {
        assertThrows(IllegalStateException.class, () -> {
            juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("A", verde2);
        });
    }

    @Test
    public void Roba2AceptaComodin() {
        assertEquals(rojo3, juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("B", comodin.comoRojo()).jugar("C", rojo3).cartaActual());
    }

    @Test
    public void Roba2AceptaRoba2() {
        assertEquals(azulRoba2, juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("B", azulRoba2).cartaActual());
    }

    @Test
    public void Roba2ColorIncorrecto() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).jugar("C", verdeRoba2).jugar("B", verde7);
        });
    }

    @Test
    public void turnosConComodinCorrecto() {
        assertEquals(verde7, juego.jugar("A", comodin.comoVerde()).jugar("B", verde7).cartaActual());
    }

    @Test
    public void comodinAceptaComodin() {
        assertEquals(comodin.comoAzul(), juego.jugar("A", comodin.comoVerde()).jugar("B", comodin.comoAzul()).cartaActual());
    }

    @Test
    public void comodinColorIncorrecto() {
        assertThrows(IllegalArgumentException.class, () -> {
            juego.jugar("A", comodin.comoVerde()).jugar("B", rojo4);
        });
    }

    @Test
    public void turnoConTomaDelMazoCorrecto() {
        assertEquals(verde2, juego.jugar("A", comodin.comoVerde()).jugar("B", comodin.comoAzul()).jugar("C", comodin.comoVerde()).tomar("A").jugar("A", verde2).cartaActual());
    }

    @Test
    public void turnoConTomaDelMazoYPasoDeTurno() {
        assertEquals(rojo3,
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
                        .jugar("C", rojo3).cartaActual());
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
    public void ganadorDespuesDeJuegoTerminado() {
        assertEquals("C", juegoSimple.tomar("A").pasarTurno("A").tomar("B").pasarTurno("B").jugar("C", rojoReversa).ganador());
    }

    @Test
    public void tomarCartaEnJuegoTerminado() {
        assertThrows(IllegalStateException.class, () -> juegoSimple.tomar("A").pasarTurno("A").tomar("B").pasarTurno("B").jugar("C", rojoReversa).tomar("B"));

    }

    @Test
    public void jugadorACantaUnoYTerminaJuego() {
        assertEquals("A", juegoBasico2Cartas.jugar("A", rojo4.uno()).jugar("B", verde4.uno()).jugar("A", azul4).ganador());
    }

    @Test
    public void jugadorANoCantaUnoYJuegaCartaDePenalizacion() {
        assertEquals(verde4, juegoBasico2Cartas.jugar("A", rojo4).jugar("B", verde4.uno()).jugar("A", verde4).cartaActual());
    }

    @Test
    public void ganadorJuegoTerminadoCantandoUno() {
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
                        .jugar("B", amarilloSalteo.uno())
                        .tomar("A")
                        .pasarTurno("A")
                        .jugar("B", verdeSalteo)
                        .ganador());
    }

    @Test
    public void jugadorNoCantaUno() {
        assertThrows(IllegalArgumentException.class, () ->
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
                        .jugar("B", amarilloSalteo.uno())
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
        assertEquals(azul4, juegoEmpiezaRoba2.jugar("B", azul4).cartaActual());

    }

    @Test
    public void turnoIncorrectoJuegoEmpiezaSalteo(){
        assertThrows( IllegalStateException.class, () -> juegoEmpiezaSalteo.jugar("A", azul2));

    }

    @Test
    public void turnoCorrectoJuegoEmpiezaSalteo(){
        assertEquals(azul4, juegoEmpiezaSalteo.jugar("B", azul4).cartaActual());

    }

    @Test
    public void turnoIncorrectoJuegoEmpiezaReversa(){
        assertThrows( IllegalStateException.class, () -> juegoEmpiezaReversa.jugar("A", azul2).jugar("B", azul4));

    }

    @Test
    public void turnoCorrectoJuegoEmpiezaReversa(){
        assertEquals(azul7, juegoEmpiezaReversa.jugar("A", azul2.uno())
                .jugar("C", azul7.uno())
                .cartaActual());
    }

}

