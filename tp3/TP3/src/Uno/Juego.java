package Uno;


import java.util.*;


public class Juego {
    private Carta cartaActual;
    private List<Jugador> jugadores;
    private int turnoActual;
    private Deque<Carta> mazoRestante;
    private int sentido;
    private boolean yaRoboEnEsteTurno;
    private boolean juegoTerminado;
    private String ganador;

    public Juego(List<Carta> mazo, int cartasPorJugador, String... jugadores) {

        this.mazoRestante = new ArrayDeque<>(mazo);
        this.sentido = 1;
        this.juegoTerminado = false;
        this.yaRoboEnEsteTurno = false;

        this.cartaActual  = Optional.ofNullable(mazoRestante.pollFirst())
                .orElseThrow(() -> new IllegalArgumentException("Mazo vacío al intentar elegir carta inicial"));

        this.jugadores = new ArrayList<>();
        for (String nombre : jugadores) {
            List<Carta> mano = new ArrayList<>();
            for (int i = 0; i < cartasPorJugador; i++) {
                mano.add(Optional.ofNullable(mazoRestante.pollFirst())
                        .orElseThrow(() -> new IllegalArgumentException("Mazo sin suficientes cartas para repartir")));
            }
            this.jugadores.add(new Jugador(nombre, mano));
        }
        this.turnoActual = 0;

        this.cartaActual.aplicarEfecto(this);
    }

    private Jugador getJugador(String nombre) {
        return jugadores.stream()
                .filter(j -> j.getNombre().equals(nombre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Jugador desconocido: " + nombre));
    }


    public String colorCartaActual() {
        return cartaActual.color();
    }

    public String jugadorEnTurno() {
        return jugadores.get(turnoActual).getNombre();
    }

    public Juego tomar(String jugador) {
        if (juegoTerminado) { throw new IllegalStateException("Jugador terminado"); }
        if (!jugadorEnTurno().equals(jugador)) {
            throw new IllegalStateException("No es el turno de " + jugador);
        }
        Carta cartaTomada = Optional.ofNullable(mazoRestante.pollFirst())
                .orElseThrow(() -> new IllegalArgumentException("Mazo vacío al intentar tomar carta del mazo"));
        getJugador(jugador).getMano().add(cartaTomada);
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
        turnoActual = turnoActual();
        yaRoboEnEsteTurno = false;
        return this;
    }

    public Juego jugar(String nombreJugador, Carta carta) {
        if (juegoTerminado) throw new IllegalStateException("Jugador terminado");
        if (!jugadorEnTurno().equals(nombreJugador))
            throw new IllegalStateException("No es el turno de " + nombreJugador);
        Jugador jugador = getJugador(nombreJugador);
        List<Carta> mano = jugador.getMano();
        if (!mano.contains(carta))
            throw new IllegalArgumentException("No tenés esa carta en la mano");
        if (!cartaActual.aceptaSobre(carta))
            throw new IllegalArgumentException("La carta no es válida");

        mano.remove(carta);
        if (mano.isEmpty()) {
            juegoTerminado = true;
            ganador = nombreJugador;
            return this;
        }

        cartaActual = carta;
        yaRoboEnEsteTurno = false;

        carta.aplicarEfecto(this);
        siguienteTurno();
        return this;
    }

    public void siguienteTurno() {
        turnoActual = turnoActual();
    }

    public int turnoActual() {
        return Math.floorMod(turnoActual + sentido, jugadores.size());
    }

    public void cambiarSentido() {
        sentido *= -1;
    }

    public void robar2Cartas(int idx) {
        Jugador jugador = jugadores.get(idx);
        for (int i = 0; i < 2; i++) {
            Carta c = Optional.ofNullable(mazoRestante.pollFirst())
                    .orElseThrow(() -> new IllegalArgumentException("Mazo vacío"));
            jugador.getMano().add(c);
        }
    }


    public String ganador(){
        if (!juegoTerminado) { throw new IllegalStateException("El juego no termino"); }
        else return ganador;
    }


}
