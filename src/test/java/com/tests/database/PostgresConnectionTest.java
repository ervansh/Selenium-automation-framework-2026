package com.tests.database;

import com.framework.config.FrameworkConfig;
import com.framework.database.DatabaseClient;
import com.framework.database.QueryExecutor;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgresConnectionTest extends BaseTest {
    @Test
    public void postgresShouldExecuteSelectOne() throws SQLException {
        if (!FrameworkConfig.getInstance().getBoolean("db.tests.enabled", false))
            throw new SkipException("Database tests are disabled. Set db.tests.enabled=true to enable.");
        try (Connection connection = DatabaseClient.openConnection()) {
            var values = QueryExecutor.query(connection, "SELECT 1 AS value", row -> row.getInt("value"));
            Assert.assertEquals(values.size(), 1);
            Assert.assertEquals(values.getFirst(), Integer.valueOf(1));
        }
    }
}