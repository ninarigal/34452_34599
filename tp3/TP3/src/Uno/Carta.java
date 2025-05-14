package Uno;

public abstract class Carta {
    protected String color;
    protected int numero;
    public Carta(String color, int numero) {
        this.color = color;
        this.numero = numero;
    }
    public String color() {
        return this.color;
    }
    public int numero() {
        return this.numero;
    }
    public abstract boolean aceptaCarta(Carta carta);
}
