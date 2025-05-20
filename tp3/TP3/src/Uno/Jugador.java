package Uno;

import java.util.List;

public class Jugador {
    private String nombre;
    private Jugador derecha, izquierda;
    private List<Carta> mano;

    public Jugador(String nombre, List<Carta> mano) {
        this.nombre = nombre;
        this.mano   = mano;
    }

    public String getNombre() { return nombre; }
    public List<Carta> getMano() { return mano; }

    public void setVecinos(Jugador izquierda, Jugador derecha) {
        this.izquierda = izquierda;
        this.derecha   = derecha;
    }
    public Jugador getDerecha()   { return derecha; }
    public Jugador getIzquierda() { return izquierda; }
}

