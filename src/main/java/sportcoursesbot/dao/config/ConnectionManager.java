package sportcoursesbot.dao.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static ConnectionPool POOL = new ConnectionPool(new JdbcMySQLEnvironment(true));

    public static Connection take() {
        return POOL.take();
    }

    public static void close(Statement statement, Connection connection) throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public static void closerOrThrow(Statement statement, Connection connection) {
        try {
            close(statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void close(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }
}
