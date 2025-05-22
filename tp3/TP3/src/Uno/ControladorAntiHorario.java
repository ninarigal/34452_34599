package Uno;

public class ControladorAntiHorario extends Controlador {
    public Jugador quienSigue(Jugador actual) { return actual.getIzquierda(); }
    public Controlador getOpuesto() { return new ControladorHorario(); }

}
