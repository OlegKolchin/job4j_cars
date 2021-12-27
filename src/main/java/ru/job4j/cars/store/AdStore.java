package ru.job4j.cars.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Ad;
import ru.job4j.cars.model.User;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class AdStore {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public static class AdStoreHolder {
        public static final AdStore HOLDER_INSTANCE = new AdStore();
    }

    public static AdStore getInstance() {
        return AdStoreHolder.HOLDER_INSTANCE;
    }

    public void save(Ad ad) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.saveOrUpdate(ad);
        session.getTransaction().commit();
        session.close();
    }

    public void updateAdSaleStatus(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createSQLQuery("update ad set sold = not(sold) where id=" + id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void save(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }

    public User findUserByEmail(String email) {
        return (User) tx(session -> session.createQuery("from User u where u.email = :u_email")
                .setParameter("u_email", email)
                .uniqueResult());
    }

    public User findUserByName(String name) {
        return (User) tx(session -> session.createQuery("from User u where u.name = :name")
                .setParameter("name", name)
                .uniqueResult());
    }


    public List findAll() {
        return tx(session -> session.createQuery("from Ad").list());
    }

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
}
