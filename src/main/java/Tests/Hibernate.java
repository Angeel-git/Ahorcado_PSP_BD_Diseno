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

    public static void insertarJugador(Jugador jugador) {
        try (Session sesion = getSession()) {
            sesion.beginTransaction();
            sesion.persist(jugador); // Guarda el jugador en la base de datos
            sesion.getTransaction().commit();
        }
    }

    public static void cerrar() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
