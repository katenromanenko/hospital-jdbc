package com.example.hospital.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresConnection implements JDBCConnection {
    @Override
    public Connection getConnection() {
        try {
            String url = "jdbc:postgresql://localhost:5432/clinic";
            String user = "postgres";
            String password = "postgres";

            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}