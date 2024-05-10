package todocreate.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {

        String jdbcUrl = "jdbc:postgresql://researchdatabase.c3qcyaa4296a.ap-south-1.rds.amazonaws.com:5432/research_db";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "postgres", "password");
            return connection;
        }catch (SQLException e){
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return null;

    }

}
