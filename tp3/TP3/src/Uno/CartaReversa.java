package Uno;

public class CartaReversa extends CartaDeAccion {

    public CartaReversa(String color) {
        super(color);
    }
    public void aplicarEfecto(Juego juego) {
        juego.cambiarSentido();
    }
    public boolean teGustaTipoDe(Carta carta){
        return carta instanceof CartaReversa;
    }

}
