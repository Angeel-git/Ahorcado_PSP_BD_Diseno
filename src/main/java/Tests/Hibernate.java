package Tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Hibernate {

    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private static final SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void insertarJSON(List<Palabra> palabras) {
        eliminarPalabras();
        Session sesion = getSession();
        sesion.beginTransaction();
        for (Palabra palabra : palabras) {
            sesion.persist(palabra);
        }
        sesion.getTransaction().commit();
    }

    public static Palabra hacerConsulta(int numero) {
        try (Session sesion = getSession()) {
            sesion.beginTransaction();
            Palabra palabra = sesion.createQuery("FROM Palabra WHERE id = :id", Palabra.class)
                    .setParameter("id", numero)
                    .uniqueResult();
            sesion.getTransaction().commit();
            return palabra;
        }
    }

    public static void insertarPartida(Partida partida) {
        try (Session sesion = getSession()) {
            sesion.beginTransaction();
            sesion.persist(partida);
            sesion.getTransaction().commit();
        }
    }

    public static void insertarJugador(Jugador jugador) {
        try (Session sesion = getSession()) {
            sesion.beginTransaction();
            sesion.persist(jugador);
            sesion.getTransaction().commit();
        }
    }

    public static boolean jugadorExiste(String nombre) {
        try (Session sesion = getSession()) {
            sesion.beginTransaction();
            Long count = sesion.createQuery(
                            "SELECT COUNT(j) FROM Jugador j WHERE j.nombre = :nombre", Long.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();
            sesion.getTransaction().commit();
            return count != null && count > 0;
        }
    }

    public static Jugador obtenerJugador(String nombre) {
        try (Session sesion = getSession()) {
            sesion.beginTransaction();
            Jugador jugador = sesion.createQuery(
                            "FROM Jugador j WHERE j.nombre = :nombre", Jugador.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();
            sesion.getTransaction().commit();
            return jugador;
        }
    }

    public static void eliminarPalabras() {
        try (Session sesion = getSession()) {
            sesion.beginTransaction();
            sesion.createQuery("DELETE FROM Palabra").executeUpdate();
            sesion.getTransaction().commit();
        }
    }

    public static void cerrar() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
