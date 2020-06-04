package sportcoursesbot.dao.coach;

import sportcoursesbot.dao.config.ConnectionManager;
import sportcoursesbot.dao.tool.EntityDaoUtil;
import sportcoursesbot.shared.entity.Coach;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CoachDaoImpl implements CoachDao {
    private static String SELECT_ALL_COACHES = "SELECT * FROM coaches";

    @Override
    public List<Coach> getAllCoaches() {
        List<Coach> coaches;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.take();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_COACHES);
            coaches = EntityDaoUtil.initCoaches(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            ConnectionManager.close(statement,connection);
        }
        return coaches;
    }
}
