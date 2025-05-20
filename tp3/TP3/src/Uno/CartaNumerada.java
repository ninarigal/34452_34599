package Uno;

import java.util.Objects;

public class CartaNumerada extends Carta {
    private int numero;
    private String color;

    private CartaNumerada(int num, String color) {
        this.color = color;
        this.numero = num;
    }
    public static CartaNumerada with(int num, String color) {
        return new CartaNumerada(num, color);
    }
    public String color() {return this.color;}
    public void aplicarEfecto(Juego juego) { }

    public boolean teGustaColorDe(String color) {
        return this.color.equals(color);
    }
    public boolean teGustaNumeroDe(int numero) {
        return this.numero == numero;
    }

    public boolean aceptaSobre(Carta nueva) {
        return nueva.teGustaColorDe(this.color) || nueva.teGustaNumeroDe(this.numero);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CartaNumerada)) return false;
        CartaNumerada otra = (CartaNumerada) obj;
        return this.numero == otra.numero
                && Objects.equals(this.color, otra.color);
    }

    public int hashCode() {
        return Objects.hash(color, numero);
    }

}
