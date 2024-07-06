package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";


    public static Connection getConnection() {
        Connection connection = null;
        try {
//            Class<Driver> driverClass = Driver.class;
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection to  database was successful.");
        } catch (SQLException ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();

        }
        return connection;
    }
}