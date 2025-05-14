package Uno;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Juego {
    private Carta cartaActual;
    private List<String> jugadores;
    private int turnoActual;
    private Map<String, List<Carta>> manos;       // la mano de cada jugador
    private Deque<Carta> mazoRestante;            // el mazo de donde se robará

    public Juego(List<Carta> mazo, int cartasPorJugador, String... jugadores) {
        this.jugadores = Arrays.asList(jugadores); // que esto despues se convierta en las keys del mapa
        this.manos = new HashMap<>();
        this.mazoRestante = new ArrayDeque<>(mazo);

        this.cartaActual = Optional.ofNullable(mazoRestante.pollFirst())
                .orElseThrow(() -> new IllegalArgumentException("Mazo vacío al intentar elegir carta inicial"));

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

    public int numeroCartaActual() {
        return cartaActual.numero();
    }

    public String jugadorEnTurno() {
        return jugadores.get(turnoActual);
    }

    public Juego jugar(String jugador, Carta carta) {
//        if (!jugadores.get(turnoActual).equals(jugador)) {
//            throw new IllegalStateException("No es el turno de " + jugador);
//        }
//
//        if ((cartaActual.color().equals(carta.color())) || cartaActual.numero() == carta.numero()){
//            cartaActual = carta;
//            return this;
//        }
//        throw new IllegalArgumentException("La carta no es valida");

//        if (gameOver) {
//            throw new IllegalStateException("La partida ya ha terminado");
//        }

        // 1) turno correcto
        if (!jugadorEnTurno().equals(jugador)) {
            throw new IllegalStateException("No es el turno de " + jugador);
        }

        // 2) carta en la mano
        List<Carta> mano = manos.get(jugador);
        if (!mano.contains(carta)) {
            throw new IllegalArgumentException("No tienes esa carta en la mano");
        }

        // 3) legalidad de la jugada (mismo color o número)
        if (!cartaActual.aceptaCarta(carta)){
            throw new IllegalArgumentException("La carta no es válida sobre " +
                    cartaActual.color() + " " + cartaActual.numero());
        }

        // 4) ejecutar jugada
        mano.remove(carta);
        cartaActual = carta;

//        // 5) fin de partida?
//        if (mano.isEmpty()) {
//            gameOver = true;
//            return this;
//        }

        // 6) avanzar turno
        turnoActual = (turnoActual + 1) % jugadores.size();
        return this;
    }
}
