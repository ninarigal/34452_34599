package Uno;

public abstract class CartaColorida extends Carta {
    protected String color;
    public CartaColorida(String color) {
        this.color = color;
    }
    public String color(){return color;}
    public abstract boolean aceptaSobre(CartaColorida actual);
}
