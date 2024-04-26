package tn.scholarcircle.testapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class MyDatabase {

    private final String URL ="jdbc:mysql://localhost:3306/testapp";
    private final String USER ="root";
    private final String PASSWORD ="";
    private Connection connection;
    private static MyDatabase instance;

    public MyDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static MyDatabase getInstance() {
        if (instance == null)
            instance = new MyDatabase();
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
}
