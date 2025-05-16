package Uno;

public class CartaComodinColorida extends CartaColorida {
    public CartaComodinColorida(String color) {
        super(color);
    }

    @Override
    public TipoCarta tipo() {
        return TipoCarta.WILD;
    }

    @Override
    public boolean aceptaSobre(CartaColorida actual) {
        return true;
    }
}
