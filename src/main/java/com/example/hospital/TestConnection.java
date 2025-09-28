package com.example.hospital;

import com.example.hospital.config.JDBCConnection;
import com.example.hospital.config.PostgresConnection;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        JDBCConnection cp = new PostgresConnection();
        try (Connection conn = cp.getConnection()) {
            System.out.println("OK: подключились к " + conn.getMetaData().getURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
