package Uno;

public class CartaSalteo extends CartaDeAccion {

    public CartaSalteo(String color) {
        super(color, TipoCarta.SKIP);
    }
    public void aplicarEfecto(Juego juego) {
        juego.perderTurno(); // saltea al siguiente
    }
}
