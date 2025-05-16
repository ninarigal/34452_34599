package Uno;

//public class CartaComodin  {
//
//    public void elegirColor(String colorElegido) {
//    }
//}

public class CartaComodin extends Carta {
    public TipoCarta tipo() {
        return TipoCarta.WILD;
    }

    public CartaColorida colorizar(String colorElegido) {
        if (colorElegido == null || colorElegido.isEmpty()) {
            throw new IllegalArgumentException("Debe elegirse un color válido para el comodín");
        }
        return new CartaComodinColorida(colorElegido);
    }
}

