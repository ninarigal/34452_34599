package Uno;


import java.util.*;


public class Juego {
    private Carta cartaActual;
    private List<Jugador> jugadores;
    private Jugador turnoActual;
    private Deque<Carta> mazoRestante;
    private boolean yaRoboEnEsteTurno;
    private boolean juegoTerminado;
    private String ganador;
    private Controlador controladorActual;

    public Juego(List<Carta> mazo, int cartasPorJugador, String... jugadores) {

        this.mazoRestante = new ArrayDeque<>(mazo);
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
            this.jugadores.addLast(new Jugador(nombre, mano));
        }


        for (int i = 0; i < this.jugadores.size(); i++) {
            Jugador actual = this.jugadores.get(i);

            // Vecino izquierdo: si estoy en 0, tomo el último; sino, el anterior
            Jugador izq;
            if (i == 0) {
                izq = this.jugadores.getLast();
            } else {
                izq = this.jugadores.get(i - 1);
            }

            // Vecino derecho: si estoy en el último, tomo el primero; sino, el siguiente
            Jugador der;
            if (i == this.jugadores.size() - 1) {
                der = this.jugadores.getFirst();
            } else {
                der = this.jugadores.get(i + 1);
            }
            actual.setVecinos(izq, der);
        }

        this.controladorActual = new ControladorHorario();
        this.turnoActual = this.jugadores.getFirst();
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

    public Carta cartaActual() {
        return cartaActual;
    }

    public String jugadorEnTurno() {
        return this.turnoActual.getNombre();
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
        siguienteTurno();
        yaRoboEnEsteTurno = false;
        return this;
    }

    public Juego jugar(String nombreJugador, Carta carta) {
        if (juegoTerminado) throw new IllegalStateException("Juego terminado");
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
    //    turnoActual = this.jugadores.removeFirst();
    //    this.jugadores.addLast(this.turnoActual);
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
