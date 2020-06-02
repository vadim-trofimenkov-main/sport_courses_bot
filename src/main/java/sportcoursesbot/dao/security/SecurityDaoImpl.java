package sportcoursesbot.dao.security;

import sportcoursesbot.dao.config.ConnectionManager;
import sportcoursesbot.shared.exception.UserNotFoundException;
import sportcoursesbot.shared.tool.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityDaoImpl implements SecurityDao {

    public static final String STATUS = "status";
    public static final String SELECT_USER_STATUS_BY_ID = "SELECT status FROM users WHERE id=?";

    @Override
    public String getUserRole(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.take();
            statement = connection.prepareStatement(SELECT_USER_STATUS_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String string = resultSet.getString(STATUS);
                return string;
            } else {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            ExceptionHandler.printAndThrowRuntime(e);
        } finally {
            ConnectionManager.close(statement, connection);
        }
        return null;
    }
}
