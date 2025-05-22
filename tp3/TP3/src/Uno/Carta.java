package Uno;

public abstract class Carta {
    protected boolean unoCantado;
    public Carta(){
        this.unoCantado = false;
    }
    public abstract boolean teGustaColorDe(String color);
    public abstract boolean teGustaNumeroDe(int numero);
    public boolean teGustaTipoDe(Carta carta) { return this.tipo().equals(carta.tipo()); }
    public abstract boolean aceptaSobre(Carta actual);
    public abstract String color();
    public String tipo() { return this.getClass().getSimpleName(); }
    public abstract void aplicarEfecto(Juego juego);

    public Carta uno() {
        this.unoCantado = true;
        return this;
    }
    public boolean isUnoCantado() {
        return unoCantado;
    }
    public void resetUno() {
        this.unoCantado = false;
    }
}
