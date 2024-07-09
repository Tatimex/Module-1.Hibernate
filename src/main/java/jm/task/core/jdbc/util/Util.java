package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Util {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure( "hibernate.cfg.xml" );
                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory();
                System.out.println( "Session Factory is built" );
            } catch (Exception e) {
                System.out.println( "There is an exception in the Session Factory" );
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
