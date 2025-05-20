package Uno;

public class CartaSalteo extends CartaDeAccion {

    public CartaSalteo(String color) {
        super(color);
    }
    public void aplicarEfecto(Juego juego) {
        juego.siguienteTurno();
    }

}
