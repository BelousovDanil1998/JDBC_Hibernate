package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String create = "CREATE TABLE IF NOT EXISTS table_name (\n id int auto_increment, name VARCHAR(255) null, lastName VARCHAR(255) null, age int null, CONSTRAINT table_name_pk PRIMARY KEY (id));";
            Query query = session.createSQLQuery(create);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("table was created");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String create = "DROP TABLE IF EXISTS table_name";
            Query query = session.createSQLQuery(create);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("table was dropped");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction t = null;
        try (Session session = sessionFactory.openSession()) {
            t = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем Ц " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction t = null;
        try (Session session = sessionFactory.openSession()) {
            t = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String create = "TRUNCATE table_name";
            Query query = session.createSQLQuery(create);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
