package Uno;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    private Carta cartaActual;
    private List<String> jugadores = new ArrayList<String>();
    private int turnoActual;

    public Juego(List<Carta> mazo, int cartasPorJugador, String jugador1, String jugador2) {
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        cartaActual = mazo.getFirst();
        turnoActual = 0;
    }

    public String colorCartaActual() {
        return cartaActual.color();
    }

    public int numeroCartaActual() {
        return cartaActual.numero();
    }

    public Juego jugar(String jugador, Carta carta) {
        if (!jugadores.get(turnoActual).equals(jugador)) {
            throw new IllegalStateException("No es el turno de " + jugador);
        }

        if ((cartaActual.color().equals(carta.color())) || cartaActual.numero() == carta.numero()){
            cartaActual = carta;
            return this;
        }
        throw new IllegalArgumentException("La carta no es valida");
    }
}
