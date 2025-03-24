package Tests;

import jakarta.persistence.*;

@Entity
@Table(name = "jugadores") // Especifica el nombre de la tabla
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento del ID
    private int id;

    @Column(nullable = false, unique = true) // Nombre no puede ser nulo y debe ser único
    private String nombre;

    // Constructor vacío requerido por Hibernate
    public Jugador() {}

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
