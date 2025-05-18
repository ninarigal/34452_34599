package Uno;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Juego {
    private Carta cartaActual;
    private List<String> jugadores;
    private int turnoActual;
    private Map<String, List<Carta>> manos;
    private Deque<Carta> mazoRestante;
    private int sentido;
    private boolean yaRoboEnEsteTurno = false;

    public Juego(List<Carta> mazo, int cartasPorJugador, String... jugadores) {
        this.jugadores = Arrays.asList(jugadores);
        this.mazoRestante = new ArrayDeque<>(mazo);
        this.sentido = 1;

        Carta primera = Optional.ofNullable(mazoRestante.pollFirst())
                .orElseThrow(() -> new IllegalArgumentException("Mazo vacío al intentar elegir carta inicial"));

        this.cartaActual = primera;

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

    public List<Carta> getMano(String jugador) {
        List<Carta> mano = manos.get(jugador);
        if (mano == null) throw new IllegalArgumentException("Jugador desconocido: " + jugador);
        return Collections.unmodifiableList(mano);
    }

    public int cartasRestantesEnMazo() {
        return mazoRestante.size();
    }

    public String colorCartaActual() {
        return cartaActual.color();
    }

    public String jugadorEnTurno() {
        return jugadores.get(turnoActual);
    }

    public Juego tomar(String jugador) {
        if (!jugadorEnTurno().equals(jugador)) {
            throw new IllegalStateException("No es el turno de " + jugador);
        }
        Carta cartaTomada = Optional.ofNullable(mazoRestante.pollFirst())
                .orElseThrow(() -> new IllegalArgumentException("Mazo vacío al intentar tomar carta del mazo"));
        manos.get(jugador).add(cartaTomada);
        yaRoboEnEsteTurno = true;
        return this;
    }

    public Juego pasarTurno(String jugador) {
        if (!jugadorEnTurno().equals(jugador)) {
            throw new IllegalStateException("No es el turno de " + jugador);
        }
        if (!yaRoboEnEsteTurno) {
            throw new IllegalStateException("Solo podés pasar el turno después de robar");
        }
        turnoActual = siguienteTurno();
        yaRoboEnEsteTurno = false;
        return this;
    }

    public Juego jugar(String jugador, Carta carta) {
        if (!jugadorEnTurno().equals(jugador)) {
            throw new IllegalStateException("No es el turno de " + jugador);
        }
        List<Carta> mano = manos.get(jugador);
        if (!mano.contains(carta)) {
            throw new IllegalArgumentException("No tenés esa carta en la mano");
        }
        if (!cartaActual.aceptaSobre(carta)) {
            throw new IllegalArgumentException("La carta no es válida");
        }
        mano.remove(carta);
        cartaActual = carta;
        yaRoboEnEsteTurno = false;

        switch (carta.tipo()) {
            case REVERSE:
                sentido *= -1;
                turnoActual = siguienteTurno();
                break;
            case SKIP:
                turnoActual = siguienteTurno();
                turnoActual = siguienteTurno();
                break;
            case DRAW_TWO:
                int siguiente = siguienteTurno();
                for (int i = 0; i < 2; i++) {
                    Carta robada = Optional.ofNullable(mazoRestante.pollFirst())
                            .orElseThrow(() -> new IllegalArgumentException("Mazo vacío al intentar robar"));
                    manos.get(jugadores.get(siguiente)).add(robada);
                }
                turnoActual = siguienteTurno();
                turnoActual = siguienteTurno();
                break;
            default:
                turnoActual = siguienteTurno();
        }
        return this;
    }


    private int siguienteTurno() {
        return Math.floorMod(turnoActual + sentido, jugadores.size());
    }
}
