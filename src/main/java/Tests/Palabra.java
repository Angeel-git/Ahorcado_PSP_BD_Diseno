package Tests;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Palabra {
    @Id
    private int id;
    @Column
    private String palabra;

    public Palabra() {
    }

    public Palabra(int id) {
        this.id = id;
    }

    public Palabra(int id, String palabra) {
        this.id = id;
        this.palabra = palabra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    @Override
    public String toString() {
        return "Palabra{id=" + id + ", palabra='" + palabra + "'}";
    }
}
