package Uno;

public class CartaRoba2 extends CartaDeAccion{

    private CartaRoba2(String color) {
        super(color);
    }
    public static CartaRoba2 with(String color) {
        return new CartaRoba2(color);
    }
    public void aplicarEfecto(Juego juego) {
        juego.siguienteTurno();
        juego.robar2Cartas(juego.getTurnoActual());
    }
}
