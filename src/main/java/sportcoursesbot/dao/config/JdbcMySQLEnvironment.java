package sportcoursesbot.dao.config;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class JdbcMySQLEnvironment extends Environment {

    JdbcMySQLEnvironment(boolean initImmediate) {
        super(initImmediate);
    }




    @Override
    void init() {
        try {
            Properties properties = new Properties();
            InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(propertiesStream);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");
            poolSize = Integer.parseInt(properties.getProperty("poolsize"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}