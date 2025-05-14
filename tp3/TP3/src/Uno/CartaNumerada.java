package Uno;

public class CartaNumerada extends Carta {
    private int numero;
    public CartaNumerada(int num, String color) {
        super(color);
        this.numero = num;
    }
    public int numero() {return this.numero;}

    public boolean aceptaSobre(Carta actual) {
        return (this.color.equals(actual.color()) || actual.tipo().equals(this.tipo()) && this.numero == ((CartaNumerada) actual).numero());
    }

    public TipoCarta tipo() {
        return TipoCarta.NUMERADA;
    }


}
