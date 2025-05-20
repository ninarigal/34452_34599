package Uno;

public class CartaSalteo extends CartaDeAccion {

    private CartaSalteo(String color) {
        super(color);
    }
    public static CartaSalteo with(String color) {
        return new CartaSalteo(color);
    }
    public void aplicarEfecto(Juego juego) {
        juego.siguienteTurno();
    }

}
