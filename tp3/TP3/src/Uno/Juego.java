package Uno;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Juego {
    private Carta cartaActual;
    private LinkedList<Jugador> jugadores;
    private Jugador turnoActual;
    private Deque<Carta> mazoRestante;
    private boolean juegoTerminado;
    private String ganador;
    private Controlador controladorActual;

    public Juego(List<Carta> mazo, int cartasPorJugador, String... nombres) {
        this.mazoRestante = new ArrayDeque<>(mazo);
        this.cartaActual  = robarCarta("Mazo vacío al intentar elegir carta inicial");
        this.jugadores    = crearJugadores(nombres, cartasPorJugador);
        asignarVecinos(jugadores);
        this.controladorActual = new ControladorHorario();
        this.turnoActual       = jugadores.getFirst();
        this.cartaActual.aplicarEfecto(this);
    }

    public String colorCartaActual() {
        return cartaActual.color();
    }

    public Carta cartaActual() {
        return cartaActual;
    }

    public String jugadorEnTurno() {
        return this.turnoActual.getNombre();
    }

    public Juego tomar(String jugador) {
        validarJuegoNoTerminado();
        validarTurno(jugador);
        Jugador j = getJugador(jugador);
        j.getMano().add(robarCarta("Mazo vacio al intentar robar carta"));
        j.setTrueToma();
        return this;
    }

    public Juego pasarTurno(String jugador) {
        validarTurno(jugador);
        Jugador j = getJugador(jugador);
        validarYaRoboEnTurno(j);
        siguienteTurno();
        j.setFalseToma();
        return this;
    }

    public Juego jugar(String nombreJugador, Carta carta) {
        validarJuegoNoTerminado();
        validarTurno(nombreJugador);

        Jugador jugador = getJugador(nombreJugador);
        validarPoseeCarta(jugador, carta);
        validarCartaValida(carta);
        int antes = jugador.getMano().size();

        jugador.getMano().remove(carta);

        if (antes == 2 && !carta.isUnoCantado()) {
            robar2Cartas(jugador);
        }
        carta.resetUno();

        if (jugador.getMano().isEmpty()) {
            return declararGanador(nombreJugador);
        }

        cartaActual = carta;
        jugador.setFalseToma();
        carta.aplicarEfecto(this);
        siguienteTurno();
        return this;
    }

    public void siguienteTurno() {
        this.turnoActual = controladorActual.quienSigue(turnoActual);

    }

    public Jugador getTurnoActual(){
        return this.turnoActual;
    }

    public void cambiarSentido() {
        this.controladorActual = this.controladorActual.getOpuesto();
    }

    public void robar2Cartas(Jugador jugador) {
        for (int i = 0; i < 2; i++) {
            jugador.getMano().add(robarCarta("Mazo vacio al intentar robar carta"));
        }
    }

    public String ganador(){
        if (!juegoTerminado) { throw new IllegalStateException("El juego no termino"); }
        else return ganador;
    }


    private void validarYaRoboEnTurno(Jugador jugador) {
        if (!jugador.getYaTomoEnEsteTurno()) {
            throw new IllegalStateException("Solo podés pasar el turno después de robar");
        }
    }
    private void validarJuegoNoTerminado() {
        if (juegoTerminado) {
            throw new IllegalStateException("Juego terminado");
        }
    }
    private void validarTurno(String nombre) {
        if (!jugadorEnTurno().equals(nombre)) {
            throw new IllegalStateException("No es el turno de " + nombre);
        }
    }
    private void validarPoseeCarta(Jugador jugador, Carta carta) {
        if (!jugador.getMano().contains(carta)) {
            throw new IllegalArgumentException("No tenés esa carta en la mano");
        }
    }
    private void validarCartaValida(Carta carta) {
        if (!cartaActual.aceptaSobre(carta)) {
            throw new IllegalArgumentException("La carta no es válida");
        }
    }
    private Juego declararGanador(String nombre) {
        this.juegoTerminado = true;
        this.ganador = nombre;
        return this;
    }

    private Carta robarCarta(String msgError) {
        return Optional.ofNullable(mazoRestante.pollFirst())
                .orElseThrow(() -> new IllegalArgumentException(msgError));
    }

    private List<Carta> repartirMano(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> robarCarta("Mazo sin suficientes cartas para repartir"))
                .collect(Collectors.toList());
    }

    private LinkedList<Jugador> crearJugadores(String[] nombres, int cartasPorJugador) {
        return Stream.of(nombres)
                .map(nombre -> new Jugador(nombre, repartirMano(cartasPorJugador)))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private void asignarVecinos(LinkedList<Jugador> lista) {
        int n = lista.size();
        for (int i = 0; i < n; i++) {
            Jugador actual = lista.get(i);
            Jugador izq    = (i == 0)     ? lista.getLast() : lista.get(i - 1);
            Jugador der    = (i == n - 1) ? lista.getFirst()     : lista.get(i + 1);
            actual.setVecinos(izq, der);
        }
    }

    private Jugador getJugador(String nombre) {
        return jugadores.stream()
                .filter(j -> j.getNombre().equals(nombre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Jugador desconocido: " + nombre));
    }

}
