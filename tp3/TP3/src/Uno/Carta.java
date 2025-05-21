package Uno;

public abstract class Carta {
    public abstract boolean teGustaColorDe(String color);
    public abstract boolean teGustaNumeroDe(int numero);
    public boolean teGustaTipoDe(Carta carta) {
        return this.tipo().equals(carta.tipo());
    }
    public abstract boolean aceptaSobre(Carta actual);
    public abstract String color();
    public String tipo() {
        return this.getClass().getSimpleName();
    }
    public abstract void aplicarEfecto(Juego juego);

    private boolean unoCantado = false;
    public Carta uno(Juego any) {
        this.unoCantado = true;
        return this;
    }
    protected boolean isUnoCantado() {
        return unoCantado;
    }
    protected void resetUno() {
        this.unoCantado = false;
    }
}
