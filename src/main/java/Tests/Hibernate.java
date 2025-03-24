package Tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        try (Session sesion = getSession()) {
            Transaction transaction = sesion.beginTransaction();

            for (Palabra palabra : palabras) {
                // Verificar si ya existe
                Palabra existente = sesion.get(Palabra.class, palabra.getId());
                if (existente == null) {
                    sesion.persist(palabra); // Insertar solo si no existe
                } else {
                    sesion.merge(palabra); // Actualizar si ya existe
                }
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Palabra hacerConsulta(int numero) {
        try (Session sesion = getSession()) {
            return sesion.createQuery("FROM Palabra WHERE id = :id", Palabra.class)
                    .setParameter("id", numero)
                    .uniqueResult();
        }
    }
    public static Jugador hacerConsultaJug(String nombre) {
        try (Session sesion = getSession()) {
            return sesion.createQuery("FROM Jugador WHERE nombre = :nombre", Jugador.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();
        }
    }

    public static void insertarPartida(Partida partida) {
        try (Session sesion = getSession()) {
            Transaction transaction = sesion.beginTransaction();
            sesion.persist(partida);
            transaction.commit();
        }
    }

    public static void insertarJugador(Jugador jugador) {
        try (Session sesion = getSession()) {
            Transaction transaction = sesion.beginTransaction();
            sesion.persist(jugador);
            transaction.commit();
        }
    }

    public static boolean jugadorExiste(String nombre) {
        try (Session sesion = getSession()) {
            Long count = sesion.createQuery(
                            "SELECT COUNT(j) FROM Jugador j WHERE j.nombre = :nombre", Long.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();
            return count != null && count > 0;
        }
    }

    public static Jugador obtenerJugador(String nombre) {
        try (Session sesion = getSession()) {
            return sesion.createQuery("FROM Jugador j WHERE j.nombre = :nombre", Jugador.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();
        }
    }

    public static void eliminarPalabras() {
        try (Session sesion = getSession()) {
            Transaction transaction = sesion.beginTransaction();

            // Eliminar primero las referencias en Partida
            sesion.createQuery("DELETE FROM Partida WHERE palabra_id IN (SELECT id FROM Palabra)").executeUpdate();

            // Ahora eliminar las palabras
            sesion.createQuery("DELETE FROM Palabra").executeUpdate();

            transaction.commit();
        }
    }

    public static void cerrar() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
