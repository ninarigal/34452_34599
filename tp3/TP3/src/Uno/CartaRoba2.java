package Uno;

public class CartaRoba2 extends CartaDeAccion{

    public CartaRoba2(String color) {
        super(color);
    }
    public void aplicarEfecto(Juego juego) {
        juego.robar2Cartas(juego.turnoActual());
        juego.siguienteTurno();
    }
    public boolean teGustaTipoDe(Carta carta){
        return carta instanceof CartaRoba2;
    }
}
