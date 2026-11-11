package com.framework.database;

import com.framework.config.FrameworkConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Opens short-lived JDBC connections; callers own and close returned connections. */
public final class DatabaseClient {
    private DatabaseClient() { }

    public static Connection openConnection() throws SQLException {
        FrameworkConfig config = FrameworkConfig.getInstance();
        String url = config.get("db.url", "");
        if (url.isBlank()) throw new IllegalStateException("Set db.url before executing database tests.");
        return DriverManager.getConnection(url, config.get("db.username", ""), config.get("db.password", ""));
    }
}