package sportcoursesbot.dao.course;

import sportcoursesbot.dao.config.ConnectionManager;
import sportcoursesbot.dao.tool.EntityDaoUtil;
import sportcoursesbot.shared.entity.Course;
import sportcoursesbot.shared.exception.UserFriendlyException;

import java.sql.*;
import java.util.List;

public class CourseDaoImpl implements CourseDao {
    private static final String SELECT_ALL_COURSES = "SELECT id, title, start_date FROM courses";
    private static final String SELECT_FULL_COURSE = "SELECT * FROM courses WHERE id = ?";

    @Override
    public List<Course> getAllCoursesShort() {
        List<Course> courses;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.take();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_COURSES);
            courses = EntityDaoUtil.initCoursesShort(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(statement, connection);
        }
        return courses;
    }

    @Override
    public Course getFullCourse(int id) {
        Course course;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionManager.take();
            statement = connection.prepareStatement(SELECT_FULL_COURSE);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                course = EntityDaoUtil.initCourse(resultSet);
            } else {
                throw new UserFriendlyException("Course does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(statement, connection);
        }
        return course;
    }
}
