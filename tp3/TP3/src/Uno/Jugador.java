package Uno;

import java.util.List;

public class Jugador {
    private String nombre;
    private Jugador derecha, izquierda;
    private List<Carta> mano;
    private boolean yaTomoEnEsteTurno;

    public Jugador(String nombre, List<Carta> mano) {
        this.nombre = nombre;
        this.mano   = mano;
        this.yaTomoEnEsteTurno = false;
    }
    public String getNombre() { return nombre; }
    public List<Carta> getMano() { return mano; }
    public void setVecinos(Jugador izquierda, Jugador derecha) {
        this.izquierda = izquierda;
        this.derecha   = derecha;
    }
    public Jugador getDerecha()   { return derecha; }
    public Jugador getIzquierda() { return izquierda; }
    public boolean getYaTomoEnEsteTurno() { return yaTomoEnEsteTurno;}
    public void setToma(boolean estado) { this.yaTomoEnEsteTurno = estado; }

}

