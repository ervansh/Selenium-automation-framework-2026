package com.framework.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Parameterized JDBC helpers. Bind values; never concatenate untrusted input into SQL. */
public final class QueryExecutor {
    private QueryExecutor() { }

    @FunctionalInterface
    public interface RowMapper<T> { T map(ResultSet resultSet) throws SQLException; }

    public static int update(Connection connection, String sql, Object... parameters) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, parameters);
            return statement.executeUpdate();
        }
    }

    public static <T> List<T> query(Connection connection, String sql, RowMapper<T> mapper,
                                    Object... parameters) throws SQLException {
        List<T> results = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) results.add(mapper.map(resultSet));
            }
        }
        return List.copyOf(results);
    }

    private static void bind(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int index = 0; index < parameters.length; index++) statement.setObject(index + 1, parameters[index]);
    }
}