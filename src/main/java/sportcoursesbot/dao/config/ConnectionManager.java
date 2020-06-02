package sportcoursesbot.dao.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private final static Environment ENVIRONMENT = new JdbcMySQLEnvironment(true);
    private final static ConnectionPool POOL = new ConnectionPool(ENVIRONMENT);

    public static Connection take() {
        return POOL.takeConnection();
    }

    public static void close(Statement statement, Connection connection) {
        close(statement);
        close(connection);
    }

    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
