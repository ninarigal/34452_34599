package Uno;

public class CartaRoba2 extends CartaDeAccion{

    public CartaRoba2(String color) {
        super(color, TipoCarta.DRAW_TWO);
    }
    public void aplicarEfecto(Juego juego) {
        int objetivo = juego.turnoActual();    // quién robará
        juego.robar2Cartas(objetivo);     // roba 2 cartas
        juego.perderTurno();                   // luego salteo su turno
    }
}
