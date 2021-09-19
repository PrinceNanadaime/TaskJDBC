package jm.task.core.jdbc.util;

import java.sql.Connection;
import com.mysql.cj.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util implements AutoCloseable {

    private Connection connection;

    public Connection getConnection(){
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql",
                    "root", "root");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    private static StandardServiceRegistry registry;

    public static SessionFactory getSessionFactory() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default_class");
        EntityManager entityManager = emf.createEntityManager();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        return session.getSessionFactory();
    }

    @Override
    public void close(){
        try {
            if (connection != null &&!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
