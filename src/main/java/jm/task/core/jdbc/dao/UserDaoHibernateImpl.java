package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery( """
                                        CREATE TABLE IF NOT EXISTS "user"(
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(64),
                                        lastname VARCHAR(64),
                                        age SMALLINT
                                        );
                    """ ).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery( "DROP TABLE IF EXISTS \"user\"" ).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = null;
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            allUsers = session.createQuery("FROM User", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUsers;
    }

//    @Override
//    public List<User> getAllUsers() {
//
//        List<User> allUser = new ArrayList<>();
//        try (final Session session = Util.getSessionFactory().openSession()) {
//            allUser = (List<User>) session.createQuery("FROM User").list();
//            System.out.println("Лист создан");
//        }
//        return allUser;
//    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery( "DELETE FROM User" ).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
