package Uno;

public class CartaNumerada extends Carta {
    public CartaNumerada(int num, String color) {
        super(color, num);
    }

    public boolean aceptaCarta(Carta carta) {
        return ((this.color.equals(carta.color()))|| (this.numero == carta.numero()));
    }
}
