package sportcoursesbot.dao.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class JdbcConfigurer {
    private static Environment environment;
    private static JdbcConfigurer instance;
    private static final Boolean DOUBLE_CHECK = true;

    {
        initDriver();
    }

    private JdbcConfigurer() {
    }

    private void initDriver() {
        try {
            Class.forName(environment.getDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(environment.getUrl(), environment.getUsername(), environment.getPassword());
    }

    public static JdbcConfigurer getInstance() {
        if (JdbcConfigurer.environment == null) {
            JdbcConfigurer.environment = new JdbcMySQLEnvironment();
        }
        if (instance == null) {
            synchronized (DOUBLE_CHECK) {
                if (instance == null) {
                    instance = new JdbcConfigurer();
                }
            }
        }
        return instance;
    }

    public static void setEnvironment(Environment environment) {
        JdbcConfigurer.environment = environment;
    }
}
