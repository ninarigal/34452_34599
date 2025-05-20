package Uno;

public class CartaReversa extends CartaDeAccion {

    private CartaReversa(String color) {
        super(color);
    }
    public static CartaReversa with(String color) {
        return new CartaReversa(color);
    }
    public void aplicarEfecto(Juego juego) {
        juego.cambiarSentido();
    }
}
