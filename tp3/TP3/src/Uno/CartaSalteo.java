package Uno;

public class CartaSalteo extends CartaDeAccion {

    public CartaSalteo(String color) {
        super(color);
    }
    public void aplicarEfecto(Juego juego) {
        juego.perderTurno(); // saltea al siguiente
    }

    public boolean teGustaTipoDe(Carta carta){
        return carta instanceof CartaSalteo;
    }
}
