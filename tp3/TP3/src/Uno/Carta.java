package Uno;

public abstract class Carta {
    protected String color;
    public Carta(String color) {
        this.color = color;
    }
    public String color() {
        return this.color;
    }
    public abstract boolean aceptaSobre(Carta actual);
    public abstract TipoCarta tipo();

}
