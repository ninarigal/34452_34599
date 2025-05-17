package Uno;

public class CartaNumerada extends Carta {
    private int numero;
    private String color;
    public CartaNumerada(int num, String color) {
        super(TipoCarta.NUMERADA);
        this.color = color;
        this.numero = num;
    }
    public int numero() {return this.numero;}
    public String color() {return this.color;}
    public boolean teGustaColorDe(String color) {
        return this.color.equals(color);
    }
    public boolean teGustaNumeroDe(int numero) {
        return this.numero == numero;
    }
    public boolean teGustaTipoDe(TipoCarta tipo) {
        return this.tipo().equals(tipo);
    }
    public boolean aceptaSobre(Carta nueva) {
        return nueva.teGustaColorDe(this.color) || nueva.teGustaNumeroDe(this.numero);
    }
}
