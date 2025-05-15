package Uno;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Juego {
    private CartaColorida cartaActual;
    private List<String> jugadores;
    private int turnoActual;
    private Map<String, List<Carta>> manos;       // la mano de cada jugador
    private Deque<Carta> mazoRestante;            // el mazo de donde se robará
    private int sentido;

    public Juego(List<Carta> mazo, int cartasPorJugador, String... jugadores) {
        this.jugadores = Arrays.asList(jugadores); // que esto despues se convierta en las keys del mapa
        this.manos = new HashMap<>();
        this.mazoRestante = new ArrayDeque<>(mazo);
        this.sentido = 1;

        Carta primera = Optional.ofNullable(mazoRestante.pollFirst())
                .orElseThrow(() -> new IllegalArgumentException("Mazo vacío al intentar elegir carta inicial"));

        if (primera instanceof CartaColorida) {
            this.cartaActual = (CartaColorida) primera;
        } else {
            throw new IllegalArgumentException("La primera carta no puede ser una WildCard");
        }


        this.manos = this.jugadores.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        jugador -> IntStream.range(0, cartasPorJugador)
                                .mapToObj(i -> Optional.ofNullable(mazoRestante.pollFirst())
                                        .orElseThrow(() -> new IllegalArgumentException("Mazo sin suficientes cartas para repartir")))
                                .collect(Collectors.toList())
                ));

        this.turnoActual = 0;
    }

    // Devuelve la mano actual de un jugador (solo lectura)
    public List<Carta> getMano(String jugador) {
        List<Carta> mano = manos.get(jugador);
        if (mano == null) throw new IllegalArgumentException("Jugador desconocido: " + jugador);
        return Collections.unmodifiableList(mano);
    }

    // Cuántas quedan en el mazo para robar
    public int cartasRestantesEnMazo() {
        return mazoRestante.size();
    }

    public String colorCartaActual() {
        return cartaActual.color();
    }

//    public int numeroCartaActual() {
//        return cartaActual.numero();
//    }

    public String jugadorEnTurno() {
        return jugadores.get(turnoActual);
    }

    public Juego jugar(String jugador, CartaColorida carta) {


        if (!jugadorEnTurno().equals(jugador)) {
            throw new IllegalStateException("No es el turno de " + jugador);
        }

        List<Carta> mano = manos.get(jugador);
        if (!mano.contains(carta)) {
            throw new IllegalArgumentException("No tienes esa carta en la mano");
        }


        if (!carta.aceptaSobre(cartaActual)){
            throw new IllegalArgumentException("La carta no es válida");
        }

        mano.remove(carta);
        cartaActual = carta;
        if (carta.tipo() == TipoCarta.REVERSE) {
            this.sentido *= -1;
        }
        turnoActual = (turnoActual + this.sentido) % jugadores.size();

        return this;
    }
}
