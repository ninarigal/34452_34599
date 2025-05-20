package Uno;

import java.util.List;

public class Jugador {
    private String nombre;
    List<Carta> mano;

    public Jugador(String nombre, List<Carta> mano) {
        this.nombre = nombre;
        this.mano = mano;
    }

    public String getNombre() {
        return nombre;
    }
    public List<Carta> getMano() {
        return mano;
    }
}
