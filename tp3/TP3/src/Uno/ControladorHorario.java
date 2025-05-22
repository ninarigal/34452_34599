package Uno;

public class ControladorHorario extends Controlador {
    public Jugador quienSigue(Jugador actual) { return actual.getDerecha(); }

    public Controlador getOpuesto() { return new ControladorAntiHorario(); }

}
