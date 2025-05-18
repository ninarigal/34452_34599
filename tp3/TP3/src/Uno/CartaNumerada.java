package Uno;

public class CartaNumerada extends Carta {
    private int numero;
    private String color;
    public CartaNumerada(int num, String color) {
        this.color = color;
        this.numero = num;
    }
    public String color() {return this.color;}
    public void aplicarEfecto(Juego juego) { juego.siguienteTurno();}

    public boolean teGustaColorDe(String color) {
        return this.color.equals(color);
    }
    public boolean teGustaNumeroDe(int numero) {
        return this.numero == numero;
    }

    public boolean teGustaTipoDe(Carta carta) {
        return carta instanceof CartaNumerada;
    }
    public boolean aceptaSobre(Carta nueva) {
        return nueva.teGustaColorDe(this.color) || nueva.teGustaNumeroDe(this.numero);
    }

}
