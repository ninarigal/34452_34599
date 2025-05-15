package Uno;

public class CartaReversa extends CartaColorida {

    public CartaReversa(String color) {
        super(color);
    }

    public boolean aceptaSobre(CartaColorida actual) {
        return (this.color.equals(actual.color()) || actual.tipo().equals(this.tipo()));
    }

    public TipoCarta tipo() {
        return TipoCarta.REVERSE;
    }
}
