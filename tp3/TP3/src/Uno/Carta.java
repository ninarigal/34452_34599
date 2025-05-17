package Uno;

public abstract class Carta {
    protected TipoCarta tipo;
    public Carta(TipoCarta tipo) {
        this.tipo = tipo;
    }
    public  TipoCarta tipo(){ return this.tipo; };
    public abstract boolean teGustaColorDe(String color);
    public abstract boolean teGustaNumeroDe(int numero);
    public abstract boolean teGustaTipoDe(TipoCarta tipo);
    public abstract boolean aceptaSobre(Carta actual);
    public abstract String color();
}
