package Uno;

public class CartaDeAccion extends Carta {
    protected String color;
    public CartaDeAccion(String color, TipoCarta tipo) {
        super(tipo);
        this.color = color;
    }
    public String color() {return this.color;}
    public boolean teGustaColorDe(String color) {
        return this.color.equals(color);
    }
    public boolean teGustaNumeroDe(int numero) {
        return false;
    }
    public boolean teGustaTipoDe(TipoCarta tipo){
        return this.tipo.equals(tipo);
    }
    public boolean aceptaSobre(Carta nueva){
        return nueva.teGustaColorDe(this.color) || nueva.teGustaTipoDe(this.tipo());
    }
}
