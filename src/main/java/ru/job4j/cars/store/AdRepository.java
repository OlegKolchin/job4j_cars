package ru.job4j.cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class AdRepository {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public List recentAds() {
       return tx(session -> session.createQuery("from Ad a where a.created between :yes and :tod")
               .setParameter("yes", Date.from(LocalDate.now()
                       .minusDays(1).atStartOfDay(ZoneId.systemDefault())
                       .toInstant()))
               .setParameter("tod", new Date())
               .list());
    }

    public List findByBrand(String brand) {
        return tx(session -> session.createQuery("from Ad a join a.car.brand b where b.name = :bname")
                .setParameter("bname", brand).list());
    }

    public List findWithPhoto() {
        return tx(session -> session.createQuery("from Ad a where photoUrl != null")
                .list());
    }

    private <T> T tx(final Function<Session, T> command) {
        Session session = sf.openSession();
        try {
            T rsl = command.apply(session);
            session.beginTransaction().commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        AdRepository repository = new AdRepository();
        List list = repository.findWithPhoto();
        System.out.println(list.isEmpty());
    }
}
