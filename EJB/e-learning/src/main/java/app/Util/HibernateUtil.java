package app.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import app.Models.*;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static Session getSession() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(Users.class);
                configuration.addAnnotatedClass(Instructors.class);
                configuration.addAnnotatedClass(Students.class);
                configuration.addAnnotatedClass(Courses.class);
                configuration.addAnnotatedClass(Notifications.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory.openSession();
    }
}