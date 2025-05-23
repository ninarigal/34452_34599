package Uno;

import java.util.Objects;

public class CartaComodin extends Carta {
    private String colorAsignado;

    private CartaComodin(){}
    public static CartaComodin with(){return new CartaComodin();}

    public boolean teGustaColorDe(String color) {
        return true;
    }

    public boolean teGustaNumeroDe(int numero) {
        return false;
    }

    public boolean teGustaTipoDe(Carta carta) {
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

    public void aplicarEfecto(Juego juego) {}

    public boolean aceptaSobre(Carta nueva) {
        return nueva.teGustaColorDe(this.colorAsignado) ;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof CartaComodin;
    }



}

