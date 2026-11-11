package com.framework.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Explicit transaction boundary. Closing without commit rolls back.
 * The caller owns the Connection and must not use it after this object is closed.
 */
public final class DatabaseTransaction implements AutoCloseable {
    private final Connection connection;
    private boolean completed;
    private boolean closed;

    public DatabaseTransaction(Connection connection) throws SQLException {
        if (connection == null) throw new IllegalArgumentException("Connection cannot be null.");
        this.connection = connection;
        if (connection.isClosed()) throw new SQLException("Cannot start a transaction on a closed connection.");
        connection.setAutoCommit(false);
    }
    public void commit() throws SQLException {
        ensureOpen();
        connection.commit();
        completed = true;
    }
    public void rollback() throws SQLException {
        ensureOpen();
        connection.rollback();
        completed = true;
    }
    private void ensureOpen() {
        if (closed) throw new IllegalStateException("Transaction is already closed.");
        if (completed) throw new IllegalStateException("Transaction has already been completed.");
    }
    @Override
    public void close() throws SQLException {
        if (closed) return;
        closed = true;
        try {
            if (!completed) connection.rollback();
        } finally {
            connection.close();
        }
    }
}
