package Uno;

public class CartaReversa extends CartaDeAccion {

    public CartaReversa(String color) {
        super(color, TipoCarta.REVERSE);
    }
    public void aplicarEfecto(Juego juego) {
        juego.cambiarSentido();  // invierto el sentido
        juego.siguienteTurno();          // paso al jugador que ahora corresponde
    }

}
