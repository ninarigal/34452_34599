package Uno;

public class CartaRoba2 extends CartaColorida {

    public CartaRoba2(String color) {
        super(color);
    }

    public boolean aceptaSobre(CartaColorida actual) {
        return (this.color.equals(actual.color()) || actual.tipo().equals(this.tipo()));
    }

    public TipoCarta tipo() {
        return TipoCarta.DRAW_TWO;
    }
}
