package Uno;

public abstract class Carta {
    public abstract boolean teGustaColorDe(String color);
    public abstract boolean teGustaNumeroDe(int numero);
    public abstract boolean teGustaTipoDe(Carta carta);
    public abstract boolean aceptaSobre(Carta actual);
    public abstract String color();
    public abstract void aplicarEfecto(Juego juego);
}
