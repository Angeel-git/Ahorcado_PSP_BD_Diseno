package Tests;

import jakarta.persistence.*;

@Entity
@Table(name = "partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private int id;

    @ManyToOne
    @JoinColumn(name = "jugador_id") // Clave foránea a Jugador
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "palabra_id") // Clave foránea a Palabra
    private Palabra palabra; // Referencia a la palabra jugada

    @Column
    private int intentos;

    @Column
    private boolean gano;

    public Partida() {
    }

    public Partida(Jugador jugador, Palabra palabra, int intentos, boolean gano) {
        this.jugador = jugador;
        this.palabra = palabra;
        this.intentos = intentos;
        this.gano = gano;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Palabra getPalabra() {
        return palabra;
    }

    public void setPalabra(Palabra palabra) {
        this.palabra = palabra;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public boolean isGano() {
        return gano;
    }

    public void setGano(boolean gano) {
        this.gano = gano;
    }
}
