package Uno;

public class CartaComodin extends Carta {
    private String colorAsignado;
    public CartaComodin() {
        super(TipoCarta.WILD);
    }

    public boolean teGustaColorDe(String color) {
        return true;
    }

    public boolean teGustaNumeroDe(int numero) {
        return false;
    }

    public boolean teGustaTipoDe(TipoCarta tipo) {
        return true;
    }

    public CartaComodin comoRojo(){
        this.colorAsignado = "Rojo";
        return this;
    }

    public CartaComodin comoVerde(){
        this.colorAsignado = "Verde";
        return this;
    }

    public CartaComodin comoAzul(){
        this.colorAsignado = "Azul";
        return this;
    }

    public CartaComodin comoAmarillo(){
        this.colorAsignado = "Amarillo";
        return this;
    }
    public String color() {return this.colorAsignado;}

    public boolean aceptaSobre(Carta nueva) {
        return nueva.teGustaColorDe(this.colorAsignado) ;
    }
}

