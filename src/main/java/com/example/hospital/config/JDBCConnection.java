package com.example.hospital.config;

import java.sql.Connection;
import java.sql.SQLException;

public interface JDBCConnection {
    Connection getConnection() throws java.sql.SQLException;
}
