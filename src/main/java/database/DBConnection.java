package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/hackathon_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "progetto";

    public static Connection getConnection() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("Errore di connessione al database!");
            throw e;
        }
    }
}


